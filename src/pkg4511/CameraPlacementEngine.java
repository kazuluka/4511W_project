/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg4511;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Patrick
 */
public class CameraPlacementEngine {
    
    public static List<Node> extractCameras(final Node[][] plans){
        
        ArrayList<Node> cams = new ArrayList<Node>();
        for(int y = 0; y < plans.length; y++){
        for(int x = 0; x < plans[y].length; x++){
            if(plans[x][y].type==NodeType.CAMERA){
                cams.add(plans[x][y]);
            }
        }
        }
        return cams;
    }
    
    
    public static CameraPlacementState getImprovedState(Node[][] currentPlacement, final Node[][] floorplans){
        //Get current state of affairs
        List<Node> cams = extractCameras(currentPlacement);
        //Create new CameraPlacementState
        CameraPlacementState current = new CameraPlacementState( floorplans, cams);
        //Get successor
        /*
         * The below allows us to select what function we'd like to use: 
         *   getBestSuccessor() // tests everything, finds the best choice of those. Slow iterations, checks lots of unnecessary stuff
         *   getFirstBetterSuccessor() // Still tests everything, but chooses the first better thing it finds. Much, much faster.
         */
        
        
        
//        return current.getBestSuccessor();
//        return current.getFirstBetterSuccessor();
          return current.quickGetFirstBetterSuccessor();
        
    }
}
