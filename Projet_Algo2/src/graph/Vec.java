package graph;

import java.util.ArrayList;

public class Vec {
    private ArrayList<String> vec;
    
    public Vec(){
        vec = new ArrayList<String>();
    }
    
    public void add(String nameNode){
        getVec().add(nameNode);
    }
    
    public void remove(String nameNode){
        int cpt = 0;
        boolean found = false;
        for(int i=0; i<getVec().size() && !found; i++){
            String elm = getVec().get(i);
            if(elm.equals(nameNode)){
                getVec().remove(i);
                found = true;
            }
        }
    }
    
    public boolean contains(String nameNode){
        for(String elm : getVec()){
            if(elm.equals(nameNode)){
                return true;
            }
        }
        return false;
    }

    void showAll() throws Exception {
        System.out.print("|");
        for(String elm : getVec()){
            System.out.print(elm + "|");
        }
        System.out.println("");
    }

    String getFirst() {
        return getVec().get(0);
    }

    public ArrayList<String> getVec() {
        return vec;
    }

    public void setVec(ArrayList<String> vec) {
        this.vec = vec;
    }
}
