/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import bd.EntidadDAO;
import java.io.Serializable;
import java.util.ArrayList;
import modelo.Entidad;

/**
 *
 * @author Andres
 */
public class GestorEntidad extends Gestor implements Serializable{

    public GestorEntidad() throws Exception {
        super();
    }
    
    public ArrayList<Entidad> listarEntidadesPatron(String patron) throws Exception{
       try {
            abrirConexion();
            EntidadDAO entidadDAO = new EntidadDAO(conexion);
            return entidadDAO.listarEntidadesPatron(patron);
        } finally {
            cerrarConexion();
        }
    }
    
    public ArrayList<Entidad> listarEntidades() throws Exception{
       try {
            abrirConexion();
            EntidadDAO entidadDAO = new EntidadDAO(conexion);
            return entidadDAO.listarEntidades();
        } finally {
            cerrarConexion();
        }
    }
    
    public Entidad consultarEntidadPorNombre(String nombre) throws Exception{
       try {
            abrirConexion();
            EntidadDAO entidadDAO = new EntidadDAO(conexion);
            return entidadDAO.consultarEntidadPorNombre(nombre);
        } finally {
            cerrarConexion();
        }
    }
    
    public Entidad consultarEntidadPorNit(String nit) throws Exception{
       try {
            abrirConexion();
            EntidadDAO entidadDAO = new EntidadDAO(conexion);
            return entidadDAO.consultarEntidadPorNit(nit);
        } finally {
            cerrarConexion();
        }
    }
    
    public Integer guardarEntidad(Entidad entidad, Boolean existe) throws Exception{
       try {
            abrirConexion();
            EntidadDAO entidadDAO = new EntidadDAO(conexion);
            return entidadDAO.guardarEntidad(entidad, existe);
        } finally {
            cerrarConexion();
        }
    }
}
