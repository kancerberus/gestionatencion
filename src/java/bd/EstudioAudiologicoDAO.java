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
import java.util.List;
import javax.faces.model.SelectItem;
import modelo.CampoLibre;
import modelo.EstudioAudiologico;
import modelo.Timpanograma;

/**
 *
 * @author Andres
 */
public class EstudioAudiologicoDAO {

    private Connection conexion;

    public EstudioAudiologicoDAO(Connection conexion) {
        this.conexion = conexion;
    }

    public List<CampoLibre> listarOpcionesTipoCampoLibre(String nombreLista) throws SQLException {
        CampoLibre cl = null;
        List<CampoLibre> lista = new ArrayList<>();
        ResultSet rs;
        Consulta consulta = null;
        try {
            consulta = new Consulta(getConexion());
            String sql = "select "
                    + " value,label,label2,codigo_lista "
                    + " from "
                    + " lista l "
                    + " inner join detalle_lista dl on (l.codigo=dl.codigo_lista) "
                    + " where nombre = '" + nombreLista + "' order by orden ";
            rs = consulta.ejecutar(sql);
            while (rs.next()) {
                cl = new CampoLibre(rs.getString("value"), rs.getString("codigo_lista"), rs.getString("label"));
                lista.add(cl);
            }
            return lista;
        } catch (SQLException ex) {
            throw ex;
        } finally {
            consulta.desconectar();
        }

    }

    public List<Timpanograma> listarOpcionesTipoTimpanograma(String nombreLista) throws SQLException {
        Timpanograma tg = null;
        List<Timpanograma> lista = new ArrayList<>();
        ResultSet rs;
        Consulta consulta = null;
        try {
            consulta = new Consulta(getConexion());
            String sql = "select "
                    + " value,label,label2,codigo_lista "
                    + " from "
                    + " lista l "
                    + " inner join detalle_lista dl on (l.codigo=dl.codigo_lista) "
                    + " where nombre = '" + nombreLista + "' order by orden ";
            rs = consulta.ejecutar(sql);
            while (rs.next()) {
                tg = new Timpanograma(rs.getString("value"), rs.getString("codigo_lista"), rs.getString("label"), rs.getString("label2"));
                lista.add(tg);
            }
            return lista;
        } catch (SQLException ex) {
            throw ex;
        } finally {
            consulta.desconectar();
        }

    }

    public Integer guardarEstudio(EstudioAudiologico estudioAudiologico) throws SQLException {
        ResultSet rs;
        Consulta consulta = null;
        Integer resultado = 0;

        SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat formatoHora = new SimpleDateFormat("HH:mm:ss");
        try {
            consulta = new Consulta(getConexion());
            String sql = "begin";
            consulta.actualizar(sql);

            sql = " INSERT INTO estudio_audiologico( "
                    + " fecha, hora, "
                    + " primera_vez, control, codigo_rips, "
                    + " remitido_por, nombre_acompanante, direccion_acompanante, "
                    + " telefono_acompanante, parentesco_acompanante, acu_od, "
                    + " acu_oi, acu_bilateral, acu_continuo, "
                    + " acu_pulsatil, acu_frecuencia, acu_ruido_blanco, "
                    + " acu_tono_puro, acu_tono_warble, acu_umbral, "
                    + " acu_intensidad, acu_enmascaramiento, motivo_consulta, "
                    + " antecedentes, diagnostico_audiologico, observaciones, cod_cita)"
                    + " VALUES ('" + formatoFecha.format(estudioAudiologico.getFecha()) + "', '" + formatoHora.format(estudioAudiologico.getHora()) + "', "
                    + " " + estudioAudiologico.getPrimeraVez().toString() + ", " + estudioAudiologico.getControl().toString() + ", '" + estudioAudiologico.getCodigoRIPS() + "', "
                    + " '" + estudioAudiologico.getRemitidoPor() + "', '" + estudioAudiologico.getNombreAcompanante() + "', '" + estudioAudiologico.getDireccionAcompanante() + "', "
                    + " '" + estudioAudiologico.getTelefonoAcompanante() + "', '" + estudioAudiologico.getParentescoAcompanante() + "', '" + estudioAudiologico.getAcufenometria().getOd() + "', "
                    + " '" + estudioAudiologico.getAcufenometria().getOi() + "', '" + estudioAudiologico.getAcufenometria().getBilateral() + "', '" + estudioAudiologico.getAcufenometria().getContinuo() + "', "
                    + " '" + estudioAudiologico.getAcufenometria().getPulsatil() + "', '" + estudioAudiologico.getAcufenometria().getFrecuencia() + "', '" + estudioAudiologico.getAcufenometria().getRuidoBlanco() + "', "
                    + " '" + estudioAudiologico.getAcufenometria().getTonoPuro() + "', '" + estudioAudiologico.getAcufenometria().getTonoWarble() + "', '" + estudioAudiologico.getAcufenometria().getUmbralSenal() + "', "
                    + " '" + estudioAudiologico.getAcufenometria().getIntensidadAcufeno() + "', '" + estudioAudiologico.getAcufenometria().getEnmascaramiento() + "', '" + estudioAudiologico.getMotivoConsulta() + "', "
                    + " '" + estudioAudiologico.getAntecedentes() + "', '" + estudioAudiologico.getDiagnosticoAudiologico() + "', '" + estudioAudiologico.getObservaciones() + "', " + estudioAudiologico.getCita().getCodigo() + ") returning codigo";
            rs = consulta.ejecutar(sql);
            if (rs.next()) {
                resultado = rs.getInt("codigo");
            }

            for (CampoLibre cl : estudioAudiologico.getTablaCampoLibre()) {
                sql = " INSERT INTO parametros_estudio_audiologico( "
                        + " value, codigo_lista, codigo_estudio,  "
                        + " valor1, valor2, valor3) "
                        + " VALUES ('" + cl.getCodigo() + "', '" + cl.getCodigoLista() + "', '" + resultado + "', "
                        + " '" + (cl.getOd() == null ? "" : cl.getOd()) + "', '" + (cl.getOi() == null ? "" : cl.getOi()) + "', '" + (cl.getCampoLibre() == null ? "" : cl.getCampoLibre()) + "') ";
                consulta.actualizar(sql);
            }

            for (Timpanograma tg : estudioAudiologico.getTablaTimpanograma()) {
                sql = " INSERT INTO parametros_estudio_audiologico( "
                        + " value, codigo_lista, codigo_estudio,  "
                        + " valor1, valor2) "
                        + " VALUES ('" + tg.getCodigo() + "', '" + tg.getCodigoLista() + "', '" + resultado + "', "
                        + " '" + (tg.getValor1() == null ? "" : tg.getValor1()) + "', '" + (tg.getValor2() == null ? "" : tg.getValor2()) + "') ";
                consulta.actualizar(sql);
            }

            for (CampoLibre cl : estudioAudiologico.getValoresInmitanciaAcustica()) {
                sql = " INSERT INTO parametros_estudio_audiologico( "
                        + " value, codigo_lista, codigo_estudio,  "
                        + " valor1, valor2) "
                        + " VALUES ('" + cl.getCodigo() + "', '" + cl.getCodigoLista() + "', '" + resultado + "', "
                        + " '" + (cl.getOd() == null ? "" : cl.getOd()) + "', '" + (cl.getOi() == null ? "" : cl.getOi()) + "') ";
                consulta.actualizar(sql);
            }

            sql = "commit";
            consulta.actualizar(sql);

            return 1;
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
