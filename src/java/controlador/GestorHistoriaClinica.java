/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import bd.HistoriaClinicaDAO;
import java.util.List;
import modelo.Cita;

/**
 *
 * @author Andres
 */
public class GestorHistoriaClinica extends Gestor{

    public GestorHistoriaClinica() throws Exception {
        super();
    }
    
    
    public List<Cita> consultarCitasPaciente(String identificacion) throws Exception {
        try {
            abrirConexion();
            HistoriaClinicaDAO historiaClinicaDAO = new HistoriaClinicaDAO(conexion);
            return historiaClinicaDAO.consultarCitasPaciente(identificacion);
        } finally {
            cerrarConexion();
        }
    }
    
}
