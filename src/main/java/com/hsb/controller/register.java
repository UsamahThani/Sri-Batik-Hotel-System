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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Usamah
 */
public class register extends HttpServlet {

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
            out.println("<title>Servlet register</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet register at " + request.getContextPath() + "</h1>");
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
        
        //database tools
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        //do this if staff registeration
        if(request.getParameter("formType").equals("registerStaff")){
            try{
                String staffId = request.getParameter("staffId");
                String staffName = request.getParameter("staffName");
                String staffEmail = request.getParameter("staffEmail");
                String staffNumber = request.getParameter("staffNumber");
                String staffPass = request.getParameter("staffPassword");
                
                //connect to database
                conn = database.getDatabase();
                
                //check if the staffId already registered
                String checkSQL = "SELECT 1 FROM staff WHERE staffId = ?";
                pstmt = conn.prepareStatement(checkSQL);
                pstmt.setString(1, staffId);
                rs = pstmt.executeQuery();
                
                if(rs.next()){
                    //staffId already exists
                    out.println("<script>");
                    out.println("alert('The staff ID: "+ staffId +" already registered!')");
                    out.println("history.back()");
                    out.println("</script>");
                }else{
                    //staff not exist yet and register staff
                    String staffSQL = "INSERT INTO staff (staffId, staffName, staffEmail, staffNumber, staffPassword) VALUES (?, ?, ?, ?, ?)";
                    pstmt = conn.prepareStatement(staffSQL);
                    pstmt.setString(1, staffId);
                    pstmt.setString(2, staffName);
                    pstmt.setString(3, staffEmail);
                    pstmt.setString(4, staffNumber);
                    pstmt.setString(5, staffPass);
                    pstmt.executeUpdate();
                    
                    //view alert staff registered successfull
                    out.println("<script>");
                    out.println("alert('Staff has been registered successfully!')");
                    out.println("history.back()");
                    out.println("</script>");
                }
            }   catch (SQLException ex) {
                Logger.getLogger(register.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else if(request.getParameter("formType").equals("registerAdmin")){
            try {
                //do this id admin registeration
                String adminId = request.getParameter("adminId");
                String adminName = request.getParameter("adminName");
                String adminEmail = request.getParameter("adminEmail");
                String adminNumber = request.getParameter("adminNumber");
                String adminPass = request.getParameter("adminPassword");
                
                //connect to database
                conn = database.getDatabase();
                
                //check if the adminId already registered
                String checkSQL = "SELECT 1 FROM admin WHERE adminID = ?";
                pstmt = conn.prepareStatement(checkSQL);
                pstmt.setString(1, adminId);
                rs = pstmt.executeQuery();
                
                if(rs.next()){
                    //adminId already exists
                    out.println("<script>");
                    out.println("alert('The admin ID: "+ adminId +" already registered!')");
                    out.println("history.back()");
                    out.println("</script>");
                }else{
                    //admin not exist yet and register admin
                    String adminSQL = "INSERT INTO admin (adminId, adminName, adminEmail, adminNumber, adminPassword) VALUES (?, ?, ?, ?, ?)";
                    pstmt = conn.prepareStatement(adminSQL);
                    pstmt.setString(1, adminId);
                    pstmt.setString(2, adminName);
                    pstmt.setString(3, adminEmail);
                    pstmt.setString(4, adminNumber);
                    pstmt.setString(5, adminPass);
                    pstmt.executeUpdate();
                    
                    //view alert admin registered successfull
                    out.println("<script>");
                    out.println("alert('Staff has been registered successfully!')");
                    out.println("history.back()");
                    out.println("</script>");
                }   
            } catch (SQLException ex) {
                throw new ServletException("Servlet Could not display records.", ex);
            }
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
