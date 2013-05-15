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
    public static int wall_w = 2;
    public static int[] roomWidths = {50, 80, 100, 120, 160};
    public static int numRoomWidths = roomWidths.length;
    public static int[] roomHeights = {50, 80, 100, 120, 160};
    public static int numRoomHeights = roomHeights.length;
    public static int[] mapWidths = {100, 150, 200, 250, 300, 350, 400};
    public static int numMapWidths = mapWidths.length;
    public static int[] mapHeights = {100, 150, 200, 250, 300, 350, 400};
    public static int numMapHeights = mapHeights.length;
    public static Node[][] Nodes = new Node[img_h][img_w];
    public static int black = Integer.parseInt("F000000", 16);
    public static int silver = Integer.parseInt("FC0C0C0", 16);
    public static int white = Integer.parseInt("FFFFFFF", 16);


    public static void randomizeNodeMap() {
        int mapWidth = mapWidths[rand.nextInt(numMapWidths)];
        int mapHeight = mapHeights[rand.nextInt(numMapHeights)];
        Coord start = new Coord(rand.nextInt(img_w-mapWidth+1), rand.nextInt(img_h-mapHeight+1));
        int x_bound = start.x+mapWidth;
        int y_bound = start.y+mapHeight;
        for (int y = 0; y < img_h; y++) {
            for (int x = 0; x < img_w; x++) {
                if (x >= start.x && x < x_bound && y >= start.y && y < y_bound) {
                    if (isWall(x, y, x_bound, y_bound, start)) {
                        Nodes[x][y] = new Node(x, y, NodeType.WALL);
                    } else {
                        Nodes[x][y] = new Node(x, y, NodeType.FLOOR);
                    }
                } else {
                    Nodes[x][y] = new Node(x, y, NodeType.NOTHING);
                }
            }
        }
    }
    
    public BufferedImage generateRandomBMP() {
        randomizeNodeMap();
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
    
    public static boolean isWall(int x, int y, int x_bound, int y_bound, Coord start) {
        return (x >= start.x && x < start.x + wall_w) || (x >= x_bound-wall_w && x < x_bound) || 
                (y >= start.y && y < start.y + wall_w) || (y >= y_bound-wall_w && y < y_bound);
    }
        
}