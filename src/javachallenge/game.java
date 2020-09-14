/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javachallenge;

import java.awt.List;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 *
 * @author hp
 */
public class game {

    private LinkedList<jugador> lplayers;
    private String[] moves;
    private String[][] smoves;

    public game(File file) {
        try {
            orderData(file);
        } catch (IOException ex) {
            Logger.getLogger(game.class.getName()).log(Level.SEVERE, null, ex);
        }

        //System.out.println(lplayers.toString());
    }

    private void orderData(File file) throws IOException {
        lplayers = new LinkedList<>();
        BufferedReader br = new BufferedReader(new FileReader(file));

        String st;
        String line = "";
        ArrayList players = new ArrayList<String>();
        //String[] moves;
        String texto2 = "";
        while ((st = br.readLine()) != null) {
            line = st.split(" ")[0] + "\r\n";
            players.add(line);
            texto2 = texto2 + st.split(" ")[0] + " " + st.split(" ")[1] + "\r\n";
        }
        moves = texto2.split("\r\n");

        Collection<String> colPlayers = (Collection<String>) players.stream().distinct().collect(Collectors.toList());//get number of players
        int cont = 0;
        smoves = new String[moves.length][];
        for (String player : colPlayers) {
            String separar = "";
            for (String linea : moves) {
                if (linea.split(" ")[0].trim().equals(player.trim())) {
                    separar = separar + linea + "\r\n";
                }
            }
            String[] aux1 = separar.split("\r\n");
            smoves[cont] = aux1;
            cont++;
        }
        smoves = borrarNulos();//remove nulls
        cargarJugadores(colPlayers);//charge players
        cargar();//charge everything else
    }

    private void cargar() {
        cargarFrames();//charge the frames
        cargarFlags();//charge the flags
        cargarPuntajes();//charge scores
    }

    private String[][] borrarNulos() {
        ArrayList valores = new ArrayList();
        for (String[] item : smoves) {
            if (item != null) {
                valores.add(item);
            }
        }
        String[][] target = (String[][]) valores.toArray(new String[valores.size()][]);
        return target;

    }

    private void cargarJugadores(Collection<String> players) {
        for (String player : players) {
            jugador j = new jugador();
            j.setName(player.split(" ")[0].trim());
            j.setTotal(0);
            lplayers.add(j);
        }
    }

   

    private void cargarFrames() {

        for (jugador player : lplayers) {
            int cont = 0;
            int turno = 1;
            int i = 0;
            while (i < moves.length) {
                boolean insertar = false;
                for (int j = i; j < moves.length; j++) {
                    String p = moves[j].split(" ")[0].trim();
                    if (p.equals(player.getName().trim())) {
                        cont++;
                        insertar = true;
                    } else {
                        break;
                    }
                }
                String[] playeraux = new String[cont];
                frame fr = new frame();
                if (insertar) {
                    playeraux = Arrays.copyOfRange(moves, i, i + cont);
                    fr.addShoots(playeraux);
                    fr.setTurno(turno);
                    player.addFrame(fr);
                    turno++;
                }
                i = i + 1 + cont;
                cont = 0;
            }
        }
    }

    private void cargarFlags() {
        for (jugador player : lplayers) {
            for (frame f : player.getLframes()) {
                int suma = 0;
                for (shoot s : f.getLshoots()) {
                    String valor = s.getValor().trim();
                    if (valor.equals("F")) {
                        s.isFoul(valor);
                    } else {
                        if (f.getTiros() != 1) {
                            if (f.getTurno() == 10 && valor.equals("10")) {
                                s.isStrike(valor);
                            } else {
                                suma = suma + Integer.parseInt(valor.toString());
                                if (s.getTiro() == 1) {
                                    s.isNormal(valor);
                                } else {
                                    if (suma == 10) {
                                        s.isSpare(valor);
                                    } else {
                                        s.isNormal(valor);
                                    }
                                }
                            }
                        } else {
                            if (f.getTiros() == 1) {
                                s.isStrike(valor);
                            }
                        }
                    }
                }
            }
        }
//        for (jugador player : lplayers) {
//            for (frame f : player.getLframes()) {
//                for (shoot s : f.getLshoots()) {
//                    System.out.println("jugador: " + player.getName() + ", frame: " + f.getTurno()
//                            + ", tiro: " + s.getTiro() + ", valor: " + s.getValor()
//                            + ", flag: " + s.getFlag());
//                }
//            }
//        }
    }

    private void cargarPuntajes() {
        for (jugador player : lplayers) {
            int sumaj = 0;
            for (frame f : player.getLframes()) {
                int sumaf = 0;
                if (f.getTurno() != 10) {

                    for (shoot s : f.getLshoots()) {
                        String flag = s.getFlag();
                        switch (flag) {
                            case "X":
                                sumaf = sumaf + Integer.parseInt(s.getValor().toString()) + sumarStrikes(player, f.getTurno());
                                break;
                            case "F":
                                sumaf = sumaf + sumarFoul();
                                break;
                            case "/":
                                sumaf = sumaf + Integer.parseInt(s.getValor().toString()) + sumarSpares(f, player, f.getTurno());
                                break;
                            case "N":
                                sumaf = sumaf + sumarNormal(s.getValor());
                                break;
                            default:
                                break;
                        }

                    }
                    f.setTotal(player.getTotal() + sumaf);
                    player.setTotal(f.getTotal());

                } else {
                    for (shoot s : f.getLshoots()) {
                        String flag = s.getFlag();
                        switch (flag) {
                            case "F":
                                sumaf = sumaf + sumarFoul();
                                break;
                            case "N":
                                if (f.getTiros() == 3) {
                                    String spare = f.getLshoots().get(1).getFlag().trim();
                                    if (spare.equals("/")) {
                                        //no hago nada, espero a la sumatoria de spare
                                    } else {
                                        sumaf = sumaf + sumarNormal(s.getValor());
                                    }
                                } else {

                                    sumaf = sumaf + sumarNormal(s.getValor());
                                }

                                break;
                            case "/":
                                sumaf = sumarSpares(f, player, f.getTurno());
                                break;
                            case "X":
                                sumaf = sumaf + Integer.parseInt(s.getValor().toString());
                                break;
                            default:
                                break;
                        }
                    }
                    f.setTotal(player.getTotal() + sumaf);
                    player.setTotal(f.getTotal());
                }

            }
        }
    }

