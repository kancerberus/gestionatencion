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
import modelo.Especialidad;

/**
 *
 * @author Andres
 */
public class EspecialidadDAO {

    private Connection conexion;

    public EspecialidadDAO(Connection conexion) {
        this.conexion = conexion;
    }

    public ArrayList<Especialidad> listarEspecialidadesPatron(String patron) throws SQLException {
        Especialidad especialidad;
        ArrayList<Especialidad> listaEspecialidades = new ArrayList<>();
        ResultSet rs;
        Consulta consulta = null;
        try {
            consulta = new Consulta(getConexion());
            String sql = "select codigo,nombre from especialidades where nombre ilike '%" + patron + "%';";

            rs = consulta.ejecutar(sql);

            while (rs.next()) {
                especialidad = new Especialidad();
                especialidad.setCodigo(rs.getString("codigo"));
                especialidad.setNombre(rs.getString("nombre"));

                listaEspecialidades.add(especialidad);

            }
            return listaEspecialidades;

        } catch (SQLException ex) {
            throw ex;
        } finally {
            consulta.desconectar();
        }
    }
    
    public ArrayList<Especialidad> listarEspecialidadesProfesionalPatron(String patron, String cedula) throws SQLException {
        Especialidad especialidad;
        ArrayList<Especialidad> listaEspecialidades = new ArrayList<>();
        ResultSet rs;
        Consulta consulta = null;
        try {
            consulta = new Consulta(getConexion());
            String sql = 
                        " select e.codigo,e.nombre from " +
                        " especialidades e " +
                        " inner join rel_especialidades_profesional r on (e.codigo=r.fk_codigo_especialidad) " +
                        " where " +
                        " e.nombre ilike '%" + patron + "%' and " +
                        " r.fk_cedula_profesional = '" + cedula + "' ";
            rs = consulta.ejecutar(sql);

            while (rs.next()) {
                especialidad = new Especialidad();
                especialidad.setCodigo(rs.getString("codigo"));
                especialidad.setNombre(rs.getString("nombre"));
                listaEspecialidades.add(especialidad);
            }
            return listaEspecialidades;

        } catch (SQLException ex) {
            throw ex;
        } finally {
            consulta.desconectar();
        }
    }
    
    public Especialidad consultarEspecialidadPorNombre(String nombre) throws SQLException {
        Especialidad especialidad = new Especialidad();
        
        ResultSet rs;
        Consulta consulta = null;
        try {
            consulta = new Consulta(getConexion());
            String sql = "select codigo,nombre from especialidades where nombre = '" + nombre + "';";
            rs = consulta.ejecutar(sql);
            if (rs.next()) {
                especialidad = new Especialidad();
                especialidad.setCodigo(rs.getString("codigo"));
                especialidad.setNombre(rs.getString("nombre"));
            }
            return especialidad;

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
