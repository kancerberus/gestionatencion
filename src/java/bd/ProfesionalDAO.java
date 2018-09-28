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
import modelo.Profesional;

/**
 *
 * @author Andres
 */
public class ProfesionalDAO {

    private Connection conexion;

    public ProfesionalDAO(Connection conexion) {
        this.conexion = conexion;
    }

    public ArrayList<Profesional> listarProfesionalesPatron(String patron) throws SQLException {
        Profesional profesional;
        ArrayList<Profesional> listaProfesionales = new ArrayList<>();
        ResultSet rs;
        Consulta consulta = null;
        try {
            consulta = new Consulta(getConexion());
            String sql = "select cedula,nombre from profesionales where nombre ilike '%" + patron + "%';";

            rs = consulta.ejecutar(sql);

            while (rs.next()) {
                profesional = new Profesional();
                profesional.setCedula(rs.getString("cedula"));
                profesional.setNombre(rs.getString("nombre"));

                listaProfesionales.add(profesional);

            }
            return listaProfesionales;

        } catch (SQLException ex) {
            throw ex;
        } finally {
            consulta.desconectar();
        }
    }

    public Profesional consultarProfesionalPorNombre(String nombre) throws SQLException {
        Profesional profesional = new Profesional();

        ResultSet rs;
        Consulta consulta = null;
        try {
            consulta = new Consulta(getConexion());
            String sql = "select cedula,nombre from profesionales where nombre = '" + nombre + "';";
            rs = consulta.ejecutar(sql);
            if (rs.next()) {
                profesional = new Profesional();
                profesional.setCedula(rs.getString("cedula"));
                profesional.setNombre(rs.getString("nombre"));
            }
            return profesional;

        } catch (SQLException ex) {
            throw ex;
        } finally {
            consulta.desconectar();
        }
    }

    public List<Profesional> consultarProfesionalesPorEspecialidad(String codigoEspecialidad) throws SQLException {
        List<Profesional> listaProfesionales = new ArrayList<>();
        Profesional profesional = new Profesional();

        ResultSet rs;
        Consulta consulta = null;
        try {
            consulta = new Consulta(getConexion());
            String sql
                    = //                    " select "
                    //                    + " p.cedula,p.nombre,min((fecha||' '||hora)::timestamp) proximaFranja"
                    //                    + " from "
                    //                    + " rel_especialidades_profesional rel "
                    //                    + " inner join profesionales p on (rel.fk_cedula_profesional=p.cedula) "
                    //                    + " left join agenda a on (rel.fk_cedula_profesional=a.cedula_profesional and rel.fk_codigo_especialidad=a.codigo_especialidad and fecha>=current_date and codigo_cita is null) "
                    //                    + " where fk_codigo_especialidad='" + codigoEspecialidad + "' "
                    //                    + " group by 1,2 "
                    //                    + " order by min((fecha||' '||hora)::timestamp) asc; ";
                    " select p.cedula,p.nombre from "
                    + " rel_especialidades_profesional rel "
                    + " inner join profesionales p on (rel.fk_cedula_profesional=p.cedula) "
                    + " where p.activo and fk_codigo_especialidad='" + codigoEspecialidad + "'";
            rs = consulta.ejecutar(sql);
            while (rs.next()) {
                profesional = new Profesional();
                profesional.setCedula(rs.getString("cedula"));
                profesional.setNombre(rs.getString("nombre"));
                //profesional.setProximaFranja(rs.getTimestamp("proximaFranja"));
                listaProfesionales.add(profesional);
            }
            return listaProfesionales;

        } catch (SQLException ex) {
            throw ex;
        } finally {
            consulta.desconectar();
        }
    }

    public List<Profesional> consultarDisponibilidadEspecialidadCantidadFranjas(String codigoEspecialidad, Integer cantidadFranjas) throws SQLException {
        List<Profesional> listaProfesionales = new ArrayList<>();
        Profesional profesional = new Profesional();

        ResultSet rs;
        Consulta consulta = null;
        try {
            consulta = new Consulta(getConexion());
            String sql
                    = " select cedula_profesional,nombre,min(fecha_min) proxima_franja "
                    + " from "
                    + " ( "
                    + " select cedula_profesional,grupo,min((fecha||' '||hora)::timestamp) fecha_min,count(*) "
                    + " from "
                    + "     ( "
                    + "     select  "
                    + "     *,row_number() over (order by cedula_profesional,fecha,hora) numeracion "
                    + "     from agenda  "
                    + "     where  "
                    + "     fecha>=current_date and "
                    + "     codigo_cita is null and codigo_especialidad='" + codigoEspecialidad + "' order by cedula_profesional,fecha,hora "
                    + "     ) agenda "
                    + " inner join "
                    + " ( "
                    + " select  "
                    + " row_number() over (order by separador) grupo,case when row_number() over (order by separador)=1 then 1 else lag(separador+1) over(order by separador) end tope_inf, "
                    + " separador tope_sup from  "
                    + " 	( "
                    + " 	select *, "
                    + " 	case when  "
                    + " 	(lead(hora) over(order by cedula_profesional,fecha,hora)) = (hora + (interval '1 minutes'*duracion))  "
                    + " 	and "
                    + " 	(lead(cedula_profesional) over(order by cedula_profesional,fecha,hora))=cedula_profesional "
                    + " 	then true else false end siguiente, "
                    + " 	row_number() over(order by cedula_profesional,fecha,hora) separador "
                    + " 	from agenda  "
                    + " 	where  "
                    + " 	fecha>=current_date and "
                    + " 	codigo_cita is null and codigo_especialidad='" + codigoEspecialidad + "' order by cedula_profesional,fecha,hora "
                    + " 	) sc "
                    + " where not siguiente order by separador "
                    + " ) intervalos on (numeracion between tope_inf and tope_sup) "
                    + " group by 1,2 having count(*) >= " + cantidadFranjas + " "
                    + " ) grupos "
                    + " inner join profesionales p on (p.cedula=grupos.cedula_profesional) "
                    + " group by 1,2 ";
            rs = consulta.ejecutar(sql);
            while (rs.next()) {
                profesional = new Profesional();
                profesional.setCedula(rs.getString("cedula_profesional"));
                profesional.setNombre(rs.getString("nombre"));
                profesional.setProximaFranja(rs.getTimestamp("proxima_franja"));
                listaProfesionales.add(profesional);
            }
            return listaProfesionales;

        } catch (SQLException ex) {
            throw ex;
        } finally {
            consulta.desconectar();
        }
    }

