/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg4511;

/**
 *
 * @author sever408
 */
public enum NodeType {
    //This defines what is contained in the Node on the map.
    FLOOR (0), 
    WALL (1), 
    NOTHING (2), 
    CAMERA (3), 
    POSSIBLE (4), 
    COVERED (5), 
    UNASSIGNED (6), 
    DOOR (7), 
    MESH (8);
    
    private final int index;
    
    NodeType(int index){
        this.index = index;
    }
    
    public int index() {
        return index;
    }
}
