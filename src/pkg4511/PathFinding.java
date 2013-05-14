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
    ArrayList<Node> path = new ArrayList(4000); //array for painting given paths
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

                
                if((i>=2)&&(j>=2)){
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
                    checkNodes[0][0] = floormap[i-2][j-2];
                    checkNodes[1][0] = floormap[i-1][j-2];
                    checkNodes[2][0] = floormap[i][j-2];
                    checkNodes[0][1] = floormap[i-2][j-1];
                    checkNodes[1][1] = floormap[i-1][j-1];
                    checkNodes[2][1] = floormap[i][j-1];
                    checkNodes[0][2] = floormap[i-2][j];
                    checkNodes[1][2] = floormap[i-1][j];
                    checkNodes[2][2] = floormap[i][j];
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
                        System.out.println("Should be adding connections");
                        System.out.println("M: (" + m.currentNode.x + ", " + m.currentNode.y + ")");
                        System.out.println("P: (" + p.currentNode.x + ", " + p.currentNode.y + ")");
                        m.addOption(p.currentNode);
                        p.addOption(m.currentNode);                        
                    }
                }
            }
        checked[count]=true;
        count++;
        }        
    }
    
    private float distance(MeshPoint m, MeshPoint p){
        int mx = m.currentNode.x;
        int my = m.currentNode.y;
        int px = p.currentNode.x;
        int py = p.currentNode.y;
        float a = mx-px;
        float b = my-py;
        if (a==0 && b==0){
            return 0;
        }
        float c = (float)Math.sqrt(a*a + b*b);
        return c;
    }
    
    //checks if there is a strait line walk from point m to point p
    private boolean straitWalk(MeshPoint m, MeshPoint p){
        //System.out.println("checking how often I am here");
        //System.out.println("M: (" + m.currentNode.x + ", " + m.currentNode.y + ")");
        //System.out.println("P: (" + p.currentNode.x + ", " + p.currentNode.y + ")");
        boolean canWalk = false;
        float c = distance(m,p);
        int mx = m.currentNode.x;
        int my = m.currentNode.y;
        int px = p.currentNode.x;
        int py = p.currentNode.y;
        MeshPoint currentWinner = m;
        options = new ArrayList();
        // <editor-fold defaultstate="collapsed" desc="Switch">
        switch (m.position){
            case("BR"):
                options.add(new MeshOption(floormap[mx-1][my],p.currentNode));
                options.add(new MeshOption(floormap[mx-1][my+1],p.currentNode));
                options.add(new MeshOption(floormap[mx+1][my],p.currentNode));
               options.add(new MeshOption(floormap[mx+1][my+1],p.currentNode));   
            case("M1"):
                options.add(new MeshOption(floormap[mx+1][my],p.currentNode));
                options.add(new MeshOption(floormap[mx][my+1],p.currentNode));
                options.add(new MeshOption(floormap[mx+1][my+1],p.currentNode));
                break;
            case("BL"):
                options.add(new MeshOption(floormap[mx-1][my],p.currentNode));
                options.add(new MeshOption(floormap[mx-1][my-1],p.currentNode));
                options.add(new MeshOption(floormap[mx][my+1],p.currentNode));
                options.add(new MeshOption(floormap[mx+1][my+1],p.currentNode));
            case("M2"):
                options.add(new MeshOption(floormap[mx+1][my],p.currentNode));
                options.add(new MeshOption(floormap[mx][my-1],p.currentNode));
                options.add(new MeshOption(floormap[mx+1][my-1],p.currentNode));
                break;
            case("TL"):
                options.add(new MeshOption(floormap[mx+1][my],p.currentNode));
                options.add(new MeshOption(floormap[mx+1][my],p.currentNode));
                options.add(new MeshOption(floormap[mx][my+1],p.currentNode));
                options.add(new MeshOption(floormap[mx-1][my+1],p.currentNode));
            case("M4"):
                options.add(new MeshOption(floormap[mx-1][my],p.currentNode));
                options.add(new MeshOption(floormap[mx][my-1],p.currentNode));
                options.add(new MeshOption(floormap[mx-1][my-1],p.currentNode));
                break;
            case("TR"):
                options.add(new MeshOption(floormap[mx][my-1],p.currentNode));
                options.add(new MeshOption(floormap[mx-1][my-1],p.currentNode));
                options.add(new MeshOption(floormap[mx+1][my],p.currentNode));
                options.add(new MeshOption(floormap[mx+1][my+1],p.currentNode));
            case("M3"):
                options.add(new MeshOption(floormap[mx-1][my],p.currentNode));
                options.add(new MeshOption(floormap[mx-1][my+1],p.currentNode));
                options.add(new MeshOption(floormap[mx][my+1],p.currentNode));
                break;
            }
        //</editor-fold>
        Iterator<MeshOption> itr = options.iterator();
        while(itr.hasNext()){
            MeshOption mo = itr.next();
            //System.out.println("MO: (" + mo.startPoint.x + ", " + mo.startPoint.y + ")");
            if(c>mo.getDistance()){
                c = mo.getDistance();                
                currentWinner = new MeshPoint(mo.startPoint, m.direction, m.position);
            }
        }
        if((currentWinner.currentNode.x==mx)&&(currentWinner.currentNode.y==my)){
            return false;
        }
        if(currentWinner.currentNode.type!=NodeType.FLOOR){
            return false;
        }
        if((currentWinner.currentNode.type==NodeType.FLOOR) &&
                (!(currentWinner.currentNode.x==px)||!(currentWinner.currentNode.y==py))){
            canWalk = straitWalk(currentWinner, p);
        }else if((currentWinner.currentNode.x==px)&&(currentWinner.currentNode.y==py)){
            return true;
        }  
        
        return canWalk;
    }
    

    
    public ArrayList<MeshPoint> getMesh(){
        return mesh;
    }
}
