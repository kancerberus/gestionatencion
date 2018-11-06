/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Andres
 */
public class EstudioAudiologico implements Serializable {
        
    private Date fecha;
    private Date hora;
    private Boolean primeraVez;
    private Boolean control;
    private String codigoRIPS;
    
    private Cita cita;
    private String remitidoPor;
    private String nombreAcompanante;
    private String direccionAcompanante;
    private String telefonoAcompanante;
    private String parentescoAcompanante;
    private String motivoConsulta;
    private String antecedentes;
    private String diagnosticoAudiologico;
    private String observaciones;
    
    private Grafico audiometriaTonosPurosOD;
    
    private List<CampoLibre> tablaCampoLibre;
    private List<Timpanograma> tablaTimpanograma;
    private List<CampoLibre> valoresInmitanciaAcustica;
    private String impedanciometro;    
    private Acufenometria acufenometria;
    
    
   

    public EstudioAudiologico() {
        audiometriaTonosPurosOD = new Grafico();
        tablaCampoLibre = new ArrayList<>();
        tablaTimpanograma = new ArrayList<>();
        valoresInmitanciaAcustica = new ArrayList<>();
        acufenometria = new Acufenometria();
        
    }

    /**
     * @return the fecha
     */
    public Date getFecha() {
        return fecha;
    }

    /**
     * @param fecha the fecha to set
     */
    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    /**
     * @return the hora
     */
    public Date getHora() {
        return hora;
    }

    /**
     * @param hora the hora to set
     */
    public void setHora(Date hora) {
        this.hora = hora;
    }

    /**
     * @return the primeraVez
     */
    public Boolean getPrimeraVez() {
        return primeraVez;
    }

    /**
     * @param primeraVez the primeraVez to set
     */
    public void setPrimeraVez(Boolean primeraVez) {
        this.primeraVez = primeraVez;
    }

    /**
     * @return the control
     */
    public Boolean getControl() {
        return control;
    }

    /**
     * @param control the control to set
     */
    public void setControl(Boolean control) {
        this.control = control;
    }

    /**
     * @return the codigoRIPS
     */
    public String getCodigoRIPS() {
        return codigoRIPS;
    }

    /**
     * @param codigoRIPS the codigoRIPS to set
     */
    public void setCodigoRIPS(String codigoRIPS) {
        this.codigoRIPS = codigoRIPS;
    }

    /**
     * @return the cita
     */
    public Cita getCita() {
        return cita;
    }

    /**
     * @param cita the cita to set
     */
    public void setCita(Cita cita) {
        this.cita = cita;
    }

    /**
     * @return the remitidoPor
     */
    public String getRemitidoPor() {
        return remitidoPor;
    }

    /**
     * @param remitidoPor the remitidoPor to set
     */
    public void setRemitidoPor(String remitidoPor) {
        this.remitidoPor = remitidoPor;
    }

    /**
     * @return the nombreAcompanante
     */
    public String getNombreAcompanante() {
        return nombreAcompanante;
    }

    /**
     * @param nombreAcompanante the nombreAcompanante to set
     */
    public void setNombreAcompanante(String nombreAcompanante) {
        this.nombreAcompanante = nombreAcompanante;
    }

    /**
     * @return the direccionAcompanante
     */
    public String getDireccionAcompanante() {
        return direccionAcompanante;
    }

    /**
     * @param direccionAcompanante the direccionAcompanante to set
     */
    public void setDireccionAcompanante(String direccionAcompanante) {
        this.direccionAcompanante = direccionAcompanante;
    }

    /**
     * @return the telefonoAcompanante
     */
    public String getTelefonoAcompanante() {
        return telefonoAcompanante;
    }

    /**
     * @param telefonoAcompanante the telefonoAcompanante to set
     */
    public void setTelefonoAcompanante(String telefonoAcompanante) {
        this.telefonoAcompanante = telefonoAcompanante;
    }

