/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg4511;

import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author Jess
 */
public class MeshPointEvaluator {
    //8 cases different 2x2 cases for determining if a node is a corner.
    // Checking outward corners     
    // x wall | o floor              x o
    // 4 rotations to cover cases    o o <-BR createMeshPoint
    int[][] checkNodesTR = {{0,0},{1,0}}; 
    int[][] checkNodesTL = {{0,0},{0,1}};
    int[][] checkNodesBR = {{1,0},{0,0}};
    int[][] checkNodesBL = {{0,1},{0,0}};
    int[][] checkNodesM1 = {{1,1},{1,0}};
    int[][] checkNodesM2 = {{1,1},{0,1}};
    int[][] checkNodesM3 = {{1,0},{1,1}};
    int[][] checkNodesM4 = {{0,1},{1,1}};
    
    Node[][] toCheck = new Node[2][2];
    int[][] intToCheck = new int[2][2];
    MeshPoint meshPoint = null;    
    
    public MeshPointEvaluator(){}
    
    public MeshPointEvaluator(Node[][] toCheck){    
        this.toCheck = toCheck;
        translateToCheck();
        createMeshPoint();
    }

    public void translateToCheck(){
        NodeType toCheckType;
        for(int i=0; i<2;i++){
            for(int k = 0; k<2;k++){
                toCheckType = toCheck[i][k].type;
                if((toCheckType==NodeType.WALL) || (toCheckType == NodeType.DOOR)){
                    intToCheck[i][k] = 1;
                }else if(toCheckType == NodeType.FLOOR){
                    intToCheck[i][k] = 0;
                }else{
                    intToCheck[i][k] = -1;
                }
            }
        }
    }
    
    public void createMeshPoint(){
        // <editor-fold defaultstate="collapsed" desc="Outside Corners">        
        if(Arrays.deepEquals(checkNodesTR, intToCheck)){
            meshPoint = new MeshPoint(toCheck[0][1], checkNodesTR, "TR");
            //System.out.println("TR");
            return;
        }
        if(Arrays.deepEquals(checkNodesTL, intToCheck)){
            meshPoint = new MeshPoint(toCheck[0][0], checkNodesTL, "TL");
            //System.out.println("TL");
            return;
        }
        if(Arrays.deepEquals(checkNodesBL, intToCheck)){
            meshPoint = new MeshPoint(toCheck[1][0], checkNodesBL, "BL");
            //System.out.println("BL");
            return;
        }        
        if(Arrays.deepEquals(checkNodesBR, intToCheck)){
            meshPoint = new MeshPoint(toCheck[1][1], checkNodesBR, "BR");
            //System.out.println("BR");
            return;
        }
        //</editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Middle points">
        if(Arrays.deepEquals(checkNodesM1, intToCheck)){
            meshPoint = new MeshPoint(toCheck[1][1], checkNodesM1, "M1");
            //System.out.println("M1");
            return;
        }        
        if(Arrays.deepEquals(checkNodesM2, intToCheck)){
            meshPoint = new MeshPoint(toCheck[1][0], checkNodesM2, "M2");
            //System.out.println("M2");
            return;
        }     
        if(Arrays.deepEquals(checkNodesM3, intToCheck)){            
            meshPoint = new MeshPoint(toCheck[0][1], checkNodesM3, "M3");
            //System.out.println("M3");
            return;
        }     
        if(Arrays.deepEquals(checkNodesM4, intToCheck)){
            meshPoint = new MeshPoint(toCheck[0][0], checkNodesM4, "M4");
            //System.out.println("M4");
            return;
        }
        //</editor-fold>
    }
    
    public boolean isMeshPointSet(){
        if(meshPoint==null){
            return false;
        }
        //System.out.println("Adding Node: ("+createMeshPoint.currentNode.x+", "+createMeshPoint.currentNode.y+")");
        return true;
    }
    
    public MeshPoint getMeshPoint(){
        return meshPoint;
    }
}
