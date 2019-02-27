/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.io.Serializable;

/**
 *
 * @author Andres
 */
public class GestorPrincipal implements Serializable {

    private boolean nuevaCita = false;
    private boolean crearAgenda = false;
    private boolean agendas = false;
    private boolean crearPaciente = false;
    private boolean consultarCitas = false;
    private boolean exportarAgenda = false;
    private boolean exportarInforme = false;
    private boolean resumenProfesional = false;
    private boolean crearTerapia = false;
    private boolean consultarTerapia = false;

    private boolean crearEntidad = false;
    private boolean consultarEntidad = false;
    private boolean crearProfesional = false;
    private boolean consultarProfesional = false;
    private boolean crearUsuario = false;
    private boolean consultarUsuario = false;

    private boolean exportarPacientes = false;
    private boolean listarPacientes = false;
    private boolean replicarTerapia = false;
    
    private boolean historiaClinica = false;


    public GestorPrincipal() {

    }

    public String selGeneral(String opcion) {
        switch (opcion) {
            case "nueva_cita":
                setNuevaCita(true);
                setCrearAgenda(false);
                setAgendas(false);
                setCrearPaciente(false);
                setConsultarCitas(false);
                setExportarAgenda(false);
                setExportarInforme(false);
                setResumenProfesional(false);
                setCrearTerapia(false);
                setConsultarTerapia(false);

                setCrearEntidad(false);
                setConsultarEntidad(false);
                setCrearProfesional(false);
                setConsultarProfesional(false);
                setCrearUsuario(false);
                setConsultarUsuario(false);

                setExportarPacientes(false);
                setListarPacientes(false);
                setReplicarTerapia(false);
                setHistoriaClinica(false);

                break;
            case "crear_agenda":
                setNuevaCita(false);
                setCrearAgenda(true);
                setAgendas(false);
                setCrearPaciente(false);
                setConsultarCitas(false);
                setExportarAgenda(false);
                setExportarInforme(false);
                setResumenProfesional(false);
                setCrearTerapia(false);
                setConsultarTerapia(false);
                setCrearEntidad(false);
                setConsultarEntidad(false);
                setCrearProfesional(false);
                setConsultarProfesional(false);
                setCrearUsuario(false);
                setConsultarUsuario(false);
                setExportarPacientes(false);
                setListarPacientes(false);
                setReplicarTerapia(false);
                setHistoriaClinica(false);
                break;
            case "crear_paciente":
                setNuevaCita(false);
                setCrearAgenda(false);
                setAgendas(false);
                setCrearPaciente(true);
                setConsultarCitas(false);
                setExportarAgenda(false);
                setExportarInforme(false);
                setResumenProfesional(false);
                setCrearTerapia(false);
                setConsultarTerapia(false);
                setCrearEntidad(false);
                setConsultarEntidad(false);
                setCrearProfesional(false);
                setConsultarProfesional(false);
                setCrearUsuario(false);
                setConsultarUsuario(false);
                setExportarPacientes(false);
                setListarPacientes(false);
                setReplicarTerapia(false);
                setHistoriaClinica(false);
                break;
            case "consultar_cita":
                setNuevaCita(false);
                setCrearAgenda(false);
                setAgendas(false);
                setCrearPaciente(false);
                setConsultarCitas(true);
                setExportarAgenda(false);
                setExportarInforme(false);
                setResumenProfesional(false);
                setCrearTerapia(false);
                setConsultarTerapia(false);
                setCrearEntidad(false);
                setConsultarEntidad(false);
                setCrearProfesional(false);
                setConsultarProfesional(false);
                setCrearUsuario(false);
                setConsultarUsuario(false);
                setExportarPacientes(false);
                setListarPacientes(false);
                setReplicarTerapia(false);
                setHistoriaClinica(false);
                break;
            case "agendas":
                setNuevaCita(false);
                setCrearAgenda(false);
                setAgendas(true);
                setCrearPaciente(false);
                setConsultarCitas(false);
                setExportarAgenda(false);
                setExportarInforme(false);
                setResumenProfesional(false);
                setCrearTerapia(false);
                setConsultarTerapia(false);
                setCrearEntidad(false);
                setConsultarEntidad(false);
                setCrearProfesional(false);
                setConsultarProfesional(false);
                setCrearUsuario(false);
                setConsultarUsuario(false);
                setExportarPacientes(false);
                setListarPacientes(false);
                setReplicarTerapia(false);
                setHistoriaClinica(false);
                break;
            case "exportar":
                setNuevaCita(false);
                setCrearAgenda(false);
                setAgendas(false);
                setCrearPaciente(false);
                setConsultarCitas(false);
                setExportarAgenda(true);
                setExportarInforme(false);
                setResumenProfesional(false);
                setCrearTerapia(false);
                setConsultarTerapia(false);
                setCrearEntidad(false);
                setConsultarEntidad(false);
                setCrearProfesional(false);
                setConsultarProfesional(false);
                setCrearUsuario(false);
                setConsultarUsuario(false);
                setExportarPacientes(false);
                setListarPacientes(false);
                setReplicarTerapia(false);
                setHistoriaClinica(false);
                break;
            case "resumen_profesional":
                setNuevaCita(false);
                setCrearAgenda(false);
                setAgendas(false);
                setCrearPaciente(false);
                setConsultarCitas(false);
                setExportarAgenda(false);
                setExportarInforme(false);
                setResumenProfesional(true);
                setCrearTerapia(false);
                setConsultarTerapia(false);
                setCrearEntidad(false);
                setConsultarEntidad(false);
                setCrearProfesional(false);
                setConsultarProfesional(false);
                setCrearUsuario(false);
                setConsultarUsuario(false);
                setExportarPacientes(false);
                setListarPacientes(false);
                setReplicarTerapia(false);
                setHistoriaClinica(false);
                break;
            case "crear_terapia":
                setNuevaCita(false);
                setCrearAgenda(false);
                setAgendas(false);
                setCrearPaciente(false);
                setConsultarCitas(false);
                setExportarAgenda(false);
                setExportarInforme(false);
                setResumenProfesional(false);
                setCrearTerapia(true);
                setConsultarTerapia(false);
                setCrearEntidad(false);
                setConsultarEntidad(false);
                setCrearProfesional(false);
                setConsultarProfesional(false);
                setCrearUsuario(false);
                setConsultarUsuario(false);
                setExportarPacientes(false);
                setListarPacientes(false);
                setReplicarTerapia(false);
                setHistoriaClinica(false);
                break;
            case "consultar_terapia":
                setNuevaCita(false);
                setCrearAgenda(false);
                setAgendas(false);
                setCrearPaciente(false);
                setConsultarCitas(false);
                setExportarAgenda(false);
                setExportarInforme(false);
                setResumenProfesional(false);
                setCrearTerapia(false);
                setConsultarTerapia(true);
                setCrearEntidad(false);
                setConsultarEntidad(false);
                setCrearProfesional(false);
                setConsultarProfesional(false);
                setCrearUsuario(false);
                setConsultarUsuario(false);
                setExportarPacientes(false);
                setListarPacientes(false);
                setReplicarTerapia(false);
                setHistoriaClinica(false);
                break;

            case "crear_entidad":
                setNuevaCita(false);
                setCrearAgenda(false);
                setAgendas(false);
                setCrearPaciente(false);
                setConsultarCitas(false);
                setExportarAgenda(false);
                setExportarInforme(false);
                setResumenProfesional(false);
                setCrearTerapia(false);
                setConsultarTerapia(false);
                setCrearEntidad(true);
                setConsultarEntidad(false);
                setCrearProfesional(false);
                setConsultarProfesional(false);
                setCrearUsuario(false);
                setConsultarUsuario(false);
                setExportarPacientes(false);
                setListarPacientes(false);
                setReplicarTerapia(false);
                setHistoriaClinica(false);
                break;
            case "consultar_entidad":
                setNuevaCita(false);
                setCrearAgenda(false);
                setAgendas(false);
                setCrearPaciente(false);
                setConsultarCitas(false);
                setExportarAgenda(false);
                setExportarInforme(false);
                setResumenProfesional(false);
                setCrearTerapia(false);
                setConsultarTerapia(false);
                setCrearEntidad(false);
                setConsultarEntidad(true);
                setCrearProfesional(false);
                setConsultarProfesional(false);
                setCrearUsuario(false);
                setConsultarUsuario(false);
                setExportarPacientes(false);
                setListarPacientes(false);
                setReplicarTerapia(false);
                setHistoriaClinica(false);
                break;
            case "crear_profesional":
                setNuevaCita(false);
                setCrearAgenda(false);
                setAgendas(false);
                setCrearPaciente(false);
                setConsultarCitas(false);
                setExportarAgenda(false);
                setExportarInforme(false);
                setResumenProfesional(false);
                setCrearTerapia(false);
                setConsultarTerapia(false);
                setCrearEntidad(false);
                setConsultarEntidad(false);
                setCrearProfesional(true);
                setConsultarProfesional(false);
                setCrearUsuario(false);
                setConsultarUsuario(false);
                setExportarPacientes(false);
                setListarPacientes(false);
                setReplicarTerapia(false);
                setHistoriaClinica(false);
                break;
            case "consultar_profesional":
                setNuevaCita(false);
                setCrearAgenda(false);
                setAgendas(false);
                setCrearPaciente(false);
                setConsultarCitas(false);
                setExportarAgenda(false);
                setExportarInforme(false);
                setResumenProfesional(false);
                setCrearTerapia(false);
                setConsultarTerapia(false);
                setCrearEntidad(false);
                setConsultarEntidad(false);
                setCrearProfesional(false);
                setConsultarProfesional(true);
                setCrearUsuario(false);
                setConsultarUsuario(false);
                setExportarPacientes(false);
                setListarPacientes(false);
                setReplicarTerapia(false);
                setHistoriaClinica(false);
                break;
            case "crear_usuario":
                setNuevaCita(false);
                setCrearAgenda(false);
                setAgendas(false);
                setCrearPaciente(false);
                setConsultarCitas(false);
                setExportarAgenda(false);
                setExportarInforme(false);
                setResumenProfesional(false);
                setCrearTerapia(false);
                setConsultarTerapia(false);
                setCrearEntidad(false);
                setConsultarEntidad(false);
                setCrearProfesional(false);
                setConsultarProfesional(false);
                setCrearUsuario(true);
                setConsultarUsuario(false);
                setExportarPacientes(false);
                setListarPacientes(false);
                setReplicarTerapia(false);
                setHistoriaClinica(false);
                break;
            case "consultar_usuario":
                setNuevaCita(false);
                setCrearAgenda(false);
                setAgendas(false);
                setCrearPaciente(false);
                setConsultarCitas(false);
                setExportarAgenda(false);
                setExportarInforme(false);
                setResumenProfesional(false);
                setCrearTerapia(false);
                setConsultarTerapia(false);
                setCrearEntidad(false);
                setConsultarEntidad(false);
                setCrearProfesional(false);
                setConsultarProfesional(false);
                setCrearUsuario(false);
                setConsultarUsuario(true);
                setExportarPacientes(false);
                setListarPacientes(false);
                setReplicarTerapia(false);
                setHistoriaClinica(false);
                break;
            case "exportar_paciente":
                setNuevaCita(false);
                setCrearAgenda(false);
                setAgendas(false);
                setCrearPaciente(false);
                setConsultarCitas(false);
                setExportarAgenda(false);
                setExportarInforme(false);
                setResumenProfesional(false);
                setCrearTerapia(false);
                setConsultarTerapia(false);
                setCrearEntidad(false);
                setConsultarEntidad(false);
                setCrearProfesional(false);
                setConsultarProfesional(false);
                setCrearUsuario(false);
                setConsultarUsuario(false);
                setExportarPacientes(true);
                setListarPacientes(false);
                setReplicarTerapia(false);
                setHistoriaClinica(false);
                break;
                case "consultar_paciente":
                setNuevaCita(false);
                setCrearAgenda(false);
                setAgendas(false);
                setCrearPaciente(false);
                setConsultarCitas(false);
                setExportarAgenda(false);
                setExportarInforme(false);
                setResumenProfesional(false);
                setCrearTerapia(false);
                setConsultarTerapia(false);
                setCrearEntidad(false);
                setConsultarEntidad(false);
                setCrearProfesional(false);
                setConsultarProfesional(false);
                setCrearUsuario(false);
                setConsultarUsuario(false);
                setExportarPacientes(false);
                setListarPacientes(true);
                setReplicarTerapia(false);
                setHistoriaClinica(false);
                break;
                
                case "exportar_informe":
                setNuevaCita(false);
                setCrearAgenda(false);
                setAgendas(false);
                setCrearPaciente(false);
                setConsultarCitas(false);
                setExportarAgenda(false);
                setExportarInforme(true);
                setResumenProfesional(false);
                setCrearTerapia(false);
                setConsultarTerapia(false);
                setCrearEntidad(false);
                setConsultarEntidad(false);
                setCrearProfesional(false);
                setConsultarProfesional(false);
                setCrearUsuario(false);
                setConsultarUsuario(false);
                setExportarPacientes(false);
                setListarPacientes(false);
                setReplicarTerapia(false);
                setHistoriaClinica(false);
                break;
                
                case "replicar_terapia":
                setNuevaCita(false);
                setCrearAgenda(false);
                setAgendas(false);
                setCrearPaciente(false);
                setConsultarCitas(false);
                setExportarAgenda(false);
                setExportarInforme(false);
                setResumenProfesional(false);
                setCrearTerapia(false);
                setConsultarTerapia(false);
                setCrearEntidad(false);
                setConsultarEntidad(false);
                setCrearProfesional(false);
                setConsultarProfesional(false);
                setCrearUsuario(false);
                setConsultarUsuario(false);
                setExportarPacientes(false);
                setListarPacientes(false);
                setReplicarTerapia(true);
                setHistoriaClinica(false);
                break;
                
                case "historia_clinica":
                setNuevaCita(false);
                setCrearAgenda(false);
                setAgendas(false);
                setCrearPaciente(false);
                setConsultarCitas(false);
                setExportarAgenda(false);
                setExportarInforme(false);
                setResumenProfesional(false);
                setCrearTerapia(false);
                setConsultarTerapia(false);
                setCrearEntidad(false);
                setConsultarEntidad(false);
                setCrearProfesional(false);
                setConsultarProfesional(false);
                setCrearUsuario(false);
                setConsultarUsuario(false);
                setExportarPacientes(false);
                setListarPacientes(false);
                setReplicarTerapia(false);
                setHistoriaClinica(true);
                break;
        }

        return "";
    }

