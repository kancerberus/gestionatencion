/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;
import bd.AgendaDAO;
import java.util.Date;

/**
 *
 * @author Andres
 */
import java.util.List;
import modelo.FranjaAgenda;
import org.primefaces.model.ScheduleModel;

public class GestorAgenda extends Gestor{
    
    public GestorAgenda() throws Exception {
        super();
    }

    public Integer crearAgenda(List<FranjaAgenda> listaFranjasAgenda) throws Exception {
        try {
            abrirConexion();
            inicioTransaccion();
            AgendaDAO agendaDAO = new AgendaDAO(conexion);
            return agendaDAO.crearAgenda(listaFranjasAgenda);        
        } finally {
            finTransaccion();
            cerrarConexion();
        }
    }
    
    public List<FranjaAgenda> consultarAgenda(String cedulaProfesional, String codigoEspecialidad, Date fecha) throws Exception {
        try {
            abrirConexion();
            AgendaDAO agendaDAO = new AgendaDAO(conexion);
            return agendaDAO.consultarAgenda(cedulaProfesional, codigoEspecialidad, fecha);
        } finally {
            cerrarConexion();
        }
    }
    
    public List<FranjaAgenda> consultarAgendaReplicar(String cedulaProfesional, Date fecha) throws Exception {
        try {
            abrirConexion();
            AgendaDAO agendaDAO = new AgendaDAO(conexion);
            return agendaDAO.consultarAgendaReplicar(cedulaProfesional, fecha);
        } finally {
            cerrarConexion();
        }
    }
    
    public Integer guardarAgendaReplicar(ScheduleModel modeloFechas, List<FranjaAgenda> listaFranjas) throws Exception {
        try {
            abrirConexion();
            AgendaDAO agendaDAO = new AgendaDAO(conexion);
            return agendaDAO.guardarAgendaReplicar(modeloFechas, listaFranjas);
        } finally {
            cerrarConexion();
        }
    }
    
    public void eliminarAgenda(String cedulaProfesional, String codigoEspecialidad, ScheduleModel modelo, Boolean modoEliminar) throws Exception {
        try {
            abrirConexion();
            AgendaDAO agendaDAO = new AgendaDAO(conexion);
            agendaDAO.eliminarAgenda(cedulaProfesional, codigoEspecialidad, modelo, modoEliminar);
        } finally {
            cerrarConexion();
        }
    }
    
    public List<FranjaAgenda> consultarAgendaMes(String cedulaProfesional, String codigoEspecialidad, Date fecha) throws Exception {
        try {
            abrirConexion();
            AgendaDAO agendaDAO = new AgendaDAO(conexion);
            return agendaDAO.consultarAgendaMes(cedulaProfesional, codigoEspecialidad, fecha);
        } finally {
            cerrarConexion();
        }
    }
    
    public List<FranjaAgenda> consultarAgendaPorProfesional(String cedulaProfesional, Date fechaInicial, Date fechaFinal) throws Exception {
        try {
            abrirConexion();
            AgendaDAO agendaDAO = new AgendaDAO(conexion);
            return agendaDAO.consultarAgendaPorProfesional(cedulaProfesional, fechaInicial, fechaFinal);
        } finally {
            cerrarConexion();
        }
    }
    
}
