package graph;

import java.util.ArrayList;

public class Vec {
    private ArrayList<MyVec> vec;
    
    public Vec(){
        vec = new ArrayList<>();
    }
    
    public void add(String nameNode){
        MyVec myVec = new MyVec();
        myVec.setNameNode(nameNode);
        getVec().add(myVec);
    }
    
    public void remove(String nameNode){
        int cpt = 0;
        boolean found = false;
        for(int i=0; i<getVec().size() && !found; i++){
            MyVec elm = getVec().get(i);
            if(elm.getNameNode().equals(nameNode)){
                getVec().remove(i);
                found = true;
            }
        }
    }
    
    public boolean contains(String nameNode){
        for(MyVec elm : getVec()){
            if(elm.getNameNode().equals(nameNode)){
                return true;
            }
        }
        return false;
    }

    void showAll() throws Exception {
        System.out.print("|");
        for(MyVec elm : getVec()){
            System.out.print(elm.getNameNode() + "|");
        }
        System.out.println("");
    }

    String getFirst() {
        return getVec().get(0).getNameNode();
    }

    public ArrayList<MyVec> getVec() {
        return vec;
    }

    public void setVec(ArrayList<MyVec> vec) {
        this.vec = vec;
    }
}