    /**
     * @return the nuevaCita
     */
    public boolean isNuevaCita() {
        return nuevaCita;
    }

    /**
     * @param nuevaCita the nuevaCita to set
     */
    public void setNuevaCita(boolean nuevaCita) {
        this.nuevaCita = nuevaCita;
    }

    /**
     * @return the crearAgenda
     */
    public boolean isCrearAgenda() {
        return crearAgenda;
    }

    /**
     * @param crearAgenda the crearAgenda to set
     */
    public void setCrearAgenda(boolean crearAgenda) {
        this.crearAgenda = crearAgenda;
    }

    /**
     * @return the agendas
     */
    public boolean isAgendas() {
        return agendas;
    }

    /**
     * @param agendas the agendas to set
     */
    public void setAgendas(boolean agendas) {
        this.agendas = agendas;
    }

    /**
     * @return the crearPaciente
     */
    public boolean isCrearPaciente() {
        return crearPaciente;
    }

    /**
     * @param crearPaciente the crearPaciente to set
     */
    public void setCrearPaciente(boolean crearPaciente) {
        this.crearPaciente = crearPaciente;
    }

    /**
     * @return the consultarCitas
     */
    public boolean isConsultarCitas() {
        return consultarCitas;
    }

    /**
     * @param consultarCitas the consultarCitas to set
     */
    public void setConsultarCitas(boolean consultarCitas) {
        this.consultarCitas = consultarCitas;
    }

