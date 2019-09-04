package byow.Core;

import byow.TileEngine.TETile;
import edu.princeton.cs.introcs.StdDraw;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Random;
import java.util.List;


import static byow.TileEngine.Tileset.NOTHING;
import static byow.TileEngine.Tileset.FLOOR;
import static byow.TileEngine.Tileset.WALL;
import static java.awt.Color.black;

public class World implements Serializable {
    public static final int WINDWIDTH = 60;
    public static final int WINDHEIGHT = 40;
    private static final int ROOMBOUND = 10; // min room width / height
    //100 = max area of a room
    public static final int ROOMS_TO_SCREEN = WINDWIDTH * WINDHEIGHT / 100;
    private final int HALL_WIDTH = 3;

    protected TETile[][] world;
    private Random random;
    private Avatar avatar;
    protected PriorityQueue portals;
    protected Door door;
    protected Key key;
    protected ArrayList<Position> floorList;
    protected ArrayList<Position> wallList;
    protected boolean gameOver;

    /**
     * order the rooms to be able to connect hallways in a systematic fashion
     */
    protected PriorityQueue<Room> rooms;

    public World(Random random) {
        gameOver = false;
        this.random = random;
        this.world = new TETile[WINDWIDTH][WINDHEIGHT];
        floorList = new ArrayList<>();
        wallList = new ArrayList<>();

        drawEmptyWorld();

        int numOfRooms = random.nextInt(ROOMBOUND) + 10;
        drawRooms(numOfRooms);
        drawHalls();
        //Make avatar and door

        avatar = new Avatar(this, getRandomPos(floorList));
        key = new Key(this, getRandomPos(floorList));
        door = new Door(world, getRandomPos(wallList));

        //Make portals
        portals = new PriorityQueue();
        for (int i = 0; i < 5; i++) {
            Portal portal = new Portal(world, getRandomPos(floorList), getRandomPos(floorList));
            portals.add(portal);
        }
    }
    public Portal choosePortal(Position startPortal) {
        // TERenderer ter = new TERenderer();
        PriorityQueue<Portal> otherPortals = new PriorityQueue<>(portals);
        otherPortals.remove(startPortal);
        drawMessageFrame("Choose which portal to go to", 0);
        StdDraw.pause(1500);
        while(true) {
            StdDraw.clear(black);
            //ter.renderFrame(world);
            drawMessageFrame("Please enter digit  1 - " + otherPortals.size(), 0);
            drawMessageFrame("Portals are ranked from left to right", 1);
            if(StdDraw.hasNextKeyTyped()) {
                char input = StdDraw.nextKeyTyped();
                //if the input digit is less than the number of portals
                int portalNum = Character.getNumericValue(input);
                StdDraw.clear(black);
                //System.out.println("input = " + portalNum + " / portal size=" + otherPortals.size());
                if(portalNum <= portals.size() && input > '0') {
                    drawMessageFrame("Traveling to Portal " + portalNum, 10);
                    StdDraw.pause(1000);
                    int i = 1;
                    for(Portal p : otherPortals) {
                        if(i == portalNum) {
                            portals.remove(p);
                            return p;
                        }
                        i += 1;
                    }
                } else {
                    drawMessageFrame("Won't go into the portal", 0);
                    StdDraw.pause(1000);
                    break;
                }
            }
        }
        StdDraw.clear(black);
        return null;


    }
    public void drawMessageFrame(String message, int lowerHeight) {
        int width = WINDWIDTH / 2;
        int height = WINDHEIGHT - 2 - lowerHeight;
        // Draw the actual text
        StdDraw.text(width, height, message);
        StdDraw.show();
    }

    public Position getRandomPos(List<Position> posList) {
        Position p = posList.get(random.nextInt(posList.size()));
        if (!world[p.x][p.y].equals(FLOOR)) {
            getRandomPos(posList);
        }
        return p;
    }

    //For loading the avatar
    public void setAvatar(Avatar avatar) {
        Position p = this.avatar.getPos();
        world[p.x][p.y] = FLOOR;
        this.avatar = avatar;
    }
    public void gameOver() {
        gameOver = true;
    }

    public Avatar getAvatar() {
        return avatar;
    }
    public Door getDoor() {
        return door;
    }

    public TETile[][] getWorld() {
        return world;
    }

    // Create an empty world
    public void drawEmptyWorld() {
        for (int i = 0; i < WINDWIDTH; i++) {
            for (int j = 0; j < WINDHEIGHT; j++) {
                world[i][j] = NOTHING;
            }
        }
    }

    // fill in the array list of walls
    public void cleanWallList() {
        ArrayList<Position> copy = new ArrayList<>(wallList);
        for (Position p : copy) {
            if (floorList.contains(p) || wallIsCorner(p)) {
                wallList.remove(p);
            }
        }
    }

