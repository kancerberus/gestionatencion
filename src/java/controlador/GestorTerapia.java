/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import bd.TerapiaDAO;
import java.util.Date;
import java.util.List;
import modelo.Cita;
import modelo.DetalleTerapia;
import modelo.Diagnostico;
import modelo.Paciente;
import modelo.Terapia;

/**
 *
 * @author Andres
 */
public class GestorTerapia extends Gestor {

    public GestorTerapia() throws Exception {
        super();
    }

    public List<Terapia> consultarTerapiasPaciente(Paciente paciente, Boolean activa, Date fechaInicial, Date fechaFinal, String edadInicial, String edadFinal, String procedimiento, Boolean autorizadas, Boolean sinProximaCita) throws Exception {
        try {
            abrirConexion();
            TerapiaDAO terapiaDAO = new TerapiaDAO(conexion);
            return terapiaDAO.consultarTerapiasPaciente(paciente, activa, fechaInicial, fechaFinal, edadInicial, edadFinal, procedimiento, autorizadas, sinProximaCita);
        } finally {
            cerrarConexion();
        }
    }
    
    public List<Terapia> consultarTerapiasPacienteAutorizarEvolucion(Paciente paciente, Boolean activa) throws Exception {
        try {
            abrirConexion();
            TerapiaDAO terapiaDAO = new TerapiaDAO(conexion);
            return terapiaDAO.consultarTerapiasPacienteAutorizarEvolucion(paciente, activa);
        } finally {
            cerrarConexion();
        }
    }

    public Integer actualizarTerapia(Terapia terapia) throws Exception {
        try {
            abrirConexion();
            TerapiaDAO terapiaDAO = new TerapiaDAO(conexion);
            return terapiaDAO.actualizarTerapia(terapia);
        } finally {
            cerrarConexion();
        }
    }
    
    public Integer actualizarTerapiaInformeTerapeutico(Terapia terapia) throws Exception {
        try {
            abrirConexion();
            TerapiaDAO terapiaDAO = new TerapiaDAO(conexion);
            return terapiaDAO.actualizarTerapiaInformeTerapeutico(terapia);
        } finally {
            cerrarConexion();
        }
    }
    
    public Integer autorizarEvolucion(Terapia terapia) throws Exception {
        try {
            abrirConexion();
            TerapiaDAO terapiaDAO = new TerapiaDAO(conexion);
            return terapiaDAO.autorizarEvolucion(terapia);
        } finally {
            cerrarConexion();
        }
    }

    public Integer guardarTerapia(Terapia terapia) throws Exception {
        try {
            abrirConexion();
            TerapiaDAO terapiaDAO = new TerapiaDAO(conexion);
            return terapiaDAO.guardarTerapia(terapia);
        } finally {
            cerrarConexion();
        }
    }

    public Integer actualizarTerapiaCita(Terapia terapia, List<DetalleTerapia> detalleTerapia, Boolean primeraVez) throws Exception {
        try {
            abrirConexion();
            TerapiaDAO terapiaDAO = new TerapiaDAO(conexion);
            return terapiaDAO.actualizarTerapiaCita(terapia, detalleTerapia, primeraVez);
        } finally {
            cerrarConexion();
        }
    }
    
    public Terapia consultarTerapiaPorCita(Cita cita) throws Exception {
        try {
            abrirConexion();
            TerapiaDAO terapiaDAO = new TerapiaDAO(conexion);
            return terapiaDAO.consultarTerapiaPorCita(cita);
        } finally {
            cerrarConexion();
        }
    }
    
    public List<DetalleTerapia> consultarDetalleTerapiaPorCita(Terapia terapia) throws Exception {
        try {
            abrirConexion();
            TerapiaDAO terapiaDAO = new TerapiaDAO(conexion);
            return terapiaDAO.consultarDetalleTerapiaPorCita(terapia);
        } finally {
            cerrarConexion();
        }
    }

}
