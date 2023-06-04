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
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Usamah
 */
public class staff_payment extends HttpServlet {

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
            out.println("<title>Servlet staff_payment</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet staff_payment at " + request.getContextPath() + "</h1>");
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
        /**
         * list to do
         * 1. request parameter the form and set it to database payment.
         * 2. get date and time of payment and set it to database.
         */
        
        //get request parameter
        String payCustName = request.getParameter("custName");
        String tp = request.getParameter("totalPayment");
        String tpRM = tp.substring(2).trim();
        double totalPayment = Double.parseDouble(tpRM);
        int period = Integer.parseInt(request.getParameter("period"));
        
        //get the current date
        Date currentDate = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formattedDate = dateFormat.format(currentDate);
        
        //database connection
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection conn = null;
        
        if(period != 0){
            try{
                conn = database.getDatabase();
                //add data into database
                String dateSQL = "INSERT INTO payment(payCustName, payDate, payTotal) VALUES (?, ?, ?)";
                pstmt = conn.prepareStatement(dateSQL);
                pstmt.setString(1, payCustName);
                pstmt.setString(2, formattedDate);
                pstmt.setDouble(3, totalPayment);
                pstmt.executeUpdate();

                //send to success view
                request.getRequestDispatcher("staff_paymentSuccess.view").forward(request, response);
            }catch (SQLException e){
                throw new ServletException("Error saving date to database", e);
            }
        }else{
            out.println("<script>");
            out.println("alert('Error! Please checkout room first before make payment!');");
            out.println("history.back();");
            out.println("</script>");
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