    public boolean wallIsCorner(Position p) {
        return wallList.contains(new Position(p.x + 1, p.y)) && wallList.contains(new Position(p.x, p.y - 1))
                || wallList.contains(new Position(p.x + 1, p.y)) && wallList.contains(new Position(p.x, p.y + 1))
                || wallList.contains(new Position(p.x - 1, p.y)) && wallList.contains(new Position(p.x, p.y - 1))
                || wallList.contains(new Position(p.x - 1, p.y)) && wallList.contains(new Position(p.x, p.y + 1));
    }


    ///////////////////////////////////////////////////////////////////////////////////////////////
    //Drawing rooms
    ///////////////////////////////////////////////////////////////////////////////////////////////
    /**
     * From this point on, all methods relate to drawing rooms,
     * halls, and variety of hallway
     * @param numOfRooms
     */
    public void drawRooms(int numOfRooms) {
        rooms = new PriorityQueue<>();
        for (int i = 0; i < Math.min(numOfRooms, ROOMS_TO_SCREEN); i++) {
            drawRoom(random);
        }
    }


    // Draw rooms in the world
    public void drawRoom(Random rand) {
        this.random = rand;
        Position p = new Position(rand.nextInt(WINDWIDTH - 1),
                rand.nextInt(WINDHEIGHT - 1));
        Room r = genRoom(world, p);
        //organize rooms in priority queue
        rooms.add(r);
        // p is always start at bottom left of the room
        // Draws wall
        for (int i = p.x; i < p.x + r.width; i++) {
            world[i][p.y] = WALL;
            world[i][p.y + r.height - 1] = WALL;
            wallList.add(new Position(i, p.y));
            wallList.add(new Position(i, p.y + r.height - 1));
        }
        for (int j = p.y; j < p.y + r.height; j++) {
            world[p.x][j] = WALL;
            world[p.x + r.width - 1][j] = WALL;
            wallList.add(new Position(p.x, j));
            wallList.add(new Position(p.x + r.width - 1, j));
        }

        // Draws floor
        for (int i = p.x + 1; i < p.x + r.width - 1; i++) {
            for (int j = p.y + 1; j < p.y + r.height - 1; j++) {
                world[i][j] = FLOOR;
                floorList.add(new Position(i, j));
            }
        }
    }

    public Room genRoom(TETile[][] w, Position p) {
        Room r = new Room(p);
        while (r.width < 5 || r.height < 5) {
            // Bounds in the RANDOM.nextInt() will restrict max size of the room
            // width and height are the min length of the size of the
            r.width = random.nextInt(ROOMBOUND);
            r.height = random.nextInt(ROOMBOUND);
        }
        if (!r.validRoom(w)) {
            p.x = random.nextInt(WINDWIDTH);
            p.y = random.nextInt(WINDHEIGHT);
            r = genRoom(w, p);
        }
        return r;
    }

