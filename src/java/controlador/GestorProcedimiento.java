/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import bd.ProcedimientoDAO;
import java.util.List;
import javax.faces.model.SelectItem;
import modelo.Procedimiento;

/**
 *
 * @author Andres
 */
public class GestorProcedimiento extends Gestor {
    public GestorProcedimiento() throws Exception {
        super();
    }
    
    public List<Procedimiento> consultarProcedimientosPorEspecialidad(String codigoEspecialidad) throws Exception {
        try {
            abrirConexion();
            ProcedimientoDAO procedimientoDAO = new ProcedimientoDAO(conexion);
            return procedimientoDAO.consultarProcedimientosPorEspecialidad(codigoEspecialidad);
        } finally {
            cerrarConexion();
        }
    }
    
    public List<Procedimiento> consultarProcedimientosPorEspecialidadPerfil(String codigoEspecialidad, Integer codigoPerfil) throws Exception {
        try {
            abrirConexion();
            ProcedimientoDAO procedimientoDAO = new ProcedimientoDAO(conexion);
            return procedimientoDAO.consultarProcedimientosPorEspecialidadPerfil(codigoEspecialidad, codigoPerfil);
        } finally {
            cerrarConexion();
        }
    }
    public List<SelectItem> consultarTerapias() throws Exception {
        try {
            abrirConexion();
            ProcedimientoDAO procedimientoDAO = new ProcedimientoDAO(conexion);
            return procedimientoDAO.consultarTerapias();
        } finally {
            cerrarConexion();
        }
    }
    
}
