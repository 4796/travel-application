/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package control;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import klase.Korisnik;
import klase.Operacija;
import klase.Putovanje;
import odgovori.Request;
import odgovori.Response;

/**
 *
 * @author lazar
 */
public class Control {
    public Socket socket;
    static Control instance;
    ObjectInputStream in;
    ObjectOutputStream out;
    public Control() {
        try {
            socket=new Socket("localhost", 9000);
            out=new ObjectOutputStream(socket.getOutputStream());
            in=new ObjectInputStream(socket.getInputStream());
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "nije uspostavljena veza sa serverom, molimo pokusajte kasnije");
            System.exit(0);
            //Logger.getLogger(Control.class.getName()).log(Level.SEVERE, null, ex);
            instance=null;
            socket=null;
            in=null;
            out=null;
        }
    }
    
    public static Control getInstance(){
    if(instance==null)
        instance=new Control();
    return instance;
    }
    
    public Korisnik logIn(String[] imeIPrezime) throws Exception{
        try {
            
       //      System.out.println(imeIPrezime[0]+"  "+imeIPrezime[1]);
            out.writeObject(new Request(Operacija.LogIn, imeIPrezime));
            Response r= (Response)in.readObject();
            if(r.getException()!=null)
                throw r.getException();
            return (Korisnik)r.getResult();
        } catch (IOException ex) {
            Logger.getLogger(Control.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Control.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public boolean regIn(Korisnik k) throws Exception{
    try {
            out.writeObject(new Request(Operacija.Reg, k));
            Response r= (Response)in.readObject();
            if(r.getException()!=null)
                throw r.getException();
            return true;
        } catch (IOException ex) {
            Logger.getLogger(Control.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Control.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    public boolean prijavaPutovanja(Putovanje putovanje) throws Exception{
        try {
            out.writeObject(new Request(Operacija.PrijavaPutovanja, putovanje));
            Response r= (Response)in.readObject();
            if(r.getException()!=null)
                throw r.getException();
            return true;
        } catch (IOException ex) {
            Logger.getLogger(Control.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Control.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
       
    }
    
    public List<Putovanje> listaPutovanja(Korisnik k){
         try {
     out.writeObject(new Request(Operacija.PrikazPrijava, k));
            Response r= (Response)in.readObject();
            if(r.getException()!=null)
                return null;
                //throw r.getException();
            return (LinkedList<Putovanje>)r.getResult();
        } catch (IOException ex) {
            Logger.getLogger(Control.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Control.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
        
        
    }

    public boolean brisanjePutovanja(Putovanje putovanje, Korisnik korisnik) throws Exception {
        try {
            Object[] niz=new Object[2];
            niz[0]=putovanje;
            niz[1]=korisnik;
     out.writeObject(new Request(Operacija.BrisanjePutovanja, niz));
            Response r= (Response)in.readObject();
            if(r.getException()!=null)
                throw r.getException();
        } catch (IOException ex) {
            Logger.getLogger(Control.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Control.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        return true;
    }
}
