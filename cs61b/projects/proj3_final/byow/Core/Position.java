package byow.Core;

import java.io.Serializable;


public class Position implements Serializable {
    protected int x;
    protected int y;

    Position(int posX, int posY) {
        this.x = posX;
        this.y = posY;
    }

    public void setPos(int xValue, int yValue) {
        this.x = xValue;
        this.y = yValue;
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

    public static Position minY(Position p1, Position p2) {
        if (p1.y < p2.y) {
            return p1;
        }
        return p2;
    }

    public static Position maxY(Position p1, Position p2) {
        if (p1.y < p2.y) {
            return p2;
        }
        return p1;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }

    @Override
    public boolean equals(Object other) {
        Position otherPos;
        if (other instanceof Position) {
            otherPos = (Position) other;
        } else {
            otherPos = ((Portal) other).p;
        }
        return x == otherPos.x && y == otherPos.y;
    }


    @Override
    public int hashCode() {
        return Integer.hashCode(x) + Integer.hashCode(y);
    }
}
