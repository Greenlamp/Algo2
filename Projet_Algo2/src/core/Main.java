package core;

import graph.Graph;
import graph.GraphFile;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {
    public static void main(String[] args){
        //String nameFile = args[0];
        //String nameFile = "graphe_double_noCycle";
        //String nameFile = "graphe_double_Cycle";
        //String nameFile = "graphe0";
        //String nameFile = "graphe1";
        //String nameFile = "graphe2";
        //String nameFile = "graphe3";
        String nameFile = "graphe4";
        
        Graph graph = new Graph();
        try {
            graph = GraphFile.load(nameFile + ".txt");
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }        
        graph.toPng("out");
        
        int degree = 0;
        try {
            //graph.pronfondeur(graph.getFirstNode());
            //graph.unmark();
            //degree = graph.degre("CePha");
            //System.out.println("Degré: " + degree);
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            System.out.println("CM: " + graph.hasCycles("CM"));
            //System.out.println("CD: " + graph.hasCycles("CD"));
            System.out.println("CP: " + graph.hasCycles("CP"));
            System.out.println("CePha: " + graph.hasCycles("CePha"));
            System.out.println("C$: " + graph.hasCycles("C$"));
            System.out.println("CIG: " + graph.hasCycles("CIG"));
            System.out.println("CPSY: " + graph.hasCycles("CPSY"));
            //System.out.println("CPS: " + graph.possedeCycle("CPS"));
            //System.out.println("CL: " + graph.possedeCycle("CL"));
            //System.out.println("CJ: " + graph.possedeCycle("CJ"));
            //System.out.println("CK: " + graph.possedeCycle("CK"));
            
           /* ArrayList<Graph> cycles = new ArrayList<>();
            graph.getCycles("CePha", cycles);
            for(Graph cycle : cycles){
                System.out.println("Nouveau cycle: ");
                cycle.profondeur(cycle.getFirstNode());
            }*/
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        graph.unmark();
    }
}
