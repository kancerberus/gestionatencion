/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.util.ArrayList;
import modelo.Profesional;
import bd.ProfesionalDAO;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author Andres
 */

public class GestorProfesional extends Gestor implements Serializable{

    public GestorProfesional() throws Exception {
        super();
    }
    
    public ArrayList<Profesional> listarProfesionalesPatron(String patron) throws Exception{
       try {
            abrirConexion();
            ProfesionalDAO profesionalDAO = new ProfesionalDAO(conexion);
            return profesionalDAO.listarProfesionalesPatron(patron);
        } finally {
            cerrarConexion();
        }
    }
    
    public Profesional consultarProfesionalPorNombre(String nombre) throws Exception{
       try {
            abrirConexion();
            ProfesionalDAO profesionalDAO = new ProfesionalDAO(conexion);
            return profesionalDAO.consultarProfesionalPorNombre(nombre);
        } finally {
            cerrarConexion();
        }
    }
    
    public List<Profesional> consultarProfesionalesPorEspecialidad(String codigoEspecialidad) throws Exception {
       try {
            abrirConexion();
            ProfesionalDAO profesionalDAO = new ProfesionalDAO(conexion);
            return profesionalDAO.consultarProfesionalesPorEspecialidad(codigoEspecialidad);
        } finally {
            cerrarConexion();
        }
    }
    
    public List<Profesional> consultarDisponibilidadEspecialidadCantidadFranjas(String codigoEspecialidad, Integer cantidadFranjas) throws Exception {
       try {
            abrirConexion();
            ProfesionalDAO profesionalDAO = new ProfesionalDAO(conexion);
            return profesionalDAO.consultarDisponibilidadEspecialidadCantidadFranjas(codigoEspecialidad, cantidadFranjas);
        } finally {
            cerrarConexion();
        }
    }
    
    public List<Profesional> listarProfesionalesEspecialidadPatron(String patron, String codigoEspecialidad) throws Exception {
       try {
            abrirConexion();
            ProfesionalDAO profesionalDAO = new ProfesionalDAO(conexion);
            return profesionalDAO.listarProfesionalesEspecialidadPatron(patron, codigoEspecialidad);
        } finally {
            cerrarConexion();
        }
    }
    
    public List<Profesional> listarProfesionales() throws Exception {
       try {
            abrirConexion();
            ProfesionalDAO profesionalDAO = new ProfesionalDAO(conexion);
            return profesionalDAO.listarProfesionales();
        } finally {
            cerrarConexion();
        }
    }
    
    public Profesional consultarProfesionalPorCedula(String cedula) throws Exception{
       try {
            abrirConexion();
            ProfesionalDAO profesionalDAO = new ProfesionalDAO(conexion);
            return profesionalDAO.consultarProfesionalPorCedula(cedula);
        } finally {
            cerrarConexion();
        }
    }
    
    public Integer guardarProfesional(Profesional profesional, Boolean existe) throws Exception{
       try {
            abrirConexion();
            ProfesionalDAO profesionalDAO = new ProfesionalDAO(conexion);
            return profesionalDAO.guardarProfesional(profesional, existe);
        } finally {
            cerrarConexion();
        }
    }

}
