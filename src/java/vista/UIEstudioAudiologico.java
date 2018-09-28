/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vista;

import javax.annotation.PostConstruct;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import modelo.Acufenometria;
import modelo.CampoLibre;
//import javax.faces.component.UIColumn;
//import javax.faces.bean.ManagedProperty;
//import javax.faces.bean.ManagedBean;
import modelo.EstudioAudiologico;
import modelo.InmitanciaAcustica;
import modelo.Punto;
import modelo.PuntoL;
import org.primefaces.event.CellEditEvent;
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.CategoryAxis;
import org.primefaces.model.chart.LineChartModel;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.LineChartSeries;

/**
 *
 * @author Andres
 */
public class UIEstudioAudiologico implements Serializable {

    @PostConstruct
    public void init() {
        createLineModels();
    }

    private EstudioAudiologico estudioAudiologico;

    private LineChartModel audiometriaTonosPurosOD;
    private LineChartModel audiometriaTonosPurosOI;
    private LineChartModel audiometriaVocal;

    private List<Punto> puntosAudiometriaTonosPurosOD;
    private List<Punto> puntosAudiometriaTonosPurosOI;
    private List<PuntoL> puntosAudiometriaVocalS1;
    private List<PuntoL> puntosAudiometriaVocalS2;

    //tabla OD OI
    private List<CampoLibre> tablaCampoLibre;

    //acufenometria
    private Acufenometria acufenometria;
    //inmitancia acustica
    private InmitanciaAcustica inmitanciaAcustica;

    public UIEstudioAudiologico() {
        estudioAudiologico = new EstudioAudiologico();

        puntosAudiometriaTonosPurosOD = new ArrayList<>();
        puntosAudiometriaTonosPurosOI = new ArrayList<>();
        puntosAudiometriaVocalS1 = new ArrayList<>();
        puntosAudiometriaVocalS2 = new ArrayList<>();
        inicializarCampoLibre();
        inmitanciaAcustica = new InmitanciaAcustica();
        acufenometria = new Acufenometria();
    }

