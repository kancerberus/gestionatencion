/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.io.File;
import java.sql.Connection;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.util.JRLoader;

/**
 *
 * @author Andres
 */
public class GestorReporte extends HttpServlet {

    public void exportar(HttpServletRequest request, HttpServletResponse response) {
        try {
            //String path = this.getClass().getClassLoader().getResource("valoracion.jasper").getPath();
            File reportFile;
            HashMap<String, Object> mapa = new HashMap<>();
            ServletOutputStream out;
//            String nomReporte = "valoracion";
//            ServletContext sc = getServletConfig().getServletContext();
            //System.out.println(path);
            //ASIGNAR LA RUTA ACTUAL DEL REPORTE
            //mapa.put("SUBREPORT_DIR", sc.getRealPath(nomReporte + ".jasper").replace(nomReporte.split("/")[nomReporte.split("/").length - 1] + ".jasper", ""));

            String controlador = "org.postgresql.Driver";
            Class.forName(controlador).newInstance();
            String url = "jdbc:postgresql://localhost:5432/gestionatencion1008";
            String usuario = "postgres";
            String clave = "1234";
            Connection conexion = java.sql.DriverManager.getConnection(url, usuario, clave);
            reportFile = new File(this.getClass().getClassLoader().getResource("valoracion.jasper").getPath());

            JasperReport jasperReport = (JasperReport) JRLoader.loadObject(reportFile);

            byte[] fichero = JasperRunManager.runReportToPdf(jasperReport, mapa, conexion);
            
              response.setContentType("application/pdf");
                response.setHeader("Content-disposition", "attachment; filename=valoracion.pdf");
                response.setHeader("Cache-Control", "max-age=30");
                response.setHeader("Pragma", "No-cache");
                response.setDateHeader("Expires", 0);
                response.setContentLength(fichero.length);
                out = response.getOutputStream();
                out.write(fichero, 0, fichero.length);
                out.flush();
                out.close();

        } catch (Exception ex) {
            Logger.getLogger(GestorReporte.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
