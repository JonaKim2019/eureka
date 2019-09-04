package byow.Core;

import byow.TileEngine.TETile;

import java.io.Serializable;

import static byow.Core.Engine.WINDHEIGHT;
import static byow.Core.Engine.WINDWIDTH;
import static byow.TileEngine.Tileset.*;

public class Room implements Comparable<Room>, Serializable {
    protected Position p;
    protected int width;
    protected int height;


    Room(Position p) {
        width = 0;
        height = 0;
        this.p = p;
    }

    public int getX() {
        return p.x;
    }

    public int getY() {
        return p.y;
    }

    public int getHeight() {
        return this.height;
    }

    public int getWidth() {
        return this.width;
    }

    /**
     * want to break ties with the y position
     *
     * @param other
     * @return
     */
    @Override
    public int compareTo(Room other) {
        int result = Integer.compare(p.x, other.p.x); //negative means less
        if (result == 0) {
            result = Integer.compare(p.y, other.p.y);
        }
        return result;
    }

    // checks if the next room can be drawn at position p
    public boolean validRoom(TETile[][] world) {

        // Check if room is within bounds
        if (p.y + height < 0 || p.y + height > WINDHEIGHT
                || p.x + width < 0 || p.x + width > WINDWIDTH) {
            return false;
        }

        // Checks for overlapping
        for (int i = p.x; i < width + p.x; i++) {
            for (int j = p.y; j < height + p.y; j++) {
                if (!(world[i][j].equals(NOTHING))) {
                    return false;
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
}