    private void createLineModels() {

        LineChartModel modelTonosPurosOD = new LineChartModel();
        ChartSeries serieTonosPurosOD = new ChartSeries();

        puntosAudiometriaTonosPurosOD.add(new Punto("250", 0));
        puntosAudiometriaTonosPurosOD.add(new Punto("500", 0));
        puntosAudiometriaTonosPurosOD.add(new Punto("1K", 0));
        puntosAudiometriaTonosPurosOD.add(new Punto("2K", 0));
        puntosAudiometriaTonosPurosOD.add(new Punto("4K", 0));
        puntosAudiometriaTonosPurosOD.add(new Punto("8K", 0));

        for (Punto p : puntosAudiometriaTonosPurosOD) {
            serieTonosPurosOD.set(p.getX(), p.getY());
        }
        modelTonosPurosOD.addSeries(serieTonosPurosOD);

        audiometriaTonosPurosOD = modelTonosPurosOD;
        audiometriaTonosPurosOD.setTitle("Oido Derecho");
        audiometriaTonosPurosOD.setLegendPosition("e");
        //getAudiometriaTonosPurosOD().setShowPointLabels(true);
        audiometriaTonosPurosOD.getAxes().put(AxisType.X, new CategoryAxis("Hz"));
        Axis yAxis = audiometriaTonosPurosOD.getAxis(AxisType.Y);
        yAxis.setLabel("dB");
        yAxis.setMin(0);
        yAxis.setMax(120);

        //####
        LineChartModel modelTonosPurosOI = new LineChartModel();
        ChartSeries serieTonosPurosOI = new ChartSeries();

        puntosAudiometriaTonosPurosOI.add(new Punto("250", 0));
        puntosAudiometriaTonosPurosOI.add(new Punto("500", 0));
        puntosAudiometriaTonosPurosOI.add(new Punto("1K", 10));
        puntosAudiometriaTonosPurosOI.add(new Punto("2K", 0));
        puntosAudiometriaTonosPurosOI.add(new Punto("4K", 0));
        puntosAudiometriaTonosPurosOI.add(new Punto("8K", 0));

        for (Punto p : puntosAudiometriaTonosPurosOI) {
            serieTonosPurosOI.set(p.getX(), p.getY());
        }
        modelTonosPurosOI.addSeries(serieTonosPurosOI);

        audiometriaTonosPurosOI = modelTonosPurosOI;
        audiometriaTonosPurosOI.setTitle("Oido Izquierdo");
        audiometriaTonosPurosOI.setLegendPosition("e");
        //getAudiometriaTonosPurosOD().setShowPointLabels(true);
        audiometriaTonosPurosOI.getAxes().put(AxisType.X, new CategoryAxis("Hz"));
        yAxis = audiometriaTonosPurosOI.getAxis(AxisType.Y);
        yAxis.setLabel("dB");
        yAxis.setMin(0);
        yAxis.setMax(120);

        //##
        LineChartModel modelVocal = new LineChartModel();
        LineChartSeries serieVocal1 = new LineChartSeries();
        LineChartSeries serieVocal2 = new LineChartSeries();

        puntosAudiometriaVocalS1.add(new PuntoL(0, 0));
        puntosAudiometriaVocalS1.add(new PuntoL(10, 30));
        puntosAudiometriaVocalS1.add(new PuntoL(13, 65));
        puntosAudiometriaVocalS1.add(new PuntoL(15, 90));
        puntosAudiometriaVocalS1.add(new PuntoL(20, 100));

        for (PuntoL p : puntosAudiometriaVocalS1) {
            serieVocal1.set(p.getX(), p.getY());
        }
        modelVocal.addSeries(serieVocal1);

        puntosAudiometriaVocalS2.add(new PuntoL(15, 0));
        puntosAudiometriaVocalS2.add(new PuntoL(25, 30));
        puntosAudiometriaVocalS2.add(new PuntoL(28, 65));
        puntosAudiometriaVocalS2.add(new PuntoL(30, 90));
        puntosAudiometriaVocalS2.add(new PuntoL(35, 100));

        for (PuntoL p : puntosAudiometriaVocalS2) {
            serieVocal2.set(p.getX(), p.getY());
        }
        modelVocal.addSeries(serieVocal2);

        audiometriaVocal = modelVocal;
        audiometriaVocal.setTitle("Audiometria Vocal");
        audiometriaVocal.setLegendPosition("e");
        //getAudiometriaTonosPurosOD().setShowPointLabels(true);
        audiometriaVocal.getAxes().put(AxisType.X, new CategoryAxis("dB"));
        yAxis = audiometriaVocal.getAxis(AxisType.Y);
        yAxis.setLabel("%");
        yAxis.setMin(0);
        yAxis.setMax(100);

    }

    public void onCellEditTonosPurosOD(CellEditEvent event) {
        Punto p;
        Object oldValue = event.getOldValue();
        Object newValue = event.getNewValue();
        if (event.getColumn().getHeaderText().equalsIgnoreCase("dB")) {
            p = new Punto((puntosAudiometriaTonosPurosOD.get(event.getRowIndex())).getX(), Integer.parseInt(newValue.toString()));
            puntosAudiometriaTonosPurosOD.set(event.getRowIndex(), p);
        } else if (event.getColumn().getHeaderText().equalsIgnoreCase("Hz")) {
            p = new Punto(newValue.toString(), (puntosAudiometriaTonosPurosOD.get(event.getRowIndex())).getY());
            puntosAudiometriaTonosPurosOD.set(event.getRowIndex(), p);
        }

        LineChartModel modelTonosPurosOD = new LineChartModel();
        ChartSeries serieTonosPurosOD = new ChartSeries();

        for (Punto punto : puntosAudiometriaTonosPurosOD) {
            serieTonosPurosOD.set(punto.getX(), punto.getY());
        }
        modelTonosPurosOD.addSeries(serieTonosPurosOD);

        audiometriaTonosPurosOD = modelTonosPurosOD;

        audiometriaTonosPurosOD.setTitle("Oido Derecho");
        audiometriaTonosPurosOD.setLegendPosition("e");
        //getAudiometriaTonosPurosOD().setShowPointLabels(true);
        audiometriaTonosPurosOD.getAxes().put(AxisType.X, new CategoryAxis("Hz"));
        Axis yAxis = audiometriaTonosPurosOD.getAxis(AxisType.Y);
        yAxis.setLabel("dB");
        yAxis.setMin(0);
        yAxis.setMax(120);

    }

