/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bd;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
public class CitaDAO {

    private Connection conexion;

    public CitaDAO(Connection conexion) {
        this.conexion = conexion;
    }

    public Integer consultarCodigoAtencionCita(Cita cita) throws SQLException {
        Consulta consulta = null;
        ResultSet rs;
        String sql = "";
        Integer resultado = 0;
        try {
            consulta = new Consulta(getConexion());
            if (cita.getListaProcedimientos().get(0).getTipo() == 1) {//terapia
                sql = "select codigo_terapia as codigo from detalle_terapia where cod_cita=" + cita.getCodigo();
            } else if (cita.getListaProcedimientos().get(0).getTipo() == 2) {//valoracion
                sql = "select codigo from valoracion where cod_cita=" + cita.getCodigo();
            } else if (cita.getListaProcedimientos().get(0).getTipo() == 4) {//estudio audiologico
                sql = "select codigo from estudio_audiologico where cod_cita=" + cita.getCodigo();
            }
            rs = consulta.ejecutar(sql);
            if (rs.next()) {
                resultado = rs.getInt("codigo");
            }

            return resultado;

        } catch (SQLException ex) {
            throw ex;
        } finally {
            consulta.desconectar();
        }
    }

    public Integer guardarCita(Cita cita, List<Cita> listaCitaMultiple, Boolean usarFranjaExtendida, Integer cantidadFranjas) throws SQLException {

        Consulta consulta = null;
        Integer resultado = 0, codigoTerapia = 0, cantidad = 0;
        String sql;
        ResultSet rs;
        SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat formatoHora = new SimpleDateFormat("HH:mm:ss");

        try {
            consulta = new Consulta(getConexion());
            if (cita.getCodigo() == null) {
                if (usarFranjaExtendida) {
                    sql = " begin;";
                    consulta.actualizar(sql);
                    for (Cita c : listaCitaMultiple) {

                        //if (cita.getProcedimiento().getTipo() == 1) {
                        if (c.getListaProcedimientos().get(0).getTipo() == 1) {
                            //aqui tiene sentido analizar solo un procedimiento para terapia
                            sql = "select coalesce(codigo,-1) codigo from ( "
                                    + " select codigo,1 j from terapia where id_paciente='" + c.getPaciente().getIdentificacion() + "' and codigo_procedimiento='" + c.getListaProcedimientos().get(0).getCodigo() + "' and activa=true and cantidad_pendiente>=0 "
                                    + " ) c right join (select 1 j) sc using (j)";
                            rs = consulta.ejecutar(sql);
                            if (rs.next()) {
                                codigoTerapia = rs.getInt("codigo");
                            }
                            if (codigoTerapia == -1) {//si algun paciente no tiene terapia activa, no se realiza ningun cambio
                                sql = " rollback;";
                                consulta.actualizar(sql);
                                return -1;
                            }
                        }

                        //validar duplicidad (fecha, hora, profesional, estado)
                        sql = " select count(*) cantidad from citas c "
                                + " inner join (lista l inner join detalle_lista dl on (l.codigo=dl.codigo_lista and l.nombre='ESTADOS_CITA')) ec on (c.estado=ec.value) "
                                + " where "
                                + " fecha='" + formatoFecha.format(c.getFecha()) + "' "
                                + " and hora='" + formatoHora.format(c.getHora()) + "' "
                                + " and id_profesional='" + cita.getProfesional().getCedula() + "' and ec.label='Programada' ";
                        rs = consulta.ejecutar(sql);
                        if (rs.next()) {
                            cantidad = rs.getInt("cantidad");
                        }
                        if (cantidad > 0) {//ya existe una cita
                            sql = " rollback;";
                            consulta.actualizar(sql);
                            return -2;
                        }

                        sql = " INSERT INTO citas("
                                + " id_paciente, fecha, hora,  codigo_especialidad, "
                                + " id_profesional, codigo_entidad, numero_autorizacion, observaciones, "
                                + " usuario, estado, codigo_observacion)"
                                + " VALUES "
                                + " ( '" + c.getPaciente().getIdentificacion() + "', '" + formatoFecha.format(c.getFecha()) + "',"
                                + " '" + formatoHora.format(c.getHora()) + "', '" + cita.getEspecialidad().getCodigo() + "', "
                                + " '" + cita.getProfesional().getCedula() + "', '" + c.getPaciente().getEntidad().getCodigo() + "', "
                                + " '" + cita.getNumeroAutorizacion() + "', '" + cita.getObservaciones() + "', "
                                + " '" + cita.getUsuario().getUsuario() + "','" + cita.getEstado().getCodigo() + "','" + cita.getCodigoObservacion() + "') returning codigo;";
                        rs = consulta.ejecutar(sql);
                        if (rs.next()) {
                            resultado = rs.getInt("codigo");
                        }
                        // =0 viene sin modificar o reseteado del ultimo ciclo, supone que no es procedimiento tipo 1  >0 procedimiento tipo 1 <0 deberia haber retornado -1
                        if (codigoTerapia > 0) {
                            sql = " insert into detalle_terapia(consecutivo, codigo_terapia, cod_cita) "
                                    + " select coalesce(max(consecutivo),0)+1," + codigoTerapia + "," + resultado + " consecutivo "
                                    + " from detalle_terapia where codigo_terapia=" + codigoTerapia;
                            consulta.actualizar(sql);
                        }
                        codigoTerapia = 0;
                        //for (Procedimiento pro : cita.getListaProcedimientos()) {
                        sql = " INSERT INTO rel_procedimientos_cita("
                                + " codigo_procedimiento, fecha, hora, codigo_cita)"
                                + " VALUES( "
                                + " '" + c.getListaProcedimientos().get(0).getCodigo() + "', "
                                + " '" + formatoFecha.format(c.getFecha()) + "', "
                                + " '" + formatoHora.format(c.getHora()) + "', "
                                + " " + resultado + ");";
                        consulta.actualizar(sql);

                        //verificar franjas contiguas y libres
                        sql = " select count(*) cantidad "
                                + " from "
                                + " ( "
                                + " select  "
                                + " '" + cita.getProfesional().getCedula() + "'::varchar cedula_profesional, "
                                + " substring(fecha_hora::varchar,1,10)::date fecha,  "
                                + " substring(fecha_hora::varchar,12,8)::time hora  "
                                + " from  "
                                + " generate_series('" + formatoFecha.format(c.getFecha()) + " " + formatoHora.format(c.getHora()) + "'::timestamp,'" + formatoFecha.format(c.getFecha()) + " " + formatoHora.format(c.getHora()) + "'::timestamp + interval '" + c.getDuracionExtendida() + " minute' - interval '1 minute','" + (Integer.parseInt(c.getDuracionExtendida()) / cantidadFranjas) + " minutes') fecha_hora "
                                + " ) sc "
                                + " inner join agenda using (cedula_profesional,fecha,hora) ";
                        rs = consulta.ejecutar(sql);
                        if (rs.next()) {
                            cantidad = rs.getInt("cantidad");
                        }
                        if (cantidad != cantidadFranjas) {//no pasa la validacion de franjas contiguas y libres
                            sql = " rollback;";
                            consulta.actualizar(sql);
                            return -3;
                        }

                        sql = " delete "
                                + " from agenda a using ( "
                                + " select "
                                + " '" + cita.getProfesional().getCedula() + "'::varchar cedula_profesional, "
                                + " substring(fecha_hora::varchar,1,10)::date fecha, "
                                + " substring(fecha_hora::varchar,12,8)::time hora "
                                + " from "
                                + " generate_series('" + formatoFecha.format(c.getFecha()) + " " + formatoHora.format(c.getHora()) + "'::timestamp,'" + formatoFecha.format(c.getFecha()) + " " + formatoHora.format(c.getHora()) + "'::timestamp + interval '" + c.getDuracionExtendida() + " minute' - interval '1 minute','" + (Integer.parseInt(c.getDuracionExtendida()) / cantidadFranjas) + " minutes') fecha_hora "
                                + " ) tmp "
                                + " where "
                                + " a.cedula_profesional=tmp.cedula_profesional "
                                + " and a.fecha=tmp.fecha "
                                + " and a.hora=tmp.hora ";
                        consulta.actualizar(sql);

                        sql = "insert into agenda values('" + cita.getProfesional().getCedula() + "','" + cita.getEspecialidad().getCodigo() + "','" + formatoFecha.format(c.getFecha()) + "','" + formatoHora.format(c.getHora()) + "'," + c.getDuracionExtendida() + "," + resultado + ")";
                        consulta.actualizar(sql);
                        
                        //}
                    }
                    sql = " commit;";
                    consulta.actualizar(sql);
                } else if (cita.getListaProcedimientos().size() > 1) {//viejo
                    if (cita.getListaProcedimientos().get(0).getTipo() == 1) {
                        //REALIZAR VALIDACION DE TERAPIA PARA UN SOLO PROCEDIMIENTO ????????????????????
                        sql = "select coalesce(codigo,-1) codigo from ( "
                                + " select codigo,1 j from terapia where id_paciente='" + cita.getPaciente().getIdentificacion() + "' and codigo_procedimiento='" + cita.getListaProcedimientos().get(0).getCodigo() + "' and activa=true and cantidad_pendiente>0 "
                                + " ) c right join (select 1 j) sc using (j)";
                        rs = consulta.ejecutar(sql);
                        if (rs.next()) {
                            codigoTerapia = rs.getInt("codigo");
                        }
                        if (codigoTerapia == -1) {//tiene una o mas terapias activas
                            return -1;
                        }
                    }

                    //validar duplicidad (fecha, hora, profesional, estado)
                    sql = " select count(*) cantidad from citas c "
                            + " inner join (lista l inner join detalle_lista dl on (l.codigo=dl.codigo_lista and l.nombre='ESTADOS_CITA')) ec on (c.estado=ec.value) "
                            + " where "
                            + " fecha='" + formatoFecha.format(cita.getListaProcedimientos().get(0).getFecha()) + "' "
                            + " and hora='" + formatoHora.format(cita.getListaProcedimientos().get(0).getHora()) + "' "
                            + " and id_profesional='" + cita.getProfesional().getCedula() + "' and ec.label='Programada' ";
                    rs = consulta.ejecutar(sql);
                    if (rs.next()) {
                        cantidad = rs.getInt("cantidad");
                    }
                    if (cantidad > 0) {//ya existe una cita
                        return -2;
                    }

                    sql = " begin;";
                    consulta.actualizar(sql);
                    sql = " INSERT INTO citas("
                            + " id_paciente, fecha, hora,  codigo_especialidad, "
                            + " id_profesional, codigo_entidad, numero_autorizacion, observaciones, "
                            + " usuario, estado, codigo_observacion)"
                            + " VALUES "
                            + " ( '" + cita.getPaciente().getIdentificacion() + "', '" + formatoFecha.format(cita.getListaProcedimientos().get(0).getFecha()) + "',"
                            + " '" + formatoHora.format(cita.getListaProcedimientos().get(0).getHora()) + "', '" + cita.getEspecialidad().getCodigo() + "', "
                            + " '" + cita.getProfesional().getCedula() + "', '" + cita.getEntidad().getCodigo() + "', "
                            + " '" + cita.getNumeroAutorizacion() + "', '" + cita.getObservaciones() + "', "
                            + " '" + cita.getUsuario().getUsuario() + "','" + cita.getEstado().getCodigo() + "','" + cita.getCodigoObservacion() + "') returning codigo;";
                    rs = consulta.ejecutar(sql);
                    if (rs.next()) {
                        resultado = rs.getInt("codigo");
                    }
                    if (codigoTerapia > 0) {
                        sql = " insert into detalle_terapia(consecutivo, codigo_terapia, cod_cita) "
                                + " select coalesce(max(consecutivo),0)+1," + codigoTerapia + "," + resultado + " consecutivo from detalle_terapia where codigo_terapia=" + codigoTerapia;
                        consulta.actualizar(sql);
                    }
                    for (Procedimiento pro : cita.getListaProcedimientos()) {
                        sql = " INSERT INTO rel_procedimientos_cita("
                                + " codigo_procedimiento, fecha, hora, codigo_cita)"
                                + " VALUES( "
                                + " '" + pro.getCodigo() + "', "
                                + " '" + formatoFecha.format(pro.getFecha()) + "', "
                                + " '" + formatoHora.format(pro.getHora()) + "', "
                                + " " + resultado + ");";
                        consulta.actualizar(sql);

                        sql = "update agenda set codigo_cita='" + resultado + "' "
                                + " where "
                                + " cedula_profesional ='" + cita.getProfesional().getCedula() + "' and "
                                + " fecha='" + formatoFecha.format(pro.getFecha()) + "' and "
                                + " hora='" + formatoHora.format(pro.getHora()) + "'; ";
                        consulta.actualizar(sql);
                    }

                    sql = " commit;";
                    consulta.actualizar(sql);
                } else {//si la cita no tiene varios procedimientos, se supone que es cita multiple o cita uno y uno
                    //que por normalizacion las citas vienen en el arreglo listaCitaMultiple

                    //si el procedimiento es de tipo terapia
                    //se recorren los pacientes buscando que tengan terapias activas para el procedimiento
                    sql = " begin;";
                    consulta.actualizar(sql);
                    for (Cita c : listaCitaMultiple) {

                        //if (cita.getProcedimiento().getTipo() == 1) {
                        if (c.getListaProcedimientos().get(0).getTipo() == 1) {
                            //aqui tiene sentido analizar solo un procedimiento para terapia
                            sql = "select coalesce(codigo,-1) codigo from ( "
                                    + " select codigo,1 j from terapia where id_paciente='" + c.getPaciente().getIdentificacion() + "' and codigo_procedimiento='" + c.getListaProcedimientos().get(0).getCodigo() + "' and activa=true and cantidad_pendiente>=0 "
                                    + " ) c right join (select 1 j) sc using (j)";
                            rs = consulta.ejecutar(sql);
                            if (rs.next()) {
                                codigoTerapia = rs.getInt("codigo");
                            }
                            if (codigoTerapia == -1) {//si algun paciente no tiene terapia activa, no se realiza ningun cambio
                                sql = " rollback;";
                                consulta.actualizar(sql);
                                return -1;
                            }
                        }

                        //validar duplicidad (fecha, hora, profesional, estado)
                        sql = " select count(*) cantidad from citas c "
                                + " inner join (lista l inner join detalle_lista dl on (l.codigo=dl.codigo_lista and l.nombre='ESTADOS_CITA')) ec on (c.estado=ec.value) "
                                + " where "
                                + " fecha='" + formatoFecha.format(c.getFecha()) + "' "
                                + " and hora='" + formatoHora.format(c.getHora()) + "' "
                                + " and id_profesional='" + cita.getProfesional().getCedula() + "' and ec.label='Programada' ";
                        rs = consulta.ejecutar(sql);
                        if (rs.next()) {
                            cantidad = rs.getInt("cantidad");
                        }
                        if (cantidad > 0) {//ya existe una cita
                            sql = " rollback;";
                            consulta.actualizar(sql);
                            return -2;
                        }

                        sql = " INSERT INTO citas("
                                + " id_paciente, fecha, hora,  codigo_especialidad, "
                                + " id_profesional, codigo_entidad, numero_autorizacion, observaciones, "
                                + " usuario, estado, codigo_observacion)"
                                + " VALUES "
                                + " ( '" + c.getPaciente().getIdentificacion() + "', '" + formatoFecha.format(c.getFecha()) + "',"
                                + " '" + formatoHora.format(c.getHora()) + "', '" + cita.getEspecialidad().getCodigo() + "', "
                                + " '" + cita.getProfesional().getCedula() + "', '" + c.getPaciente().getEntidad().getCodigo() + "', "
                                + " '" + cita.getNumeroAutorizacion() + "', '" + cita.getObservaciones() + "', "
                                + " '" + cita.getUsuario().getUsuario() + "','" + cita.getEstado().getCodigo() + "','" + cita.getCodigoObservacion() + "') returning codigo;";
                        rs = consulta.ejecutar(sql);
                        if (rs.next()) {
                            resultado = rs.getInt("codigo");
                        }
                        // =0 viene sin modificar o reseteado del ultimo ciclo, supone que no es procedimiento tipo 1  >0 procedimiento tipo 1 <0 deberia haber retornado -1
                        if (codigoTerapia > 0) {
                            sql = " insert into detalle_terapia(consecutivo, codigo_terapia, cod_cita) "
                                    + " select coalesce(max(consecutivo),0)+1," + codigoTerapia + "," + resultado + " consecutivo "
                                    + " from detalle_terapia where codigo_terapia=" + codigoTerapia;
                            consulta.actualizar(sql);
                        }
                        codigoTerapia = 0;
                        //for (Procedimiento pro : cita.getListaProcedimientos()) {
                        sql = " INSERT INTO rel_procedimientos_cita("
                                + " codigo_procedimiento, fecha, hora, codigo_cita)"
                                + " VALUES( "
                                + " '" + c.getListaProcedimientos().get(0).getCodigo() + "', "
                                + " '" + formatoFecha.format(c.getFecha()) + "', "
                                + " '" + formatoHora.format(c.getHora()) + "', "
                                + " " + resultado + ");";
                        consulta.actualizar(sql);

                        sql = "update agenda set codigo_cita='" + resultado + "' "
                                + " where "
                                + " cedula_profesional ='" + cita.getProfesional().getCedula() + "' and "
                                + " fecha='" + formatoFecha.format(c.getFecha()) + "' and "
                                + " hora='" + formatoHora.format(c.getHora()) + "'; ";
                        consulta.actualizar(sql);
                        //}
                    }
                    sql = " commit;";
                    consulta.actualizar(sql);
                }

            } else {
                sql = "begin;";
                consulta.actualizar(sql);
                sql = "update citas set "
                        + " id_paciente='" + cita.getPaciente().getIdentificacion() + "',"
                        + " estado='" + cita.getEstado().getCodigo() + "', "
                        + " motivo='" + cita.getMotivo() + "', "
                        + " fecha_registro_estado=current_timestamp, "
                        + " responsable='" + cita.getResponsable() + "', "
                        + " medio='" + cita.getMedio() + "', "
                        + " numero_autorizacion='" + cita.getNumeroAutorizacion() + "' "
                        + " where codigo=" + cita.getCodigo();
                resultado = consulta.actualizar(sql);
                // "4";"Cancelada Entidad" "3";"Cancelada Paciente"
                //liberar la agenda si la opcion fue cancelar
                if (cita.getEstado().getCodigo().equalsIgnoreCase("3") || cita.getEstado().getCodigo().equalsIgnoreCase("4")) {
                    sql = "update agenda set codigo_cita = null where codigo_cita=" + cita.getCodigo();
                    consulta.actualizar(sql);
                }
                //elimina el detalle_terapia si la cita es de terapia
                if(cita.getListaProcedimientos().get(0).getTipo() == 1) {
                    sql = "delete from detalle_terapia where cod_cita = " + cita.getCodigo();
                    consulta.actualizar(sql);
                }
                sql = " commit;";
                consulta.actualizar(sql);

            }
            return resultado;
        } catch (SQLException ex) {
            throw ex;
        } finally {
            consulta.desconectar();
        }
    }

