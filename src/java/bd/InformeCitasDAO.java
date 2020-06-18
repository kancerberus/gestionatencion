/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bd;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;


/**
 *
 * @author Andres
 */
public class InformeCitasDAO {

    private Connection conexion;    
    SimpleDateFormat format_cita = new SimpleDateFormat("yyyy/MM/dd");
    SimpleDateFormat format_asig = new SimpleDateFormat("yyyy/MM/dd");
    SimpleDateFormat format_deseada = new SimpleDateFormat("yyyy/MM/dd");

    Date date_cita = null;
    Date date_asignacion = null;
    Date date_deseada = null;
    
    public InformeCitasDAO(Connection conexion) {
        this.conexion = conexion;
    }

    public ArrayList<String> ConsultaInformeCitas(String fecha_ini, String fecha_fin) throws SQLException, ParseException {
        
        Consulta consulta = null;
        String sql; 
        ResultSet rs;        
        ArrayList<String> listaInformes = new ArrayList<>();
        
        try {
            consulta = new Consulta(getConexion());
            sql = " select "
                    + " l.label as tipo_identificacion, c.id_paciente, p.nombre as primer_nombre, "
                    + " p.segundo_nombre,primer_apellido,segundo_apellido, "
                    + "m.departamento as departamento_DANE, m.nombre as municipio_DANE, "
                    + "p.direccion1 as direccion,p.telefono1, "
                    + "p.telefono2,to_char(c.fecha, 'YYYY/MM/DD') as fecha_cita, rpc.hora as hora_cita, "
                    + "to_char(c.fecha_registro, 'YYYY/MM/DD') as fecha_asignacion, to_char(c.fecha_registro, 'HH24:MI:SS') as hora_asignacion, "
                    + "'RISARALDA' as departamento_ips, 'PEREIRA' as municipio_ips, 'INSTITUTO DE AUDIOLOGIA INTEGRAL' as ips_cita, "                   
                    + "esp.nombre as servicio, pro.nombre as procedimiento, ent.nombre as entidad, "
                    + "prof.nombre as profesional, fecha_deseada "
                    + "from citas c " 
                    + "inner join pacientes p on (p.identificacion=c.id_paciente) " 
                    + "inner join entidades e on (e.codigo=c.codigo_entidad) " 
                    + "inner join detalle_lista l on (l.value=p.tipo_identificacion) " 
                    + "inner join municipios m on (m.codigo=p.ciudad_residencia) "
                    + "inner join especialidades esp on (esp.codigo=c.codigo_especialidad) " 
                    + "inner join entidades ent on (ent.codigo=c.codigo_entidad) " 
                    + "inner join profesionales prof on (prof.cedula=c.id_profesional) " 
                    + "inner join rel_procedimientos_cita rpc on (rpc.codigo_cita=c.codigo) " 
                    + "inner join procedimientos pro on (pro.codigo=rpc.codigo_procedimiento) " 
                    + "where l.codigo_lista = 6 "
                    + "and c.fecha between '" + fecha_ini + "' and '" + fecha_fin +"'";                                        

 
            rs = consulta.ejecutar(sql);
            while (rs.next()) {
                
                String tipo_identificacion = rs.getString("tipo_identificacion");
                String id_paciente = rs.getString("id_paciente");
                String primer_nombre = rs.getString("primer_nombre");
                String segundo_nombre = rs.getString("segundo_nombre");
                String primer_apellido = rs.getString("primer_apellido");
                String segundo_apellido = rs.getString("segundo_apellido");
                String departamento_DANE = rs.getString("departamento_DANE");
                String municipio_DANE = rs.getString("municipio_DANE");
                String direccion = rs.getString("direccion");
                String telefono1 = rs.getString("telefono1");                                                                                                     
                String telefono2 = rs.getString("telefono2");

                if ("".equals(telefono2) || "0".equals(telefono2) || ".".equals(telefono2)) {
                    telefono2 = "999";
                } 
                                             
                String fecha_cita = rs.getString("fecha_cita");
                String hora_cita = rs.getString("hora_cita");
                String fecha_asignacion = rs.getString("fecha_asignacion");
                String hora_asignacion = rs.getString("hora_asignacion");
                String departamento_ips = rs.getString("departamento_ips");
                String municipio_ips = rs.getString("municipio_ips");
                String ips_cita = rs.getString("ips_cita");
                String servicio = rs.getString("servicio");
                String procedimiento = rs.getString("procedimiento");
                String entidad = rs.getString("entidad");
                String profesional = rs.getString("profesional");
                String fecha_deseada = rs.getString("fecha_deseada");
                
                
                    //Convierte las fechas tipo cadena en tipo DATE
                
                    date_cita = format_cita.parse(fecha_cita);
                    date_asignacion = format_asig.parse(fecha_asignacion);                    
                    //date_deseada = format_deseada.parse(fecha_deseada);
                    
                    //Resta de fecha cita vs fecha asignacion en días
                    long diferencia = date_cita.getTime() - date_asignacion.getTime();                    
                    long diffDays = diferencia / (24 * 60 * 60 * 1000);
                                                                               
                    listaInformes.add(primer_nombre + " " + segundo_nombre + " " + primer_apellido + " " + segundo_apellido 
                                  +","+ tipo_identificacion + "," + id_paciente + "," + departamento_DANE + "," + municipio_DANE
                                  + "," + direccion + "," + telefono1 + "," + telefono2 + "," + fecha_asignacion+ "," + hora_asignacion
                                  + "," + fecha_cita + "," + hora_cita + "," + departamento_ips + "," + municipio_ips 
                                  + "," + "6600100277" + "," + ips_cita + "," + servicio
                                  + "," + procedimiento + "," + "1" + "," + diffDays + "," + "0" + "," + entidad
                                  + "," + profesional +"," + fecha_deseada);  

            }
            return listaInformes;
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
