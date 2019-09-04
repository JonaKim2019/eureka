package byow.Core;

import byow.TileEngine.TETile;

import java.io.Serializable;

import static byow.TileEngine.Tileset.*;

public class Key implements Serializable {
    protected Position p;
    TETile key;
    World world;
    boolean found = false;

    public Key(World world, Position keyPos) {
        this.world = world;
        p = keyPos;
        key = KEY;
        world.getWorld()[p.x][p.y] = key;
    }
    public void found() {
        found = true;
        Avatar av = world.getAvatar();
        world.getWorld()[av.p.x][av.p.y] = FLOOR;
        world.getWorld()[p.x][p.y] = AVATAR;
        av.setPos(p.x, p.y);
        world.door.unlock();
    }

}