    public List<Cita> consultarCitas(Paciente paciente, Date fecha, Integer limite) throws SQLException {
        Cita cita;
        Profesional pro;
        Especialidad e;
        Paciente pac;
        List<Cita> listaCitas = new ArrayList<>();
        Consulta consulta = null;
        Integer resultado = 0;
        String sql;
        ResultSet rs;
        Boolean primero = Boolean.TRUE;
        SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd");
        //SimpleDateFormat formatoHora = new SimpleDateFormat("HH:mm:ss");
        SimpleDateFormat formatoFechaHora = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String filtro = "";
        if (fecha != null) {
            filtro = " c.fecha='" + formatoFecha.format(fecha) + "'";
        }
        if (!paciente.getIdentificacion().equalsIgnoreCase("")) {
            filtro = filtro.equalsIgnoreCase("") ? " c.id_paciente='" + paciente.getIdentificacion() + "'" : filtro + " AND c.id_paciente='" + paciente.getIdentificacion() + "' ";
        } else if (paciente.getNombreCompleto() != null) {
            filtro = filtro.equalsIgnoreCase("") ? " pac.nombre||(case when character_length(coalesce(pac.segundo_nombre,''))=0 then '' else ' '||pac.segundo_nombre end)||(case when character_length(coalesce(pac.primer_apellido,''))=0 then '' else ' '||pac.primer_apellido end)||(case when character_length(coalesce(pac.segundo_apellido,''))=0 then '' else ' '||pac.segundo_apellido end)='" + paciente.getNombreCompleto() + "'" : filtro + " AND pac.nombre||(case when character_length(coalesce(pac.segundo_nombre,''))=0 then '' else ' '||pac.segundo_nombre end)||(case when character_length(coalesce(pac.primer_apellido,''))=0 then '' else ' '||pac.primer_apellido end)||(case when character_length(coalesce(pac.segundo_apellido,''))=0 then '' else ' '||pac.segundo_apellido end)='" + paciente.getNombreCompleto() + "' ";
        }
        try {
            consulta = new Consulta(getConexion());
            sql = "select "
                    + " pac.nombre||(case when character_length(coalesce(pac.segundo_nombre,''))=0 then '' else ' '||pac.segundo_nombre end)||(case when character_length(coalesce(pac.primer_apellido,''))=0 then '' else ' '||pac.primer_apellido end)||(case when character_length(coalesce(pac.segundo_apellido,''))=0 then '' else ' '||pac.segundo_apellido end) nombre_completo, "
                    + " c.codigo codigo_cita,c.id_paciente,pac.nombre nombre_paciente,"
                    + " c.fecha,c.hora,c.codigo_especialidad,e.nombre nombre_especialidad,"
                    + " c.id_profesional,pro.nombre nombre_profesional, dl.value codigo_estado,dl.label nombre_estado,"
                    + " c.motivo, c.fecha_registro_estado, c.medio, c.responsable, c.numero_autorizacion "
                    + " from"
                    + " citas c"
                    + " inner join especialidades e on (e.codigo=c.codigo_especialidad)"
                    + " inner join profesionales pro on (pro.cedula=c.id_profesional)"
                    + " inner join pacientes pac on (pac.identificacion=c.id_paciente)"
                    + " inner join detalle_lista dl on (dl.value=c.estado)"
                    + " inner join lista l on (l.codigo=dl.codigo_lista and l.nombre='ESTADOS_CITA')"
                    + " where "
                    + filtro
                    + " order by c.fecha desc,c.hora desc ";
            if (limite > 0) {
                sql += "limit " + limite;
            }
            rs = consulta.ejecutar(sql);
            while (rs.next()) {
                cita = new Cita();
                pro = new Profesional();
                e = new Especialidad();
                pac = new Paciente();
                cita.setListaProcedimientos(new ArrayList<Procedimiento>());
                pro.setCedula(rs.getString("id_profesional"));
                pro.setNombre(rs.getString("nombre_profesional"));
                e.setCodigo(rs.getString("codigo_especialidad"));
                e.setNombre(rs.getString("nombre_especialidad"));
                pac.setIdentificacion(rs.getString("id_paciente"));
                pac.setNombre(rs.getString("nombre_paciente"));
                pac.setNombreCompleto(rs.getString("nombre_completo"));
                cita.setCodigo(rs.getInt("codigo_cita"));
                cita.setPaciente(pac);
                cita.setFecha(rs.getDate("fecha"));
                cita.setHora(rs.getTime("hora"));
                cita.setEspecialidad(e);
                cita.setProfesional(pro);
                cita.setEstado(new Objeto(rs.getString("codigo_estado"), rs.getString("nombre_estado")));
                //c.motivo, c.fecha_registro_estado, c.medio, c.responsable, c.numero_autorizacion
                cita.setMotivo(rs.getString("motivo"));
                cita.setFechaRegistroEstado(rs.getDate("fecha_registro_estado"));
                cita.setMedio(rs.getString("medio"));
                cita.setResponsable(rs.getString("responsable"));
                cita.setNumeroAutorizacion(rs.getString("numero_autorizacion"));

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

    public List<Cita> consultarCitasProfesionalDia(String identificacion) throws SQLException {
        Cita cita;
        Profesional pro;
        Especialidad e;
        Entidad ent;
        Paciente pac;
        List<Cita> listaCitas = new ArrayList<>();
        List<Procedimiento> listaProcedimientos;
        Consulta consulta = null;
        String sql;
        ResultSet rs;

        try {
            consulta = new Consulta(getConexion());
            sql
                    = " select "
                    + " c.codigo codigo_cita,c.id_paciente, "
                    + " pac.nombre nombre_paciente, "
                    + " pac.nombre||(case when character_length(coalesce(pac.segundo_nombre,''))=0 then '' else ' '||pac.segundo_nombre end)||(case when character_length(coalesce(pac.primer_apellido,''))=0 then '' else ' '||pac.primer_apellido end)||(case when character_length(coalesce(pac.segundo_apellido,''))=0 then '' else ' '||pac.segundo_apellido end) nombre_completo, "
                    + " pac.fecha_nacimiento, "
                    + " ((current_date-pac.fecha_nacimiento)/365)::integer edad, "
                    + " l_sexo.label sexo, "
                    + " l_estado_civil.label estado_civil, "
                    + " pac.ocupacion, pac.grado_escolar, ent.nombre nombre_entidad,"
                    + " l_tipo_afiliacion.label tipo_afiliacion, "
                    + " l_tipo_identificacion.label tipo_identificacion, "
                    + " pac.direccion1, pac.telefono1, pac.telefono2,"
                    + " c.fecha,c.hora,c.codigo_especialidad,e.nombre nombre_especialidad, "
                    + " c.id_profesional, pro.nombre nombre_profesional, "
                    + " l_estados_cita.value codigo_estado,l_estados_cita.label nombre_estado, "
                    + " l_condicion.value condicion, "
                    + " proc.codigo codigo_procedimiento, proc.nombre nombre_procedimiento, rpc.fecha fecha_procedimiento, "
                    + " rpc.hora hora_procedimiento, proc.tipo tipo_procedimiento, "
                    + " case when v.codigo is null and est.codigo is null then false else true end tiene_atencion"
                    + " from "
                    + " citas c "
                    + " inner join rel_procedimientos_cita rpc on (c.codigo=rpc.codigo_cita) "
                    + " inner join procedimientos proc on (rpc.codigo_procedimiento=proc.codigo) "
                    + " inner join especialidades e on (e.codigo=c.codigo_especialidad) "
                    + " inner join profesionales pro on (pro.cedula=c.id_profesional) "
                    + " inner join pacientes pac on (pac.identificacion=c.id_paciente) "
                    + " left join entidades ent ON (ent.codigo=pac.entidad) "
                    + " left join valoracion v on (c.codigo=v.cod_cita) " //inactivacion boton atender
                    + " left join estudio_audiologico est on (c.codigo=est.cod_cita) " //inactivacion boton atender
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
                    + " left join (lista l "
                    + " inner join detalle_lista dl on (l.codigo=dl.codigo_lista and l.nombre='CONDICION_TERAPIA') ) l_condicion on (l_condicion.value=pac.condicion) "
                    + " where "
                    + " id_profesional='" + identificacion + "' and c.fecha=current_date "
                    //+ " and l_estados_cita.label in ('Programada','Paciente en sala') "
                    + " order by c.fecha asc,c.hora asc ";
            rs = consulta.ejecutar(sql);
            while (rs.next()) {
                pac = new Paciente();
                pac.setIdentificacion(rs.getString("id_paciente"));
                pac.setNombre(rs.getString("nombre_paciente"));
                pac.setNombreCompleto(rs.getString("nombre_completo"));

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
                pac.setTelefono2(rs.getString("telefono2"));
                pac.setCondicion(rs.getString("condicion"));

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

                listaProcedimientos = new ArrayList<>();
                listaProcedimientos.add(new Procedimiento(rs.getString("codigo_procedimiento"), rs.getString("nombre_procedimiento"), rs.getDate("fecha_procedimiento"), rs.getTime("hora_procedimiento"), rs.getInt("tipo_procedimiento")));
                cita.setListaProcedimientos(listaProcedimientos);
                cita.setTieneAtencion(rs.getBoolean("tiene_atencion"));

                listaCitas.add(cita);
            }

            return listaCitas;
        } catch (SQLException ex) {
            throw ex;
        } finally {
            consulta.desconectar();
        }
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
