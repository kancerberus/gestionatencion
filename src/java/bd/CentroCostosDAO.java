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
import modelo.CentroCostos;

/**
 *
 * @author Andres
 */
public class CentroCostosDAO {

    private Connection conexion;

    public CentroCostosDAO(Connection conexion) {
        this.conexion = conexion;
    }
    
    public ArrayList<CentroCostos> listarCentroCostos() throws SQLException {
        CentroCostos centroCostos;
        ArrayList<CentroCostos> listaCentroCostos = new ArrayList<>();
        ResultSet rs;
        Consulta consulta = null;
        try {
            consulta = new Consulta(getConexion());
            String sql
                    = " select "
                    + " codigo,nombre,direccion "                   
                    + " from "
                    + " centro_costos;";

            rs = consulta.ejecutar(sql);

            while (rs.next()) {
                centroCostos = new CentroCostos();
                centroCostos.setCodigo(rs.getString("codigo"));
                centroCostos.setNombre(rs.getString("nombre"));
                centroCostos.setDireccion(rs.getString("direccion"));
                
                listaCentroCostos.add(centroCostos);
            }
            return listaCentroCostos;

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
