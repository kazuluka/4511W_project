/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg4511;

import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.TexturePaint;
import java.awt.image.BufferedImage;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.Random;


import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.Timer;
import javax.swing.AbstractButton;
import java.awt.Container;
import java.awt.Insets;
import java.awt.Dimension;
import javax.swing.JFileChooser;
import javax.swing.filechooser.*;


public class Main extends JPanel implements ActionListener {

  Random generator = new Random();
  public int pNodeCount=0;
  public int CameraCount=0;
  Node[][] Nodes = new Node[400][400]; //the array where the loaded image is mapped
//  Node[][] possibleNodes = new Node[400][400]; //these are possible nodes for camera placement
  Node[][] cameras = new Node[400][400]; //array where cameras are placed
  static private final String newline = "\n"; //Why?! --p
  Timer timer;
  public String wall = "wall";
  public String floor = "floor";
  public String nothing = "nothing";
  public String camera = "camera";
  public String possible = "possible";
  //private int y = 0;
  //private int x = 0;
  private int img_w;
  private int img_h;
  private int time=1;
  JButton b1 = new JButton("Open");
  JButton b2 = new JButton("Scan Nodes");
  JButton b3 = new JButton("Clear"); 
  JButton b4 = new JButton("Place Random"); 
  JFileChooser fc;
  File file = null;
  boolean run = false;
  boolean placeRandom = false;
  boolean addNode=false;
  boolean initialScan=false;
  BufferedImage bimage1 = null;
  Graphics g3d;
  public static void addComponentsToPane(Container pane) {
      
  }
  public Main() {
    timer = new Timer(time, this);
    timer.start();
    this.setLayout(null); 
    b1.setVerticalTextPosition(AbstractButton.CENTER);                                          //CREATING BUTTONS
    b1.setHorizontalTextPosition(AbstractButton.LEADING); //aka LEFT, for left-to-right locales
    b1.setMnemonic(KeyEvent.VK_D);
    b1.setActionCommand("Open");
    b1.setEnabled(true);
    add(b1);
    b2.setVerticalTextPosition(AbstractButton.CENTER);
    b2.setHorizontalTextPosition(AbstractButton.LEADING); //aka LEFT, for left-to-right locales
    b2.setMnemonic(KeyEvent.VK_D);
    b2.setActionCommand("Run");
    b2.setEnabled(true);
    add(b2);
    b3.setVerticalTextPosition(AbstractButton.CENTER);
    b3.setHorizontalTextPosition(AbstractButton.LEADING); //aka LEFT, for left-to-right locales
    b3.setMnemonic(KeyEvent.VK_D);
    b3.setActionCommand("Clear");
    b3.setEnabled(true);
    add(b3);
    b4.setVerticalTextPosition(AbstractButton.CENTER);
    b4.setHorizontalTextPosition(AbstractButton.LEADING); //aka LEFT, for left-to-right locales
    b4.setMnemonic(KeyEvent.VK_D);
    b4.setActionCommand("Random");
    b4.setEnabled(true);
    add(b4);
    b1.addActionListener(this);
    b2.addActionListener(this);
    b3.addActionListener(this);
    b4.addActionListener(this);
    b1.setBounds(90,425,80,20);
    b2.setBounds(170,425,110,20);
    b3.setBounds(280,425,110,20);
    b4.setBounds(90,445,125,20);
    fc = new JFileChooser();

  }

