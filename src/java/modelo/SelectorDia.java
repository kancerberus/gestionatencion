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
public class SelectorDia {
    
    private String fecha;
    private boolean franja1;
    private boolean franja2;

    public SelectorDia() {

    }

    public SelectorDia(String fecha, boolean franja1, boolean franja2) {
        this.fecha = fecha;
        this.franja1 = franja1;
        this.franja2 = franja2;
    }

    /**
     * @return the fecha
     */
    public String getFecha() {
        return fecha;
    }

    /**
     * @param fecha the fecha to set
     */
    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    /**
     * @return the franja1
     */
    public boolean isFranja1() {
        return franja1;
    }

    /**
     * @param franja1 the franja1 to set
     */
    public void setFranja1(boolean franja1) {
        this.franja1 = franja1;
    }

    /**
     * @return the franja2
     */
    public boolean isFranja2() {
        return franja2;
    }

    /**
     * @param franja2 the franja2 to set
     */
    public void setFranja2(boolean franja2) {
        this.franja2 = franja2;
    }
    
}
