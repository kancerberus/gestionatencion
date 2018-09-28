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
public class Timpanograma {

    private String parametro;    
    private String descripcion;
    private String valor1;
    private String valor2; 

    public Timpanograma(String parametro, String descripcion) {
        this.parametro = parametro;
        this.descripcion = descripcion;
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
     * @return the descripcion
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * @param descripcion the descripcion to set
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * @return the valor1
     */
    public String getValor1() {
        return valor1;
    }

    /**
     * @param valor1 the valor1 to set
     */
    public void setValor1(String valor1) {
        this.valor1 = valor1;
    }

    /**
     * @return the valor2
     */
    public String getValor2() {
        return valor2;
    }

    /**
     * @param valor2 the valor2 to set
     */
    public void setValor2(String valor2) {
        this.valor2 = valor2;
    }
    

    
    
}
