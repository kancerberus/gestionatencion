/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import bd.MunicipioDAO;
import java.util.ArrayList;
import modelo.Municipio;

/**
 *
 * @author Andres
 */
public class GestorMunicipio extends Gestor{
    public GestorMunicipio() throws Exception{
        super();
    }
    
    public ArrayList<Municipio> listarMunicipiosPatron(String patron) throws Exception{
       try {
            abrirConexion();
            MunicipioDAO municipioDAO = new MunicipioDAO(conexion);
            return municipioDAO.listarMunicipiosPatron(patron);
        } finally {
            cerrarConexion();
        }
    }
    
    public Municipio consultarMunicipioPorNombre(String nombre) throws Exception{
       try {
            abrirConexion();
            MunicipioDAO municipioDAO = new MunicipioDAO(conexion);
            return municipioDAO.consultarMunicipioPorNombre(nombre);
        } finally {
            cerrarConexion();
        }
    }
    
}
