/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vista;

import controlador.GestorCentroCostos;
import controlador.GestorEntidad;
import controlador.GestorMunicipio;
import controlador.GestorPaciente;
import controlador.GestorUtilidades;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.model.SelectItem;
import modelo.CentroCostos;
import modelo.ControlExport;
import modelo.Entidad;
import modelo.Municipio;
import modelo.Paciente;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import util.Utilidades;

/**
 *
 * @author Andres
 */
public class UIPaciente implements Serializable {

    private Paciente paciente;
    private Boolean existe;
    private String usuario;
    private List<SelectItem> listaCentroCostos = new ArrayList<>();
    private GestorCentroCostos gestorCentroCostos;
    private GestorEntidad gestorEntidad;
    private GestorMunicipio gestorMunicipio;
    private GestorPaciente gestorPaciente;
    private GestorUtilidades gestorUtilidades;
    public Utilidades util = new Utilidades();

    private List<SelectItem> listaSexo;
    private List<SelectItem> listaTipoAfiliacion;
    private List<SelectItem> listaEstadoCivil;
    private List<SelectItem> listaTipoIdentificacion;
    private List<SelectItem> listaRegimen;
    private List<SelectItem> listaCondicion;
    private List<Paciente> listaPacientes;

    private List<ControlExport> listaControlExport;

    private StreamedContent archivo;
    private StreamedContent archivo2;
    

    public UIPaciente() throws Exception {
        paciente = new Paciente();
        paciente.setEntidad(new Entidad());
        paciente.setCiudadNacimiento(new Municipio());
        paciente.setCiudadResidencia(new Municipio());
        gestorCentroCostos = new GestorCentroCostos();
        gestorEntidad = new GestorEntidad();
        gestorMunicipio = new GestorMunicipio();
        gestorPaciente = new GestorPaciente();
        gestorUtilidades = new GestorUtilidades();
        existe = Boolean.FALSE;

        listaControlExport = new ArrayList<>();
        listaPacientes = new ArrayList<>();

        cargarListaSexo();
        cargarListaEstadoCivil();
        cargarListaTipoAfiliacion();
        cargarListaTipoIdentificacion();
        cargarListaRegimen();
        cargarListaCondicion();
    }

    public void consultarPacientePorId() throws Exception {
        Paciente p;
        String id;
        p = gestorPaciente.consultarPacientePorId(paciente.getIdentificacion());
        if (p != null) {
            paciente = p;
            existe = Boolean.TRUE;
        } else {
            id = paciente.getIdentificacion();
            paciente = new Paciente();
            paciente.setIdentificacion(id);
            existe = Boolean.FALSE;
        }

    }

    public void listarCentroCostos() throws Exception {
        List<CentroCostos> listaCC = gestorCentroCostos.listarCentroCostos();
        for (CentroCostos cc : listaCC) {
            listaCentroCostos.add(new SelectItem(cc.getCodigo(), cc.getNombre()));
        }
    }

    public List<String> listarEntidadesPatron(String query) throws Exception {
        ArrayList<Entidad> listaEntidadesLocal;
        listaEntidadesLocal = getGestorEntidad().listarEntidadesPatron(query);
        List<String> listaEnt = new ArrayList<>();
        for (Entidad e : listaEntidadesLocal) {
            listaEnt.add(e.getNombre());
        }
        return listaEnt;
    }

    public List<String> listarMunicipiosPatron(String query) throws Exception {
        ArrayList<Municipio> listaMunicipiosLocal;
        listaMunicipiosLocal = getGestorMunicipio().listarMunicipiosPatron(query);
        List<String> listaMun = new ArrayList<>();
        for (Municipio m : listaMunicipiosLocal) {
            listaMun.add(m.getNombre());
        }
        return listaMun;
    }

