/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;
import client.CongNhan;
import client.QueQuan;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author kami
 */
public class Control {
    private Connection con;
    private ServerSocket myServer;
    private int port = 7899;
    private int option;

    public Control() {
        getDBConnection("users", "root", "06011999");
        openServer(port);
        
        while(true){
            listening();
        }
    }
    
    private void getDBConnection(String dbName, String username, String password){
        String dbUrl = "jdbc:mysql://localhost:3306/" + dbName;
        
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(dbUrl, username, password);
        }catch(SQLException | ClassNotFoundException e){
            e.printStackTrace();
        }
    }
    
    private void openServer(int port){
        try{
            myServer = new ServerSocket(port);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    private void listening(){
        try{
            Socket clientSocket = myServer.accept();
            ObjectInputStream ois = new ObjectInputStream(clientSocket.getInputStream());
            ObjectOutputStream oos = new ObjectOutputStream(clientSocket.getOutputStream());
            option = (int)ois.readObject();
            oos.writeObject("ok");
            
            if(option == 0){
                Object o = ois.readObject();
                if(o instanceof String){
                    String s = (String) o;
                    if(s.equals("list")){
                        oos.writeObject(listQQ());
                    }
                }
            }
            
            if(option == 1){
                Object o = ois.readObject();
                if(o instanceof CongNhan){
                    CongNhan c = (CongNhan) o;
                    if(addCongnhan(c)){
                        oos.writeObject("true");
                    }
                    else{
                        oos.writeObject("false");
                    }
                }
            }
            
            if(option == 2){
                Object o = ois.readObject();
                if(o instanceof String){
                    String s = (String) o;
                    oos.writeObject(listCN(s));
                }
            }
            
            if(option == 3){
                Object o = ois.readObject();
                if(o instanceof String){
                    String s = (String) o;
                    oos.writeObject(check(s));
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    private CongNhan check(String s) throws Exception{
        String query = "SELECT * FROM users.congnhan WHERE hoten = '" + s + "'";
        try{
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(query);
            
            while(rs.next()){
                CongNhan c = new CongNhan();
                c.setHoten(rs.getString("hoten"));
                c.setNamsinh(rs.getInt("namsinh"));
                c.setQuequan(rs.getString("quequan"));
                c.setGioitinh(rs.getString("gioitinh"));
                c.setHesoluong(rs.getDouble("hesoluong"));
                return c;
            }
        }catch(SQLException e){
            throw e;
        }
        return null;
    }
    
    private boolean addCongnhan(CongNhan c){
        String query = "INSERT INTO users.congnhan(hoten, namsinh, quequan, gioitinh, hesoluong) VALUES(?, ?, ?, ?, ?)";
        try{
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, c.getHoten());
            ps.setInt(2, c.getNamsinh());
            ps.setString(3, c.getQuequan());
            ps.setString(4, c.getGioitinh());
            ps.setDouble(5, c.getHesoluong());
            
            ps.executeUpdate();
            return true;
        }catch(Exception e){
            e.printStackTrace();
        }
        return false;
    }
    
    private ArrayList<CongNhan> listCN(String s) {
        String query = "SELECT * FROM users.congnhan WHERE quequan = '" + s + "'";
        ArrayList<CongNhan> list = new ArrayList<>();
        try{
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(query);
            while(rs.next()){
                CongNhan c = new CongNhan();
                c.setHoten(rs.getString("hoten"));
                c.setNamsinh(rs.getInt("namsinh"));
                c.setQuequan(rs.getString("quequan"));
                c.setGioitinh(rs.getString("gioitinh"));
                c.setHesoluong(rs.getDouble("hesoluong"));
                list.add(c);
            }
            return list;
        }catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }
    
    private ArrayList<QueQuan> listQQ(){
        String query = "SELECT * FROM users.quequan";
        ArrayList<QueQuan>list = new ArrayList<>();
        try{
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(query);
            while(rs.next()){
                QueQuan qq = new QueQuan();
                qq.setDiadanh(rs.getString("diadanh"));
                list.add(qq);
            }
            return list;
        }catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }
}
