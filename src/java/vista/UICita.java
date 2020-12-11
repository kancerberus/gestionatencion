/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vista;

import controlador.GestorAgenda;
import controlador.GestorCita;
import controlador.GestorEntidad;
import controlador.GestorEspecialidad;
import controlador.GestorPaciente;
import controlador.GestorInformeCitas;
import controlador.GestorProcedimiento;
import controlador.GestorProfesional;
import controlador.GestorTerapia;
import controlador.GestorUtilidades;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import modelo.Cita;
import modelo.Entidad;
import modelo.Especialidad;
import modelo.FranjaAgenda;
import modelo.Objeto;
import modelo.Paciente;
import modelo.Procedimiento;
import modelo.Profesional;
import modelo.Terapia;
import modelo.FranjaInforme;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultScheduleEvent;
import org.primefaces.model.DefaultScheduleModel;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.ScheduleEvent;
import org.primefaces.model.ScheduleModel;
import org.primefaces.model.StreamedContent;
import util.Utilidades;

/**
 *
 * @author Andres
 */
public class UICita implements Serializable {

    private String usuario;
    private Cita cita;
    private Date fechaActual = new Date();
    private List<SelectItem> comboListaProcedimientos;
    private List<String> listaProcedimientos;
    private List<FranjaAgenda> listaFaCalendario;
    private List<FranjaAgenda> listaFaCalendarioBk;
    //private List<FranjaAgenda> listaFaCalendarioPuente;
    private FranjaAgenda franjaExtendida;
    private List<SelectItem> listaObservacionesCita;
    private List<Terapia> listaTerapias;
    private List<Procedimiento> listaProcedimientosBase;
    private List<Paciente> listaPacienteMultiple;
    private List<Cita> listaCitaMultiple;
    private Boolean mostrarUltimasCitas;
    private Boolean usarFranjaExtendida;

    private GestorPaciente gestorPaciente;
    private GestorInformeCitas gestorInformeCitas;
    private GestorEspecialidad gestorEspecialidad;
    private GestorEntidad gestorEntidad;
    private GestorProfesional gestorProfesional;
    private GestorCita gestorCita;
    private GestorProcedimiento gestorProcedimiento;
    private GestorUtilidades gestorUtilidades;

    //calendario
    private ScheduleModel eventModel;
    private ScheduleModel eventModelBk;
    //private ScheduleModel eventModelPuente;
    private List<SelectItem> listaProfesionales;
    private List<Profesional> listaProfesionalesProximaFranja;
    private ScheduleEvent franjaEvento = new DefaultScheduleEvent();
    private ScheduleEvent eventoExtendido;
    private Date fechaCita = new Date();

    //consulta
    private Date fechaInicial = new Date();
    private Date fechaFinal = new Date();
    private List<Cita> listaCitas;
    private List<SelectItem> listaEstadosCita;
    private Cita citaSeleccionada;
    private List<FranjaInforme> listaInformes;

    public Utilidades util = new Utilidades();

    //dialogo estado
    private Boolean razonHab;
    private Boolean responsableHab;
    private Boolean medioHab;
    private Boolean autorizacionHab;
    private StreamedContent archivo;

    public UICita() throws Exception {
        cita = new Cita();
        cita.setPaciente(new Paciente());
        cita.setEspecialidad(new Especialidad());
        cita.setProfesional(new Profesional());
        cita.setEntidad(new Entidad());
        cita.setEstado(new Objeto());
        gestorPaciente = new GestorPaciente();
        gestorInformeCitas = new GestorInformeCitas();
        gestorEspecialidad = new GestorEspecialidad();
        gestorEntidad = new GestorEntidad();
        gestorProfesional = new GestorProfesional();
        gestorCita = new GestorCita();
        comboListaProcedimientos = new ArrayList<>();
        gestorProcedimiento = new GestorProcedimiento();
        gestorUtilidades = new GestorUtilidades();
        listaFaCalendario = new ArrayList<>();
        listaProcedimientos = new ArrayList<>();
        listaProcedimientosBase = new ArrayList<>();
        listaPacienteMultiple = new ArrayList<>();
        listaCitaMultiple = new ArrayList<>();
        listaCitas = new ArrayList<>();
        listaInformes = new ArrayList<>();
        listaFaCalendarioBk = new ArrayList<>();
        //listaFaCalendarioPuente = new ArrayList<>();

        citaSeleccionada = new Cita();
        cargarListaEstadosCita();
        cargarListaObservacionesCita();
        eventModel = new DefaultScheduleModel();
        eventModelBk = new DefaultScheduleModel();

        razonHab = Boolean.FALSE;
        responsableHab = Boolean.FALSE;
        medioHab = Boolean.FALSE;
        autorizacionHab = Boolean.FALSE;

        mostrarUltimasCitas = Boolean.FALSE;
    }

