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
    ArrayList<Node> path = new ArrayList<>(4000); //array for painting given paths
    ArrayList<Node> travelPoints = new ArrayList<>(4000);
    
    public PathFinding(Node[][] processedFloor){
        floormap = processedFloor;
    }
    
    public void createTravelPoints(){
        for(int i = 0; i< floormap.length; i++){
            for(int j = 0; i < floormap[i].length;j++){
                Node check = floormap[i][j];
                if(check.type==NodeType.DOOR){
                    travelPoints.add(check);
                }
            }
        }
    }
    
    
    
}