    public void showScores() {
        System.out.print("Frame     ");
        for (int i = 1; i <= 10; i++) {
            System.out.print(i + "   ");
        }
        System.out.println();
        for (jugador player : lplayers) {
            System.out.println(player.getName());
            System.out.print("Pinfalls  ");
            String total = "";
            for (frame f : player.getLframes()) {
                for (shoot s : f.getLshoots()) {
                    String flag = s.getFlag();
                    String valor = s.getValor();
                    switch (flag) {
                        case "F":
                            if (s.getTiro() == 1) {
                                System.out.print(flag + " ");
                            } else {
                                System.out.print(flag + " ");
                            }
                            break;
                        case "N":
                            if (s.getTiro() == 1) {
                                System.out.print(valor.trim() + " ");
                            } else {
                                System.out.print(valor.trim() + " ");
                            }
                            break;
                        case "/":
                            if (s.getTiro() == 1) {
                                System.out.print(flag.trim() + " ");
                            } else {
                                System.out.print(flag.trim() + " ");
                            }
                            break;
                        case "X":
                            if (f.getTurno() == 10) {
                                System.out.print(flag.trim() + " ");
                            } else {
                                System.out.print("  " + flag.trim() + " ");
                            }
                            break;
                    }
                }
                String result = String.valueOf(f.getTotal()).trim();
                String space = "";
                if (result.length() == 1) {
                    space = "   ";
                } else {
                    if (result.length() == 2) {
                        space = "  ";
                    } else {
                        space = " ";
                    }
                }
                total = total + result + space;

            }
            System.out.println();
            System.out.print("Scores    ");
            System.out.println(total);

        }
    }

    private int sumarStrikes(jugador player, int ini) {

        java.util.List<frame> next = player.getLframes().subList(ini, ini + 1);
        int tiros = next.get(0).getTiros();
        int suma = 0;
        if (tiros == 1) {//otro strike
            if (next.get(0).getTurno() == 10) {//suma 10 + los dos primeros del frame10
                suma = suma + 10;
                int cont = 0;
                for (int i = 0; i < next.size(); i++) {
                    for (int j = 0; j < next.get(i).getLshoots().size(); j++) {
                        if (cont == 2) {
                            break;
                        }
                        String flag = next.get(i).getLshoots().get(j).getFlag().trim();
                        if (flag.equals("F")) {
                            suma = suma + 0;
                        } else {
                            suma = suma + Integer.parseInt(next.get(i).getLshoots().get(j).getValor().toString());
                        }
                        cont++;
                    }
                }
            } else {//suma 10 y entra al siguiente frame

                suma = suma + 10 + sumarStrikes1(player, ini + 1);
            }
        } else {//no es strike y suma el frame obtenido 
            for (int i = 0; i < next.size(); i++) {
                int cont = 0;
                for (int j = 0; j < next.get(i).getLshoots().size(); j++) {
                    if (cont == 2) {
                        break;
                    }
                    String flag = next.get(i).getLshoots().get(j).getFlag().trim();
                    if (flag.equals("F")) {
                        suma = suma + 0;
                    } else {
                        suma = suma + Integer.parseInt(next.get(i).getLshoots().get(j).getValor().toString());
                    }
                    cont++;
                }

            }
        }

        return suma;

    }

    private int sumarStrikes1(jugador player, int ini) {
        java.util.List<frame> nexttwo = player.getLframes().subList(ini, ini + 1);
        for (int i = 0; i < nexttwo.size(); i++) {
            for (int j = 0; j < nexttwo.get(i).getLshoots().size(); j++) {
                String valor = nexttwo.get(i).getLshoots().get(j).getValor().trim();
                if (valor.equals("F")) {
                    return 0;
                } else {
                    return Integer.parseInt(valor.toString());
                }
            }
        }
        return 0;
    }

    private int sumarSpares(frame f, jugador player, int ini) {
        int turno = f.getTurno();
        if (turno == 10) {
            int suma = 0;
            for (shoot s : f.getLshoots()) {
                if (s.getFlag().equals("F")) {
                    suma = suma + 0;
                    return 0;
                } else {
                    suma = suma + Integer.parseInt(s.getValor().toString());
                }
            }
            return suma;
        } else {
            java.util.List<frame> nexttwo = player.getLframes().subList(ini, ini + 1);
            String flag = nexttwo.get(0).getLshoots().get(0).getFlag().trim();
            if (flag.equals("F")) {
                return 0;
            } else {
                return Integer.parseInt(nexttwo.get(0).getLshoots().get(0).getValor().toString());
            }
        }
    }

    private int sumarNormal(String valor) {
        return Integer.parseInt(valor.toString());
    }

    private int sumarFoul() {
        return 0;
    }

}
