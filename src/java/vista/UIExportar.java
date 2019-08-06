/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vista;

//librerias para imagenes base64
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import org.apache.commons.codec.binary.Base64;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.util.JRLoader;


/**
 *
 * @author Andres
 */
@WebServlet(name = "UIExportar", urlPatterns = {"/exportar"})
public class UIExportar extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String nomReporte = request.getParameter("nomReporte");
            String parametros = request.getParameter("parametros");
            String valores = request.getParameter("valores");
            String tipos = request.getParameter("tipos");
            //String formato = request.getParameter("formato");
            //String bd = request.getParameter("bd");
            HashMap<String, Object> mapa = new HashMap<>();
            SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd");

            //this.cargarPropiedades();
            for (int i = 0; i < parametros.split(",").length; i++) {
                if (tipos.split(",")[i].equals("I")) {
                    mapa.put(parametros.split(",")[i], Integer.parseInt(valores.split(",")[i]));
                }
                if (tipos.split(",")[i].equals("S")) {
                    mapa.put(parametros.split(",")[i], valores.split(",")[i]);
                }
                if (tipos.split(",")[i].equals("D")) {
                    mapa.put(parametros.split(",")[i], formatoFecha.parse(valores.split(",")[i]));
                }
            }                        
            
            File reportFile;

            ServletOutputStream out;
            ServletContext sc = getServletConfig().getServletContext();
            
            mapa.put("SUBREPORT_DIR",sc.getRealPath("reportes") + "\\");

            //ASIGNAR LA RUTA ACTUAL DEL REPORTE
            //mapa.put("SUBREPORT_DIR", sc.getRealPath(nomReporte + ".jasper").replace(nomReporte.split("/")[nomReporte.split("/").length - 1] + ".jasper", ""));
            String controlador = "org.postgresql.Driver";
            Class.forName(controlador).newInstance();
            String url = "jdbc:postgresql://localhost:5432/gestionatencion";
            String usuario = "postgres";
            String clave = "Sptsolutech*";
            Connection conexion = java.sql.DriverManager.getConnection(url, usuario, clave);
            //reportFile = new File(this.getClass().getClassLoader().getResource("valoracion.jasper").getPath());
            reportFile = new File(sc.getRealPath("reportes\\" + nomReporte + ".jasper"));

            JasperReport jasperReport = (JasperReport) JRLoader.loadObject(reportFile);

            byte[] fichero = JasperRunManager.runReportToPdf(jasperReport, mapa, conexion);

            response.setContentType("application/pdf");
            response.setHeader("Content-disposition", "attachment; filename="+nomReporte+".pdf");
            response.setHeader("Cache-Control", "max-age=30");
            response.setHeader("Pragma", "No-cache");
            response.setDateHeader("Expires", 0);
            response.setContentLength(fichero.length);
            out = response.getOutputStream();
            out.write(fichero, 0, fichero.length);
            out.flush();
            out.close();
            
            //InputStream stream = new ByteArrayInputStream(Base64.decodeBase64(("hola").getBytes()));

        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | SQLException | JRException | ParseException ex) {
            Logger.getLogger(UIExportar.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
