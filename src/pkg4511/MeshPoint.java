/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg4511;

/**
 *
 * @author Jess
 */
class MeshPoint {
    Node startPoint, endPoint;
    float distance = Integer.MAX_VALUE;
    
    public MeshPoint(Node s, Node e){
        startPoint = s;
        endPoint = e;
        distance = getDistance();
    }
    
    public float getDistance(){
        int a = startPoint.x-endPoint.x;
        int b = startPoint.y-endPoint.y;
        return (float)Math.sqrt(a*a + b*b);
    }
    
}