    public void connectRoom(Room currRoom, Room nextRoom) {
        int xDist = currRoom.p.x + currRoom.width - nextRoom.p.x;
        int yDist = currRoom.p.y + currRoom.height - nextRoom.p.y;
        if (xDist < yDist) { //connect bottom/right room
            if (xDist > 0) { // room is under
                if (xDist < HALL_WIDTH) {
                    //draw L shape from bottom of currRoom - > left of nextRoom
                    drawLDR(currRoom, nextRoom);
                } else {
                    //connect straight from bottom of currRoom -> top of nextRoom
                    drawVertL(currRoom, nextRoom);
                }

            } else { // room is on the right
                if (yDist < HALL_WIDTH) {
                    //draw L shape from right of currRoom -> bottom of nextRoom
                    drawLRU(currRoom, nextRoom);
                } else if (yDist < currRoom.height) {
                    //draw straight from right of currRoom -> left of nextRoom
                    drawHoriz(currRoom, nextRoom);
                } else {
                    //draw L shape from bottom of currRoom - > left of nextRoom
                    drawLDR(currRoom, nextRoom);
                }
            }
        } else { // connect top/right room
            if (yDist > 0) { //room is right
                if (yDist < HALL_WIDTH) {
                    //draw L shape from top of currRoom -> left of nextRoom
                    drawLUR(currRoom, nextRoom);
                } else {
                    //draw straight from right of currRoom - > left of nextRoom
                    drawHoriz(currRoom, nextRoom);
                }

            } else { // room is on top
                if (xDist < HALL_WIDTH) {
                    //draw L shape from top of currRoom -> left of nextRoom
                    drawLUR(currRoom, nextRoom);
                } else {
                    //draw straight from top of currRoom -> bottom of nextRoom
                    drawVertU(currRoom, nextRoom);
                }
            }
        }
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////
    //Drawing walls
    ///////////////////////////////////////////////////////////////////////////////////////////////
    //put a wall if there is no floor
    public void drawHalls() {
        Room currRoom = rooms.remove();
        while (!rooms.isEmpty()) {
            //System.out.println(currRoom);
            Room nextRoom = rooms.remove();
            connectRoom(currRoom, nextRoom);
            currRoom = nextRoom;
        }
        cleanWallList();
    }
    private void putWall(TETile[][] w, int x, int y) {
        w[x][y] = w[x][y].insertWall();
    }

    /**
     * draw a L shaped from Upper of current room to the left side
     * of the next (Right)room
     *
     * @param currRoom : starting room
     * @param nextRoom : ending room
     */
    private void drawLUR(Room currRoom, Room nextRoom) {
        //starting left point of the vertical wall
        Position start = new Position(currRoom.p.x + currRoom.width / 2, currRoom.p.y);
        //end point of the upper horizontal wall
        Position end = new Position(nextRoom.p.x, nextRoom.p.y + nextRoom.height / 2);
        //vertical
        for (int i = start.y + currRoom.height - 1; i < end.y - 1; i++) {
            putWall(world, start.x, i);
            world[start.x + 1][i] = FLOOR;
            putWall(world, start.x + 2, i);
        }
        //corner
        putWall(world, start.x, end.y - 1);
        world[start.x + 1][end.y - 1] = FLOOR;
        putWall(world, start.x, end.y);
        putWall(world, start.x + 1, end.y);

        //horizontal
        for (int i = start.x + 2; i <= end.x; i++) {
            putWall(world, i, end.y);
            world[i][end.y - 1] = FLOOR;
            putWall(world, i, end.y - 2);
        }
    }

    /**
     * draw an L shaped hall from bottom of currRoom to left of nextRoom
     *
     * @param currRoom
     * @param nextRoom
     */
    private void drawLDR(Room currRoom, Room nextRoom) {
        //starting left point of the vertical wall
        Position start = new Position(currRoom.p.x + currRoom.width / 2, currRoom.p.y);
        //end point of the upper horizontal wall
        Position end = new Position(nextRoom.p.x, nextRoom.p.y + nextRoom.height / 2);
        //vertical
        for (int i = start.y; i > end.y + 1; i--) {
            putWall(world, start.x, i);
            world[start.x + 1][i] = FLOOR;
            putWall(world, start.x + 2, i);
        }
        //corner
        putWall(world, start.x, end.y + 1);
        putWall(world, start.x, end.y);
        world[start.x + 1][end.y + 1] = FLOOR;
        putWall(world, start.x + 1, end.y);
        //horizontal
        for (int i = start.x + 2; i <= end.x; i++) {
            putWall(world, i, end.y);
            world[i][end.y + 1] = FLOOR;
            putWall(world, i, end.y + 2);
        }
    }

    /**
     * draw L shaped hall from rightside of currRoom -> bottom of nextRoom
     * it goes right then up
     *
     * @param currRoom
     * @param nextRoom
     */
    private void drawLRU(Room currRoom, Room nextRoom) {
        Position start = new Position(currRoom.p.x + currRoom.width - 1,
                currRoom.p.y + currRoom.width / 2 - 1);
        //end point of the right vertical wall
        Position end = new Position(nextRoom.p.x + nextRoom.width / 2,
                nextRoom.p.y - 1);

        //horizontal
        for (int i = start.x; i < end.x; i++) {
            putWall(world, i, start.y);
            world[i][start.y + 1] = FLOOR;
            putWall(world, i, start.y + 2);
        }
        //corner
        putWall(world, end.x - 1, start.y);
        world[end.x - 1][start.y + 1] = FLOOR;
        putWall(world, end.x, start.y);
        putWall(world, end.x, start.y + 1);
        //vertical
        for (int i = start.y + 2; i < end.y + 2; i++) {
            putWall(world, end.x, i);
            world[end.x - 1][i] = FLOOR;
            putWall(world, end.x - 2, i);
        }

    }


    private void drawVertU(Room currRoom, Room nextRoom) {
        int xDist = currRoom.p.x + currRoom.width - nextRoom.p.x;
        xDist = currRoom.width - xDist;
        Position start = new Position(currRoom.p.x + xDist, currRoom.p.y);
        for (int i = start.y + currRoom.height - 1; i < nextRoom.p.y + 1; i++) {
            putWall(world, start.x, i);
            world[start.x + 1][i] = FLOOR;
            putWall(world, start.x + 2, i);
        }
    }

    private void drawVertL(Room currRoom, Room nextRoom) {
        int xDist = nextRoom.p.x - currRoom.p.x;
        Position start = new Position(currRoom.p.x + xDist, currRoom.p.y);
        for (int i = start.y; i > nextRoom.p.y + nextRoom.height - 2; i--) {
            putWall(world, start.x, i);
            world[start.x + 1][i] = FLOOR;
            putWall(world, start.x + 2, i);
        }
    }

    private void drawHoriz(Room currRoom, Room nextRoom) {
        int yDist = currRoom.p.y + currRoom.height - nextRoom.p.y - 1;
        yDist = currRoom.height - yDist + 1;
        if (currRoom.p.y > nextRoom.p.y) {
            yDist = nextRoom.p.y + nextRoom.height - currRoom.p.y - 1;
        }
        Position start = new Position(currRoom.p.x + currRoom.width - 1, currRoom.p.y + yDist);
        for (int i = start.x; i <= nextRoom.p.x; i++) {
            putWall(world, i, start.y - 2);
            world[i][start.y - 1] = FLOOR;
            putWall(world, i, start.y);
        }
    }

}
