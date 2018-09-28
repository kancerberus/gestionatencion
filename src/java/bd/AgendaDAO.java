/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bd;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.Entidad;
import modelo.Especialidad;
import modelo.FranjaAgenda;
import modelo.Paciente;
import modelo.Procedimiento;
import modelo.Profesional;
import org.primefaces.model.ScheduleEvent;
import org.primefaces.model.ScheduleModel;

/**
 *
 * @author Andres
 */
public class AgendaDAO {

    private Connection conexion;

    public AgendaDAO(Connection conexion) {
        this.conexion = conexion;
    }

    public Integer crearAgenda(List<FranjaAgenda> listaFranjasAgenda) throws SQLException {

        Consulta consulta = null;
        String sql;
        SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat formatoHora = new SimpleDateFormat("HH:mm:ss");

        try {
            consulta = new Consulta(getConexion());
            for (FranjaAgenda fa : listaFranjasAgenda) {
                sql = "INSERT INTO agenda(cedula_profesional, codigo_especialidad, fecha, hora, duracion) "
                        + " VALUES ("
                        + "'" + fa.getProfesional().getCedula() + "',"
                        + fa.getEspecialidad().getCodigo() + ","
                        + "'" + formatoFecha.format(fa.getFechaHora()) + "',"
                        + "'" + formatoHora.format(fa.getFechaHora()) + "',"
                        + fa.getDuracion()
                        + ");";
                consulta.actualizar(sql);
            }
            return 1;
        } catch (SQLException ex) {
            throw ex;
        } finally {
            consulta.desconectar();
        }
    }

