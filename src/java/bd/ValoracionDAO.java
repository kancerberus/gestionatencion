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
import java.text.SimpleDateFormat;
import modelo.Terapia;
import modelo.Valoracion;
import modelo.Diagnostico;

/**
 *
 * @author Andres
 */
public class ValoracionDAO {

    private Connection conexion;

    public ValoracionDAO(Connection conexion) {
        this.conexion = conexion;
    }

    public Integer guardarValoracion(Valoracion valoracion, Boolean enviaTerapia, Diagnostico diagnostico1, Diagnostico diagnostico2) throws SQLException {

        Consulta consulta = null;
        Integer resultado = 0, codigoTerapia;
        String sql;
        ResultSet rs;
        SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat formatoHora = new SimpleDateFormat("HH:mm:ss");

        try {
            consulta = new Consulta(getConexion());
            

            //Consultar códigos de diagnostico
            String nom_diagnostico1 = diagnostico1.getNombre_diagostico();
            String nom_diagnostico2 = diagnostico2.getNombre_diagostico();                                    
            String[] arrayDiagnostico1 = nom_diagnostico1.split("-");
            String[] arrayDiagnostico2 = nom_diagnostico2.split("-");
            
            // En este momento tenemos un array en el que cada elemento es un color.
            diagnostico1.setCodigo_diagnostico(trim(arrayDiagnostico1[0]));                            
            diagnostico1.setNombre_diagostico(trim(arrayDiagnostico1[1]));            
            diagnostico2.setCodigo_diagnostico(trim(arrayDiagnostico2[0]));                            
            diagnostico2.setNombre_diagostico(trim(arrayDiagnostico2[1]));            
            
            sql = "begin";
            consulta.actualizar(sql);
            //actualizo paciente
            sql = "update pacientes set condicion='" + valoracion.getCita().getPaciente().getCondicion() + "' where identificacion='" + valoracion.getCita().getPaciente().getIdentificacion() + "'";
            consulta.actualizar(sql);
            //inserto la valoracion
            sql
                    = " INSERT INTO valoracion( "
                    + " area, fecha, hora, primera_vez, control, codigo_rips, "
                    + " codigo_diagnostico, codigo_diagnostico2, id_paciente, id_profesinal, remitido_por, "
                    + " nombre_acompanante, direccion_acompanante, telefono_acompanante, "
                    + " parentesco_acompanante, motivo_consulta, antecedentes_evaluacion, "
                    + " impresion_diagnostica, conducta_seguir, cod_cita, tipo_formato, observacion_recetario) "
                    + " VALUES ( "
                    + " '" + valoracion.getArea() + "', '" + formatoFecha.format(valoracion.getFecha()) + "', '" + formatoHora.format(valoracion.getHora()) + "', "
                    + " " + valoracion.getPrimeraVez() + ", " + valoracion.getControl() + ", '" + valoracion.getCodigoRIPS() + "', "
                    + " '" + diagnostico1.getCodigo_diagnostico() + "', '" + diagnostico2.getCodigo_diagnostico() + "', '" + valoracion.getCita().getPaciente().getIdentificacion() + "', "
                    + " '" + valoracion.getCita().getProfesional().getCedula() + "', '" + valoracion.getRemitidoPor() + "', "
                    + " '" + valoracion.getNombreAcompanante() + "', '" + valoracion.getDireccionAcompanante() + "', '" + valoracion.getTelefonoAcompanante() + "', "
                    + " '" + valoracion.getParentescoAcompanante() + "', '" + valoracion.getMotivoConsulta() + "', '" + valoracion.getAntecedentesEvaluacion() + "', "
                    + " '" + valoracion.getImpresionDiagnostica() + "', '" + valoracion.getConductaSeguir() + "', " + valoracion.getCita().getCodigo() + ","
                    + " '" + valoracion.getTipoFormato() + "','" + valoracion.getObservacionRecetario() + "') returning codigo; ";
            rs = consulta.ejecutar(sql);

            if (rs.next()) {
                resultado = rs.getInt("codigo");
            }

            if (enviaTerapia) {
                for (Terapia t : valoracion.getListaTerapias()) {
                    //inserto un encabezado de terapia por cada terapia escogida
                    sql = " INSERT INTO terapia( "
                            + " id_paciente, id_profesional, codigo_procedimiento, fecha,  "
                            + " cantidad_formulada, cantidad_autorizada, cantidad_pendiente,  "
                            + " cantidad_atendida, activa, codigo_valoracion, nombre_acompanante,  "
                            + " parentesco_acompanante, codigo_rips, codigo_diagnostico, primera_vez,  "
                            + " control, diagnostico, plan_tratamiento, evolucion) "
                            + " VALUES ( '" + valoracion.getCita().getPaciente().getIdentificacion() + "', '" + valoracion.getCita().getProfesional().getCedula() + "',"
                            + " '" +  t.getProcedimiento().getCodigo() + "', current_date,  "
                            + " " +  t.getCantidadFormulada() + ", " + (t.getAutorizada().getCodigo().equalsIgnoreCase("2") ? t.getCantidadFormulada() : "0") + ", "
                            + " " + (t.getCantidadFormulada()-1) + ", "
                            + " 1, true, '" + resultado + "', '',  "
                            + " '', '', '', null,  "
                            + " null, '', '', '') returning codigo;";
                    rs = consulta.ejecutar(sql);
                    if (rs.next()) {
                        codigoTerapia = rs.getInt("codigo");

                        sql = " insert into detalle_terapia(consecutivo,codigo_terapia,actividad,fecha,hora,estado,cod_cita) "
                                + " values(1," + codigoTerapia + ",'Actividad de valoración',current_date,current_time,'F'," + valoracion.getCita().getCodigo() + ") ";
                        consulta.actualizar(sql);
                    }
                    // =0 viene sin modificar o reseteado del ultimo ciclo, supone que no es procedimiento tipo 1  >0 procedimiento tipo 1 <0 deberia haber retornado -1

                }
            }

            sql = "update citas set estado='2' where codigo=" + valoracion.getCita().getCodigo();
            consulta.actualizar(sql);

            sql = "commit";
            consulta.actualizar(sql);

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
