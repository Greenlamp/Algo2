package graph;

public class Arc {

    private Node originNode;
    private String label;
    private Node destNode;
    private Arc nextArc;

    public Arc() {
        originNode = null;
        label = null;
        destNode = null;
        nextArc = null;
    }

    public Node getOriginNode() {
        return originNode;
    }

    public void setOriginNode(Node originNode) {
        this.originNode = originNode;
    }

    public Node getDestNode() {
        return destNode;
    }

    public void setDestNode(Node destNode) {
        this.destNode = destNode;
    }

    public Arc getNextArc() {
        return nextArc;
    }

    public void setNextArc(Arc nextArc) {
        this.nextArc = nextArc;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}
