/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bd;

import static com.sun.faces.facelets.tag.jstl.fn.JstlFunction.trim;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.Cita;
import modelo.DetalleTerapia;
import modelo.Entidad;
import modelo.Paciente;
import modelo.Procedimiento;
import modelo.Profesional;
import modelo.Terapia;
import modelo.Valoracion;
import modelo.Diagnostico;
import modelo.Especialidad;

/**
 *
 * @author Andres
 */
public class TerapiaDAO {

    private Connection conexion;

    public TerapiaDAO(Connection conexion) {
        this.conexion = conexion;
    }

    public List<Terapia> consultarTerapiasPaciente(Paciente paciente, Boolean activa, Date fechaInicial, Date fechaFinal, String edadInicial, String edadFinal, String procedimiento, Boolean autorizadas, Boolean sinProximaCita) throws SQLException {
        Terapia t;
        List<Terapia> listaTerapias = new ArrayList<>();
        //List<Procedimiento> listaP;
        Profesional prof, prof_valora;
        Valoracion v;
        Entidad e;
        Paciente pac;
        Procedimiento proc;
        Cita c, citaValora;
        Consulta consulta = null;
        String sql, filtro = "";
        ResultSet rs;
        Boolean primero = true;
        SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd");

        try {
            if (!paciente.getIdentificacion().equalsIgnoreCase("")) {
                filtro = " t.id_paciente='" + paciente.getIdentificacion() + "' ";
                primero = false;
            } else if (paciente.getNombreCompleto() != null) {
                filtro = " pa.nombre||(case when character_length(coalesce(pa.segundo_nombre,''))=0 then '' else ' '||pa.segundo_nombre end)||(case when character_length(coalesce(pa.primer_apellido,''))=0 then '' else ' '||pa.primer_apellido end)||(case when character_length(coalesce(pa.segundo_apellido,''))=0 then '' else ' '||pa.segundo_apellido end)='" + paciente.getNombreCompleto() + "' ";
                primero = false;
            }
            if (activa) {
                filtro = filtro + (!primero ? " and " : "") + " t.activa=" + activa;
                primero = false;
            }
            if (fechaInicial != null && fechaFinal != null) {
                filtro = filtro + (!primero ? " and " : "") + " t.fecha between '" + formatoFecha.format(fechaInicial) + "' and '" + formatoFecha.format(fechaFinal) + "' ";
                primero = false;
            }

            if (edadInicial != null && edadFinal != null) {
                filtro = filtro + (!primero ? " and " : "") + " (current_date-pa.fecha_nacimiento)/365 between '" + edadInicial + "' and '" + edadFinal + "' ";
                primero = false;
            }

            if (procedimiento != null) {
                filtro = filtro + (!primero ? " and " : "") + " t.codigo_procedimiento='" + procedimiento + "' ";
                primero = false;
            }

            if (paciente.getEntidad().getCodigo() != null) {
                filtro = filtro + (!primero ? " and " : "") + " pa.entidad='" + paciente.getEntidad().getCodigo() + "' ";
            }

            if (autorizadas != null) {
                if (autorizadas) {
                    filtro = filtro + (!primero ? " and " : "") + " t.cantidad_autorizada > 0 ";
                }
            }

            if (!filtro.equalsIgnoreCase("")) {
                filtro = " where " + filtro;
            }

            consulta = new Consulta(getConexion());
            sql
                    = " select "
                    + " t.codigo codigo_terapia, t.id_paciente, t.id_profesional, t.codigo_procedimiento, t.fecha,  "
                    + " cantidad_formulada, cantidad_autorizada, cantidad_pendiente,  "
                    + " cantidad_atendida, activa, t.codigo_valoracion, t.nombre_acompanante,  "
                    + " t.parentesco_acompanante, t.codigo_rips, t.codigo_diagnostico, t.primera_vez,  "
                    + " t.control, t.diagnostico, t.plan_tratamiento, t.evolucion, "
                    + " pa.nombre nombre_paciente , pa.fecha_nacimiento, "
                    + " prof.nombre nombre_profesional, "
                    + " proc.nombre nombre_procedimiento,"
                    + " count(case when c.estado='5' then c.codigo else null end) cantidad_inasistidas, "
                    + " e.codigo codigo_entidad, e.nombre nombre_entidad, pa.correo_electronico, pa.telefono1, pa.telefono2, "
                    + " pa.nombre||(case when character_length(coalesce(pa.segundo_nombre,''))=0 then '' else ' '||pa.segundo_nombre end)||(case when character_length(coalesce(pa.primer_apellido,''))=0 then '' else ' '||pa.primer_apellido end)||(case when character_length(coalesce(pa.segundo_apellido,''))=0 then '' else ' '||pa.segundo_apellido end) nombre_completo, "
                    + " t.fecha_solicitud, t.horario_preferencial, t.observaciones observaciones_terapia, t.seguimiento_1, t.seguimiento_2, t.seguimiento_3, "
                    + " (case when prof_valora.nombre is null then prof.nombre else prof_valora.nombre end)  nombre_prof_valora, "
                    + " (case when v.codigo_diagnostico is null then t.codigo_diagnostico else v.codigo_diagnostico end ) dx_valora, "
                    + " t.nro_autorizacion, pc.prox_cita, t.informe_terapeutico "
                    + " from  "
                    + " terapia t "
                    + " left join profesionales prof on (t.id_profesional=prof.cedula) "//prof terapia
                    + " inner join pacientes pa on (t.id_paciente=pa.identificacion) "
                    + " left join valoracion v on (t.codigo_valoracion=v.codigo) "
                    + " left join profesionales prof_valora on (v.id_profesinal=prof_valora.cedula) "//prof valora
                    + " inner join procedimientos proc on (t.codigo_procedimiento=proc.codigo) "
                    + " left join detalle_terapia dt on (t.codigo=dt.codigo_terapia) "
                    + " left join citas c on (dt.cod_cita=c.codigo) "
                    + " left join entidades e on (pa.entidad = e.codigo) "
                    + " left join (select "
                    + " t.codigo,min(c.fecha) prox_cita "
                    + " from "
                    + " terapia t "
                    + " inner join detalle_terapia dt on (t.codigo=dt.codigo_terapia) "
                    + " inner join citas c on (c.codigo=dt.cod_cita) "
                    + " where "
                    + " dt.estado in ('I','E') and c.fecha>=current_date "
                    + " group by 1) pc on (t.codigo=pc.codigo) "
                    //+ " where "
                    //+ " t.id_paciente='" + identificacion + "' "
                    + filtro
                    + " group by 1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40,41 "
                    + " order by t.fecha desc ";
            if (sinProximaCita) {
                sql = "select * from (" + sql + ") sc where prox_cita is null";
            }
            /*
            select
t.codigo,count(case when c.estado='5' then c.codigo else null end)
from
terapia t
left join detalle_terapia dt on (t.codigo=dt.codigo_terapia)
left join citas c on (dt.cod_cita=c.codigo)
group by 1
             */
            rs = consulta.ejecutar(sql);
            while (rs.next()) {
                t = new Terapia();
                e = new Entidad(rs.getString("codigo_entidad"), rs.getString("nombre_entidad"));
                pac = new Paciente();
                prof = new Profesional();
                //listaP = new ArrayList<>();
                proc = new Procedimiento(rs.getString("codigo_procedimiento"), rs.getString("nombre_procedimiento"));
                c = new Cita();
                //listaP.add(new Procedimiento(rs.getString("codigo_procedimiento"),rs.getString("nombre_procedimiento")));

                pac.setIdentificacion(rs.getString("id_paciente"));
                pac.setNombre(rs.getString("nombre_paciente"));
                pac.setNombreCompleto(rs.getString("nombre_completo"));

                pac.setEmail(rs.getString("correo_electronico"));
                pac.setTelefono1(rs.getString("telefono1"));
                pac.setTelefono2(rs.getString("telefono2"));
                pac.setEntidad(e);

                pac.setFechaNacimiento(rs.getDate("fecha_nacimiento"));
                Date fa = new Date();
                Calendar fechaNacimiento = Calendar.getInstance();
                Calendar fechaActual = Calendar.getInstance();
                fechaNacimiento.setTime(pac.getFechaNacimiento());
                fechaActual.setTime(fa);
                Long resta = (fechaNacimiento.getTimeInMillis() < 0 ? fechaActual.getTimeInMillis() + (fechaNacimiento.getTimeInMillis() * (-1)) : fechaActual.getTimeInMillis() - fechaNacimiento.getTimeInMillis()) / 1000 / 60 / 60 / 24 / 365;
                Integer edad = resta.intValue();
                pac.setEdad(edad.toString());

                prof.setCedula(rs.getString("id_profesional"));
                prof.setNombre(rs.getString("nombre_profesional"));

                t.setCodigo(rs.getInt("codigo_terapia"));
                c.setPaciente(pac);
                c.setFecha(rs.getDate("prox_cita"));
                //c.setProfesional(prof);
                //c.setListaProcedimientos(listaP);
                t.setNroAutorizacion(rs.getString("nro_autorizacion"));
                t.setProfesionalPrescribe(prof);
                t.setProcedimiento(proc);
                t.setCita(c);
                t.setFecha(rs.getDate("fecha"));
                t.setCantidadFormulada(rs.getInt("cantidad_formulada"));
                t.setCantidadAutorizada(rs.getInt("cantidad_autorizada"));
                t.setCantidadPendiente(rs.getInt("cantidad_pendiente"));
                t.setCantidadAtendida(rs.getInt("cantidad_atendida"));
                t.setCantidadInasistidas(rs.getInt("cantidad_inasistidas"));
                t.setActiva(rs.getBoolean("activa"));
                t.setCodigoValoracion(rs.getInt("codigo_valoracion"));
                t.setNombreAcompanante(rs.getString("nombre_acompanante"));
                t.setParentescoAcompanante(rs.getString("parentesco_acompanante"));
                t.setCodigoRIPS(rs.getString("codigo_rips"));
                t.setCodigoDiagnostico(rs.getString("codigo_diagnostico"));
                t.setPrimeraVez(rs.getBoolean("primera_vez"));
                t.setControl(rs.getBoolean("control"));
                t.setDiagnostico(rs.getString("diagnostico"));
                t.setPlanTratamiento(rs.getString("plan_tratamiento"));
                t.setEvolucion(rs.getString("evolucion"));

                t.setFechaSolicitud(rs.getDate("fecha_solicitud"));
                t.setHorarioPreferencial(rs.getString("horario_preferencial"));
                t.setObservaciones(rs.getString("observaciones_terapia"));
                t.setSeguimiento1(rs.getString("seguimiento_1"));
                t.setSeguimiento2(rs.getString("seguimiento_2"));
                t.setSeguimiento3(rs.getString("seguimiento_3"));
                t.setInformeTerapeutico(rs.getString("informe_terapeutico"));

                //seteo del profesional que valora-> terapia.valoracion.cita.profesional.nombre
                prof_valora = new Profesional();
                prof_valora.setNombre(rs.getString("nombre_prof_valora"));
                v = new Valoracion();
                citaValora = new Cita();
                citaValora.setProfesional(prof_valora);
                v.setCita(citaValora);
                v.setCodigoDiagnostico(rs.getString("dx_valora"));
                t.setValoracion(v);

                listaTerapias.add(t);
            }

            return listaTerapias;
        } catch (SQLException ex) {
            throw ex;
        } finally {
            consulta.desconectar();
        }
    }