    public void onCellEditTonosPurosOI(CellEditEvent event) {
        Punto p;
        Object oldValue = event.getOldValue();
        Object newValue = event.getNewValue();
        if (event.getColumn().getHeaderText().equalsIgnoreCase("dB")) {
            p = new Punto((puntosAudiometriaTonosPurosOI.get(event.getRowIndex())).getX(), Integer.parseInt(newValue.toString()));
            puntosAudiometriaTonosPurosOI.set(event.getRowIndex(), p);
        } else if (event.getColumn().getHeaderText().equalsIgnoreCase("Hz")) {
            p = new Punto(newValue.toString(), (puntosAudiometriaTonosPurosOI.get(event.getRowIndex())).getY());
            puntosAudiometriaTonosPurosOI.set(event.getRowIndex(), p);
        }

        LineChartModel modelTonosPurosOI = new LineChartModel();
        ChartSeries serieTonosPurosOI = new ChartSeries();

        for (Punto punto : puntosAudiometriaTonosPurosOI) {
            serieTonosPurosOI.set(punto.getX(), punto.getY());
        }
        modelTonosPurosOI.addSeries(serieTonosPurosOI);

        audiometriaTonosPurosOI = modelTonosPurosOI;

        audiometriaTonosPurosOI.setTitle("Oido Izquierdo");
        audiometriaTonosPurosOI.setLegendPosition("e");
        //getAudiometriaTonosPurosOD().setShowPointLabels(true);
        audiometriaTonosPurosOI.getAxes().put(AxisType.X, new CategoryAxis("Hz"));
        Axis yAxis = audiometriaTonosPurosOI.getAxis(AxisType.Y);
        yAxis.setLabel("dB");
        yAxis.setMin(0);
        yAxis.setMax(120);
    }

    public void onCellEditAudimetriaVocal1(CellEditEvent event) {
        PuntoL p;
        //Object oldValue = event.getOldValue();
        Object newValue = event.getNewValue();
        if (event.getColumn().getHeaderText().equalsIgnoreCase("%")) {
            p = new PuntoL((puntosAudiometriaVocalS1.get(event.getRowIndex())).getX(), Integer.parseInt(newValue.toString()));
            puntosAudiometriaVocalS1.set(event.getRowIndex(), p);
        } else if (event.getColumn().getHeaderText().equalsIgnoreCase("dB")) {
            p = new PuntoL(Integer.parseInt(newValue.toString()), (puntosAudiometriaVocalS1.get(event.getRowIndex())).getY());
            puntosAudiometriaVocalS1.set(event.getRowIndex(), p);
        }

        LineChartModel modelVocal = new LineChartModel();
        LineChartSeries serieVocal1 = new LineChartSeries();
        LineChartSeries serieVocal2 = new LineChartSeries();

        for (PuntoL punto : puntosAudiometriaVocalS1) {
            serieVocal1.set(punto.getX(), punto.getY());
        }
        modelVocal.addSeries(serieVocal1);

        for (PuntoL punto : puntosAudiometriaVocalS2) {
            serieVocal2.set(punto.getX(), punto.getY());
        }
        modelVocal.addSeries(serieVocal2);

        audiometriaVocal = modelVocal;
        audiometriaVocal.setTitle("Audiometria Vocal");
        audiometriaVocal.setLegendPosition("e");
        //getAudiometriaTonosPurosOD().setShowPointLabels(true);
        audiometriaVocal.getAxes().put(AxisType.X, new CategoryAxis("dB"));
        Axis yAxis = audiometriaVocal.getAxis(AxisType.Y);
        yAxis.setLabel("%");
        yAxis.setMin(0);
        yAxis.setMax(100);

    }