    public void guardarPaciente() {
        Boolean invalido = false;
        /*
         paciente identificacion ""
         paciente nombre ""
         centro costos codigo null
         fecha nacimiento null
         entidad nombre null
         direccion1 ""
         telefono1 ""
         ciudad nombre null
         ciudad nombre null
         */
        try {
            if (paciente.getIdentificacion().equalsIgnoreCase("")) {
                invalido = true;
            }
            if (paciente.getNombre().equalsIgnoreCase("")) {
                invalido = true;
            }
            if (paciente.getPrimerApellido() == null) {
                invalido = true;
            }
            if (paciente.getFechaNacimiento() == null) {
                invalido = true;
            }
            if (paciente.getEntidad().getNombre() == null) {
                invalido = true;
            }
            if (paciente.getDireccion1().equalsIgnoreCase("")) {
                invalido = true;
            }
            if (paciente.getTelefono1().equalsIgnoreCase("")) {
                invalido = true;
            }
            if (paciente.getTelefono2().equalsIgnoreCase("")) {
                invalido = true;
            }
            if (paciente.getCiudadNacimiento().getNombre() == null) {
                invalido = true;
            }
            if (paciente.getCiudadResidencia().getNombre() == null) {
                invalido = true;
            }

            if (!invalido) {
                Entidad ent = gestorEntidad.consultarEntidadPorNombre(paciente.getEntidad().getNombre());
                Municipio cr = gestorMunicipio.consultarMunicipioPorNombre(paciente.getCiudadResidencia().getNombre());
                Municipio cn = gestorMunicipio.consultarMunicipioPorNombre(paciente.getCiudadNacimiento().getNombre());
                paciente.getEntidad().setCodigo(ent.getCodigo());
                paciente.getCiudadNacimiento().setCodigo(cn.getCodigo());
                paciente.getCiudadResidencia().setCodigo(cr.getCodigo());

                Integer resultado = gestorPaciente.guardarPaciente(paciente, existe);
                if (resultado > 0) {
                    util.mostrarMensaje("El paciente se guardo exitosamente.");
                    paciente = new Paciente();
                } else {
                    util.mostrarMensaje("Se presento un error al guardar.");
                }
            } else {
                util.mostrarMensaje("Hay campos requeridos sin diligenciar.");
            }

        } catch (SQLException ex) {
            util.mostrarMensaje(ex.getMessage());
        } catch (Exception ex) {
            util.mostrarMensaje(ex.getMessage());
        }

    }

    public void limpiarPaciente() {
        paciente = new Paciente();
        existe = Boolean.FALSE;
    }

