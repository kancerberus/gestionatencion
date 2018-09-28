/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vista;

import controlador.GestorPerfil;
import controlador.GestorProfesional;
import controlador.GestorUsuario;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.model.SelectItem;
import modelo.Perfil;
import modelo.Profesional;
import modelo.Usuario;
import util.Utilidades;

/**
 *
 * @author Andres
 */
public class UIUsuario implements Serializable {

    private Usuario usuario;
    private Boolean existe;
    private GestorUsuario gestorUsuario;
    private List<Usuario> listaUsuarios;
    public Utilidades util = new Utilidades();
    private List<SelectItem> comboListaProfesionales;
    private List<SelectItem> comboListaPerfiles;
    private GestorProfesional gestorProfesional;
    private GestorPerfil gestorPerfil;

    public UIUsuario() throws Exception {
        gestorUsuario = new GestorUsuario();
        listaUsuarios = new ArrayList<>();
        gestorProfesional = new GestorProfesional();
        gestorPerfil = new GestorPerfil();
        usuario = new Usuario();
        comboListaProfesionales = new ArrayList<>();
        comboListaPerfiles = new ArrayList<>();
        cargarComboProfesionales();
        cargarComboPerfiles();
    }

    public void consultarUsuario() {
        try {
            setListaUsuarios(gestorUsuario.listarUsuarios());
        } catch (Exception ex) {
            Logger.getLogger(UIEntidad.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void guardarUsuario() {
        try {
            Integer resultado = gestorUsuario.guardarUsuario(getUsuario(), getExiste());
            if (resultado > 0) {
                util.mostrarMensaje("El usuario se guardo exitosamente.");
                usuario = new Usuario();
            } else {
                util.mostrarMensaje("Se presento un error al guardar.");
            }
        } catch (Exception ex) {
            Logger.getLogger(UIUsuario.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void consultarUsuarioPorLogin() {
        Usuario p;
        String login = usuario.getUsuario();
        try {
            p = gestorUsuario.consultarUsuarioPorLogin(usuario.getUsuario());
            if (p != null) {
                usuario = p;
                setExiste((Boolean) true);
            } else {
                usuario = new Usuario();
                usuario.setUsuario(login);
                setExiste((Boolean) false);
            }
        } catch (Exception ex) {
            Logger.getLogger(UIUsuario.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void cargarComboProfesionales() {
        List<Profesional> listaProfesionales;
        try {
            listaProfesionales = gestorProfesional.listarProfesionales();
            for (Profesional p : listaProfesionales) {
                comboListaProfesionales.add(new SelectItem(p.getCedula(), p.getNombre()));
            }
        } catch (Exception ex) {
            Logger.getLogger(UIUsuario.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void cargarComboPerfiles() {
        List<Perfil> listaPerfiles;
        try {
            listaPerfiles = getGestorPerfil().listarPerfiles();
            for (Perfil p : listaPerfiles) {
                getComboListaPerfiles().add(new SelectItem(p.getCodigo(), p.getNombre()));
            }
        } catch (Exception ex) {
            Logger.getLogger(UIUsuario.class.getName()).log(Level.SEVERE, null, ex);
        }
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
     * @return the gestorUsuario
     */
    public GestorUsuario getGestorUsuario() {
        return gestorUsuario;
    }

    /**
     * @param gestorUsuario the gestorUsuario to set
     */
    public void setGestorUsuario(GestorUsuario gestorUsuario) {
        this.gestorUsuario = gestorUsuario;
    }

    /**
     * @return the listaUsuarios
     */
    public List<Usuario> getListaUsuarios() {
        return listaUsuarios;
    }

    /**
     * @param listaUsuarios the listaUsuarios to set
     */
    public void setListaUsuarios(List<Usuario> listaUsuarios) {
        this.listaUsuarios = listaUsuarios;
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
     * @return the comboListaProfesionales
     */
    public List<SelectItem> getComboListaProfesionales() {
        return comboListaProfesionales;
    }

    /**
     * @param comboListaProfesionales the comboListaProfesionales to set
     */
    public void setComboListaProfesionales(List<SelectItem> comboListaProfesionales) {
        this.comboListaProfesionales = comboListaProfesionales;
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
     * @return the gestorPerfil
     */
    public GestorPerfil getGestorPerfil() {
        return gestorPerfil;
    }

    /**
     * @param gestorPerfil the gestorPerfil to set
     */
    public void setGestorPerfil(GestorPerfil gestorPerfil) {
        this.gestorPerfil = gestorPerfil;
    }

    /**
     * @return the comboListaPerfiles
     */
    public List<SelectItem> getComboListaPerfiles() {
        return comboListaPerfiles;
    }

    /**
     * @param comboListaPerfiles the comboListaPerfiles to set
     */
    public void setComboListaPerfiles(List<SelectItem> comboListaPerfiles) {
        this.comboListaPerfiles = comboListaPerfiles;
    }

}
