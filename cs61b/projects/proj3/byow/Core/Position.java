package byow.Core;

public class Position {
    protected int x;
    protected int y;

    Position(int posX, int posY) {
        this.x = posX;
        this.y = posY;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public static Position minX(Position p1, Position p2) {
        if (p1.x < p2.x) {
            return p1;
        }
        return p2;
    }

    public static Position maxX(Position p1, Position p2) {
        if (p1.x < p2.x) {
            return p2;
        }
        return p1;
    }

    public static Position minY(Position p1, Position p2){
        if (p1.y < p2.y) {
            return p1;
        }
        return p2;
    }

    public static Position maxY(Position p1, Position p2){
        if (p1.y < p2.y) {
            return p2;
        }
        return p1;
    }

    @Override
    public String toString () {
        return "(" + x + ", " + y + ")";
    }
}
