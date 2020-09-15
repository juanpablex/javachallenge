/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javachallenge;


import java.io.File;

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

}
