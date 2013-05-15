/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg4511;

import java.util.ArrayList;

/**
 *
 * @author Jess
 */
class MeshPolygon {
    ArrayList<MeshPoint> corners = new ArrayList();
    ArrayList<MeshPoint> centerEdgeNodes = new ArrayList();
    
    public MeshPolygon(MeshPoint c1,MeshPoint c2,MeshPoint c3){
        
    }
    
    
    //TODO: Need to implement, need to think of a way to get shared edges
    // to have same center point.
    public void generateCenterEdge(){
        if(!isPolygon()){return;}
        
        for(int i =1; i<corners.size()-1;i++){
            MeshPoint first = corners.get(i);
            MeshPoint second = corners.get(i+1);
            //float distance = first.distance(second);
            int[] cord = first.midPoint(second);
        }
    }
    
    public boolean isPolygon(){
        if(corners.size()>=3){
            return true;
        }
        return false;
    }
    
}
