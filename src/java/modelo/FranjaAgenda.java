/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.util.Date;

/**
 *
 * @author Andres
 */
public class FranjaAgenda implements Cloneable{
    private String id;
    private Profesional profesional;
    private Especialidad especialidad;
    private Paciente paciente;
    private Date fechaHora;
    private String duracion;
    private String codCita;
    private Boolean preAsignada;
    private Boolean seleccionada;
    private Procedimiento procedimiento;
    private String observaciones;
    private Boolean reservadoValoracion;
    private String accion;

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone(); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    
    public FranjaAgenda() {
        preAsignada = false;
        seleccionada = false;
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
     * @return the especialidad
     */
    public Especialidad getEspecialidad() {
        return especialidad;
    }

    /**
     * @param especialidad the especialidad to set
     */
    public void setEspecialidad(Especialidad especialidad) {
        this.especialidad = especialidad;
    }

    /**
     * @return the fechaHora
     */
    public Date getFechaHora() {
        return fechaHora;
    }

    /**
     * @param fechaHora the fechaHora to set
     */
    public void setFechaHora(Date fechaHora) {
        this.fechaHora = fechaHora;
    }

    /**
     * @return the duracion
     */
    public String getDuracion() {
        return duracion;
    }

    /**
     * @param duracion the duracion to set
     */
    public void setDuracion(String duracion) {
        this.duracion = duracion;
    }

    /**
     * @return the codCita
     */
    public String getCodCita() {
        return codCita;
    }

    /**
     * @param codCita the codCita to set
     */
    public void setCodCita(String codCita) {
        this.codCita = codCita;
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
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return the preAsignada
     */
    public Boolean getPreAsignada() {
        return preAsignada;
    }

    /**
     * @param preAsignada the preAsignada to set
     */
    public void setPreAsignada(Boolean preAsignada) {
        this.preAsignada = preAsignada;
    }

    /**
     * @return the seleccionada
     */
    public Boolean getSeleccionada() {
        return seleccionada;
    }

    /**
     * @param seleccionada the seleccionada to set
     */
    public void setSeleccionada(Boolean seleccionada) {
        this.seleccionada = seleccionada;
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
     * @return the reservadoValoracion
     */
    public Boolean getReservadoValoracion() {
        return reservadoValoracion;
    }

    /**
     * @param reservadoValoracion the reservadoValoracion to set
     */
    public void setReservadoValoracion(Boolean reservadoValoracion) {
        this.reservadoValoracion = reservadoValoracion;
    }

    /**
     * @return the accion
     */
    public String getAccion() {
        return accion;
    }

    /**
     * @param accion the accion to set
     */
    public void setAccion(String accion) {
        this.accion = accion;
    }

     
}
