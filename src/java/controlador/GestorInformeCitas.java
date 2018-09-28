/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import bd.InformeCitasDAO;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author Andres
 */
public class GestorInformeCitas extends Gestor {
    public GestorInformeCitas() throws Exception {
        super();
    }

    public ArrayList<String> consultarInformeCitas(String fecha_ini, String fecha_fin) throws Exception {       
        try {
            abrirConexion();            
            InformeCitasDAO informeCitasDAO;                        
            informeCitasDAO = new InformeCitasDAO(conexion);
            return informeCitasDAO.ConsultaInformeCitas(fecha_ini,fecha_fin);
        } finally {
            cerrarConexion();
        }        
    }

}


