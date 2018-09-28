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
import javax.faces.model.SelectItem;

/**
 *
 * @author Andres
 */
public class UtilidadesDAO {

    private Connection conexion;

    public UtilidadesDAO(Connection conexion) {
        this.conexion = conexion;
    }

    public List<SelectItem> listarCombo(String nombreLista, String modo) throws SQLException {
        SelectItem si = null;
        List<SelectItem> lista = new ArrayList<>();
        ResultSet rs;
        Consulta consulta = null;
        try {
            consulta = new Consulta(getConexion());
            String sql = "select"
                    + " value,label"
                    + " from"
                    + " lista l"
                    + " inner join detalle_lista dl on (l.codigo=dl.codigo_lista)"
                    + " where nombre = '" + nombreLista + "'";
            rs = consulta.ejecutar(sql);
            while (rs.next()) {
                if (modo.equalsIgnoreCase("VALUE")) {
                    si = new SelectItem(rs.getString("value"), rs.getString("label"));
                } else if (modo.equalsIgnoreCase("LABEL")) {
                    si = new SelectItem(rs.getString("label"), rs.getString("label"));
                } else if (modo.equalsIgnoreCase("COMBINADO")) {
                    si = new SelectItem(rs.getString("value"), rs.getString("value") + " - " + rs.getString("label"));
                }
                lista.add(si);
            }
            return lista;
        } catch (SQLException ex) {
            throw ex;
        } finally {
            consulta.desconectar();
        }

    }

    public String consultarValueCombo(String nombreLista, String label) throws SQLException {
        String value = "";
        ResultSet rs;
        Consulta consulta = null;
        try {
            consulta = new Consulta(getConexion());
            String sql = "select"
                    + " value"
                    + " from"
                    + " lista l"
                    + " inner join detalle_lista dl on (l.codigo=dl.codigo_lista)"
                    + " where nombre = '" + nombreLista + "' and label='" + label + "' ";
            rs = consulta.ejecutar(sql);
            while (rs.next()) {
                value = rs.getString("value");
            }
            return value;
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
