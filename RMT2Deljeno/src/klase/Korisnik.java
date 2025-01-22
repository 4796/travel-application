/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package klase;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author lazar
 */
public class Korisnik implements Serializable{
    int id;
    String username;
    String password;
    String ime;
    String prezime;
    String email;
    String JMBG;
    String pasos;
    LocalDate datumRodjenja;
    List<Putovanje> putovanja;
    
    @Override
    public int hashCode() {
        int hash = 7;
        return hash;
    }

    public List<Putovanje> getPutovanja() {
        return putovanja;
    }

    public void setPutovanja(List<Putovanje> putovanja) {
        this.putovanja = putovanja;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Korisnik other = (Korisnik) obj;
        if (!Objects.equals(this.username, other.username)) {
            return false;
        }
        return Objects.equals(this.password, other.password);
    }

    public LocalDate getDatumRodjenja() {
        return datumRodjenja;
    }

    public void setDatumRodjenja(LocalDate datumRodjenja) {
        this.datumRodjenja = datumRodjenja;
    }

    

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Korisnik{" + "username=" + username + ", password=" + password + ", ime=" + ime + ", prezime=" + prezime + ", email=" + email + ", JMBG=" + JMBG + ", pasos=" + pasos +"  rodj "+ datumRodjenja+'}';
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public String getPrezime() {
        return prezime;
    }

    public void setPrezime(String prezime) {
        this.prezime = prezime;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getJMBG() {
        return JMBG;
    }

    public void setJMBG(String JMBG) {
        this.JMBG = JMBG;
    }

    public String getPasos() {
        return pasos;
    }

    public void setPasos(String pasos) {
        this.pasos = pasos;
    }

    public Korisnik(String username, String password, String ime, String prezime, String email, String JMBG, String pasos, LocalDate rodj) {
        this.username = username;
        this.password = password;
        this.ime = ime;
        this.prezime = prezime;
        this.email = email;
        this.JMBG = JMBG;
        this.pasos = pasos;
        this.datumRodjenja=rodj;
    }
}
