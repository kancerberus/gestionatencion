/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 <p:ajax event="dateSelect" listener="#{uiagenda.consultarAgenda}" update="pnlCalendarioAgendas,calendarioAgendas" />
 */
package vista;

import controlador.GestorAgenda;
import controlador.GestorEspecialidad;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.faces.model.SelectItem;
import modelo.Profesional;
import modelo.FranjaAgenda;
import controlador.GestorProfesional;
import java.io.Serializable;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Calendar;
import java.text.SimpleDateFormat;

import modelo.Especialidad;
import modelo.SelectorDia;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultScheduleEvent;
import util.Utilidades;

//calendario
import org.primefaces.model.DefaultScheduleModel;
import org.primefaces.model.ScheduleEvent;
import org.primefaces.model.ScheduleModel;

/**
 *
 * @author Andres
 */
public class UIAgenda implements Serializable {

    private Profesional profesional;
    private Date fechaInicial;
    private Date fechaFinal;

    private Especialidad especialidadAm;
    private Date horaInicialAm;
    private Date horaFinalAm;

    private Boolean generaAgendaAm;
    private Boolean generaAgendaPm;
    //private Especialidad especialidadPm;
    private Date horaInicialPm;
    private Date horaFinalPm;

    private String duracionAm;
    //private String duracionPm;

    //selector de dias
    private List<SelectorDia> selectorDias;

    private List<FranjaAgenda> listaFranjasAgenda = new ArrayList<>();

    public Utilidades util = new Utilidades();

    //calendario
    private ScheduleModel eventModel;
    private Especialidad especialidadAgendas;
    private Profesional profesionalAgendas;
    private List<SelectItem> listaProfesionales;
    private Date fecha = new Date();
    List<FranjaAgenda> listaFaCalendario;

    private GestorProfesional gestorProfesional;
    private GestorEspecialidad gestorEspecialidad;

    //modo eliminar
    private Boolean modoEliminar;
    private ScheduleModel eventModelEliminar;

    //exportar
    private List<FranjaAgenda> listaFranjasExportar = new ArrayList<>();

    public UIAgenda() throws Exception {
        listaProfesionales = new ArrayList<>();
        profesional = new Profesional();
        especialidadAm = new Especialidad();
        //especialidadPm = new Especialidad();
        gestorProfesional = new GestorProfesional();
        gestorEspecialidad = new GestorEspecialidad();
        generaAgendaAm = Boolean.TRUE;
        generaAgendaPm = Boolean.TRUE;
        selectorDias = new ArrayList<>();

        //calendario
        eventModel = new DefaultScheduleModel();
        profesionalAgendas = new Profesional();
        especialidadAgendas = new Especialidad();
        listaFaCalendario = new ArrayList<>();

        //modoEliminar
        modoEliminar = false;
        eventModelEliminar = new DefaultScheduleModel();
    }

