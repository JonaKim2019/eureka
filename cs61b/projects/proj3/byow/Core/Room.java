package byow.Core;

import byow.TileEngine.TETile;

import byow.Core.ArrayList;
import java.util.Random;

import static byow.Core.Engine.WINDHEIGHT;
import static byow.Core.Engine.WINDWIDTH;
import static byow.TileEngine.Tileset.*;

public class Room implements Comparable<Room>{
    protected Position p;
    protected int width;
    protected int height;


    Room(Position p) {
        width = 0;
        height = 0;
        this.p = p;
    }
    Room(int w, int h, Position p) {
        width = w;
        height = h;
        this.p = p;
    }
    public Position getPos() {
        return this.p;
    }

    public int getHeight() {
        return this.height;
    }

    public int getWidth() {
        return this.width;
    }

    /**
     * want to break ties with the y position
     * @param other
     * @return
     */
    @Override
    public int compareTo(Room other) {
        int result = Integer.compare(p.x, other.p.x); //negative means less
        if(result == 0) {
            result = Integer.compare(p.y, other.p.y);
        }
        return result;
    }

    // checks if the next room can be drawn at position p
    public boolean validRoom(TETile[][] world) {

        // Check if room is within bounds
        if (p.y + height < 0 || p.y + height > WINDHEIGHT
                || p.x + width < 0 || p.x + width > WINDWIDTH ) {
            return false;
        }

        // Checks for overlapping
        for (int i = Math.min(p.x, width + p.x ); i < Math.max(p.x, width + p.x ); i++) {
            for (int j = Math.min(p.y, height + p.y); j < Math.max(p.y, height + p.y); j++) {
                if (!(world[i][j].equals(NOTHING))) {

                }
            }
        }
        return true;
    }

    @Override
    public String toString() {
        return "room: width = " + width + "\n    : height = "
                + height + "\n    : position = " + p.toString();
    }

    public static void generateRoom(TETile[][] world, Position p, int w, int h) {
        for (int x = p.getX(); x < p.getX() + w; x += 1) {
            world[x][p.getY()] = WALL;
            world[x][p.getY() + h - 1] = WALL;
        }
        for (int y = p.getY(); y < p.getY() + h; y += 1) {
            world[p.getX()][y] = WALL;
            world[p.getX() + w - 1][y] = WALL;
        }
    }
    public static Position innerRandomPoint(Room r, InitialWorld world) {
        Random random = new Random(world.getSeed());
        int innerX = random.nextInt(r.width - 2) + r.p.getX() + 1;
        int innerY = random.nextInt(r.height - 2) + r.p.getY() + 1;
        Position innerPosition = new Position(innerX, innerY);
        return innerPosition;
    }
    private static void fillInsideRoom(TETile[][] world, Room r) {
        for (int x = 1; x < r.width - 1; x += 1) {
            for (int y = 1; y < r.height - 1; y += 1) {
                world[r.p.getX() + x][r.p.getY() + y] = FLOOR;
            }
        }
    }
    public static void fillRoomList(TETile[][] world, ArrayList<Room> roomList) {
        for (int i = 0; i < roomList.size(); i += 1) {
            fillInsideRoom(world, roomList.get(i));
        }
    }

    public static ArrayList<Room> sortRooms(ArrayList<Room> roomList) {
        ArrayList<Room> newRoomList = new ArrayList<>();
        int roomListSize = roomList.size();
        for (int i = 0; i < roomListSize; i += 1) {
            int minRoom = smallestRoom(roomList);
            newRoomList.addLast(roomList.remove(minRoom));
        }
        return newRoomList;
    }
    public static int smallestRoom(ArrayList<Room> roomList) {
        int min = 110; int minIndex = 0;
        for (int i = 0; i < roomList.size(); i += 1) {
            int positSum = roomList.get(i).p.getX() + roomList.get(i).p.getY();
            if (positSum < min) {
                min = positSum;
                minIndex = i;
            }
        }
        return minIndex;
    }

}