    private void cargarListaSexo() {
        try {
            setListaSexo(gestorUtilidades.listarCombo("SEXO", "VALUE"));
        } catch (Exception ex) {
            Logger.getLogger(UICita.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void cargarListaEstadoCivil() {
        try {
            setListaEstadoCivil(gestorUtilidades.listarCombo("ESTADO_CIVIL", "VALUE"));
        } catch (Exception ex) {
            Logger.getLogger(UICita.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void cargarListaRegimen() {
        try {
            setListaRegimen(gestorUtilidades.listarCombo("REGIMEN", "VALUE"));
        } catch (Exception ex) {
            Logger.getLogger(UICita.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void cargarListaTipoAfiliacion() {
        try {
            setListaTipoAfiliacion(gestorUtilidades.listarCombo("TIPO_AFILIACION", "VALUE"));
        } catch (Exception ex) {
            Logger.getLogger(UICita.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void cargarListaTipoIdentificacion() {
        try {
            setListaTipoIdentificacion(gestorUtilidades.listarCombo("TIPO_IDENTIFICACION", "VALUE"));
        } catch (Exception ex) {
            Logger.getLogger(UICita.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void cargarListaCondicion() {
        try {
            setListaCondicion(gestorUtilidades.listarCombo("CONDICION_TERAPIA", "VALUE"));
        } catch (Exception ex) {
            Logger.getLogger(UICita.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void listarControlExport() {
        try {
            listaControlExport = gestorPaciente.listarControlExport();
        } catch (Exception ex) {
            util.mostrarMensaje(ex.getMessage());
        }
    }

    public void generarExport() {
        try {
            gestorPaciente.generarExport();
            listarControlExport();
        } catch (Exception ex) {
            util.mostrarMensaje(ex.getMessage());
        }
    }

    public void generarArchivo(ControlExport exportSelecionado) {
        try {
            ArrayList<String> listaPacientes;// = new ArrayList();
            ArrayList<String> listaPacientes2;// = new ArrayList();
            String cadena = "";
            String cadena2 = "";
            listaPacientes = gestorPaciente.consultarControlExport(exportSelecionado.getCodExport());
            for (String c : listaPacientes) {
                if (cadena.equalsIgnoreCase("")) {
                    cadena = c;
                } else {
                    cadena = cadena + "\r\n" + c;
                }
            }

            listaPacientes2 = gestorPaciente.consultarControlExport2(exportSelecionado.getCodExport());
            for (String c : listaPacientes2) {
                if (cadena2.equalsIgnoreCase("")) {
                    cadena2 = c;
                } else {
                    cadena2 = cadena2 + "\r\n" + c;
                }
            }
            
            InputStream stream = new ByteArrayInputStream(cadena.getBytes());
            InputStream stream2 = new ByteArrayInputStream(cadena2.getBytes());
            
            archivo = new DefaultStreamedContent(stream, "text/plain", "pacientes.txt");
            archivo2 = new DefaultStreamedContent(stream2, "text/plain", "pacientes2.txt");
            
        } catch (Exception ex) {
            Logger.getLogger(UIPaciente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void listarPacientes() {
        try {
            listaPacientes.clear();
            listaPacientes = gestorPaciente.listarPacientes();
        } catch (Exception ex) {
            Logger.getLogger(UIPaciente.class.getName()).log(Level.SEVERE, null, ex);
        }
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
     * @return the usuario
     */
    public String getUsuario() {
        return usuario;
    }

    /**
     * @param usuario the usuario to set
     */
    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    /**
     * @return the listaCentroCostos
     */
    public List<SelectItem> getListaCentroCostos() {
        return listaCentroCostos;
    }

    /**
     * @param listaCentroCostos the listaCentroCostos to set
     */
    public void setListaCentroCostos(List<SelectItem> listaCentroCostos) {
        this.listaCentroCostos = listaCentroCostos;
    }

    /**
     * @return the gestorCentroCostos
     */
    public GestorCentroCostos getGestorCentroCostos() {
        return gestorCentroCostos;
    }

    /**
     * @param gestorCentroCostos the gestorCentroCostos to set
     */
    public void setGestorCentroCostos(GestorCentroCostos gestorCentroCostos) {
        this.gestorCentroCostos = gestorCentroCostos;
    }

    /**
     * @return the gestorEntidad
     */
    public GestorEntidad getGestorEntidad() {
        return gestorEntidad;
    }

    /**
     * @param gestorEntidad the gestorEntidad to set
     */
    public void setGestorEntidad(GestorEntidad gestorEntidad) {
        this.gestorEntidad = gestorEntidad;
    }

    /**
     * @return the gestorMunicipio
     */
    public GestorMunicipio getGestorMunicipio() {
        return gestorMunicipio;
    }

    /**
     * @param gestorMunicipio the gestorMunicipio to set
     */
    public void setGestorMunicipio(GestorMunicipio gestorMunicipio) {
        this.gestorMunicipio = gestorMunicipio;
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
     * @return the listaSexo
     */
    public List<SelectItem> getListaSexo() {
        return listaSexo;
    }

    /**
     * @param listaSexo the listaSexo to set
     */
    public void setListaSexo(List<SelectItem> listaSexo) {
        this.listaSexo = listaSexo;
    }

    /**
     * @return the listaTipoAfiliacion
     */
    public List<SelectItem> getListaTipoAfiliacion() {
        return listaTipoAfiliacion;
    }

    /**
     * @param listaTipoAfiliacion the listaTipoAfiliacion to set
     */
    public void setListaTipoAfiliacion(List<SelectItem> listaTipoAfiliacion) {
        this.listaTipoAfiliacion = listaTipoAfiliacion;
    }

    /**
     * @return the listaEstadoCivil
     */
    public List<SelectItem> getListaEstadoCivil() {
        return listaEstadoCivil;
    }

    /**
     * @param listaEstadoCivil the listaEstadoCivil to set
     */
    public void setListaEstadoCivil(List<SelectItem> listaEstadoCivil) {
        this.listaEstadoCivil = listaEstadoCivil;
    }

    /**
     * @return the listaTipoIdentificacion
     */
    public List<SelectItem> getListaTipoIdentificacion() {
        return listaTipoIdentificacion;
    }

    /**
     * @param listaTipoIdentificacion the listaTipoIdentificacion to set
     */
    public void setListaTipoIdentificacion(List<SelectItem> listaTipoIdentificacion) {
        this.listaTipoIdentificacion = listaTipoIdentificacion;
    }

    /**
     * @return the listaRegimen
     */
    public List<SelectItem> getListaRegimen() {
        return listaRegimen;
    }

    /**
     * @param listaRegimen the listaRegimen to set
     */
    public void setListaRegimen(List<SelectItem> listaRegimen) {
        this.listaRegimen = listaRegimen;
    }

    /**
     * @return the listaControlExport
     */
    public List<ControlExport> getListaControlExport() {
        return listaControlExport;
    }

    /**
     * @param listaControlExport the listaControlExport to set
     */
    public void setListaControlExport(List<ControlExport> listaControlExport) {
        this.listaControlExport = listaControlExport;
    }

    /**
     * @return the archivo
     */
    public StreamedContent getArchivo() {
        return archivo;
    }

    /**
     * @param archivo the archivo to set
     */
    public void setArchivo(StreamedContent archivo) {
        this.archivo = archivo;
    }

    /**
     * @return the listaPacientes
     */
    public List<Paciente> getListaPacientes() {
        return listaPacientes;
    }

    /**
     * @param listaPacientes the listaPacientes to set
     */
    public void setListaPacientes(List<Paciente> listaPacientes) {
        this.listaPacientes = listaPacientes;
    }

    public StreamedContent getArchivo2() {
        return archivo2;
    }

    public void setArchivo2(StreamedContent archivo2) {
        this.archivo2 = archivo2;
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

    
}