    public void crearAgenda() throws Exception {
        Especialidad e;
        Boolean valido = Boolean.TRUE;
        try {
            //validacion de campos
            if (especialidadAm.getNombre() == null || profesional.getNombre() == null || fechaInicial == null || fechaFinal == null) {
                valido = Boolean.FALSE;
            }
            if (generaAgendaAm && (horaInicialAm == null || horaFinalAm == null || duracionAm == null)) {
                valido = Boolean.FALSE;
            }
            if (generaAgendaPm && (horaInicialPm == null || horaFinalPm == null || duracionAm == null)) {
                valido = Boolean.FALSE;
            }
            if (!generaAgendaAm && !generaAgendaPm) {
                valido = Boolean.FALSE;
            }
            if (selectorDias.isEmpty()) {
                valido = Boolean.FALSE;
            }

            if (valido) {
                listaFranjasAgenda.clear();
                //validacion pendiente que no se cruce con la agenda que ya tiene registrada
                Profesional p = gestorProfesional.consultarProfesionalPorNombre(profesional.getNombre());

                Calendar fechaHoraInicialDia = Calendar.getInstance();
                Calendar fechaHoraFinalDia = Calendar.getInstance();
                Calendar fechaFinalAbsoluta = Calendar.getInstance();

                SimpleDateFormat formatoHoraFecha = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd");
                SimpleDateFormat formatoHora = new SimpleDateFormat("HH:mm:ss");

                if (generaAgendaAm) {
                    //temporal mientras se implementa el convertidor
                    e = gestorEspecialidad.consultarEspecialidadPorNombre(especialidadAm.getNombre());

                    //while (fechaHoraInicialDia.before(fechaFinalAbsoluta)) {
                    for (SelectorDia dia : selectorDias) {
                        if (dia.isFranja1()) {
                            fechaHoraInicialDia.setTime(formatoHoraFecha.parse(dia.getFecha() + " " + formatoHora.format(horaInicialAm)));
                            fechaHoraFinalDia.setTime(formatoHoraFecha.parse(dia.getFecha() + " " + formatoHora.format(horaFinalAm)));

                            while (fechaHoraInicialDia.before(fechaHoraFinalDia)) {
                                FranjaAgenda fa = new FranjaAgenda();
                                fa.setEspecialidad(e);
                                fa.setProfesional(p);
                                fa.setDuracion(duracionAm);
                                fa.setFechaHora(fechaHoraInicialDia.getTime());
                                listaFranjasAgenda.add(fa);
                                //agrega duracion minutos
                                fechaHoraInicialDia.add(Calendar.MINUTE, Integer.parseInt(duracionAm));
                            }
                        }
                    }
                }

                //franja tarde
                if (generaAgendaPm) {

                    //temporal mientras se implementa el convertidor
                    e = gestorEspecialidad.consultarEspecialidadPorNombre(especialidadAm.getNombre());

                    //while (fechaHoraInicialDia.before(fechaFinalAbsoluta)) {
                    for (SelectorDia dia : selectorDias) {
                        if (dia.isFranja2()) {
                            fechaHoraInicialDia.setTime(formatoHoraFecha.parse(dia.getFecha() + " " + formatoHora.format(horaInicialPm)));
                            fechaHoraFinalDia.setTime(formatoHoraFecha.parse(dia.getFecha() + " " + formatoHora.format(horaFinalPm)));

                            while (fechaHoraInicialDia.before(fechaHoraFinalDia)) {
                                FranjaAgenda fa = new FranjaAgenda();
                                fa.setEspecialidad(e);
                                fa.setProfesional(p);
                                fa.setDuracion(duracionAm);
                                fa.setFechaHora(fechaHoraInicialDia.getTime());
                                listaFranjasAgenda.add(fa);
                                //agrega duracion minutos
                                fechaHoraInicialDia.add(Calendar.MINUTE, Integer.parseInt(duracionAm));
                            }
                        }
                    }
                }
                GestorAgenda gestorAgenda = new GestorAgenda();
                Integer resultado = gestorAgenda.crearAgenda(listaFranjasAgenda);
                if (resultado == 1) {
                    util.mostrarMensaje("Programacion creada exitosamente.");
                    limpiarCreacionAgenda();
                }
            } else {
                util.mostrarMensaje("Imposible guardar, revise que todos los campos necesarios esten diligenciados, el selector y al menos una franja.");
            }

        } catch (ParseException | SQLException ex) {
            //Logger.getLogger(UIAgenda.class.getName()).log(Level.SEVERE, null, ex);
            util.mostrarMensaje(ex.getMessage());
        }

    }

