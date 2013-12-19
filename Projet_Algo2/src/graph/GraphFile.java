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
        graph.nbSommet =nbSommet;
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
}
