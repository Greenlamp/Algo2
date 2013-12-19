package core;

import graph.Graph;
import graph.GraphFile;
import graph.Node;
import graph.Vec;
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
        //String nameFile = "graphe4";
        //String nameFile = "graphe5";
        //String nameFile = "graphe6";
        String nameFile = "example1";
        //String nameFile = "example2";
        //String nameFile = "example3";
        //String nameFile = "example4";
        
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
        
        try {            
            Node node = graph.getFirstNode();
            while(node != null){
                boolean cont = true;
                while(cont){
                    Vec vec = new Vec();
                    cont = graph.getCycles(node, vec);
                }
                node = node.getNextNode();
            }
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        graph.toPng("outSimpl");
    }
}
