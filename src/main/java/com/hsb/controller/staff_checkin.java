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
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Usamah
 */
public class staff_checkin extends HttpServlet {

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
            out.println("<title>Check In Customer...</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet staff_checkin at " + request.getContextPath() + "</h1>");
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
        HttpSession session = request.getSession();
        
        String custName = request.getParameter("custName");
        String custNumber = request.getParameter("custNumber");
        String custAddress = request.getParameter("custAddress");
        String custEmail = request.getParameter("custEmail");
        int custPeriod = Integer.parseInt(request.getParameter("custPeriod"));
        String custRoomNum = request.getParameter("custRoomNum");
        double totalPayment = 0;
        double roomPrice = 0;
        
        try{
            conn = database.getDatabase();
            
            //check the room status, if room not available, error will comeout
            String checkSQL = "SELECT is_available FROM room WHERE roomNumber = ?";
            pstmt = conn.prepareStatement(checkSQL);
            pstmt.setString(1, custRoomNum);
            rs = pstmt.executeQuery();
            if(rs.next()){
                int is_available = rs.getInt("is_available");
                // is_available = 0 means room is available, 1 means room is occupied
                if(is_available == 0){
                    //insert customer details into customer table
                    String insertSQL = "INSERT INTO CUSTOMER "
                            + "(custName, custNumber, custAddress, custEmail, custPeriod, custRoomNum)"
                            + " VALUES (?, ?, ?, ?, ?, ?)";
                    pstmt = conn.prepareStatement(insertSQL);
                    pstmt.setString(1, custName);
                    pstmt.setString(2, custNumber);
                    pstmt.setString(3, custAddress);
                    pstmt.setString(4, custEmail);
                    pstmt.setInt(5, custPeriod);
                    pstmt.setString(6, custRoomNum);
                    pstmt.executeUpdate();
                    
                    
                    //update room status
                    String updateSQL = "UPDATE room SET is_available = ? WHERE roomNumber = ?";
                    pstmt = conn.prepareStatement(updateSQL);
                    pstmt.setBoolean(1, true);
                    pstmt.setString(2, custRoomNum);
                    pstmt.executeUpdate();
                    
                    session.setAttribute("custName", custName);
                    session.setAttribute("custNumber", custNumber);
                    session.setAttribute("custPeriod", custPeriod);
                    session.setAttribute("custRoomNum", custRoomNum);                    
                    response.sendRedirect("staff_checkinSuccess.view");
                    
                    //calculate the payment
                    String paymentSQL = "SELECT roomPrice FROM room WHERE roomNumber = ?";
                    pstmt = conn.prepareStatement(paymentSQL);
                    pstmt.setString(1, custRoomNum);
                    rs = pstmt.executeQuery();
                    if(rs.next()){
                        roomPrice = rs.getDouble("roomPrice");
                        request.setAttribute("roomPrice", roomPrice);
                    }
                    totalPayment = roomPrice * custPeriod;
                    session.setAttribute("roomPrice", roomPrice);
                    session.setAttribute("totalPayment", totalPayment);
                    
                }else{
                    //room is not available and failed check in
                    out.println("<script>");
                    out.println("alert('The room is occupied, please select another room!');");
                    out.println("history.back();");
                    out.println("</script>");
                }
                
            }  
        } catch (SQLException ex) {
            Logger.getLogger(staff_checkin.class.getName()).log(Level.SEVERE, null, ex);
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
