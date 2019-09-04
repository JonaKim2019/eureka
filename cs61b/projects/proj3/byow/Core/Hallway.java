package byow.Core;

import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

import byow.Core.ArrayList;

public class Hallway {
    Position corner;
    Position start;
    Position end;
    int key;

    public Hallway(Position c, Position s, Position e, int k) {
        corner = c;
        start = s;
        end = e;
        key = k;
    }

    public static Position addVerticalHallway(TETile[][] world, Position p, int h) {
        for (int y = 0; y < h; y += 1) {
            world[p.getX() - 1][p.getY() + y] = Tileset.WALL;
            world[p.getX() + 1][p.getY() + y] = Tileset.WALL;
        }
        return new Position(p.getX(), p.getY() + h);
    }

    public static Position addHorizontalHallway(TETile[][] world, Position p, int w) {
        for (int x = 0; x < w; x += 1) {
            //System.out.println("world: "+world.length+" w: "+w+" p.x: "+p.xPos+" p.y: "+p.yPos);
            world[p.getX() + x][p.getY() - 1] = Tileset.WALL;
            world[p.getX() + x][p.getY() + 1] = Tileset.WALL;
        }
        return new Position(p.getX() + w, p.getY());
    }

    private static void fillVerticalHallway(TETile[][] world, Position p, int h) {
        for (int y = 0; y < h; y += 1) {
            world[p.getX()    ][p.getY() + y] = Tileset.FLOOR;
        }
    }

    private static void fillHorizontalHallway(TETile[][] world, Position p, int w) {
        for (int x = 0; x < w; x += 1) {
            world[p.getX() + x][p.getY()    ] = Tileset.FLOOR;
        }
    }

    private static void fillLHallway(TETile[][] world, Hallway hw) {
        switch (hw.key) {
            case 0: {
                fillHorizontalHallway(world, hw.start, hw.corner.getX() - hw.start.getX() + 1);
                fillVerticalHallway(world, Position.minY(hw.corner, hw.end),
                        Math.abs(hw.corner.getY() - hw.end.getY()) + 1);
                break;
            }
            case 1: {
                fillVerticalHallway(world, hw.start, hw.corner.getY() - hw.start.getY() + 1);
                fillHorizontalHallway(world, Position.minX(hw.corner, hw.end),
                        Math.abs(hw.corner.getX() - hw.end.getX()) + 1);
                break;
            }
            default: break;
        }
    }

    public static void fillLHallwayList(TETile[][] world, ArrayList<Hallway> hallwayList) {
        for (int i = 0; i < hallwayList.size(); i += 1) {
            fillLHallway(world, hallwayList.get(i));
        }
    }
}