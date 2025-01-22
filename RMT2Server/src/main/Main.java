/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main;

import control.Control;

/**
 *
 * @author lazar
 */
public class Main {
    public static void main(String[] args) {
        Control c=Control.getInstance();
        try {
            c.startServer();
        } catch (Exception e) {
            System.out.println("Nije uspela konekcija sa bazom");
        }
        
    }
}
