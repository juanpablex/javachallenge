/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javachallenge;

/**
 *
 * @author hp
 */
public class shoot {

    private boolean normal;
    private boolean foul;
    private boolean strike;
    private boolean spare;
    private String flag;
    private int tiro;
    private int score = 0;
    private String valor;

    public shoot() {
        tiro = 0;
        normal = false;
        foul = false;
        strike = false;
        spare = false;
        flag = "";
        score = 0;
        valor = "";
    }

    public void isNormal(String score) {

        setStrike(false);
        setSpare(false);
        setFoul(false);
        setNormal(true);

    }

    public void setNormal(boolean normal) {
        this.normal = normal;
        if (this.normal) {
            setFlag("N");
        }
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public void isFoul(String score) {

        setStrike(false);
        setSpare(false);
        setFoul(true);
        setNormal(false);

    }

    public void setFoul(boolean foul) {
        this.foul = foul;
        if (this.foul) {
            setFlag("F");
        }
    }

    public void isStrike(String score) {

        setStrike(true);
        setSpare(false);
        setFoul(false);
        setNormal(false);

    }

    public void setStrike(boolean strike) {
        this.strike = strike;
        if (this.strike) {
            setFlag("X");
        }
    }

    public void isSpare(String value) {

        setSpare(true);
        setStrike(false);
        setFoul(false);
        setNormal(false);

    }

    public void setSpare(boolean spare) {
        this.spare = spare;
        if (this.spare) {
            setFlag("/");
        }
    }

    public void setShootType(String valor) {
        isNormal(valor);

    }

    public int getSuma() {
        return this.score;
    }

    public void setSuma(String valor) {
        setValor(valor);
        if (valor.equals("F")) {
            score = 0;
        } else {
            score = Integer.parseInt(valor.toString());
        }
    }

    public void setSumaInt(int valor) {
        this.score = valor;
    }

    public int getTiro() {
        return tiro;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public void setTiro(int tiro) {
        this.tiro = tiro;
    }

    public void verificar(int score) {

    }
}
