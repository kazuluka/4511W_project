/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg4511;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Jess
 */
public class PathFinding {
    Node[][] floormap = new Node[400][400]; // Contains unchanging floormap data. No cameras, covered, or "possible"
    ArrayList<Node> path = new ArrayList(4000); //array for painting given paths
    ArrayList<Node> travelPoints = new ArrayList(100);
    ArrayList<MeshPoint> mesh = new ArrayList(100);
    
    public PathFinding(Node[][] processedFloor){
        floormap = processedFloor;
    }
    
    public void createTravelPoints(){
        Node[][] checkNodes  = new Node[3][3];
        MeshPointEvaluator test;
        for(int j = 0; j< floormap.length-1; j++){
            for(int i = 0; i < floormap[i].length-1;i++){
                
                //hopefully doors are not directly in corners
                Node check = floormap[i][j];
                if(check.type==NodeType.DOOR){
                    travelPoints.add(check);
                }
                
                if((i>=2)&&(j>=2)){
                    checkNodes[0][0] = floormap[i-2][j-2];
                    checkNodes[1][0] = floormap[i-1][j-2];
                    checkNodes[2][0] = floormap[i][j-2];
                    checkNodes[0][1] = floormap[i-2][j-1];
                    checkNodes[1][1] = floormap[i-1][j-1];
                    checkNodes[2][1] = floormap[i][j-1];
                    checkNodes[0][2] = floormap[i-2][j];
                    checkNodes[1][2] = floormap[i-1][j];
                    checkNodes[2][2] = floormap[i][j];
                    test = new MeshPointEvaluator(checkNodes); 
                    if(test.isMeshPointSet()){
                        travelPoints.add(test.getMeshPoint());
                    }                
                }             
            }
        }
    }
    
    public ArrayList<Node> getTravelPoints(){
        return travelPoints;
    }
    
    
    
}
