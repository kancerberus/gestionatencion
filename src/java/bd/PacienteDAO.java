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
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import modelo.ControlExport;
import modelo.Entidad;
import modelo.Municipio;
import modelo.Paciente;

/**
 *
 * @author Andres
 */
public class PacienteDAO {

    private Connection conexion;

    public PacienteDAO(Connection conexion) {
        this.conexion = conexion;
    }

    public ArrayList<Paciente> listarPacientesPatron(String patron) throws SQLException {
        Paciente paciente;
        Entidad entidad;
        Municipio ciudadResidencia;
        Municipio ciudadNacimiento;
        ArrayList<Paciente> listaPacientes = new ArrayList<>();
        ResultSet rs;
        Consulta consulta = null;
        try {
            consulta = new Consulta(getConexion());
            String sql
                    = " select "
                    + " p.nombre||(case when character_length(coalesce(p.segundo_nombre,''))=0 then '' else ' '||p.segundo_nombre end)||(case when character_length(coalesce(p.primer_apellido,''))=0 then '' else ' '||p.primer_apellido end)||(case when character_length(coalesce(p.segundo_apellido,''))=0 then '' else ' '||p.segundo_apellido end) nombre_completo, "
                    + " identificacion,p.nombre,historia_clinica,entidad,tipo_usuario,fecha_nacimiento,edad,direccion1, "
                    + " direccion2,telefono1,telefono2,ciudad_residencia,cr.nombre nombre_ciudad_residencia,ciudad_nacimiento,cn.nombre nombre_ciudad_nacimiento,"
                    + " fecha_apertura,padres,p.correo_electronico "
                    + " from "
                    + " pacientes p"
                    + " inner join municipios cr on (p.ciudad_residencia=cr.codigo) "
                    + " inner join municipios cn on (p.ciudad_nacimiento=cn.codigo) "
                    + " where p.nombre||(case when character_length(coalesce(p.segundo_nombre,''))=0 then '' else ' '||p.segundo_nombre end)||(case when character_length(coalesce(p.primer_apellido,''))=0 then '' else ' '||p.primer_apellido end)||(case when character_length(coalesce(p.segundo_apellido,''))=0 then '' else ' '||p.segundo_apellido end) ilike '%" + patron + "%';";

            rs = consulta.ejecutar(sql);

            while (rs.next()) {
                paciente = new Paciente();
                paciente.setIdentificacion(rs.getString("identificacion"));
                paciente.setNombre(rs.getString("nombre"));
                paciente.setNombreCompleto(rs.getString("nombre_completo"));
                paciente.setHistoriaClinica(rs.getString("historia_clinica"));
                entidad = new Entidad();
                entidad.setCodigo(rs.getString("entidad"));
                paciente.setEntidad(entidad);
                paciente.setTipoUsuario(rs.getString("tipo_usuario"));
                paciente.setFechaNacimiento(rs.getDate("fecha_nacimiento"));
                paciente.setEdad(rs.getString("edad"));
                paciente.setDireccion1(rs.getString("direccion1"));
                paciente.setDireccion2(rs.getString("direccion2"));
                paciente.setTelefono1(rs.getString("telefono1"));
                paciente.setTelefono2(rs.getString("telefono2"));
                ciudadResidencia = new Municipio(rs.getString("ciudad_residencia"), rs.getString("nombre_ciudad_residencia"));
                ciudadNacimiento = new Municipio(rs.getString("ciudad_nacimiento"), rs.getString("nombre_ciudad_nacimiento"));
                paciente.setCiudadResidencia(ciudadResidencia);
                paciente.setCiudadNacimiento(ciudadNacimiento);
                paciente.setFechaApertura(rs.getDate("fecha_apertura"));
                paciente.setPadres(rs.getString("padres"));
                paciente.setEmail(rs.getString("correo_electronico"));

                listaPacientes.add(paciente);

            }
            return listaPacientes;

        } catch (SQLException ex) {
            throw ex;
        } finally {
            consulta.desconectar();
        }
    }

