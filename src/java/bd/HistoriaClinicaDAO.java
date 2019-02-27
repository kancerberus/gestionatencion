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
import modelo.Cita;
import modelo.Entidad;
import modelo.Especialidad;
import modelo.Objeto;
import modelo.Paciente;
import modelo.Procedimiento;
import modelo.Profesional;

/**
 *
 * @author Andres
 */
public class HistoriaClinicaDAO {
    
    private Connection conexion;

    public HistoriaClinicaDAO(Connection conexion) {
        this.conexion = conexion;
    }
    
    
    public List<Cita> consultarCitasPaciente(String identificacion) throws SQLException {
        Cita cita;
        Profesional pro;
        Especialidad e;
        Entidad ent;
        Paciente pac;
        List<Cita> listaCitas = new ArrayList<>();
        Consulta consulta = null;
        String sql;
        ResultSet rs;

        try {
            consulta = new Consulta(getConexion());
            sql
                    = " select "
                    + " c.codigo codigo_cita,c.id_paciente, "
                    + " (pac.nombre || ' ' || pac.segundo_nombre) nombre_paciente, "
                    + " pac.fecha_nacimiento, "
                    + " ((current_date-pac.fecha_nacimiento)/365)::integer edad, "
                    + " l_sexo.label sexo, "
                    + " l_estado_civil.label estado_civil, "
                    + " pac.ocupacion, pac.grado_escolar, ent.nombre nombre_entidad,"
                    + " l_tipo_afiliacion.label tipo_afiliacion, "
                    + " l_tipo_identificacion.label tipo_identificacion, "
                    + " pac.direccion1, pac.telefono1,"
                    + " c.fecha,c.hora,c.codigo_especialidad,e.nombre nombre_especialidad, "
                    + " c.id_profesional, pro.nombre nombre_profesional, "
                    + " l_estados_cita.value codigo_estado,l_estados_cita.label nombre_estado "
                    + " from "
                    + " citas c "
                    + " inner join especialidades e on (e.codigo=c.codigo_especialidad) "
                    + " inner join profesionales pro on (pro.cedula=c.id_profesional) "
                    + " inner join pacientes pac on (pac.identificacion=c.id_paciente) "
                    + " left join entidades ent ON (ent.codigo=pac.entidad) "
                    + " left join (lista l "
                    + " inner join detalle_lista dl on (l.codigo=dl.codigo_lista and l.nombre='ESTADOS_CITA') ) l_estados_cita on (l_estados_cita.value=c.estado) "
                    + " left join (lista l  "
                    + " inner join detalle_lista dl on (l.codigo=dl.codigo_lista and l.nombre='SEXO') ) l_sexo on (l_sexo.value=pac.sexo) "
                    + " left join (lista l  "
                    + " inner join detalle_lista dl on (l.codigo=dl.codigo_lista and l.nombre='ESTADO_CIVIL') ) l_estado_civil on (l_estado_civil.value=pac.estado_civil) "
                    + " left join (lista l "
                    + " inner join detalle_lista dl on (l.codigo=dl.codigo_lista and l.nombre='TIPO_AFILIACION') ) l_tipo_afiliacion on (l_tipo_afiliacion.value=pac.tipo_afiliacion) "
                    + " left join (lista l "
                    + " inner join detalle_lista dl on (l.codigo=dl.codigo_lista and l.nombre='TIPO_IDENTIFICACION') ) l_tipo_identificacion on (l_tipo_identificacion.value=pac.tipo_identificacion) "
                    + " where "
                    //+ " id_profesional='82' and fecha=current_date and l_estados_cita.label in ('Programada','Paciente en sala') "
                    + " c.id_paciente='" + identificacion + "' "
                    + " order by c.fecha asc,c.hora asc ";
            rs = consulta.ejecutar(sql);
            while (rs.next()) {
                pac = new Paciente();
                pac.setIdentificacion(rs.getString("id_paciente"));
                pac.setNombre(rs.getString("nombre_paciente"));
                pac.setFechaNacimiento(rs.getDate("fecha_nacimiento"));
                pac.setEdad(rs.getString("edad"));
                pac.setSexo(rs.getString("sexo"));
                pac.setEstadoCivil(rs.getString("estado_civil"));
                pac.setOcupacion(rs.getString("ocupacion"));
                pac.setGradoEscolar(rs.getString("grado_escolar"));
                pac.setTipoAfiliacion(rs.getString("tipo_afiliacion"));
                pac.setTipoIdentificacion(rs.getString("tipo_identificacion"));
                pac.setDireccion1(rs.getString("direccion1"));
                pac.setTelefono1(rs.getString("telefono1"));

                ent = new Entidad();
                ent.setNombre(rs.getString("nombre_entidad"));
                pac.setEntidad(ent);

                cita = new Cita();
                pro = new Profesional();
                e = new Especialidad();

                pro.setCedula(rs.getString("id_profesional"));
                pro.setNombre(rs.getString("nombre_profesional"));
                e.setCodigo(rs.getString("codigo_especialidad"));
                e.setNombre(rs.getString("nombre_especialidad"));

                cita.setCodigo(rs.getInt("codigo_cita"));
                cita.setPaciente(pac);
                cita.setFecha(rs.getDate("fecha"));
                cita.setHora(rs.getTime("hora"));
                cita.setEspecialidad(e);
                cita.setProfesional(pro);
                cita.setEstado(new Objeto(rs.getString("codigo_estado"), rs.getString("nombre_estado")));

                listaCitas.add(cita);
            }
            
            for (Cita c : listaCitas) {
                sql = "select p.*,r.fecha,r.hora "
                        + " from"
                        + " citas c "
                        + " inner join rel_procedimientos_cita r on (c.codigo = r.codigo_cita) "
                        + " inner join procedimientos p on (r.codigo_procedimiento=p.codigo)"
                        + " where c.codigo = '" + c.getCodigo() + "' "
                        + " order by c.hora asc";
                rs = consulta.ejecutar(sql);
                while (rs.next()) {
                    c.getListaProcedimientos().add(new Procedimiento(rs.getString("codigo"), rs.getString("nombre"), rs.getDate("fecha"), rs.getTime("hora"), rs.getInt("tipo")));
                }
            }

            return listaCitas;
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
