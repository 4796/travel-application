/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main;



import forme.Login;
import forme.Regist;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author lazar
 */
public class Main {
    public static Regist reg;
    public static Login log;
    public static void main(String[] args) {
        
        log=new Login();
        reg=new Regist();
        log.setVisible(true);
        

    }
}