    public Paciente consultarPacientePorNombre(String nombre) throws SQLException {
        Paciente paciente = null;
        Entidad entidad;
        Municipio ciudadResidencia;
        Municipio ciudadNacimiento;
        ResultSet rs;
        Consulta consulta = null;
        try {
            consulta = new Consulta(getConexion());
            String sql
                    = " select "
                    + " identificacion,p.nombre,historia_clinica,entidad,tipo_usuario,fecha_nacimiento,edad,direccion1, "
                    + " direccion2,telefono1,telefono2,ciudad_residencia,cr.nombre nombre_ciudad_residencia,ciudad_nacimiento,cn.nombre nombre_ciudad_nacimiento,"
                    + " fecha_apertura,padres,p.correo_electronico "
                    + " from "
                    + " pacientes p"
                    + " inner join municipios cr on (p.ciudad_residencia=cr.codigo) "
                    + " inner join municipios cn on (p.ciudad_nacimiento=cn.codigo) "
                    + " where p.nombre = '" + nombre + "';";

            rs = consulta.ejecutar(sql);

            if (rs.next()) {
                paciente = new Paciente();
                paciente.setIdentificacion(rs.getString("identificacion"));
                paciente.setNombre(rs.getString("nombre"));
                paciente.setHistoriaClinica(rs.getString("historia_clinica"));
                entidad = new Entidad();
                entidad.setCodigo(rs.getString("entidad"));
                paciente.setEntidad(entidad);
                paciente.setTipoUsuario(rs.getString("tipo_usuario"));
                paciente.setFechaNacimiento(rs.getDate("fecha_nacimiento"));
                paciente.setEdad(rs.getString("edad"));
                paciente.setDireccion1(rs.getString("direccion1"));
                paciente.setDireccion2(rs.getString("direccion2"));
                paciente.setTelefono1(rs.getString("telefono1"));
                paciente.setTelefono2(rs.getString("telefono2"));
                ciudadResidencia = new Municipio(rs.getString("ciudad_residencia"), rs.getString("nombre_ciudad_residencia"));
                ciudadNacimiento = new Municipio(rs.getString("ciudad_nacimiento"), rs.getString("nombre_ciudad_nacimiento"));
                paciente.setCiudadResidencia(ciudadResidencia);
                paciente.setCiudadNacimiento(ciudadNacimiento);
                paciente.setFechaApertura(rs.getDate("fecha_apertura"));
                paciente.setPadres(rs.getString("padres"));
                paciente.setEmail(rs.getString("correo_electronico"));
            }
            return paciente;

        } catch (SQLException ex) {
            throw ex;
        } finally {
            consulta.desconectar();
        }
    }

