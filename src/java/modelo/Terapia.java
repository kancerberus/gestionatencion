/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Andres
 */
public class Terapia {

    private Integer codigo;
    private Cita cita;
    private Profesional profesionalPrescribe;
    private Procedimiento procedimiento;
    private Date fecha;
    private Date hora;
    private Integer cantidadFormulada;
    private Integer cantidadAutorizada;
    private Integer cantidadPendiente;
    private Integer cantidadAtendida;
    private Integer cantidadInasistidas;
    private Boolean activa;
    private Integer codigoValoracion;
    private String nombreAcompanante;
    private String parentescoAcompanante;
    private String codigoRIPS;
    private String codigoDiagnostico;
    private String codigoDiagnostico2;
    private Boolean primeraVez;
    private Boolean control;
    private String diagnostico;
    private String planTratamiento;
    private String evolucion;
    private String recomendacion;
    private String nroAutorizacion;
    private List<DetalleTerapia> detalleTerapia;

    private Date fechaSolicitud;
    private String horarioPreferencial;
    private String observaciones;
    private String seguimiento1;
    private String seguimiento2;
    private String seguimiento3;
    private Valoracion valoracion;

    private String cantSesiones;
    private Objeto autorizada;

    private String informeTerapeutico;

    private Diagnostico diagnostico1;
    private Diagnostico diagnostico2;

    public Terapia() {
        cita = new Cita();
        valoracion = new Valoracion();
        procedimiento = new Procedimiento();
        detalleTerapia = new ArrayList<>();
        profesionalPrescribe = new Profesional();
        autorizada = new Objeto("1", "NO");
        
        diagnostico1 = new Diagnostico();
        diagnostico2 = new Diagnostico();
    }

    /**
     * @return the codigo
     */
    public Integer getCodigo() {
        return codigo;
    }

    /**
     * @param codigo the codigo to set
     */
    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
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
     * @return the cantidadFormulada
     */
    public Integer getCantidadFormulada() {
        return cantidadFormulada;
    }

    /**
     * @param cantidadFormulada the cantidadFormulada to set
     */
    public void setCantidadFormulada(Integer cantidadFormulada) {
        this.cantidadFormulada = cantidadFormulada;
    }

    /**
     * @return the cantidadAutorizada
     */
    public Integer getCantidadAutorizada() {
        return cantidadAutorizada;
    }

    /**
     * @param cantidadAutorizada the cantidadAutorizada to set
     */
    public void setCantidadAutorizada(Integer cantidadAutorizada) {
        this.cantidadAutorizada = cantidadAutorizada;
    }

    /**
     * @return the cantidadPendiente
     */
    public Integer getCantidadPendiente() {
        return cantidadPendiente;
    }

    /**
     * @param cantidadPendiente the cantidadPendiente to set
     */
    public void setCantidadPendiente(Integer cantidadPendiente) {
        this.cantidadPendiente = cantidadPendiente;
    }

    /**
     * @return the cantidadAtendida
     */
    public Integer getCantidadAtendida() {
        return cantidadAtendida;
    }

