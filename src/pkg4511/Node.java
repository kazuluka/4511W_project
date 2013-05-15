/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg4511;

/**
 *
 * @author sever408
 */
public class Node {                      //node object
   public int x = 0;
   public int y = 0;
//   public int coverage = 0;
//   public boolean occupied = false;
   public NodeType type = NodeType.UNASSIGNED; //The type of node -- wall, floor, nothing, camera, possible
   public int orientation = 0; //This is used for cameras -- the direction they are pointing
   public Node(int a, int b, NodeType setType) { //constructor
       x = a;
       y = b;
       type = setType;
   }
//   public void setCoverage(int cov){ //This is used for cameras
//       coverage = cov;
//   }
//   public int getCoverage(){ //This is used for cameras
//       return coverage;
//   }
   public void setType(NodeType setType){ //This is used for cameras
       type = setType;
   }
   public NodeType getType(){ //This is used for cameras
       return type;
   }
   public void setOri(int direction){ //This is used for cameras
       orientation = direction;
   }
   public int getOri(){ //This is used for cameras
       return orientation;
   }
   
   @Override
   public Node clone(){
       Node clone = new Node(x, y, this.type);
       clone.setOri(orientation);
       return clone;
   }
   
   @Override
   public boolean equals(Object otherGuy){
       Node otherNode = (Node) otherGuy;
       
       return ((this.x == otherNode.x) && (this.y == otherNode.y) && 
               (this.getOri() == otherNode.getOri()) && 
               (this.getType() == otherNode.getType()));
   }
}