    public Paciente consultarPacientePorId(String identificacion) throws SQLException {
        Paciente paciente = null;
        ResultSet rs;
        Consulta consulta = null;
        try {
            consulta = new Consulta(getConexion());
            String sql
                    = " select "
                    + " p.nombre||(case when character_length(coalesce(p.segundo_nombre,''))=0 then '' else ' '||p.segundo_nombre end)||(case when character_length(coalesce(p.primer_apellido,''))=0 then '' else ' '||p.primer_apellido end)||(case when character_length(coalesce(p.segundo_apellido,''))=0 then '' else ' '||p.segundo_apellido end) nombre_completo, "
                    + " identificacion,p.nombre,historia_clinica,entidad,e.nombre nombre_entidad,tipo_usuario,fecha_nacimiento,edad,direccion1, "
                    + " direccion2,telefono1,telefono2,ciudad_residencia,cr.nombre nombre_ciudad_residencia,ciudad_nacimiento,cn.nombre nombre_ciudad_nacimiento, "
                    + " fecha_apertura,padres,p.correo_electronico, "
                    + " l_sexo.value sexo, l_sexo.label sexo2,"
                    + " l_estado_civil.value estado_civil, l_estado_civil.label estado_civil2,"
                    + " p.ocupacion, "
                    + " p.grado_escolar, "
                    + " l_tipo_afiliacion.value tipo_afiliacion, l_tipo_afiliacion.label tipo_afiliacion2,"
                    + " l_tipo_identificacion.value tipo_identificacion, l_tipo_identificacion.label tipo_identificacion2,"
                    + " l_condicion.value condicion, "
                    + " p.nombre, p.segundo_nombre, p.primer_apellido, p.segundo_apellido"
                    + " from "
                    + " pacientes p "
                    + " inner join municipios cr on (p.ciudad_residencia=cr.codigo) "
                    + " inner join municipios cn on (p.ciudad_nacimiento=cn.codigo) "
                    + " inner join entidades e on (p.entidad=e.codigo) "
                    + " left join (lista l "
                    + " inner join detalle_lista dl on (l.codigo=dl.codigo_lista and l.nombre='SEXO') ) l_sexo on (l_sexo.value=p.sexo) "
                    + " left join (lista l "
                    + " inner join detalle_lista dl on (l.codigo=dl.codigo_lista and l.nombre='ESTADO_CIVIL') ) l_estado_civil on (l_estado_civil.value=p.estado_civil) "
                    + " left join (lista l "
                    + " inner join detalle_lista dl on (l.codigo=dl.codigo_lista and l.nombre='TIPO_AFILIACION') ) l_tipo_afiliacion on (l_tipo_afiliacion.value=p.tipo_afiliacion) "
                    + " left join (lista l "
                    + " inner join detalle_lista dl on (l.codigo=dl.codigo_lista and l.nombre='TIPO_IDENTIFICACION') ) l_tipo_identificacion on (l_tipo_identificacion.value=p.tipo_identificacion) "
                    + " left join (lista l "
                    + " inner join detalle_lista dl on (l.codigo=dl.codigo_lista and l.nombre='CONDICION_TERAPIA') ) l_condicion on (l_condicion.value=p.condicion) "
                    + " where p.identificacion = '" + identificacion + "' ";

            rs = consulta.ejecutar(sql);

            if (rs.next()) {
                paciente = new Paciente();
                paciente.setIdentificacion(rs.getString("identificacion"));
                paciente.setNombre(rs.getString("nombre"));
                paciente.setSegundoNombre(rs.getString("segundo_nombre"));
                paciente.setPrimerApellido(rs.getString("primer_apellido"));
                paciente.setSegundoApellido(rs.getString("segundo_apellido"));

                paciente.setNombreCompleto(rs.getString("nombre_completo"));
                paciente.setHistoriaClinica(rs.getString("historia_clinica"));
                paciente.setTipoUsuario(rs.getString("tipo_usuario"));

                paciente.setEntidad(new Entidad(rs.getString("entidad"), rs.getString("nombre_entidad")));
                paciente.setCiudadResidencia(new Municipio(rs.getString("ciudad_residencia"), rs.getString("nombre_ciudad_residencia")));
                paciente.setCiudadNacimiento(new Municipio(rs.getString("ciudad_nacimiento"), rs.getString("nombre_ciudad_nacimiento")));

                paciente.setFechaNacimiento(rs.getDate("fecha_nacimiento"));
                Date fa = new Date();
                Calendar fechaNacimiento = Calendar.getInstance();
                Calendar fechaActual = Calendar.getInstance();
                fechaNacimiento.setTime(paciente.getFechaNacimiento());
                fechaActual.setTime(fa);
                Long resta = (fechaNacimiento.getTimeInMillis() < 0 ? fechaActual.getTimeInMillis() + (fechaNacimiento.getTimeInMillis() * (-1)) : fechaActual.getTimeInMillis() - fechaNacimiento.getTimeInMillis()) / 1000 / 60 / 60 / 24 / 365;
                Integer edad = resta.intValue();
                paciente.setEdad(edad.toString());

                paciente.setDireccion1(rs.getString("direccion1"));
                paciente.setDireccion2(rs.getString("direccion2"));
                paciente.setTelefono1(rs.getString("telefono1"));
                paciente.setTelefono2(rs.getString("telefono2"));

                paciente.setFechaApertura(rs.getDate("fecha_apertura"));
                paciente.setPadres(rs.getString("padres"));
                paciente.setEmail(rs.getString("correo_electronico"));

                paciente.setSexo(rs.getString("sexo"));
                paciente.setSexo2(rs.getString("sexo2"));
                paciente.setGradoEscolar(rs.getString("grado_escolar"));
                paciente.setOcupacion(rs.getString("ocupacion"));
                paciente.setEstadoCivil(rs.getString("estado_civil"));
                paciente.setEstadoCivil2(rs.getString("estado_civil2"));
                paciente.setTipoAfiliacion(rs.getString("tipo_afiliacion"));
                paciente.setTipoAfiliacion2(rs.getString("tipo_afiliacion2"));
                paciente.setTipoIdentificacion(rs.getString("tipo_identificacion"));
                paciente.setTipoIdentificacion2(rs.getString("tipo_identificacion2"));
                paciente.setCondicion(rs.getString("condicion"));

            }
            return paciente;

        } catch (SQLException ex) {
            throw ex;
        } finally {
            consulta.desconectar();
        }
    }

