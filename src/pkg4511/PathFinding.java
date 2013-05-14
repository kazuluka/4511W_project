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
    ArrayList<MeshPoint> mesh = new ArrayList();
    
    public PathFinding(Node[][] processedFloor){
        floormap = processedFloor;
    }
    
    public void createMeshPoints(){
        Node[][] checkNodes  = new Node[3][3];
        MeshPointEvaluator test;
        int count = 0;
        for(int i = 0; i< floormap.length-1; i++){
            for(int j = 0; j < floormap[i].length-1;j++){
                
                //hopefully doors are not directly in corners and doors should be pixels wide
                Node check = floormap[i][j];

                
                if((i>=2)&&(j>=2)){
            //TODO: need to implement doors- need to have special 
            //marking because they are entrance exit pts aka final destinations
                    /*if((i<=floormap.length-2) && (j<=floormap[i].length-2)){
                        if(check.type==NodeType.DOOR){
                            if((floormap[i-1][j-1].type==NodeType.DOOR&&
                                    (floormap[i-1])){
                            int directions = {{1,1,}}
                            mesh.add(check);
                            }
                        }
                    }*/
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
                        count++;                        
                        System.out.println("Count: "+count);
                        mesh.add(test.getMeshPoint());
                    }                
                }             
            }
        }
    }
    
    public ArrayList<MeshPoint> getMesh(){
        return mesh;
    }
}
