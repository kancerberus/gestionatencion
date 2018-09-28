/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vista;

import controlador.GestorEntidad;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.Entidad;
import util.Utilidades;

/**
 *
 * @author Andres
 */
public class UIEntidad implements Serializable {

    private Entidad entidad;
    private Boolean existe;
    private GestorEntidad gestorEntidad;
    private List<Entidad> listaEntidades;
    public Utilidades util = new Utilidades();

    public UIEntidad() throws Exception {
        listaEntidades = new ArrayList<>();
        gestorEntidad = new GestorEntidad();
        entidad = new Entidad();
    }

    public void guardarEntidad() {
        try {
            Integer resultado = gestorEntidad.guardarEntidad(entidad, existe);
            if (resultado > 0) {
                util.mostrarMensaje("La entidad se guardo exitosamente.");
                entidad = new Entidad();
            } else {
                util.mostrarMensaje("Se presento un error al guardar.");
            }
        } catch (Exception ex) {
            Logger.getLogger(UIEntidad.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void consultarEntidad() {
        try {
            setListaEntidades(gestorEntidad.listarEntidades());
        } catch (Exception ex) {
            Logger.getLogger(UIEntidad.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void consultarEntidadPorNit() {
        Entidad e;
        String nit = entidad.getNit();
        try {
            e = gestorEntidad.consultarEntidadPorNit(entidad.getNit());
            if (e != null) {
                entidad = e;
                existe = true;
            } else {
                entidad = new Entidad();
                entidad.setNit(nit);
                existe = false;
            }
        } catch (Exception ex) {
            Logger.getLogger(UIEntidad.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * @return the entidad
     */
    public Entidad getEntidad() {
        return entidad;
    }

    /**
     * @param entidad the entidad to set
     */
    public void setEntidad(Entidad entidad) {
        this.entidad = entidad;
    }

    /**
     * @return the gestorEntidad
     */
    public GestorEntidad getGestorEntidad() {
        return gestorEntidad;
    }

    /**
     * @param gestorEntidad the gestorEntidad to set
     */
    public void setGestorEntidad(GestorEntidad gestorEntidad) {
        this.gestorEntidad = gestorEntidad;
    }

    /**
     * @return the listaEntidades
     */
    public List<Entidad> getListaEntidades() {
        return listaEntidades;
    }

    /**
     * @param listaEntidades the listaEntidades to set
     */
    public void setListaEntidades(List<Entidad> listaEntidades) {
        this.listaEntidades = listaEntidades;
    }

    /**
     * @return the existe
     */
    public Boolean getExiste() {
        return existe;
    }

    /**
     * @param existe the existe to set
     */
    public void setExiste(Boolean existe) {
        this.existe = existe;
    }

}
