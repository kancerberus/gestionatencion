/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import bd.CentroCostosDAO;
import java.io.Serializable;
import java.util.ArrayList;
import modelo.CentroCostos;

/**
 *
 * @author Andres
 */
public class GestorCentroCostos extends Gestor implements Serializable{

    public GestorCentroCostos() throws Exception {
        super();
    }
    
    public ArrayList<CentroCostos> listarCentroCostos() throws Exception{
       try {
            abrirConexion();
            CentroCostosDAO entidadDAO = new CentroCostosDAO(conexion);
            return entidadDAO.listarCentroCostos();
        } finally {
            cerrarConexion();
        }
    }
}