    /**
     * @return the parentescoAcompanante
     */
    public String getParentescoAcompanante() {
        return parentescoAcompanante;
    }

    /**
     * @param parentescoAcompanante the parentescoAcompanante to set
     */
    public void setParentescoAcompanante(String parentescoAcompanante) {
        this.parentescoAcompanante = parentescoAcompanante;
    }

    /**
     * @return the motivoConsulta
     */
    public String getMotivoConsulta() {
        return motivoConsulta;
    }

    /**
     * @param motivoConsulta the motivoConsulta to set
     */
    public void setMotivoConsulta(String motivoConsulta) {
        this.motivoConsulta = motivoConsulta;
    }

    /**
     * @return the antecedentes
     */
    public String getAntecedentes() {
        return antecedentes;
    }

    /**
     * @param antecedentes the antecedentes to set
     */
    public void setAntecedentes(String antecedentes) {
        this.antecedentes = antecedentes;
    }

    /**
     * @return the diagnosticoAudiologico
     */
    public String getDiagnosticoAudiologico() {
        return diagnosticoAudiologico;
    }

    /**
     * @param diagnosticoAudiologico the diagnosticoAudiologico to set
     */
    public void setDiagnosticoAudiologico(String diagnosticoAudiologico) {
        this.diagnosticoAudiologico = diagnosticoAudiologico;
    }

    /**
     * @return the observaciones
     */
    public String getObservaciones() {
        return observaciones;
    }

    /**
     * @param observaciones the observaciones to set
     */
    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    /**
     * @return the audiometriaTonosPurosOD
     */
    public Grafico getAudiometriaTonosPurosOD() {
        return audiometriaTonosPurosOD;
    }

    /**
     * @param audiometriaTonosPurosOD the audiometriaTonosPurosOD to set
     */
    public void setAudiometriaTonosPurosOD(Grafico audiometriaTonosPurosOD) {
        this.audiometriaTonosPurosOD = audiometriaTonosPurosOD;
    }

    /**
     * @return the tablaCampoLibre
     */
    public List<CampoLibre> getTablaCampoLibre() {
        return tablaCampoLibre;
    }

    /**
     * @param tablaCampoLibre the tablaCampoLibre to set
     */
    public void setTablaCampoLibre(List<CampoLibre> tablaCampoLibre) {
        this.tablaCampoLibre = tablaCampoLibre;
    }

    /**
     * @return the tablaTimpanograma
     */
    public List<Timpanograma> getTablaTimpanograma() {
        return tablaTimpanograma;
    }

    /**
     * @param tablaTimpanograma the tablaTimpanograma to set
     */
    public void setTablaTimpanograma(List<Timpanograma> tablaTimpanograma) {
        this.tablaTimpanograma = tablaTimpanograma;
    }

    /**
     * @return the valoresInmitanciaAcustica
     */
    public List<CampoLibre> getValoresInmitanciaAcustica() {
        return valoresInmitanciaAcustica;
    }

    /**
     * @param valoresInmitanciaAcustica the valoresInmitanciaAcustica to set
     */
    public void setValoresInmitanciaAcustica(List<CampoLibre> valoresInmitanciaAcustica) {
        this.valoresInmitanciaAcustica = valoresInmitanciaAcustica;
    }

    /**
     * @return the impedanciometro
     */
    public String getImpedanciometro() {
        return impedanciometro;
    }

    /**
     * @param impedanciometro the impedanciometro to set
     */
    public void setImpedanciometro(String impedanciometro) {
        this.impedanciometro = impedanciometro;
    }

    /**
     * @return the acufenometria
     */
    public Acufenometria getAcufenometria() {
        return acufenometria;
    }

    /**
     * @param acufenometria the acufenometria to set
     */
    public void setAcufenometria(Acufenometria acufenometria) {
        this.acufenometria = acufenometria;
    }
    
    
}
