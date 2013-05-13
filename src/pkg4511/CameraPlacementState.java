/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg4511;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Patrick
 */
public class CameraPlacementState {
    List<Node> cameraLocations;
    final Node[][] plans;
    private double score;
    
    
    CameraPlacementState(final Node[][] floorplan, List<Node> cameras){
        plans = floorplan;
        cameraLocations = cameras;
    }
    
    public void setScore(){
        score = CameraPlacementEvaluator.evaluatePlacement(this, plans);
    }
    
    public double getScore(){
        return score;
    }
    
    @Override
    public CameraPlacementState clone(){
        CameraPlacementState clone = new CameraPlacementState(plans, new ArrayList<Node>());
        for(Node c : this.cameraLocations){
            clone.cameraLocations.add(c);
        }
        return clone;
    }
    
    public CameraPlacementState getBestSuccessor(){
        this.setScore();
        //Generate a successor, test successor, keep highest-scoring successor
        CameraPlacementState best = this;
        CameraPlacementState challenger = null;
        
        //Try all the spin possibilities:
        for(int c = 0; c < cameraLocations.size(); c++){
            System.out.println("On rotation, camera "+c);
            for(int orient = 0; orient < 360; orient++){
                Node newCam = cameraLocations.get(c).clone();
                newCam.setOri(orient);
                List<Node> newCamList = new ArrayList<Node>();
                for(int copy = 0; copy<cameraLocations.size(); copy++){
                    if(c!=copy){
                        newCamList.add(cameraLocations.get(copy));
                    }
                }
                newCamList.add(newCam);
                
                challenger = new CameraPlacementState(plans, newCamList);
                
                challenger.setScore();
                
                if(challenger.getScore() > best.getScore()){
                    best = challenger;
                    System.out.println("New best: "+best.getScore());
                }
            }
        }
        
        //Try all new location possibilities:
        List<Node> possibleMoves = this.calculatePossibleLocations();
        for(int c = 0; c < cameraLocations.size(); c++){
            System.out.println("On relocation, camera "+c);
            for(Node poss : possibleMoves){
                Node newCam = cameraLocations.get(c).clone();
                newCam.x = poss.x;
                newCam.y = poss.y;
                List<Node> newCamList = new ArrayList<Node>();
                for(int copy = 0; copy<cameraLocations.size(); copy++){
                    if(c!=copy){
                        newCamList.add(cameraLocations.get(copy));
                    }
                }
                newCamList.add(newCam);
                
                challenger = new CameraPlacementState(plans, newCamList);
                
                challenger.setScore();
                
                if(challenger.getScore() > best.getScore()){
                    best = challenger;
                    System.out.println("New best: "+best.getScore());
                }
            }
        }
        if(best == this){
            return null;
        }
        return best;        
    }
    
    
    
    
    
        private List<Node> calculatePossibleLocations(){
//                    if(run){                                    //This will mark and add to possibleNodes[] nodes that can become cameras.
            ArrayList<Node> possibilities = new ArrayList<Node>();
            for (int y = 0; y < plans.length; y++){          //Only floor nodes that are adjacent to walls are possible.
            for (int x = 0; x < plans[y].length; x++){
                Node evalNode = plans[x][y]; //center
                //System.out.println(Integer.toHexString(rgb));
                Node rightNode, topNode, leftNode, bottomNode;
                
                rightNode = new Node(0,0, NodeType.UNASSIGNED);
                topNode = new Node(0,0, NodeType.UNASSIGNED);
                bottomNode = new Node(0,0, NodeType.UNASSIGNED);
                leftNode = new Node(0,0, NodeType.UNASSIGNED);
                
                
                if(x!=plans[y].length-1){
                rightNode =plans[x+1][y];
                }
                if(y!=plans.length-1){
                topNode = plans[x][y+1];
                }
                if (x!=0){
                leftNode = plans[x-1][y];
                }
                if (y!=0){
                bottomNode = plans[x][y-1];    
                }
                if(evalNode.getType() == NodeType.FLOOR && ((rightNode.getType() == NodeType.WALL) ||
                        (topNode.getType() == NodeType.WALL) || (bottomNode.getType() == NodeType.WALL)
                        || (leftNode.getType() == NodeType.WALL) )){
                    possibilities.add(evalNode);
                }

            }
            }
//            run = false;
          return possibilities;
    }
    
}
