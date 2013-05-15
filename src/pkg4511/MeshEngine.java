/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg4511;

import pkg4511.MeshPolygon;
import java.util.ArrayList;
import pkg4511.MeshPoint;
import pkg4511.Node;
import pkg4511.NodeType;

/**
 *
 * @author Jess
 */
public class MeshEngine {
    Node[][] floormap = new Node[400][400]; // Contains unchanging floormap data. No cameras, covered, or "possible"
    Mesh floorMesh = new Mesh();
    
    public MeshEngine(Node[][] processedFloor){
        floormap = processedFloor;
    }
    
    public void createAllMeshPolygons(){
        MeshPolygon singlePoly = new MeshPolygon();
        MeshPoint startCorner;
        boolean continueProcess = true;
        while(continueProcess){
            for(int i = 0; i < floormap.length; i++){ //row
                for(int j = 0; j < floormap.length; j++){ // collumn
                    if(floormap[i][j].type.FLOOR==NodeType.FLOOR){
                        floormap[i][j].setType(NodeType.MESHPOINT);                        
                        startCorner = new MeshPoint(floormap[i][j],"TL");
                        createSingleMeshPolygon(startCorner);
                    }
                    
                }

            }
        }
        



    }

    private void createSingleMeshPolygon(MeshPoint startCorner) {
        int expansionI = 2;
        int expansionJ = 2;
        ArrayList<MeshPoint> possible = new ArrayList(); 
        boolean keepExpanding = true;
        int x = startCorner.currentNode.x;
        int y = startCorner.currentNode.y;
        
        while(keepExpanding)
        for(int i = 0; i < expansionI && ((i+expansionI)<floormap.length); i++){ //row
            for(int j = 0; j < expansionJ && ((j+expansionJ)<floormap[i+expansionI].length); j++){ // collumn
                if(i == 0){
                    j=1;
                }                
                int ix = i+x;
                int jy = j+y;
                if(floormap[ix][jy].type.FLOOR==NodeType.FLOOR){
                    
                }
            }
        }
    }
    
    
}
