package byow.Core;

import byow.TileEngine.TETile;

import java.io.Serializable;

import static byow.TileEngine.Tileset.LOCKED_DOOR;
import static byow.TileEngine.Tileset.UNLOCKED_DOOR;

public class Door implements Serializable {
    protected Position p;
    TETile door;
    TETile[][] world;
    public Door(TETile[][] world, Position end) {
        this.world = world;
        p = end;
        door = LOCKED_DOOR;
        world[p.x][p.y] = door;
    }

    public void unlock() {
        world[p.x][p.y] = UNLOCKED_DOOR;
    }
    public boolean unlocked() {
        return !door.equals(UNLOCKED_DOOR);
    }
}
