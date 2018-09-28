/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.CategoryAxis;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.LineChartModel;

/**
 *
 * @author Andres
 */
@ManagedBean
public class Grafico implements Serializable {
    private List<Punto> serie;
    private LineChartModel lineModel2;

//    public Grafico() {
//    }
    
    @PostConstruct
    public void init() {
        createLineModels();
    }
    
    private void createLineModels() {
        /*
        lineModel1 = initLinearModel();
        lineModel1.setTitle("Linear Chart");
        lineModel1.setLegendPosition("e");
        
        Axis yAxis = lineModel1.getAxis(AxisType.Y);
        yAxis.setMin(0);
        yAxis.setMax(10);
        */
        
        setLineModel2(initCategoryModel());
        getLineModel2().setTitle("Category Chart");
        getLineModel2().setLegendPosition("e");
        getLineModel2().setShowPointLabels(true);
        getLineModel2().getAxes().put(AxisType.X, new CategoryAxis("Years"));
        Axis yAxis = getLineModel2().getAxis(AxisType.Y);
        yAxis.setLabel("Births");
        yAxis.setMin(0);
        yAxis.setMax(200);
        
    }
    
    private LineChartModel initCategoryModel() {
        LineChartModel model = new LineChartModel();

        ChartSeries boys = new ChartSeries();
        boys.setLabel("Boys");
        boys.set("2004", 120);
        boys.set("2005", 100);
        boys.set("2006", 44);
        boys.set("2007", 150);
        boys.set("2008", 25);

        ChartSeries girls = new ChartSeries();
        girls.setLabel("Girls");
        girls.set("2004", 52);
        girls.set("2005", 60);
        girls.set("2006", 110);
        girls.set("2007", 90);
        girls.set("2008", 120);

        model.addSeries(boys);
        model.addSeries(girls);

        return model;
    }

    /**
     * @return the serie
     */
    public List<Punto> getSerie() {
        return serie;
    }

    /**
     * @param serie the serie to set
     */
    public void setSerie(List<Punto> serie) {
        this.serie = serie;
    }

    /**
     * @return the lineModel2
     */
    public LineChartModel getLineModel2() {
        return lineModel2;
    }

    /**
     * @param lineModel2 the lineModel2 to set
     */
    public void setLineModel2(LineChartModel lineModel2) {
        this.lineModel2 = lineModel2;
    }
    
}
