/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

public class FinesToInvoice {

    private String fineNum;
    private String caseNo;
    private String recievedFrom;
    private String date;
    private double amount;


    public String getFineNum() {
        return fineNum;
    }

    public void setFineNum(String fineNum) {
        this.fineNum = fineNum;
    }

    public String getCaseNo() {
        return caseNo;
    }

    public void setCaseNo(String caseNo) {
        this.caseNo = caseNo;
    }

    public String getRecievedFrom() {
        return recievedFrom;
    }

    public void setRecievedFrom(String recievedFrom) {
        this.recievedFrom = recievedFrom;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
