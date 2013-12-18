package graph;

import graphviz.GraphViz;
import java.io.File;
import java.util.ArrayList;

public class Graph {
    private Node firstNode;
    int cpt;
    private ArrayList<Graph> listeCycle;
    
    public Graph(){
        firstNode = null;
        cpt = 0;
        listeCycle = new ArrayList<>();
    }

    public void addNode(String id, String name, int value) {
        Node newNode = new Node();
        newNode.setName(name);
        newNode.setValue(value);
        newNode.setId(id);
        boolean found = false;
        if(this.getFirstNode() == null){
            this.setFirstNode(newNode);
        }else{
            Node node = this.getFirstNode();
            while(node.getNextNode() != null){
                if(node.getNextNode().getId().equals(newNode.getId())){
                    found = true;
                }
                node = node.getNextNode();
            }
            if(!found){
                newNode.setPrevNode(node);
                node.setNextNode(newNode);
            }
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

    public void getCycles(Node node, Vec vec) throws Exception{
        vec.add(node.getName());
        Arc arc = node.getArc();
        while(arc != null){
            if(!vec.contains(arc.getDestNode().getName())){
                getCycles(arc.getDestNode(), vec);
            }else{
                if(arc.getDestNode().getName().equals(vec.getFirst())){
                    vec.showAll();
                    buildCycle(vec);
                }
            }
            arc = arc.getNextArc();
        }
        vec.remove(node.getName());
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

    public Node getNode(String nameNode) throws Exception {
        Node node = this.firstNode;
        while(node != null && !node.getName().equals(nameNode)){
            node = node.getNextNode();
        }
        if(node == null){
            throw new Exception("Sommet "+nameNode+" introuvable");
        }
        return node;
    }

    private void buildCycle(Vec vec) throws Exception {
        Graph newGraph = new Graph();
        int nbElm = vec.getVec().size();
        for(int i=0; i<nbElm-1; i++){
            String nameNode = vec.getVec().get(i).getNameNode();
            String nameNextNode = vec.getVec().get(i+1).getNameNode();
            Arc arc = getArc(nameNode, nameNextNode);
            Node node = getNode(nameNode);
            Node nextNode = getNode(nameNextNode);
            newGraph.addNode(node.getId(), node.getName(), node.getValue());
            newGraph.addNode(nextNode.getId(), nextNode.getName(), nextNode.getValue());
            newGraph.addArc(nameNode, nameNextNode, Integer.parseInt(arc.getLabel()));
        }
        String nameNode = vec.getVec().get(vec.getVec().size()-1).getNameNode();
        String nameNextNode = vec.getVec().get(0).getNameNode();
        Arc arc = getArc(nameNode, nameNextNode);
        Node node = getNode(nameNode);
        Node nextNode = getNode(nameNextNode);
        newGraph.addNode(node.getId(), node.getName(), node.getValue());
        newGraph.addNode(nextNode.getId(), nextNode.getName(), nextNode.getValue());
        newGraph.addArc(nameNode, nameNextNode, Integer.parseInt(arc.getLabel()));
        getListeCycle().add(newGraph);
    }

    private Arc getArc(String nameNode, String nameNextNode) throws Exception {
        Node node = this.firstNode;
        while(node != null){
            Arc arc = node.getArc();
            while(arc != null){
                if(arc.getOriginNode().getName().equals(nameNode) && arc.getDestNode().getName().equals(nameNextNode)){
                    return arc;
                }
                arc = arc.getNextArc();
            }
            node = node.getNextNode();
        }
        throw new Exception("Arc "+nameNode+" - " +nameNextNode+ " introuvable");
    }

    public ArrayList<Graph> getListeCycle() {
        return listeCycle;
    }

    public void setListeCycle(ArrayList<Graph> listeCycle) {
        this.listeCycle = listeCycle;
    }
}
