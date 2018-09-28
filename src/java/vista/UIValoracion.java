/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vista;

import controlador.GestorPaciente;
import controlador.GestorProcedimiento;
import controlador.GestorReporte;
import controlador.GestorUtilidades;
import controlador.GestorValoracion;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.model.SelectItem;
import modelo.Cita;
import modelo.Terapia;
import modelo.Valoracion;
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
    public Utilidades util = new Utilidades();
    private GestorUtilidades gestorUtilidades;

    private List<SelectItem> listaTipoFormato;
    private Integer codigoValoracion;
    private Boolean guardado;

    private List<SelectItem> listaCondicion;
    private List<SelectItem> comboConfirmacion;

    private List<SelectItem> listaCodigosDiagnostico;
    private List<SelectItem> listaCodigosDiagnostico2;

    private Boolean enviaTerapia;

    public UIValoracion() throws Exception {
        this.valoracion = new Valoracion();
        this.valoracion.setCita(new Cita());
        gestorUtilidades = new GestorUtilidades();
        guardado = Boolean.FALSE;
        consultarTerapias();
        cargarListaTipoFormato();
        cargarListaCondicion();
        cargarListaCodigosDiagnostico();
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
            //contrato.getListaRequeridos().get(event.getRowIndex());
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

    private void cargarListaCodigosDiagnostico() {
        try {
            setListaCodigosDiagnostico(getGestorUtilidades().listarCombo("DIAGNOSTICO_CIE10", "COMBINADO"));
            setListaCodigosDiagnostico2(getGestorUtilidades().listarCombo("DIAGNOSTICO_CIE10", "COMBINADO"));
        } catch (Exception ex) {
            Logger.getLogger(UICita.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void guardarValoracion() {
        try {
            gestorValoracion = new GestorValoracion();
            codigoValoracion = gestorValoracion.guardarValoracion(valoracion, enviaTerapia);

            if (codigoValoracion > 0) {
                guardado = Boolean.TRUE;
                util.mostrarMensaje("La Valoracion Nro." + codigoValoracion + " se guardo exitosamente.");
            } else {
                guardado = Boolean.FALSE;
                util.mostrarMensaje("Se presento un error al guardar.");
            }
        } catch (Exception ex) {
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

}
