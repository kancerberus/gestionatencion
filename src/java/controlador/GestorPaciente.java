/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import bd.PacienteDAO;
import java.util.ArrayList;
import java.util.List;
import modelo.ControlExport;
import modelo.Paciente;

/**
 *
 * @author Andres
 */
public class GestorPaciente extends Gestor {

    public GestorPaciente() throws Exception {
        super();
    }

    public ArrayList<Paciente> listarPacientesPatron(String patron) throws Exception {
        try {
            abrirConexion();
            PacienteDAO pacienteDAO = new PacienteDAO(conexion);
            return pacienteDAO.listarPacientesPatron(patron);
        } finally {
            cerrarConexion();
        }
    }

    public Paciente consultarPacientePorNombre(String nombre) throws Exception {
        try {
            abrirConexion();
            PacienteDAO pacienteDAO = new PacienteDAO(conexion);
            return pacienteDAO.consultarPacientePorNombre(nombre);
        } finally {
            cerrarConexion();
        }
    }

    public Paciente consultarPacientePorNombreCompleto(String nombre) throws Exception {
        try {
            abrirConexion();
            PacienteDAO pacienteDAO = new PacienteDAO(conexion);
            return pacienteDAO.consultarPacientePorNombreCompleto(nombre);
        } finally {
            cerrarConexion();
        }
    }

    public Integer guardarPaciente(Paciente paciente, Boolean existe) throws Exception {
        try {
            abrirConexion();
            PacienteDAO pacienteDAO = new PacienteDAO(conexion);
            return pacienteDAO.guardarPaciente(paciente, existe);
        } finally {
            cerrarConexion();
        }
    }
    
    public Integer actualizarPaciente1(Paciente paciente) throws Exception {
        try {
            abrirConexion();
            PacienteDAO pacienteDAO = new PacienteDAO(conexion);
            return pacienteDAO.actualizarPaciente1(paciente);
        } finally {
            cerrarConexion();
        }
    }

    public Paciente consultarPacientePorId(String identificacion) throws Exception {
        try {
            abrirConexion();
            PacienteDAO pacienteDAO = new PacienteDAO(conexion);
            return pacienteDAO.consultarPacientePorId(identificacion);
        } finally {
            cerrarConexion();
        }
    }

    public ArrayList<ControlExport> listarControlExport() throws Exception {
        try {
            abrirConexion();
            PacienteDAO pacienteDAO = new PacienteDAO(conexion);
            return pacienteDAO.listarControlExport();
        } finally {
            cerrarConexion();
        }
    }

    public Integer generarExport() throws Exception {
        try {
            abrirConexion();
            PacienteDAO pacienteDAO = new PacienteDAO(conexion);
            return pacienteDAO.generarExport();
        } finally {
            cerrarConexion();
        }
    }
    
    public ArrayList<String> consultarControlExport(String codExport) throws Exception {
        try {
            abrirConexion();
            PacienteDAO pacienteDAO = new PacienteDAO(conexion);
            return pacienteDAO.consultarControlExport(codExport);
        } finally {
            cerrarConexion();
        }
    }
    
    public ArrayList<String> consultarControlExport2(String codExport) throws Exception {
        try {
            abrirConexion();
            PacienteDAO pacienteDAO = new PacienteDAO(conexion);
            return pacienteDAO.consultarControlExport2(codExport);
        } finally {
            cerrarConexion();
        }
    }
    public List<Paciente> listarPacientes() throws Exception {
        try {
            abrirConexion();
            PacienteDAO pacienteDAO = new PacienteDAO(conexion);
            return pacienteDAO.listarPacientes();
        } finally {
            cerrarConexion();
        }
    }

}
