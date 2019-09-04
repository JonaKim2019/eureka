package byow.Core;

import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

import byow.Core.ArrayList;
import java.util.Random;

public class LockedDoor {

    public static Position fillLockedDoor(TETile[][] wholeNewWorld,
                                          ArrayList<Room> roomList, InitialWorld world) {
        Position p = chooseLockedDoor(roomList, world);
        wholeNewWorld[p.getX()][p.getY()] = Tileset.LOCKED_DOOR;
        return p;
    }

    public static Position chooseLockedDoor(ArrayList<Room> roomList, InitialWorld world) {
        Random rand = new Random(world.getSeed());
        Position door;
        if (!filterRoomList(roomList).isEmpty()) {
            door = setLockedDooer(filterRoomList(roomList).
                    get(rand.nextInt(filterRoomList(roomList).size())), world);
        } else {
            door = setLockedDooer(roomList.get(Room.smallestRoom(roomList)), world);
        }
        return door;
    }

    private static Position setLockedDooer(Room r, InitialWorld world) {
        Random rand = new Random(world.getSeed());
        return new Position(r.getPos().getX() + rand.nextInt(r.getWidth() - 2) + 1,
                r.getPos().getY());
    }

    private static ArrayList<Room> filterRoomList(ArrayList<Room> roomList) {
        ArrayList<Room> filtered = new ArrayList<>();
        for (int i = 0; i < roomList.size(); i += 1) {
            if (roomList.get(i).getPos().getY() <= 3) {
                filtered.addLast(roomList.get(i));
            }
        }
        return filtered;
    }
}