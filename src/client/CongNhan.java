/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import java.io.Serializable;

/**
 *
 * @author kami
 */
public class CongNhan implements Serializable{
    private String hoten;
    private int namsinh;
    private String quequan;
    private String gioitinh;
    private double hesoluong;

    public CongNhan() {
    }

    public CongNhan(String hoten, int namsinh, String quequan, String gioitinh, double hesoluong) {
        this.hoten = hoten;
        this.namsinh = namsinh;
        this.quequan = quequan;
        this.gioitinh = gioitinh;
        this.hesoluong = hesoluong;
    }

    public String getHoten() {
        return hoten;
    }

    public void setHoten(String hoten) {
        this.hoten = hoten;
    }

    public int getNamsinh() {
        return namsinh;
    }

    public void setNamsinh(int namsinh) {
        this.namsinh = namsinh;
    }

    public String getQuequan() {
        return quequan;
    }

    public void setQuequan(String quequan) {
        this.quequan = quequan;
    }

    public String getGioitinh() {
        return gioitinh;
    }

    public void setGioitinh(String gioitinh) {
        this.gioitinh = gioitinh;
    }

    public double getHesoluong() {
        return hesoluong;
    }

    public void setHesoluong(double hesoluong) {
        this.hesoluong = hesoluong;
    } 

    @Override
    public String toString() {
        return hoten+"\t"+namsinh+"\t"+quequan+"\t"+gioitinh+"\t"+hesoluong+"\n";
    }
    
}
