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
import modelo.Perfil;

/**
 *
 * @author Andres
 */
public class PerfilDAO {

    private Connection conexion;

    public PerfilDAO(Connection conexion) {
        this.conexion = conexion;
    }

    public ArrayList<Perfil> listarPerfiles() throws SQLException {
        Perfil perfil;
        ArrayList<Perfil> listaPerfiles = new ArrayList<>();
        ResultSet rs;
        Consulta consulta = null;
        try {
            consulta = new Consulta(getConexion());
            String sql
                    = " SELECT cod_perfil, nombre "
                    + " FROM perfiles";

            rs = consulta.ejecutar(sql);

            while (rs.next()) {
                perfil = new Perfil();
                perfil.setCodigo(rs.getInt("cod_perfil"));
                perfil.setNombre(rs.getString("nombre"));                

                listaPerfiles.add(perfil);
            }
            return listaPerfiles;

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
