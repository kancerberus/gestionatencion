/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vista;

import controlador.GestorCita;
import controlador.GestorEntidad;
import controlador.GestorPaciente;
import controlador.GestorProcedimiento;
import controlador.GestorProfesional;
import controlador.GestorTerapia;
import controlador.GestorUtilidades;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.model.SelectItem;
import modelo.Cita;
import modelo.DetalleTerapia;
import modelo.Diagnostico;
import modelo.Entidad;
import modelo.Paciente;
import modelo.Profesional;
import modelo.Terapia;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultScheduleEvent;
import org.primefaces.model.DefaultScheduleModel;
import org.primefaces.model.ScheduleEvent;
import org.primefaces.model.ScheduleModel;
import controlador.GestorDiagnostico;
import util.Utilidades;

/**
 *
 * @author Andres
 */
@ManagedBean(name = "uiterapia")
@SessionScoped
public class UITerapia implements Serializable {

    //private String identificacion;
    //private String nombrePaciente;
    private Paciente paciente;
    private Boolean activa;
    private Boolean autorizadas;
    private Date fechaInicial;
    private Date fechaFinal;
    private String edadInicial;
    private String edadFinal;

    private List<Terapia> listaTerapias;
    private Terapia terapiaSeleccionada;
    private Terapia terapia;
    public Utilidades util = new Utilidades();
    private GestorPaciente gestorPaciente;
    private GestorUtilidades gestorUtilidades;
    private List<SelectItem> cmbTerapias;
    private List<SelectItem> cmbEntidades;
    private GestorTerapia gestorTerapia;
    private GestorDiagnostico gestorDiagnostico;
    private GestorProfesional gestorProfesional;
    private GestorCita gestorCita;
    //comentada para implementar division de franja multiples sesiones terapia
    //private DetalleTerapia detalleTerapia;
    private List<DetalleTerapia> detalleTerapia;
    private Boolean guardado;
    private List<SelectItem> listaCondicion;
    private Boolean permiteDividir;

    private List<SelectItem> cmbProfesionales;
    private Boolean sinProximaCita;
    private String rutaExportar;
    private String rutaRecetario;

    private List<SelectItem> listaCodigosDiagnostico;

    private List<Cita> listaCitasReplicar;
    private ScheduleModel eventModelReplicar;
    
    private String manejoVentana;


    public UITerapia() throws Exception {
        activa = Boolean.TRUE;
        terapiaSeleccionada = new Terapia();
        terapia = new Terapia();
//        diagnostico1 = new Diagnostico();
//        diagnostico2 = new Diagnostico();
        gestorDiagnostico = new GestorDiagnostico();
        gestorPaciente = new GestorPaciente();
        gestorTerapia = new GestorTerapia();
        gestorUtilidades = new GestorUtilidades();
        gestorCita = new GestorCita();
        gestorProfesional = new GestorProfesional();
        cmbProfesionales = new ArrayList();
        listaCitasReplicar = new ArrayList<>();
        guardado = Boolean.FALSE;
        //detalleTerapia = new DetalleTerapia();
        detalleTerapia = new ArrayList<>();
        paciente = new Paciente();
        eventModelReplicar = new DefaultScheduleModel();
        cargarListaCondicion();
        consultarTerapias();
        listarEntidades();
        listarProfesionales();
    }

