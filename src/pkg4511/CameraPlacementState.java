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
    
    private static int quick_onCamera = 0;
    private static boolean quick_wrappedAround = false;
    
    
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
    
    
    public CameraPlacementState getFirstBetterSuccessor(){
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
                    return best;
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
                    return best;
                }
            }
        }
        if(best == this){
            return null;
        }
        return best; //Should never run        
    }
    
    
    private CameraPlacementState improveSingleCamera(Node camera){
        //Motivation: We waste a lot of time calculating for cameras that are, for the moment, "complete"
        //So, let's narrow the focus of the algorithm to one camera, so we skip unnecessary calculations
        //We'll be using the Best-Choice algorithm to optimize this camera.
        this.setScore();
        //Generate a successor, test successor, keep highest-scoring successor
        CameraPlacementState best = this;
        CameraPlacementState challenger = null;
        
        //Try all the spin possibilities:
//            System.out.println("On rotation, camera "+c);
            for(int orient = 0; orient < 360; orient++){
                Node newCam = camera.clone();
                newCam.setOri(orient);
                List<Node> newCamList = new ArrayList<Node>();
                for(int copy = 0; copy<cameraLocations.size(); copy++){
                    if(!camera.equals(cameraLocations.get(copy))){
                        newCamList.add(cameraLocations.get(copy));
                    }
                }
                newCamList.add(newCam);
                
                challenger = new CameraPlacementState(plans, newCamList);
                
                challenger.setScore();
                
                if(challenger.getScore() > best.getScore()){
                    best = challenger;
                    System.out.println("New best: "+best.getScore());
//                    return best;
                }
            }
        
        
        //Try all new location possibilities:
        List<Node> possibleMoves = this.calculatePossibleLocations();
//            System.out.println("On relocation, camera "+c);
            for(Node poss : possibleMoves){
                Node newCam = camera.clone();
                newCam.x = poss.x;
                newCam.y = poss.y;
                List<Node> newCamList = new ArrayList<Node>();
                for(int copy = 0; copy<cameraLocations.size(); copy++){
                    if(!camera.equals(cameraLocations.get(copy))){
                        newCamList.add(cameraLocations.get(copy));
                    }
                }
                newCamList.add(newCam);
                
                challenger = new CameraPlacementState(plans, newCamList);
                
                challenger.setScore();
                
                if(challenger.getScore() > best.getScore()){
                    best = challenger;
                    System.out.println("New best: "+best.getScore());
//                    return best;
                }
            }
        if(best == this){
            return null;
        }
        return best;
        
    }
    
    public void initializeQuick(){
        quick_onCamera = 0;
        quick_wrappedAround = false;
    }
    
    
    public CameraPlacementState quickGetFirstBetterSuccessor(){
        while(true){
            System.out.println("Evaluating camera "+quick_onCamera+", wrapped around is "+quick_wrappedAround);
            Node currentCamera = cameraLocations.get(quick_onCamera);
            CameraPlacementState next = improveSingleCamera(currentCamera);
            quick_onCamera++;
            if(next == null && quick_onCamera>=cameraLocations.size() && quick_wrappedAround){
                    return null;//We've run out of ways to improve.
                }
            if(quick_onCamera>=cameraLocations.size()){
                quick_onCamera = 0;
                quick_wrappedAround = true;
            }
            if(next!=null){
                quick_wrappedAround = false;//We've found something this time around,
                //Which means we're not done yet.
                return next;
            }
        }
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
            //Remove existing camera locations: Avoid destroying a camera!
          for(Node existingCamera : cameraLocations){
              for(int i = 0; i<possibilities.size(); i++){
                  Node possible = possibilities.get(i);
                  if(existingCamera.x == possible.x && existingCamera.y == possible.y){
                      possibilities.remove(i);
                      i--;//Keep in the same location in the list
                  }
              }
          }
//            run = false;
          return possibilities;
    }
    
}
