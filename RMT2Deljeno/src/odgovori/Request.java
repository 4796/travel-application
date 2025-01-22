/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package odgovori;

import java.io.Serializable;
import klase.Operacija;

/**
 *
 * @author lazar
 */
public class Request implements Serializable{
    Operacija operacija;
    Object argumenti;

    public Request(Operacija operacija, Object argumenti) {
        this.operacija = operacija;
        this.argumenti = argumenti;
    }

    public Operacija getOperacija() {
        return operacija;
    }

    public void setOperacija(Operacija operacija) {
        this.operacija = operacija;
    }

    public Object getArgumenti() {
        return argumenti;
    }

    public void setArgumenti(Object argumenti) {
        this.argumenti = argumenti;
    }
    
}