    public void consultarAgenda(SelectEvent event) throws Exception {
        SimpleDateFormat formatoHora = new SimpleDateFormat("HH:mm:ss");
        //reseteo agenda
        getEventModel().clear();
        String estilo;

        //temporal mientras se implementa el convertidor
        Especialidad e = gestorEspecialidad.consultarEspecialidadPorNombre(especialidadAgendas.getNombre());

        GestorAgenda gestorAgenda = new GestorAgenda();
        listaFaCalendario.clear();
        Calendar cal = Calendar.getInstance();
        listaFaCalendario = gestorAgenda.consultarAgenda(profesionalAgendas.getCedula(), e.getCodigo(), fecha);
        for (FranjaAgenda fa : listaFaCalendario) {
            cal.setTime(fa.getFechaHora());
            cal.add(Calendar.MINUTE, Integer.parseInt(fa.getDuracion()));
            String titulo = fa.getEspecialidad().getNombre() + " - " + fa.getDuracion() + " Min"
                    + (fa.getPaciente().getNombreCompleto() != null ? (!fa.getPaciente().getNombreCompleto().equalsIgnoreCase("") ? " - " + fa.getPaciente().getNombreCompleto() : "") : "")
                    + (fa.getCodCita() != null ? (!fa.getCodCita().equalsIgnoreCase("") ? " - " + fa.getCodCita() : "") : "")
                    + (fa.getObservaciones() != null ? (!fa.getObservaciones().equalsIgnoreCase("") ?  " - " + fa.getObservaciones() :"" ) :"");            
           
            DefaultScheduleEvent evento = new DefaultScheduleEvent(titulo, fa.getFechaHora(), cal.getTime());
            evento.setDescription(formatoHora.format(fa.getFechaHora()) + ";" + fa.getEspecialidad().getCodigo() + ";" + fa.getCodCita() + ";" + fa.getObservaciones());
            evento.setData(fa);
            if(fa.getReservadoValoracion()) {
                evento.setStyleClass("reservadoValoracion");
            }
            getEventModel().addEvent(evento);
        }

        //Setear id's
        for (FranjaAgenda fa : listaFaCalendario) {
            for (ScheduleEvent ev : eventModel.getEvents()) {
                String[] datosFranja = ev.getDescription().split(";");
                if (formatoHora.format(fa.getFechaHora()).equalsIgnoreCase(datosFranja[0]) && fa.getEspecialidad().getCodigo().equalsIgnoreCase(datosFranja[1])) {
                    fa.setId(ev.getId());
                }
            }
        }

        //Cargar schedule eliminar
        eventModelEliminar.clear();
        List<FranjaAgenda> listaFaCalendarioMes;
        listaFaCalendarioMes = gestorAgenda.consultarAgendaMes(profesionalAgendas.getCedula(), e.getCodigo(), fecha);
        for (FranjaAgenda fa : listaFaCalendarioMes) {
            cal.setTime(fa.getFechaHora());
            cal.add(Calendar.HOUR, 23);
            estilo = fa.getCodCita() != null ? "eventoConCita" : "eventoSinCita";
            DefaultScheduleEvent eventoDSE = new DefaultScheduleEvent("", fa.getFechaHora(), cal.getTime(), true);
            eventoDSE.setStyleClass(estilo);
            //ScheduleEvent evento = eventoDSE;
            eventModelEliminar.addEvent(eventoDSE);
        }

    }

    public void onEventSelect(SelectEvent selectEvent) {
        DefaultScheduleEvent franjaEvento = (DefaultScheduleEvent) selectEvent.getObject();
        if (franjaEvento.getStyleClass().equalsIgnoreCase("eventoConCita")) {
            util.mostrarMensaje("La agenda del dia ya tiene citas programadas.");
        } else {
            if (franjaEvento.getTitle().equalsIgnoreCase("Seleccionado")) {
                franjaEvento.setTitle("");
            } else {
                franjaEvento.setTitle("Seleccionado");
            }
            eventModelEliminar.updateEvent(franjaEvento);
        }

    }

    public void onEventSelectCalendarioAgendas(SelectEvent selectEvent) {
        String titulo = "";
        DefaultScheduleEvent franjaEvento = (DefaultScheduleEvent) selectEvent.getObject();
        Calendar cal = Calendar.getInstance();
        DefaultScheduleEvent evento;
        SimpleDateFormat formatoHora = new SimpleDateFormat("HH:mm:ss");

        for (FranjaAgenda fa : listaFaCalendario) {
            if (franjaEvento.getId().equalsIgnoreCase(fa.getId())) {//franja seleccionada
                for (ScheduleEvent se : eventModel.getEvents()) {//buscar el evento
                    if (se.getId().equalsIgnoreCase(fa.getId())) {
                        if (fa.getCodCita() != null) {
                            util.mostrarMensaje("La franja seleccionada posee una cita.");
                        } else if (fa.getSeleccionada()) { //restablecer
                            titulo = fa.getEspecialidad().getNombre() + " - " + fa.getDuracion() + " Min"
                                    + (fa.getPaciente().getNombreCompleto() != null ? (!fa.getPaciente().getNombreCompleto().equalsIgnoreCase("") ? " - " + fa.getPaciente().getNombreCompleto() : "") : "")
                                    + (fa.getCodCita() != null ? (!fa.getCodCita().equalsIgnoreCase("") ? " - " + fa.getCodCita() : "") : "")
                                    + (fa.getObservaciones() != null ? (!fa.getObservaciones().equalsIgnoreCase("") ?  " - " + fa.getObservaciones() :"" ) :"") ;
                                        fa.setSeleccionada(Boolean.FALSE);
                        } else {
                            titulo = fa.getEspecialidad().getNombre() + " - " + fa.getDuracion() + " Min"
                                    + (fa.getPaciente().getNombreCompleto() != null ? (!fa.getPaciente().getNombreCompleto().equalsIgnoreCase("") ? " - " + fa.getPaciente().getNombreCompleto() : "") : "")
                                    + (fa.getCodCita() != null ? (!fa.getCodCita().equalsIgnoreCase("") ? " - " + fa.getCodCita() : "") : "")
                                    + (fa.getObservaciones() != null ? (!fa.getObservaciones().equalsIgnoreCase("") ?  " - " + fa.getObservaciones() :"" ) :"") 
                                    + " - Eliminar";
                            fa.setSeleccionada(Boolean.TRUE);
                        }
                        if (fa.getCodCita() == null) {
                            cal.setTime(fa.getFechaHora());
                            cal.add(Calendar.MINUTE, Integer.parseInt(fa.getDuracion()));
                            evento = new DefaultScheduleEvent(titulo, fa.getFechaHora(), cal.getTime());
                            evento.setDescription(formatoHora.format(fa.getFechaHora()) + ";" + fa.getEspecialidad().getCodigo() + ";" + fa.getCodCita() + ";" + fa.getObservaciones());
                            evento.setId(se.getId());
                            evento.setData(fa);
                            eventModel.updateEvent(evento);
                        }
                    }
                }
            }
        }
    }