    /**
     * @return the exportarAgenda
     */
    public boolean isExportarAgenda() {
        return exportarAgenda;
    }

    /**
     * @param exportarAgenda the exportarAgenda to set
     */
    public void setExportarAgenda(boolean exportarAgenda) {
        this.exportarAgenda = exportarAgenda;
    }   
   
    /**
     * @return the exportarInforme
     */
    public boolean isExportarInforme() {
        return exportarInforme;
    }

    /**
     * @param exportarInforme the exportarAgenda to set
     */
    public void setExportarInforme(boolean exportarInforme) {
        this.exportarInforme = exportarInforme;
    }
    
    /**
     * @return the resumenProfesional
     */
    public boolean isResumenProfesional() {
        return resumenProfesional;
    }

    /**
     * @param resumenProfesional the resumenProfesional to set
     */
    public void setResumenProfesional(boolean resumenProfesional) {
        this.resumenProfesional = resumenProfesional;
    }

    /**
     * @return the crearTerapia
     */
    public boolean isCrearTerapia() {
        return crearTerapia;
    }

    /**
     * @param crearTerapia the crearTerapia to set
     */
    public void setCrearTerapia(boolean crearTerapia) {
        this.crearTerapia = crearTerapia;
    }

    /**
     * @return the consultarTerapia
     */
    public boolean isConsultarTerapia() {
        return consultarTerapia;
    }