    public void onEventSelect(SelectEvent selectEvent) {
        Integer i, j, cantValidos = 0;
        Calendar cal = Calendar.getInstance();
        Calendar calEx = Calendar.getInstance();
        SimpleDateFormat formatoHora = new SimpleDateFormat("HH:mm:ss");
        DefaultScheduleEvent evento;
        Boolean valido = true;
        Integer indicePro = 0;
        Integer indiceCita = 0, duracionAcumulada = 0;
        String titulo = "", nombreProcedimiento = "";
        Procedimiento pro;
        Cita c;

        if (listaPacienteMultiple.size() > 1 && listaProcedimientos.size() > 1) {
            util.mostrarMensaje("No es posible agendar una cita si ha escogido varios pacientes y varios procedimientos a la vez.");
            return;
        }

        if ((listaPacienteMultiple.size() > 1 || listaProcedimientos.size() > 1) && usarFranjaExtendida) {
            util.mostrarMensaje("No es posible combinar la extension de franja con varios pacientes o varios procedimientos.");
            return;
        }

        if (listaProcedimientos.isEmpty()) {
            util.mostrarMensaje("Seleccione al menos un procedimiento.");
            return;
        }

        try {
            //### RESTABLECER AMBITO
            //remover la franja extendida de la lista original, y restablecer con base en bk
            if (franjaExtendida != null) {
                listaFaCalendario.remove(franjaExtendida);
                eventModel.deleteEvent(eventoExtendido);

                for (FranjaAgenda fa : listaFaCalendarioBk) {
                    listaFaCalendario.add(fa);
                }
                for (ScheduleEvent se : eventModelBk.getEvents()) {
                    eventModel.addEvent(se);
                }
                listaFaCalendario = ordenarFranjas(listaFaCalendario);
                listaFaCalendarioBk.clear();
                eventModelBk.clear();
                franjaExtendida = null;
                eventoExtendido = null;

                //restablecer id's de listaFaCalendario
                for (FranjaAgenda fa : listaFaCalendario) {
                    for (ScheduleEvent ev : eventModel.getEvents()) {
                        String[] datosFranja = ev.getDescription().split(";");
                        if (formatoHora.format(fa.getFechaHora()).equalsIgnoreCase(datosFranja[0]) && fa.getEspecialidad().getCodigo().equalsIgnoreCase(datosFranja[1])) {
                            fa.setId(ev.getId());
                        }
                    }
                }
            }

            //#################### UN PROCEDIMIENTO UN PACIENTE CON FRANJA EXTENDIDA
            if (usarFranjaExtendida) {

                //reset seleccion
                for (FranjaAgenda fa : listaFaCalendario) {
                    fa.setSeleccionada(Boolean.FALSE);
                }

                //Resetear preasignadas
                for (FranjaAgenda fa : listaFaCalendario) {
                    if (fa.getPreAsignada()) {
                        titulo = fa.getEspecialidad().getNombre()
                                + (fa.getProcedimiento().getCodigo() != null ? (!fa.getProcedimiento().getNombre().equalsIgnoreCase("") ? " - " + fa.getProcedimiento().getNombre() : "") : "")
                                + " - " + fa.getDuracion() + " Min"
                                + (fa.getPaciente().getNombreCompleto() != null ? (!fa.getPaciente().getNombreCompleto().equalsIgnoreCase("") ? " - " + fa.getPaciente().getNombreCompleto() : "") : "")
                                + (fa.getCodCita() != null ? (!fa.getCodCita().equalsIgnoreCase("") ? " - " + fa.getCodCita() : "") : "");
                        fa.setPreAsignada(Boolean.FALSE);

                        for (ScheduleEvent se : eventModel.getEvents()) {//buscar el evento
                            if (se.getId().equalsIgnoreCase(fa.getId())) {
                                //actualizar el evento con los cambios realizados
                                cal.setTime(fa.getFechaHora());
                                cal.add(Calendar.MINUTE, Integer.parseInt(fa.getDuracion()));
                                evento = new DefaultScheduleEvent(titulo, fa.getFechaHora(), cal.getTime());
                                evento.setDescription(formatoHora.format(fa.getFechaHora()) + ";" + fa.getEspecialidad().getCodigo() + ";" + fa.getCodCita());
                                evento.setId(se.getId());
                                eventModel.updateEvent(evento);
                            }
                        }
                    }
                }
                //normalizar paciente(s)

                //REVISAR DADA PROC 1 PAC 1
                listaCitaMultiple.clear();
                if (listaPacienteMultiple.isEmpty()) {
                    if (!cita.getPaciente().getIdentificacion().equalsIgnoreCase("") && !cita.getPaciente().getNombre().equalsIgnoreCase("")) {
                        c = new Cita();
                        c.setPaciente(cita.getPaciente());
                        listaCitaMultiple.add(c);
                    }
                } else {
                    for (Paciente p : listaPacienteMultiple) {
                        c = new Cita();
                        c.setPaciente(p);
                        listaCitaMultiple.add(c);
                    }
                }

                //evento seleccionado
                franjaEvento = (ScheduleEvent) selectEvent.getObject();

                //### ANALISIS ###
                //recorrer todas las franjas, buscando la seleccionada, al encontrarla preguntar si esta ocupada,
                //sino, buscar de esa en adelante si hay franjas contiguas y si las contiguas estan libres.
                for (i = 0; i < listaFaCalendario.size() /*poner limite*/; i++) {
                    if (franjaEvento.getId().equalsIgnoreCase(listaFaCalendario.get(i).getId())) {//franja seleccionada
                        if (listaFaCalendario.get(i).getCodCita() != null) {
                            util.mostrarMensaje("La franja seleccionada ya fue asignada.");
                            valido = Boolean.FALSE;
                            break;
                        } else {
                            //analisis de franjas
                            //cal.getTime()
                            cantValidos = 1;
                            listaFaCalendario.get(i).setSeleccionada(Boolean.TRUE);
                            duracionAcumulada = Integer.parseInt(listaFaCalendario.get(i).getDuracion());
                            //for (j = i + 1; j < listaFaCalendario.size() && cantValidos < listaProcedimientos.size(); j++) {
                            for (j = i + 1; j < listaFaCalendario.size() && duracionAcumulada < Integer.parseInt(cita.getDuracionExtendida()); j++) {
                                cal.setTime(listaFaCalendario.get(j - 1).getFechaHora());
                                cal.add(Calendar.MINUTE, Integer.parseInt(listaFaCalendario.get(j - 1).getDuracion()));
                                if (!formatoHora.format(listaFaCalendario.get(j).getFechaHora()).equalsIgnoreCase(formatoHora.format(cal.getTime()))//franja contigua
                                        || listaFaCalendario.get(j).getCodCita() != null //franja libre
                                        || duracionAcumulada + Integer.parseInt(listaFaCalendario.get(j).getDuracion()) > Integer.parseInt(cita.getDuracionExtendida())) {
                                    break;
                                } else {
                                    cantValidos++;
                                    listaFaCalendario.get(j).setSeleccionada(Boolean.TRUE);
                                    duracionAcumulada += Integer.parseInt(listaFaCalendario.get(j).getDuracion());
                                }

                            }
                        }
                    }
                }

                if (valido) {
                    //if (cantValidos == listaProcedimientos.size()) {
                    if (duracionAcumulada == Integer.parseInt(cita.getDuracionExtendida())) {
                        cita.setListaProcedimientos(new ArrayList<Procedimiento>());

                        //hago copia de las franjas y eventos seleccionados
                        for (FranjaAgenda fa : listaFaCalendario) {
                            if (fa.getSeleccionada()) {
                                listaFaCalendarioBk.add(fa);
                                for (ScheduleEvent se : eventModel.getEvents()) {
                                    if (se.getId().equals(fa.getId())) {
                                        eventModelBk.addEvent(se);
                                    }
                                }
                            }
                        }

                        //retiro seleccionados tanto de la lista como del modelo con base en bks
                        for (FranjaAgenda fa : listaFaCalendarioBk) {
                            listaFaCalendario.remove(fa);
                        }
                        for (ScheduleEvent se : eventModelBk.getEvents()) {
                            eventModel.deleteEvent(se);
                        }

                        //### FABRICO la franja a insertar
                        FranjaAgenda fAgenda = (FranjaAgenda) listaFaCalendarioBk.get(0).clone();//se supone que la nueva franja inicia en el primer elemento de las retiradas

                        pro = null;
                        for (Procedimiento p : listaProcedimientosBase) {
                            if (p.getCodigo().equalsIgnoreCase(listaProcedimientos.get(0))) {//se supone que escogieron solo un procedimiento

                                pro = p;
                                pro.setFecha(fAgenda.getFechaHora());
                                pro.setHora(fAgenda.getFechaHora());
                            }
                        }
                        listaCitaMultiple.get(0).setFecha(fAgenda.getFechaHora());
                        listaCitaMultiple.get(0).setHora(fAgenda.getFechaHora());

                        fAgenda.setDuracion(duracionAcumulada.toString());
                        titulo = fAgenda.getEspecialidad().getNombre()
                                + " - " + pro.getNombre()
                                + " - " + fAgenda.getDuracion() + " Min"
                                + " - " + listaCitaMultiple.get(0).getPaciente().getNombreCompleto();
                        fAgenda.setPreAsignada(Boolean.TRUE);

                        listaCitaMultiple.get(0).getListaProcedimientos().add(pro);
                        listaCitaMultiple.get(0).setDuracionExtendida(duracionAcumulada.toString());

                        cal.setTime(fAgenda.getFechaHora());
                        cal.add(Calendar.MINUTE, duracionAcumulada);
                        evento = new DefaultScheduleEvent(titulo, fAgenda.getFechaHora(), cal.getTime());
                        evento.setDescription(formatoHora.format(fAgenda.getFechaHora()) + ";" + fAgenda.getEspecialidad().getCodigo() + ";" + fAgenda.getCodCita());
                        //evento.setId(fAgenda.getId());
                        listaFaCalendario.add(fAgenda);
                        eventModel.addEvent(evento);
                        franjaExtendida = fAgenda;
                        eventoExtendido = evento;
                        listaFaCalendario = ordenarFranjas(listaFaCalendario);

                        //restaurar los preasignados
                        /*
                    for (FranjaAgenda fa : listaFaCalendario) {
                        if (fa.getPreAsignada() || fa.getSeleccionada()) {//realiza alguna accion si estaba preasignada o fue seleccionada
                            for (ScheduleEvent se : eventModel.getEvents()) {//buscar el evento
                                if (se.getId().equalsIgnoreCase(fa.getId())) {
                                    if (fa.getSeleccionada()) {
                                        //buscar nombre procedimiento
                                        pro = null;
                                        for (Procedimiento p : listaProcedimientosBase) {
                                            if (p.getCodigo().equalsIgnoreCase(listaProcedimientos.get(0))) {//se supone que escogieron solo un procedimiento
                                                //nombreProcedimiento = si.getLabel();
                                                pro = p;
                                                pro.setFecha(fa.getFechaHora());
                                                pro.setHora(fa.getFechaHora());
                                            }
                                        }
                                        listaCitaMultiple.get(indiceCita).setFecha(fa.getFechaHora());
                                        listaCitaMultiple.get(indiceCita).setHora(fa.getFechaHora());

                                        titulo = fa.getEspecialidad().getNombre()
                                                + " - " + pro.getNombre()
                                                + " - " + fa.getDuracion() + " Min"
                                                + " - " + listaCitaMultiple.get(indiceCita).getPaciente().getNombreCompleto();
                                        fa.setPreAsignada(Boolean.TRUE);
                                        //Procedimiento pro = new Procedimiento(listaProcedimientos.get(indicePro), nombreProcedimiento, fa.getFechaHora(), fa.getFechaHora(),0);
                                        listaCitaMultiple.get(indiceCita).getListaProcedimientos().add(pro);
                                        //cita.getListaProcedimientos().add(pro);
                                        indiceCita++;
                                    }

                                    else if (fa.getPreAsignada()) {//no fue seleccionada, restablecerla
                                        titulo = fa.getEspecialidad().getNombre()
                                                + (fa.getProcedimiento().getCodigo() != null ? (!fa.getProcedimiento().getNombre().equalsIgnoreCase("") ? " - " + fa.getProcedimiento().getNombre() : "") : "")
                                                + " - " + fa.getDuracion() + " Min"
                                                + (fa.getPaciente().getNombreCompleto() != null ? (!fa.getPaciente().getNombreCompleto().equalsIgnoreCase("") ? " - " + fa.getPaciente().getNombreCompleto() : "") : "")
                                                + (fa.getCodCita() != null ? (!fa.getCodCita().equalsIgnoreCase("") ? " - " + fa.getCodCita() : "") : "");
                                        fa.setPreAsignada(Boolean.FALSE);
                                    }
                                    //actualizar la franja con los cambios realizados
                                    cal.setTime(fa.getFechaHora());
                                    cal.add(Calendar.MINUTE, Integer.parseInt(fa.getDuracion()));
                                    evento = new DefaultScheduleEvent(titulo, fa.getFechaHora(), cal.getTime());
                                    evento.setId(se.getId());
                                    eventModel.updateEvent(evento);

                                }
                            }
                        }
                    }
                         */
                        //actualizar los seleccionados
                        for (SelectItem s : listaProfesionales) {
                            if (s.getValue().toString().equalsIgnoreCase(cita.getProfesional().getCedula())) {
                                cita.getProfesional().setNombre(s.getLabel());
                            }
//                        cita.setFecha(cita.getListaProcedimientos().get(0).getFecha());
//                        cita.setHora(cita.getListaProcedimientos().get(0).getHora());
                            cita.setFecha(listaCitaMultiple.get(0).getFecha());
                            cita.setHora(listaCitaMultiple.get(0).getHora());
                        }
                    } else {
                        util.mostrarMensaje("No hay franjas suficientes o no es posible obtener la duracion deseada a partir de las franjas encontradas.");
                    }
                }
            } //#################### UN PROCEDIMIENTO CON UNO O MAS PACIENTES
            else if (listaProcedimientos.size() == 1) {
                //reset seleccion
                for (FranjaAgenda fa : listaFaCalendario) {
                    fa.setSeleccionada(Boolean.FALSE);
                }
                //normalizar paciente(s)
                listaCitaMultiple.clear();
                if (listaPacienteMultiple.isEmpty()) {
                    if (!cita.getPaciente().getIdentificacion().equalsIgnoreCase("") && !cita.getPaciente().getNombre().equalsIgnoreCase("")) {
                        c = new Cita();
                        c.setPaciente(cita.getPaciente());
                        listaCitaMultiple.add(c);
                    }
                } else {
                    for (Paciente p : listaPacienteMultiple) {
                        c = new Cita();
                        c.setPaciente(p);
                        listaCitaMultiple.add(c);
                    }
                }

                //evento seleccionado
                franjaEvento = (ScheduleEvent) selectEvent.getObject();

                //recorrer todas las franjas, buscando la seleccionada, al encontrarla preguntar si esta ocupada,
                //sino, buscar de esa en adelante si hay franjas contiguas y si las contiguas estan libres.
                for (i = 0; i < listaFaCalendario.size() && cantValidos < listaCitaMultiple.size(); i++) {
                    if (franjaEvento.getId().equalsIgnoreCase(listaFaCalendario.get(i).getId())) {//franja seleccionada
                        if (listaFaCalendario.get(i).getCodCita() != null) {
                            util.mostrarMensaje("La franja seleccionada ya fue asignada.");
                            valido = Boolean.FALSE;
                            break;
                        } else {
                            //analisis de franjas
                            //cal.getTime()
                            cantValidos = 1;
                            listaFaCalendario.get(i).setSeleccionada(Boolean.TRUE);
                            //for (j = i + 1; j < listaFaCalendario.size() && cantValidos < listaProcedimientos.size(); j++) {
                            for (j = i + 1; j < listaFaCalendario.size() && cantValidos < listaCitaMultiple.size(); j++) {
                                cal.setTime(listaFaCalendario.get(j - 1).getFechaHora());
                                cal.add(Calendar.MINUTE, Integer.parseInt(listaFaCalendario.get(j - 1).getDuracion()));
                                if (!formatoHora.format(listaFaCalendario.get(j).getFechaHora()).equalsIgnoreCase(formatoHora.format(cal.getTime()))//franja contigua
                                        || listaFaCalendario.get(j).getCodCita() != null //franja libre
                                        ) {
                                    break;
                                } else {
                                    cantValidos++;
                                    listaFaCalendario.get(j).setSeleccionada(Boolean.TRUE);
                                }

                            }
                        }
                    }
                }
                if (valido) {
                    //if (cantValidos == listaProcedimientos.size()) {
                    if (cantValidos == listaCitaMultiple.size()) {
                        cita.setListaProcedimientos(new ArrayList<Procedimiento>());
                        //restaurar los preasignados
                        for (FranjaAgenda fa : listaFaCalendario) {
                            if (fa.getPreAsignada() || fa.getSeleccionada()) {//realiza alguna accion si estaba preasignada o fue seleccionada
                                for (ScheduleEvent se : eventModel.getEvents()) {//buscar el evento
                                    if (se.getId().equalsIgnoreCase(fa.getId())) {
                                        if (fa.getSeleccionada()) {
                                            //buscar nombre procedimiento
                                            pro = null;
                                            for (Procedimiento p : listaProcedimientosBase) {
                                                if (p.getCodigo().equalsIgnoreCase(listaProcedimientos.get(0))) {//se supone que escogieron solo un procedimiento
                                                    //nombreProcedimiento = si.getLabel();
                                                    pro = p;
                                                    pro.setFecha(fa.getFechaHora());
                                                    pro.setHora(fa.getFechaHora());
                                                }
                                            }
                                            listaCitaMultiple.get(indiceCita).setFecha(fa.getFechaHora());
                                            listaCitaMultiple.get(indiceCita).setHora(fa.getFechaHora());

                                            titulo = fa.getEspecialidad().getNombre()
                                                    + " - " + pro.getNombre()
                                                    + " - " + fa.getDuracion() + " Min"
                                                    + " - " + listaCitaMultiple.get(indiceCita).getPaciente().getNombreCompleto();
                                            fa.setPreAsignada(Boolean.TRUE);
                                            //Procedimiento pro = new Procedimiento(listaProcedimientos.get(indicePro), nombreProcedimiento, fa.getFechaHora(), fa.getFechaHora(),0);
                                            listaCitaMultiple.get(indiceCita).getListaProcedimientos().add(pro);
                                            //cita.getListaProcedimientos().add(pro);
                                            indiceCita++;
                                        } else if (fa.getPreAsignada()) {//no fue seleccionada, restablecerla
                                            titulo = fa.getEspecialidad().getNombre()
                                                    + (fa.getProcedimiento().getCodigo() != null ? (!fa.getProcedimiento().getNombre().equalsIgnoreCase("") ? " - " + fa.getProcedimiento().getNombre() : "") : "")
                                                    + " - " + fa.getDuracion() + " Min"
                                                    + (fa.getPaciente().getNombreCompleto() != null ? (!fa.getPaciente().getNombreCompleto().equalsIgnoreCase("") ? " - " + fa.getPaciente().getNombreCompleto() : "") : "")
                                                    + (fa.getCodCita() != null ? (!fa.getCodCita().equalsIgnoreCase("") ? " - " + fa.getCodCita() : "") : "");
                                            fa.setPreAsignada(Boolean.FALSE);
                                        }
                                        cal.setTime(fa.getFechaHora());
                                        cal.add(Calendar.MINUTE, Integer.parseInt(fa.getDuracion()));
                                        evento = new DefaultScheduleEvent(titulo, fa.getFechaHora(), cal.getTime());
                                        evento.setDescription(formatoHora.format(fa.getFechaHora()) + ";" + fa.getEspecialidad().getCodigo() + ";" + fa.getCodCita());
                                        evento.setId(se.getId());
                                        eventModel.updateEvent(evento);

                                    }
                                }
                            }
                        }
                        //actualizar los seleccionados
                        for (SelectItem s : listaProfesionales) {
                            if (s.getValue().toString().equalsIgnoreCase(cita.getProfesional().getCedula())) {
                                cita.getProfesional().setNombre(s.getLabel());
                            }
//                        cita.setFecha(cita.getListaProcedimientos().get(0).getFecha());
//                        cita.setHora(cita.getListaProcedimientos().get(0).getHora());
                            cita.setFecha(listaCitaMultiple.get(0).getFecha());
                            cita.setHora(listaCitaMultiple.get(0).getHora());
                        }
                    } else {
                        util.mostrarMensaje("No hay franjas suficientes para agendar los procedimientos seleccionados.");
                    }
                }
                //################# VARIOS PROCEDIMIENTOS CON UN UNICO PACIENTE
            } else if (listaProcedimientos.size() > 1) {
                //reset seleccion
                for (FranjaAgenda fa : listaFaCalendario) {
                    fa.setSeleccionada(Boolean.FALSE);
                }
                franjaEvento = (ScheduleEvent) selectEvent.getObject();
                //cita.getListaProcedimientos().size();
                for (i = 0; i < listaFaCalendario.size() && cantValidos < listaProcedimientos.size(); i++) {
                    if (franjaEvento.getId().equalsIgnoreCase(listaFaCalendario.get(i).getId())) {//franja seleccionada
                        if (listaFaCalendario.get(i).getCodCita() != null) {
                            util.mostrarMensaje("La franja seleccionada ya fue asignada.");
                            valido = Boolean.FALSE;
                            break;
                        } else {
                            //analisis de franjas
                            //cal.getTime()
                            cantValidos = 1;
                            listaFaCalendario.get(i).setSeleccionada(Boolean.TRUE);
                            for (j = i + 1; j < listaFaCalendario.size() && cantValidos < listaProcedimientos.size(); j++) {
                                cal.setTime(listaFaCalendario.get(j - 1).getFechaHora());
                                cal.add(Calendar.MINUTE, Integer.parseInt(listaFaCalendario.get(j - 1).getDuracion()));
                                if (!formatoHora.format(listaFaCalendario.get(j).getFechaHora()).equalsIgnoreCase(formatoHora.format(cal.getTime()))//franja contigua
                                        || listaFaCalendario.get(j).getCodCita() != null //franja libre
                                        ) {
                                    break;
                                } else {
                                    cantValidos++;
                                    listaFaCalendario.get(j).setSeleccionada(Boolean.TRUE);
                                }

                            }
                        }
                    }
                }
                if (valido) {
                    if (cantValidos == listaProcedimientos.size()) {
                        cita.setListaProcedimientos(new ArrayList<Procedimiento>());
                        //restaurar los preasignados
                        for (FranjaAgenda fa : listaFaCalendario) {
                            if (fa.getPreAsignada() || fa.getSeleccionada()) {//realiza alguna accion si estaba preasignada o fue seleccionada
                                for (ScheduleEvent se : eventModel.getEvents()) {//buscar el evento
                                    if (se.getId().equalsIgnoreCase(fa.getId())) {
                                        if (fa.getSeleccionada()) {
                                            //buscar nombre procedimiento
                                            pro = null;
                                            for (Procedimiento p : listaProcedimientosBase) {
                                                if (p.getCodigo().equalsIgnoreCase(listaProcedimientos.get(indicePro))) {
                                                    //nombreProcedimiento = si.getLabel();
                                                    pro = p;
                                                    pro.setFecha(fa.getFechaHora());
                                                    pro.setHora(fa.getFechaHora());
                                                }
                                            }
                                            titulo = fa.getEspecialidad().getNombre()
                                                    + " - " + pro.getNombre()
                                                    + " - " + fa.getDuracion() + " Min"
                                                    + " - " + cita.getPaciente().getNombreCompleto();
                                            fa.setPreAsignada(Boolean.TRUE);
                                            //Procedimiento pro = new Procedimiento(listaProcedimientos.get(indicePro), nombreProcedimiento, fa.getFechaHora(), fa.getFechaHora(),0);
                                            indicePro++;
                                            cita.getListaProcedimientos().add(pro);
                                        } else if (fa.getPreAsignada()) {//no fue seleccionada, restablecerla
                                            titulo = fa.getEspecialidad().getNombre()
                                                    + (fa.getProcedimiento().getCodigo() != null ? (!fa.getProcedimiento().getNombre().equalsIgnoreCase("") ? " - " + fa.getProcedimiento().getNombre() : "") : "")
                                                    + " - " + fa.getDuracion() + " Min"
                                                    + (fa.getPaciente().getNombreCompleto() != null ? (!fa.getPaciente().getNombreCompleto().equalsIgnoreCase("") ? " - " + fa.getPaciente().getNombreCompleto() : "") : "")
                                                    + (fa.getCodCita() != null ? (!fa.getCodCita().equalsIgnoreCase("") ? " - " + fa.getCodCita() : "") : "");
                                            fa.setPreAsignada(Boolean.FALSE);
                                        }
                                        cal.setTime(fa.getFechaHora());
                                        cal.add(Calendar.MINUTE, Integer.parseInt(fa.getDuracion()));
                                        evento = new DefaultScheduleEvent(titulo, fa.getFechaHora(), cal.getTime());
                                        evento.setDescription(formatoHora.format(fa.getFechaHora()) + ";" + fa.getEspecialidad().getCodigo() + ";" + fa.getCodCita());
                                        evento.setId(se.getId());
                                        eventModel.updateEvent(evento);

                                    }
                                }
                            }
                        }
                        //actualizar los seleccionados
                        for (SelectItem s : listaProfesionales) {
                            if (s.getValue().toString().equalsIgnoreCase(cita.getProfesional().getCedula())) {
                                cita.getProfesional().setNombre(s.getLabel());
                            }
                            cita.setFecha(cita.getListaProcedimientos().get(0).getFecha());
                            cita.setHora(cita.getListaProcedimientos().get(0).getHora());
                        }
                    } else {
                        util.mostrarMensaje("No hay franjas suficientes para agendar los procedimientos seleccionados.");
                    }
                }

            }
        } catch (CloneNotSupportedException ex) {
            Logger.getLogger(UICita.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public List<String> listarPacientesPatron(String query) throws Exception {
        ArrayList<Paciente> listaPacientesLocal;
        listaPacientesLocal = getGestorPaciente().listarPacientesPatron(query);
        List<String> listaPac = new ArrayList<>();
        for (Paciente p : listaPacientesLocal) {
            listaPac.add(p.getNombreCompleto());
        }
        return listaPac;

    }

    public List<String> listarEspecialidadesPatron(String query) throws Exception {
        ArrayList<Especialidad> listaEspecialidadesLocal;
        listaEspecialidadesLocal = getGestorEspecialidad().listarEspecialidadesPatron(query);
        List<String> listaEsp = new ArrayList<>();
        for (Especialidad e : listaEspecialidadesLocal) {
            listaEsp.add(e.getNombre());
        }
        return listaEsp;
    }

    public void generarExportInforme() {
        try {
            ArrayList<String> listaInformes;// = new ArrayList();
            String cadena = "";

            //Se brinda un formato reconocible para la base de datos en las fechas
            SimpleDateFormat formatoI = new SimpleDateFormat("yyyy-MM-dd");
            String fechaI = formatoI.format(fechaInicial);
            String fechaF = formatoI.format(fechaFinal);

            listaInformes = gestorInformeCitas.consultarInformeCitas(fechaI, fechaF);
            for (String c : listaInformes) {
                if (cadena.equalsIgnoreCase("")) {
                    cadena = c;
                } else {
                    cadena = cadena + "\r\n" + c;
                }
            }

            InputStream stream = new ByteArrayInputStream(cadena.getBytes());

            archivo = new DefaultStreamedContent(stream, "text/plain", "informe.txt");

        } catch (Exception ex) {
            Logger.getLogger(UICita.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void consultarPacientePorNombre(SelectEvent event) throws Exception {
        Paciente paciente = gestorPaciente.consultarPacientePorNombre(event.getObject().toString());
        cita.getPaciente().setIdentificacion(paciente.getIdentificacion());
    }

    public void consultarPacientePorId() throws Exception {
        Paciente pacienteAnt = new Paciente();
        pacienteAnt.setIdentificacion(cita.getPaciente().getIdentificacion());

        Paciente paciente = gestorPaciente.consultarPacientePorId(cita.getPaciente().getIdentificacion());
        if (paciente != null) {
            cita.setPaciente(paciente);
            cita.setEntidad(paciente.getEntidad());
            consultarTerapiasPaciente();
        } else {
            cita.setPaciente(pacienteAnt);
            cita.setEntidad(new Entidad());
            util.mostrarMensaje("Paciente no encontrado.");
        }

        consultarUltimasCitas();

    }

    public void consultarPacientePorNombreCompleto() throws Exception {

        Paciente pacienteAnt = new Paciente();
        pacienteAnt.setNombreCompleto(cita.getPaciente().getNombreCompleto());

        Paciente paciente = gestorPaciente.consultarPacientePorNombreCompleto(cita.getPaciente().getNombreCompleto());
        if (paciente != null) {
            cita.setPaciente(paciente);
            cita.setEntidad(paciente.getEntidad());
            consultarTerapiasPaciente();
        } else {
            cita.setPaciente(pacienteAnt);
            cita.setEntidad(new Entidad());
            util.mostrarMensaje("Paciente no encontrado.");
        }
        consultarUltimasCitas();

    }

    public void consultarTerapiasPaciente() {
        try {
            GestorTerapia gestorTerapia = new GestorTerapia();
            if (listaTerapias != null && listaTerapias.size() > 0) {
                listaTerapias.clear();
            }
            listaTerapias = gestorTerapia.consultarTerapiasPaciente(cita.getPaciente(), Boolean.TRUE, null, null, null, null, null, null, false);
        } catch (Exception ex) {
            Logger.getLogger(UITerapia.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void guardar() {
        try {
            consultarDisponibilidadEspecialidadCantidadFranjas();
        } catch (Exception ex) {
            Logger.getLogger(UICita.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void limpiarCita() {
        mostrarUltimasCitas = Boolean.FALSE;
        usarFranjaExtendida = Boolean.FALSE;
        cita = new Cita();
        this.listaProcedimientos = new ArrayList<>();
        comboListaProcedimientos = new ArrayList<>();
        listaTerapias.clear();
        listaPacienteMultiple.clear();
        listaCitaMultiple.clear();
        listaCitas.clear();
    }

    public void consultarAgenda(SelectEvent event) throws Exception {
        //reseteo agenda
        getEventModel().clear();
        DefaultScheduleEvent evento;
        //franjaEventoAnterior = null;
        listaFaCalendario.clear();
        //temporal mientras se implementa el convertidor
        Especialidad e = gestorEspecialidad.consultarEspecialidadPorNombre(cita.getEspecialidad().getNombre());

        SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat formatoHora = new SimpleDateFormat("HH:mm:ss");
        GestorAgenda gestorAgenda = new GestorAgenda();

        Calendar cal = Calendar.getInstance();
        listaFaCalendario = gestorAgenda.consultarAgenda(cita.getProfesional().getCedula(), e.getCodigo(), fechaCita);
        for (FranjaAgenda fa : listaFaCalendario) {
            cal.setTime(fa.getFechaHora());
            cal.add(Calendar.MINUTE, Integer.parseInt(fa.getDuracion()));
            String titulo = fa.getEspecialidad().getNombre()
                    + (fa.getProcedimiento().getCodigo() != null ? (!fa.getProcedimiento().getNombre().equalsIgnoreCase("") ? " - " + fa.getProcedimiento().getNombre() : "") : "")
                    + " - " + fa.getDuracion() + " Min"
                    + (fa.getPaciente().getNombreCompleto() != null ? (!fa.getPaciente().getNombreCompleto().equalsIgnoreCase("") ? " - " + fa.getPaciente().getNombreCompleto() : "") : "")
                    + (fa.getCodCita() != null ? (!fa.getCodCita().equalsIgnoreCase("") ? " - " + fa.getCodCita() : "") : "");
            evento = new DefaultScheduleEvent(titulo, fa.getFechaHora(), cal.getTime());
            evento.setDescription(formatoHora.format(fa.getFechaHora()) + ";" + fa.getEspecialidad().getCodigo() + ";" + fa.getCodCita());
            //evento.setId(fa.getProfesional().getCedula() + "@" + formatoFecha.format(fa.getFechaHora()) + "@" + formatoHora.format(fa.getFechaHora()));
            if (fa.getReservadoValoracion()) {
                evento.setStyleClass("reservadoValoracion");
            }
            getEventModel().addEvent(evento);

        }
        //actualizar id's de listaFaCalendario
        for (FranjaAgenda fa : listaFaCalendario) {
            for (ScheduleEvent ev : eventModel.getEvents()) {
                String[] datosFranja = ev.getDescription().split(";");
                if (formatoHora.format(fa.getFechaHora()).equalsIgnoreCase(datosFranja[0]) && fa.getEspecialidad().getCodigo().equalsIgnoreCase(datosFranja[1])) {
                    fa.setId(ev.getId());
                }
            }
        }

    }

    public void consultarProfesionalesPorEspecialidad(SelectEvent event) throws Exception {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        UILogin uilogin = (UILogin) facesContext.getExternalContext().getSessionMap().get("loginBean");

        List<Profesional> listaPro;

        //reseteo variables
        listaProfesionales = new ArrayList<>();//lista combo profesionales
        cita.setProfesional(new Profesional());//valor combo profesionales
        //setEventModel(new DefaultScheduleModel());//componente agenda
        eventModel.clear();

        Especialidad e = gestorEspecialidad.consultarEspecialidadPorNombre(cita.getEspecialidad().getNombre());
        listaPro = getGestorProfesional().consultarProfesionalesPorEspecialidad(e.getCodigo());

        //List<String> listaProf = new ArrayList<>();
        for (Profesional p : listaPro) {
            listaProfesionales.add(new SelectItem(p.getCedula(), p.getNombre()));
        }

        comboListaProcedimientos.clear();
        cita.getListaProcedimientos().clear();

        listaProcedimientosBase = gestorProcedimiento.consultarProcedimientosPorEspecialidadPerfil(e.getCodigo(), uilogin.getSesion().getUsuario().getPerfil().getCodigo());
        for (Procedimiento p : listaProcedimientosBase) {
            comboListaProcedimientos.add(new SelectItem(p.getCodigo(), p.getNombre()));
        }

    }

    public void consultarDisponibilidadEspecialidadCantidadFranjas() throws Exception {
        Especialidad e = gestorEspecialidad.consultarEspecialidadPorNombre(cita.getEspecialidad().getNombre());
        Integer cantidad = 1;
        if (listaPacienteMultiple.size() >= listaProcedimientos.size()) {
            cantidad = listaPacienteMultiple.size();
        } else if (listaPacienteMultiple.size() < listaProcedimientos.size()) {
            cantidad = listaProcedimientos.size();
        }
        listaProfesionalesProximaFranja = getGestorProfesional().consultarDisponibilidadEspecialidadCantidadFranjas(e.getCodigo(), cantidad);
    }

    public void refrescarAgenda() {
        getEventModel().clear();
        System.out.println("click");
    }

    public void guardarCita() {
        Boolean invalido = false;
        FacesContext facesContext = FacesContext.getCurrentInstance();
        UILogin uilogin = (UILogin) facesContext.getExternalContext().getSessionMap().get("loginBean");
        try {
            //validaciones
            if (cita.getPaciente().getNombre().equalsIgnoreCase("")) {
                invalido = true;
            }
            if (cita.getEspecialidad().getNombre() == null) {
                invalido = true;
            }
            if (cita.getFecha() == null) {
                invalido = true;
            }
            if (cita.getHora() == null) {
                invalido = true;
            }
            if (cita.getProfesional().getNombre() == null) {
                invalido = true;
            }
            if (cita.getEntidad().getNombre() == null) {
                invalido = true;
            }
//            if (listaCitaMultiple.isEmpty()) {
//                invalido = true;
//            }
            if (!invalido) {
                Especialidad esp = gestorEspecialidad.consultarEspecialidadPorNombre(cita.getEspecialidad().getNombre());
                Entidad ent = gestorEntidad.consultarEntidadPorNombre(cita.getEntidad().getNombre());
                cita.getEspecialidad().setCodigo(esp.getCodigo());
                cita.getEntidad().setCodigo(ent.getCodigo());
                cita.setUsuario(uilogin.getSesion().getUsuario());
                cita.getEstado().setCodigo("1");
                //completar la informacion del procedimiento
                for (Procedimiento p : listaProcedimientosBase) {
                    if (p.getCodigo().equalsIgnoreCase(cita.getProcedimiento().getCodigo())) {
                        cita.setProcedimiento(p);
                    }
                }
                Integer resultado;
                if (usarFranjaExtendida && franjaExtendida == null) {
                    resultado = -4;
                } else {
                    resultado = gestorCita.guardarCita(cita, listaCitaMultiple, usarFranjaExtendida, listaFaCalendarioBk != null ? listaFaCalendarioBk.size() : 0);
                }

                if (resultado > 0) {
                    util.mostrarMensaje("La cita Nro." + resultado + " se guardo exitosamente.");
                    limpiarCita();
                } else if (resultado == -1) {
                    util.mostrarMensaje("Uno o mas pacientes seleccionados no tienen terapia activa.");
                } else if (resultado == -2) {
                    util.mostrarMensaje("Se presentó una duplicidad de horario, no es posible guardar.");
                    limpiarCita();
                } else if (resultado == -3) {
                    util.mostrarMensaje("Las franja seleccionada no esta disponible, no es posible guardar.");
                    limpiarCita();
                } else if (resultado == -4) {
                    util.mostrarMensaje("Marcó extender franja pero no se ha escogido una cita extendida.");
                } else if (resultado == -5) {
                    util.mostrarMensaje("Uno o mas pacientes seleccionados tienen citas de terapia pendientes por evolucionar.");
                } else {
                    util.mostrarMensaje("Se presento un error al guardar.");
                }
            } else {
                util.mostrarMensaje("Hay campos requeridos sin diligenciar.");
            }

        } catch (SQLException ex) {
            util.mostrarMensaje(ex.getMessage());
        } catch (Exception ex) {
            util.mostrarMensaje(ex.getMessage());
        }

    }

    public void consultarCitas() {
        try {
            listaCitas.clear();
            listaCitas = gestorCita.consultarCitas(cita.getPaciente(), fechaInicial, 0);
        } catch (Exception ex) {
            util.mostrarMensaje(ex.getMessage());
        }
    }

    public void consultarUltimasCitas() {
        try {
            listaCitas.clear();
            mostrarUltimasCitas = Boolean.FALSE;
            listaCitas = gestorCita.consultarCitas(cita.getPaciente(), null, 5);
            if (listaCitas.size() > 0) {
                mostrarUltimasCitas = Boolean.TRUE;
            }
        } catch (Exception ex) {
            util.mostrarMensaje(ex.getMessage());
        }
    }

    public void guardarEstadoCita() {
        try {

            Integer resultado = gestorCita.guardarCita(citaSeleccionada, null, false, 0);
            if (resultado > 0) {
                util.mostrarMensaje("La cita Nro." + citaSeleccionada.getCodigo() + " se actualizó correctamente.");
            } else {
                util.mostrarMensaje("Se presento un error al guardar.");
            }
            consultarCitas();
        } catch (Exception ex) {
            util.mostrarMensaje(ex.getMessage());
        }

    }

    private void cargarListaEstadosCita() {
        try {
            setListaEstadosCita(gestorUtilidades.listarCombo("ESTADOS_CITA", "VALUE"));
        } catch (Exception ex) {
            Logger.getLogger(UICita.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void cargarListaObservacionesCita() {
        try {
            setListaObservacionesCita(gestorUtilidades.listarCombo("OBSERVACIONES_CITA", "VALUE"));
        } catch (Exception ex) {
            Logger.getLogger(UICita.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Cita deshabilitarCamposDialogoIni(Cita cs) {
        if (cs.getEstado().getCodigo().equalsIgnoreCase("1")) {
            razonHab = Boolean.FALSE;
            cs.setMotivo("");
            responsableHab = Boolean.FALSE;
            cs.setResponsable("");
            medioHab = Boolean.FALSE;
            cs.setMedio("");
            autorizacionHab = Boolean.FALSE;
            cs.setNumeroAutorizacion("");
        } else if (cs.getEstado().getCodigo().equalsIgnoreCase("2")) {
            razonHab = Boolean.FALSE;
            cs.setMotivo("");
            responsableHab = Boolean.FALSE;
            cs.setResponsable("");
            medioHab = Boolean.FALSE;
            cs.setMedio("");
            autorizacionHab = Boolean.TRUE; //citaSeleccionada.setNumeroAutorizacion("");
        } else if (cs.getEstado().getCodigo().equalsIgnoreCase("3")) {
            razonHab = Boolean.TRUE; //citaSeleccionada.setMotivo("");
            responsableHab = Boolean.TRUE; //citaSeleccionada.setResponsable("");
            medioHab = Boolean.TRUE; //citaSeleccionada.setMedio("");
            autorizacionHab = Boolean.FALSE;
            cs.setNumeroAutorizacion("");
        } else if (cs.getEstado().getCodigo().equalsIgnoreCase("4")) {
            razonHab = Boolean.TRUE; //citaSeleccionada.setMotivo("");
            responsableHab = Boolean.TRUE; //citaSeleccionada.setResponsable("");
            medioHab = Boolean.TRUE; //citaSeleccionada.setMedio("");
            autorizacionHab = Boolean.FALSE;
            cs.setNumeroAutorizacion("");
        } else if (cs.getEstado().getCodigo().equalsIgnoreCase("5")) {
            razonHab = Boolean.FALSE;
            cs.setMotivo("");
            responsableHab = Boolean.FALSE;
            cs.setResponsable("");
            medioHab = Boolean.FALSE;
            cs.setMedio("");
            autorizacionHab = Boolean.FALSE;
            cs.setNumeroAutorizacion("");
        }
        return cs;
    }

    public void deshabilitarCamposDialogo() {
        if (citaSeleccionada.getEstado().getCodigo().equalsIgnoreCase("1")) {
            razonHab = Boolean.FALSE;
            citaSeleccionada.setMotivo("");
            responsableHab = Boolean.FALSE;
            citaSeleccionada.setResponsable("");
            medioHab = Boolean.FALSE;
            citaSeleccionada.setMedio("");
            autorizacionHab = Boolean.FALSE;
            citaSeleccionada.setNumeroAutorizacion("");
        } else if (citaSeleccionada.getEstado().getCodigo().equalsIgnoreCase("2")) {
            razonHab = Boolean.FALSE;
            citaSeleccionada.setMotivo("");
            responsableHab = Boolean.FALSE;
            citaSeleccionada.setResponsable("");
            medioHab = Boolean.FALSE;
            citaSeleccionada.setMedio("");
            autorizacionHab = Boolean.TRUE; //citaSeleccionada.setNumeroAutorizacion("");
        } else if (citaSeleccionada.getEstado().getCodigo().equalsIgnoreCase("3")) {
            razonHab = Boolean.TRUE; //citaSeleccionada.setMotivo("");
            responsableHab = Boolean.TRUE; //citaSeleccionada.setResponsable("");
            medioHab = Boolean.TRUE; //citaSeleccionada.setMedio("");
            autorizacionHab = Boolean.FALSE;
            citaSeleccionada.setNumeroAutorizacion("");
        } else if (citaSeleccionada.getEstado().getCodigo().equalsIgnoreCase("4")) {
            razonHab = Boolean.TRUE; //citaSeleccionada.setMotivo("");
            responsableHab = Boolean.TRUE; //citaSeleccionada.setResponsable("");
            medioHab = Boolean.TRUE; //citaSeleccionada.setMedio("");
            autorizacionHab = Boolean.FALSE;
            citaSeleccionada.setNumeroAutorizacion("");
        } else if (citaSeleccionada.getEstado().getCodigo().equalsIgnoreCase("5")) {
            razonHab = Boolean.FALSE;
            citaSeleccionada.setMotivo("");
            responsableHab = Boolean.FALSE;
            citaSeleccionada.setResponsable("");
            medioHab = Boolean.FALSE;
            citaSeleccionada.setMedio("");
            autorizacionHab = Boolean.FALSE;
            citaSeleccionada.setNumeroAutorizacion("");
        }
    }

    public void adicionarPacienteAtencionMultiple() {
        Paciente paciente;
        try {
            if (listaPacienteMultiple.size() < 4) {
                paciente = gestorPaciente.consultarPacientePorId(cita.getPaciente().getIdentificacion());
                if (paciente != null) {
                    listaPacienteMultiple.add(paciente);
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(UICita.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public List<FranjaAgenda> ordenarFranjas(List<FranjaAgenda> listaFranjasOri) {
        List<FranjaAgenda> listaFranjasN = new ArrayList<>();
        FranjaAgenda fA, fAMayor;
        FranjaAgenda fAA = new FranjaAgenda();
        fAA.setFechaHora(new Date(1));//setea una fecha X
        Integer i, j, cantidad = listaFranjasOri.size();

        //Encontrar menor
        fAA = listaFranjasOri.get(0);
        for (j = 1; j < cantidad; j++) {
            if (listaFranjasOri.get(j).getFechaHora().before(fAA.getFechaHora())) {
                fAA = listaFranjasOri.get(j);
            }
        }

        //Encontrar mayor
        fAMayor = listaFranjasOri.get(0);
        for (j = 1; j < cantidad; j++) {
            if (listaFranjasOri.get(j).getFechaHora().after(fAMayor.getFechaHora())) {
                fAMayor = listaFranjasOri.get(j);
            }
        }

        //faa contiene el menor, se agrega el primer elemento
        listaFranjasN.add(fAA);

        for (i = 0; i < cantidad - 2; i++) {
            fA = fAMayor;
            for (j = 0; j < cantidad; j++) {
                if (listaFranjasOri.get(j).getFechaHora().after(fAA.getFechaHora()) && listaFranjasOri.get(j).getFechaHora().before(fA.getFechaHora())) {
                    fA = listaFranjasOri.get(j);
                }
            }
            listaFranjasN.add(fA);
            fAA = fA;
        }

        listaFranjasN.add(fAMayor);
        return listaFranjasN;

    }

    public void monitoreaUsoFranjaExtendida() {
        if (usarFranjaExtendida) {
            util.mostrarMensaje("verdadero.");
        } else {
            util.mostrarMensaje("falso.");
        }
    }

    public String getUsuario() {
        return usuario;
    }

    /**
     * @param usuario the usuario to set
     */
    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    /**
     * @return the cita
     */
    public Cita getCita() {
        return cita;
    }

    /**
     * @param cita the cita to set
     */
    public void setCita(Cita cita) {
        this.cita = cita;
    }

    /**
     * @return the gestorPaciente
     */
    public GestorPaciente getGestorPaciente() {
        return gestorPaciente;
    }

    /**
     * @param gestorPaciente the gestorPaciente to set
     */
    public void setGestorPaciente(GestorPaciente gestorPaciente) {
        this.gestorPaciente = gestorPaciente;
    }

    /**
     * @return the gestorEspecialidad
     */
    public GestorEspecialidad getGestorEspecialidad() {
        return gestorEspecialidad;
    }

    /**
     * @param gestorEspecialidad the gestorEspecialidad to set
     */
    public void setGestorEspecialidad(GestorEspecialidad gestorEspecialidad) {
        this.gestorEspecialidad = gestorEspecialidad;
    }

    /**
     * @return the gestorEntidad
     */
    public GestorEntidad getGestorEntidad() {
        return gestorEntidad;
    }

    /**
     * @param gestorEntidad the gestorEntidad to set
     */
    public void setGestorEntidad(GestorEntidad gestorEntidad) {
        this.gestorEntidad = gestorEntidad;
    }

    /**
     * @return the listaProfesionales
     */
    public List<SelectItem> getListaProfesionales() {
        return listaProfesionales;
    }

    /**
     * @param listaProfesionales the listaProfesionales to set
     */
    public void setListaProfesionales(List<SelectItem> listaProfesionales) {
        this.listaProfesionales = listaProfesionales;
    }

    /**
     * @return the eventModel
     */
    public ScheduleModel getEventModel() {
        return eventModel;
    }

    /**
     * @param eventModel the eventModel to set
     */
    public void setEventModel(ScheduleModel eventModel) {
        this.eventModel = eventModel;
    }

    /**
     * @return the gestorProfesional
     */
    public GestorProfesional getGestorProfesional() {
        return gestorProfesional;
    }

    /**
     * @param gestorProfesional the gestorProfesional to set
     */
    public void setGestorProfesional(GestorProfesional gestorProfesional) {
        this.gestorProfesional = gestorProfesional;
    }

    /**
     * @return the franjaEvento
     */
    public ScheduleEvent getFranjaEvento() {
        return franjaEvento;
    }

    /**
     * @param franjaEvento the franjaEvento to set
     */
    public void setFranjaEvento(ScheduleEvent franjaEvento) {
        this.franjaEvento = franjaEvento;
    }

    /**
     * @return the gestorCita
     */
    public GestorCita getGestorCita() {
        return gestorCita;
    }

    /**
     * @param gestorCita the gestorCita to set
     */
    public void setGestorCita(GestorCita gestorCita) {
        this.gestorCita = gestorCita;
    }

    /**
     * @return the fechaInicial
     */
    public Date getFechaInicial() {
        return fechaInicial;
    }

    /**
     * @param fechaInicial the fechaInicial to set
     */
    public void setFechaInicial(Date fechaInicial) {
        this.fechaInicial = fechaInicial;
    }

    /**
     * @return the fechaFinal
     */
    public Date getFechaFinal() {
        return fechaFinal;
    }

    /**
     * @param fechaFinal the fechaFinal to set
     */
    public void setFechaFinal(Date fechaFinal) {
        this.fechaFinal = fechaFinal;
    }

    /**
     * @return the listaCitas
     */
    public List<Cita> getListaCitas() {
        return listaCitas;
    }

    /**
     * @param listaCitas the listaCitas to set
     */
    public void setListaCitas(List<Cita> listaCitas) {
        this.listaCitas = listaCitas;
    }

    /**
     * @return the fechaCita
     */
    public Date getFechaCita() {
        return fechaCita;
    }

    /**
     * @param fechaCita the fechaCita to set
     */
    public void setFechaCita(Date fechaCita) {
        this.fechaCita = fechaCita;
    }

    /**
     * @return the fechaActual
     */
    public Date getFechaActual() {
        return fechaActual;
    }

    /**
     * @param fechaActual the fechaActual to set
     */
    public void setFechaActual(Date fechaActual) {
        this.fechaActual = fechaActual;
    }

    /**
     * @return the comboListaProcedimientos
     */
    public List<SelectItem> getComboListaProcedimientos() {
        return comboListaProcedimientos;
    }

    /**
     * @param comboListaProcedimientos the comboListaProcedimientos to set
     */
    public void setComboListaProcedimientos(List<SelectItem> comboListaProcedimientos) {
        this.comboListaProcedimientos = comboListaProcedimientos;
    }

    /**
     * @return the gestorProcedimiento
     */
    public GestorProcedimiento getGestorProcedimiento() {
        return gestorProcedimiento;
    }

    /**
     * @param gestorProcedimiento the gestorProcedimiento to set
     */
    public void setGestorProcedimiento(GestorProcedimiento gestorProcedimiento) {
        this.gestorProcedimiento = gestorProcedimiento;
    }

    /**
     * @return the listaProcedimientos
     */
    public List<String> getListaProcedimientos() {
        return listaProcedimientos;
    }

    /**
     * @param listaProcedimientos the listaProcedimientos to set
     */
    public void setListaProcedimientos(List<String> listaProcedimientos) {
        this.listaProcedimientos = listaProcedimientos;
    }

    /**
     * @return the listaProfesionalesProximaFranja
     */
    public List<Profesional> getListaProfesionalesProximaFranja() {
        return listaProfesionalesProximaFranja;
    }

    /**
     * @param listaProfesionalesProximaFranja the
     * listaProfesionalesProximaFranja to set
     */
    public void setListaProfesionalesProximaFranja(List<Profesional> listaProfesionalesProximaFranja) {
        this.listaProfesionalesProximaFranja = listaProfesionalesProximaFranja;
    }

    /**
     * @return the listaEstadosCita
     */
    public List<SelectItem> getListaEstadosCita() {
        return listaEstadosCita;
    }

    /**
     * @param listaEstadosCita the listaEstadosCita to set
     */
    public void setListaEstadosCita(List<SelectItem> listaEstadosCita) {
        this.listaEstadosCita = listaEstadosCita;
    }

    /**
     * @return the gestorUtilidades
     */
    public GestorUtilidades getGestorUtilidades() {
        return gestorUtilidades;
    }

    /**
     * @param gestorUtilidades the gestorUtilidades to set
     */
    public void setGestorUtilidades(GestorUtilidades gestorUtilidades) {
        this.gestorUtilidades = gestorUtilidades;
    }

    /**
     * @return the citaSeleccionada
     */
    public Cita getCitaSeleccionada() {
        return citaSeleccionada;
    }

    /**
     * @param citaSeleccionada the citaSeleccionada to set
     */
    public void setCitaSeleccionada(Cita citaSeleccionada) {
        this.citaSeleccionada = deshabilitarCamposDialogoIni(citaSeleccionada);
    }

    /**
     * @return the razonHab
     */
    public Boolean getRazonHab() {
        return razonHab;
    }

    /**
     * @param razonHab the razonHab to set
     */
    public void setRazonHab(Boolean razonHab) {
        this.razonHab = razonHab;
    }

    /**
     * @return the responsableHab
     */
    public Boolean getResponsableHab() {
        return responsableHab;
    }

    /**
     * @param responsableHab the responsableHab to set
     */
    public void setResponsableHab(Boolean responsableHab) {
        this.responsableHab = responsableHab;
    }

    /**
     * @return the medioHab
     */
    public Boolean getMedioHab() {
        return medioHab;
    }

    /**
     * @param medioHab the medioHab to set
     */
    public void setMedioHab(Boolean medioHab) {
        this.medioHab = medioHab;
    }

    /**
     * @return the autorizacionHab
     */
    public Boolean getAutorizacionHab() {
        return autorizacionHab;
    }

    /**
     * @param autorizacionHab the autorizacionHab to set
     */
    public void setAutorizacionHab(Boolean autorizacionHab) {
        this.autorizacionHab = autorizacionHab;
    }

    /**
     * @return the listaObservacionesCita
     */
    public List<SelectItem> getListaObservacionesCita() {
        return listaObservacionesCita;
    }

    /**
     * @param listaObservacionesCita the listaObservacionesCita to set
     */
    public void setListaObservacionesCita(List<SelectItem> listaObservacionesCita) {
        this.listaObservacionesCita = listaObservacionesCita;
    }

    /**
     * @return the listaTerapias
     */
    public List<Terapia> getListaTerapias() {
        return listaTerapias;
    }

    /**
     * @param listaTerapias the listaTerapias to set
     */
    public void setListaTerapias(List<Terapia> listaTerapias) {
        this.listaTerapias = listaTerapias;
    }

    /**
     * @return the listaProcedimientosBase
     */
    public List<Procedimiento> getListaProcedimientosBase() {
        return listaProcedimientosBase;
    }

    /**
     * @param listaProcedimientosBase the listaProcedimientosBase to set
     */
    public void setListaProcedimientosBase(List<Procedimiento> listaProcedimientosBase) {
        this.listaProcedimientosBase = listaProcedimientosBase;
    }

    /**
     * @return the listaPacienteMultiple
     */
    public List<Paciente> getListaPacienteMultiple() {
        return listaPacienteMultiple;
    }

    /**
     * @param listaPacienteMultiple the listaPacienteMultiple to set
     */
    public void setListaPacienteMultiple(List<Paciente> listaPacienteMultiple) {
        this.listaPacienteMultiple = listaPacienteMultiple;
    }

    /**
     * @return the listaCitaMultiple
     */
    public List<Cita> getListaCitaMultiple() {
        return listaCitaMultiple;
    }

    /**
     * @param listaCitaMultiple the listaCitaMultiple to set
     */
    public void setListaCitaMultiple(List<Cita> listaCitaMultiple) {
        this.listaCitaMultiple = listaCitaMultiple;
    }

    /**
     * @return the mostrarUltimasCitas
     */
    public Boolean getMostrarUltimasCitas() {
        return mostrarUltimasCitas;
    }

    /**
     * @param mostrarUltimasCitas the mostrarUltimasCitas to set
     */
    public void setMostrarUltimasCitas(Boolean mostrarUltimasCitas) {
        this.mostrarUltimasCitas = mostrarUltimasCitas;
    }

    public StreamedContent getArchivo() {
        return archivo;
    }

    public void setArchivo2(StreamedContent archivo) {
        this.archivo = archivo;
    }

    public List<FranjaInforme> getListaInformes() {
        return listaInformes;
    }

    public void setListaInformes(List<FranjaInforme> listaInformes) {
        this.listaInformes = listaInformes;
    }

    public GestorInformeCitas getGestorInformeCitas() {
        return gestorInformeCitas;
    }

    public void setGestorInformeCitas(GestorInformeCitas gestorInformeCitas) {
        this.gestorInformeCitas = gestorInformeCitas;
    }

    /**
     * @return the usarFranjaExtendida
     */
    public Boolean getUsarFranjaExtendida() {
        return usarFranjaExtendida;
    }

    /**
     * @param usarFranjaExtendida the usarFranjaExtendida to set
     */
    public void setUsarFranjaExtendida(Boolean usarFranjaExtendida) {
        this.usarFranjaExtendida = usarFranjaExtendida;
    }

    /**
     * @return the listaFaCalendarioBk
     */
    public List<FranjaAgenda> getListaFaCalendarioBk() {
        return listaFaCalendarioBk;
    }

    /**
     * @param listaFaCalendarioBk the listaFaCalendarioBk to set
     */
    public void setListaFaCalendarioBk(List<FranjaAgenda> listaFaCalendarioBk) {
        this.listaFaCalendarioBk = listaFaCalendarioBk;
    }

    /**
     * @return the franjaExtendida
     */
    public FranjaAgenda getFranjaExtendida() {
        return franjaExtendida;
    }

    /**
     * @param franjaExtendida the franjaExtendida to set
     */
    public void setFranjaExtendida(FranjaAgenda franjaExtendida) {
        this.franjaExtendida = franjaExtendida;
    }

    /**
     * @return the eventModelBk
     */
    public ScheduleModel getEventModelBk() {
        return eventModelBk;
    }

    /**
     * @param eventModelBk the eventModelBk to set
     */
    public void setEventModelBk(ScheduleModel eventModelBk) {
        this.eventModelBk = eventModelBk;
    }

    /**
     * @return the eventoExtendido
     */
    public ScheduleEvent getEventoExtendido() {
        return eventoExtendido;
    }

    /**
     * @param eventoExtendido the eventoExtendido to set
     */
    public void setEventoExtendido(ScheduleEvent eventoExtendido) {
        this.eventoExtendido = eventoExtendido;
    }

}