    public Paciente consultarPacientePorNombreCompleto(String nombre) throws SQLException {
        Paciente paciente = null;
        ResultSet rs;
        Consulta consulta = null;
        try {
            consulta = new Consulta(getConexion());
            String sql
                    = " select "
                    + " p.nombre||(case when character_length(coalesce(p.segundo_nombre,''))=0 then '' else ' '||p.segundo_nombre end)||(case when character_length(coalesce(p.primer_apellido,''))=0 then '' else ' '||p.primer_apellido end)||(case when character_length(coalesce(p.segundo_apellido,''))=0 then '' else ' '||p.segundo_apellido end) nombre_completo, "
                    + " identificacion,p.nombre,historia_clinica,entidad,e.nombre nombre_entidad,tipo_usuario,fecha_nacimiento,edad,direccion1, "
                    + " direccion2,telefono1,telefono2,ciudad_residencia,cr.nombre nombre_ciudad_residencia,ciudad_nacimiento,cn.nombre nombre_ciudad_nacimiento, "
                    + " fecha_apertura,padres,p.correo_electronico, "
                    + " l_sexo.value sexo, "
                    + " l_estado_civil.value estado_civil, "
                    + " p.ocupacion, "
                    + " p.grado_escolar, "
                    + " l_tipo_afiliacion.value tipo_afiliacion, "
                    + " l_tipo_identificacion.value tipo_identificacion "
                    + " from "
                    + " pacientes p "
                    + " inner join municipios cr on (p.ciudad_residencia=cr.codigo) "
                    + " inner join municipios cn on (p.ciudad_nacimiento=cn.codigo) "
                    + " inner join entidades e on (p.entidad=e.codigo) "
                    + " left join (lista l "
                    + " inner join detalle_lista dl on (l.codigo=dl.codigo_lista and l.nombre='SEXO') ) l_sexo on (l_sexo.value=p.sexo) "
                    + " left join (lista l "
                    + " inner join detalle_lista dl on (l.codigo=dl.codigo_lista and l.nombre='ESTADO_CIVIL') ) l_estado_civil on (l_estado_civil.value=p.estado_civil) "
                    + " left join (lista l "
                    + " inner join detalle_lista dl on (l.codigo=dl.codigo_lista and l.nombre='TIPO_AFILIACION') ) l_tipo_afiliacion on (l_tipo_afiliacion.value=p.tipo_afiliacion) "
                    + " left join (lista l "
                    + " inner join detalle_lista dl on (l.codigo=dl.codigo_lista and l.nombre='TIPO_IDENTIFICACION') ) l_tipo_identificacion on (l_tipo_identificacion.value=p.tipo_identificacion) "
                    + " where p.nombre||(case when character_length(coalesce(p.segundo_nombre,''))=0 then '' else ' '||p.segundo_nombre end)||(case when character_length(coalesce(p.primer_apellido,''))=0 then '' else ' '||p.primer_apellido end)||(case when character_length(coalesce(p.segundo_apellido,''))=0 then '' else ' '||p.segundo_apellido end) = '" + nombre + "' ";

            rs = consulta.ejecutar(sql);

            if (rs.next()) {
                paciente = new Paciente();
                paciente.setIdentificacion(rs.getString("identificacion"));
                paciente.setNombre(rs.getString("nombre"));
                paciente.setNombreCompleto(rs.getString("nombre_completo"));
                paciente.setHistoriaClinica(rs.getString("historia_clinica"));
                paciente.setTipoUsuario(rs.getString("tipo_usuario"));

                paciente.setEntidad(new Entidad(rs.getString("entidad"), rs.getString("nombre_entidad")));
                paciente.setCiudadResidencia(new Municipio(rs.getString("ciudad_residencia"), rs.getString("nombre_ciudad_residencia")));
                paciente.setCiudadNacimiento(new Municipio(rs.getString("ciudad_nacimiento"), rs.getString("nombre_ciudad_nacimiento")));

                paciente.setFechaNacimiento(rs.getDate("fecha_nacimiento"));
                Date fa = new Date();
                Calendar fechaNacimiento = Calendar.getInstance();
                Calendar fechaActual = Calendar.getInstance();
                fechaNacimiento.setTime(paciente.getFechaNacimiento());
                fechaActual.setTime(fa);
                Long resta = (fechaNacimiento.getTimeInMillis() < 0 ? fechaActual.getTimeInMillis() + (fechaNacimiento.getTimeInMillis() * (-1)) : fechaActual.getTimeInMillis() - fechaNacimiento.getTimeInMillis()) / 1000 / 60 / 60 / 24 / 365;
                Integer edad = resta.intValue();
                paciente.setEdad(edad.toString());

                paciente.setDireccion1(rs.getString("direccion1"));
                paciente.setDireccion2(rs.getString("direccion2"));
                paciente.setTelefono1(rs.getString("telefono1"));
                paciente.setTelefono2(rs.getString("telefono2"));

                paciente.setFechaApertura(rs.getDate("fecha_apertura"));
                paciente.setPadres(rs.getString("padres"));
                paciente.setEmail(rs.getString("correo_electronico"));

                paciente.setSexo(rs.getString("sexo"));
                paciente.setGradoEscolar(rs.getString("grado_escolar"));
                paciente.setOcupacion(rs.getString("ocupacion"));
                paciente.setEstadoCivil(rs.getString("estado_civil"));
                paciente.setTipoAfiliacion(rs.getString("tipo_afiliacion"));
                paciente.setTipoIdentificacion(rs.getString("tipo_identificacion"));

            }
            return paciente;

        } catch (SQLException ex) {
            throw ex;
        } finally {
            consulta.desconectar();
        }
    }