    public List<FranjaAgenda> consultarAgenda(String cedulaProfesional, String codigoEspecialidad, Date fecha) throws SQLException {
        Consulta consulta = null;
        ResultSet rs;
        SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat formatoHoraFecha = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        List<FranjaAgenda> listaFa = new ArrayList<>();
        try {
            consulta = new Consulta(getConexion());
            String sql
                    = " SELECT "
                    + " p.nombre||(case when character_length(coalesce(p.segundo_nombre,''))=0 then '' else ' '||p.segundo_nombre end)||(case when character_length(coalesce(p.primer_apellido,''))=0 then '' else ' '||p.primer_apellido end)||(case when character_length(coalesce(p.segundo_apellido,''))=0 then '' else ' '||p.segundo_apellido end) nombre_completo, "
                    + " a.cedula_profesional, a.codigo_especialidad, e.nombre nombre_especialidad, a.fecha, a.hora, a.duracion, a.codigo_cita,"
                    + " p.identificacion id_paciente,p.nombre nombre_paciente,pro.codigo codigo_procedimiento,pro.nombre nombre_procedimiento "
                    + " FROM agenda a "
                    + " inner join especialidades e ON (a.codigo_especialidad = e.codigo) "
                    + " left join citas c ON (c.codigo = a.codigo_cita) "
                    + " left join pacientes p ON (c.id_paciente = p.identificacion) "
                    + " left join rel_procedimientos_cita rpc ON (a.codigo_cita=rpc.codigo_cita and a.fecha=rpc.fecha and a.hora=rpc.hora) "
                    + " left join procedimientos pro ON (rpc.codigo_procedimiento=pro.codigo)"
                    + " WHERE "
                    + " a.cedula_profesional = '" + cedulaProfesional + "' AND "
                    + " a.codigo_especialidad= '" + codigoEspecialidad + "' AND "
                    + " a.fecha = '" + formatoFecha.format(fecha) + "'"
                    + " ORDER BY "
                    + " a.hora ASC";
            rs = consulta.ejecutar(sql);

            while (rs.next()) {
                FranjaAgenda fa = new FranjaAgenda();
                Especialidad e = new Especialidad();
                Profesional p = new Profesional();
                Paciente pac = new Paciente();
                Procedimiento pro = new Procedimiento();
                e.setCodigo(rs.getString("codigo_especialidad"));
                e.setNombre(rs.getString("nombre_especialidad"));
                p.setCedula(rs.getString("cedula_profesional"));
                pac.setIdentificacion(rs.getString("id_paciente"));
                pac.setNombre(rs.getString("nombre_paciente"));
                pac.setNombreCompleto(rs.getString("nombre_completo"));
                fa.setProcedimiento(pro);
                if (rs.getString("codigo_procedimiento") != null) {
                    fa.setProcedimiento(new Procedimiento(rs.getString("codigo_procedimiento"), rs.getString("nombre_procedimiento")));
                }
                fa.setEspecialidad(e);
                fa.setProfesional(p);
                fa.setPaciente(pac);
                fa.setFechaHora(formatoHoraFecha.parse(rs.getString("fecha") + " " + rs.getString("hora")));
                fa.setCodCita(rs.getString("codigo_cita"));
                fa.setDuracion(rs.getString("duracion"));

                listaFa.add(fa);
            }
        } catch (SQLException ex) {
            throw ex;
        } catch (ParseException ex) {
            Logger.getLogger(AgendaDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            consulta.desconectar();
        }
        return listaFa;
    }

    public List<FranjaAgenda> consultarAgendaMes(String cedulaProfesional, String codigoEspecialidad, Date fecha) throws SQLException {
        Consulta consulta = null;
        ResultSet rs;
        SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat formatoHoraFecha = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        List<FranjaAgenda> listaFa = new ArrayList<>();
        try {
            consulta = new Consulta(getConexion());
            String sql
                    = " SELECT a.cedula_profesional, a.codigo_especialidad, e.nombre nombre_especialidad, a.fecha, '00:00:00'::time hora , max(a.codigo_cita) codigo_cita "
                    + " FROM agenda a "
                    + " inner join especialidades e ON (a.codigo_especialidad = e.codigo) "
                    + " left join citas c ON (c.codigo = a.codigo_cita) "
                    + " WHERE "
                    + " a.cedula_profesional = '" + cedulaProfesional + "' AND "
                    + " a.codigo_especialidad= '" + codigoEspecialidad + "' AND "
                    + " a.fecha between "
                    + " (date_part('year','" + formatoFecha.format(fecha) + "'::date)::varchar||'-'||date_part('month','" + formatoFecha.format(fecha) + "'::date)::varchar||'-01')::date and "
                    + " ((date_part('year','" + formatoFecha.format(fecha) + "'::date)::varchar||'-'||date_part('month','" + formatoFecha.format(fecha) + "'::date)::varchar||'-01')::date+interval '1 month'-interval '1 day')::date "
                    + " group by 1,2,3,4,5 "
                    + " ORDER BY "
                    + " a.fecha ASC ";
            rs = consulta.ejecutar(sql);

            while (rs.next()) {
                FranjaAgenda fa = new FranjaAgenda();
                Especialidad e = new Especialidad();
                Profesional p = new Profesional();

                Procedimiento pro = new Procedimiento();
                e.setCodigo(rs.getString("codigo_especialidad"));
                e.setNombre(rs.getString("nombre_especialidad"));
                p.setCedula(rs.getString("cedula_profesional"));

                fa.setProcedimiento(pro);

                fa.setEspecialidad(e);
                fa.setProfesional(p);

                fa.setFechaHora(formatoHoraFecha.parse(rs.getString("fecha") + " " + rs.getString("hora")));
                fa.setCodCita(rs.getString("codigo_cita"));

                listaFa.add(fa);
            }
        } catch (SQLException ex) {
            throw ex;
        } catch (ParseException ex) {
            Logger.getLogger(AgendaDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            consulta.desconectar();
        }
        return listaFa;
    }

    public List<FranjaAgenda> consultarAgendaPorProfesional(String cedulaProfesional, Date fechaInicial, Date fechaFinal) throws SQLException {
        Consulta consulta = null;
        ResultSet rs;
        SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat formatoHoraFecha = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String filtro = "";
        if (cedulaProfesional != null) {
            filtro = " a.cedula_profesional = '" + cedulaProfesional + "' AND ";
        }

        List<FranjaAgenda> listaFa = new ArrayList<>();
        try {
            consulta = new Consulta(getConexion());
            String sql
                    = " SELECT a.cedula_profesional, prof.nombre nombre_profesional, a.codigo_especialidad, e.nombre nombre_especialidad, a.fecha, a.hora, a.duracion, a.codigo_cita,"
                    + " p.identificacion id_paciente,p.nombre||(case when character_length(coalesce(p.segundo_nombre,''))=0 then '' else ' '||p.segundo_nombre end)||(case when character_length(coalesce(p.primer_apellido,''))=0 then '' else ' '||p.primer_apellido end)||(case when character_length(coalesce(p.segundo_apellido,''))=0 then '' else ' '||p.segundo_apellido end) nombre_paciente,pro.codigo codigo_procedimiento,pro.nombre nombre_procedimiento, "
                    + " p.entidad codigo_entidad,ent.nombre nombre_entidad,((current_date-p.fecha_nacimiento)/365)::integer edad,p.telefono1, "
                    + " case when l.value='7' then c.observaciones else l.label||' '||c.observaciones end observaciones "
                    + " FROM agenda a "
                    + " inner join especialidades e ON (a.codigo_especialidad = e.codigo) "
                    + " inner join profesionales prof ON (a.cedula_profesional=prof.cedula) "
                    + " left join citas c ON (c.codigo = a.codigo_cita) "
                    + " left join pacientes p ON (c.id_paciente = p.identificacion) "
                    + " left join entidades ent ON (ent.codigo=p.entidad) "
                    + " left join rel_procedimientos_cita rpc ON (a.codigo_cita=rpc.codigo_cita and a.fecha=rpc.fecha and a.hora=rpc.hora) "
                    + " left join procedimientos pro ON (rpc.codigo_procedimiento=pro.codigo) "
                    + " left join "
                    + " (select value,label from lista l "
                    + " inner join detalle_lista dl on (l.codigo=dl.codigo_lista and l.nombre='OBSERVACIONES_CITA')) l on (c.codigo_observacion=l.value) "
                    + " WHERE "
                    + filtro
                    + " a.fecha between '" + formatoFecha.format(fechaInicial) + "' AND '" + formatoFecha.format(fechaFinal) + "'"
                    + " ORDER BY "
                    + " prof.nombre, a.fecha ASC,a.hora ASC";
            rs = consulta.ejecutar(sql);

            while (rs.next()) {
                FranjaAgenda fa = new FranjaAgenda();
                Especialidad e = new Especialidad();
                Profesional p = new Profesional();
                Paciente pac = new Paciente();
                Procedimiento pro = new Procedimiento();
                e.setCodigo(rs.getString("codigo_especialidad"));
                e.setNombre(rs.getString("nombre_especialidad"));
                p.setCedula(rs.getString("cedula_profesional"));
                p.setNombre(rs.getString("nombre_profesional"));
                pac.setIdentificacion(rs.getString("id_paciente"));
                pac.setNombre(rs.getString("nombre_paciente"));
                pac.setEdad(rs.getString("edad"));
                pac.setTelefono1(rs.getString("telefono1"));
                if (rs.getString("codigo_entidad") != null) {
                    pac.setEntidad(new Entidad(rs.getString("codigo_entidad"), rs.getString("nombre_entidad")));
                }

                fa.setProcedimiento(pro);
                if (rs.getString("codigo_procedimiento") != null) {
                    fa.setProcedimiento(new Procedimiento(rs.getString("codigo_procedimiento"), rs.getString("nombre_procedimiento")));
                }
                fa.setEspecialidad(e);
                fa.setProfesional(p);
                fa.setPaciente(pac);
                fa.setFechaHora(formatoHoraFecha.parse(rs.getString("fecha") + " " + rs.getString("hora")));
                fa.setCodCita(rs.getString("codigo_cita"));
                fa.setDuracion(rs.getString("duracion"));
                fa.setObservaciones(rs.getString("observaciones"));

                listaFa.add(fa);
            }
        } catch (SQLException ex) {
            throw ex;
        } catch (ParseException ex) {
            Logger.getLogger(AgendaDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            consulta.desconectar();
        }
        return listaFa;
    }

    public void eliminarAgenda(String cedulaProfesional, String codigoEspecialidad, ScheduleModel modelo, Boolean modoEliminar) throws SQLException {
        Consulta consulta = null;
        ResultSet rs;
        SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat formatoHora = new SimpleDateFormat("HH:mm:ss");
        try {
            consulta = new Consulta(getConexion());
            String sql = "begin;";
            consulta.actualizar(sql);
            for (ScheduleEvent se : modelo.getEvents()) {
                if (modoEliminar) {
                    if (se.getTitle().equalsIgnoreCase("Seleccionado") && se.getStyleClass().equalsIgnoreCase("eventoSinCita")) {
                        sql
                                = " delete from agenda where cedula_profesional='" + cedulaProfesional + "' "
                                + " and codigo_especialidad='" + codigoEspecialidad + "' and fecha='" + formatoFecha.format(se.getStartDate()) + "'";
                        consulta.actualizar(sql);
                    }
                } else if (((FranjaAgenda) se.getData()).getSeleccionada()) {
                    sql
                            = " delete from agenda where cedula_profesional='" + cedulaProfesional + "' "
                            + " and codigo_especialidad='" + codigoEspecialidad + "' and fecha='" + formatoFecha.format(se.getStartDate()) + "' "
                            + " and hora='" + formatoHora.format(se.getStartDate()) + "'";
                    consulta.actualizar(sql);
                }

            }
            sql = "commit;";
            consulta.actualizar(sql);
        } catch (SQLException ex) {
            throw ex;
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
