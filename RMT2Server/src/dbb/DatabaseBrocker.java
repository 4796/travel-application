/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dbb;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import klase.Korisnik;
import klase.Prevoz;
import klase.Putovanje;
/**
 *
 * @author lazar
 */
public class DatabaseBrocker {
    Connection connection;

    public DatabaseBrocker() {
        connection=null;
        try {
            connection=DriverManager.getConnection("jdbc:mysql://localhost:3306/RMT2", "root", "");
            System.out.println("uspesna konekcija sa bazom");
        } catch (SQLException ex) {
            System.out.println("Baza nije pokrenuta, probajte kasnije");
            System.exit(0);
            //Logger.getLogger(DatabaseBrocker.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public List<Korisnik> listaKorisnika(){
        try {
            List<Korisnik> lista=new LinkedList<>();
            Statement statement=connection.createStatement();
            String kveri="SELECT * FROM korisnik";
            ResultSet rs= statement.executeQuery(kveri);
            while (rs.next()) {

                Korisnik korisnik=new Korisnik(rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8), rs.getDate(9).toLocalDate());
                //System.out.println(korisnik.toString());
                korisnik.setId(rs.getInt(1)); 
                lista.add(korisnik);
            }
//            for (Korisnik korisnik : lista) {
//                System.out.println("aa  "+korisnik);
//            }
            return lista;
        } catch (SQLException ex) {
            System.out.println("nema konekcije sa serverom, mozda zelite da probate ponovo");
            Logger.getLogger(DatabaseBrocker.class.getName()).log(Level.SEVERE, null, ex);
        }
    return null;
    }

