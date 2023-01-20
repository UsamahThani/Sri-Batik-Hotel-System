/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.hsb.controller;

import com.hsb.model.AdminBean;
import com.hsb.model.AdminLoginDao;
import com.hsb.model.StaffLoginBean;
import com.hsb.model.StaffLoginDao;
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
import javax.servlet.http.HttpSession;

/**
 *
 * @author Usamah
 */
public class login extends HttpServlet {

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
            out.println("<title>Staff Login Process...</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet staff_loginProcess at " + request.getContextPath() + "</h1>");
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
        
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection conn = null;
        HttpSession session = request.getSession(true);
        
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        
        if(request.getParameter("formType").equals("staff")){
            try {
                String staffId = request.getParameter("staffId");
                String staffPassword = request.getParameter("staffPass");
                StaffLoginBean staff = new StaffLoginBean();
                staff.setStaffId(staffId);
                staff.setStaffPassword(staffPassword);
                String invalidMsgs = "Invalid staff ID or password!";
                
                StaffLoginDao loginDao = new StaffLoginDao();
                String userValidate = loginDao.authenticateUser(staff);
                if(userValidate.equals("SUCCESS")){
                    session.setAttribute("staffId", staff.getStaffId());
                    request.getRequestDispatcher("staff_mainpage.jsp").forward(request, response); ;
                }else{
                    out.println("<script>");
                    out.println("alert('" + invalidMsgs + "')");
                    out.println("history.back()");
                    out.println("</script>");
                }       
                out.close();
            } catch (SQLException ex) {
                Logger.getLogger(login.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else if(request.getParameter("formType").equals("admin")){
            try {
                //get parameter from form
                String adminId = request.getParameter("adminId");
                String adminPass = request.getParameter("adminPass");
                
                //call admin bean and set parameter
                AdminBean admin = new AdminBean();
                admin.setAdminId(adminId);
                admin.setAdminPassword(adminPass);
                
                //call AdminLoginDao
                AdminLoginDao loginDao = new AdminLoginDao();
                String adminValidate = loginDao.authenticateUser(admin);
                if(adminValidate.equals("SUCCESS")){
                    session.setAttribute("adminId", admin.getAdminId());
                    request.getRequestDispatcher("admin_mainpage.jsp").forward(request, response);
                }else{
                    out.println("<script>");
                    out.println("alert('Wrong admin Id or password! Please try again!')");
                    out.println("history.back()");
                    out.println("</script>");
                }
            } catch (SQLException ex) {
                Logger.getLogger(login.class.getName()).log(Level.SEVERE, null, ex);
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
