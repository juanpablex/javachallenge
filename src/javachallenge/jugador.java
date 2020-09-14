/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javachallenge;

import java.util.LinkedList;

/**
 *
 * @author hp
 */
public class jugador {

    private String name;
    private int total;
    private LinkedList<frame> lframes;

    public jugador() {
        name = "";
        total = 0;
        lframes = new LinkedList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public LinkedList<frame> getLframes() {
        return lframes;
    }

    public void addFrame(frame fr) {
        lframes.add(fr);
    }

  

    @Override
    public String toString() {
        return name + " - " + total;
    }

}
