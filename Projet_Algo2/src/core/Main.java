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
        nameFile = nameFile.split(".txt")[0];
        System.out.println("Cycles supprimés:");
        System.out.println("");
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
        graph.toTxt(nameFile,"NoCycles");

        System.out.println("Fichier "+nameFile+"NoCycles.gv contient la situation sans cycles.");
        System.out.println("Utilisez la commande : <Commande graphviz pour créer le fichier png>");
        System.out.println("pour créer l'image.");
        
        System.out.println("");
        System.out.println("Ordre des remboursements :");
        System.out.println("");
        
        graph.rembourser();
        graph.toTxt(nameFile,"Remb");
        
        System.out.println("");
        System.out.println("Fichier "+nameFile+"Remb.gv contient la situation actuelle.");
        System.out.println("Utilisez la commande : <Commande graphviz pour créer le fichier png>");
        System.out.println("pour créer l’image.");
    }
}
