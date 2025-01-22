/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package control;

import dbb.DatabaseBrocker;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import klase.Korisnik;
import klase.Putovanje;

/**
 *
 * @author lazar
 */
public class Control {

    
    DatabaseBrocker db;
    static Control instance;
    ServerSocket socket;
    List<ClientHandler> lista=new LinkedList<>();
    public Control() {
        db=new DatabaseBrocker();
        try {
            socket=new ServerSocket(9000);
        } catch (IOException ex) {
            //System.out.println("aa");
            Logger.getLogger(Control.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }
    
    public void startServer(){
    while (true) {                
        try {
            
            Socket s=socket.accept();
            ClientHandler clientHandler=new ClientHandler(s);
            
            clientHandler.start();
            
            lista.add(clientHandler);
        } catch (IOException ex) {
            //Logger.getLogger(Control.class.getName()).log(Level.SEVERE, null, ex);
        }
            
            }
    }
    
    public static Control getInstance(){
        if(instance==null)
            instance=new Control();
        return instance;
    }
    
    public List<Korisnik> listaKorisnika(){
    return db.listaKorisnika();
    }

    public List<Korisnik> listaStanovnika() {
        return db.listaStanovnika();
    }
    
    void unesiKorisnika(Korisnik korisnik) {
        db.unesiKorisnika(korisnik);
    }

    List<Putovanje> listaPutovanja(int korisnik) {
       return db.listaPutovanja(korisnik);
    }
    
    void unesiPrijavu(Putovanje putovanje, int korisnik) {
        db.unesiPrijavu(putovanje, korisnik);
    }

    void brisanjePrijave(Putovanje putovanje, Korisnik korisnik) {
        db.brisanjePrijave(putovanje, korisnik);
    }
}