    public void consultarTerapiasPaciente() {
        try {
            listaTerapias = gestorTerapia.consultarTerapiasPaciente(paciente, activa, fechaInicial, fechaFinal, edadInicial, edadFinal, terapia.getProcedimiento().getCodigo(), autorizadas, sinProximaCita);
        } catch (Exception ex) {
            Logger.getLogger(UITerapia.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void consultarTerapiasPacienteAutorizarEvolucion() {
        try {
            listaTerapias = gestorTerapia.consultarTerapiasPacienteAutorizarEvolucion(paciente, activa);
        } catch (Exception ex) {
            Logger.getLogger(UITerapia.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void listarProfesionales() {
        List<Profesional> listaProfesionales;
        try {
            listaProfesionales = gestorProfesional.listarProfesionales();
            for (Profesional p : listaProfesionales) {
                cmbProfesionales.add(new SelectItem(p.getCedula(), p.getNombre()));
            }
        } catch (Exception ex) {
            Logger.getLogger(UITerapia.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void actualizarTerapia() {
        Integer resultado;
        try {
            //GestorTerapia gestorTerapia = new GestorTerapia();
            resultado = gestorTerapia.actualizarTerapia(terapiaSeleccionada);
            if (resultado > 0) {
                util.mostrarMensaje("La terapia Nro." + terapiaSeleccionada.getCodigo() + " se guardo exitosamente.");
            } else {
                util.mostrarMensaje("Se presento un error al guardar.");
            }
        } catch (Exception ex) {
            Logger.getLogger(UITerapia.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void actualizarPacienteTerapia() {
        Integer resultado;
        try {
            //GestorTerapia gestorTerapia = new GestorTerapia();
            resultado = gestorPaciente.actualizarPaciente1(terapiaSeleccionada.getCita().getPaciente());
            if (resultado > 0) {
                util.mostrarMensaje("El paciente se guardo exitosamente.");
            } else {
                util.mostrarMensaje("Se presento un error al guardar.");
            }
        } catch (Exception ex) {
            Logger.getLogger(UITerapia.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void actualizarTerapiaInformeTerapeutico() {
        Integer resultado;
        try {
            //GestorTerapia gestorTerapia = new GestorTerapia();
            resultado = gestorTerapia.actualizarTerapiaInformeTerapeutico(terapiaSeleccionada);
            if (resultado > 0) {
                util.mostrarMensaje("La terapia Nro." + terapiaSeleccionada.getCodigo() + " se guardo exitosamente.");
            } else {
                util.mostrarMensaje("Se presento un error al guardar.");
            }
        } catch (Exception ex) {
            Logger.getLogger(UITerapia.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void autorizarEvolucion() {
        Integer resultado;
        try {
            //GestorTerapia gestorTerapia = new GestorTerapia();
            resultado = gestorTerapia.autorizarEvolucion(terapiaSeleccionada);
            if (resultado > 0) {
                util.mostrarMensaje("La terapia " + terapiaSeleccionada.getCodigo() + " consecutivo " + terapiaSeleccionada.getDetalleTerapia().get(0).getConsecutivo() + " se autorizó exitosamente.");
                listaTerapias = gestorTerapia.consultarTerapiasPacienteAutorizarEvolucion(paciente, activa);
            } else {
                util.mostrarMensaje("Se presento un error al guardar.");
            }
        } catch (Exception ex) {
            Logger.getLogger(UITerapia.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /*
    public void consultarPacientePorNombreCompletoItemSelect(SelectEvent event) throws Exception {

        Paciente pacienteAnt = new Paciente();
        pacienteAnt.setNombre(event.getObject().toString());

        Paciente paciente = getGestorPaciente().consultarPacientePorNombreCompleto(event.getObject().toString());
        if (paciente != null) {
            getTerapia().setPaciente(paciente);
            //cita.setEntidad(paciente.getEntidad());
        } else {
            getTerapia().setPaciente(pacienteAnt);
            //cita.setEntidad(new Entidad());
            util.mostrarMensaje("Paciente no encontrado.");
        }

    }
     */
    public void consultarPacientePorNombreCompleto() throws Exception {

        Paciente pacienteAnt = new Paciente();
        pacienteAnt.setNombreCompleto(getTerapia().getCita().getPaciente().getNombreCompleto());

        Paciente paciente = getGestorPaciente().consultarPacientePorNombreCompleto(getTerapia().getCita().getPaciente().getNombreCompleto());
        if (paciente != null) {
            getTerapia().getCita().setPaciente(paciente);
            //cita.setEntidad(paciente.getEntidad());
        } else {
            getTerapia().getCita().setPaciente(pacienteAnt);
            //cita.setEntidad(new Entidad());
            util.mostrarMensaje("Paciente no encontrado.");
        }

    }

    public List<String> listarPacientesPatron(String query) throws Exception {
        ArrayList<Paciente> listaPacientesLocal;
        listaPacientesLocal = gestorPaciente.listarPacientesPatron(query);
        List<String> listaPac = new ArrayList<>();
        for (Paciente p : listaPacientesLocal) {
            listaPac.add(p.getNombreCompleto());
        }
        return listaPac;

    }

    private void consultarTerapias() {
        try {
            GestorProcedimiento gestorProcedimiento = new GestorProcedimiento();
            cmbTerapias = gestorProcedimiento.consultarTerapias();
        } catch (Exception ex) {
            Logger.getLogger(UIValoracion.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void guardarTerapia() {
        Integer resultado;
        try {
            resultado = gestorTerapia.guardarTerapia(terapia);
            if (resultado != null) {
                if (resultado == 0) {
                    util.mostrarMensaje("El paciente ya tiene una terapia activa de este tipo.");
                } else if (resultado > 0) {
                    util.mostrarMensaje("La terapia Nro." + resultado + " se guardo exitosamente.");
                    terapia = new Terapia();
                } else {
                    util.mostrarMensaje("Se presento un error.");
                }
            } else {
                util.mostrarMensaje("Se presento un error al guardar.");
            }
        } catch (Exception ex) {
            Logger.getLogger(UITerapia.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void actualizarTerapiaCita() {
        manejoVentana = "";
        Integer resultado;
        Integer codigoTerapia;
        Boolean valido = Boolean.TRUE;
        
        try {         
            
            if (terapia.getControl().equals(false) && terapia.getPrimeraVez().equals(false)) {
                util.mostrarMensaje("Debe marcar si es PRIMERA VEZ o CONTROL");
                valido = Boolean.FALSE;
            }            
            
            if (terapia.getCodigoRIPS().equalsIgnoreCase("")) {
                util.mostrarMensaje("Debe ingresar un RIPS.");
                valido = Boolean.FALSE;
            }
            
            if (terapia.getNombreAcompanante().equalsIgnoreCase("")) {
                util.mostrarMensaje("Debe ingresar un acudiente.");
                valido = Boolean.FALSE;
            }
            
            if (terapia.getDiagnostico().equalsIgnoreCase("")) {
                util.mostrarMensaje("Debe ingresar un diagnóstico.");
                valido = Boolean.FALSE;
            }
            
            if (terapia.getParentescoAcompanante().equalsIgnoreCase("")) {
                util.mostrarMensaje("Debe ingresar un parentesco.");
                valido = Boolean.FALSE;
            }
            
            if (terapia.getPlanTratamiento().equalsIgnoreCase("")) {
                util.mostrarMensaje("Debe ingresar un plan de tratamiento.");
                valido = Boolean.FALSE;
            }
            
            //Verifica que esten llenas cada una de las actividades
                 for(int i = 0; i<detalleTerapia.size() ; i++){
                       String campodetalle = detalleTerapia.get(i).getActividad();
                        if (campodetalle == null || campodetalle.equalsIgnoreCase(""))  {
                                i++;
                                util.mostrarMensaje("Debe ingresar la actividad # "+ i );
                                 valido = Boolean.FALSE;
                    }            
             }
             

            if (valido){
                            resultado = gestorTerapia.actualizarTerapiaCita(terapia, getDetalleTerapia(), permiteDividir);
                            if (resultado != null) {
                                guardado = Boolean.TRUE;
                                util.mostrarMensaje("La terapia Nro." + terapia.getCodigo() + " se guardo exitosamente.");
                                codigoTerapia = terapia.getCodigo();
                                terapia = new Terapia();
                                terapia.setCodigo(codigoTerapia);
                                manejoVentana = "window.close();";
                            } else {
                                guardado = Boolean.FALSE;
                                util.mostrarMensaje("Se presento un error al guardar.");
                            }
            }

        } catch (Exception ex) {
            util.mostrarMensaje(ex.getMessage());
            Logger.getLogger(UITerapia.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void listarEntidades() {
        try {
            cmbEntidades = new ArrayList<>();
            ArrayList<Entidad> listaEntidadesLocal;
            GestorEntidad gestorEntidad = new GestorEntidad();
            listaEntidadesLocal = gestorEntidad.listarEntidades();

            for (Entidad e : listaEntidadesLocal) {
                cmbEntidades.add(new SelectItem(e.getCodigo(), e.getNombre()));
            }
        } catch (Exception ex) {
            Logger.getLogger(UITerapia.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void cargarListaCondicion() {
        try {
            setListaCondicion(getGestorUtilidades().listarCombo("CONDICION_TERAPIA", "VALUE"));
        } catch (Exception ex) {
            Logger.getLogger(UICita.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void cargarListaCodigosDiagnostico() {
        try {
            setListaCodigosDiagnostico(getGestorUtilidades().listarCombo("DIAGNOSTICO_CIE10", "COMBINADO"));
        } catch (Exception ex) {
            Logger.getLogger(UICita.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void consultarCitasTerapia() {
        try {
            listaCitasReplicar.clear();
            listaCitasReplicar = getGestorCita().consultarCitasTerapia(terapia.getProfesionalPrescribe(), fechaInicial);
            if (listaCitasReplicar.isEmpty()) {
                util.mostrarMensaje("La consulta no recuperó registros.");
            }
        } catch (Exception ex) {
            util.mostrarMensaje(ex.getMessage());
        }
    }

    public void guardarCitasReplicar() {
        try {
            Boolean valido = Boolean.FALSE;
            for (Cita c : listaCitasReplicar) {
                if (c.getTieneAtencion()) {
                    valido = Boolean.TRUE;
                }
            }

            if (valido) {
                if (!eventModelReplicar.getEvents().isEmpty()) {
                    Integer resultado = gestorCita.guardarCitasReplicar(eventModelReplicar, listaCitasReplicar);

                    if (null != resultado) {
                        switch (resultado) {
                            case -2:
                                util.mostrarMensaje("Uno o mas pacientes no tienen terapia activa.");
                                break;
                            case -1:
                                util.mostrarMensaje("El profesional no tiene la agenda adecuada para copiar la citas seleccionadas.");
                                break;
                            case 1:
                                util.mostrarMensaje("Citas copiadas.");
                                break;
                            default:
                                break;
                        }
                    }
                } else {
                    util.mostrarMensaje("Seleccione al menos una día en el calendario.");
                }
            } else {
                util.mostrarMensaje("Seleccione al menos una cita.");
            }

        } catch (Exception ex) {
            util.mostrarMensaje(ex.getMessage());
        }
    }

    public void onEventSelectReplicar(SelectEvent selectEvent) {
        DefaultScheduleEvent eventoCapturado = (DefaultScheduleEvent) selectEvent.getObject();
        SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd");
        ScheduleEvent evento = new DefaultScheduleEvent("", new Date(), new Date());

        for (ScheduleEvent se : eventModelReplicar.getEvents()) {
            if (formatoFecha.format(se.getStartDate()).equalsIgnoreCase(formatoFecha.format(eventoCapturado.getStartDate()))) {
                evento = se;
                break;
            }
        }
        eventModelReplicar.deleteEvent(evento);
    }

    public void onDateSelectReplicar(SelectEvent selectEvent) {
        SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd");
        Boolean encontrado = Boolean.FALSE;
        ScheduleEvent evento = new DefaultScheduleEvent("", new Date(), new Date());
        Date fechaCapturada = (Date) selectEvent.getObject();

        for (ScheduleEvent se : eventModelReplicar.getEvents()) {
            if (formatoFecha.format(se.getStartDate()).equalsIgnoreCase(formatoFecha.format(fechaCapturada))) {
                encontrado = Boolean.TRUE;
                evento = se;
                break;
            }
        }

        if (encontrado) {
            eventModelReplicar.deleteEvent(evento);
        } else {
            eventModelReplicar.addEvent(new DefaultScheduleEvent("", fechaCapturada, fechaCapturada));
        }
    }

    public List<String> listarDiagnosticos(String query) throws Exception {
        ArrayList<Diagnostico> listaDiagnosticos;
        listaDiagnosticos = gestorDiagnostico.listarDiagnosticos(query);
        List<String> listaDiag = new ArrayList<>();
        for (Diagnostico d : listaDiagnosticos) {
            listaDiag.add(d.getNombre_diagostico());
        }
        return listaDiag;
    }

    public void configurarRutaInformeTerapeutico() {
        rutaExportar = "window.open('.././exportar?nomReporte=informeTerapeutico&parametros=codigoTerapia&valores=" + terapiaSeleccionada.getCodigo() + "&tipos=I');";
    }

    public void configurarRutaRecetario() {
        //rutaExportar = "window.open('.././exportar?nomReporte=informeTerapeutico&parametros=codigoTerapia&valores=" + terapiaSeleccionada.getCodigo() + "&tipos=I');";

        rutaRecetario = "window.open('.././exportar?nomReporte=recetariot&parametros=codigoTerapia&valores=" + terapiaSeleccionada.getCodigo() + "&tipos=I');";
    }

    public void dividirFranja() {
        int i;
        //por solicitud se va permitir que la division no sea exacta y me la franja minima sea de 7 min
        //es decir una franja de 15 min se puede dividir en una franja de 8 y otra de 7
        //if (detalleTerapia.get(0).getDuracion() % Integer.parseInt(terapia.getCantSesiones()) != 0 || detalleTerapia.get(0).getDuracion() / Integer.parseInt(terapia.getCantSesiones()) < 10) {
        if (detalleTerapia.get(0).getDuracion() / Integer.parseInt(terapia.getCantSesiones()) < 7) {    //solo falla cuando la division es menor a 7
            util.mostrarMensaje("No es posible dividir en " + terapia.getCantSesiones() + " sesiones una cita de " + detalleTerapia.get(0).getDuracion() + " minutos");
        } else if (detalleTerapia.size() != Integer.parseInt(terapia.getCantSesiones())) {
            if (detalleTerapia.size() > Integer.parseInt(terapia.getCantSesiones())) { //disminuir tamaño
                for (i = 0; i < detalleTerapia.size() - Integer.parseInt(terapia.getCantSesiones()); i++) {
                    detalleTerapia.remove(detalleTerapia.size() - 1);
                }
            } else {
                for (i = 0; i < Integer.parseInt(terapia.getCantSesiones()) - detalleTerapia.size(); i++) {
                    //seteo el consecutivo como el ultimo mas uno
                    detalleTerapia.add(new DetalleTerapia(detalleTerapia.get(detalleTerapia.size() - 1).getConsecutivo() + 1));
                }
            }
        }
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
     * @return the activa
     */
    public Boolean getActiva() {
        return activa;
    }

    /**
     * @param activa the activa to set
     */
    public void setActiva(Boolean activa) {
        this.activa = activa;
    }

    /**
     * @return the terapiaSeleccionada
     */
    public Terapia getTerapiaSeleccionada() {
        return terapiaSeleccionada;
    }

    /**
     * @param terapiaSeleccionada the terapiaSeleccionada to set
     */
    public void setTerapiaSeleccionada(Terapia terapiaSeleccionada) {
        this.terapiaSeleccionada = terapiaSeleccionada;
    }

    /**
     * @return the terapia
     */
    public Terapia getTerapia() {
        return terapia;
    }

    /**
     * @param terapia the terapia to set
     */
    public void setTerapia(Terapia terapia) {
        this.terapia = terapia;
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
     * @return the cmbTerapias
     */
    public List<SelectItem> getCmbTerapias() {
        return cmbTerapias;
    }

    /**
     * @param cmbTerapias the cmbTerapias to set
     */
    public void setCmbTerapias(List<SelectItem> cmbTerapias) {
        this.cmbTerapias = cmbTerapias;
    }

    /**
     * @return the gestorTerapia
     */
    public GestorTerapia getGestorTerapia() {
        return gestorTerapia;
    }

    /**
     * @param gestorTerapia the gestorTerapia to set
     */
    public void setGestorTerapia(GestorTerapia gestorTerapia) {
        this.gestorTerapia = gestorTerapia;
    }

    /**
     * @return the guardado
     */
    public Boolean getGuardado() {
        return guardado;
    }

    /**
     * @param guardado the guardado to set
     */
    public void setGuardado(Boolean guardado) {
        this.guardado = guardado;
    }

    /**
     * @return the paciente
     */
    public Paciente getPaciente() {
        return paciente;
    }

    /**
     * @param paciente the paciente to set
     */
    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
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
     * @return the edadInicial
     */
    public String getEdadInicial() {
        return edadInicial;
    }

    /**
     * @param edadInicial the edadInicial to set
     */
    public void setEdadInicial(String edadInicial) {
        this.edadInicial = edadInicial;
    }

    /**
     * @return the edadFinal
     */
    public String getEdadFinal() {
        return edadFinal;
    }

    /**
     * @param edadFinal the edadFinal to set
     */
    public void setEdadFinal(String edadFinal) {
        this.edadFinal = edadFinal;
    }

    /**
     * @return the cmbEntidades
     */
    public List<SelectItem> getCmbEntidades() {
        return cmbEntidades;
    }

    /**
     * @param cmbEntidades the cmbEntidades to set
     */
    public void setCmbEntidades(List<SelectItem> cmbEntidades) {
        this.cmbEntidades = cmbEntidades;
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
     * @return the listaCondicion
     */
    public List<SelectItem> getListaCondicion() {
        return listaCondicion;
    }

    /**
     * @param listaCondicion the listaCondicion to set
     */
    public void setListaCondicion(List<SelectItem> listaCondicion) {
        this.listaCondicion = listaCondicion;
    }

    /**
     * @return the autorizadas
     */
    public Boolean getAutorizadas() {
        return autorizadas;
    }

    /**
     * @param autorizadas the autorizadas to set
     */
    public void setAutorizadas(Boolean autorizadas) {
        this.autorizadas = autorizadas;
    }

    /**
     * @return the cmbProfesionales
     */
    public List<SelectItem> getCmbProfesionales() {
        return cmbProfesionales;
    }

    /**
     * @param cmbProfesionales the cmbProfesionales to set
     */
    public void setCmbProfesionales(List<SelectItem> cmbProfesionales) {
        this.cmbProfesionales = cmbProfesionales;
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
     * @return the sinProximaCita
     */
    public Boolean getSinProximaCita() {
        return sinProximaCita;
    }

    /**
     * @param sinProximaCita the sinProximaCita to set
     */
    public void setSinProximaCita(Boolean sinProximaCita) {
        this.sinProximaCita = sinProximaCita;
    }

    /**
     * @return the listaCodigosDiagnostico
     */
    public List<SelectItem> getListaCodigosDiagnostico() {
        return listaCodigosDiagnostico;
    }

    /**
     * @param listaCodigosDiagnostico the listaCodigosDiagnostico to set
     */
    public void setListaCodigosDiagnostico(List<SelectItem> listaCodigosDiagnostico) {
        this.listaCodigosDiagnostico = listaCodigosDiagnostico;
    }

    /**
     * @return the rutaExportar
     */
    public String getRutaExportar() {
        return rutaExportar;
    }

    /**
     * @param rutaExportar the rutaExportar to set
     */
    public void setRutaExportar(String rutaExportar) {
        this.rutaExportar = rutaExportar;
    }

    /**
     * @return the listaCitasReplicar
     */
    public List<Cita> getListaCitasReplicar() {
        return listaCitasReplicar;
    }

    /**
     * @param listaCitasReplicar the listaCitasReplicar to set
     */
    public void setListaCitasReplicar(List<Cita> listaCitasReplicar) {
        this.listaCitasReplicar = listaCitasReplicar;
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
     * @return the eventModelReplicar
     */
    public ScheduleModel getEventModelReplicar() {
        return eventModelReplicar;
    }

    /**
     * @param eventModelReplicar the eventModelReplicar to set
     */
    public void setEventModelReplicar(ScheduleModel eventModelReplicar) {
        this.eventModelReplicar = eventModelReplicar;
    }

    public GestorDiagnostico getGestorDiagnostico() {
        return gestorDiagnostico;
    }

    public void setGestorDiagnostico(GestorDiagnostico gestorDiagnostico) {
        this.gestorDiagnostico = gestorDiagnostico;
    }

    /**
     * @return the rutaRecetario
     */
    public String getRutaRecetario() {
        return rutaRecetario;
    }

    /**
     * @param rutaRecetario the rutaRecetario to set
     */
    public void setRutaRecetario(String rutaRecetario) {
        this.rutaRecetario = rutaRecetario;
    }

    /**
     * @return the detalleTerapia
     */
    public List<DetalleTerapia> getDetalleTerapia() {
        return detalleTerapia;
    }

    /**
     * @param detalleTerapia the detalleTerapia to set
     */
    public void setDetalleTerapia(List<DetalleTerapia> detalleTerapia) {
        this.detalleTerapia = detalleTerapia;
    }

    /**
     * @return the permiteDividir
     */
    public Boolean getPermiteDividir() {
        return permiteDividir;
    }

    /**
     * @param permiteDividir the permiteDividir to set
     */
    public void setPermiteDividir(Boolean permiteDividir) {
        this.permiteDividir = permiteDividir;
    }

    public String getManejoVentana() {
        return manejoVentana;
    }

    public void setManejoVentana(String manejoVentana) {
        this.manejoVentana = manejoVentana;
    }        

}
