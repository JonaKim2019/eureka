package byow.Core;

import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

import byow.Core.ArrayList;
import java.util.Random;

import static byow.TileEngine.Tileset.*;

public class CreateWorld {
    public static final int MAXROOM = 10;
    public static final int WIDTH = 80;
    public static final int HEIGHT = 30;

    // create corners
    public static void createCorner(TETile[][] world, Position corner) {
        for (int i = corner.getX() - 1; i <= corner.getX() + 1; i += 1) {
            for (int j = corner.getY() - 1; j <= corner.getY() + 1; j += 1) {
                world[i][j] = WALL;
            }
        }
        world[corner.getX()][corner.getY()] = FLOOR;
    }

    public static ArrayList<Room> drawRoom(TETile[][] wholeNewWorld,
                                           int roomNumber, InitialWorld world) {
        Random rand = new Random(world.getSeed() + 10);
        int maxW = 4;
        int maxH = 5;

        ArrayList<Room> roomList = new ArrayList<>();
        for (int i = 0; i < roomNumber; i += 1) {
            int roomWidth = rand.nextInt(maxW) + 3;
            int roomHeight = rand.nextInt(maxH) + 3;
            int roomPx = rand.nextInt(WIDTH - roomWidth);
            int roomPy = rand.nextInt(HEIGHT - roomHeight);
            Position startingPos = new Position(roomPx, roomPy);
            Room.generateRoom(wholeNewWorld, startingPos, roomWidth, roomHeight);
            Room newRoom = new Room(roomWidth, roomHeight, startingPos);
            roomList.addLast(newRoom);
        }
        return roomList;
    }

    public static Hallway drawLWay(TETile[][] wholeNewWorld, Room r1, Room r2, InitialWorld world) {
        Random rand = new Random(world.getSeed());

        Position p1 = Room.innerRandomPoint(r1, world);
        Position p2 = Room.innerRandomPoint(r2, world);
        //System.out.println("add one："+p2.xPos+", "+p1.xPos);
        int key = rand.nextInt(2);
        switch (key) {
            case 0: {  //draw horizontal way first.
                Position horizontalStart = Position.minX(p1, p2);
                Position horCornerPt = Hallway.addHorizontalHallway(wholeNewWorld, horizontalStart,
                        Math.abs(p2.getX() - p1.getX()));
                Position verticalStart = Position.minY(horCornerPt, Position.maxX(p1, p2));
                Hallway.addVerticalHallway(wholeNewWorld, verticalStart, Math.abs(p2.getY() - p1.getY()));
                createCorner(wholeNewWorld, horCornerPt);
                return new Hallway(horCornerPt, horizontalStart, Position.maxX(p1, p2), 0);
            }
            case 1: { //draw vertical way first.
                Position verticalStart = Position.minY(p1, p2);
                Position verCornerPt = Hallway.addVerticalHallway(wholeNewWorld, verticalStart,
                        Math.abs(p2.getY() - p1.getY()));
                Position horizontalStart = Position.minX(verCornerPt, Position.maxY(p1, p2));
                Hallway.addHorizontalHallway(wholeNewWorld, horizontalStart,
                        Math.abs(p2.getX() - p1.getX()));
                createCorner(wholeNewWorld, verCornerPt);
                return new Hallway(verCornerPt, verticalStart, Position.maxY(p1, p2), 1);
            }
            default: {
                return null;
            }
        }
    }

    public static Position fillPlayer(TETile[][] wholeNewWorld,
                                      ArrayList<Room> roomList, InitialWorld world) {
        Random rand = new Random(world.getSeed() + 1234);
        Position p = Room.innerRandomPoint(roomList.get(roomList.size()
                - rand.nextInt(roomList.size() - 1) - 1), world);
        if (p.getX() == LockedDoor.chooseLockedDoor(roomList, world).getX()) {
            p = Room.innerRandomPoint(roomList.get(rand.nextInt(roomList.size() - 3) + 3), world);
        }
        wholeNewWorld[p.getX()][p.getY()] = AVATAR;
        return p;
    }

    public static NewWorld generate(InitialWorld world) {
        long se = world.getSeed();
        Random rand = new Random(se + 1584);

        TETile[][] wholeNewWorld = new TETile[WIDTH][HEIGHT];
        // initialize wholeNewWorld
        for (int x = 0; x < WIDTH; x += 1) {
            for (int y = 0; y < HEIGHT; y += 1) {
                wholeNewWorld[x][y] = Tileset.NOTHING;
            }
        }

        int roomNumber = rand.nextInt(MAXROOM) + 8;
        ArrayList<Room> roomList = drawRoom(wholeNewWorld, roomNumber, world);
        roomList = Room.sortRooms(roomList);

        ArrayList<Hallway> hallwayList = new ArrayList<>();
        for (int i = 0; i < roomList.size() - 1; i += 1) {
            hallwayList.addLast(drawLWay(wholeNewWorld, roomList.get(i), roomList.get(i + 1), world));
        }
        Room.fillRoomList(wholeNewWorld, roomList);
        Hallway.fillLHallwayList(wholeNewWorld, hallwayList);
        Position lockedDoor = LockedDoor.fillLockedDoor(wholeNewWorld, roomList, world);
        Position player = fillPlayer(wholeNewWorld, roomList, world);
        NewWorld nw = new NewWorld(wholeNewWorld, lockedDoor, player);
        return nw;
    }
}
