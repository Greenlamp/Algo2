package graph;

import graphviz.GraphViz;
import java.io.File;
import java.util.ArrayList;

public class Graph {
    private Node firstNode;
    int cpt;
    public Graph(){
        firstNode = null;
        cpt = 0;
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
    
    private void marquerSommet(String nameNode){
        Node node = this.firstNode;
        while(node != null && !node.getName().equals(nameNode)){
            node = node.getNextNode();
        }
        if(node != null){
            node.setMarque(true);
        }
    }

    public ArrayList<Graph> getCycles(String nameNode, ArrayList<Graph> cycles) throws Exception{
        if(hasCycles(nameNode)){
            Node node = this.firstNode;
            while(node != null && !node.getName().equals(nameNode)){
                node = node.getNextNode();
            }
            if(node == null){
                throw new Exception("Sommet introuvable");
            }
            return getCycles(node, nameNode,cycles);
        }else{
            return cycles;
        }
    }

    private ArrayList<Graph> getCycles(Node node, String nameNode, ArrayList<Graph> cycles) {
        return cycles;
    }

    public boolean hasCycles(String nameNode) throws Exception {
        Node node = firstNode;
        while(node != null && !node.getName().equals(nameNode)){
            node = node.getNextNode();
        }
        if(node == null){
            throw new Exception("Sommet "+nameNode+" introuvable");
        }
        unmark();
        return hasCycle(node, nameNode);
    }

    private boolean hasCycle(Node node, String nameNode) {
        node.setMarque(true);
        //System.out.println(node.getName());
        Arc arc = node.getArc();
        while(arc != null){
            //System.out.println("destNode: " + (arc.getDestNode().getName() != null ? arc.getDestNode().getName() : null));
            if(arc.getDestNode() != null && !arc.getDestNode().isMarque()){
                if(hasCycle(arc.getDestNode(), nameNode)){
                    return true;
                }
            }else if(arc.getDestNode() != null && arc.getDestNode().isMarque() && arc.getDestNode().getName().equals(nameNode)){
                return true;
            }
            //System.out.println("de retour sur le sommet " + node.getName());
            arc = arc.getNextArc();
        }
        //System.out.println("Pas d'autre arc pour " + node.getName());
        return false;
    }
    
}
