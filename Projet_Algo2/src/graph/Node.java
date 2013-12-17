package graph;


public class Node {
    private Node nextNode;
    private String name;
    private String id;
    private int value;
    private Node prevNode;
    private Arc arc;
    private boolean marque;
    private boolean marque2;
    
    public Node(){
        nextNode = null;
        name = null;
        value = 0;
        prevNode = null;
        arc = null;
        id = null;
        marque = false;
        marque2 = false;
    }

    public Node getNextNode() {
        return nextNode;
    }

    public void setNextNode(Node nextNode) {
        this.nextNode = nextNode;
    }

    public Node getPrevNode() {
        return prevNode;
    }

    public void setPrevNode(Node prevNode) {
        this.prevNode = prevNode;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Arc getArc() {
        return arc;
    }

    public void setArc(Arc arc) {
        this.arc = arc;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isMarque() {
        return marque;
    }

    public void setMarque(boolean marque) {
        this.marque = marque;
    }

    public boolean isMarque2() {
        return marque2;
    }

    public void setMarque2(boolean marque2) {
        this.marque2 = marque2;
    }
    
}
