/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import modelo.Usuario;
import bd.UsuarioDAO;
import java.util.ArrayList;
import java.util.List;
import modelo.Menu;

/**
 *
 * @author Andres
 */
public class GestorUsuario extends Gestor {

    public GestorUsuario() throws Exception {
        super();
    }

    public Usuario validarUsuario(String usuario, String clave) throws Exception {
        try {
            abrirConexion();
            UsuarioDAO usuarioDAO = new UsuarioDAO(conexion);
            return usuarioDAO.validarUsuario(usuario, clave);
        } finally {
            cerrarConexion();
        }
    }

    public List<Menu> listarOpcionesMenu(String usuario) throws Exception {
        try {
            abrirConexion();
            UsuarioDAO usuarioDAO = new UsuarioDAO(conexion);
            return usuarioDAO.listarOpcionesMenu(usuario);
        } finally {
            cerrarConexion();
        }
    }
    
    public ArrayList<Usuario> listarUsuarios() throws Exception{
       try {
            abrirConexion();
            UsuarioDAO usuarioDAO = new UsuarioDAO(conexion);
            return usuarioDAO.listarUsuarios();
        } finally {
            cerrarConexion();
        }
    }
    
    public Usuario consultarUsuarioPorLogin(String login) throws Exception{
       try {
            abrirConexion();
            UsuarioDAO usuarioDAO = new UsuarioDAO(conexion);
            return usuarioDAO.consultarUsuarioPorLogin(login);
        } finally {
            cerrarConexion();
        }
    }
    
    public Integer guardarUsuario(Usuario usuario, Boolean existe) throws Exception{
       try {
            abrirConexion();
            UsuarioDAO usuarioDAO = new UsuarioDAO(conexion);
            return usuarioDAO.guardarUsuario(usuario, existe);
        } finally {
            cerrarConexion();
        }
    }
}
