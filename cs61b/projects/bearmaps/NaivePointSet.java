package bearmaps;
import java.util.ArrayList;
import java.util.List;

import static bearmaps.Point.distance;

public class NaivePointSet implements PointSet {

    private List<Point> pointList;

    public NaivePointSet(List<Point> points) {
        pointList = new ArrayList<Point>();
        for (Point p : points) {
            pointList.add(p);
        }
    }
    @Override
    public Point nearest(double x, double y) {
        Point p = new Point(x, y);
        Point closestPoint = pointList.get(0);
        double closestDistance = distance(closestPoint, p);
        double tempDistance = 0;
        for (int i = 1; i < pointList.size(); i++) {
            tempDistance = distance(pointList.get(i), p);
            if (tempDistance < closestDistance) {
                closestPoint = pointList.get(i);
                closestDistance = tempDistance;
            }
        }
        return closestPoint;
    }
    /*
    public static void main(String[] args) {
        Point p1 = new Point(1.1, 2.2); // constructs a Point with x = 1.1, y = 2.2
        Point p2 = new Point(3.3, 4.4);
        Point p3 = new Point(-2.9, 4.2);

        NaivePointSet nn = new NaivePointSet(List.of(p1, p2, p3));
        Point ret = nn.nearest(3.0, 4.0); // returns p2
        System.out.println(ret.getX()); // evaluates to 3.3
        System.out.println(ret.getY()); // evaluates to 4.4
    }
    */
}
