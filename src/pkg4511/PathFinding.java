/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg4511;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

/**
 *
 * @author Jess
 */
public class PathFinding {
    Node[][] floormap = new Node[400][400]; // Contains unchanging floormap data. No cameras, covered, or "possible"
    ArrayList<Node> path = new ArrayList(); //array for painting given paths
    ArrayList<MeshPoint> mesh = new ArrayList();
    ArrayList<MeshOption> options = new ArrayList();
    
    public PathFinding(Node[][] processedFloor){
        floormap = processedFloor;
    }
    
    public void createMeshPoints(){
        Node[][] checkNodes  = new Node[3][3];
        MeshPointEvaluator test;
        //int count = 0;
        for(int i = 0; i< floormap.length-1; i++){
            for(int j = 0; j < floormap[i].length-1;j++){
                
                //hopefully doors are not directly in corners and doors should be pixels wide
                Node check = floormap[i][j];

                
                if((i>=1)&&(j>=1)){
            //TODO: need to implement doors- need to have special 
            //marking because they are entrance exit pts aka final destinations
                    /*if((i<=floormap.length-2) && (j<=floormap[i].length-2)){
                        if(check.type==NodeType.DOOR){
                            if((floormap[i-1][j-1].type==NodeType.DOOR&&
                                    (floormap[i-1])){
                            int directions = {{1,1,}}
                            mesh.add(check);
                            }
                        }
                    }*/
                    checkNodes[0][0] = floormap[i-1][j-1];
                    checkNodes[1][0] = floormap[i][j-1];
                    checkNodes[0][1] = floormap[i-1][j];
                    checkNodes[1][1] = floormap[i][j];
                    test = new MeshPointEvaluator(checkNodes); 
                    if(test.isMeshPointSet()){
                        //count++;                        
                       // System.out.println("Count: "+count);
                        mesh.add(test.getMeshPoint());
                    }                
                }             
            }
        }
        //TODO:fix connections - looks like I am looping through a lot of nodes I shouldn't
        makeMeshPointConnections();
    }
    
    
    //checking for connections between MeshPoints in our newly created mesh ArrayList
    public void makeMeshPointConnections(){
        System.out.println("Inside makeMeshPointConnections");
        boolean[] checked = new boolean[mesh.size()];
        Arrays.fill(checked, false);
        int count = 0;
        for(MeshPoint m : mesh){
            if(!checked[count]){
                for(int i = count+1; i<mesh.size();i++){                    
                    MeshPoint p = mesh.get(i); 
                   if(straitWalk(m, mesh.get(i))){
                        //System.out.println("Should be adding connections");
                        //System.out.println("M: (" + m.currentNode.x + ", " + m.currentNode.y + ")");
                        //System.out.println("P: (" + p.currentNode.x + ", " + p.currentNode.y + ")");
                        m.addOption(p);
                        p.addOption(m);                        
                    }
                }
            }
        checked[count]=true;
        count++;
        }
        createMeshPolygons();
    }
        
    //checks if there is a strait line walk from point m to point p
    private boolean straitWalk(MeshPoint m, MeshPoint p){
        boolean canWalk = false;
        float c = m.distance(p);
        int mx = m.currentNode.x;
        int my = m.currentNode.y;
        int px = p.currentNode.x;
        int py = p.currentNode.y;
        MeshPoint currentWinner = m;
        options = new ArrayList();
        
        options.add(new MeshOption(floormap[mx-1][my],p));
        options.add(new MeshOption(floormap[mx-1][my+1],p));
        options.add(new MeshOption(floormap[mx-1][my-1],p));
        
        options.add(new MeshOption(floormap[mx+1][my],p));
        options.add(new MeshOption(floormap[mx+1][my+1],p));   
        options.add(new MeshOption(floormap[mx+1][my-1],p));
        
        options.add(new MeshOption(floormap[mx][my+1],p));
        options.add(new MeshOption(floormap[mx][my-1],p));

        Iterator<MeshOption> itr = options.iterator();
        while(itr.hasNext()){
            MeshOption mo = itr.next();
            if(c>mo.getDistance()){
                c = mo.getDistance();                
                currentWinner = new MeshPoint(mo.sNode);
            }
        }
        if(currentWinner.currentNode.type!=NodeType.FLOOR){
            return false;
        }
        if((currentWinner.currentNode.x==mx)&&(currentWinner.currentNode.y==my)){
            return false;
        }
        if((currentWinner.currentNode.x==px)&&(currentWinner.currentNode.y==py)){
            return true;
        }
        if((currentWinner.currentNode.type==NodeType.FLOOR) &&
                (!(currentWinner.currentNode.x==px)||!(currentWinner.currentNode.y==py))){
            //System.out.println("Type: "+currentWinner.currentNode.type);
            canWalk = straitWalk(currentWinner, p);
        }
        
        return canWalk;
    }
    
    public void createMeshPolygons(){
        MeshPoint[] polygon = new MeshPoint[3];
        for(MeshPoint p: mesh){
            polygon[0]= p;
            for(MeshOption o: p.options){
                int i = mesh.indexOf(o.endPoint);
                System.out.println("Got an i: "+i);
                polygon[1]= mesh.get(i);
                for(MeshOption e : o.endPoint.options){
                    for(MeshOption o2 : p.options)
                    if(o2.endPoint.sameNodeInside(e.endPoint)){
                    System.out.println("WE HAVE A POLYGON!");
                    }
                }
            }
        } 
    }
    
    
    
    public ArrayList<MeshPoint> getMesh(){
        return mesh;
    }
}
