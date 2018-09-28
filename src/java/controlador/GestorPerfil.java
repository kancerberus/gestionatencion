/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.util.List;
import modelo.Perfil;
import bd.PerfilDAO;

/**
 *
 * @author Andres
 */
public class GestorPerfil extends Gestor {

    public GestorPerfil() throws Exception {
        super();
    }

    public List<Perfil> listarPerfiles() throws Exception {
        try {
            abrirConexion();
            PerfilDAO perfilDAO = new PerfilDAO(conexion);
            return perfilDAO.listarPerfiles();
        } finally {
            cerrarConexion();
        }
    }
}
