package graph;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.LinkedList;

public class GraphFile {

    public static Graph load(String nameFile) throws FileNotFoundException, IOException, Exception{
        Graph graph = new Graph();
        LinkedList<String> data = new LinkedList<>();
        FileInputStream fis = new FileInputStream(nameFile);
        DataInputStream dis = new DataInputStream(fis);
        BufferedReader br = new BufferedReader(new InputStreamReader(dis));
        String line = null;
        while((line = br.readLine()) != null){
            data.add(line);
        }
        br.close();
        
        if(data.size() == 0){
            throw new Exception("Fichier vide");
        }
        
        int nbSommet = Integer.parseInt(data.removeFirst());
        for(int i=0; i<nbSommet; i++){
            String[] sommet = data.get(i).split(" ");
            String nameSommet = sommet[0];
            int valueSommet = Integer.parseInt(sommet[1]);
            graph.addNode(String.valueOf(i), nameSommet, valueSommet);
        }
        for(int i=0; i<nbSommet; i++){
            data.removeFirst();
        }
        for(String arc : data){
            String[] split = arc.split(" ");
            String nameSrc = split[0];
            String nameDest = split[1];
            int value = Integer.parseInt(split[2]);
            try {
                graph.addArc(nameSrc, nameDest, value);
            } catch (Exception ex) {
                throw new Exception("Fichier incorrect");
            }
        }
        data.clear();
        return graph;
    }
    
    public static void save(Graph graph,String namefile) throws IOException, Exception{
        LinkedList<String> data = new LinkedList<>();
        // First ligne nb sommet
        data.add(getNbSommet(graph)+"");
        
        //Recuperer tout les noeuds
        Node node = graph.getFirstNode(); 
        while(node != null){
            data.add(node.getName()+" "+node.getValue());
            node = node.getNextNode();
        }
        
        //recuperer tout les arcs
        pronfondeur(graph.getFirstNode(),data);
        
        //Ecrire le fichier
        try{
            PrintWriter out  = new PrintWriter(new FileWriter(namefile+"_DettesRemb.gv"));
            for (String line : data){
                out.println(line);
            }
            out.close();
        }
        catch(Exception e){
            throw new Exception("Erreur d'écriture");
        }
    }
    
    public static void pronfondeur(Node node,LinkedList<String> data) throws Exception {
        node.setMarque(true);
        System.out.println(node.getName());
        Arc arc = node.getArc();
        while(arc != null){
            if(arc.getDestNode() != null && !arc.getDestNode().isMarque()){
                pronfondeur(arc.getDestNode(),data);
            }
            data.add(arc.getOriginNode().getName()+" "+arc.getDestNode().getName()+" "+arc.getLabel());
            arc = arc.getNextArc();
        }
    }
    
    public static int getNbSommet(Graph graph) {
        Node node = graph.getFirstNode();
        int cptNode = 0;
        
        while(node != null){
            node = node.getNextNode();
            cptNode++;
        }
        return cptNode;
    }
}
