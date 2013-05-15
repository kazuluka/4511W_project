/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg4511;

/**
 *
 * @author Jess
 */
public class MeshOption {
    MeshPoint startPoint, endPoint;
    float distance = Integer.MAX_VALUE;
    Node    sNode, eNode;
    
    public MeshOption(MeshPoint s, MeshPoint e){
        startPoint = s;
        endPoint = e;
        sNode = s.currentNode;
        eNode = e.currentNode;
        distance = getDistance();
    }
    
    public MeshOption(Node s, MeshPoint e){
        endPoint = e;
        sNode = s;
        eNode = e.currentNode;
        distance = getDistance();
    }
    
    public float getDistance(){
        int a = sNode.x-eNode.x;
        int b = sNode.y-eNode.y;
        if (a==0 && b==0){
            return 0;
        }
        return (float)Math.sqrt(a*a + b*b);
    }
    
}
