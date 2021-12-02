/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
/**
 *
 * @author kami
 */
public class View extends JFrame implements ActionListener{
    private JTextField txtHoten;
    private JTextField txtNamsinh;
    private JComboBox txtQuequan;
    private JTextField txtGioitinh;
    private JTextField txtHesoluong;
    private JButton btnTimkiem;
    private JButton btnLietke;
    private JButton btnThem;
    private JButton btnReset;
    private int option;
    private String quequan;

    public View() {
        super("Quản lý công nhân");
        this.setPreferredSize(new Dimension(400, 200));
        txtHoten = new JTextField(25);
        txtNamsinh = new JTextField(4);
        
        ArrayList<QueQuan> list = new ArrayList<>();
        Control ctr = new Control();
        ctr.openConnection();
        ctr.ioStream();
        option = 0;
        ctr.sendOption(option);
        if(ctr.receiveData().equals("ok")){
            ctr.sendString("list");
            list = ctr.receiveQQ();
        }
        ctr.closeConnection();
        String[] array = new String[list.size()];
        for(int i=0; i<array.length; i++){
            array[i] = list.get(i).getDiadanh();
        }
        txtQuequan = new JComboBox(array);
        quequan = txtQuequan.getSelectedItem().toString();
        txtQuequan.addItemListener(new ItemListener() {
        @Override
        public void itemStateChanged(ItemEvent e) {
            if (e.getSource() == txtQuequan) {
                quequan = txtQuequan.getSelectedItem().toString();
                }
            }
        });
        
        txtGioitinh = new JTextField(3);
        txtHesoluong = new JTextField(2);
        btnTimkiem = new JButton("Tìm kiếm");
        btnLietke = new JButton("Liệt kê");
        btnThem = new JButton("Thêm công nhân");
        btnReset = new JButton("Đặt lại");
        
        JPanel content = new JPanel();
        content.setLayout(new GridLayout(7,2));
        content.add(new JLabel("Họ và tên")); content.add(txtHoten);
        content.add(new JLabel("Năm sinh")); content.add(txtNamsinh);
        content.add(new JLabel("Quê quán")); content.add(txtQuequan);
        content.add(new JLabel("Giới tính")); content.add(txtGioitinh);
        content.add(new JLabel("Hệ số lương")); content.add(txtHesoluong);
        content.add(btnThem); content.add(btnLietke);
        content.add(btnTimkiem); content.add(btnReset);
        btnThem.addActionListener(this);
        btnLietke.addActionListener(this);
        btnTimkiem.addActionListener(this);
        btnReset.addActionListener(this);
        this.setContentPane(content);
        this.pack();
        
        this.addWindowListener(new WindowAdapter(){
            @Override
            public void windowClosing(WindowEvent e){
                System.exit(0);
            }
        });
    }
    
    @Override
    public void actionPerformed(ActionEvent e){
        JButton btn = (JButton) e.getSource();
        if(btn.equals(btnThem)){
            btnThem_actionPerformed();
        }else{
            if(btn.equals(btnLietke)){
                btnLietke_actionPerformed();
            }
            else{
                if(btn.equals(btnTimkiem)){
                    btnTimkiem_actionPerformed();
                }
                else{
                    btnReset_actionPerformed();
                }
            }
        }
    }
    
    public void btnThem_actionPerformed(){
        option = 1;
        if("".equals(txtHoten.getText()) || "".equals(txtNamsinh.getText()) || "".equals(txtGioitinh.getText()) || "".equals(txtHesoluong.getText())){
            showMessage("Hãy điền đầy đủ thông tin");
        }
        else{
            CongNhan c = new CongNhan();
            c.setHoten(txtHoten.getText());
            c.setNamsinh(Integer.parseInt(txtNamsinh.getText()));
            c.setQuequan(quequan);
            c.setGioitinh(txtGioitinh.getText());
            c.setHesoluong(Double.parseDouble(txtHesoluong.getText()));

            Control ctr = new Control();
            ctr.openConnection();
            ctr.ioStream();
            ctr.sendOption(option);
            if(ctr.receiveData().equals("ok")){
                ctr.sendData(c);
                String result = ctr.receiveData();
                if(result.equals("true")){
                    showMessage("Đã thêm thành công!");
                }
                else{
                    showMessage("Thêm thất bại");
                }
            }
            else{
                showMessage("Thêm thất bại");
            }
            ctr.closeConnection(); 
       }
    }
    
    public void btnLietke_actionPerformed(){
        option = 2;
        Control ctr = new Control();
        ctr.openConnection();
        ctr.ioStream();
        ctr.sendOption(option);
        if(ctr.receiveData().equals("ok")){
            ctr.sendString(quequan);
            ArrayList<CongNhan> listCN = new ArrayList<>();
            listCN = ctr.receiveList();
            if(listCN.isEmpty()){
                showMessage("Danh sách trống");
            }
            else{
                JFrame frame = new JFrame("Danh sách đồng hương quê "+quequan);
                /*JTextArea area = new JTextArea();
                for(CongNhan i : list){
                    area.append(i.toString());
                }
                frame.add(area);*/
                
                JPanel panel = new JPanel(new GridLayout(listCN.size(), 6));
                for(CongNhan i : listCN){
                    panel.add(new JLabel(i.getHoten()));
                    panel.add(new JLabel());
                    panel.add(new JLabel(String.valueOf(i.getNamsinh())));
                    panel.add(new JLabel(i.getQuequan()));
                    panel.add(new JLabel(i.getGioitinh()));
                    panel.add(new JLabel(String.valueOf(i.getHesoluong())));
                }
                frame.add(panel);
                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }
        }
        else{
            showMessage("Liệt kê thất bại");
        }
        ctr.closeConnection();
    }
    
    public void btnTimkiem_actionPerformed(){
        option = 3;
        String name = txtHoten.getText();
        if("".equals(name)){
            showMessage("Hãy nhập tên");
        }
        else{
            Control ctr = new Control();
            ctr.openConnection();
            ctr.ioStream();
            ctr.sendOption(option);
            if(ctr.receiveData().equals("ok")){
                ctr.sendString(name);
                CongNhan c = ctr.receiveCongnhan();
                if(c.getHoten() == null){
                    showMessage("Không tìm thấy công nhân");
                }
                else{
                    JFrame frame = new JFrame("Thông tin công nhân");
                    frame.setPreferredSize(new Dimension(300,150));
                    JPanel panel = new JPanel();
                    panel.setLayout(new GridLayout(5, 2));
                    panel.add(new JLabel("Họ tên: ")); panel.add(new JLabel(c.getHoten()));
                    panel.add(new JLabel("Năm sinh: ")); panel.add(new JLabel(String.valueOf(c.getNamsinh())));
                    panel.add(new JLabel("Quê quán: ")); panel.add(new JLabel(c.getQuequan()));
                    panel.add(new JLabel("Giới tính: ")); panel.add(new JLabel(c.getGioitinh()));
                    panel.add(new JLabel("Hệ số lương: ")); panel.add(new JLabel(String.valueOf(c.getHesoluong())));
                    frame.add(panel);
                    frame.pack();
                    frame.setLocationRelativeTo(null);
                    frame.setVisible(true);
                }
            }
            else{
                showMessage("Không tìm thấy công nhân");
            }
            ctr.closeConnection();
        }
    }
    
    public void btnReset_actionPerformed(){
        txtHoten.setText("");
        txtNamsinh.setText("");
        txtGioitinh.setText("");
        txtHesoluong.setText("");
    }
    
    public void showMessage(String s){
        JOptionPane.showMessageDialog(this, s);
    }
}
