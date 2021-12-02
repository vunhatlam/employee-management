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
public class QueQuan implements Serializable{
    private String diadanh;

    public QueQuan() {
    }

    public QueQuan(String diadanh) {
        this.diadanh = diadanh;
    }

    public String getDiadanh() {
        return diadanh;
    }

    public void setDiadanh(String diadanh) {
        this.diadanh = diadanh;
    }
    
}
