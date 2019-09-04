//package byow.Core;
//
//import byow.TileEngine.TERenderer;
//import byow.TileEngine.TETile;
//import byow.TileEngine.Tileset;
//
//import java.util.*;
//
//import static byow.Core.Room.fillInsideRoom;
//import static byow.Core.Room.generateRoom;
//import static byow.TileEngine.Tileset.*;
//
//public class TestGame {
//    public static void main(String[] args) {
//        TERenderer ter = new TERenderer();
//        final int WIDTH = 80;
//        final int HEIGHT = 40;
//        // constraint to the number of rooms
//        final int ROOMS_TO_SCREEN = WIDTH * HEIGHT / 10; //100 = max area of a room
//        System.out.println(ROOMS_TO_SCREEN);
//        ter.initialize(WIDTH, HEIGHT);
//
//        TETile[][] world = new TETile[WIDTH][HEIGHT];
//        for (int x = 0; x < WIDTH; x++) {
//            for (int y = 0; y < HEIGHT; y++) {
//                world[x][y] = NOTHING;
//            }
//        }
//        Random r = new Random(312800); // works for 312800
//        Position p = new Position(r.nextInt(WIDTH-1),r.nextInt(HEIGHT-1));
//
//        //draw all the rooms
//        int numOfRooms = 15;
//        // r.nextInt((max - min) + 1) + min
//        for (int i = 0; i < 20; i++) {
//            int w = r.nextInt((10 - 5) + 1) + 5;
//            int h = r.nextInt((10 - 5) + 1) + 5;
//            if (!(p.y + h < 0 || p.y + h > HEIGHT || p.x + w < 0 || p.x + w > WIDTH )) {
//                generateRoom(world, p, w, h);
//                fillInsideRoom(world, p, w, h);
//                p.x = r.nextInt(WIDTH-1);
//                p.y = r.nextInt(HEIGHT-1);
//            }
//        }
//
//        // make hallways
//        for (int i = 0; i < 230; i ++) {
//            Position p1 = new Position(r.nextInt(WIDTH - 1), r.nextInt(HEIGHT - 1));
//            int w = r.nextInt((10 - 4) + 1) + 4;
//            int h = r.nextInt((10 - 4) + 1) + 4;
//            Position start = new Position(p1.x + w / 2, p1.y);
//            //end point of the upper horizontal wall
//            if (start.x + w < WIDTH - 2 && start.y + h < HEIGHT - 2) {
//                //vertical
//                for (int j = start.y ; j < start.y + h; j++) {
//
//                    world[start.x][j] = FLOOR;
//
//                }
//                //corner
//                world[start.x][start.y + h] = FLOOR;
//
//
//                //horizontal
//                for (int k = start.x + 1; k <= start.x + w; k++) {
//                    world[k][start.y + h] = FLOOR;
//
//                }
//            }
//        }
//        // make walls
//        for (int a = 0; a < WIDTH; a++) {
//            for (int b = 0; b < HEIGHT; b++) {
//                if (world[a][b].equals(NOTHING)) {
//                    world[a][b] = WALL;
//                }
//            }
//        }
//        // make walls along the edges horizontally
//        for (int a = 0; a < WIDTH; a++) {
//            if (world[a][1].equals(FLOOR)) {
//                world[a][1] = WALL;
//            }
//            if (world[a][HEIGHT-1].equals(FLOOR)) {
//                world[a][HEIGHT-1] = WALL;
//            }
//        }
//        // make walls along the edges vertically
//        for (int a = 0; a < HEIGHT; a++) {
//            if (world[0][a].equals(FLOOR)) {
//                world[0][a] = WALL;
//            }
//            if (world[WIDTH-1][a].equals(FLOOR)) {
//                world[WIDTH-1][a] = WALL;
//            }
//        }
//        // put in avatar
//        ArrayList<Position> array = new ArrayList<>();
//        HashMap<Position, Tileset> arrayMap = new HashMap<>();
//        for (int a = 0; a < WIDTH; a++) {
//            for (int b = 0; b < HEIGHT; b++) {
//                if (world[a][b].equals(NOTHING)) {
//                    world[a][b] = WALL;
//                }
//            }
//        }
//
//        //draw the hallways
////        Room currRoom = engine.rooms.remove();
////        while(!engine.rooms.isEmpty()) {
////            //System.out.println(currRoom);
////            Room nextRoom = engine.rooms.remove();
////            engine.connectRoom(world, currRoom, nextRoom);
////            currRoom = nextRoom;
////        }
//
//
//        ter.renderFrame(world);
//    }
//}