    /**
     * @param consultarTerapia the consultarTerapia to set
     */
    public void setConsultarTerapia(boolean consultarTerapia) {
        this.consultarTerapia = consultarTerapia;
    }

    /**
     * @return the crearEntidad
     */
    public boolean isCrearEntidad() {
        return crearEntidad;
    }

    /**
     * @param crearEntidad the crearEntidad to set
     */
    public void setCrearEntidad(boolean crearEntidad) {
        this.crearEntidad = crearEntidad;
    }

    /**
     * @return the consultarEntidad
     */
    public boolean isConsultarEntidad() {
        return consultarEntidad;
    }

    /**
     * @param consultarEntidad the consultarEntidad to set
     */
    public void setConsultarEntidad(boolean consultarEntidad) {
        this.consultarEntidad = consultarEntidad;
    }

    /**
     * @return the crearProfesional
     */
    public boolean isCrearProfesional() {
        return crearProfesional;
    }

    /**
     * @param crearProfesional the crearProfesional to set
     */
    public void setCrearProfesional(boolean crearProfesional) {
        this.crearProfesional = crearProfesional;
    }

    /**
     * @return the consultarProfesional
     */
    public boolean isConsultarProfesional() {
        return consultarProfesional;
    }

    /**
     * @param consultarProfesional the consultarProfesional to set
     */
    public void setConsultarProfesional(boolean consultarProfesional) {
        this.consultarProfesional = consultarProfesional;
    }

