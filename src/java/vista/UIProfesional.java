/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vista;

import controlador.GestorCita;
import controlador.GestorProfesional;
import controlador.GestorTerapia;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.faces.context.FacesContext;
import modelo.Cita;
import modelo.DetalleTerapia;
import modelo.EstudioAudiologico;
import modelo.Profesional;
import modelo.Valoracion;
import util.Utilidades;

/**
 *
 * @author Andres
 */
public class UIProfesional implements Serializable {

    private GestorCita gestorCita;
    private Profesional profesional;
    private Boolean existe;
    private List<Cita> listaCitasProfesionalDia;
    private List<Cita> listaCitasPaciente;
    private Cita citaSeleccionada;
    private GestorProfesional gestorProfesional;
    private GestorTerapia gestorTerapia;
    private List<Profesional> listaProfesionales;
    public Utilidades util = new Utilidades();
    private String codigoAtencion;
    private String rutaRecetario;

    public UIProfesional() throws Exception {
        gestorCita = new GestorCita();
        citaSeleccionada = new Cita();
        gestorProfesional = new GestorProfesional();
        gestorTerapia = new GestorTerapia();
        consultarCitasProfesionalDia();
        profesional = new Profesional();
    }

    public void consultarCitasProfesionalDia() {
        try {
            FacesContext facesContext = FacesContext.getCurrentInstance();
            UILogin uilogin = (UILogin) facesContext.getExternalContext().getSessionMap().get("loginBean");
            listaCitasProfesionalDia = gestorCita.consultarCitasProfesionalDia(uilogin.getSesion().getUsuario().getId());
        } catch (Exception ex) {
            Logger.getLogger(UIProfesional.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void consultarCitasPaciente() {
        try {
            FacesContext facesContext = FacesContext.getCurrentInstance();
            UILogin uilogin = (UILogin) facesContext.getExternalContext().getSessionMap().get("loginBean");
            listaCitasPaciente = gestorCita.consultarCitasPaciente(citaSeleccionada.getPaciente().getIdentificacion());
        } catch (Exception ex) {
            Logger.getLogger(UIProfesional.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void consultarCodigoAtencionCita(Cita cita) {
        String codigo;
        try {
            codigo = gestorCita.consultarCodigoAtencionCita(cita).toString();
            if (cita.getListaProcedimientos().get(0).getTipo() == 1) {
                //codigoAtencion = "window.open('.././exportar?nomReporte=terapia&amp;parametros=codigoTerapia&amp;valores=" + codigo + "&amp;tipos=I');";
                codigoAtencion = "window.open('.././exportar?nomReporte=terapia&parametros=codigoTerapia&valores=" + codigo + "&tipos=I');";
            } else if (cita.getListaProcedimientos().get(0).getTipo() == 2) {
                //codigoAtencion = "window.open('.././exportar?nomReporte=valoracion&amp;parametros=codigoValoracion&amp;valores=" + codigo + "&amp;tipos=I');";
                codigoAtencion = "window.open('.././exportar?nomReporte=valoracion&parametros=codigoValoracion&valores=" + codigo + "&tipos=I');";
                rutaRecetario = "window.open('.././exportar?nomReporte=recetario&parametros=codigoValoracion&valores=" + codigo + "&tipos=I');";
            } else if (cita.getListaProcedimientos().get(0).getTipo() == 4) {
                //codigoAtencion = "window.open('.././exportar?nomReporte=valoracion&amp;parametros=codigoValoracion&amp;valores=" + codigo + "&amp;tipos=I');";
                codigoAtencion = "window.open('.././exportar?nomReporte=estudio_audiologico&parametros=codigo_estudio&valores=" + codigo + "&tipos=I');";                
            }
            //#{itemCita.listaProcedimientos.get(0).tipo eq 1 ? 
            //"window.open('.././exportar?nomReporte=terapia&amp;parametros=codigoTerapia&amp;valores=".(uiprofesional.codigoAtencion)."&amp;tipos=I');"
            //: (itemCita.listaProcedimientos.get(0).tipo eq 2 ? 
            //'window.open(\'.././exportar?nomReporte=valoracion&amp;parametros=codigoValoracion&amp;valores='+(uiprofesional.codigoAtencion)+'&amp;tipos=I\');'
        } catch (Exception ex) {
            Logger.getLogger(UIProfesional.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void configurarTipoCita(Cita cita) {

        FacesContext contextoJSF = FacesContext.getCurrentInstance();
        ELContext contextoEL = contextoJSF.getELContext();
        ExpressionFactory ef = contextoJSF.getApplication().getExpressionFactory();
        List<DetalleTerapia> dt;        

        if (cita.getListaProcedimientos().get(0).getTipo() == 1) {
            try {
                UITerapia uiterapia = (UITerapia) ef.createValueExpression(contextoEL, "#{uiterapia}", UITerapia.class).getValue(contextoEL);
                //UITerapia uiterapia = gestorTerapia.consultarTerapiaPorCita(cita);                
                uiterapia.setTerapia(gestorTerapia.consultarTerapiaPorCita(cita));
                dt = gestorTerapia.consultarDetalleTerapiaPorCita(uiterapia.getTerapia());
                uiterapia.setDetalleTerapia(dt);
                uiterapia.setPermiteDividir(dt.size() == 1 && dt.get(0).getEstado().equalsIgnoreCase("i"));
                uiterapia.setGuardado(Boolean.FALSE);                
                //
                contextoJSF.getExternalContext().getRequestMap().put("uiterapia", UITerapia.class);
            } catch (Exception ex) {
                Logger.getLogger(UIProfesional.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (cita.getListaProcedimientos().get(0).getTipo() == 2) {
            UIValoracion uivaloracion = (UIValoracion) ef.createValueExpression(contextoEL, "#{uivaloracion}", UIValoracion.class).getValue(contextoEL);
            //limpiar variables
            uivaloracion.setValoracion(new Valoracion());
            uivaloracion.setEnviaTerapia(Boolean.TRUE);
            uivaloracion.setTerapiasAutorizadas("");
            uivaloracion.setGuardado(Boolean.FALSE);
            
            uivaloracion.getValoracion().setCita(cita);
            contextoJSF.getExternalContext().getRequestMap().put("uivaloracion", UIValoracion.class);
        } else if (cita.getListaProcedimientos().get(0).getTipo() == 4) {
            UIEstudioAudiologico uiestudioaudiologico = (UIEstudioAudiologico) ef.createValueExpression(contextoEL, "#{uiestudioaudiologico}", UIEstudioAudiologico.class).getValue(contextoEL);
            //limpiar variables
            //uiestudioaudiologico.setEstudioAudiologico(new EstudioAudiologico());
            
            uiestudioaudiologico.getEstudioAudiologico().setCita(cita);
            contextoJSF.getExternalContext().getRequestMap().put("uiestudioaudiologico", UIEstudioAudiologico.class);
        }
    }

    public void consultarProfesionales() {
        try {
            setListaProfesionales(gestorProfesional.listarProfesionales());
        } catch (Exception ex) {
            Logger.getLogger(UIEntidad.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void guardarProfesional() {
        try {
            Integer resultado = gestorProfesional.guardarProfesional(getProfesional(), getExiste());
            if (resultado > 0) {
                util.mostrarMensaje("El profesional se guardo exitosamente.");
                profesional = new Profesional();
            } else {
                util.mostrarMensaje("Se presento un error al guardar.");
            }
        } catch (Exception ex) {
            Logger.getLogger(UIProfesional.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void consultarProfesionalPorCedula() {
        Profesional p;
        String cedula = profesional.getCedula();
        try {
            p = gestorProfesional.consultarProfesionalPorCedula(profesional.getCedula());
            if (p != null) {
                profesional = p;
                setExiste((Boolean) true);
            } else {
                profesional = new Profesional();
                profesional.setCedula(cedula);
                setExiste((Boolean) false);
            }
        } catch (Exception ex) {
            Logger.getLogger(UIProfesional.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * @return the gestorCita
     */
    public GestorCita getGestorCita() {
        return gestorCita;
    }

    /**
     * @param gestorCita the gestorCita to set
     */
    public void setGestorCita(GestorCita gestorCita) {
        this.gestorCita = gestorCita;
    }

    /**
     * @return the listaCitasProfesionalDia
     */
    public List<Cita> getListaCitasProfesionalDia() {
        return listaCitasProfesionalDia;
    }

    /**
     * @param listaCitasProfesionalDia the listaCitasProfesionalDia to set
     */
    public void setListaCitasProfesionalDia(List<Cita> listaCitasProfesionalDia) {
        this.listaCitasProfesionalDia = listaCitasProfesionalDia;
    }

    /**
     * @return the citaSeleccionada
     */
    public Cita getCitaSeleccionada() {
        return citaSeleccionada;
    }

    /**
     * @param citaSeleccionada the citaSeleccionada to set
     */
    public void setCitaSeleccionada(Cita citaSeleccionada) {
        this.citaSeleccionada = citaSeleccionada;
    }

    /**
     * @return the listaCitasPaciente
     */
    public List<Cita> getListaCitasPaciente() {
        return listaCitasPaciente;
    }

    /**
     * @param listaCitasPaciente the listaCitasPaciente to set
     */
    public void setListaCitasPaciente(List<Cita> listaCitasPaciente) {
        this.listaCitasPaciente = listaCitasPaciente;
    }

    /**
     * @return the gestorProfesional
     */
    public GestorProfesional getGestorProfesional() {
        return gestorProfesional;
    }

    /**
     * @param gestorProfesional the gestorProfesional to set
     */
    public void setGestorProfesional(GestorProfesional gestorProfesional) {
        this.gestorProfesional = gestorProfesional;
    }

    /**
     * @return the listaProfesionales
     */
    public List<Profesional> getListaProfesionales() {
        return listaProfesionales;
    }

    /**
     * @param listaProfesionales the listaProfesionales to set
     */
    public void setListaProfesionales(List<Profesional> listaProfesionales) {
        this.listaProfesionales = listaProfesionales;
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
     * @return the existe
     */
    public Boolean getExiste() {
        return existe;
    }

    /**
     * @param existe the existe to set
     */
    public void setExiste(Boolean existe) {
        this.existe = existe;
    }

    /**
     * @return the codigoAtencion
     */
    public String getCodigoAtencion() {
        return codigoAtencion;
    }

    /**
     * @param codigoAtencion the codigoAtencion to set
     */
    public void setCodigoAtencion(String codigoAtencion) {
        this.codigoAtencion = codigoAtencion;
    }

    /**
     * @return the rutaRecetario
     */
    public String getRutaRecetario() {
        return rutaRecetario;
    }

    /**
     * @param rutaRecetario the rutaRecetario to set
     */
    public void setRutaRecetario(String rutaRecetario) {
        this.rutaRecetario = rutaRecetario;
    }

}
