/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.hsb.view;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Usamah
 */
public class staff_checkinSuccess extends HttpServlet {

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
        HttpSession session = request.getSession(false);
        
        String custName = (String) session.getAttribute("custName");
        String custNumber = (String) session.getAttribute("custNumber");
        int custPeriod = (int) session.getAttribute("custPeriod");
        String custRoomNum = (String) session.getAttribute("custRoomNum");
        double roomPrice = (double) session.getAttribute("roomPrice");
        double totalPayment = (double) session.getAttribute("totalPayment");
        
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        
        out.println("<script>");
        out.println("alert('Check In Successful!"
                + "\\nCustomer Name: " + custName + ""
                        + "\\nPhone Number: " + custNumber + ""
                                + "\\nPeriod of Stay: " + custPeriod + " night(s)"
                                        + "\\nRoom Number: " + custRoomNum +""
                                                + "\\nRoom Price: RM " + String.format("%.2f", roomPrice ) + ""
                                                        + "\\nTotal Payment: RM " + String.format("%.2f", totalPayment ) + "');");
        out.println("history.back();");
        out.println("</script>");
        response.flushBuffer();
        session.invalidate();
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
