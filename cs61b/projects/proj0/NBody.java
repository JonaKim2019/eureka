public class NBody {

    /**
     *  Read radius from file
     *  @param String file name
     *  @return a double corresponding to the radius of the universe in that file
     */

    public static double readRadius(String fileName) {

        In in = new In(fileName);
        in.readInt();
        return in.readDouble();
    }

    /**
     *  Read Body from file
     *  @param String file name
     *  @return an array of Bodies corresponding to the bodies in the file
     */

    public static Body[] readBodies(String fileName) {

        In bodyName = new In(fileName);
        int num = bodyName.readInt();
        bodyName.readDouble();
        Body[] bs = new Body[num];
        int index = 0;

        while(index != num) {
            double xP = bodyName.readDouble();
            double yP = bodyName.readDouble();
            double xV = bodyName.readDouble();
            double yV = bodyName.readDouble();
            double m = bodyName.readDouble();
            String img = bodyName.readString();
            bs[index++] = new Body(xP, yP, xV, yV, m, img);
        }
        return bs;
    }


    public static void main(String[] args) {

        /* get data */

        double T = Double.parseDouble(args[0]);
        double dt = Double.parseDouble(args[1]);
        String filename = args[2];
        double universeRadius = readRadius(filename);
        Body[] bs = readBodies(filename);

        /* Draw */

        StdDraw.setScale(-universeRadius, universeRadius);
        StdDraw.clear();
        StdDraw.picture(0, 0, "images/starfield.jpg");

        for (Body b : bs) {
            b.draw();
        }

        /* Animation */

        StdDraw.enableDoubleBuffering();

        double timeSpectrum = 0;

        while(timeSpectrum <= T) {
            double[] xForces = new double[bs.length];
            double[] yForces = new double[bs.length];
            for(int i=0; i<bs.length; i++) {
                xForces[i] = bs[i].calcNetForceExertedByX(bs);
                yForces[i] = bs[i].calcNetForceExertedByY(bs);
            }

            for(int i=0; i<bs.length; i++) {
                bs[i].update(dt, xForces[i], yForces[i]);
            }

            StdDraw.picture(0, 0, "images/starfield.jpg");

            for (Body b : bs) {
                b.draw();
            }

            StdDraw.show();
            StdDraw.pause(10);
            timeSpectrum += dt;
        }

		/* 
		   Printing the Universe
		   When reached time T, print out the final state of the universe.
		*/

        StdOut.printf("%d\n", bs.length);
        StdOut.printf("%.2e\n", universeRadius);

        for (int i = 0; i < bs.length; i++) {
            StdOut.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n",
                    bs[i].xxPos, bs[i].yyPos, bs[i].xxVel,
                    bs[i].yyVel, bs[i].mass, bs[i].imgFileName);
        }
    }
}