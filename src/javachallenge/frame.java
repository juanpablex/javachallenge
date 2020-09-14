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
public class frame {

    private boolean ultimo;
    private LinkedList<shoot> lshoots;
    private int total;
    private int turno;
    private int tiros;

    public frame() {
        turno = 1;
        total = 0;
        lshoots = new LinkedList<>();
        ultimo = false;
        tiros = 0;
    }

    public boolean isUltimo() {
        return ultimo;
    }

    public void setUltimo() {
        if (turno == 10) {
            ultimo = true;
        } else {
            ultimo = false;
        }
    }

    public int getTurno() {
        return turno;
    }

    public void setTurno(int turno) {
        this.turno = turno;
    }

    public LinkedList<shoot> getLshoots() {
        return lshoots;
    }

    public void setShoot(shoot tiro) {
        //this.tiro = tiro;
    }

    public int getTotal() {
        return this.total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public LinkedList<shoot> getFrame() {
        return lshoots;
    }

    public void verificar() {

    }

    public int getTiros() {
        return tiros;
    }

    public void setTiros(String[] shoot) {
        if (shoot.length == 1) {
            tiros = 1;
        } else {
            if (shoot.length == 2) {
                tiros = 2;
            } else {
                tiros = 3;
            }
        }
    }

    public void addShoots(String[] shoot) {
        setTiros(shoot);
        int cont = 1;
        for (String item : shoot) {
            shoot sh = new shoot();
            sh.setTiro(cont);
            sh.setSuma(item.split(" ")[1].trim());
            lshoots.add(sh);
            cont++;
        }
    }

}
