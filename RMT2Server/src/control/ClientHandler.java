/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package control;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import klase.Korisnik;
import klase.Operacija;
import klase.Putovanje;
import odgovori.Request;
import odgovori.Response;

/**
 *
 * @author lazar
 */
public class ClientHandler extends Thread{
    Korisnik korisnik;
    Socket socket;
    ObjectInputStream in;
    ObjectOutputStream out;
    public ClientHandler(Socket s) {
        try {
            socket=s;
            out=new ObjectOutputStream(socket.getOutputStream());
            in=new ObjectInputStream(socket.getInputStream());
            
            
            
        } catch (IOException ex) {
            Logger.getLogger(ClientHandler.class.getName()).log(Level.SEVERE, null, ex);
             socket=null;
            in=null;
            out=null;
        }
    }

    @Override
    public void run() {

            while (true) {   

            try {
                Request req= (Request)in.readObject();

                switch (req.getOperacija()) {
                    case Operacija.LogIn:   //ako ne nadje vraca null i null, ako je exc null i ovaj nije null onda je 
                                          //okej, a ako je exc ne null onda je exc
                        try {
                            korisnik=logIn(req.getArgumenti());
                            out.writeObject(new Response(korisnik, null));
                        } catch (Exception e) {
                            out.writeObject(new Response(null, e));
                        }
                        break;
                    case Operacija.Reg:
                        try {
                            if(registracija(req.getArgumenti()))
                                out.writeObject(new Response(null, null));  //ako dodje do greske onda salje neki exc

                        } catch (Exception e) {
                            out.writeObject(new Response(null, e));
                        }
                        break;
                    case Operacija.PrijavaPutovanja:   //ako je bilo kakav problem vraca ex
                                          
                        try {
                            out.writeObject(new Response(prijavaPutovanja((Putovanje)req.getArgumenti()), null));
                        } catch (Exception e) {
                            out.writeObject(new Response(null, e));
                            System.out.println(""+e.getMessage());
                        }
                        break;
                    case Operacija.PrikazPrijava:   //ako je greska onda null za result, exc je nebitan
                                          
                        try {
                            List<Putovanje> putovanja=prikazPrijava((Korisnik)req.getArgumenti());
                            out.writeObject(new Response(putovanja, null));
                        } catch (Exception e) {
                            out.writeObject(new Response(null, e));
                        }
                        break;
                    case Operacija.BrisanjePutovanja:   
                                          
                        try {
                            brisanjePrijave(req.getArgumenti());
                            out.writeObject(new Response(null, null));
                        } catch (Exception e) {
                            out.writeObject(new Response(null, e));
                            e.printStackTrace();
                        }
                        break;
                    default:
                        System.out.println("GRESKA???");
                }
            } catch (IOException ex) {
                System.out.println("izasao korisnik");
                break;
            } catch (ClassNotFoundException ex) {
                System.out.println("izasao korisnik");
                break;
            }
        }
        } 
        
    

    private Korisnik logIn(Object argumenti) {
        String user=(((String[]) argumenti)[0]).strip();
        String pass=(((String[]) argumenti)[1]).strip();
        Korisnik k;
        Control control=Control.getInstance();
      //  System.out.println("primljeno"+user+" "+pass);
        List<Korisnik> lista= control.listaKorisnika();
        for (Korisnik korisnik1 : lista) {
           // System.out.println("iz liste"+ korisnik1);
            if(korisnik1!=null && korisnik1.getUsername().equals(user) && korisnik1.getPassword().equals(pass)){
            korisnik= korisnik1;

            return korisnik1;
            
            }
        }
        return null;
    }

