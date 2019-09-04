package byow.lab12;
import org.junit.Test;
import static org.junit.Assert.*;

import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

import java.util.Random;

/**
 * Draws a world consisting of hexagonal regions.
 */
public class HexWorld {
    private static final int WIDTH = 50;
    private static final int HEIGHT = 50;

    private static final long SEED = 2873123;
    private static final Random RANDOM = new Random(SEED);

    private static class Position{
        int x;
        int y;

        Position(int posX, int posY) {
            x = posX;
            y = posY;
        }

    }
    /** Returns the ith hexRowWidth given the size of hexagon and i(start from 0). */
    private static int hexRowWidth(int size, int i) {
        int linesize;
        if (i < size) {
            linesize = size + 2 * i;
        }
        else {
            linesize = 5 * size - 2 * (i + 1);
        }
        return linesize;
    }


    /** Returns the ith hexRowXOffSet given the size of hexagon and i(start from 0).
     *  Assumes that the first(bottom) row starts from zero. */
    private static int hexRowOffset(int size, int i) {
        int offSet = 0;
        if (i < size) {
            offSet = -i;
        }
        else {
            offSet = i - 2 * size + 1;
        }
        return offSet;
    }

    /** Adds a row of the same tile.
     * @param world the world to draw on
     * @param p the leftmost position of the row
     * @param width the number of tiles wide to draw
     * @param t the tile to draw
     */
    public static void addRow(TETile[][] world, Position p, int width, TETile t) {
        for (int i = 0; i < width; i++) {
            int posX = p.x + i;
            int posY = p.y;
            Random r = new Random(77777);
            world[posX][posY] = TETile.colorVariant(t, 32, 32, 32, r);
        }
    }

    /**
     * Adds a hexagon to the world.
     * @param world the world to draw on
     * @param p the bottom left coordinate of the hexagon
     * @param s the size of the hexagon
     * @param t the tile to draw
     */
    public static void addHexagon(TETile[][] world, Position p, int s, TETile t) {

        if (s < 2) {
            throw new IllegalArgumentException("Hexagon must be at least size 2.");
        }

        // One hexagon has s * 2 rows, from the first row 0.
        for (int i = 0; i < 2 * s; i++) {
            int width = hexRowWidth(s, i);
            int offset = hexRowOffset(s, i);
            int rowStartX = p.x + offset;
            int rowStartY = p.y + i;
            Position rowStartP = new Position(rowStartX, rowStartY);
            addRow(world, rowStartP, width, t);
        }
    }

    /** Draws a column of N hexes, each one with a random biome.
     * @param world the world to draw on
     * @param p the bottom left coordinate of the first hexagon
     * @param n the number of hexes in this column
     * @param s the size of these hexes
     */
    public static void drawRandomVerticalHexes(TETile[][] world, Position p, int n, int s) {
        Position nextPos = p;
        for (int i = 0; i < n; i++) {
            addHexagon(world, nextPos, s, randomTetile());
            // Actually Position p never changed.
            nextPos = new Position(nextPos.x, nextPos.y + 2 * s);
        }

    }

    private static TETile randomTetile() {
        int tileNum = RANDOM.nextInt(3);
        switch (tileNum) {
            case 0: return Tileset.WALL;
            case 1: return Tileset.FLOWER;
            case 2: return Tileset.MOUNTAIN;
            case 3: return Tileset.GRASS;
            case 4: return Tileset.WATER;
            case 5: return Tileset.FLOOR;
            case 6: return Tileset.SAND;
            case 7: return Tileset.TREE;
            case 8: return Tileset.LOCKED_DOOR;
            case 9: return Tileset.UNLOCKED_DOOR;
            default: return Tileset.FLOWER;
        }
    }

    /** Draws all the columns
     * @param world the world to draw on
     * @param p the bottom left coordinate of the first hexagon
     * @param s the size of these hexes
     */
    public static void drawColsOfHexes(TETile[][] world, Position p, int s) {
        Position nextPos = p;
        drawRandomVerticalHexes(world, nextPos, 3, 3);
        nextPos = new Position(nextPos.x + 2 * s - 1, nextPos.y - s);
        drawRandomVerticalHexes(world, nextPos, 4, 3);
        nextPos = new Position(nextPos.x + 2 * s - 1, nextPos.y - s);
        drawRandomVerticalHexes(world, nextPos, 5, 3);
        nextPos = new Position(nextPos.x + 2 * s - 1, nextPos.y + s);
        drawRandomVerticalHexes(world, nextPos, 4, 3);
        nextPos = new Position(nextPos.x + 2 * s - 1, nextPos.y + s);
        drawRandomVerticalHexes(world, nextPos, 3, 3);
    }


    @Test
    public void testHexRowWidth() {
        assertEquals(3, hexRowWidth(3, 5));
        assertEquals(5, hexRowWidth(3, 4));
        assertEquals(7, hexRowWidth(3, 3));
        assertEquals(7, hexRowWidth(3, 2));
        assertEquals(5, hexRowWidth(3, 1));
        assertEquals(3, hexRowWidth(3, 0));
        assertEquals(2, hexRowWidth(2, 0));
        assertEquals(4, hexRowWidth(2, 1));
        assertEquals(4, hexRowWidth(2, 2));
        assertEquals(2, hexRowWidth(2, 3));
    }

    @Test
    public void testHexRowOffset() {
        assertEquals(0, hexRowOffset(3, 5));
        assertEquals(-1, hexRowOffset(3, 4));
        assertEquals(-2, hexRowOffset(3, 3));
        assertEquals(-2, hexRowOffset(3, 2));
        assertEquals(-1, hexRowOffset(3, 1));
        assertEquals(0, hexRowOffset(3, 0));
        assertEquals(0, hexRowOffset(2, 0));
        assertEquals(-1, hexRowOffset(2, 1));
        assertEquals(-1, hexRowOffset(2, 2));
        assertEquals(0, hexRowOffset(2, 3));
    }

    public static void main(String[] args) {
        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);

        TETile[][] world = new TETile[WIDTH][HEIGHT];
        for (int x = 0; x < WIDTH; x += 1) {
            for (int y = 0; y < HEIGHT; y += 1) {
                world[x][y] = Tileset.NOTHING;
            }
        }


        Position testPos2 = new Position(20, 20);
        //drawRandomVerticalHexes(world, testPos2, 3, 3);
        drawColsOfHexes(world, testPos2, 3);

        ter.renderFrame(world);
    }
}
