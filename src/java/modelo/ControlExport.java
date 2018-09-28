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
public class ControlExport {

    public ControlExport(String codExport, Date fecha, String tema, Integer cantidad) {
        this.codExport = codExport;
        this.fecha = fecha;
        this.tema = tema;
        this.cantidad = cantidad;
    }
    
    private String codExport;
    private Date fecha;
    private String tema;
    private Integer cantidad;

    /**
     * @return the codExport
     */
    public String getCodExport() {
        return codExport;
    }

    /**
     * @param codExport the codExport to set
     */
    public void setCodExport(String codExport) {
        this.codExport = codExport;
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
     * @return the tema
     */
    public String getTema() {
        return tema;
    }

    /**
     * @param tema the tema to set
     */
    public void setTema(String tema) {
        this.tema = tema;
    }

    /**
     * @return the cantidad
     */
    public Integer getCantidad() {
        return cantidad;
    }

    /**
     * @param cantidad the cantidad to set
     */
    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }
    
}
