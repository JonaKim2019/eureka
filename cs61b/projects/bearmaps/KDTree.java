package bearmaps;
import java.util.List;
import static bearmaps.Point.distance;

/**
 * @source: Professor Hug
 */

public class KDTree implements PointSet {
    private static final boolean HORIZONTAL = false;
    private static final boolean VERTICAL = true;
    private Node root;

    private class Node {
        private Point p;
        private boolean orientation;
        private Node left; // also down
        private Node right; // also up

        Node(Point p, boolean orientation) {
            this.p = p;
            this.orientation = orientation;
        }
    }
    public KDTree(List<Point> points) {
        for (Point p : points) {
            root = insert(p, root, HORIZONTAL);
        }
    }

    private Node insert(Point p, Node n, boolean orientation) {
        if (n == null) {
            return new Node(p, orientation);
        }
        if (p.equals(n.p)) {
            return n;
        }
        int compare = comparePoints(p, n.p, orientation);
        if (compare < 0) {
            n.left = insert(p, n.left, !orientation);
        } else if (compare >= 0) {
            n.right = insert(p, n.right, !orientation);
        }
        return n;
    }

    private int comparePoints(Point a, Point b, boolean orientation) {
        if (orientation == HORIZONTAL) {
            return Double.compare(a.getX(), b.getX());
        } else {
            return Double.compare(a.getY(), b.getY());
        }
    }
    @Override
    public Point nearest(double x, double y) {
        Point p = new Point(x, y);
        return nearest(root, p, root).p;
    }

    private Node nearest(Node n, Point goal, Node best) {
        if (n == null) {
            return best;
        }
        Node goodChoice = null;
        Node badChoice = null;
        if (distance(n.p, goal) < distance(best.p, goal)) {
            best = n;
        }
        double minDistance = distance(best.p, goal);
        int compare = comparePoints(goal, n.p, n.orientation);
        if (compare > 0) {
            goodChoice = n.right;
            badChoice = n.left;
        } else {
            goodChoice = n.left;
            badChoice = n.right;
        }
        // compare a new point and minDistance
        // change
        best =  nearest(goodChoice, goal, best);
        if (calcDistance(n, goal, n.orientation) < minDistance) {
            best =  nearest(badChoice, goal, best);
        }
        return best;
    }
    private double calcDistance(Node n, Point p, boolean boo) {
        if (boo == VERTICAL) {
            Point point = new Point(p.getX(), n.p.getY());
            double distance = distance(point, p);
            return distance;
        } else {
            Point point = new Point(n.p.getX(), p.getY());
            double distance = distance(point, p);
            return distance;
        }
    }

}
