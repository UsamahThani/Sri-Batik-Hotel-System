/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.hsb.model;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 *
 * @author Usamah
 */
public class database {
    static Connection conn = null; 
    
    private database(){
    }
    
    public static Connection getDatabase(){
        try{
           if (conn == null) {
               Class.forName("com.mysql.jdbc.Driver");
               
               String dbUrl = "jdbc:mysql://localhost:3306/";
               String dbName = "hotelsribatik";
               String dbUser = "root";
               String dbPass = "1234";//If using XAMPP, no password. If using mySQL, password is 1234.
               
               conn = DriverManager.getConnection(dbUrl + dbName, dbUser, dbPass);
            } 
        }catch (Exception e) {
            e.printStackTrace();
        }
        return conn;
    }
}
