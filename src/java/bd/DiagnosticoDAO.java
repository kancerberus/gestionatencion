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
import modelo.Diagnostico;

/**
 *
 * @author Andres
 */
public class DiagnosticoDAO {
    private Connection conexion;

    public DiagnosticoDAO(Connection conexion) {
        this.conexion = conexion;
    }
    
    public ArrayList<Diagnostico> listarDiagnosticos(String patron) throws SQLException {
        Diagnostico diagnostico;
        ArrayList<Diagnostico> listaDiagnosticos = new ArrayList<>();
        ResultSet rs;
        Consulta consulta = null;
        try {
            consulta = new Consulta(getConexion());
            String sql
                    = " select "
                    + "cod_diagnostico, nombre_diagnostico "
                    + " from "
                    + " diagnosticos where nombre_diagnostico like '%" + patron + "%';";

            rs = consulta.ejecutar(sql);

            while (rs.next()) {
                diagnostico = new Diagnostico();
                diagnostico.setCodigo_diagnostico(rs.getString("cod_diagnostico"));
                diagnostico.setNombre_diagostico(rs.getString("nombre_diagnostico"));
                listaDiagnosticos.add(diagnostico);
            }
            return listaDiagnosticos;

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
