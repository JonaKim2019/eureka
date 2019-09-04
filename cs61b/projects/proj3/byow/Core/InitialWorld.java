package byow.Core;

public class InitialWorld {
    protected int width;
    protected int height;
    protected long seed;

    public InitialWorld() {
        width = 80;
        height = 30;
        seed = 99999;
    }

    public InitialWorld(int w, int h, long seed) {
        width = w;
        height = h;
        seed = seed;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public long getSeed() {
        return this.seed;
    }
    public InitialWorld setWorldValues(int w, int h, long s) {
        InitialWorld world = new InitialWorld();
        world.width = w;
        world.height = h;
        world.seed = s;
        return world;
    }
}