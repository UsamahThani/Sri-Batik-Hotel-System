/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.hsb.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Usamah
 */
public class AdminLoginDao {
    public String authenticateUser(AdminBean admin) throws SQLException{
        String adminId = admin.getAdminId();
        String password = admin.getAdminPassword();
        
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        String adminIdDB = "";
        String passwordDB = "";
        
        try{
            conn = database.getDatabase();
            String query = "SELECT adminId, adminPassword FROM admin";
            pstmt = conn.prepareStatement(query);
            rs = pstmt.executeQuery();
            while(rs.next()){
                adminIdDB = rs.getString("adminId");
                passwordDB = rs.getString("adminPassword");
                
                if(adminId.equals(adminIdDB) && password.equals(passwordDB)){
                    return "SUCCESS";
                }
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return "Invalid staff credentials";
    }
}