    /**
     * @return the crearUsuario
     */
    public boolean isCrearUsuario() {
        return crearUsuario;
    }

    /**
     * @param crearUsuario the crearUsuario to set
     */
    public void setCrearUsuario(boolean crearUsuario) {
        this.crearUsuario = crearUsuario;
    }

    /**
     * @return the consultarUsuario
     */
    public boolean isConsultarUsuario() {
        return consultarUsuario;
    }

    /**
     * @param consultarUsuario the consultarUsuario to set
     */
    public void setConsultarUsuario(boolean consultarUsuario) {
        this.consultarUsuario = consultarUsuario;
    }

    /**
     * @return the exportarPacientes
     */
    public boolean isExportarPacientes() {
        return exportarPacientes;
    }

    /**
     * @param exportarPacientes the exportarPacientes to set
     */
    public void setExportarPacientes(boolean exportarPacientes) {
        this.exportarPacientes = exportarPacientes;
    }

    /**
     * @return the listarPacientes
     */
    public boolean isListarPacientes() {
        return listarPacientes;
    }

    /**
     * @param listarPacientes the listarPacientes to set
     */
    public void setListarPacientes(boolean listarPacientes) {
        this.listarPacientes = listarPacientes;
    }

    /**
     * @return the replicarTerapia
     */
    public boolean isReplicarTerapia() {
        return replicarTerapia;
    }

    /**
     * @param replicarTerapia the replicarTerapia to set
     */
    public void setReplicarTerapia(boolean replicarTerapia) {
        this.replicarTerapia = replicarTerapia;
    }

    /**
     * @return the historiaClinica
     */
    public boolean isHistoriaClinica() {
        return historiaClinica;
    }

    /**
     * @param historiaClinica the historiaClinica to set
     */
    public void setHistoriaClinica(boolean historiaClinica) {
        this.historiaClinica = historiaClinica;
    }

}
