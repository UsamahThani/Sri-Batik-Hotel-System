/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.hsb.controller;

import com.hsb.model.database;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Usamah
 */
public class admin_adminedit extends HttpServlet {

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
        try ( PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet admin_adminedit</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet admin_adminedit at " + request.getContextPath() + "</h1>");
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
        response.setContentType("text/html;charset=UTF-8");
            PrintWriter out = response.getWriter();
            Connection conn = null;
            PreparedStatement pstmt = null;
            ResultSet rs = null;
            
            try {
                String adminId = request.getParameter("adminId");
                String adminName = request.getParameter("adminName");
                String adminEmail = request.getParameter("adminEmail");
                String adminNumber = request.getParameter("adminNumber");
                String adminPass = request.getParameter("adminPassword");

                //call database and update the new admin details
                conn = database.getDatabase();
                String updateSQL = "UPDATE admin SET adminName = ?, adminEmail = ?, adminNumber = ?, adminPassword = ? WHERE adminID = ?";
                pstmt = conn.prepareStatement(updateSQL);
                pstmt.setString(1, adminName);
                pstmt.setString(2, adminEmail);
                pstmt.setString(3, adminNumber);
                pstmt.setString(4, adminPass);
                pstmt.setString(5, adminId);
                pstmt.executeUpdate();
                
                //edit admin successful
                out.println("<script>");
                out.println("alert('Staff details updated!');");
                out.println("history.back();");
                out.println("</script>");
            
            } catch (SQLException ex) {
                throw new ServletException("Servlet Could not display records.", ex);
        }
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