    /**
     * @param cantidadAtendida the cantidadAtendida to set
     */
    public void setCantidadAtendida(Integer cantidadAtendida) {
        this.cantidadAtendida = cantidadAtendida;
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
     * @return the codigoValoracion
     */
    public Integer getCodigoValoracion() {
        return codigoValoracion;
    }

    /**
     * @param codigoValoracion the codigoValoracion to set
     */
    public void setCodigoValoracion(Integer codigoValoracion) {
        this.codigoValoracion = codigoValoracion;
    }

    /**
     * @return the nombreAcompanante
     */
    public String getNombreAcompanante() {
        return nombreAcompanante;
    }

    /**
     * @param nombreAcompanante the nombreAcompanante to set
     */
    public void setNombreAcompanante(String nombreAcompanante) {
        this.nombreAcompanante = nombreAcompanante;
    }

    /**
     * @return the parentescoAcompanante
     */
    public String getParentescoAcompanante() {
        return parentescoAcompanante;
    }

    /**
     * @param parentescoAcompanante the parentescoAcompanante to set
     */
    public void setParentescoAcompanante(String parentescoAcompanante) {
        this.parentescoAcompanante = parentescoAcompanante;
    }

    /**
     * @return the codigoDiagnostico
     */
    public String getCodigoDiagnostico() {
        return codigoDiagnostico;
    }

    /**
     * @param codigoDiagnostico the codigoDiagnostico to set
     */
    public void setCodigoDiagnostico(String codigoDiagnostico) {
        this.codigoDiagnostico = codigoDiagnostico;
    }

    /**
     * @return the primeraVez
     */
    public Boolean getPrimeraVez() {
        return primeraVez;
    }

    /**
     * @param primeraVez the primeraVez to set
     */
    public void setPrimeraVez(Boolean primeraVez) {
        this.primeraVez = primeraVez;
    }

    /**
     * @return the control
     */
    public Boolean getControl() {
        return control;
    }

    /**
     * @param control the control to set
     */
    public void setControl(Boolean control) {
        this.control = control;
    }

    /**
     * @return the diagnostico
     */
    public String getDiagnostico() {
        return diagnostico;
    }

    /**
     * @param diagnostico the diagnostico to set
     */
    public void setDiagnostico(String diagnostico) {
        this.diagnostico = diagnostico;
    }

    /**
     * @return the planTratamiento
     */
    public String getPlanTratamiento() {
        return planTratamiento;
    }

    /**
     * @param planTratamiento the planTratamiento to set
     */
    public void setPlanTratamiento(String planTratamiento) {
        this.planTratamiento = planTratamiento;
    }

    /**
     * @return the evolucion
     */
    public String getEvolucion() {
        return evolucion;
    }

    /**
     * @param evolucion the evolucion to set
     */
    public void setEvolucion(String evolucion) {
        this.evolucion = evolucion;
    }

    /**
     * @return the hora
     */
    public Date getHora() {
        return hora;
    }

    /**
     * @param hora the hora to set
     */
    public void setHora(Date hora) {
        this.hora = hora;
    }

    /**
     * @return the codigoRIPS
     */
    public String getCodigoRIPS() {
        return codigoRIPS;
    }

    /**
     * @param codigoRIPS the codigoRIPS to set
     */
    public void setCodigoRIPS(String codigoRIPS) {
        this.codigoRIPS = codigoRIPS;
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
     * @return the profesionalPrescribe
     */
    public Profesional getProfesionalPrescribe() {
        return profesionalPrescribe;
    }

    /**
     * @param profesionalPrescribe the profesionalPrescribe to set
     */
    public void setProfesionalPrescribe(Profesional profesionalPrescribe) {
        this.profesionalPrescribe = profesionalPrescribe;
    }

    /**
     * @return the procedimiento
     */
    public Procedimiento getProcedimiento() {
        return procedimiento;
    }

    /**
     * @param procedimiento the procedimiento to set
     */
    public void setProcedimiento(Procedimiento procedimiento) {
        this.procedimiento = procedimiento;
    }

    /**
     * @return the cantidadInasistidas
     */
    public Integer getCantidadInasistidas() {
        return cantidadInasistidas;
    }

    /**
     * @param cantidadInasistidas the cantidadInasistidas to set
     */
    public void setCantidadInasistidas(Integer cantidadInasistidas) {
        this.cantidadInasistidas = cantidadInasistidas;
    }

    /**
     * @return the nroAutorizacion
     */
    public String getNroAutorizacion() {
        return nroAutorizacion;
    }

    /**
     * @param nroAutorizacion the nroAutorizacion to set
     */
    public void setNroAutorizacion(String nroAutorizacion) {
        this.nroAutorizacion = nroAutorizacion;
    }

    /**
     * @return the recomendacion
     */
    public String getRecomendacion() {
        return recomendacion;
    }

    /**
     * @param recomendacion the recomendacion to set
     */
    public void setRecomendacion(String recomendacion) {
        this.recomendacion = recomendacion;
    }

    /**
     * @return the fechaSolicitud
     */
    public Date getFechaSolicitud() {
        return fechaSolicitud;
    }

    /**
     * @param fechaSolicitud the fechaSolicitud to set
     */
    public void setFechaSolicitud(Date fechaSolicitud) {
        this.fechaSolicitud = fechaSolicitud;
    }

    /**
     * @return the horarioPreferencial
     */
    public String getHorarioPreferencial() {
        return horarioPreferencial;
    }

    /**
     * @param horarioPreferencial the horarioPreferencial to set
     */
    public void setHorarioPreferencial(String horarioPreferencial) {
        this.horarioPreferencial = horarioPreferencial;
    }

    /**
     * @return the observaciones
     */
    public String getObservaciones() {
        return observaciones;
    }

    /**
     * @param observaciones the observaciones to set
     */
    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    /**
     * @return the seguimiento1
     */
    public String getSeguimiento1() {
        return seguimiento1;
    }

    /**
     * @param seguimiento1 the seguimiento1 to set
     */
    public void setSeguimiento1(String seguimiento1) {
        this.seguimiento1 = seguimiento1;
    }

    /**
     * @return the seguimiento2
     */
    public String getSeguimiento2() {
        return seguimiento2;
    }

    /**
     * @param seguimiento2 the seguimiento2 to set
     */
    public void setSeguimiento2(String seguimiento2) {
        this.seguimiento2 = seguimiento2;
    }

    /**
     * @return the seguimiento3
     */
    public String getSeguimiento3() {
        return seguimiento3;
    }

    /**
     * @param seguimiento3 the seguimiento3 to set
     */
    public void setSeguimiento3(String seguimiento3) {
        this.seguimiento3 = seguimiento3;
    }

    /**
     * @return the valoracion
     */
    public Valoracion getValoracion() {
        return valoracion;
    }

    /**
     * @param valoracion the valoracion to set
     */
    public void setValoracion(Valoracion valoracion) {
        this.valoracion = valoracion;
    }

    /**
     * @return the cantSesiones
     */
    public String getCantSesiones() {
        return cantSesiones;
    }

    /**
     * @param cantSesiones the cantSesiones to set
     */
    public void setCantSesiones(String cantSesiones) {
        this.cantSesiones = cantSesiones;
    }

    /**
     * @return the codigoDiagnostico2
     */
    public String getCodigoDiagnostico2() {
        return codigoDiagnostico2;
    }

    /**
     * @param codigoDiagnostico2 the codigoDiagnostico2 to set
     */
    public void setCodigoDiagnostico2(String codigoDiagnostico2) {
        this.codigoDiagnostico2 = codigoDiagnostico2;
    }

    /**
     * @return the autorizada
     */
    public Objeto getAutorizada() {
        return autorizada;
    }

    /**
     * @param autorizada the autorizada to set
     */
    public void setAutorizada(Objeto autorizada) {
        this.autorizada = autorizada;
    }

    /**
     * @return the informeTerapeutico
     */
    public String getInformeTerapeutico() {
        return informeTerapeutico;
    }

    /**
     * @param informeTerapeutico the informeTerapeutico to set
     */
    public void setInformeTerapeutico(String informeTerapeutico) {
        this.informeTerapeutico = informeTerapeutico;
    }

    /**
     * @return the diagnostico1
     */
    public Diagnostico getDiagnostico1() {
        return diagnostico1;
    }

    /**
     * @param diagnostico1 the diagnostico1 to set
     */
    public void setDiagnostico1(Diagnostico diagnostico1) {
        this.diagnostico1 = diagnostico1;
    }

    /**
     * @return the diagnostico2
     */
    public Diagnostico getDiagnostico2() {
        return diagnostico2;
    }

    /**
     * @param diagnostico2 the diagnostico2 to set
     */
    public void setDiagnostico2(Diagnostico diagnostico2) {
        this.diagnostico2 = diagnostico2;
    }

}