    public List<Profesional> listarProfesionalesEspecialidadPatron(String patron, String codigoEspecialidad) throws SQLException {
        List<Profesional> listaProfesionales = new ArrayList<>();
        Profesional profesional = new Profesional();

        ResultSet rs;
        Consulta consulta = null;
        try {
            consulta = new Consulta(getConexion());
            String sql
                    = " select p.cedula,p.nombre from "
                    + " rel_especialidades_profesional rel "
                    + " inner join profesionales p on (rel.fk_cedula_profesional=p.cedula) "
                    + " where p.activo and p.nombre ilike '%" + patron + "%' and fk_codigo_especialidad='" + codigoEspecialidad + "'";
            rs = consulta.ejecutar(sql);
            while (rs.next()) {
                profesional = new Profesional();
                profesional.setCedula(rs.getString("cedula"));
                profesional.setNombre(rs.getString("nombre"));
                listaProfesionales.add(profesional);
            }
            return listaProfesionales;

        } catch (SQLException ex) {
            throw ex;
        } finally {
            consulta.desconectar();
        }
    }

    public ArrayList<Profesional> listarProfesionales() throws SQLException {
        Profesional profesional;
        ArrayList<Profesional> listaProfesionales = new ArrayList<>();
        ResultSet rs;
        Consulta consulta = null;
        try {
            consulta = new Consulta(getConexion());
            String sql
                    = " SELECT cedula, nombre, direccion, telefono, activo "
                    + " FROM profesionales order by nombre";

            rs = consulta.ejecutar(sql);

            while (rs.next()) {
                profesional = new Profesional();
                profesional.setCedula(rs.getString("cedula"));
                profesional.setNombre(rs.getString("nombre"));
                profesional.setDireccion(rs.getString("direccion"));
                profesional.setTelefono(rs.getString("telefono"));
                profesional.setActivo(rs.getBoolean("activo"));

                listaProfesionales.add(profesional);
            }
            return listaProfesionales;

        } catch (SQLException ex) {
            throw ex;
        } finally {
            consulta.desconectar();
        }
    }

    public Integer guardarProfesional(Profesional profesional, Boolean existe) throws SQLException {
        ResultSet rs;
        Consulta consulta = null;
        String sql = null;
        Integer resultado, codigo = 1;
        try {
            consulta = new Consulta(getConexion());
            if (existe) {
                sql
                        = " UPDATE profesionales "
                        + " SET nombre='" + profesional.getNombre() + "', direccion='" + profesional.getDireccion() + "', "
                        + " telefono='" + profesional.getTelefono() + "',activo= " + profesional.getActivo().toString()
                        + " WHERE cedula='" + profesional.getCedula() + "' ";
            } else {
                sql
                        = " INSERT INTO profesionales( "
                        + " cedula, nombre, direccion, telefono) "
                        + " VALUES "
                        + " ('" + profesional.getCedula() + "', '" + profesional.getNombre() + "',"
                        + " '" + profesional.getDireccion() + "', '" + profesional.getTelefono() + "') ";
            }
            resultado = consulta.actualizar(sql);
            return resultado;

        } catch (SQLException ex) {
            throw ex;
        } finally {
            consulta.desconectar();
        }
    }

    public Profesional consultarProfesionalPorCedula(String cedula) throws SQLException {
        Profesional profesional = null;

        ResultSet rs;
        Consulta consulta = null;
        try {
            consulta = new Consulta(getConexion());
            String sql
                    = " select "
                    + " cedula,nombre,direccion, "
                    + " telefono,activo "
                    + " from "
                    + " profesionales where cedula = '" + cedula + "';";

            rs = consulta.ejecutar(sql);

            if (rs.next()) {
                profesional = new Profesional();
                profesional.setCedula(rs.getString("cedula"));
                profesional.setNombre(rs.getString("nombre"));
                profesional.setDireccion(rs.getString("direccion"));
                profesional.setTelefono(rs.getString("telefono"));
                profesional.setActivo(rs.getBoolean("activo"));

            }
            return profesional;

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
