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
    
    private String codigo;
    private String codigoLista;
    private String parametro;
    private String od;
    private String oi;
    private String campoLibre;

    public CampoLibre(String codigo, String codigoLista, String parametro) {
        this.codigo = codigo;
        this.codigoLista = codigoLista;
        this.parametro = parametro;
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

    /**
     * @return the codigo
     */
    public String getCodigo() {
        return codigo;
    }

    /**
     * @param codigo the codigo to set
     */
    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    /**
     * @return the codigoLista
     */
    public String getCodigoLista() {
        return codigoLista;
    }

    /**
     * @param codigoLista the codigoLista to set
     */
    public void setCodigoLista(String codigoLista) {
        this.codigoLista = codigoLista;
    }
    
    
    
}
