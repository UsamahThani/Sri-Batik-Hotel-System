/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.hsb.model;

/**
 *
 * @author Usamah
 */
public class CustomerBean implements java.io.Serializable {
    private String custName;
    private String custNumber;
    private String custAddress;
    private String custEmail;
    private int custPeriod;
    private String custRoomNum;
    private double custRoomPrice;
    private double custTotalPayment;

    public CustomerBean() {
    }

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public String getCustNumber() {
        return custNumber;
    }

    public void setCustNumber(String custNumber) {
        this.custNumber = custNumber;
    }

    public String getCustAddress() {
        return custAddress;
    }

    public void setCustAddress(String custAddress) {
        this.custAddress = custAddress;
    }

    public String getCustEmail() {
        return custEmail;
    }

    public void setCustEmail(String custEmail) {
        this.custEmail = custEmail;
    }

    public int getCustPeriod() {
        return custPeriod;
    }

    public void setCustPeriod(int custPeriod) {
        this.custPeriod = custPeriod;
    }

    public String getCustRoomNum() {
        return custRoomNum;
    }

    public void setCustRoomNum(String custRoomNum) {
        this.custRoomNum = custRoomNum;
    }
    
    public double getCustRoomPrice() {
        return custRoomPrice;
    }

    public void setCustRoomPrice(double custRoomPrice) {
        this.custRoomPrice = custRoomPrice;
    }
    
    public double getCustTotalPayment() {
        return custTotalPayment;
    }

    public void setCustTotalPayment(double custTotalPayment) {
        this.custTotalPayment = custTotalPayment;
    }
    
    public String getCustRP() {
        return String.format("RM %.2f", custRoomPrice);
    }
    
    public String getCustTP() {
        return String.format("RM %.2f", custTotalPayment);
    }

}
