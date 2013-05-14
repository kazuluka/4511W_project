/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg4511;

import java.util.ArrayList;

/**
 *
 * @author Jess
 */
class MeshPoint {
    Node currentNode;
    ArrayList<MeshOption> options = new ArrayList(10);
    int[][] direction = new int[3][3]; // left, right, top, bottom, br,bl,tl,tr
    
    public MeshPoint(Node c, int[][] allowedDirections){
        int i=0;
        currentNode = c;
        direction  = allowedDirections;
        
    }
    
    public boolean canTravelD(int i, int k){
        if (direction[i][k]==0);{ //0 - no wall
           return true;
        }
    }

    public void addOption(Node a){
        MeshOption newOption = new MeshOption(currentNode, a);
        options.add(newOption);
    }
    
}
