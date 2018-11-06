/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import bd.EstudioAudiologicoDAO;
import java.io.Serializable;
import java.util.List;
import modelo.CampoLibre;
import modelo.EstudioAudiologico;
import modelo.Timpanograma;

/**
 *
 * @author Andres
 */
public class GestorEstudioAudiologico extends Gestor implements Serializable {

    public GestorEstudioAudiologico() throws Exception {
        super();
    }

    public List<CampoLibre> listarOpcionesTipoCampoLibre(String nombreLista) throws Exception {
        try {
            abrirConexion();
            EstudioAudiologicoDAO estudioAudiologicoDAO = new EstudioAudiologicoDAO(conexion);
            return estudioAudiologicoDAO.listarOpcionesTipoCampoLibre(nombreLista);
        } finally {
            cerrarConexion();
        }
    }
    
    public List<Timpanograma> listarOpcionesTipoTimpanograma(String nombreLista) throws Exception {
        try {
            abrirConexion();
            EstudioAudiologicoDAO estudioAudiologicoDAO = new EstudioAudiologicoDAO(conexion);
            return estudioAudiologicoDAO.listarOpcionesTipoTimpanograma(nombreLista);
        } finally {
            cerrarConexion();
        }
    }
    
    public Integer guardarEstudio(EstudioAudiologico estudioAudiologico) throws Exception {
        try {
            abrirConexion();
            EstudioAudiologicoDAO estudioAudiologicoDAO = new EstudioAudiologicoDAO(conexion);
            return estudioAudiologicoDAO.guardarEstudio(estudioAudiologico);
        } finally {
            cerrarConexion();
        }
    }

}
