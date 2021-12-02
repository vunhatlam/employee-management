/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

/**
 *
 * @author kami
 */
public class View {

    public View() {
        showMessage("Server is running...");
        Control ctr = new Control();
    }
    
    private void showMessage(String msg){
        System.out.println(msg);
    }
}
