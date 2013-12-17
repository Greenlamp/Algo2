package graph;

import graphviz.GraphViz;
import java.io.File;
import java.util.ArrayList;

public class Graph {
    private Node firstNode;
    public Graph(){
        firstNode = null;
    }

    public void addNode(String id, String name, int value) {
        Node newNode = new Node();
        newNode.setName(name);
        newNode.setValue(value);
        newNode.setId(id);
        if(this.getFirstNode() == null){
            this.setFirstNode(newNode);
        }else{
            Node node = this.getFirstNode();
            while(node.getNextNode() != null){
                node = node.getNextNode();
            }
            newNode.setPrevNode(node);
            node.setNextNode(newNode);
        }
    }

    public void addArc(String nameSrc, String nameDest, int value) throws Exception {
        Node node = this.getFirstNode();
        Node nodeSrc = null;
        Node nodeDest = null;
        while(node != null || (nodeSrc == null && nodeDest == null)){
            if(nodeSrc == null && node.getName().equals(nameSrc)){
                nodeSrc = node;
            }else if(node.getName().equals(nameDest)){
                nodeDest = node;
            }
            node = node.getNextNode();
        }
        if(nodeSrc != null && nodeDest != null){
            Arc newArc = new Arc();
            newArc.setOriginNode(nodeSrc);
            newArc.setDestNode(nodeDest);
            newArc.setLabel(String.valueOf(value));
            Arc arcSrc = nodeSrc.getArc();
            if(arcSrc == null){
                nodeSrc.setArc(newArc);
            }else{
                while(arcSrc.getNextArc() != null){
                    arcSrc = arcSrc.getNextArc();
                }
                arcSrc.setNextArc(newArc);
            }
        }else{
            if(nodeSrc == null && nodeDest == null){
                throw new Exception("Node source et node destination introuvable");
            }else if(nodeSrc == null){
                throw new Exception("Node source introuvable");
            }else if(nodeDest == null){
                throw new Exception("Node destination introuvable");
            }
        }
    }

    public void toPng(String nameFile) {
        GraphViz gv = new GraphViz();
        gv.addln(gv.start_digraph());
        gv.addln("node [color=orange, style=filled]");
        Node node = this.getFirstNode();
        while(node != null){
            Arc arc = node.getArc();
            while(arc != null){
                gv.addln(arc.getOriginNode().getId() + " -> " + arc.getDestNode().getId() + "[label="+arc.getLabel()+"]");
                arc = arc.getNextArc();
            }
            node = node.getNextNode();
        }
        node = this.getFirstNode();
        while(node != null){
            gv.addln(node.getId() + " [label = \""+node.getName()+"\n"+node.getValue()+"\"]");
            node = node.getNextNode();
        }
        gv.addln(gv.end_graph());
        System.out.println(gv.getDotSource());
        String type = "png";
        File out = new File(nameFile + "." + type);
        byte[] graph = gv.getGraph(gv.getDotSource(), type);
        gv.writeGraphToFile(gv.getGraph(gv.getDotSource(), type), out );
    }

    public void profondeur(Node node) throws Exception {
        node.setMarque(true);
        Arc arc = node.getArc();
        System.out.println(node.getName());
        while(arc != null){
            if(arc.getDestNode() != null && !arc.getDestNode().isMarque()){
                profondeur(arc.getDestNode());
            }
            arc = arc.getNextArc();
        }
    }

    public int degre(String nom) throws Exception {
        int cpt = 0;
        boolean found = false;
        Node node = this.getFirstNode();
        while(node != null && !found){
            if(node.getName().equals(nom)){
                found = true;
                Arc arc = node.getArc();
                while(arc != null){
                    cpt++;
                    arc = arc.getNextArc();
                }
            }
            node = node.getNextNode();
        }
        if(!found){
            throw new Exception("Sommet introuvable");
        }
        return cpt;
    }

    public Node getFirstNode() {
        return firstNode;
    }

    public void setFirstNode(Node firstNode) {
        this.firstNode = firstNode;
    }

    public void unmark() {
        Node node = this.firstNode;
        while(node != null){
            node.setMarque(false);
            node = node.getNextNode();
        }
    } 

    public boolean hasCycle(String nameNode) throws Exception{
        Node node = this.firstNode;
        while(node != null && !node.getName().equals(nameNode)){
            node = node.getNextNode();
        }
        if(node == null){
            unmark();
            throw new Exception("Sommet introuvable");
        }else{
            node.setMarque(true);
            Arc arc = node.getArc();
            while(arc != null){
                Arc nextArc = arc;
                while(nextArc != null && nextArc.getDestNode() != null && !nextArc.getDestNode().isMarque()){
                    Node nodeArc = nextArc.getDestNode();
                    nodeArc.setMarque(true);
                    nextArc = nextArc.getDestNode().getArc();
                }
                if(nextArc != null && nextArc.getDestNode().isMarque() && nextArc.getDestNode().getName().equals(nameNode)){
                    unmark();
                    return true;
                }
                arc = arc.getNextArc();
            }
            unmark();
            return false;
        }
    }

    public ArrayList<Graph> getCycles(String nameNode) throws Exception{
        ArrayList<Graph> cycles = new ArrayList<>();
        Node node = this.firstNode;
        while(node != null && !node.getName().equals(nameNode)){
            node = node.getNextNode();
        }
        if(node == null){
            unmark();
            throw new Exception("Sommet introuvable");
        }
        if(hasCycle(nameNode)){
            node.setMarque(true);
            Arc arc = node.getArc();
            while(arc != null){
                Graph graph = new Graph();
                graph.addNode(arc.getOriginNode().getId(), arc.getOriginNode().getName(), arc.getOriginNode().getValue());
                Arc nextArc = arc;
                while(nextArc != null && nextArc.getDestNode() != null && !nextArc.getDestNode().isMarque()){
                    graph.addNode(nextArc.getDestNode().getId(), nextArc.getDestNode().getName(), nextArc.getDestNode().getValue());
                    graph.addArc(nextArc.getOriginNode().getName(), nextArc.getDestNode().getName(), Integer.parseInt(nextArc.getLabel()));
                    Node nodeArc = nextArc.getDestNode();
                    nodeArc.setMarque(true);
                    nextArc = nextArc.getDestNode().getArc();
                }
                if(nextArc != null && nextArc.getDestNode().isMarque() && nextArc.getDestNode().getName().equals(nameNode)){
                    unmark();
                    node.setMarque(true);
                    cycles.add(graph);
                }
                arc = arc.getNextArc();
            }
            unmark();
            return cycles;
        }else{
            unmark();
            return cycles;
        }
        
        
    }
    
}
