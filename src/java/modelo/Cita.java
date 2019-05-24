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
public class Cita {

    private Integer codigo;
    private Paciente paciente;
    private Date fechaRegistro;
    private Date fecha;
    private Date hora;
    private Especialidad especialidad;
    private Profesional profesional;
    private Entidad entidad;
    private String numeroAutorizacion;
    private String codigoObservacion;
    private String observaciones;
    private Usuario usuario;
    private Objeto estado;
    private Procedimiento procedimiento;
    private List<Procedimiento> listaProcedimientos;
    private String motivo;
    private Date fechaRegistroEstado;
    private String responsable;
    private String medio;
    private Boolean tieneAtencion;
    private String duracionExtendida;
    private Date oportunidadDeseada;
    private String observaciones2;
    

    public Cita() {
        this.codigoObservacion = "";
        this.paciente = new Paciente();
        this.especialidad = new Especialidad();
        this.profesional = new Profesional();
        this.entidad = new Entidad();
        this.usuario = new Usuario();
        this.estado = new Objeto();
        listaProcedimientos = new ArrayList<>();
        this.procedimiento = new Procedimiento();        
    }

    /**
     * @return the paciente
     */
    public Paciente getPaciente() {
        return paciente;
    }

    /**
     * @param paciente the paciente to set
     */
    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    /**
     * @return the fechaRegistro
     */
    public Date getFechaRegistro() {
        return fechaRegistro;
    }

    /**
     * @param fechaRegistro the fechaRegistro to set
     */
    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    /**
     * @return the especialidad
     */
    public Especialidad getEspecialidad() {
        return especialidad;
    }

    /**
     * @param especialidad the especialidad to set
     */
    public void setEspecialidad(Especialidad especialidad) {
        this.especialidad = especialidad;
    }

    /**
     * @return the profesional
     */
    public Profesional getProfesional() {
        return profesional;
    }

    /**
     * @param profesional the profesional to set
     */
    public void setProfesional(Profesional profesional) {
        this.profesional = profesional;
    }

    /**
     * @return the entidad
     */
    public Entidad getEntidad() {
        return entidad;
    }

    /**
     * @param entidad the entidad to set
     */
    public void setEntidad(Entidad entidad) {
        this.entidad = entidad;
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
     * @return the usuario
     */
    public Usuario getUsuario() {
        return usuario;
    }

    /**
     * @param usuario the usuario to set
     */
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    /**
     * @return the codigo
     */
    public Integer getCodigo() {
        return codigo;
    }

    /**
     * @param codigo the codigo to set
     */
    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    /**
     * @return the listaProcedimientos
     */
    public List<Procedimiento> getListaProcedimientos() {
        return listaProcedimientos;
    }

    /**
     * @param listaProcedimientos the listaProcedimientos to set
     */
    public void setListaProcedimientos(List<Procedimiento> listaProcedimientos) {
        this.listaProcedimientos = listaProcedimientos;
    }

    /**
     * @return the estado
     */
    public Objeto getEstado() {
        return estado;
    }

    /**
     * @param estado the estado to set
     */
    public void setEstado(Objeto estado) {
        this.estado = estado;
    }

    /**
     * @return the motivo
     */
    public String getMotivo() {
        return motivo;
    }

    /**
     * @param motivo the motivo to set
     */
    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    /**
     * @return the fechaRegistroEstado
     */
    public Date getFechaRegistroEstado() {
        return fechaRegistroEstado;
    }

    /**
     * @param fechaRegistroEstado the fechaRegistroEstado to set
     */
    public void setFechaRegistroEstado(Date fechaRegistroEstado) {
        this.fechaRegistroEstado = fechaRegistroEstado;
    }

    /**
     * @return the responsable
     */
    public String getResponsable() {
        return responsable;
    }

    /**
     * @param responsable the responsable to set
     */
    public void setResponsable(String responsable) {
        this.responsable = responsable;
    }

    /**
     * @return the medio
     */
    public String getMedio() {
        return medio;
    }

    /**
     * @param medio the medio to set
     */
    public void setMedio(String medio) {
        this.medio = medio;
    }

    /**
     * @return the codigoObservacion
     */
    public String getCodigoObservacion() {
        return codigoObservacion;
    }

    /**
     * @param codigoObservacion the codigoObservacion to set
     */
    public void setCodigoObservacion(String codigoObservacion) {
        this.codigoObservacion = codigoObservacion;
    }

    /**
     * @return the procedimiento
     */
    public Procedimiento getProcedimiento() {
        return procedimiento;
    }

    /**
     * @param procedimiento the procedimiento to set
     */
    public void setProcedimiento(Procedimiento procedimiento) {
        this.procedimiento = procedimiento;
    }

    /**
     * @return the duracionExtendida
     */
    public String getDuracionExtendida() {
        return duracionExtendida;
    }

    /**
     * @param duracionExtendida the duracionExtendida to set
     */
    public void setDuracionExtendida(String duracionExtendida) {
        this.duracionExtendida = duracionExtendida;
    }

    /**
     * @return the tieneAtencion
     */
    public Boolean getTieneAtencion() {
        return tieneAtencion;
    }

    /**
     * @param tieneAtencion the tieneAtencion to set
     */
    public void setTieneAtencion(Boolean tieneAtencion) {
        this.tieneAtencion = tieneAtencion;
    }

    public Date getOportunidadDeseada() {
        return oportunidadDeseada;
    }

    public void setOportunidadDeseada(Date oportunidadDeseada) {
        this.oportunidadDeseada = oportunidadDeseada;
    }

    public String getObservaciones2() {
        return observaciones2;
    }

    public void setObservaciones2(String observaciones2) {
        this.observaciones2 = observaciones2;
    }
    
    
}
