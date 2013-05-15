/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg4511;

import java.awt.image.BufferedImage;
import java.util.Random;

/**
 *
 * @author blan0283
 */
public class CreateImage {

    /**
     * @param args the command line arguments
     */
    public static Random rand = new Random();
    public static int img_h = 400;
    public static int img_w = 400;
    public static int wall_w = 5;
    //public static Node[][] Nodes = new Node[img_h][img_w];
    public static int black = Integer.parseInt("FFFFFFF", 16);
    public static int silver = Integer.parseInt("FC0C0C0", 16);
    public static int white = Integer.parseInt("FFFFFFF", 16);

    public static void main(String[] args) {
        // TODO code application logic here
        //initializeNodeMap();
        //printMap();
        //System.out.println(ix.nextInt(10));
        

    }
    public static void initializeNodeMap(Node[][] Nodes) {
        for (int y = 0; y < img_h; y++) {
            for (int x = 0; x < img_w; x++) {
                if (isOuterWall(x, y)) {
                    Nodes[x][y] = new Node(x, y, NodeType.WALL);
                } else {
                    Nodes[x][y] = new Node(x, y, NodeType.FLOOR);
                }
            }
        }
    }
    public BufferedImage generateRandomBMP(Node[][] Nodes) {
        initializeNodeMap(Nodes);
        BufferedImage img = new BufferedImage(img_w, img_h, BufferedImage.TYPE_INT_RGB);
        for (int y = 0; y < img_h; y++) {
            for (int x = 0; x < img_w; x++) {
                NodeType type = Nodes[x][y].getType();
                if (type == NodeType.FLOOR) {
                    img.setRGB(x, y, silver);
                } else if (type == NodeType.WALL) {
                    img.setRGB(x, y, black);
                } else if (type == NodeType.NOTHING) {
                    img.setRGB(x, y, white);
                }
            }
        }
        return img;
    }
    public static void printMap(Node[][] Nodes) {
        for (int y = 0; y < img_h; y = y + wall_w) {
            for (int x = 0; x < img_w; x = x + wall_w) {
                NodeType type = Nodes[x][y].getType();
                if (type == NodeType.FLOOR) {
                    System.out.print("O ");
                } else if (type == NodeType.WALL) {
                    System.out.print("X ");
                } else if (type == NodeType.NOTHING) {
                    System.out.print("  ");
                }
            }
            System.out.println();
        }
    }
    
    public static boolean isOuterWall(int x, int y) {
        return (x >= 0 && x < wall_w) || (x >= img_w-wall_w && x < img_w) || 
                (y >= 0 && y < wall_w) || (y >= img_h-wall_w && y < img_h);
    }
        
}

        /*
         Graphics2D g2d = img.createGraphics();
         g2d.setColor(new Color(0, 212, 212)); //cyan for possible nodes



         }*/
        /*
         public void init() { 
         Image image = getImage(getCodeBase(), imageFile); 
         ImagePanel imagePanel = new ImagePanel(image); 
         getContentPane().add(imagePanel, BorderLayout.CENTER); 
         } 
         }
         /*
         class ImagePanel extends JPanel { 
         Image image;

         public ImagePanel(Image image) { 
         this.image = image; 
         }

         public void paintComponent(Graphics g) { 
         super.paintComponent(g);  // Paint background

         // Draw image at its natural size first. 
         g.drawImage(image, 0, 0, this); //85x62 image

         // Now draw the image scaled. 
         g.drawImage(image, 90, 0, 300, 62, this); 
         } */