    public void onCellEditAudimetriaVocal2(CellEditEvent event) {
        PuntoL p;
        //Object oldValue = event.getOldValue();
        Object newValue = event.getNewValue();
        if (event.getColumn().getHeaderText().equalsIgnoreCase("%")) {
            p = new PuntoL((puntosAudiometriaVocalS2.get(event.getRowIndex())).getX(), Integer.parseInt(newValue.toString()));
            puntosAudiometriaVocalS2.set(event.getRowIndex(), p);
        } else if (event.getColumn().getHeaderText().equalsIgnoreCase("dB")) {
            p = new PuntoL(Integer.parseInt(newValue.toString()), (puntosAudiometriaVocalS2.get(event.getRowIndex())).getY());
            puntosAudiometriaVocalS2.set(event.getRowIndex(), p);
        }

        LineChartModel modelVocal = new LineChartModel();
        LineChartSeries serieVocal1 = new LineChartSeries();
        LineChartSeries serieVocal2 = new LineChartSeries();

        for (PuntoL punto : puntosAudiometriaVocalS1) {
            serieVocal1.set(punto.getX(), punto.getY());
        }
        modelVocal.addSeries(serieVocal1);

        for (PuntoL punto : puntosAudiometriaVocalS2) {
            serieVocal2.set(punto.getX(), punto.getY());
        }
        modelVocal.addSeries(serieVocal2);

        audiometriaVocal = modelVocal;
        audiometriaVocal.setTitle("Audiometria Vocal");
        audiometriaVocal.setLegendPosition("e");
        //getAudiometriaTonosPurosOD().setShowPointLabels(true);
        audiometriaVocal.getAxes().put(AxisType.X, new CategoryAxis("dB"));
        Axis yAxis = audiometriaVocal.getAxis(AxisType.Y);
        yAxis.setLabel("%");
        yAxis.setMin(0);
        yAxis.setMax(100);
    }

    private void inicializarCampoLibre() {
        tablaCampoLibre = new ArrayList<>();
        CampoLibre cl = new CampoLibre("P.T.A. Promedio tonos audibles", "", "", "");
        tablaCampoLibre.add(cl);

        cl = new CampoLibre("S.T.A. Umbral captación voz", "", "", "");
        tablaCampoLibre.add(cl);
        cl = new CampoLibre("S.R.T. Umbral reconoc. palabra", "", "", "");
        tablaCampoLibre.add(cl);
        cl = new CampoLibre("S.D. Porcentaje discriminación", "", "", "");
        tablaCampoLibre.add(cl);
        cl = new CampoLibre("M.C.L. Nivel de comodidad", "", "", "");
        tablaCampoLibre.add(cl);
        cl = new CampoLibre("U.C.L. Nivel de incomodidad", "", "", "");
        tablaCampoLibre.add(cl);

    }

    /**
     * @return the estudioAudiologico
     */
    public EstudioAudiologico getEstudioAudiologico() {
        return estudioAudiologico;
    }

    /**
     * @param estudioAudiologico the estudioAudiologico to set
     */
    public void setEstudioAudiologico(EstudioAudiologico estudioAudiologico) {
        this.estudioAudiologico = estudioAudiologico;
    }

    /**
     * @return the audiometriaTonosPurosOD
     */
    public LineChartModel getAudiometriaTonosPurosOD() {
        return audiometriaTonosPurosOD;
    }

    /**
     * @param audiometriaTonosPurosOD the audiometriaTonosPurosOD to set
     */
    public void setAudiometriaTonosPurosOD(LineChartModel audiometriaTonosPurosOD) {
        this.audiometriaTonosPurosOD = audiometriaTonosPurosOD;
    }

    /**
     * @return the audiometriaTonosPurosOI
     */
    public LineChartModel getAudiometriaTonosPurosOI() {
        return audiometriaTonosPurosOI;
    }

