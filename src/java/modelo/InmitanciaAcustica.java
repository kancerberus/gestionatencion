/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Andres
 */
public class InmitanciaAcustica {    
    
    private List<Timpanograma> tablaTimpanograma;
    private List<CampoLibre> valoresInmitanciaAcustica;
    private String impedanciometro;
    
    public InmitanciaAcustica() {
        inicializarTimpanogramas();
        inicializarValores();
        
    }
    
    private void inicializarTimpanogramas() {
        tablaTimpanograma = new ArrayList<>();
        Timpanograma tg = new Timpanograma("A", "Normal");
        getTablaTimpanograma().add(tg);
        
        tg = new Timpanograma("B", "Plano");
        getTablaTimpanograma().add(tg);
        tg = new Timpanograma("B1", "Semilunar");
        getTablaTimpanograma().add(tg);
        tg = new Timpanograma("C", "Disfunción tubárica");
        getTablaTimpanograma().add(tg);
        tg = new Timpanograma("C1", "Disfunción tubárica con complianza disminuida");
        getTablaTimpanograma().add(tg);
        tg = new Timpanograma("Ad", "Aumento en la movilidad");
        getTablaTimpanograma().add(tg);
        tg = new Timpanograma("As", "Rigidez del sistema");
        getTablaTimpanograma().add(tg);        
    }
    
    private void inicializarValores() {
        valoresInmitanciaAcustica = new ArrayList<>();
        CampoLibre ia = new CampoLibre("Volumen C.A.E. (C.c.)", "", "", "");
        getValoresInmitanciaAcustica().add(ia);
        
        ia = new CampoLibre("Complianza", "", "", "");
        getValoresInmitanciaAcustica().add(ia);
        ia = new CampoLibre("Presion Maxima (mmH2O)", "", "", "");
        getValoresInmitanciaAcustica().add(ia);
        ia = new CampoLibre("Reflejos Estapediales", "", "", "");
        getValoresInmitanciaAcustica().add(ia);
        ia = new CampoLibre("Ipsi Laterales", "", "", "");
        getValoresInmitanciaAcustica().add(ia);
        ia = new CampoLibre("Contralaterales", "", "", "");
        getValoresInmitanciaAcustica().add(ia);
        
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
}
