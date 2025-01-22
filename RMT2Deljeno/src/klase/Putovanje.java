/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package klase;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author lazar
 */
public class Putovanje implements Serializable{
    int id;
    LocalDate datumUlaska;
    LocalDate datumIzlaska;
    Prevoz prevoz;
    LocalDate datumPrijave;
    List<String> zemlje;

    public Putovanje(int id, LocalDate datumUlaska, LocalDate datumIzlaska, Prevoz prevoz, LocalDate datumPrijave, List<String> zemlje) {
        this.id = id;
        this.datumUlaska = datumUlaska;
        this.datumIzlaska = datumIzlaska;
        this.prevoz = prevoz;
        this.datumPrijave = datumPrijave;
        this.zemlje = zemlje;
    }

    @Override
    public String toString() {
        return "Putovanje{" + "id=" + id + ", datumUlaska=" + datumUlaska + ", datumIzlaska=" + datumIzlaska + ", prevoz=" + prevoz + ", datumPrijave=" + datumPrijave + ", zemlje=" + zemlje + '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getDatumUlaska() {
        return datumUlaska;
    }

    public void setDatumUlaska(LocalDate datumUlaska) {
        this.datumUlaska = datumUlaska;
    }

    public LocalDate getDatumIzlaska() {
        return datumIzlaska;
    }

    public void setDatumIzlaska(LocalDate datumIzlaska) {
        this.datumIzlaska = datumIzlaska;
    }

    public Prevoz getPrevoz() {
        return prevoz;
    }

    public void setPrevoz(Prevoz prevoz) {
        this.prevoz = prevoz;
    }

    public LocalDate getDatumPrijave() {
        return datumPrijave;
    }

    public void setDatumPrijave(LocalDate datumPrijave) {
        this.datumPrijave = datumPrijave;
    }
   

    public List<String> getZemlje() {
        return zemlje;
    }

    public void setZemlje(List<String> zemlje) {
        this.zemlje = zemlje;
    }
    
}
