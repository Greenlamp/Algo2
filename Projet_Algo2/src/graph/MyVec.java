package graph;

public class MyVec {
    private String nameNode;
    
    public MyVec(){
        nameNode = null;
    }
    
    public MyVec(String nameNode){
        this.nameNode = nameNode;
    }

    public String getNameNode() {
        return nameNode;
    }

    public void setNameNode(String nameNode) {
        this.nameNode = nameNode;
    }
}
