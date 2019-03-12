/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

public class Diagnostico {

    private String codigo_diagnostico  = "";
    private String nombre_diagostico = "";
   
    public Diagnostico() {
        
    }
    
    public Diagnostico(String codigo_diagnostico, String nombre_diagostico) {
        this.codigo_diagnostico = codigo_diagnostico;
        this.nombre_diagostico = nombre_diagostico;
    }

    public String getCodigo_diagnostico() {
        return codigo_diagnostico;
    }

    public void setCodigo_diagnostico(String codigo_diagnostico) {
        this.codigo_diagnostico = codigo_diagnostico;
    }

    public String getNombre_diagostico() {
        return nombre_diagostico;
    }

    public void setNombre_diagostico(String nombre_diagostico) {
        this.nombre_diagostico = nombre_diagostico;
    }

   
}