    public Integer guardarPaciente(Paciente paciente, Boolean existe) throws SQLException {
        Consulta consulta = null;
        Integer resultado;
        SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd");
        String sql = "";
        try {
            consulta = new Consulta(getConexion());
            if (existe) {
                sql = "UPDATE pacientes "
                        + " SET nombre =  trim('" + paciente.getNombre() + "'), entidad =  '" + paciente.getEntidad().getCodigo() + "', tipo_usuario =  '" + paciente.getTipoUsuario() + "',"
                        + " fecha_nacimiento =  '" + formatoFecha.format(paciente.getFechaNacimiento()) + "', direccion1 =  '" + paciente.getDireccion1() + "', direccion2 =  '" + paciente.getDireccion2() + "',"
                        + " telefono1 =  '" + paciente.getTelefono1() + "', telefono2 =  '" + paciente.getTelefono2() + "', ciudad_residencia =  '" + paciente.getCiudadResidencia().getCodigo() + "', ciudad_nacimiento =  '" + paciente.getCiudadNacimiento().getCodigo() + "',"
                        + " padres = '" + paciente.getPadres() + "', correo_electronico ='" + paciente.getEmail() + "',"
                        + " sexo = '" + paciente.getSexo() + "',"
                        + " ocupacion = '" + paciente.getOcupacion() + "',"
                        + " estado_civil = '" + paciente.getEstadoCivil() + "',"
                        + " tipo_afiliacion = '" + paciente.getTipoAfiliacion() + "',"
                        + " grado_escolar = '" + paciente.getGradoEscolar() + "',"
                        + " tipo_identificacion = '" + paciente.getTipoIdentificacion() + "',"
                        + " segundo_nombre = trim('" + paciente.getSegundoNombre() + "'),"
                        + " primer_apellido = trim('" + paciente.getPrimerApellido() + "'),"
                        + " segundo_apellido = trim('" + paciente.getSegundoApellido() + "'),"
                        + " condicion = '" + paciente.getCondicion() + "'"
                        + " WHERE"
                        + " identificacion = '" + paciente.getIdentificacion() + "';";
            } else {
                sql
                        = "INSERT INTO pacientes("
                        + "identificacion, nombre, entidad, "
                        + "tipo_usuario, fecha_nacimiento, "
                        + "edad, direccion1, direccion2, "
                        + "telefono1, telefono2, ciudad_residencia, "
                        + "ciudad_nacimiento, fecha_apertura, padres, correo_electronico,"
                        + "sexo, ocupacion, estado_civil, tipo_afiliacion, grado_escolar, tipo_identificacion, segundo_nombre, primer_apellido, segundo_apellido, "
                        + "condicion) "
                        + "VALUES "
                        + "('" + paciente.getIdentificacion() + "', trim('" + paciente.getNombre() + "'), '" + paciente.getEntidad().getCodigo() + "', "
                        + "'" + paciente.getTipoUsuario() + "', '" + formatoFecha.format(paciente.getFechaNacimiento()) + "', "
                        + "'" + paciente.getEdad() + "', '" + paciente.getDireccion1() + "', '" + paciente.getDireccion2() + "', "
                        + "'" + paciente.getTelefono1() + "', '" + paciente.getTelefono2() + "', '" + paciente.getCiudadResidencia().getCodigo() + "', "
                        + "'" + paciente.getCiudadNacimiento().getCodigo() + "', current_date, '" + paciente.getPadres() + "','" + paciente.getEmail() + "',"
                        + "'" + paciente.getSexo() + "','" + paciente.getOcupacion() + "','" + paciente.getEstadoCivil() + "','" + paciente.getTipoAfiliacion() + "',"
                        + "'" + paciente.getGradoEscolar() + "','" + paciente.getTipoIdentificacion() + "',"
                        + "trim('" + paciente.getSegundoNombre() + "'),trim('" + paciente.getPrimerApellido() + "'),trim('" + paciente.getSegundoApellido() + "'), '" + paciente.getCondicion() + "');";
            }

            resultado = consulta.actualizar(sql);
            return resultado;
        } catch (SQLException ex) {
            throw ex;
        } finally {
            consulta.desconectar();
        }
    }
    
