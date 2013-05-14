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
    //8 cases - example case below
    // Checking outward corners     x x o
    // x wall | o floor             x x o
    // 4 rotations to cover cases   o o o <-BR meshPoint
    int[][] checkNodesTR = {{0,0,0},{1,1,0},{1,1,0}}; 
    int[][] checkNodesTL = {{0,0,0},{0,1,1},{0,1,1}};
    int[][] checkNodesBR = {{1,1,0},{1,1,0},{0,0,0}};
    int[][] checkNodesBL = {{0,1,1},{0,1,1},{0,0,0}};
    int[][] checkNodesM1 = {{1,1,1},{1,0,0},{1,0,0}};
    int[][] checkNodesM2 = {{1,1,1},{0,0,1},{0,0,1}};
    int[][] checkNodesM3 = {{1,0,0},{1,0,0},{1,1,1}};
    int[][] checkNodesM4 = {{0,0,1},{0,0,1},{1,1,1}};
    
    Node[][] toCheck = new Node[3][3];
    int[][] intToCheck = new int[3][3];
    MeshPoint meshPoint = null;    
    
    public MeshPointEvaluator(){}
    
    public MeshPointEvaluator(Node[][] toCheck){    
        this.toCheck = toCheck;
        translateToCheck();
        meshPoint();
    }

    public void translateToCheck(){
        NodeType toCheckType;
        for(int i=0; i<3;i++){
            for(int k = 0; k<3;k++){
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
    
    public void meshPoint(){
        // <editor-fold defaultstate="collapsed" desc="Outside Corners">        
        if(Arrays.deepEquals(checkNodesTR, intToCheck)){
            meshPoint = new MeshPoint(toCheck[0][2], checkNodesTR);
            System.out.println("TR");
            return;
        }
        if(Arrays.deepEquals(checkNodesTL, intToCheck)){
            meshPoint = new MeshPoint(toCheck[0][0], checkNodesTL);
            System.out.println("TL");
            return;
        }
        if(Arrays.deepEquals(checkNodesBL, intToCheck)){
            meshPoint = new MeshPoint(toCheck[2][0], checkNodesBL);
            System.out.println("BL");
            return;
        }        
        if(Arrays.deepEquals(checkNodesBR, intToCheck)){
            meshPoint = new MeshPoint(toCheck[2][2], checkNodesBR);
            System.out.println("BR");
            return;
        }
        //</editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Middle points">
        if(Arrays.deepEquals(checkNodesM1, intToCheck)){
            meshPoint = new MeshPoint(toCheck[1][1], checkNodesM1);
            System.out.println("M1");
            return;
        }        
        if(Arrays.deepEquals(checkNodesM2, intToCheck)){
            meshPoint = new MeshPoint(toCheck[1][1], checkNodesM2);
            System.out.println("M2");
            return;
        }     
        if(Arrays.deepEquals(checkNodesM3, intToCheck)){            
            meshPoint = new MeshPoint(toCheck[1][1], checkNodesM3);
            System.out.println("M3");
            return;
        }     
        if(Arrays.deepEquals(checkNodesM4, intToCheck)){
            meshPoint = new MeshPoint(toCheck[1][1], checkNodesM4);
            System.out.println("M4");
            return;
        }
        //</editor-fold>
    }
    
    public boolean isMeshPointSet(){
        if(meshPoint==null){
            return false;
        }
        System.out.println("Adding Node: ("+meshPoint.currentNode.x+", "+meshPoint.currentNode.y+")");
        return true;
    }
    
    public MeshPoint getMeshPoint(){
        return meshPoint;
    }
}
