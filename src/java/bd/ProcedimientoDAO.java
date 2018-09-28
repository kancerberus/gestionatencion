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
import modelo.Procedimiento;

/**
 *
 * @author Andres
 */
public class ProcedimientoDAO {

    private Connection conexion;

    public ProcedimientoDAO(Connection conexion) {
        this.conexion = conexion;
    }

    public List<Procedimiento> consultarProcedimientosPorEspecialidad(String codigoEspecialidad) throws SQLException {
        Procedimiento procedimiento;
        ArrayList<Procedimiento> listaProcedimientos = new ArrayList<>();
        ResultSet rs;
        Consulta consulta = null;
        try {
            consulta = new Consulta(getConexion());
            String sql
                    = " select p.codigo,p.nombre,p.tipo "
                    + " from"
                    + " procedimientos p"
                    + " inner join rel_procedimientos_especialidad r on (p.codigo=r.codigo_procedimiento)"
                    + " where"
                    + " r.codigo_especialidad = '" + codigoEspecialidad + "'";
            rs = consulta.ejecutar(sql);
            while (rs.next()) {
                procedimiento = new Procedimiento(rs.getString("codigo"), rs.getString("nombre"));
                procedimiento.setTipo(rs.getInt("tipo"));
                listaProcedimientos.add(procedimiento);
            }
            return listaProcedimientos;

        } catch (SQLException ex) {
            throw ex;
        } finally {
            consulta.desconectar();
        }
    }

    public List<Procedimiento> consultarProcedimientosPorEspecialidadPerfil(String codigoEspecialidad, Integer codigoPerfil) throws SQLException {
        Procedimiento procedimiento;
        ArrayList<Procedimiento> listaProcedimientos = new ArrayList<>();
        ResultSet rs;
        Consulta consulta = null;
        try {
            consulta = new Consulta(getConexion());
            String sql
                    = " select p.codigo,p.nombre,p.tipo "
                    + " from"
                    + " procedimientos p"
                    + " inner join rel_procedimientos_especialidad r on (p.codigo=r.codigo_procedimiento) "
                    + " inner join rel_perfil_procedimientos rpp on (p.codigo=rpp.cod_procedimiento) "
                    + " where"
                    + " r.codigo_especialidad = '" + codigoEspecialidad + "' "
                    + " and rpp.cod_perfil=" + codigoPerfil;
            rs = consulta.ejecutar(sql);
            while (rs.next()) {
                procedimiento = new Procedimiento(rs.getString("codigo"), rs.getString("nombre"));
                procedimiento.setTipo(rs.getInt("tipo"));
                listaProcedimientos.add(procedimiento);
            }
            return listaProcedimientos;

        } catch (SQLException ex) {
            throw ex;
        } finally {
            consulta.desconectar();
        }
    }

    public List<SelectItem> consultarTerapias() throws SQLException {
        SelectItem si;
        List<SelectItem> listaTerapias = new ArrayList<>();
        ResultSet rs;
        Consulta consulta = null;
        try {
            consulta = new Consulta(getConexion());
            String sql
                    = " select codigo,nombre from procedimientos where tipo=1 ";
            rs = consulta.ejecutar(sql);
            while (rs.next()) {
                si = new SelectItem(rs.getString("codigo"), rs.getString("nombre"));
                listaTerapias.add(si);
            }
            return listaTerapias;

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
