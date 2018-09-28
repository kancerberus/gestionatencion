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
public class DetalleTerapia {
    private Integer consecutivo;
    private String actividad;
    private Date fecha;
    private Date hora;

    public DetalleTerapia() {
    }

    /**
     * @return the actividad
     */
    public String getActividad() {
        return actividad;
    }

    /**
     * @param actividad the actividad to set
     */
    public void setActividad(String actividad) {
        this.actividad = actividad;
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
     * @return the consecutivo
     */
    public Integer getConsecutivo() {
        return consecutivo;
    }

    /**
     * @param consecutivo the consecutivo to set
     */
    public void setConsecutivo(Integer consecutivo) {
        this.consecutivo = consecutivo;
    }
    
    
    
}
