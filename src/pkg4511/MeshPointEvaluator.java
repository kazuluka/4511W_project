/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg4511;

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
    int[][] checkNodesM4 = {{0,0,1},{0,0,1},{0,0,0}};
    
    Node[][] toCheck = new Node[3][3];
    int[][] intToCheck = new int[3][3];
    Node meshPoint = null;
    
    public MeshPointEvaluator(){}
    
    public MeshPointEvaluator(Node[][] toCheck){    
        this.toCheck = toCheck;
        translateToCheck();
        meshPoint();
    }

    public void translateToCheck(){
        for(int i=0; i<3;i++){
            for(int k = 0; k<3;k++){
                intToCheck[i][k] = toCheck[i][k].type.WALL.index();
            }
        }
    }
    
    public void meshPoint(){
        if(Arrays.deepEquals(checkNodesTR, intToCheck)){
            meshPoint = toCheck[2][0];
        }
        if(Arrays.deepEquals(checkNodesTL, intToCheck)){
            meshPoint = toCheck[0][0];
        }
        if(Arrays.deepEquals(checkNodesBL, intToCheck)){
            meshPoint = toCheck[0][2];
        }
        
        if(Arrays.deepEquals(checkNodesBR, intToCheck)){
            meshPoint = toCheck[2][2];
        }
        //Middle point is meshPoint
        if((Arrays.deepEquals(checkNodesM1, intToCheck))||
                (Arrays.deepEquals(checkNodesM2, intToCheck))||
                (Arrays.deepEquals(checkNodesM3, intToCheck))|
                (Arrays.deepEquals(checkNodesM4, intToCheck))){
            meshPoint = toCheck[1][1];
        }        
    }
    
    public boolean isMeshPointSet(){
        if(meshPoint==null){
            return false;
        }
        return true;
    }
    
    public Node getMeshPoint(){
        return meshPoint;
    }
}
