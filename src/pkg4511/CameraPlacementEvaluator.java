/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg4511;

import java.util.Arrays;

/**
 *
 * @author Patrick
 */
public class CameraPlacementEvaluator {
 
    public static double evaluatePlacement(CameraPlacementState s, final Node[][] plans){
        double floor = getTotalFloorSpace(plans);
        
        Node[][] evalMap = new Node[400][400];
        for(int i = 0; i < evalMap.length; i++){
            evalMap[i] = Arrays.copyOf(plans[i], 400);
        }
        
        for(Node camera : s.cameraLocations){
            evalMap[camera.x][camera.y] = camera;
            calculateCoverage(camera.x, camera.y, camera.getOri(), 5, evalMap);
        }
        
        double cover = getTotalCoverageSpace(evalMap);
        
        return (cover / floor);
    }
    
    private static double getTotalFloorSpace(final Node[][] plans){
        double floorTiles = 0;
        for(int y = 0; y < plans.length; y++){
          for(int x = 0; x < plans[y].length; x++){
            if(plans[x][y].type==NodeType.FLOOR){
                floorTiles++;
            }
        }
        }
        return floorTiles;
    }
    
    private static double getTotalCoverageSpace(final Node[][] plans){
        double floorTiles = 0;
        for(int y = 0; y < plans.length; y++){
          for(int x = 0; x < plans[y].length; x++){
            if(plans[x][y].type==NodeType.COVERED){
                floorTiles++;
            }
        }
        }
        return floorTiles;
    }
    
    
    
    private static void calculateCoverage(int xPos, int yPos, int oriDeg, int fan, Node[][] map){
        //orient == degrees from pure right
        //fan == number of degrees from center we're fanning out
        assert (xPos >= 0);
        assert (xPos < 400);
        assert (yPos >= 0);
        assert (yPos < 400);

        for(double f = -1.0*fan; f<=fan; f+=0.1){
           double orient = degToRad((oriDeg+f));
           double xSlope = Math.cos(orient);
           double ySlope = Math.sin(orient);

           double xAccum = 0.0;
           double yAccum = 0.0;
           int xCur = xPos;
           int yCur = yPos;
           do{
               map[xCur][yCur].setType(NodeType.COVERED);              
               xAccum += xSlope;
               yAccum += ySlope;
               xCur = xPos + (int) Math.round(xAccum);
               yCur = yPos + (int) Math.round(yAccum);
           }while(map[xCur][yCur].type != NodeType.WALL && map[xCur][yCur].type != NodeType.NOTHING);
        }

    }
    
    private static double degToRad(double a){
        return (((double) a)/180.0) * Math.PI;
    }
    
}
