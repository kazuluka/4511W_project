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
    Node startPoint, endPoint;
    float distance = Integer.MAX_VALUE;
    
    public MeshOption(Node s, Node e){
        startPoint = s;
        endPoint = e;
        distance = getDistance();
    }
    
    public float getDistance(){
        int a = startPoint.x-endPoint.x;
        int b = startPoint.y-endPoint.y;
        if (a==0 && b==0){
            return 0;
        }
        return (float)Math.sqrt(a*a + b*b);
    }
    
}
