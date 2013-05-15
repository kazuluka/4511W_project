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
    String position;
    int[][] direction = new int[2][2]; // left, right, top, bottom, br,bl,tl,tr

    public MeshPoint(Node c){
        currentNode = c;
        //position = p;        
    }
    
    public MeshPoint(Node c, int[][] allowedDirections, String p){
        int i=0;
        currentNode = c;
        direction  = allowedDirections;
        position = p;
        
    }
    
    /*public boolean canTravelD(int i, int k){
        if (direction[i][k]==0);{ //0 - no wall
           return true;
        }
    }*/

    public void addOption(MeshPoint a){
        MeshOption newOption = new MeshOption(this, a);
        options.add(newOption);
    }

    public boolean sameNodeInside(MeshPoint otherPoint){
        if(currentNode.x == otherPoint.currentNode.x &&
              currentNode.y == otherPoint.currentNode.y){
            return true;
        }
        return false;
    }
    
    public float distance(MeshPoint otherPoint){
        int mx = currentNode.x;
        int my = currentNode.y;
        int px = otherPoint.currentNode.x;
        int py = otherPoint.currentNode.y;
        float a = mx-px;
        float b = my-py;
        if (a==0 && b==0){
            return 0;
        }
        float c = (float)Math.sqrt(a*a + b*b);
        return c;
    }
    
    public int[] midPoint(MeshPoint otherPoint){
        int[] cord = new int[2];
        
        //x cord
        int mx = currentNode.x;
        int px = otherPoint.currentNode.x;
        cord[0] = (int)Math.ceil(mx-px);
        
        //y cord
        int my = currentNode.y;
        int py = otherPoint.currentNode.y;
        cord[1] = (int)Math.ceil(my-py);
        
        return cord;
    }
    

    
}
