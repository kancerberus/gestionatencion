/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import bd.UtilidadesDAO;
import java.io.Serializable;
import java.util.List;
import javax.faces.model.SelectItem;

/**
 *
 * @author Andres
 */
public class GestorUtilidades extends Gestor implements Serializable {

    public GestorUtilidades() throws Exception {
        super();
    }

    public List<SelectItem> listarCombo(String nombreLista, String modo) throws Exception {
        try {
            abrirConexion();
            UtilidadesDAO utilidadesDAO = new UtilidadesDAO(conexion);
            return utilidadesDAO.listarCombo(nombreLista, modo);
        } finally {
            cerrarConexion();
        }
    }
    
    public String consultarValueCombo(String nombreLista, String label) throws Exception {
        try {
            abrirConexion();
            UtilidadesDAO utilidadesDAO = new UtilidadesDAO(conexion);
            return utilidadesDAO.consultarValueCombo(nombreLista, label);
        } finally {
            cerrarConexion();
        }
    }
}
