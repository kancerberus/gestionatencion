/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Andres
 */
public class Valoracion {
    
    private String area;
    private Date fecha;
    private Date hora;
    private Boolean primeraVez;
    private Boolean control;
    private String codigoRIPS;
    private String codigoDiagnostico;
    private String codigoDiagnostico2;
    private Cita cita;
    private String remitidoPor;
    private String nombreAcompanante;
    private String direccionAcompanante;
    private String telefonoAcompanante;
    private String parentescoAcompanante;
    private String motivoConsulta;
    private String antecedentesEvaluacion;
    private String impresionDiagnostica;
    private String conductaSeguir;
    private String tipoFormato;
    private String observacionRecetario;
    
    /*
    private String codigoTerapia;
    private String cantidadTerapias;
    private Boolean terapiaAutorizada;
    */
    private List<Terapia> listaTerapias;
    
    private Diagnostico diagnostico1;
    private Diagnostico diagnostico2;
    

    public Valoracion() {
        this.fecha = new Date();
        this.hora = new Date();
        listaTerapias = new ArrayList<>();
        diagnostico1 = new Diagnostico();
        diagnostico2 = new Diagnostico();
    }

    /**
     * @return the area
     */
    public String getArea() {
        return area;
    }

    /**
     * @param area the area to set
     */
    public void setArea(String area) {
        this.area = area;
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
     * @return the codigoDiagnostico
     */
    public String getCodigoDiagnostico() {
        return codigoDiagnostico;
    }

    /**
     * @param codigoDiagnostico the codigoDiagnostico to set
     */
    public void setCodigoDiagnostico(String codigoDiagnostico) {
        this.codigoDiagnostico = codigoDiagnostico;
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
     * @return the antecedentesEvaluacion
     */
    public String getAntecedentesEvaluacion() {
        return antecedentesEvaluacion;
    }

    /**
     * @param antecedentesEvaluacion the antecedentesEvaluacion to set
     */
    public void setAntecedentesEvaluacion(String antecedentesEvaluacion) {
        this.antecedentesEvaluacion = antecedentesEvaluacion;
    }

    /**
     * @return the impresionDiagnostica
     */
    public String getImpresionDiagnostica() {
        return impresionDiagnostica;
    }

    /**
     * @param impresionDiagnostica the impresionDiagnostica to set
     */
    public void setImpresionDiagnostica(String impresionDiagnostica) {
        this.impresionDiagnostica = impresionDiagnostica;
    }

    /**
     * @return the conductaSeguir
     */
    public String getConductaSeguir() {
        return conductaSeguir;
    }

    /**
     * @param conductaSeguir the conductaSeguir to set
     */
    public void setConductaSeguir(String conductaSeguir) {
        this.conductaSeguir = conductaSeguir;
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
     * @return the tipoFormato
     */
    public String getTipoFormato() {
        return tipoFormato;
    }

    /**
     * @param tipoFormato the tipoFormato to set
     */
    public void setTipoFormato(String tipoFormato) {
        this.tipoFormato = tipoFormato;
    }



    /**
     * @return the codigoDiagnostico2
     */
    public String getCodigoDiagnostico2() {
        return codigoDiagnostico2;
    }

    /**
     * @param codigoDiagnostico2 the codigoDiagnostico2 to set
     */
    public void setCodigoDiagnostico2(String codigoDiagnostico2) {
        this.codigoDiagnostico2 = codigoDiagnostico2;
    }

    /**
     * @return the listaTerapias
     */
    public List<Terapia> getListaTerapias() {
        return listaTerapias;
    }

    /**
     * @param listaTerapias the listaTerapias to set
     */
    public void setListaTerapias(List<Terapia> listaTerapias) {
        this.listaTerapias = listaTerapias;
    }

    /**
     * @return the observacionRecetario
     */
    public String getObservacionRecetario() {
        return observacionRecetario;
    }

    /**
     * @param observacionRecetario the observacionRecetario to set
     */
    public void setObservacionRecetario(String observacionRecetario) {
        this.observacionRecetario = observacionRecetario;
    }

    /**
     * @return the diagnostico1
     */
    public Diagnostico getDiagnostico1() {
        return diagnostico1;
    }

    /**
     * @param diagnostico1 the diagnostico1 to set
     */
    public void setDiagnostico1(Diagnostico diagnostico1) {
        this.diagnostico1 = diagnostico1;
    }

    /**
     * @return the diagnostico2
     */
    public Diagnostico getDiagnostico2() {
        return diagnostico2;
    }

    /**
     * @param diagnostico2 the diagnostico2 to set
     */
    public void setDiagnostico2(Diagnostico diagnostico2) {
        this.diagnostico2 = diagnostico2;
    }

    
}
