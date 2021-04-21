/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vista;

import controlador.GestorPaciente;
import controlador.GestorProcedimiento;
import controlador.GestorUtilidades;
import controlador.GestorValoracion;
import controlador.GestorDiagnostico;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import modelo.Cita;
import modelo.Terapia;
import modelo.Valoracion;
import modelo.Diagnostico;
import modelo.Paciente;
import org.primefaces.event.CellEditEvent;
import util.Utilidades;


/**
 *
 * @author Andres
 */
public class UIValoracion implements Serializable {

    private Valoracion valoracion;

    private List<SelectItem> listaTerapias;
    private GestorValoracion gestorValoracion;
    private GestorDiagnostico gestorDiagnostico;
    public Utilidades util = new Utilidades();
    private GestorUtilidades gestorUtilidades;    
    private FacesContext contextoJSF;
    private ELContext contextoEL;
    private ExpressionFactory ef;
    

    private List<SelectItem> listaTipoFormato;
    private Integer codigoValoracion;
    private Boolean guardado;

    private List<SelectItem> listaCondicion;
    private List<SelectItem> comboConfirmacion;

    private List<SelectItem> listaCodigosDiagnostico;
    private List<SelectItem> listaCodigosDiagnostico2;

    private Boolean enviaTerapia;
    private String terapiasAutorizadas;

    private String manejoVentana;
    private Paciente paciente;

    public UIValoracion() throws Exception {
        this.valoracion = new Valoracion();
        this.valoracion.setCita(new Cita());
        gestorUtilidades = new GestorUtilidades();
        gestorDiagnostico = new GestorDiagnostico();
        paciente = new Paciente();
        guardado = Boolean.FALSE;
        consultarTerapias();
        cargarListaTipoFormato();
        cargarListaCondicion();
        enviaTerapia = Boolean.TRUE;
    }

    public void adicionarTerapia() {
        valoracion.getListaTerapias().add(new Terapia());
    }

    public void removerTerapia() {
        if (valoracion.getListaTerapias().size() > 1) {
            valoracion.getListaTerapias().remove(valoracion.getListaTerapias().size() - 1);
        }
    }

    public void onCellEdit(CellEditEvent event) {
        Terapia t;
        if (event.getColumn().getHeaderText().equalsIgnoreCase("terapia")) {
            t = valoracion.getListaTerapias().get(event.getRowIndex());
            
            for (SelectItem si : listaTerapias) {
                if (event.getNewValue() == null) {
                    t.getProcedimiento().setNombre(null);
                    break;
                } else if (((String) si.getValue()).equalsIgnoreCase((String) event.getNewValue())) {
                    t.getProcedimiento().setNombre(si.getLabel());
                    break;
                }
            }
        }
        if (event.getColumn().getHeaderText().equalsIgnoreCase("autorizada")) {
            t = valoracion.getListaTerapias().get(event.getRowIndex());
            //contrato.getListaRequeridos().get(event.getRowIndex());
            for (SelectItem si : comboConfirmacion) {
                if (((String) si.getValue()).equalsIgnoreCase((String) event.getNewValue())) {
                    //t.getProcedimiento().setNombre(si.getLabel());
                    t.getAutorizada().setNombre(si.getLabel());
                    break;
                }
            }
        }
    }

