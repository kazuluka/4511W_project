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
        return current.getBestSuccessor();
        
        
    }
}