    public void paintComponent(Graphics g) {
            super.paintComponent(g); 

            Graphics2D g2d = (Graphics2D) g;
            g3d=g2d; //needed this for drawing within my coverage algorithm, kinda hacky
            g2d.setColor(new Color(0, 212, 212)); //cyan for possible nodes


            


            if (file != null){ //reading the bitmap in
            try {
                bimage1 = ImageIO.read(file);

            } catch (IOException ioe) {
                ioe.printStackTrace();
            }

            g2d.drawImage(bimage1,null,0,0);
            
            //Drawing 'possible nodes', if they exist:
            for(int y = 0; y < img_h; y++){
                for(int x = 0; x < img_w; x++){
                    if(Nodes[x][y].type==NodeType.POSSIBLE){
                        g2d.drawLine(x,y,x,y);
                    }
                }
            }
            
            
            
            
            
            
            img_w = bimage1.getWidth();
            img_h = bimage1.getHeight();
            }
            if(placeRandom){ //used for testing my coverage algorithm, not really random just looks for the first downward oriented node (x+100)
                int xRan = 0;   //this is specific to my floormap
                int yRan = 0;
                for (int y = 0; y < img_h-1; y++){
                for (int x = 0; x < img_w-1; x++){
                    //System.out.println("x: " + x);
                    //System.out.println("y: " + y);
                    if(Nodes[x][y].type == NodeType.POSSIBLE){
                    if(Nodes[x][y].getOri().equals("down")){
                        xRan = x;
                        yRan = y;
                        x = img_w-1;
                        y = img_h-1;
                    }
                    }
                }
                }
                g2d.setColor(new Color(220, 0, 0));
                g2d.drawLine(xRan+100, yRan, xRan+100, yRan);
                System.out.println("Placing random camera and calculating it's coverage at (" + (xRan+100) + "," + yRan + ")");
                System.out.println("Coverage for this camera is: " + fixed_coverage2(xRan+100,yRan,"down") + " nodes");
                placeRandom=false;
            }
            int pxColor=0;
            if(initialScan){ //maps the loaded image to a two dimensional array
                System.out.println("Running initial image scan.");
                //Blank out old map data, if applicable
                Nodes = new Node[400][400];
                for (int y = 0; y < img_h; y++){
                for (int x = 0; x < img_w; x++){
                    pxColor = bimage1.getRGB(x,y);
                    if(Integer.toHexString(pxColor).equals("ff000000")){ //This is wall
                        Nodes[x][y] = new Node(x,y, NodeType.WALL);
                    }
                    if(Integer.toHexString(pxColor).equals("ffc0c0c0")){ //This is floor
                        Nodes[x][y] = new Node(x,y, NodeType.FLOOR);
                    }
                    if(Integer.toHexString(pxColor).equals("ffffffff")){ //This is nothing
                        Nodes[x][y] = new Node(x,y, NodeType.NOTHING);
                    }
                }
                }
                initialScan=false;
            }
            String direction = "";
            if(run){                                    //This will mark and add to possibleNodes[] nodes that can become cameras.
            for (int y = 0; y < img_h-1; y++){          //Only floor nodes that are adjacent to walls are possible.
            for (int x = 0; x < img_w-1; x++){
                int rgb = bimage1.getRGB(x, y); //center
                //System.out.println(Integer.toHexString(rgb));
                int rgb2, rgb3, rgb4,rgb5;
                
                rgb2=0;
                rgb3=0;
                rgb4=0;
                rgb5=0;
                
                if(x!=img_w){
                rgb2 = bimage1.getRGB(x+1, y);
                }
                if(y!=img_h){
                rgb3 = bimage1.getRGB(x, y+1);
                }
                if (x!=0){
                rgb4 = bimage1.getRGB(x-1, y);
                }
                if (y!=0){
                rgb5 = bimage1.getRGB(x, y-1);    
                }
                if(Integer.toHexString(rgb).equals("ffc0c0c0") && rgb4 != rgb && x!=0){ //these if statements set the orientation of the possible camera
                    g2d.drawLine(x,y,x,y);                                              //node depending on which side the wall is
                    direction = "right";
                    addNode=true;
                }
                if(Integer.toHexString(rgb).equals("ffc0c0c0") && rgb5 != rgb && y!=0){
                    g2d.drawLine(x,y,x,y);
                    direction = "down";
                    addNode=true;
                }
                if(Integer.toHexString(rgb).equals("ffc0c0c0") && rgb2 != rgb){
                    g2d.drawLine(x,y,x,y);
                    direction = "left";
                    addNode=true;
                }
                if(Integer.toHexString(rgb).equals("ffc0c0c0") && rgb3 != rgb){
                    g2d.drawLine(x,y,x,y);
                    direction = "up";
                    addNode=true;
                }
                if(addNode){
                    System.out.println("Adding a node.");
                    Nodes[x][y].setType(NodeType.POSSIBLE);
                    Nodes[x][y].setOri(direction);
                    addNode=false;
                }
            }
            }
        }
            
            run = false;
    }
    