    public void consultarPaciente(String identificacion) {
        try {
            GestorPaciente gestorPaciente = new GestorPaciente();
            this.valoracion.getCita().setPaciente(gestorPaciente.consultarPacientePorId(identificacion));
        } catch (Exception ex) {
            Logger.getLogger(UIValoracion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
        public void consultarPaciente2() {
        try {            
            GestorPaciente gestorPaciente = new GestorPaciente();
            contextoJSF = FacesContext.getCurrentInstance();
            contextoEL = contextoJSF.getELContext();
            ef = contextoJSF.getApplication().getExpressionFactory();            
            String identificacion = (String) ef.createValueExpression(contextoEL, "#{uivaloracion.valoracion.cita.paciente.identificacion}", String.class).getValue(contextoEL);                                                           
            this.valoracion.getCita().setPaciente(gestorPaciente.consultarPacientePorId(identificacion));                                                
        } catch (Exception ex) {
            Logger.getLogger(UIValoracion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void consultarTerapias() {
        comboConfirmacion = new ArrayList<>();
        comboConfirmacion.add(new SelectItem("1", "NO"));
        comboConfirmacion.add(new SelectItem("2", "SI"));

        try {
            GestorProcedimiento gestorProcedimiento = new GestorProcedimiento();
            listaTerapias = gestorProcedimiento.consultarTerapias();
        } catch (Exception ex) {
            Logger.getLogger(UIValoracion.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void cargarListaTipoFormato() {
        try {
            setListaTipoFormato(getGestorUtilidades().listarCombo("TIPO_FORMATO", "VALUE"));
        } catch (Exception ex) {
            Logger.getLogger(UICita.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public List<String> listarDiagnosticos(String query) throws Exception {
        ArrayList<Diagnostico> listaDiagnosticos;
        listaDiagnosticos = gestorDiagnostico.listarDiagnosticos(query);
        List<String> listaDiag = new ArrayList<>();
        for (Diagnostico d : listaDiagnosticos) {
            listaDiag.add(d.getNombre_diagostico());
        }
        return listaDiag;
    }

    public void guardarValoracion() {
        manejoVentana = "";
        Boolean valido = Boolean.TRUE;
        try {
            if (valoracion.getCodigoRIPS().equalsIgnoreCase("")) {
                util.mostrarMensaje("Debe especificar el Codigo RIPS.");
                valido = Boolean.FALSE;
            }
            if (valoracion.getDiagnostico1().getNombre_diagostico() == null) {
                util.mostrarMensaje("Debe especificar el diagnóstico primario.");
                valido = Boolean.FALSE;
            }
            if (valoracion.getDiagnostico2().getNombre_diagostico() == null) {
                util.mostrarMensaje("Debe especificar el diagnóstico secundario.");
                valido = Boolean.FALSE;
            }
            if (valoracion.getNombreAcompanante().equalsIgnoreCase("")) {
                util.mostrarMensaje("Debe especificar el nombre del acompañante.");
                valido = Boolean.FALSE;
            }
            if (valoracion.getParentescoAcompanante().equalsIgnoreCase("")) {
                util.mostrarMensaje("Debe especificar el parentesco del acompañante.");
                valido = Boolean.FALSE;
            }
            if (valoracion.getRemitidoPor().equalsIgnoreCase("")) {
                util.mostrarMensaje("Debe especificar quien lo remite.");
                valido = Boolean.FALSE;
            }
            if (valoracion.getDireccionAcompanante().equalsIgnoreCase("")) {
                util.mostrarMensaje("Debe especificar la dirección del acompañante.");
                valido = Boolean.FALSE;
            }
            if (valoracion.getTelefonoAcompanante().equalsIgnoreCase("")) {
                util.mostrarMensaje("Debe especificar un teléfono del acompañante.");
                valido = Boolean.FALSE;
            }
            if (valoracion.getTipoFormato().equalsIgnoreCase("-1")) {
                util.mostrarMensaje("Debe especificar el tipo de formato.");
                valido = Boolean.FALSE;
            }
            if (valoracion.getMotivoConsulta().equalsIgnoreCase("")) {
                util.mostrarMensaje("Debe especificar el motivo de la consulta.");
                valido = Boolean.FALSE;
            }
            if (valoracion.getAntecedentesEvaluacion().equalsIgnoreCase("")) {
                util.mostrarMensaje("Debe especificar los antecedentes de la evaluación.");
                valido = Boolean.FALSE;
            }
            if (valoracion.getImpresionDiagnostica().equalsIgnoreCase("")) {
                util.mostrarMensaje("Debe especificar la impresión diagnóstica.");
                valido = Boolean.FALSE;
            }
            if (valoracion.getConductaSeguir().equalsIgnoreCase("")) {
                util.mostrarMensaje("Debe especificar la conducta a seguir.");
                valido = Boolean.FALSE;
            }
            if (terapiasAutorizadas.equalsIgnoreCase("v")) {
                util.mostrarMensaje("Debe especificar si viene con terapias autorizadas.");
                valido = Boolean.FALSE;
            }
            if (enviaTerapia && valoracion.getObservacionRecetario().equalsIgnoreCase("")) {
                util.mostrarMensaje("Si habilita terapias debe especificar la observacion del recetario.");
                valido = Boolean.FALSE;
            }
            if (enviaTerapia && valoracion.getListaTerapias().isEmpty()) {
                util.mostrarMensaje("Si habilita terapias debe especificar una o mas terapias.");
                valido = Boolean.FALSE;
            }

            if (enviaTerapia && !valoracion.getListaTerapias().isEmpty()) {
                for (Terapia t : valoracion.getListaTerapias()) {
                    if (t.getProcedimiento().getCodigo() == null) {
                        util.mostrarMensaje("Falta especificar el nombre de una o mas terapias.");
                        valido = Boolean.FALSE;
                        break;
                    }
                    if (t.getCantidadFormulada() == null) {
                        util.mostrarMensaje("Falta especificar la cantidad en una o mas terapias.");
                        valido = Boolean.FALSE;
                        break;
                    }
                }
            }

            if (valido) {
                gestorValoracion = new GestorValoracion();
                codigoValoracion = gestorValoracion.guardarValoracion(valoracion, enviaTerapia, terapiasAutorizadas);

                if (codigoValoracion > 0) {
                    guardado = Boolean.TRUE;
                    util.mostrarMensaje("La Valoracion Nro." + codigoValoracion + " se guardo exitosamente.");
                    manejoVentana = "window.close();";
                } else {
                    guardado = Boolean.FALSE;
                    util.mostrarMensaje("Se presento un error al guardar.");
                }
            }

        } catch (Exception ex) {
            util.mostrarMensaje(ex.getMessage());
            Logger.getLogger(UIValoracion.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void cargarListaCondicion() {
        try {
            setListaCondicion(gestorUtilidades.listarCombo("CONDICION_TERAPIA", "VALUE"));
        } catch (Exception ex) {
            Logger.getLogger(UICita.class.getName()).log(Level.SEVERE, null, ex);
        }
    }           
    
    /**
     * @return the valoracion
     */
    public Valoracion getValoracion() {
        return valoracion;
    }

    /**
     * @param valoracion the valoracion to set
     */
    public void setValoracion(Valoracion valoracion) {
        this.valoracion = valoracion;
    }

    /**
     * @return the listaTerapias
     */
    public List<SelectItem> getListaTerapias() {
        return listaTerapias;
    }

    /**
     * @param listaTerapias the listaTerapias to set
     */
    public void setListaTerapias(List<SelectItem> listaTerapias) {
        this.listaTerapias = listaTerapias;
    }

    /**
     * @return the gestorUtilidades
     */
    public GestorUtilidades getGestorUtilidades() {
        return gestorUtilidades;
    }

    /**
     * @param gestorUtilidades the gestorUtilidades to set
     */
    public void setGestorUtilidades(GestorUtilidades gestorUtilidades) {
        this.gestorUtilidades = gestorUtilidades;
    }

    /**
     * @return the listaTipoFormato
     */
    public List<SelectItem> getListaTipoFormato() {
        return listaTipoFormato;
    }

    /**
     * @param listaTipoFormato the listaTipoFormato to set
     */
    public void setListaTipoFormato(List<SelectItem> listaTipoFormato) {
        this.listaTipoFormato = listaTipoFormato;
    }

    /**
     * @return the codigoValoracion
     */
    public Integer getCodigoValoracion() {
        return codigoValoracion;
    }

    /**
     * @param codigoValoracion the codigoValoracion to set
     */
    public void setCodigoValoracion(Integer codigoValoracion) {
        this.codigoValoracion = codigoValoracion;
    }

    /**
     * @return the guardado
     */
    public Boolean getGuardado() {
        return guardado;
    }

    /**
     * @param guardado the guardado to set
     */
    public void setGuardado(Boolean guardado) {
        this.guardado = guardado;
    }

    /**
     * @return the listaCondicion
     */
    public List<SelectItem> getListaCondicion() {
        return listaCondicion;
    }

    /**
     * @param listaCondicion the listaCondicion to set
     */
    public void setListaCondicion(List<SelectItem> listaCondicion) {
        this.listaCondicion = listaCondicion;
    }

    /**
     * @return the listaCodigosDiagnostico
     */
    public List<SelectItem> getListaCodigosDiagnostico() {
        return listaCodigosDiagnostico;
    }

    /**
     * @param listaCodigosDiagnostico the listaCodigosDiagnostico to set
     */
    public void setListaCodigosDiagnostico(List<SelectItem> listaCodigosDiagnostico) {
        this.listaCodigosDiagnostico = listaCodigosDiagnostico;
    }

    /**
     * @return the listaCodigosDiagnostico2
     */
    public List<SelectItem> getListaCodigosDiagnostico2() {
        return listaCodigosDiagnostico2;
    }

    /**
     * @param listaCodigosDiagnostico2 the listaCodigosDiagnostico2 to set
     */
    public void setListaCodigosDiagnostico2(List<SelectItem> listaCodigosDiagnostico2) {
        this.listaCodigosDiagnostico2 = listaCodigosDiagnostico2;
    }

    /**
     * @return the enviaTerapia
     */
    public Boolean getEnviaTerapia() {
        return enviaTerapia;
    }

    /**
     * @param enviaTerapia the enviaTerapia to set
     */
    public void setEnviaTerapia(Boolean enviaTerapia) {
        this.enviaTerapia = enviaTerapia;
    }

    /**
     * @return the comboConfirmacion
     */
    public List<SelectItem> getComboConfirmacion() {
        return comboConfirmacion;
    }

    /**
     * @param comboConfirmacion the comboConfirmacion to set
     */
    public void setComboConfirmacion(List<SelectItem> comboConfirmacion) {
        this.comboConfirmacion = comboConfirmacion;
    }

    public GestorDiagnostico getGestorDiagnostico() {
        return gestorDiagnostico;
    }

    public void setGestorDiagnostico(GestorDiagnostico gestorDiagnostico) {
        this.gestorDiagnostico = gestorDiagnostico;
    }

    public String getTerapiasAutorizadas() {
        return terapiasAutorizadas;
    }

    /**
     * @param terapiasAutorizadas the terapiasAutorizadas to set
     */
    public void setTerapiasAutorizadas(String terapiasAutorizadas) {
        this.terapiasAutorizadas = terapiasAutorizadas;
    }

    /**
     * @return the manejoVentana
     */
    public String getManejoVentana() {
        return manejoVentana;
    }

    /**
     * @param manejoVentana the manejoVentana to set
     */
    public void setManejoVentana(String manejoVentana) {
        this.manejoVentana = manejoVentana;
    }

    public GestorValoracion getGestorValoracion() {
        return gestorValoracion;
    }

    public void setGestorValoracion(GestorValoracion gestorValoracion) {
        this.gestorValoracion = gestorValoracion;
    }     

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }        

    public FacesContext getContextoJSF() {
        return contextoJSF;
    }

    public void setContextoJSF(FacesContext contextoJSF) {
        this.contextoJSF = contextoJSF;
    }

    public ELContext getContextoEL() {
        return contextoEL;
    }

    public void setContextoEL(ELContext contextoEL) {
        this.contextoEL = contextoEL;
    }

    public ExpressionFactory getEf() {
        return ef;
    }

    public void setEf(ExpressionFactory ef) {
        this.ef = ef;
    }
    
    

}
