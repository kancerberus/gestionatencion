/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import bd.DiagnosticoDAO;
import java.util.ArrayList;
import modelo.Diagnostico;

/**
 *
 * @author Andres
 */
public class GestorDiagnostico extends Gestor{
    public GestorDiagnostico() throws Exception{
        super();
    }
    
    public ArrayList<Diagnostico> listarDiagnosticos(String patron) throws Exception{
       try {
            abrirConexion();
            DiagnosticoDAO diagnosticoDAO = new DiagnosticoDAO(conexion);
            return diagnosticoDAO.listarDiagnosticos(patron);
        } finally {
            cerrarConexion();
        }
    }
    
}
