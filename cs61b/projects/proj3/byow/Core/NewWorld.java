package byow.Core;

import byow.TileEngine.TETile;

public class NewWorld{
    private Position lockedDoor;
    private Position avatar;
    private TETile[][] world;

    // lD is for locked door
    public NewWorld(TETile[][] w, Position lD, Position a) {
        lockedDoor = lD;
        avatar = a;
        world = w;
    }

    public Position getLockedDoor() {
        return lockedDoor;
    }

    public Position getAvatar() {
        return avatar;
    }

    public TETile[][] getWorld() {
        return world;
    }
}