    /**
     * @param audiometriaTonosPurosOI the audiometriaTonosPurosOI to set
     */
    public void setAudiometriaTonosPurosOI(LineChartModel audiometriaTonosPurosOI) {
        this.audiometriaTonosPurosOI = audiometriaTonosPurosOI;
    }

    /**
     * @return the puntosAudiometriaTonosPurosOD
     */
    public List<Punto> getPuntosAudiometriaTonosPurosOD() {
        return puntosAudiometriaTonosPurosOD;
    }

    /**
     * @param puntosAudiometriaTonosPurosOD the puntosAudiometriaTonosPurosOD to
     * set
     */
    public void setPuntosAudiometriaTonosPurosOD(List<Punto> puntosAudiometriaTonosPurosOD) {
        this.puntosAudiometriaTonosPurosOD = puntosAudiometriaTonosPurosOD;
    }

    /**
     * @return the puntosAudiometriaTonosPurosOI
     */
    public List<Punto> getPuntosAudiometriaTonosPurosOI() {
        return puntosAudiometriaTonosPurosOI;
    }

    /**
     * @param puntosAudiometriaTonosPurosOI the puntosAudiometriaTonosPurosOI to
     * set
     */
    public void setPuntosAudiometriaTonosPurosOI(List<Punto> puntosAudiometriaTonosPurosOI) {
        this.puntosAudiometriaTonosPurosOI = puntosAudiometriaTonosPurosOI;
    }

    /**
     * @return the audiometriaVocal
     */
    public LineChartModel getAudiometriaVocal() {
        return audiometriaVocal;
    }

    /**
     * @param audiometriaVocal the audiometriaVocal to set
     */
    public void setAudiometriaVocal(LineChartModel audiometriaVocal) {
        this.audiometriaVocal = audiometriaVocal;
    }

    /**
     * @return the puntosAudiometriaVocalS1
     */
    public List<PuntoL> getPuntosAudiometriaVocalS1() {
        return puntosAudiometriaVocalS1;
    }

    /**
     * @param puntosAudiometriaVocalS1 the puntosAudiometriaVocalS1 to set
     */
    public void setPuntosAudiometriaVocalS1(List<PuntoL> puntosAudiometriaVocalS1) {
        this.puntosAudiometriaVocalS1 = puntosAudiometriaVocalS1;
    }

    /**
     * @return the puntosAudiometriaVocalS2
     */
    public List<PuntoL> getPuntosAudiometriaVocalS2() {
        return puntosAudiometriaVocalS2;
    }

    /**
     * @param puntosAudiometriaVocalS2 the puntosAudiometriaVocalS2 to set
     */
    public void setPuntosAudiometriaVocalS2(List<PuntoL> puntosAudiometriaVocalS2) {
        this.puntosAudiometriaVocalS2 = puntosAudiometriaVocalS2;
    }

    /**
     * @return the tablaCampoLibre
     */
    public List<CampoLibre> getTablaCampoLibre() {
        return tablaCampoLibre;
    }

    /**
     * @param tablaCampoLibre the tablaCampoLibre to set
     */
    public void setTablaCampoLibre(List<CampoLibre> tablaCampoLibre) {
        this.tablaCampoLibre = tablaCampoLibre;
    }

    /**
     * @return the inmitanciaAcustica
     */
    public InmitanciaAcustica getInmitanciaAcustica() {
        return inmitanciaAcustica;
    }

    /**
     * @param inmitanciaAcustica the inmitanciaAcustica to set
     */
    public void setInmitanciaAcustica(InmitanciaAcustica inmitanciaAcustica) {
        this.inmitanciaAcustica = inmitanciaAcustica;
    }

    /**
     * @return the acufenometria
     */
    public Acufenometria getAcufenometria() {
        return acufenometria;
    }

    /**
     * @param acufenometria the acufenometria to set
     */
    public void setAcufenometria(Acufenometria acufenometria) {
        this.acufenometria = acufenometria;
    }

}
