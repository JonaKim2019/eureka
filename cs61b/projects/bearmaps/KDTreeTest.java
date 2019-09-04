package bearmaps;

import edu.princeton.cs.algs4.Stopwatch;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.Assert.*;

/**
 * @source: Professor Hug
 */

public class KDTreeTest {
    private static Random r = new Random(500);

    @Test
    public void testNearest() {
        Point p1 = new Point(2, 3);
        Point p2 = new Point(4, 2);
        Point p3 = new Point(4, 2);
        Point p4 = new Point(4, 5);
        Point p5 = new Point(3, 3);
        Point p6 = new Point(1, 5);
        Point p7 = new Point(4, 4);
        NaivePointSet nps = new NaivePointSet(List.of(p1, p2, p3, p4, p5, p6, p7));
        KDTree kd = new KDTree(List.of(p1, p2, p3, p4, p5, p6, p7));
        Point expected = nps.nearest(0, 7);
        assertEquals(expected, kd.nearest(0, 7));
    }

    private Point randomPoint() {
        double x = r.nextDouble();
        double y = r.nextDouble();
        return new Point(x, y);

    }
    private List<Point> randomPoints(int N) {
        ArrayList<Point> points = new ArrayList<>();
        for (int i = 1; i < N; i++) {
            points.add(randomPoint());
        }
        return points;
    }

    @Test
    public void testWith1000Points() {
        List<Point> points1000 = randomPoints(500);
        NaivePointSet nps = new NaivePointSet(points1000);
        KDTree kd = new KDTree(points1000);

        List<Point> queries200 = randomPoints(200);
        for (Point p : queries200) {
            Point expected = nps.nearest(p.getX(), p.getY());
            Point actual = kd.nearest(p.getX(), p.getY());
            assertEquals(expected, actual);
        }

    }

    private void testWithPointsAndQueries(int pointCount, int queryCount) {
        List<Point> points = randomPoints(pointCount);
        NaivePointSet nps = new NaivePointSet(points);
        KDTree kd = new KDTree(points);

        List<Point> queries = randomPoints(queryCount);
        for (Point p : queries) {
            Point expected = nps.nearest(p.getX(), p.getY());
            Point actual = kd.nearest(p.getX(), p.getY());
            assertEquals(expected, actual);
        }
    }

    private void testTimeWithPointsAndQueries(int pointCount, int queryCount) {
        List<Point> points = randomPoints(pointCount);
        KDTree kd = new KDTree(points);

        Stopwatch sw = new Stopwatch();
        List<Point> queries = randomPoints(queryCount);
        for (Point p : queries) {
            Point actual = kd.nearest(p.getX(), p.getY());
        }
        System.out.println("Time elapsed for " + queryCount
                + " queries on " + pointCount + " point" + sw.elapsedTime());
    }

    @Test
    public void testWith1000PointsAnd200Queries() {
        int pointCount = 1000;
        int queryCount = 200;
        testWithPointsAndQueries(pointCount, queryCount);
    }

    @Test
    public void testWith10000PointsAnd2000Queries() {
        int pointCount = 10000;
        int queryCount = 2000;
        testWithPointsAndQueries(pointCount, queryCount);
    }
    @Test
    public void testWith500PointsAnd200Queries() {
        int pointCount = 500;
        int queryCount = 200;
        testWithPointsAndQueries(pointCount, queryCount);
    }

    @Test
    public void testTimeWith10000PointsAnd2000Queries() {
        int pointCount = 10000;
        int queryCount = 2000;
        testTimeWithPointsAndQueries(pointCount, queryCount);
    }

    @Test
    public void testTimeWithQueriesAndVariousNumbers() {
        List<Integer> pointCounts = List.of(1000, 10000, 100000, 1000000);
        for (int N : pointCounts) {
            testTimeWithPointsAndQueries(N, 10000);
        }
    }
}