    public void eliminarAgenda() throws Exception {
        GestorAgenda gestorAgenda = new GestorAgenda();
        Especialidad e = gestorEspecialidad.consultarEspecialidadPorNombre(especialidadAgendas.getNombre());
        if(modoEliminar) {
            gestorAgenda.eliminarAgenda(profesionalAgendas.getCedula(), e.getCodigo(), eventModelEliminar, modoEliminar);
        } else {
            gestorAgenda.eliminarAgenda(profesionalAgendas.getCedula(), e.getCodigo(), eventModel, modoEliminar);
        }
        
        SelectEvent event = null;
        consultarAgenda(event);
    }

    public void consultarAgendaPorProfesional() throws Exception {
        Profesional p = null;
        listaFranjasExportar.clear();
        if (profesional.getNombre() == null && fechaInicial == null && fechaFinal == null) {
            util.mostrarMensaje("Especifique un profesional o un rango de fechas");
            return;
        }
        GestorAgenda gestorAgenda = new GestorAgenda();
        if (profesional.getNombre() != null) {
            p = gestorProfesional.consultarProfesionalPorNombre(profesional.getNombre());
            listaFranjasExportar = gestorAgenda.consultarAgendaPorProfesional(p.getCedula(), fechaInicial, fechaFinal);
        } else {
            listaFranjasExportar = gestorAgenda.consultarAgendaPorProfesional(null, fechaInicial, fechaFinal);
        }

    }

    public List<String> listarProfesionalesPatron(String query) throws Exception {
        //GestorProfesional gestorProfesional = new GestorProfesional();       
        ArrayList<Profesional> listaProfesionalesLocal = new ArrayList<>();
        listaProfesionalesLocal = gestorProfesional.listarProfesionalesPatron(query);
        List<String> listaProf = new ArrayList<>();
        for (Profesional p : listaProfesionalesLocal) {
            listaProf.add(p.getNombre());
        }
        return listaProf;
        //return gestorProfesional.listarProfesionalesPatron(query);
    }

    public List<String> listarEspecialidadesPatron(String query) throws Exception {
        //gestorEspecialidad = new GestorEspecialidad();
        ArrayList<Especialidad> listaEspecialidadesLocal;
        listaEspecialidadesLocal = gestorEspecialidad.listarEspecialidadesPatron(query);

        List<String> listaEsp = new ArrayList<>();

        for (Especialidad e : listaEspecialidadesLocal) {
            listaEsp.add(e.getNombre());
        }
        return listaEsp;
    }

    public List<String> listarEspecialidadesProfesionalPatron(String query) throws Exception {
        ArrayList<Especialidad> listaEspecialidadesLocal;

        Profesional p = gestorProfesional.consultarProfesionalPorNombre(profesional.getNombre());
        listaEspecialidadesLocal = gestorEspecialidad.listarEspecialidadesProfesionalPatron(query, p.getCedula());

        List<String> listaEsp = new ArrayList<>();

        for (Especialidad e : listaEspecialidadesLocal) {
            listaEsp.add(e.getNombre());
        }
        return listaEsp;
    }