    private void clearPossibleCameraPositions(){
        for(int y = 0; y < img_h; y++){
          for(int x = 0; x < img_w; x++){
            if(Nodes[x][y].type==NodeType.POSSIBLE){
                Nodes[x][y].setType(NodeType.FLOOR);
            }
          }
        }
        
        
        
    }
    
    
    
//    private void findPotentialCameraLocations(){
//        String floor = "ffc0c0c0";
//        String wall = "ff000000";
//        String nothing = "ffffffff";
//        for(int x = 0; x<img_w; x++){
//            for(int y = 0; y<img_h; y++){
//                
//            }
//            
//        }
//        
//        
//        
//    }
    
    
    public int fixed_coverage2(int xCord, int yCord, String direction){ //this will calculate coverage for a node, given its coordinates and direction
        int covArea=0;                                                  //this is still very experimental, hence the lack of clean up
        int xLine = xCord;
        int yLine = yCord;
        int xFan = xCord;
        int yFan = yCord;
        int xFan_pos=xCord;
        int yy = 0;
        int xx = 0;
        int b = yCord;
        double m;
        double xTemp = 0;
        double yTemp = 0;
        double rad_angle = 0;
        int angle_count = 0;
        int color_grad=150;
        Node[] line = new Node[400];
        
        if(direction.equals("left")){ //going left - x is decrementing
            while(!(Nodes[xLine][yCord].getType().equals(wall))){
                xLine--;
            }
            
        }
        if(direction.equals("right")){ //going right - x is incrementing
            while(!(Nodes[xLine][yCord].getType().equals(wall))){
                xLine++;
            }
        }
        if(direction.equals("up")){ //going up - y is decrementing
            while(!(Nodes[xCord][yLine].getType().equals(wall))){
                yLine--;
            }
        }
        if(direction.equals("down")){ //going down - y is incrementing
            g3d.setColor(new Color(color_grad, 0, 0));
            for (angle_count = 45; angle_count <= 60 ; angle_count++){
                g3d.setColor(new Color(color_grad, 0, 0));
                color_grad=color_grad+5;
                rad_angle = angle_count*(Math.PI/180);
                m = Math.tan(rad_angle);
                System.out.println("angle_count: " + angle_count);

                //g3d.drawLine(xFan,yFan,angle_count,399);
                while(!(Nodes[xFan][yFan].getType().equals(wall)) && angle_count!=0){ //fan left
                    if(!(Nodes[xFan][yFan].getType().equals(nothing))){
                    covArea++;
                    g3d.drawLine(xFan,yFan,xFan,yFan);
                    }
                    xFan = xFan+1;
                    yFan = round(m*xFan-108);
                }
                xFan = xCord;
                yFan = yCord;
                while(!(Nodes[xFan][yFan].getType().equals(wall)) && angle_count!=0){ //fan left
                    if(!(Nodes[xFan][yFan].getType().equals(nothing))){
                    covArea++;
                    g3d.drawLine(xFan,yFan,xFan,yFan);
                    }
                    xFan_pos = xFan_pos+1;
                    xFan = xFan-1;
                    yFan = round(m*xFan_pos-109);
                }
                xFan = xCord;
                yFan = yCord;
                xFan_pos = xCord;
            }
        }
        //covArea = covArea - Math.abs()
        System.out.println("I have returned: " + covArea);
        return covArea;
    }
    
    public void calculateCoverage(int xPos, int yPos, int oriDeg, int fan){
        //orient == degrees from pure right
        //fan == number of degrees from center we're fanning out
//        assert(orient>=0);
//        assert(orient<(2*Math.PI));
        assert(xPos>=0);
        assert(xPos<img_w);
        assert(yPos>=0);
        assert(yPos<img_h);
//        ArrayList<Coord> inclusiveList = 
        for(int f = -1*fan; f<=fan; f++){
           double orient = degToRad((oriDeg+f));
           double xSlope = Math.cos(orient);
           double ySlope = Math.sin(orient);
           //By what value do we have to jump to get to the next round X value?
           //By what value... for Y?
           double xJump = 1.0 / xSlope;
           double yJump = 1.0 / ySlope;
           int xCur = xPos;
           int yCur = yPos;
           do{
               Nodes[xCur][yCur].setType(NodeType.COVERED);              
            
           }while(Nodes[xCur][yCur].type != NodeType.WALL && Nodes[xCur][yCur].type != NodeType.NOTHING);
        }
        
    }
    
    private double degToRad(int a){
        return (((double) a)/180.0) * Math.PI;
    }
    
    private int round(double d){ //rounding numbers traditionally
        double dAbs = Math.abs(d);
        int i = (int) dAbs;
        double result = dAbs - (double) i;
        if(result<0.5){
           return d<0 ? -i : i;            
        }else{
            return d<0 ? -(i+1) : i+1;          
        }
    }

    public static void main(String[] args) { //main function, intializes the gui

        JFrame frame = new JFrame("Security Coverage");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new Main());
        //frame.add(rects);
        frame.setSize(407, 500);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setResizable(false);


    }
    public void actionPerformed(ActionEvent e) { //listener used for button calls, button presses will set off the if statement with the corresponding
                                                // action command

    if ("Open".equals(e.getActionCommand())) {
            int returnVal = fc.showOpenDialog(Main.this);
 
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                file = fc.getSelectedFile();
                initialScan=true;
                repaint();
                //This is where a real application would open the file.
                System.out.println("Opening: " + file.getName() + "." + newline);
            } else {
                System.out.println("Open command cancelled by user." + newline);
            }
        }
    if ("Run".equals(e.getActionCommand())) {
            run = true;
            System.out.println("Scanning for possible camera positions");
            repaint();
        }
    if ("Random".equals(e.getActionCommand())) {
            placeRandom = true;
            System.out.println("Placing a random camera");
            repaint();
        }
    if ("Clear".equals(e.getActionCommand())) {
            run = false;
            System.out.println("Clearing possible camera positions");
            clearPossibleCameraPositions();
            repaint();
        }
  }
}
