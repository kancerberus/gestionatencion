/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vista;

import controlador.GestorCita;
import controlador.GestorHistoriaClinica;
import controlador.GestorPaciente;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.Cita;
import modelo.Entidad;
import modelo.Paciente;
import util.Utilidades;

/**
 *
 * @author Andres
 */
public class UIHistoriaClinica {

    private Cita cita;
    private GestorPaciente gestorPaciente;
    private GestorHistoriaClinica gestorHistoriaClinica;
    private GestorCita gestorCita;
    private Utilidades util = new Utilidades();
    private List<Cita> listaCitasPaciente;
    private String codigoAtencion;

    public UIHistoriaClinica() {
        try {
            cita = new Cita();
            gestorPaciente = new GestorPaciente();
            gestorHistoriaClinica = new GestorHistoriaClinica();
            gestorCita = new GestorCita();
            listaCitasPaciente = new ArrayList<>();
        } catch (Exception ex) {
            Logger.getLogger(UIHistoriaClinica.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void consultarPacientePorId() throws Exception {
        Paciente pacienteAnt = new Paciente();
        pacienteAnt.setIdentificacion(getCita().getPaciente().getIdentificacion());
        getListaCitasPaciente().clear();
        Paciente paciente = getGestorPaciente().consultarPacientePorId(getCita().getPaciente().getIdentificacion());
        if (paciente != null) {
            getCita().setPaciente(paciente);
            getCita().setEntidad(paciente.getEntidad());
            consultarCitasPaciente();
        } else {
            getCita().setPaciente(pacienteAnt);
            getCita().setEntidad(new Entidad());
            getUtil().mostrarMensaje("Paciente no encontrado.");
        }

    }

    public List<String> listarPacientesPatron(String query) throws Exception {
        ArrayList<Paciente> listaPacientesLocal;
        listaPacientesLocal = getGestorPaciente().listarPacientesPatron(query);
        List<String> listaPac = new ArrayList<>();
        for (Paciente p : listaPacientesLocal) {
            listaPac.add(p.getNombreCompleto());
        }
        return listaPac;

    }

    public void consultarPacientePorNombreCompleto() throws Exception {

        Paciente pacienteAnt = new Paciente();
        pacienteAnt.setNombreCompleto(getCita().getPaciente().getNombreCompleto());
        getListaCitasPaciente().clear();
        Paciente paciente = getGestorPaciente().consultarPacientePorNombreCompleto(getCita().getPaciente().getNombreCompleto());
        if (paciente != null) {
            getCita().setPaciente(paciente);
            getCita().setEntidad(paciente.getEntidad());
            consultarCitasPaciente();
        } else {
            getCita().setPaciente(pacienteAnt);
            getCita().setEntidad(new Entidad());
            getUtil().mostrarMensaje("Paciente no encontrado.");
        }

    }

    public void consultarCitasPaciente() {
        try {
            setListaCitasPaciente(getGestorHistoriaClinica().consultarCitasPaciente(getCita().getPaciente().getIdentificacion()));
        } catch (Exception ex) {
            Logger.getLogger(UIProfesional.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void consultarCodigoAtencionCita(Cita cita) {
        String codigo;
        try {
            codigo = getGestorCita().consultarCodigoAtencionCita(cita).toString();
            if (cita.getListaProcedimientos().get(0).getTipo() == 1) {
                //codigoAtencion = "window.open('.././exportar?nomReporte=terapia&amp;parametros=codigoTerapia&amp;valores=" + codigo + "&amp;tipos=I');";
                setCodigoAtencion("window.open('.././exportar?nomReporte=terapia&parametros=codigoTerapia&valores=" + codigo + "&tipos=I');");
            } else if (cita.getListaProcedimientos().get(0).getTipo() == 2) {
                //codigoAtencion = "window.open('.././exportar?nomReporte=valoracion&amp;parametros=codigoValoracion&amp;valores=" + codigo + "&amp;tipos=I');";
                setCodigoAtencion("window.open('.././exportar?nomReporte=valoracion&parametros=codigoValoracion&valores=" + codigo + "&tipos=I');");
                //"window.open('.././exportar?nomReporte=recetario&parametros=codigoValoracion&valores=" + codigo + "&tipos=I');";
            } else if (cita.getListaProcedimientos().get(0).getTipo() == 4) {
                //codigoAtencion = "window.open('.././exportar?nomReporte=valoracion&amp;parametros=codigoValoracion&amp;valores=" + codigo + "&amp;tipos=I');";
                setCodigoAtencion("window.open('.././exportar?nomReporte=estudio_audiologico&parametros=codigo_estudio&valores=" + codigo + "&tipos=I');");                
            }
            //#{itemCita.listaProcedimientos.get(0).tipo eq 1 ? 
            //"window.open('.././exportar?nomReporte=terapia&amp;parametros=codigoTerapia&amp;valores=".(uiprofesional.codigoAtencion)."&amp;tipos=I');"
            //: (itemCita.listaProcedimientos.get(0).tipo eq 2 ? 
            //'window.open(\'.././exportar?nomReporte=valoracion&amp;parametros=codigoValoracion&amp;valores='+(uiprofesional.codigoAtencion)+'&amp;tipos=I\');'
        } catch (Exception ex) {
            Logger.getLogger(UIProfesional.class.getName()).log(Level.SEVERE, null, ex);
        }
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
     * @return the gestorPaciente
     */
    public GestorPaciente getGestorPaciente() {
        return gestorPaciente;
    }

    /**
     * @param gestorPaciente the gestorPaciente to set
     */
    public void setGestorPaciente(GestorPaciente gestorPaciente) {
        this.gestorPaciente = gestorPaciente;
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
     * @return the gestorHistoriaClinica
     */
    public GestorHistoriaClinica getGestorHistoriaClinica() {
        return gestorHistoriaClinica;
    }

    /**
     * @param gestorHistoriaClinica the gestorHistoriaClinica to set
     */
    public void setGestorHistoriaClinica(GestorHistoriaClinica gestorHistoriaClinica) {
        this.gestorHistoriaClinica = gestorHistoriaClinica;
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
     * @return the util
     */
    public Utilidades getUtil() {
        return util;
    }

    /**
     * @param util the util to set
     */
    public void setUtil(Utilidades util) {
        this.util = util;
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

}
