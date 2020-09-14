/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javachallenge;

import java.awt.List;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javachallenge.game;
import javachallenge.jugador;

/**
 *
 * @author hp
 */
public class Javachallenge {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {
        // TODO code application logic here
        String perfectScore = "perfectScore.txt";
        String zeroScore = "zeroScore.txt";
        String scoresFile = "scoresFile.txt";
        File file = new File(scoresFile);
        game g = new game(file);
        g.showScores();

    }

    public static void dibujarScores(String[] scores, Collection<String> cant) {
        String dibujo = "Frame      ";
        for (int i = 0; i < 10; i++) {
            dibujo = dibujo + (i + 1) + "   ";

        }
        dibujo = dibujo;
        System.out.print(dibujo.trim());
        //String jugadores = "";
        //String scor = "";
        boolean strike = false;
        boolean spare = false;
        int turno = 1;
        int suma = 0;
        int total = 0;
        byte tiro = 1;
        for (String item : cant) {
            System.out.print("\r\n" + item.trim() + "\r\nPinfalls   ");
            String jugadores = "";
            String puntaje = "";
            for (String item2 : scores) {
                String fila = item2.split(" ")[0].trim();
                String scor = item2.split(" ")[1].trim();

                //System.out.println("fila: " + fila + ", score: " + scor + ", item: :" + item);
                if (fila.equals(item.trim())) {
                    //puntaje = puntaje + scor + "    ";
                    if (tiro == 1) {
                        if (turno == 10) {
                            if (scor.equals("10")) {
                                System.out.print(" " + "X  ");
                                tiro = 1;
                                strike = true;
                                spare = false;
                            } else {
                                System.out.print(" " + scor);
                                tiro = 2;
                                strike = false;
                                spare = false;
                            }
                        } else {
                            if (scor.equals("10")) {
                                turno++;
                                tiro = 1;
                                strike = true;
                                spare = false;
                                System.out.print("  " + "X ");
                            } else {
                                System.out.print(scor + " ");
                            }
                        }
                        suma = suma + Integer.parseInt(scor.toString());
                    } else {//tiro == 1
                        if (tiro == 2) {
                            if (scor.equals(("10"))) {

                            }
                            suma = suma + Integer.parseInt(scor.toString());
                            if (suma == 10) {
                                spare = true;
                                strike = false;
                                turno++;
                                tiro = 1;
                            } else {
                                total = total + suma;
                                suma = 0;

                            }
                        } else {//tiro == 3
                            if (scor.equals("10")) {

                            } else {

                            }
                        }

                    }

                }
            }
            jugadores += "\r\n" + "Score\r\n";
            total = 0;
            turno = 1;
            tiro = 1;
            strike = false;
            spare = false;
            suma = 0;
        }
        //System.out.println(dibujo + jugadores);
        //System.out.print(dibujo + jugadores);
        //Stream.concat(lista.stream(), lista2.stream()).forEach(element -> mostrar(element));
        //System.out.print(lista);
    }

    public static int sumar() {
        return 0;
    }

    public static void mostrar(String element) {
        System.out.print(element);
    }
}
