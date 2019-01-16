/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import bd.CitaDAO;
import java.util.Date;
import java.util.List;
import modelo.Cita;
import modelo.Paciente;
import modelo.Profesional;
import org.primefaces.model.ScheduleModel;

/**
 *
 * @author Andres
 */
public class GestorCita extends Gestor {

    public GestorCita() throws Exception {
        super();
    }

    public Integer consultarCodigoAtencionCita(Cita cita) throws Exception {
        try {
            abrirConexion();
            CitaDAO citaDAO = new CitaDAO(conexion);
            return citaDAO.consultarCodigoAtencionCita(cita);
        } finally {
            cerrarConexion();
        }
    }
    
    public Integer guardarCita(Cita cita, List<Cita> listaCitaMultiple, Boolean usarFranjaExtendida, Integer cantidadFranjas) throws Exception {
        try {
            abrirConexion();
            CitaDAO citaDAO = new CitaDAO(conexion);
            return citaDAO.guardarCita(cita, listaCitaMultiple, usarFranjaExtendida, cantidadFranjas);
        } finally {
            cerrarConexion();
        }
    }

    public List<Cita> consultarCitas(Paciente paciente, Date fecha, Integer limite) throws Exception {
        try {
            abrirConexion();
            CitaDAO citaDAO = new CitaDAO(conexion);
            return citaDAO.consultarCitas(paciente, fecha, limite);
        } finally {
            cerrarConexion();
        }
    }
    
    public List<Cita> consultarCitasTerapia(Profesional profesional, Date fecha) throws Exception {
        try {
            abrirConexion();
            CitaDAO citaDAO = new CitaDAO(conexion);
            return citaDAO.consultarCitasTerapia(profesional, fecha);
        } finally {
            cerrarConexion();
        }
    }
    
    public Integer guardarCitasReplicar(ScheduleModel modeloFechas, List<Cita> listaCitas) throws Exception {
        try {
            abrirConexion();
            CitaDAO citaDAO = new CitaDAO(conexion);
            return citaDAO.guardarCitasReplicar(modeloFechas, listaCitas);
        } finally {
            cerrarConexion();
        }
    }

    public List<Cita> consultarCitasProfesionalDia(String identificacion) throws Exception {
        try {
            abrirConexion();
            CitaDAO citaDAO = new CitaDAO(conexion);
            return citaDAO.consultarCitasProfesionalDia(identificacion);
        } finally {
            cerrarConexion();
        }
    }
    
    public List<Cita> consultarCitasPaciente(String identificacion) throws Exception {
        try {
            abrirConexion();
            CitaDAO citaDAO = new CitaDAO(conexion);
            return citaDAO.consultarCitasPaciente(identificacion);
        } finally {
            cerrarConexion();
        }
    }
}
