package byow.Core;

import byow.TileEngine.TETile;

import java.io.Serializable;

import static byow.TileEngine.Tileset.PORTAL;

public class Portal implements Serializable, Comparable<Portal> {
    protected Position p;
    protected Position jumpPos;
    TETile[][] world;
    public Portal(TETile[][] world, Position p, Position jumpPos) {
        this.world = world;
        this.p = p;
        this.jumpPos = jumpPos;
        world[p.x][p.y] = PORTAL;
    }
    @Override
    public int compareTo(Portal other) {
        int result = Integer.compare(p.x, other.p.x); //negative means less
        if (result == 0) {
            result = Integer.compare(p.y, other.p.y);
        }
        return result;
    }
    @Override
    public String toString() {
        return "Portal: " + p.toString();
    }
}


