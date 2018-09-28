/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.util.ArrayList;
import modelo.Especialidad;
import bd.EspecialidadDAO;
import java.io.Serializable;

/**
 *
 * @author Andres
 */
public class GestorEspecialidad extends Gestor implements Serializable{

    public GestorEspecialidad() throws Exception {
        super();
    }

    public ArrayList<Especialidad> listarEspecialidadesPatron(String patron) throws Exception {
        try {
            abrirConexion();
            EspecialidadDAO especialidadDAO = new EspecialidadDAO(conexion);
            return especialidadDAO.listarEspecialidadesPatron(patron);
        } finally {
            cerrarConexion();
        }
    }

    public ArrayList<Especialidad> listarEspecialidadesProfesionalPatron(String patron, String cedula) throws Exception {
        try {
            abrirConexion();
            EspecialidadDAO especialidadDAO = new EspecialidadDAO(conexion);
            return especialidadDAO.listarEspecialidadesProfesionalPatron(patron, cedula);
        } finally {
            cerrarConexion();
        }
    }

    public Especialidad consultarEspecialidadPorNombre(String nombre) throws Exception {
        try {
            abrirConexion();
            EspecialidadDAO especialidadDAO = new EspecialidadDAO(conexion);
            return especialidadDAO.consultarEspecialidadPorNombre(nombre);
        } finally {
            cerrarConexion();
        }
    }

}