    public Integer actualizarPaciente1(Paciente paciente) throws SQLException {
        Consulta consulta = null;
        Integer resultado;
        SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd");
        String sql = "";
        try {
            consulta = new Consulta(getConexion());
            sql = "update pacientes set correo_electronico='" + paciente.getEmail() + "', telefono1='" + paciente.getTelefono1() + "', telefono2='" + paciente.getTelefono2() + "' where identificacion='" + paciente.getIdentificacion() + "'";

            resultado = consulta.actualizar(sql);
            return resultado;
        } catch (SQLException ex) {
            throw ex;
        } finally {
            consulta.desconectar();
        }
    }

    public Integer generarExport() throws SQLException {
        ResultSet rs;
        Consulta consulta = null;
        Integer cantidad = 0, codExport = 0;
        try {
            consulta = new Consulta(getConexion());
            String sql = "select count(*) cantidad from pacientes where cod_export is null";
            rs = consulta.ejecutar(sql);
            if (rs.next()) {
                cantidad = rs.getInt("cantidad");
            }
            if (cantidad > 0) {
                sql = "begin";
                consulta.actualizar(sql);

                sql = "insert into control_export(tipo,cantidad) values('PACIENTES', " + cantidad + ") returning cod_export";
                rs = consulta.ejecutar(sql);
                if (rs.next()) {
                    codExport = rs.getInt("cod_export");
                }

                sql = "update pacientes set cod_export=" + codExport + " where cod_export is null";
                consulta.actualizar(sql);

                sql = "commit";
                consulta.actualizar(sql);
            }
            return cantidad;

        } catch (SQLException ex) {
            throw ex;
        } finally {
            consulta.desconectar();
        }
    }

    public ArrayList<ControlExport> listarControlExport() throws SQLException {
        ResultSet rs;
        Consulta consulta = null;
        ArrayList<ControlExport> listaControlExport = new ArrayList<>();
        try {
            consulta = new Consulta(getConexion());
            String sql = "select cod_export, tipo, fecha, cantidad from control_export where tipo='PACIENTES' order by cod_export desc";
            rs = consulta.ejecutar(sql);
            while (rs.next()) {
                listaControlExport.add(new ControlExport(rs.getString("cod_export"), rs.getDate("fecha"), rs.getString("tipo"), rs.getInt("cantidad")));
            }

            return listaControlExport;

        } catch (SQLException ex) {
            throw ex;
        } finally {
            consulta.desconectar();
        }
    }

    public ArrayList<String> consultarControlExport(String codExport) throws SQLException {
        ResultSet rs;
        Consulta consulta = null;
        ArrayList<String> listaPacientes = new ArrayList<>();
        try {
            consulta = new Consulta(getConexion());
            String sql = " select "
                    + " '99 '|| "
                    + " substring('00000000000000',1,14-char_length(substring(coalesce(identificacion,''),1,14)))||substring(coalesce(identificacion,''),1,14)|| "
                    + " substring(coalesce(primer_apellido,''),1,20)||substring('                    ',1,20-char_length(substring(coalesce(primer_apellido,''),1,20)))|| "
                    + " substring(coalesce(segundo_apellido,''),1,20)||substring('                    ',1,20-char_length(substring(coalesce(segundo_apellido,''),1,20)))|| "
                    + " substring(coalesce(p.nombre,''),1,20)||substring('                    ',1,20-char_length(substring(coalesce(p.nombre,''),1,20)))|| "
                    + " substring(coalesce(segundo_nombre,''),1,20)||substring('                    ',1,20-char_length(substring(coalesce(segundo_nombre,''),1,20)))|| "
                    + " substring(coalesce(tipo_id.label,''),1,3)||substring('   ',1,3-char_length(substring(coalesce(tipo_id.label,''),1,3)))|| "
                    + " substring('000000000000',1,12-char_length(substring(coalesce(entidad,''),1,12)))||substring(coalesce(entidad,''),1,12)|| "
                    + " substring('00000',1,5-char_length(substring(coalesce(ciudad_nacimiento,''),1,5)))||substring(coalesce(ciudad_nacimiento,''),1,5)|| "
                    + " '3'|| "
                    + " substring('000',1,3-char_length(substring(coalesce(((current_date-p.fecha_nacimiento)/365)::integer::varchar,''),1,3)))||substring(coalesce(((current_date-p.fecha_nacimiento)/365)::integer::varchar,''),1,3)|| "
                    + " (case when sexo='1' then 'M' else 'F' end)|| "
                    + " '3'|| "
                    + " (case p.tipo_usuario when '0' then '1' when '1' then '2' when '2' then '4' else '5' end)|| "
                    + " 'U' cadena "
                    + " from "
                    + " pacientes p "
                    + " left join (lista l inner join detalle_lista dl on (l.codigo=dl.codigo_lista and l.nombre='TIPO_IDENTIFICACION')) tipo_id on (p.tipo_identificacion=tipo_id.value) "
                    + " where "
                    + " cod_export= " + codExport;
            rs = consulta.ejecutar(sql);
            while (rs.next()) {
                listaPacientes.add(rs.getString("cadena"));
            }
            return listaPacientes;

        } catch (SQLException ex) {
            throw ex;
        } finally {
            consulta.desconectar();
        }
    }

