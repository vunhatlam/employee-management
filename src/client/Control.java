/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
/**
 *
 * @author kami
 */
public class Control {
    private Socket mySocket;
    private String serverHost = "localhost";
    private int port = 7899;
    private ObjectOutputStream oos;
    private ObjectInputStream ois;
    public Control() {
    }
    
    public Socket openConnection(){
        try{
            mySocket = new Socket(serverHost, port);
        }catch(IOException e){
            e.printStackTrace();
            return null;
        }
        return mySocket;
    }
    
    public void ioStream(){
        try{
            oos = new ObjectOutputStream(mySocket.getOutputStream());
            ois = new ObjectInputStream(mySocket.getInputStream());
        }catch(IOException e){
            e.printStackTrace();
        }
    }
    
    public boolean sendOption(int i){
        try{
            oos.writeObject(i);
        }catch(IOException e){
            e.printStackTrace();
            return false;
        }
        return true;
    }
    
    public boolean sendString(String s){
        try{
            oos.writeObject(s);
        }catch(IOException e){
            e.printStackTrace();
            return false;
        }
        return true;
    }
    
    public boolean sendData(CongNhan c){
        try{
            oos.writeObject(c);
        }catch(IOException e){
            e.printStackTrace();
            return false;
        }
        return true;
    }
    
    public String receiveData(){
        String result = null;
        try{
            Object o = ois.readObject();
            if(o instanceof String){
                result = (String) o;
            }
        }catch(IOException | ClassNotFoundException e){
            e.printStackTrace();
            return null;
        }
        return result;
    }
    
    public CongNhan receiveCongnhan(){
        CongNhan c = new CongNhan();
        try{
            Object o = ois.readObject();
            if(o instanceof CongNhan){
                c = (CongNhan)o;
            }
        }catch(IOException | ClassNotFoundException e){
            e.printStackTrace();
            return null;
        }
        return c;
    }
    
    public ArrayList<CongNhan> receiveList(){
        ArrayList<CongNhan> list = new ArrayList<>();
        try{
            Object o = ois.readObject();
            if(o instanceof ArrayList){
                list = (ArrayList) o;
            }
        }catch(IOException | ClassNotFoundException e){
            e.printStackTrace();
            return null;
        }
        return list;
    }
    
    public ArrayList<QueQuan> receiveQQ(){
        ArrayList<QueQuan> list = new ArrayList<>();
        try{
            Object o = ois.readObject();
            if(o instanceof ArrayList){
                list = (ArrayList) o;
            }
        }catch(IOException | ClassNotFoundException e){
            e.printStackTrace();
            return null;
        }
        return list;
    }
    
    public boolean closeConnection(){
        try{
            mySocket.close();
        }catch(IOException e){
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
