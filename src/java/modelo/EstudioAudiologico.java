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
    
    private String numeroAutorizacion;
    private String confiabilidad;
    private String otoscopia;
    private String audiometro;
    
    private Boolean weber250;
    private Boolean weber500;
    private Boolean weber1k;
    private Boolean weber2k;
    private Boolean weber4k;
    
    private String datosXMLOD;
    private String datosXMLOI;
    
    //van en la misma grafica
    private String datosXMLLogoOD;
    private String datosXMLLogoOI;
    
    
    private String datos64OD;
    private String datos64OI;
    private String datos64Logo;
    
    
   

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

    /**
     * @return the numeroAutorizacion
     */
    public String getNumeroAutorizacion() {
        return numeroAutorizacion;
    }

    /**
     * @param numeroAutorizacion the numeroAutorizacion to set
     */
    public void setNumeroAutorizacion(String numeroAutorizacion) {
        this.numeroAutorizacion = numeroAutorizacion;
    }

    /**
     * @return the confiabilidad
     */
    public String getConfiabilidad() {
        return confiabilidad;
    }

    /**
     * @param confiabilidad the confiabilidad to set
     */
    public void setConfiabilidad(String confiabilidad) {
        this.confiabilidad = confiabilidad;
    }

    /**
     * @return the otoscopia
     */
    public String getOtoscopia() {
        return otoscopia;
    }

    /**
     * @param otoscopia the otoscopia to set
     */
    public void setOtoscopia(String otoscopia) {
        this.otoscopia = otoscopia;
    }

    /**
     * @return the audiometro
     */
    public String getAudiometro() {
        return audiometro;
    }

    /**
     * @param audiometro the audiometro to set
     */
    public void setAudiometro(String audiometro) {
        this.audiometro = audiometro;
    }

    /**
     * @return the weber250
     */
    public Boolean getWeber250() {
        return weber250;
    }

    /**
     * @param weber250 the weber250 to set
     */
    public void setWeber250(Boolean weber250) {
        this.weber250 = weber250;
    }

    /**
     * @return the weber500
     */
    public Boolean getWeber500() {
        return weber500;
    }

    /**
     * @param weber500 the weber500 to set
     */
    public void setWeber500(Boolean weber500) {
        this.weber500 = weber500;
    }

    /**
     * @return the weber1k
     */
    public Boolean getWeber1k() {
        return weber1k;
    }

    /**
     * @param weber1k the weber1k to set
     */
    public void setWeber1k(Boolean weber1k) {
        this.weber1k = weber1k;
    }

    /**
     * @return the weber2k
     */
    public Boolean getWeber2k() {
        return weber2k;
    }

    /**
     * @param weber2k the weber2k to set
     */
    public void setWeber2k(Boolean weber2k) {
        this.weber2k = weber2k;
    }

    /**
     * @return the weber4k
     */
    public Boolean getWeber4k() {
        return weber4k;
    }

    /**
     * @param weber4k the weber4k to set
     */
    public void setWeber4k(Boolean weber4k) {
        this.weber4k = weber4k;
    }

    /**
     * @return the datosXMLOD
     */
    public String getDatosXMLOD() {
        return datosXMLOD;
    }

    /**
     * @param datosXMLOD the datosXMLOD to set
     */
    public void setDatosXMLOD(String datosXMLOD) {
        this.datosXMLOD = datosXMLOD;
    }

    /**
     * @return the datosXMLOI
     */
    public String getDatosXMLOI() {
        return datosXMLOI;
    }

    /**
     * @param datosXMLOI the datosXMLOI to set
     */
    public void setDatosXMLOI(String datosXMLOI) {
        this.datosXMLOI = datosXMLOI;
    }

    /**
     * @return the datos64OD
     */
    public String getDatos64OD() {
        return datos64OD;
    }

    /**
     * @param datos64OD the datos64OD to set
     */
    public void setDatos64OD(String datos64OD) {
        this.datos64OD = datos64OD;
    }

    /**
     * @return the datos64OI
     */
    public String getDatos64OI() {
        return datos64OI;
    }

    /**
     * @param datos64OI the datos64OI to set
     */
    public void setDatos64OI(String datos64OI) {
        this.datos64OI = datos64OI;
    }

    /**
     * @return the datosXMLLogoOD
     */
    public String getDatosXMLLogoOD() {
        return datosXMLLogoOD;
    }

    /**
     * @param datosXMLLogoOD the datosXMLLogoOD to set
     */
    public void setDatosXMLLogoOD(String datosXMLLogoOD) {
        this.datosXMLLogoOD = datosXMLLogoOD;
    }

    /**
     * @return the datosXMLLogoOI
     */
    public String getDatosXMLLogoOI() {
        return datosXMLLogoOI;
    }

    /**
     * @param datosXMLLogoOI the datosXMLLogoOI to set
     */
    public void setDatosXMLLogoOI(String datosXMLLogoOI) {
        this.datosXMLLogoOI = datosXMLLogoOI;
    }

    /**
     * @return the datos64Logo
     */
    public String getDatos64Logo() {
        return datos64Logo;
    }

    /**
     * @param datos64Logo the datos64Logo to set
     */
    public void setDatos64Logo(String datos64Logo) {
        this.datos64Logo = datos64Logo;
    }
    
    
}