    public ArrayList<String> consultarControlExport2(String codExport) throws SQLException {
        ResultSet rs1;
        Consulta consulta = null;
        ArrayList<String> listaPacientes2 = new ArrayList<>();
        try {
            consulta = new Consulta(getConexion());
            String sql = " select "
                    + " '99 '|| "
                    + " substring('00000000000000',1,14-char_length(substring(coalesce(identificacion,''),1,14)))||substring(coalesce(identificacion,''),1,14)||'N'|| "
                    + " substring(coalesce(PRIMER_APELLIDO ||' '|| SEGUNDO_APELLIDO ||' '|| NOMBRE ||' '|| SEGUNDO_NOMBRE,''),1,60)|| "
                    + " substring('                                                            ',1,60-char_length(substring(coalesce(NOMBRE ||' '|| SEGUNDO_NOMBRE ||' '|| PRIMER_APELLIDO ||' '|| SEGUNDO_APELLIDO,''),1,60)))|| "
                    + " substring('00000',1,5-char_length(substring(coalesce(ciudad_nacimiento,''),1,5)))||substring(coalesce(ciudad_nacimiento,''),1,5)|| "
                    + " substring(coalesce(direccion1,''),1,60)||substring('                                                            ',1,60-char_length(substring(coalesce(direccion1,''),1,60)))|| "
                    + " substring(coalesce(telefono1,''),1,15)||substring('                    ',1,15-char_length(substring(coalesce(telefono1,''),1,15)))|| "
                    + " to_char(fecha_nacimiento, 'YYYYMMDD') cadena"
                    + " from pacientes "
                    + " where "
                    + " cod_export= " + codExport;

            rs1 = consulta.ejecutar(sql);
            while (rs1.next()) {
                listaPacientes2.add(rs1.getString("cadena"));
            }
            return listaPacientes2;

        } catch (SQLException ex) {
            throw ex;
        } finally {
            consulta.desconectar();
        }
    }

