package core;

import graph.Graph;
import graph.GraphFile;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {
    public static void main(String[] args){
        String nameFile = args[0];
        
        Graph graph = new Graph();
        try {
            graph = GraphFile.load(nameFile);
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
            //System.out.println("CM: " + graph.hasCycles("CM"));
            System.out.println("c$: " + graph.hasCycles("C$"));
            System.out.println("CI: " + graph.hasCycles("CI"));
            System.out.println("CM: " + graph.hasCycles("CM"));
            System.out.println("CePha: " + graph.hasCycles("CePha"));
            System.out.println("cP: " + graph.hasCycles("CP"));
            System.out.println("CI: " + graph.hasCycles("CI"));
            //System.out.println("CELB: " + graph.hasCycles("CELB"));
            //System.out.println("CPS: " + graph.hasCycles("CPS"));
            
            ArrayList<Graph> cycles = new ArrayList<>();
            graph.getCycles("CePha", cycles);
            for(Graph cycle : cycles){
                System.out.println("Nouveau cycle: ");
                cycle.profondeur(cycle.getFirstNode());
            }
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        graph.unmark();
    }
}
