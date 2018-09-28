/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

/**
 *
 * @author Andres
 */
public class CampoLibre {
    
    private String parametro;
    private String od;
    private String oi;
    private String campoLibre;

    public CampoLibre(String parametro, String od, String oi, String campoLibre) {
        this.parametro = parametro;
        this.od = od;
        this.oi = oi;
        this.campoLibre = campoLibre;
    }

    /**
     * @return the parametro
     */
    public String getParametro() {
        return parametro;
    }

    /**
     * @param parametro the parametro to set
     */
    public void setParametro(String parametro) {
        this.parametro = parametro;
    }

    /**
     * @return the od
     */
    public String getOd() {
        return od;
    }

    /**
     * @param od the od to set
     */
    public void setOd(String od) {
        this.od = od;
    }

    /**
     * @return the oi
     */
    public String getOi() {
        return oi;
    }

    /**
     * @param oi the oi to set
     */
    public void setOi(String oi) {
        this.oi = oi;
    }

    /**
     * @return the campoLibre
     */
    public String getCampoLibre() {
        return campoLibre;
    }

    /**
     * @param campoLibre the campoLibre to set
     */
    public void setCampoLibre(String campoLibre) {
        this.campoLibre = campoLibre;
    }
    
    
    
}