    public List<Paciente> listarPacientes() throws SQLException {
        Paciente paciente = null;
        List<Paciente> listaPacientes = new ArrayList<>();
        ResultSet rs;
        Consulta consulta = null;
        try {
            consulta = new Consulta(getConexion());
            String sql
                    = " select "
                    + " p.nombre||(case when character_length(coalesce(p.segundo_nombre,''))=0 then '' else ' '||p.segundo_nombre end)||(case when character_length(coalesce(p.primer_apellido,''))=0 then '' else ' '||p.primer_apellido end)||(case when character_length(coalesce(p.segundo_apellido,''))=0 then '' else ' '||p.segundo_apellido end) nombre_completo, "
                    + " identificacion,p.nombre,historia_clinica,entidad,e.nombre nombre_entidad,l_tipo_usuario.label tipo_usuario,fecha_nacimiento,edad,direccion1, "
                    + " direccion2,telefono1,telefono2,ciudad_residencia,cr.nombre nombre_ciudad_residencia,ciudad_nacimiento,cn.nombre nombre_ciudad_nacimiento, "
                    + " fecha_apertura,padres,p.correo_electronico, "
                    + " l_sexo.label sexo, "
                    + " l_estado_civil.label estado_civil, "
                    + " p.ocupacion, "
                    + " p.grado_escolar, "
                    + " l_tipo_afiliacion.label tipo_afiliacion, "
                    + " l_tipo_identificacion.value tipo_identificacion, "
                    + " p.nombre, p.segundo_nombre, p.primer_apellido, p.segundo_apellido"
                    + " from "
                    + " pacientes p "
                    + " inner join municipios cr on (p.ciudad_residencia=cr.codigo) "
                    + " inner join municipios cn on (p.ciudad_nacimiento=cn.codigo) "
                    + " inner join entidades e on (p.entidad=e.codigo) "
                    + " left join (lista l "
                    + " inner join detalle_lista dl on (l.codigo=dl.codigo_lista and l.nombre='SEXO') ) l_sexo on (l_sexo.value=p.sexo) "
                    + " left join (lista l "
                    + " inner join detalle_lista dl on (l.codigo=dl.codigo_lista and l.nombre='ESTADO_CIVIL') ) l_estado_civil on (l_estado_civil.value=p.estado_civil) "
                    + " left join (lista l "
                    + " inner join detalle_lista dl on (l.codigo=dl.codigo_lista and l.nombre='TIPO_AFILIACION') ) l_tipo_afiliacion on (l_tipo_afiliacion.value=p.tipo_afiliacion) "
                    + " left join (lista l "
                    + " inner join detalle_lista dl on (l.codigo=dl.codigo_lista and l.nombre='TIPO_IDENTIFICACION') ) l_tipo_identificacion on (l_tipo_identificacion.value=p.tipo_identificacion) "
                    + " left join (lista l "
                    + " inner join detalle_lista dl on (l.codigo=dl.codigo_lista and l.nombre='REGIMEN') ) l_tipo_usuario on (l_tipo_usuario.value=p.tipo_usuario) ";

            rs = consulta.ejecutar(sql);

            while (rs.next()) {
                paciente = new Paciente();
                paciente.setIdentificacion(rs.getString("identificacion"));
                paciente.setNombre(rs.getString("nombre"));
                paciente.setSegundoNombre(rs.getString("segundo_nombre"));
                paciente.setPrimerApellido(rs.getString("primer_apellido"));
                paciente.setSegundoApellido(rs.getString("segundo_apellido"));

                paciente.setNombreCompleto(rs.getString("nombre_completo"));
                paciente.setHistoriaClinica(rs.getString("historia_clinica"));
                paciente.setTipoUsuario(rs.getString("tipo_usuario"));

                paciente.setEntidad(new Entidad(rs.getString("entidad"), rs.getString("nombre_entidad")));
                paciente.setCiudadResidencia(new Municipio(rs.getString("ciudad_residencia"), rs.getString("nombre_ciudad_residencia")));
                paciente.setCiudadNacimiento(new Municipio(rs.getString("ciudad_nacimiento"), rs.getString("nombre_ciudad_nacimiento")));

                paciente.setFechaNacimiento(rs.getDate("fecha_nacimiento"));
                Date fa = new Date();
                Calendar fechaNacimiento = Calendar.getInstance();
                Calendar fechaActual = Calendar.getInstance();
                fechaNacimiento.setTime(paciente.getFechaNacimiento());
                fechaActual.setTime(fa);
                Long resta = (fechaNacimiento.getTimeInMillis() < 0 ? fechaActual.getTimeInMillis() + (fechaNacimiento.getTimeInMillis() * (-1)) : fechaActual.getTimeInMillis() - fechaNacimiento.getTimeInMillis()) / 1000 / 60 / 60 / 24 / 365;
                Integer edad = resta.intValue();
                paciente.setEdad(edad.toString());

                paciente.setDireccion1(rs.getString("direccion1"));
                paciente.setDireccion2(rs.getString("direccion2"));
                paciente.setTelefono1(rs.getString("telefono1"));
                paciente.setTelefono2(rs.getString("telefono2"));

                paciente.setFechaApertura(rs.getDate("fecha_apertura"));
                paciente.setPadres(rs.getString("padres"));
                paciente.setEmail(rs.getString("correo_electronico"));

                paciente.setSexo(rs.getString("sexo"));
                paciente.setGradoEscolar(rs.getString("grado_escolar"));
                paciente.setOcupacion(rs.getString("ocupacion"));
                paciente.setEstadoCivil(rs.getString("estado_civil"));
                paciente.setTipoAfiliacion(rs.getString("tipo_afiliacion"));
                paciente.setTipoIdentificacion(rs.getString("tipo_identificacion"));
                listaPacientes.add(paciente);
            }
            return listaPacientes;

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
