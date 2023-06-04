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
public class StaffLoginDao {
    public String authenticateUser(StaffLoginBean staff) throws SQLException{
        String staffId = staff.getStaffId();
        String password = staff.getStaffPassword();
        
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        String staffIdDB = "";
        String passwordDB = "";
        
        try{
            conn = database.getDatabase();
            String query = "SELECT staffId, staffPassword FROM staff";
            pstmt = conn.prepareStatement(query);
            rs = pstmt.executeQuery();
            while(rs.next()){
                staffIdDB = rs.getString("staffId");
                passwordDB = rs.getString("staffPassword");
                
                if(staffId.equals(staffIdDB) && password.equals(passwordDB)){
                    return "SUCCESS";
                }
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return "Invalid staff credentials";
    }
}