    public List<Korisnik> listaStanovnika() {
        try {
            List<Korisnik> lista=new LinkedList<>();
            Statement statement=connection.createStatement();
            String kveri="SELECT * FROM stanovnik";
            System.out.println(kveri);
            ResultSet rs= statement.executeQuery(kveri);

            while (rs.next()) {   
                Korisnik korisnik=new Korisnik("", "", rs.getString(1), rs.getString(2), "", rs.getString(3), rs.getString(4), rs.getDate(6).toLocalDate());
                lista.add(korisnik);
            }
            return lista;
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseBrocker.class.getName()).log(Level.SEVERE, null, ex);
        }
    return null;
    }

    public void unesiKorisnika(Korisnik korisnik) {
        
        try {
           // System.out.println("sad treba update");
            Statement statement=connection.createStatement();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String rodj = korisnik.getDatumRodjenja().format(formatter);
           // String rodj=""+korisnik.getDatumRodjenja().getYear()+"-"+korisnik.getDatumRodjenja().getMonthValue()+"-"+korisnik.getDatumRodjenja().getDayOfMonth();
            String kveri="INSERT INTO korisnik(username, PASSWORD, ime, prezime, email, jmbg, pasos, datumRodjenja) VALUES('"+korisnik.getUsername()+"', '"+korisnik.getPassword()+"', '"+korisnik.getIme()+"', '"+korisnik.getPrezime()+"', '"+korisnik.getEmail()+"', "+korisnik.getJMBG()+", "+korisnik.getPasos()+", '"+rodj+"')";
            System.out.println(kveri);
            statement.executeUpdate(kveri);
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseBrocker.class.getName()).log(Level.SEVERE, null, ex);
        }catch (Exception ex){
        Logger.getLogger(DatabaseBrocker.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public List<Putovanje> listaPutovanja(int korisnik) {
        try {
            List<Putovanje> lista=new LinkedList<>();
            Statement statement=connection.createStatement();
            //sva putovanja ali bez zemalja
            String kveri="SELECT * FROM putovanje  WHERE korisnik="+korisnik;

          //  System.out.println(kveri);
            ResultSet rs= statement.executeQuery(kveri);

            while (rs.next()) {   
                Putovanje putovanje=new Putovanje(rs.getInt(1), rs.getDate(3).toLocalDate(), rs.getDate(4).toLocalDate(), Prevoz.valueOf(rs.getString(5)) , rs.getDate(6).toLocalDate(), new LinkedList<>());
                lista.add(putovanje);
          //      System.out.println(putovanje);
            }
            //dodavanje zemanja
            for (Putovanje putovanje : lista) {
                kveri="SELECT zemlja FROM putovanje pu JOIN posetaZemlje pz ON pu.id=pz.putovanje WHERE pu.korisnik="+korisnik+" AND pu.id="+putovanje.getId();
         //   System.out.println(kveri);
            rs= statement.executeQuery(kveri);
                while (rs.next()) {                    
                    putovanje.getZemlje().add(rs.getString(1));
                }
            }

            return lista;
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseBrocker.class.getName()).log(Level.SEVERE, null, ex);
        }
    return null;
        

   }
    
    public void unesiPrijavu(Putovanje putovanje, int korisnik) {
        //treba dve tabele da se promene, za zemlje i za prijave 
        try {
           // System.out.println("sad treba update");
            Statement statement=connection.createStatement();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String datU = putovanje.getDatumUlaska().format(formatter);
            String datI = putovanje.getDatumIzlaska().format(formatter);
            String datP = putovanje.getDatumPrijave().format(formatter);
           // String rodj=""+korisnik.getDatumRodjenja().getYear()+"-"+korisnik.getDatumRodjenja().getMonthValue()+"-"+korisnik.getDatumRodjenja().getDayOfMonth();
            String kveri="INSERT INTO putovanje(korisnik, datumUlaska, datumIzlaska, prevoz, datumPrijave) VALUES("+korisnik+", '"+datU+"', '"+datI+"', '"+putovanje.getPrevoz()+"', '"+datP+"')";
          //  System.out.println(kveri);
            statement.executeUpdate(kveri, Statement.RETURN_GENERATED_KEYS);
            ResultSet rs = statement.getGeneratedKeys();
            rs.next();
            int pId=rs.getInt(1);
            //treba mi id putovanja
            for (String string : putovanje.getZemlje()) {
            kveri="INSERT INTO posetazemlje(putovanje, zemlja) VALUES("+pId+", '"+string+"')";
           // System.out.println(kveri);
            statement.executeUpdate(kveri);
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseBrocker.class.getName()).log(Level.SEVERE, null, ex);
        }catch (Exception ex){
        Logger.getLogger(DatabaseBrocker.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void brisanjePrijave(Putovanje putovanje, Korisnik korisnik) {
        //uvek se brise samo jedna zemlja
    //ako ima samo jednu zemlju, onda treba da se obrise stara prijava u potpunosti
    //ako ima vise zemalja u bazi, onda treba da se izmeni samo koje zemlje ima
    
    try {
           //delete iz baze posetazemlje
            Statement statement=connection.createStatement();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String datU = putovanje.getDatumUlaska().format(formatter);
            String datI = putovanje.getDatumIzlaska().format(formatter);
            String datP = putovanje.getDatumPrijave().format(formatter);
           //da nadjemo koji je id putovanja
            String kveri="SELECT putovanje FROM posetazemlje WHERE zemlja='"+putovanje.getZemlje().get(0)+"' AND putovanje IN(\n" +
"SELECT id FROM putovanje WHERE korisnik="+korisnik.getId()+" AND datumUlaska='"+datU+"' AND datumIzlaska='"+datI+"' \n" +
"AND datumPrijave='"+datP+"'\n" +")";
        //    System.out.println(kveri);
            
            ResultSet rs = statement.executeQuery(kveri);
            rs.next();
            int pId=rs.getInt(1);
            
           // sada mogu da saznam koje su sve drzave vezane za tu prijavu, u listi je i ono sto cu obrisati
           
            statement=connection.createStatement();
            kveri="SELECT zemlja FROM posetazemlje WHERE putovanje="+pId;
        //    System.out.println(kveri);
            rs = statement.executeQuery(kveri);
            List<String> l=new LinkedList<>();
            while (rs.next()) {
            l.add(rs.getString(1));
        }
            if(l.size()==0)
                throw new Exception("Greska pri brisanju, nema zemalja za posetu");
            //bez obzira na bilo sta, sad se brise ta jedna iz zemalja
            statement=connection.createStatement();
            kveri="DELETE FROM posetazemlje WHERE zemlja='"+putovanje.getZemlje().get(0)+"' AND putovanje="+pId;
          //  System.out.println(kveri);
            statement.executeUpdate(kveri);
            
            
            //sada na osnovu broja zemalja, gledam da li treba da brisem i prijavu celu
            if(l.size()==1){
            statement=connection.createStatement();
            kveri="DELETE FROM putovanje WHERE id="+pId;
         //   System.out.println(kveri);
            statement.executeUpdate(kveri);
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseBrocker.class.getName()).log(Level.SEVERE, null, ex);
        }catch (Exception ex){
        Logger.getLogger(DatabaseBrocker.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    
    }
    
}