    public void consultarProfesionalesPorEspecialidad(SelectEvent event) throws Exception {
        List<Profesional> listaProfesionalesLocal;
        //reseteo variables
        listaProfesionales = new ArrayList<>();//lista combo profesionales
        profesionalAgendas = new Profesional();//valor combo profesionales
        //setEventModel(new DefaultScheduleModel());//componente agenda
        eventModel.clear();

        Especialidad e = gestorEspecialidad.consultarEspecialidadPorNombre(especialidadAgendas.getNombre());
        listaProfesionalesLocal = gestorProfesional.consultarProfesionalesPorEspecialidad(e.getCodigo());

        //List<String> listaProf = new ArrayList<>();
        for (Profesional p : listaProfesionalesLocal) {
            listaProfesionales.add(new SelectItem(p.getCedula(), p.getNombre()));
        }
    }

    public List<String> listarProfesionalesEspecialidadPatron(String query) throws Exception {
        List<Profesional> listaProfesionalesLocal;
        //reseteo variables
        //listaProfesionales = new ArrayList<>();//lista combo profesionales
        //profesionalAgendas = new Profesional();//valor combo profesionales
        //setEventModel(new DefaultScheduleModel());//componente agenda
        //eventModel.clear();

        Especialidad e = gestorEspecialidad.consultarEspecialidadPorNombre(especialidadAm.getNombre());
        listaProfesionalesLocal = gestorProfesional.listarProfesionalesEspecialidadPatron(query, e.getCodigo());

        List<String> listaProf = new ArrayList<>();
        for (Profesional p : listaProfesionalesLocal) {
            listaProf.add(p.getNombre());
        }
        return listaProf;
    }

    public void limpiarProfesionales(SelectEvent event) {
        profesional = new Profesional();
        //especialidadPm = new Especialidad();
    }

    public void refrescarAgenda() {
        eventModel.clear();
        System.out.println("click");
    }

    public void generarSelectorDias() {

        Calendar siguienteDia = Calendar.getInstance();
        Calendar fechaFinalCal = Calendar.getInstance();

        selectorDias.clear();
        SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd");

        fechaFinalCal.setTime(fechaFinal);

        selectorDias.add(new SelectorDia(formatoFecha.format(fechaInicial), generaAgendaAm, generaAgendaPm));
        siguienteDia.setTime(fechaInicial);

        while (siguienteDia.before(fechaFinalCal)) {
            siguienteDia.add(Calendar.DATE, 1);

            selectorDias.add(new SelectorDia(formatoFecha.format(siguienteDia.getTime()), generaAgendaAm, generaAgendaPm));
        }

    }

    public void limpiarCreacionAgenda() {
        profesional = new Profesional();
        fechaInicial = null;
        fechaFinal = null;
        horaInicialAm = null;
        horaFinalAm = null;
        duracionAm = "";
        especialidadAm = new Especialidad();
        generaAgendaPm = true;
        horaInicialPm = null;
        horaFinalPm = null;
        //especialidadPm = new Especialidad();
        //duracionPm = "";
        selectorDias.clear();
    }

    public void actualizarFranjasSelectorAm() {
        for (SelectorDia si : selectorDias) {
            si.setFranja1(generaAgendaAm);
        }
    }

    public void actualizarFranjasSelectorPm() {
        for (SelectorDia si : selectorDias) {
            si.setFranja2(generaAgendaPm);
        }
    }

    /**
     * @return the profesional
     */
    public Profesional getProfesional() {
        return profesional;
    }

