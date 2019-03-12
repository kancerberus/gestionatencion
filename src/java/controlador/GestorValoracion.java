/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import bd.ValoracionDAO;
import modelo.Valoracion;
import modelo.Diagnostico;

/**
 *
 * @author Andres
 */
public class GestorValoracion extends Gestor{

    public GestorValoracion() throws Exception {
        super();
    }
    
    public Integer guardarValoracion(Valoracion valoracion, Boolean enviaTerapia, String terapiasAutorizadas) throws Exception {
        try {
            abrirConexion();
            ValoracionDAO valoracionDAO = new ValoracionDAO(conexion);
            return valoracionDAO.guardarValoracion(valoracion, enviaTerapia, terapiasAutorizadas);
        } finally {
            cerrarConexion();
        }
    }
    
}
