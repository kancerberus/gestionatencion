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
import modelo.Entidad;

/**
 *
 * @author Andres
 */
public class EntidadDAO {

    private Connection conexion;

    public EntidadDAO(Connection conexion) {
        this.conexion = conexion;
    }

    public ArrayList<Entidad> listarEntidadesPatron(String patron) throws SQLException {
        Entidad entidad;
        ArrayList<Entidad> listaEntidades = new ArrayList<>();
        ResultSet rs;
        Consulta consulta = null;
        try {
            consulta = new Consulta(getConexion());
            String sql
                    = " select "
                    + " codigo,nombre,representante, "
                    + " direccion,telefono,nit "
                    + " from "
                    + " entidades where nombre ilike '%" + patron + "%';";

            rs = consulta.ejecutar(sql);

            while (rs.next()) {
                entidad = new Entidad();
                entidad.setCodigo(rs.getString("codigo"));
                entidad.setNombre(rs.getString("nombre"));
                entidad.setRepresentante(rs.getString("representante"));
                entidad.setDireccion(rs.getString("direccion"));
                entidad.setTelefono(rs.getString("telefono"));
                entidad.setNit(rs.getString("nit"));
                listaEntidades.add(entidad);
            }
            return listaEntidades;

        } catch (SQLException ex) {
            throw ex;
        } finally {
            consulta.desconectar();
        }
    }

    public ArrayList<Entidad> listarEntidades() throws SQLException {
        Entidad entidad;
        ArrayList<Entidad> listaEntidades = new ArrayList<>();
        ResultSet rs;
        Consulta consulta = null;
        try {
            consulta = new Consulta(getConexion());
            String sql
                    = " select "
                    + " codigo,nombre,representante, "
                    + " direccion,telefono,nit "
                    + " from "
                    + " entidades order by nombre";

            rs = consulta.ejecutar(sql);

            while (rs.next()) {
                entidad = new Entidad();
                entidad.setCodigo(rs.getString("codigo"));
                entidad.setNombre(rs.getString("nombre"));
                entidad.setRepresentante(rs.getString("representante"));
                entidad.setDireccion(rs.getString("direccion"));
                entidad.setTelefono(rs.getString("telefono"));
                entidad.setNit(rs.getString("nit"));
                listaEntidades.add(entidad);
            }
            return listaEntidades;

        } catch (SQLException ex) {
            throw ex;
        } finally {
            consulta.desconectar();
        }
    }

    public Entidad consultarEntidadPorNombre(String nombre) throws SQLException {
        Entidad entidad = null;

        ResultSet rs;
        Consulta consulta = null;
        try {
            consulta = new Consulta(getConexion());
            String sql
                    = " select "
                    + " codigo,nombre,representante, "
                    + " direccion,telefono,nit "
                    + " from "
                    + " entidades where nombre = '" + nombre + "';";

            rs = consulta.ejecutar(sql);

            if (rs.next()) {
                entidad = new Entidad();
                entidad.setCodigo(rs.getString("codigo"));
                entidad.setNombre(rs.getString("nombre"));
                entidad.setRepresentante(rs.getString("representante"));
                entidad.setDireccion(rs.getString("direccion"));
                entidad.setTelefono(rs.getString("telefono"));
                entidad.setNit(rs.getString("nit"));
            }
            return entidad;

        } catch (SQLException ex) {
            throw ex;
        } finally {
            consulta.desconectar();
        }
    }
    
    public Entidad consultarEntidadPorNit(String nit) throws SQLException {
        Entidad entidad = null;

        ResultSet rs;
        Consulta consulta = null;
        try {
            consulta = new Consulta(getConexion());
            String sql
                    = " select "
                    + " codigo,nombre,representante, "
                    + " direccion,telefono,nit "
                    + " from "
                    + " entidades where nit = '" + nit + "';";

            rs = consulta.ejecutar(sql);

            if (rs.next()) {
                entidad = new Entidad();
                entidad.setCodigo(rs.getString("codigo"));
                entidad.setNombre(rs.getString("nombre"));
                entidad.setRepresentante(rs.getString("representante"));
                entidad.setDireccion(rs.getString("direccion"));
                entidad.setTelefono(rs.getString("telefono"));
                entidad.setNit(rs.getString("nit"));
            }
            return entidad;

        } catch (SQLException ex) {
            throw ex;
        } finally {
            consulta.desconectar();
        }
    }

    public Integer guardarEntidad(Entidad entidad, Boolean existe) throws SQLException {
        ResultSet rs;
        Consulta consulta = null;
        String sql = null;
        Integer resultado, codigo = 1;
        try {
            consulta = new Consulta(getConexion());
            if (existe) {
                sql
                        = " UPDATE entidades "
                        + " SET nombre='" + entidad.getNombre() + "', representante='" + entidad.getRepresentante() + "', "
                        + " direccion='" + entidad.getDireccion() + "', telefono='" + entidad.getTelefono() + "',  "
                        + " nit='" + entidad.getNit() + "' "
                        + " WHERE codigo='" + entidad.getCodigo() + "' ";
            } else {
                sql = "select max(codigo::int)+1 codigo from entidades ";
                rs = consulta.ejecutar(sql);

                if (rs.next()) {
                    codigo = rs.getInt("codigo");
                }
                sql = " INSERT INTO entidades( "
                        + " codigo, nombre, representante, direccion, telefono, nit) "
                        + " VALUES ('" + codigo + "', '" + entidad.getNombre() + "', '" + entidad.getRepresentante() + "', '" + entidad.getDireccion() + "', '" + entidad.getTelefono() + "', '" + entidad.getNit() + "') ";

            }
            resultado = consulta.actualizar(sql);
            return resultado;
            
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