    /**
     * @param profesional the profesional to set
     */
    public void setProfesional(Profesional profesional) {
        this.profesional = profesional;
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
     * @return the horaInicialAm
     */
    public Date getHoraInicialAm() {
        return horaInicialAm;
    }

    /**
     * @param horaInicialAm the horaInicialAm to set
     */
    public void setHoraInicialAm(Date horaInicialAm) {
        this.horaInicialAm = horaInicialAm;
    }

    /**
     * @return the horaFinalAm
     */
    public Date getHoraFinalAm() {
        return horaFinalAm;
    }

    /**
     * @param horaFinalAm the horaFinalAm to set
     */
    public void setHoraFinalAm(Date horaFinalAm) {
        this.horaFinalAm = horaFinalAm;
    }

    /**
     * @return the horaInicialPm
     */
    public Date getHoraInicialPm() {
        return horaInicialPm;
    }

    /**
     * @param horaInicialPm the horaInicialPm to set
     */
    public void setHoraInicialPm(Date horaInicialPm) {
        this.horaInicialPm = horaInicialPm;
    }

    /**
     * @return the horaFinalPm
     */
    public Date getHoraFinalPm() {
        return horaFinalPm;
    }

    /**
     * @param horaFinalPm the horaFinalPm to set
     */
    public void setHoraFinalPm(Date horaFinalPm) {
        this.horaFinalPm = horaFinalPm;
    }

    /**
     * @return the listaFranjasAgenda
     */
    public List<FranjaAgenda> getListaFranjasAgenda() {
        return listaFranjasAgenda;
    }

    /**
     * @param listaFranjasAgenda the listaFranjasAgenda to set
     */
    public void setListaFranjasAgenda(List<FranjaAgenda> listaFranjasAgenda) {
        this.listaFranjasAgenda = listaFranjasAgenda;
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
     * @return the especialidadAm
     */
    public Especialidad getEspecialidadAm() {
        return especialidadAm;
    }

    /**
     * @param especialidadAm the especialidadAm to set
     */
    public void setEspecialidadAm(Especialidad especialidadAm) {
        this.especialidadAm = especialidadAm;
    }

    /**
     * @return the fecha
     */
    public Date getFecha() {
        return fecha;
    }

    /**
     * @param fecha the fecha to set
     */
    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    /**
     * @return the eventModel
     */
    public ScheduleModel getEventModel() {
        return eventModel;
    }

    /**
     * @return the especialidadAgendas
     */
    public Especialidad getEspecialidadAgendas() {
        return especialidadAgendas;
    }

    /**
     * @param especialidadAgendas the especialidadAgendas to set
     */
    public void setEspecialidadAgendas(Especialidad especialidadAgendas) {
        this.especialidadAgendas = especialidadAgendas;
    }

    /**
     * @return the profesionalAgendas
     */
    public Profesional getProfesionalAgendas() {
        return profesionalAgendas;
    }

    /**
     * @param profesionalAgendas the profesionalAgendas to set
     */
    public void setProfesionalAgendas(Profesional profesionalAgendas) {
        this.profesionalAgendas = profesionalAgendas;
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
     * @param eventModel the eventModel to set
     */
    public void setEventModel(ScheduleModel eventModel) {
        this.eventModel = eventModel;
    }

    /**
     * @return the generaAgendaPm
     */
    public Boolean getGeneraAgendaPm() {
        return generaAgendaPm;
    }

    /**
     * @param generaAgendaPm the generaAgendaPm to set
     */
    public void setGeneraAgendaPm(Boolean generaAgendaPm) {
        this.generaAgendaPm = generaAgendaPm;
    }

    /**
     * @return the listaFranjasExportar
     */
    public List<FranjaAgenda> getListaFranjasExportar() {
        return listaFranjasExportar;
    }

    /**
     * @param listaFranjasExportar the listaFranjasExportar to set
     */
    public void setListaFranjasExportar(List<FranjaAgenda> listaFranjasExportar) {
        this.listaFranjasExportar = listaFranjasExportar;
    }

    /**
     * @return the duracionAm
     */
    public String getDuracionAm() {
        return duracionAm;
    }

    /**
     * @param duracionAm the duracionAm to set
     */
    public void setDuracionAm(String duracionAm) {
        this.duracionAm = duracionAm;
    }

    /**
     * @return the selectorDias
     */
    public List<SelectorDia> getSelectorDias() {
        return selectorDias;
    }

    /**
     * @param selectorDias the selectorDias to set
     */
    public void setSelectorDias(List<SelectorDia> selectorDias) {
        this.selectorDias = selectorDias;
    }

    /**
     * @return the modoEliminar
     */
    public Boolean getModoEliminar() {
        return modoEliminar;
    }

    /**
     * @param modoEliminar the modoEliminar to set
     */
    public void setModoEliminar(Boolean modoEliminar) {
        this.modoEliminar = modoEliminar;
    }

    /**
     * @return the eventModelEliminar
     */
    public ScheduleModel getEventModelEliminar() {
        return eventModelEliminar;
    }

    /**
     * @param eventModelEliminar the eventModelEliminar to set
     */
    public void setEventModelEliminar(ScheduleModel eventModelEliminar) {
        this.eventModelEliminar = eventModelEliminar;
    }

    /**
     * @return the generaAgendaAm
     */
    public Boolean getGeneraAgendaAm() {
        return generaAgendaAm;
    }

    /**
     * @param generaAgendaAm the generaAgendaAm to set
     */
    public void setGeneraAgendaAm(Boolean generaAgendaAm) {
        this.generaAgendaAm = generaAgendaAm;
    }

}