    private boolean registracija(Object argumenti) throws Exception{
        //ako je true onda vraca true, a ako je false, onda baca izuzetak
        if(argumenti==null)
            throw new Exception("Nema unosa za registraciju.");
        Korisnik korisnik=(Korisnik) argumenti;
        if(korisnik.getIme().length()==0)
            throw new Exception("Unesite ime");
        if(korisnik.getPrezime().length()==0)
            throw new Exception("Unesite prezime");
        if(korisnik.getUsername().length()==0)
            throw new Exception("Unesite username");
        if(korisnik.getPassword().length()<2)
            throw new Exception("Unesite duzi password, minimum 2 karaktera");
        if(korisnik.getEmail().length()==0)
            throw new Exception("Unesite email");
        if(korisnik.getJMBG().length()==0)
            throw new Exception("Unesite JMBG");
        if(korisnik.getPasos().length()==0)
            throw new Exception("Unesite pasoš");
        if(!isNumeric(korisnik.getJMBG()))
            throw new Exception("Pogresan format za JMBG");
        if(!isNumeric(korisnik.getJMBG()))
            throw new Exception("Pogresan format za pasoš");
        Control control=Control.getInstance();
        //provera medju korisnicima
        List<Korisnik> lista= control.listaKorisnika();
        for (Korisnik korisnik1 : lista) {
            if(korisnik1==null)
                continue;
            if(korisnik1.getUsername().equals(korisnik.getUsername()) || korisnik1.getJMBG().equals(korisnik.getJMBG()) || korisnik1.getPasos().equals(korisnik.getPasos())){
            
                throw new Exception("Korisnik sa ovim podacima vec ima nalog");
            
            }
        }
        
        
        //provera medju stanovnicima
        List<Korisnik> listaStanovnika= control.listaStanovnika();
        boolean postoji=false;
        for (Korisnik korisnik1 : listaStanovnika) {
            if(korisnik1==null)
                continue;
            //provera da li odgovara nekom stanovniku, da li stanovnik postoji, i provera 
            //sa ili gde gleda da li koristi tudji jmbg sa drugim imenom
            if(korisnik1.getJMBG().equals(korisnik.getJMBG()) && korisnik1.getPasos().equals(korisnik.getPasos()) && korisnik1.getIme().equals(korisnik.getIme()) && korisnik1.getPrezime().equals(korisnik.getPrezime())){
                postoji=true;
                korisnik.setDatumRodjenja(korisnik1.getDatumRodjenja());
            }
        }
        if(!postoji)
            throw new Exception("Ne postoji osoba kojoj odgovaraju takvi podaci");
        //sad treba da se kreira korisnik u bazi na osnovu korisnik 
        control.unesiKorisnika(korisnik);
        //System.out.println("salje true fja");
        return true;
    }

    private boolean isNumeric(String str) {
        for (char c : str.toCharArray()) {
        if (!Character.isDigit(c)) return false;
    }
    return true;
    }
    
    private boolean prijavaPutovanja(Putovanje putovanje) throws Exception{
    //za bilo kakav problem da baci exception
        if(putovanje.getDatumPrijave().isAfter(putovanje.getDatumUlaska()))
            throw new Exception("Datum ulaska mora biti posle danasnjeg dana");
        if(putovanje.getDatumUlaska().isAfter(putovanje.getDatumIzlaska()))
            throw new Exception("Datum ulaska mora biti pre datuma izlaska");
        if(putovanje.getDatumUlaska().plusDays(90).isBefore(putovanje.getDatumIzlaska()))
            throw new Exception("Period mora biti kraci od 90 dana");
        Control control=Control.getInstance();
        List<Putovanje> listaPutovanja=control.listaPutovanja(korisnik.getId());
        for (Putovanje putovanje1 : listaPutovanja) {//ako se poklapa neki period i neka zemlja onda baca izuzetak sa porukom
            if (putovanje.getDatumUlaska().isAfter(putovanje1.getDatumIzlaska()) || putovanje.getDatumIzlaska().isBefore(putovanje1.getDatumUlaska())) {
                //nemaju zajednickih dana
            }else{
            //ako se neke zemlje podudaraju onda treba da baci izuzetak
                for (String string : putovanje1.getZemlje()) {
                    for (String string1 : putovanje.getZemlje()) {
                        if(string.equals(string1))
                            throw new Exception("Vec postoji prijava u tom periodu za neku od unetih drzava");
                    }
                }
            }
        }
        //sada posto su ispostovani svi uslovi, treba da se upise prijava u bazu
        control.unesiPrijavu(putovanje, korisnik.getId());
        return true;
    }

    private List<Putovanje> prikazPrijava(Korisnik korisnik) {
         Control control=Control.getInstance();
         return control.listaPutovanja(korisnik.getId());
    
    }

    private void brisanjePrijave(Object argumenti) throws Exception{
       //baci izuzetak bilo kakav ako dodje do greske, tekst nije bitan, ovde ispise u konzoli
        //System.out.println("ovde radi");
       Object[] niz=(Object[])argumenti;
       Putovanje putovanje=(Putovanje)niz[0];
       Korisnik korisnik=(Korisnik)niz[1];
       Control control= Control.getInstance();
       control.brisanjePrijave(putovanje, korisnik);
          
       
       
       
    }
    
} 
