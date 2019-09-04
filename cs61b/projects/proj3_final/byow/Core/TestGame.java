
package byow.Core;

import byow.TileEngine.TERenderer;
import java.util.Random;

public class TestGame {
    public static final int WIDTH = World.WINDWIDTH;
    public static final int HEIGHT = World.WINDHEIGHT;
    // constraint to the number of rooms
    public static final int ROOMS_TO_SCREEN = World.ROOMS_TO_SCREEN; //100 = max area of a room

    public static void main(String[] args) {
        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);

        Random r = new Random(490565); // works for 312800
        //draw all the rooms
        World world = new World(r);
        ter.renderFrame(world.getWorld());
        Engine engine = new Engine();
        String moves = "dddwawwwdwwss";
        for (char direction : moves.toCharArray()) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
            //engine.playGame(world);
            ter.renderFrame(world.getWorld());
        }

    }
}
