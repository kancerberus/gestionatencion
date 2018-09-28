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
public class FranjaInforme {
  
    private Profesional profesional;
    private Especialidad especialidad;
    private Paciente paciente;
    private Cita cita;
    private Date fechaHora;
    private Procedimiento procedimiento;
    
    public FranjaInforme() {

    }

    
    public Cita getCita() {
        return cita;
    }

    public void setCita(Cita cita) {
        this.cita = cita;
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
     
}
