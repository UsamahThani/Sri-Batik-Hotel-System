/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.hsb.controller;

import com.hsb.model.CustomerBean;
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
public class staff_checkout extends HttpServlet {

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
            out.println("<title>Servlet staff_checkout</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet staff_checkout at " + request.getContextPath() + "</h1>");
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
         * list to do:
         * 1. Using the parameter from the check out form, find customer 
         *    from room table.
         * 2. Check if the room available or not. If NOT available, put customer
         *    data into session/bean and update the room status also delete the customer
         *    data.
         * 3. If available, alert comes out and user need to choose room that has
         *    been checked in
         */
        
        //calling parameter and set it to bean
        CustomerBean cust = new CustomerBean();
        String custRoomNum = request.getParameter("custRoomNum");
        cust.setCustRoomNum(custRoomNum);
        
        //database connection
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection conn = null;
        
        try{
            conn = database.getDatabase();
            
            //check room status
            String checkSQL = "SELECT is_available FROM room WHERE roomNumber = ?";
            pstmt = conn.prepareStatement(checkSQL);
            pstmt.setString(1, custRoomNum);
            rs = pstmt.executeQuery();
            if(rs.next()){
                //got room status
                int is_available = rs.getInt("is_available");
                
                //if room NOT available, get customer data and set it on session/bean
                if(is_available == 1){
                    //find customer details and set it to session/bean
                    String custSQL = "SELECT custName, custPeriod FROM customer WHERE custRoomNum = ?";
                    pstmt = conn.prepareStatement(custSQL);
                    pstmt.setString(1, cust.getCustRoomNum());
                    rs = pstmt.executeQuery();
                    while(rs.next()){
                        cust.setCustName(rs.getString("custName"));
                        cust.setCustPeriod(rs.getInt("custPeriod"));
                    }
                    
                    //find room price and set it to session/bean
                    String roomSQL = "SELECT roomPrice FROM room WHERE roomNumber = ?";
                    pstmt = conn.prepareStatement(roomSQL);
                    pstmt.setString(1, cust.getCustRoomNum());
                    rs = pstmt.executeQuery();
                    while(rs.next()){
                        cust.setCustRoomPrice(rs.getDouble("roomPrice"));
                    }
                    
                    //calculate the total payment and set it to bean
                    double totalPayment = cust.getCustRoomPrice() * cust.getCustPeriod();
                    cust.setCustTotalPayment(totalPayment);
                    
                    //update the room status 
                    String updateSQL = "UPDATE room SET is_available = ? WHERE roomNumber = ?";
                    pstmt = conn.prepareStatement(updateSQL);
                    pstmt.setBoolean(1, false);
                    pstmt.setString(2, cust.getCustRoomNum());
                    pstmt.executeUpdate();
                    
                    //delete customer that check out
                    String deleteSQL = "DELETE FROM customer WHERE custRoomNum = ?";
                    pstmt = conn.prepareStatement(deleteSQL);
                    pstmt.setString(1, cust.getCustRoomNum());
                    pstmt.execute();
                    
                    //set attribute and send to staff_payment.jsp
                    request.setAttribute("cust", cust);
                    request.getRequestDispatcher("staff_payment.jsp").forward(request, response);
                }else{
                    //if the room is available, it shouldn't be checked out
                    out.println("<script>");
                    out.println("alert('The room is available! Cannot check out! Please enter the correct room number.');");
                    out.println("history.back();");
                    out.println("</script>");
                }
                
                
            }else{
                out.println("Error entering database");
            }
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
