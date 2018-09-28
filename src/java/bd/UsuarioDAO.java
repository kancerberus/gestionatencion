/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bd;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import modelo.Menu;
import modelo.Perfil;
import modelo.SubMenu;
import modelo.Usuario;

/**
 *
 * @author Andres
 */
public class UsuarioDAO {

    private Connection conexion;

    public UsuarioDAO(Connection conexion) {
        this.conexion = conexion;
    }

    public Usuario validarUsuario(String usuario, String clave) throws SQLException {
        Consulta consulta = null;
        ResultSet rs;
        String sql;
        Usuario u = null;
        Perfil p = null;
        try {
            consulta = new Consulta(getConexion());
            sql = "select u.usuario,u.nombre,u.id,u.cod_perfil,p.nombre perfil from "
                    + " usuarios u "
                    + " inner join  perfiles p using (cod_perfil) "
                    + " where usuario='" + usuario.trim() + "' and clave=md5('" + clave.trim() + "');";
            rs = consulta.ejecutar(sql);
            if (rs.next()) {
                u = new Usuario();
                u.setUsuario(rs.getString("usuario"));
                u.setNombre(rs.getString("nombre"));
                u.setId(rs.getString("id"));
                p = new Perfil();
                p.setCodigo(rs.getInt("cod_perfil"));
                p.setNombre(rs.getString("perfil"));
                u.setPerfil(p);
            }
            return u;
        } catch (SQLException ex) {
            throw ex;
        } finally {
            consulta.desconectar();
        }
    }

    public List<Menu> listarOpcionesMenu(String usuario) throws SQLException {
        Consulta consulta = null;
        ResultSet rs;
        String sql, menuAnt = "";
        List<Menu> listaMenu = new ArrayList<>();
        try {
            Menu m;
            consulta = new Consulta(getConexion());
            sql = " select "
                    + " m.nombre nombre_menu,s.nombre nombre_submenu,imagen,s.opcion nombre_opcion"
                    + " from "
                    + " usuarios u "
                    + " inner join rel_perfil_submenu r using (cod_perfil) "
                    + " inner join menu m using (cod_menu) "
                    + " inner join submenu s using (cod_menu,cod_submenu) "
                    + " where "
                    + " usuario='" + usuario + "' "
                    + " order by m.orden,s.orden ";
            rs = consulta.ejecutar(sql);
            if (rs.next()) {

                SubMenu sm = new SubMenu();
                sm.setEtiqueta(rs.getString("nombre_submenu"));
                sm.setImagen(rs.getString("imagen"));
                sm.setOpcion(rs.getString("nombre_opcion"));

                m = new Menu();
                m.setEtiqueta(rs.getString("nombre_menu"));
                m.getListaSubMenu().add(sm);
                menuAnt = rs.getString("nombre_menu");

                while (rs.next()) {
                    sm = new SubMenu();
                    sm.setEtiqueta(rs.getString("nombre_submenu"));
                    sm.setImagen(rs.getString("imagen"));
                    sm.setOpcion(rs.getString("nombre_opcion"));
                    //si pertenece al mismo menu
                    if (rs.getString("nombre_menu").equalsIgnoreCase(menuAnt)) {
                        m.getListaSubMenu().add(sm);
                    } else {
                        listaMenu.add(m);
                        m = new Menu();
                        m.setEtiqueta(rs.getString("nombre_menu"));
                        m.getListaSubMenu().add(sm);
                        menuAnt = rs.getString("nombre_menu");
                    }
                }
                listaMenu.add(m);
            }

            return listaMenu;
        } catch (SQLException ex) {
            throw ex;
        } finally {
            consulta.desconectar();
        }
    }

    public ArrayList<Usuario> listarUsuarios() throws SQLException {
        Usuario usuario;
        Perfil perfil;
        ArrayList<Usuario> listaUsuarios = new ArrayList<>();
        ResultSet rs;
        Consulta consulta = null;
        try {
            consulta = new Consulta(getConexion());
            String sql
                    = " SELECT u.usuario, u.nombre, u.cod_perfil, p.nombre nombre_perfil,u.id "
                    + " FROM usuarios u "
                    + " inner join perfiles p using (cod_perfil) ";

            rs = consulta.ejecutar(sql);

            while (rs.next()) {
                usuario = new Usuario();
                perfil = new Perfil();
                usuario.setUsuario(rs.getString("usuario"));
                usuario.setNombre(rs.getString("nombre"));

                perfil.setCodigo(rs.getInt("cod_perfil"));
                perfil.setNombre(rs.getString("nombre_perfil"));

                usuario.setPerfil(perfil);
                usuario.setId(rs.getString("id"));

                listaUsuarios.add(usuario);
            }
            return listaUsuarios;

        } catch (SQLException ex) {
            throw ex;
        } finally {
            consulta.desconectar();
        }
    }

    public Integer guardarUsuario(Usuario usuario, Boolean existe) throws SQLException {
        ResultSet rs;
        Consulta consulta = null;
        String sql = null;
        Integer resultado, codigo = 1;
        try {
            consulta = new Consulta(getConexion());
            if (existe) {
                sql
                        = " UPDATE usuarios "
                        + " SET clave=md5('" + usuario.getClave() + "'), "
                        + " nombre='" + usuario.getNombre() + "', cod_perfil=" + usuario.getPerfil().getCodigo() + ", id='" + usuario.getId() + "' "
                        + " WHERE usuario='" + usuario.getUsuario() + "' ";
            } else {
                sql
                        = " INSERT INTO usuarios( "
                        + " usuario, clave, nombre, cod_perfil, id) "
                        + " VALUES ('" + usuario.getUsuario() + "', md5('" + usuario.getClave() + "'),"
                        + " '" + usuario.getNombre() + "', " + usuario.getPerfil().getCodigo() + ", '" + usuario.getId() + "') ";

            }
            resultado = consulta.actualizar(sql);
            return resultado;

        } catch (SQLException ex) {
            throw ex;
        } finally {
            consulta.desconectar();
        }
    }

    public Usuario consultarUsuarioPorLogin(String login) throws SQLException {
        Usuario usuario = null;
        Perfil perfil = null;

        ResultSet rs;
        Consulta consulta = null;
        try {
            consulta = new Consulta(getConexion());
            String sql
                    = " SELECT usuario, clave, nombre, cod_perfil, id "
                    + " FROM usuarios where usuario='" + login + "'";

            rs = consulta.ejecutar(sql);

            if (rs.next()) {
                usuario = new Usuario();
                usuario.setUsuario(rs.getString("usuario"));
                usuario.setNombre(rs.getString("nombre"));
                perfil = new Perfil();
                perfil.setCodigo(rs.getInt("cod_perfil"));
                usuario.setPerfil(perfil);
                usuario.setId(rs.getString("id"));

            }
            return usuario;

        } catch (SQLException ex) {
            throw ex;
        } finally {
            consulta.desconectar();
        }
    }

    /**
     * @return the conexion
     */
    public Connection getConexion() {
        return conexion;
    }

    /**
     * @param conexion the conexion to set
     */
    public void setConexion(Connection conexion) {
        this.conexion = conexion;
    }
}
