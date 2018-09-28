/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author Andres
 */
@ManagedBean(name="punto")
@SessionScoped
public class Punto  implements Serializable{ 
    private String x;
    private Integer y;

    public Punto(String x, Integer y) {
        this.x = x;
        this.y = y;
    }

    /**
     * @return the x
     */
    public String getX() {
        return x;
    }

    /**
     * @param x the x to set
     */
    public void setX(String x) {
        this.x = x;
    }

    /**
     * @return the y
     */
    public Integer getY() {
        return y;
    }

    /**
     * @param y the y to set
     */
    public void setY(Integer y) {
        this.y = y;
    }

    
    
    
    
}
