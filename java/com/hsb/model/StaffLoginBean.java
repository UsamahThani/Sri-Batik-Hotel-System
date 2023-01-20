/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.hsb.model;

/**
 *
 * @author Usamah
 */
public class StaffLoginBean implements java.io.Serializable{
    private String staffId;
    private String staffPassword;
    
    public StaffLoginBean(){
    }
    public String getStaffId(){
        return staffId;
    }
    public void setStaffId(String staffId){
        this.staffId = staffId;
    }
    public String getStaffPassword(){
        return staffPassword;
    }
    public void setStaffPassword(String staffPassword){
        this.staffPassword = staffPassword;
    }
    
}
