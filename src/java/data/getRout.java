/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Empire Rider
 */
public class getRout extends HttpServlet {

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
        response.setContentType("text/html;charset=UTF-8");

try (PrintWriter out = response.getWriter()) {

            connection connection = new connection();

            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet getRout</title>");
            out.println("</head>");
            out.println("<body>");

            String from = request.getParameter("from");
            String to = request.getParameter("to");

            if (from.trim().isEmpty() || to.trim().isEmpty() || to.equalsIgnoreCase(from)) {
                out.println("<h1>Invalid Data</h1>");
                out.println("</body>");
                out.println("</html>");
                return;
            }

            int fIndex = 0;
            int tIndex = 0;
            int fromEqualIndex;
            int toEqualIndex;

            HashMap<String, String[]> fromRouts = new HashMap<>();
            HashMap<String, String[]> toRouts = new HashMap<>();

            String[] fromRout;
            String[] toRout;
            String rout;
            
            

            try {

                ResultSet fromGet = connection.get("SELECT `rout_number`, `haults` FROM `test`.`rut` WHERE `haults` LIKE '%" + from.toLowerCase() + "%' AND `haults` LIKE '%" + to.toLowerCase() + "%'");
                ResultSet toGet;
                
                 

                if (fromGet.next()) {
                    fromGet.beforeFirst();
                    while (fromGet.next()) {
                        out.println(fromGet.getString(1) + "<br>");
                        out.println(fromGet.getString(2) + "<br>");

                        System.out.println(fromGet.getString(1));
                        System.out.println(fromGet.getString(2));
                        fromRouts.put(fromGet.getString(1), fromGet.getString(2).split(","));
                    }

                    for (Map.Entry<String, String[]> entry : fromRouts.entrySet()) {
                        rout = entry.getKey();
                        fromRout = entry.getValue();

                        for (int fromHoult = 0; fromHoult < fromRout.length; fromHoult++) {
                            if (from.equalsIgnoreCase(fromRout[fromHoult])) {
                                fIndex = fromHoult;
                            }
                        }
                        for (int toHault = 0; toHault < fromRout.length; toHault++) {
                            if (to.equalsIgnoreCase(fromRout[toHault])) {
                                tIndex = toHault;
                            }
                        }

                        int k = fIndex;
                        String path = rout + " - ";
                        while (fromRout.length > -1) {
                            path += fromRout[k] + " >";
                            if (k == tIndex) {
                                break;
                            }
                            if (fIndex < tIndex) {
                                k++;
                            } else {
                                k--;
                            }
                        }
                        
                        System.out.println("<h1>This Is Your Bus Routes</h1>");
                        out.println(path.substring(0, path.length() - 1));
                        out.println("<h1>------------------------------------------------------------------------</h1>");
                        System.out.println("You can get bus");
                        System.out.println(path.substring(0, path.length() - 1));
                        System.out.println("----------------------------------------------------------------------");
                        
                    }

                } else {
                    fromGet = connection.get("SELECT `rout_number`, `haults` FROM `test`.`rut` WHERE `haults` LIKE '%" + from.toLowerCase() + "%' ");
                    toGet = connection.get("SELECT `rout_number`, `haults` FROM `test`.`rut` WHERE `haults` LIKE '%" + to.toLowerCase() + "%' ");

                    if (fromGet == null) {
                        out.println("<h1>Invalid Data</h1>");
                        out.println("</body>");
                        out.println("</html>");
                        return;
                    }
                    while (fromGet.next()) {
                        out.println(fromGet.getString(1) + "<br>");
                        out.println(fromGet.getString(2) + "<br>");

                        System.out.println(fromGet.getString(1));
                        System.out.println(fromGet.getString(2));
                        fromRouts.put(fromGet.getString(1), fromGet.getString(2).split(","));
                    }

                    out.println("<h1>------------------------------------------------------------------------</h1>");
                    System.out.println("Test");
                    System.out.println("----------------------------------------------------------------------");

                    if (toGet == null) {
                        out.println("<h1>Invalid Data</h1>");
                        out.println("</body>");
                        out.println("</html>");
                        return;
                    }
                    while (toGet.next()) {
                        out.println(toGet.getString(1) + "<br>");
                        out.println(toGet.getString(2) + "<br>");

                        System.out.println(toGet.getString(1));
                        System.out.println(toGet.getString(2));
                        toRouts.put(toGet.getString(1), toGet.getString(2).split(","));
                    }
                      
                    System.out.println("----------------------------------------------------------------------");
                    System.out.println("Test 2");

                    for (Map.Entry<String, String[]> fromEntry : fromRouts.entrySet()) {
                        String fromRoutNumber = fromEntry.getKey();
                        fromRout = fromEntry.getValue();
                        for (int fromHoult = 0; fromHoult < fromRout.length; fromHoult++) {
                            if (from.equalsIgnoreCase(fromRout[fromHoult])) {
                                fIndex = fromHoult;
                            }
                        }
                        for (int fromHoult = 0; fromHoult < fromRout.length; fromHoult++) {
                            for (Map.Entry<String, String[]> toEntry : toRouts.entrySet()) {
                                String toRoutNumber = toEntry.getKey();
                                toRout = toEntry.getValue();
                                for (int toHault = 0; toHault < toRout.length; toHault++) {
                                    if (to.equalsIgnoreCase(toRout[toHault])) {
                                        tIndex = toHault;
                                    }
                                }

                                for (int toHault = 0; toHault < toRout.length; toHault++) {
                                    if (fromRout[fromHoult].equalsIgnoreCase(toRout[toHault])) {
                                        fromEqualIndex = fromHoult;
                                        toEqualIndex = toHault;

                                        int k = fIndex;
                                        String path = "";
                                        while (fromRout.length > -1) {
                                            path += fromRout[k] + " >";
                                            if (k == fromEqualIndex) {
                                                break;
                                            }
                                            if (fIndex < fromEqualIndex) {
                                                k++;
                                            } else {
                                                k--;
                                            }
                                        }
                                        out.println(fromRoutNumber+" - "+path.substring(0, path.length() - 1));
                                        out.println("<h1>------------------------------------------------------------------------</h1>");
                                        System.out.println("Test 3");
                                        System.out.println(fromRoutNumber+" - "+path.substring(0, path.length() - 1));
                                        System.out.println("----------------------------------------------------------------------");
                                        System.out.println("est 4");

                                        path = "";
                                        int l = toEqualIndex;
                                        while (toRout.length > -1) {
                                            path += toRout[l] + " >";
                                            if (l == tIndex) {
                                                break;
                                            }
                                            if (tIndex > toEqualIndex) {
                                                l++;
                                            } else {
                                                l--;
                                            }
                                        }
                                        out.println(toRoutNumber+" - "+path.substring(0, path.length() - 1));
                                        out.println("<h1>------------------------------------------------------------------------</h1>");
                                        out.println("<h3><font color='red'>And You can get these buses</font></h3>");
                                        System.out.println(toRoutNumber+" - "+path.substring(0, path.length() - 1));
                                        System.out.println("----------------------------------------------------------------------");


                                    }
                                }
                            }
                        }

                    }
                }
            } catch (SQLException ex) {
                Logger.getLogger(getRout.class.getName()).log(Level.SEVERE, null, ex);
            }
            out.println("</body>");
            out.println("</html>");
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
