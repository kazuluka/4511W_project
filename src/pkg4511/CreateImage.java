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
    public static int[] mapWidths = {210, 250, 290, 330, 370, 400};
    public static int numMapWidths = mapWidths.length;
    public static int[] map1Heights = {250, 300, 350, 390};
    public static int numMap1Heights = map1Heights.length;
    public static int[] map2Heights = {150, 160, 170, 180, 190};
    public static int numMap2Heights = map2Heights.length;
    public static int[] map3Heights = {90, 100, 110, 120};
    public static int numMap3Heights = map3Heights.length;
    public static Node[][] Nodes = new Node[img_h][img_w];
    public static int black = Integer.parseInt("F000000", 16);
    public static int silver = Integer.parseInt("FC0C0C0", 16);
    public static int white = Integer.parseInt("FFFFFFF", 16);
    public static int numPossibleSegments = 3;


    public static void randomizeMap() {
        int numSegments = rand.nextInt(numPossibleSegments);
        int[] mapHeights;
        int numMapHeights;
        if (numSegments == 0) {
            mapHeights = map1Heights;
            numMapHeights = numMap1Heights;
        } else if (numSegments == 1) {
            mapHeights = map2Heights;
            numMapHeights = numMap2Heights;
        } else {
            mapHeights = map3Heights;
            numMapHeights = numMap3Heights;
        }
        int mapWidth = mapWidths[rand.nextInt(numMapWidths)];
        int mapHeight = mapHeights[rand.nextInt(numMapHeights)];
        Coord start = new Coord(rand.nextInt(img_w-mapWidth+1), 5);
        randomizeFrame(mapWidth, mapHeight, start);
        start.x = rand.nextInt(img_w-mapWidth+1);
        start.y += mapHeight-wall_w;
        for (int i = 0; i < numSegments ; i++) {
            mapWidth = mapWidths[rand.nextInt(numMapWidths)];
            mapHeight = mapHeights[rand.nextInt(numMapHeights)];
            randomizeFrame(mapWidth, mapHeight, start);
            start.x = rand.nextInt(img_w-mapWidth+1);
            start.y += mapHeight-wall_w;
        }
        removeInteriorWalls();
    }
    public static void initializeMap() {
        for (int y = 0; y < img_h; y++) {
            for (int x = 0; x < img_w; x++) {
                Nodes[x][y] = new Node(x, y, NodeType.NOTHING);
            }
        }
    }
    
    public static void removeInteriorWalls() {
        for (int y = wall_w+1; y < img_h-wall_w; y++) {
            for (int x = wall_w+1; x < img_w-wall_w; x++) {
                if (Nodes[x][y].type == NodeType.WALL && interiorWall(x, y)) {
                    Nodes[x][y] = new Node(x, y, NodeType.FLOOR);
                }
            }
        }
    }
    public static boolean interiorWall(int x, int y) {
        Node adjXleft1 = Nodes[x-wall_w-1][y];
        Node adjXright1 = Nodes[x+wall_w][y];
        Node adjXleft2 = Nodes[x-wall_w][y];
        Node adjXright2 = Nodes[x+wall_w-1][y];
        Node adjYup1 = Nodes[x][y-wall_w-1];
        Node adjYdown1 = Nodes[x][y+wall_w];
        Node adjYup2 = Nodes[x][y-wall_w];
        Node adjYdown2 = Nodes[x][y+wall_w-1];

        return ((adjXleft1.type == NodeType.FLOOR && adjXright1.type == NodeType.FLOOR) ||
                (adjXleft2.type == NodeType.FLOOR && adjXright2.type == NodeType.FLOOR) ||
                (adjYup1.type == NodeType.FLOOR && adjYdown1.type == NodeType.FLOOR) ||
                (adjYup2.type == NodeType.FLOOR && adjYdown2.type == NodeType.FLOOR));
    }
    public static void randomizeFrame(int mapWidth, int mapHeight, Coord start) {
        int x_bound = start.x + mapWidth;
        int y_bound = start.y + mapHeight;
        for (int y = 0; y < img_h; y++) {
            for (int x = 0; x < img_w; x++) {
                if (x >= start.x && x < x_bound && y >= start.y && y < y_bound) {
                    if (isWall(x, y, x_bound, y_bound, start)) {
                        Nodes[x][y] = new Node(x, y, NodeType.WALL);
                    } else {
                        Nodes[x][y] = new Node(x, y, NodeType.FLOOR);
                    }
                } 
            }
        }
    }
    
    public BufferedImage generateRandomBMP() {
        initializeMap();
        randomizeMap();
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