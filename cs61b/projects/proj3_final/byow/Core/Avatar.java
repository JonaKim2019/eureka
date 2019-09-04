package byow.Core;

import byow.TileEngine.TETile;
import edu.princeton.cs.introcs.StdDraw;

import java.io.Serializable;

import static byow.TileEngine.Tileset.*;

public class Avatar implements Serializable {
    protected Position p;
    World world;

    public Avatar(World world, Position start) {
        this.p = start;
        this.world = world;
        world.getWorld()[p.x][p.y] = AVATAR;
    }

    public Position getPos() {
        return this.p;
    }
    public void setPos(int xValue, int yValue) {
        p.x = xValue;
        p.y = yValue;
    }
    public void moveRight() {
        Position newPos = new Position(p.x + 1, p.y);
        if (swapTile(newPos, p)) {
            p.x += 1;
        }
    }
    // a is for avatar
    public void moveLeft() {
        Position newPos = new Position(p.x - 1, p.y);
        if (swapTile(newPos, p)) {
            p.x -= 1;
        }
    }
    // a is for avatar
    public void moveUp() {
        Position newPos = new Position(p.x, p.y + 1);
        if (swapTile(newPos, p)) {
            p.y += 1;
        }
    }
    public void moveDown() {
        Position newPos = new Position(p.x, p.y - 1);
        if (swapTile(newPos, p)) {
            p.y -= 1;
        }
    }

    /**
     * Move the avatar from its original position to its new position
     * @param newPos original position
     * @param oldPos new position
     */
    private boolean swapTile(Position newPos, Position oldPos) {
        TETile temp = world.getWorld()[newPos.x][newPos.y];
        if (temp.equals(KEY)) {
            world.key.found();
        }
        if (temp.equals(UNLOCKED_DOOR)) {
            world.gameOver();
        } else if (temp.equals(LOCKED_DOOR)) {
            StdDraw.text(world.door.p.x, world.door.p.y + 1, "key not found");

        } else if (temp.equals(FLOOR)) {
            world.getWorld()[newPos.x][newPos.y] = AVATAR;
            world.getWorld()[oldPos.x][oldPos.y] = FLOOR;
            return true;
        } else if (temp.equals(PORTAL)) {
            teleport(newPos);
        }
        return false;

    }

    private void teleport(Position portalLocation) {
        //Portal portal = world.portals.get(portalLocation);
        Portal newPortal = world.choosePortal(portalLocation);
        if (newPortal != null) {
            Position newPos = newPortal.p;

            //Position newPos = portal.jumpPos;
            world.getWorld()[p.x][p.y] = FLOOR;
            world.getWorld()[newPos.x][newPos.y] = AVATAR;
            p = newPos;
            if (world.portals.size() == 1) {
                Portal lastPortal = (Portal) world.portals.remove();
                world.getWorld()[lastPortal.p.x][lastPortal.p.y] = FLOOR;
            }
        }
    }

}