    public Integer actualizarTerapia(Terapia terapia) {
        Consulta consulta = null;
        String sql, fechaSolicitud = "";
        ResultSet rs;
        Integer resultado = 0;
        SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd");
        try {
            consulta = new Consulta(getConexion());
            if (terapia.getFechaSolicitud() != null) {
                fechaSolicitud = " fecha_solicitud='" + formatoFecha.format(terapia.getFechaSolicitud()) + "', ";
            }
            sql = "update terapia set "
                    + " nro_autorizacion = '" + terapia.getNroAutorizacion() + "', "
                    + " activa=" + terapia.getActiva() + ", "
                    + " cantidad_autorizada=" + terapia.getCantidadAutorizada() + ", "
                    + fechaSolicitud
                    + " horario_preferencial='" + terapia.getHorarioPreferencial() + "', "
                    + " observaciones='" + terapia.getObservaciones() + "', "
                    + " seguimiento_1='" + terapia.getSeguimiento1() + "', "
                    + " seguimiento_2='" + terapia.getSeguimiento2() + "', "
                    + " seguimiento_3='" + terapia.getSeguimiento3() + "' "
                    + " where codigo=" + terapia.getCodigo();
            resultado = consulta.actualizar(sql);

        } catch (SQLException ex) {
            Logger.getLogger(TerapiaDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            consulta.desconectar();
        }
        return resultado;
    }

    public Integer actualizarTerapiaInformeTerapeutico(Terapia terapia) {
        Consulta consulta = null;
        String sql, fechaSolicitud = "";
        ResultSet rs;
        Integer resultado = 0;
        SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd");
        try {
            consulta = new Consulta(getConexion());

            sql = "update terapia set "
                    + " informe_terapeutico = '" + terapia.getInformeTerapeutico() + "', "
                    + " activa = false, "
                    + " fecha_informe_terapeutico = current_date "
                    + " where codigo=" + terapia.getCodigo();
            resultado = consulta.actualizar(sql);

        } catch (SQLException ex) {
            Logger.getLogger(TerapiaDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            consulta.desconectar();
        }
        return resultado;
    }

    public Integer guardarTerapia(Terapia terapia) {
        Consulta consulta = null;
        String sql;
        ResultSet rs;
        Integer resultado = null;
        try {
            consulta = new Consulta(getConexion());

            //Consultar códigos de diagnostico
            sql = "select cod_diagnostico from diagnosticos where nombre_diagnostico='" + terapia.getDiagnostico1().getNombre_diagostico() + "'";
            rs = consulta.ejecutar(sql);

            if (rs.next()) {
                terapia.getDiagnostico1().setCodigo_diagnostico(rs.getString("cod_diagnostico"));
            }

            sql = "select cod_diagnostico from diagnosticos where nombre_diagnostico='" + terapia.getDiagnostico2().getNombre_diagostico() + "'";
            rs = consulta.ejecutar(sql);

            if (rs.next()) {
                terapia.getDiagnostico2().setCodigo_diagnostico(rs.getString("cod_diagnostico"));
            }
//            String nom_diagnostico1 = diagnostico1.getNombre_diagostico();
//            String nom_diagnostico2 = diagnostico2.getNombre_diagostico();
//            String[] arrayDiagnostico1 = nom_diagnostico1.split("-");
//            String[] arrayDiagnostico2 = nom_diagnostico2.split("-");

            // En este momento tenemos un array en el que cada elemento es un color.
//            diagnostico1.setCodigo_diagnostico(trim(arrayDiagnostico1[0]));
//            diagnostico1.setNombre_diagostico(trim(arrayDiagnostico1[1]));
//            diagnostico2.setCodigo_diagnostico(trim(arrayDiagnostico2[0]));
//            diagnostico2.setNombre_diagostico(trim(arrayDiagnostico2[1]));
            sql = "select count(*) cantidad from terapia where id_paciente='" + terapia.getCita().getPaciente().getIdentificacion() + "' "
                    + " and codigo_procedimiento='" + terapia.getProcedimiento().getCodigo() + "' and activa";
            rs = consulta.ejecutar(sql);
            if (rs.next()) {
                resultado = rs.getInt("cantidad");
            }

            if (resultado > 0) {//si tiene terapia activa, retorna 0
                resultado = 0;
            } else {//sino la inserta
                sql = " INSERT INTO terapia( "
                        + " id_paciente, id_profesional, codigo_procedimiento, fecha,  "
                        + " cantidad_formulada, cantidad_autorizada, cantidad_pendiente,  "
                        + " cantidad_atendida, activa, codigo_valoracion, nombre_acompanante,  "
                        + " parentesco_acompanante, codigo_rips, codigo_diagnostico, primera_vez,  "
                        + " control, diagnostico, plan_tratamiento, evolucion, codigo_diagnostico2, observacion_recetario) "
                        + " VALUES ( '" + terapia.getCita().getPaciente().getIdentificacion() + "', '" + terapia.getProfesionalPrescribe().getCedula() + "', '" + terapia.getProcedimiento().getCodigo() + "', current_date,  "
                        + " " + terapia.getCantidadFormulada() + ", " + terapia.getCantidadAutorizada() + ", " + (terapia.getCantidadAutorizada() > 0 ? terapia.getCantidadAutorizada() : "0") + ", "
                        + " 0, true, null, '',  "
                        + " '', '', '" + terapia.getDiagnostico1().getCodigo_diagnostico() + "', null,  "
                        + " null, '', '', '', '" + terapia.getDiagnostico2().getCodigo_diagnostico() + "', '" + terapia.getValoracion().getObservacionRecetario() + "') returning codigo";
                rs = consulta.ejecutar(sql);
                if (rs.next()) {
                    resultado = rs.getInt("codigo");
                }
            }

        } catch (SQLException ex) {
            Logger.getLogger(TerapiaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return resultado;
    }

    public Integer actualizarTerapiaCita(Terapia terapia, List<DetalleTerapia> detalleTerapia, Boolean primeraVez) {
        Consulta consulta = null;
        String sql, consecutivo = "";
        ResultSet rs;
        Integer resultado = null, i, codigoCita = 0;
        SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat formatoHora = new SimpleDateFormat("HH:mm:ss");
        SimpleDateFormat formatoHoraFecha = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        Calendar horaDuracion = Calendar.getInstance();

        try {
            consulta = new Consulta(getConexion());

            //Consultar códigos de diagnostico
            sql = "select cod_diagnostico from diagnosticos where nombre_diagnostico='" + terapia.getDiagnostico1().getNombre_diagostico() + "'";
            rs = consulta.ejecutar(sql);

            if (rs.next()) {
                terapia.getDiagnostico1().setCodigo_diagnostico(rs.getString("cod_diagnostico"));
            }

            sql = "select cod_diagnostico from diagnosticos where nombre_diagnostico='" + terapia.getDiagnostico2().getNombre_diagostico() + "'";
            rs = consulta.ejecutar(sql);

            if (rs.next()) {
                terapia.getDiagnostico2().setCodigo_diagnostico(rs.getString("cod_diagnostico"));
            }
//            String nom_diagnostico1 = diagnostico1.getNombre_diagostico();
//            String nom_diagnostico2 = diagnostico2.getNombre_diagostico();
//            String[] arrayDiagnostico1 = nom_diagnostico1.split("-");
//            String[] arrayDiagnostico2 = nom_diagnostico2.split("-");

            // En este momento tenemos un array en el que cada elemento es un color.
//            diagnostico1.setCodigo_diagnostico(trim(arrayDiagnostico1[0]));
//            diagnostico1.setNombre_diagostico(trim(arrayDiagnostico1[1]));
//            diagnostico2.setCodigo_diagnostico(trim(arrayDiagnostico2[0]));
//            diagnostico2.setNombre_diagostico(trim(arrayDiagnostico2[1]));
            sql = "begin";
            consulta.actualizar(sql);

            sql = " UPDATE terapia "
                    + " SET nombre_acompanante='" + terapia.getNombreAcompanante() + "', "
                    + " parentesco_acompanante='" + terapia.getParentescoAcompanante() + "', codigo_rips='" + terapia.getCodigoRIPS() + "', "
                    + " codigo_diagnostico='" + terapia.getDiagnostico1().getCodigo_diagnostico() + "', "
                    + " codigo_diagnostico2='" + terapia.getDiagnostico2().getCodigo_diagnostico() + "', "
                    + " primera_vez=" + terapia.getPrimeraVez() + ", "
                    + " control=" + terapia.getControl() + ", diagnostico='" + terapia.getDiagnostico() + "', "
                    + " plan_tratamiento='" + terapia.getPlanTratamiento() + "', evolucion='" + terapia.getEvolucion() + "', "
                    + " cantidad_atendida=cantidad_atendida+" + detalleTerapia.size() + ","
                    + " cantidad_pendiente=cantidad_pendiente-" + detalleTerapia.size() + ","
                    //+ " cantidad_pendiente=" + terapia.getCantSesiones() + "-cantidad_pendiente, "
                    + " recomendacion = '" + terapia.getRecomendacion() + "' "
                    + " WHERE codigo=" + terapia.getCodigo();
            consulta.actualizar(sql);

            sql = "update pacientes set condicion='" + (terapia.getCita().getPaciente().getCondicion() == null ? "" : terapia.getCita().getPaciente().getCondicion()) + "' where identificacion='" + terapia.getCita().getPaciente().getIdentificacion() + "'";
            consulta.actualizar(sql);

            /*
            EL DETALLE SE INSERTA A LA HORA DE AGENDAR LA CITA
            sql = "select coalesce(max(consecutivo)+1,1) resultado from detalle_terapia where codigo_terapia=" + terapia.getCodigo();
            rs = consulta.ejecutar(sql);
            if (rs.next()) {
                consecutivo = rs.getString("resultado");
            }
             */
 /*
            Si no hay division de franja no importa si es guardar o evolucionar
            Si hay division de franja, se evalua si es guardar o evolucionar, bandera permite dividir
             */
            if (detalleTerapia.size() == 1) {
                sql = " UPDATE detalle_terapia SET "
                        + " actividad = '" + detalleTerapia.get(0).getActividad() + "', "
                        + " fecha = current_date, "
                        + " hora = current_time, "
                        + " estado='E' "
                        + " where "
                        + " consecutivo = " + detalleTerapia.get(0).getConsecutivo()
                        + " and codigo_terapia = " + terapia.getCodigo();

                resultado = consulta.actualizar(sql);
                sql = "update citas set estado='2' where codigo=" + terapia.getCita().getCodigo();
                resultado = consulta.actualizar(sql);
            } else if (detalleTerapia.size() > 1) {
                if (primeraVez) {
                    //primer elemento de detalleTerapia
                    sql = " UPDATE detalle_terapia SET "
                            + " actividad = '" + detalleTerapia.get(0).getActividad() + "', "
                            + " fecha = current_date, "
                            + " hora = current_time, "
                            + " estado='E' "
                            + " where "
                            + " consecutivo = " + detalleTerapia.get(0).getConsecutivo()
                            + " and codigo_terapia = " + terapia.getCodigo();

                    resultado = consulta.actualizar(sql);
                    sql = "update citas set estado='2' where codigo=" + terapia.getCita().getCodigo();
                    resultado = consulta.actualizar(sql);
                    /*
                    update agenda nueva duracion
                    CONSTRAINT agenda_pkey PRIMARY KEY (cedula_profesional, fecha, hora),
                    update agenda set duracion=10 where cedula_profesional='21312' and fecha='2019-09-26' and hora='08:00:00'
                     */
                    int duracionCalculada = detalleTerapia.get(0).getDuracion() / detalleTerapia.size();
                    int resto = detalleTerapia.get(0).getDuracion() % detalleTerapia.size();

                    sql = "update agenda set duracion=" + (duracionCalculada + resto) + " where cedula_profesional='" + terapia.getCita().getProfesional().getCedula() + "' and fecha='" + formatoFecha.format(terapia.getCita().getFecha()) + "' and hora='" + formatoHora.format(terapia.getCita().getHora()) + "'";
                    resultado = consulta.actualizar(sql);

                    //el resto de los elementos
                    for (i = 1; i < detalleTerapia.size(); i++) {

                        horaDuracion.setTime(formatoHoraFecha.parse(formatoFecha.format(terapia.getCita().getFecha()) + " " + formatoHora.format(terapia.getCita().getHora())));
                        horaDuracion.add(Calendar.MINUTE, (duracionCalculada * i) + resto); //todos se desplazan resto que esta solo en el primero
                        //CITAS
                        sql = " INSERT INTO citas( "
                                + " id_paciente, fecha, hora, codigo_especialidad,  "
                                + " id_profesional, codigo_entidad, numero_autorizacion, observaciones,  "
                                + " usuario, estado, motivo, fecha_registro_estado, responsable,  "
                                + " medio, codigo_observacion, observaciones2, fecha_deseada) "
                                + " select  "
                                + " id_paciente, fecha, '" + formatoHora.format(horaDuracion.getTime()) + "', codigo_especialidad,  "
                                + " id_profesional, codigo_entidad, numero_autorizacion, observaciones,  "
                                + " usuario, '0', motivo, fecha_registro_estado, responsable,  "
                                + " medio, codigo_observacion, observaciones2, fecha_deseada "
                                + " from citas "
                                + " where codigo=" + terapia.getCita().getCodigo() + " returning codigo";
                        rs = consulta.ejecutar(sql);
                        if (rs.next()) {
                            codigoCita = rs.getInt("codigo");
                        }
                        //DETALLE_TERAPIA
                        sql = " INSERT INTO detalle_terapia( "
                                + " consecutivo, codigo_terapia, actividad, fecha, hora, estado,  "
                                + " cod_cita) "
                                + " VALUES (" + detalleTerapia.get(i).getConsecutivo() + ", " + terapia.getCodigo() + ", '" + detalleTerapia.get(i).getActividad() + "', current_date, current_time, 'E',  "
                                + " " + codigoCita + ") ";
                        resultado = consulta.actualizar(sql);

                        //REL_PROCEDIMIENTOS_CITA
                        sql = " INSERT INTO rel_procedimientos_cita( "
                                + " codigo_procedimiento, fecha, hora, codigo_cita) "
                                + " select rel.codigo_procedimiento, rel.fecha, '" + formatoHora.format(horaDuracion.getTime()) + "', " + codigoCita + " "
                                + " from "
                                + " citas c "
                                + " inner join rel_procedimientos_cita rel on (c.codigo=rel.codigo_cita and c.fecha=rel.fecha and c.hora=rel.hora) "
                                + " where c.codigo=" + terapia.getCita().getCodigo();
                        resultado = consulta.actualizar(sql);

                        //AGENDA
                        sql = " INSERT INTO agenda( "
                                + " cedula_profesional, codigo_especialidad, fecha, hora, duracion, "
                                + " codigo_cita, reservado_valoracion) "
                                + " VALUES ('" + terapia.getCita().getProfesional().getCedula() + "', '" + terapia.getCita().getEspecialidad().getCodigo() + "', "
                                + " '" + formatoFecha.format(terapia.getCita().getFecha()) + "', "
                                + " '" + formatoHora.format(horaDuracion.getTime()) + "', " + duracionCalculada + ", " + codigoCita + ", false) ";
                        resultado = consulta.actualizar(sql);

                    }
                } else {
                    for (i = 0; i < detalleTerapia.size(); i++) {
                        sql = " UPDATE detalle_terapia SET "
                                + " actividad = '" + detalleTerapia.get(i).getActividad() + "', "
                                + " fecha = current_date, "
                                //no actualizar la hora al momento de las evoluciones
                                //+ " hora = current_time, "
                                + " estado='E' "
                                + " where "
                                + " consecutivo = " + detalleTerapia.get(i).getConsecutivo()
                                + " and codigo_terapia = " + terapia.getCodigo();

                        resultado = consulta.actualizar(sql);
                    }
                }
            }

            sql = "commit";
            consulta.actualizar(sql);

        } catch (SQLException ex) {
            Logger.getLogger(TerapiaDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(TerapiaDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            consulta.desconectar();
        }
        return resultado;
    }

    public Terapia consultarTerapiaPorCita(Cita cita) throws SQLException {
        Terapia t = null;
        DetalleTerapia dt = null;
        Diagnostico d1, d2;

        //List<Terapia> listaTerapias = new ArrayList<>();
        //List<Procedimiento> listaP;
        Profesional prof;
        Especialidad esp;
        Entidad e;
        Paciente pac;
        Procedimiento proc;
        Cita c;
        Consulta consulta = null;
        String sql, filtro = "";
        ResultSet rs;
        Boolean primero = true;
        SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd");

        try {

            consulta = new Consulta(getConexion());
            sql
                    = " select t.*,pa.*,t.codigo codigo_terapia,"
                    + " pa.nombre||(case when character_length(coalesce(pa.segundo_nombre,''))=0 then '' else ' '||pa.segundo_nombre end)||(case when character_length(coalesce(pa.primer_apellido,''))=0 then '' else ' '||pa.primer_apellido end)||(case when character_length(coalesce(pa.segundo_apellido,''))=0 then '' else ' '||pa.segundo_apellido end) nombre_completo, "
                    //
                    + " l_sexo.label sexo_label, "
                    + " l_estado_civil.label estado_civil_label, "
                    + " pa.ocupacion, "
                    + " pa.grado_escolar, "
                    + " l_tipo_afiliacion.label tipo_afiliacion_label, "
                    + " l_tipo_identificacion.label tipo_identificacion_label, "
                    + " l_condicion.value condicion, "
                    + " e.codigo codigo_entidad, e.nombre nombre_entidad, "
                    + " case when v.codigo_diagnostico is null then t.codigo_diagnostico else v.codigo_diagnostico end codigo_diagnostico_principal, "
                    + " case when v.codigo_diagnostico2 is null then t.codigo_diagnostico2 else v.codigo_diagnostico2 end codigo_diagnostico_secundario, "
                    + " diag1.cod_diagnostico cod_diagnostico1,diag1.nombre_diagnostico nombre_diagnostico1, "
                    + " diag2.cod_diagnostico cod_diagnostico2,diag2.nombre_diagnostico nombre_diagnostico2, "
                    + " pro.cedula, c.fecha fecha_cita, c.hora hora_cita, c.codigo_especialidad "
                    //
                    + " from "
                    + " terapia t "
                    + " inner join pacientes pa on (t.id_paciente=pa.identificacion) "
                    + " inner join entidades e on (pa.entidad = e.codigo) "
                    + " inner join detalle_terapia dt on (t.codigo=dt.codigo_terapia) "
                    + " inner join citas c on (dt.cod_cita=c.codigo) "
                    + " inner join profesionales pro on (c.id_profesional=pro.cedula) "
                    + " left join diagnosticos diag1 on (t.codigo_diagnostico=diag1.cod_diagnostico) "
                    + " left join diagnosticos diag2 on (t.codigo_diagnostico2=diag2.cod_diagnostico) "
                    + " left join valoracion v on (t.codigo_valoracion=v.codigo)  "
                    //
                    + " left join (lista l "
                    + " inner join detalle_lista dl on (l.codigo=dl.codigo_lista and l.nombre='SEXO') ) l_sexo on (l_sexo.value=pa.sexo) "
                    + " left join (lista l "
                    + " inner join detalle_lista dl on (l.codigo=dl.codigo_lista and l.nombre='ESTADO_CIVIL') ) l_estado_civil on (l_estado_civil.value=pa.estado_civil) "
                    + " left join (lista l "
                    + " inner join detalle_lista dl on (l.codigo=dl.codigo_lista and l.nombre='TIPO_AFILIACION') ) l_tipo_afiliacion on (l_tipo_afiliacion.value=pa.tipo_afiliacion) "
                    + " left join (lista l "
                    + " inner join detalle_lista dl on (l.codigo=dl.codigo_lista and l.nombre='TIPO_IDENTIFICACION') ) l_tipo_identificacion on (l_tipo_identificacion.value=pa.tipo_identificacion) "
                    + " left join (lista l "
                    + " inner join detalle_lista dl on (l.codigo=dl.codigo_lista and l.nombre='CONDICION_TERAPIA') ) l_condicion on (l_condicion.value=pa.condicion) "
                    //
                    + " WHERE c.codigo= " + cita.getCodigo();

            rs = consulta.ejecutar(sql);
            if (rs.next()) {
                t = new Terapia();
                e = new Entidad(rs.getString("codigo_entidad"), rs.getString("nombre_entidad"));
                pac = new Paciente();
                prof = new Profesional();
                prof.setCedula(rs.getString("cedula"));

                esp = new Especialidad();
                esp.setCodigo(rs.getString("codigo_especialidad"));

                //listaP = new ArrayList<>();
                //proc = new Procedimiento(rs.getString("codigo_procedimiento"), rs.getString("nombre_procedimiento"));
                c = new Cita();
                c.setProfesional(prof);
                c.setFecha(rs.getDate("fecha_cita"));
                c.setHora(rs.getTime("hora_cita"));
                c.setEspecialidad(esp);

                //listaP.add(new Procedimiento(rs.getString("codigo_procedimiento"),rs.getString("nombre_procedimiento")));
                pac.setIdentificacion(rs.getString("id_paciente"));
                //pac.setNombre(rs.getString("nombre_paciente"));
                pac.setNombreCompleto(rs.getString("nombre_completo"));

                pac.setEmail(rs.getString("correo_electronico"));
                pac.setTelefono1(rs.getString("telefono1"));
                pac.setTelefono2(rs.getString("telefono2"));
                pac.setEntidad(e);

                pac.setFechaNacimiento(rs.getDate("fecha_nacimiento"));

                pac.setEstadoCivil(rs.getString("estado_civil_label"));
                pac.setOcupacion(rs.getString("ocupacion"));
                pac.setTipoAfiliacion(rs.getString("tipo_afiliacion_label"));
                pac.setDireccion1(rs.getString("direccion1"));
                pac.setSexo(rs.getString("sexo_label"));
                pac.setGradoEscolar(rs.getString("grado_escolar"));
                pac.setTipoIdentificacion(rs.getString("tipo_identificacion_label"));
                pac.setCondicion(rs.getString("condicion"));

                Date fa = new Date();
                Calendar fechaNacimiento = Calendar.getInstance();
                Calendar fechaActual = Calendar.getInstance();
                fechaNacimiento.setTime(pac.getFechaNacimiento());
                fechaActual.setTime(fa);
                Long resta = (fechaNacimiento.getTimeInMillis() < 0 ? fechaActual.getTimeInMillis() + (fechaNacimiento.getTimeInMillis() * (-1)) : fechaActual.getTimeInMillis() - fechaNacimiento.getTimeInMillis()) / 1000 / 60 / 60 / 24 / 365;
                Integer edad = resta.intValue();
                pac.setEdad(edad.toString());

                //prof.setCedula(rs.getString("id_profesional"));
                //prof.setNombre(rs.getString("nombre_profesional"));
                t.setCodigo(rs.getInt("codigo_terapia"));
                c.setPaciente(pac);
                c.setCodigo(cita.getCodigo());
                //c.setProfesional(prof);
                //c.setListaProcedimientos(listaP);
                //t.setProfesionalPrescribe(prof);
                //t.setProcedimiento(proc);
                t.setCita(c);
                t.setFecha(rs.getDate("fecha"));
                t.setCantidadFormulada(rs.getInt("cantidad_formulada"));
                t.setCantidadAutorizada(rs.getInt("cantidad_autorizada"));
                t.setCantidadPendiente(rs.getInt("cantidad_pendiente"));
                t.setCantidadAtendida(rs.getInt("cantidad_atendida"));
                //t.setCantidadInasistidas(rs.getInt("cantidad_inasistidas"));
                t.setActiva(rs.getBoolean("activa"));
                t.setCodigoValoracion(rs.getInt("codigo_valoracion"));
                t.setNombreAcompanante(rs.getString("nombre_acompanante"));
                t.setParentescoAcompanante(rs.getString("parentesco_acompanante"));
                t.setCodigoRIPS(rs.getString("codigo_rips"));
                t.setCodigoDiagnostico(rs.getString("codigo_diagnostico_principal"));
                t.setCodigoDiagnostico2(rs.getString("codigo_diagnostico_secundario"));
                t.setPrimeraVez(rs.getBoolean("primera_vez"));
                t.setControl(rs.getBoolean("control"));
                t.setDiagnostico(rs.getString("diagnostico"));
                t.setPlanTratamiento(rs.getString("plan_tratamiento"));
                t.setEvolucion(rs.getString("evolucion"));
                t.setNroAutorizacion(rs.getString("nro_autorizacion"));
                t.setRecomendacion(rs.getString("recomendacion"));

                d1 = new Diagnostico(rs.getString("cod_diagnostico1"), rs.getString("nombre_diagnostico1"));
                d2 = new Diagnostico(rs.getString("cod_diagnostico2"), rs.getString("nombre_diagnostico2"));

                t.setDiagnostico1(d1);
                t.setDiagnostico2(d2);

                //listaTerapias.add(t);
            }
            t.setDetalleTerapia(new ArrayList<DetalleTerapia>());

            sql = " select * from detalle_terapia where codigo_terapia=" + t.getCodigo() + " and estado='F' ";
            rs = consulta.ejecutar(sql);
            while (rs.next()) {
                dt = new DetalleTerapia();
                dt.setFecha(rs.getDate("fecha"));
                dt.setHora(rs.getTime("hora"));
                dt.setActividad(rs.getString("actividad"));
                dt.setConsecutivo(rs.getInt("consecutivo"));
                t.getDetalleTerapia().add(dt);
            }
            return t;
        } catch (SQLException ex) {
            throw ex;
        } finally {
            consulta.desconectar();
        }
    }

    public List<DetalleTerapia> consultarDetalleTerapiaPorCita(Terapia terapia) throws SQLException {
        DetalleTerapia dt = null;
        List<DetalleTerapia> detalleTerapia = new ArrayList<>();
        Consulta consulta = null;
        String sql;
        ResultSet rs;

        SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd");

        try {

            consulta = new Consulta(getConexion());
            sql = " select dt.*,a.duracion from "
                    + " detalle_terapia dt "
                    + " inner join agenda a on (dt.cod_cita=a.codigo_cita) "
                    + " where dt.codigo_terapia = " + terapia.getCodigo() + " and estado in ('I','E') order by consecutivo asc";
            rs = consulta.ejecutar(sql);
            while (rs.next()) {
                dt = new DetalleTerapia();
                dt.setFecha(rs.getDate("fecha"));
                dt.setHora(rs.getTime("hora"));
                dt.setActividad(rs.getString("actividad"));
                dt.setConsecutivo(rs.getInt("consecutivo"));
                dt.setEstado(rs.getString("estado"));
                dt.setDuracion(rs.getInt("duracion"));
                detalleTerapia.add(dt);
            }
            return detalleTerapia;
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
