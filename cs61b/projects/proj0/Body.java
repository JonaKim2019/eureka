public class Body {

    /**
     * 	double xxPos: Its current x position
     *	double yyPos: Its current y position
     *	double xxVel: Its current velocity in the x direction
     *	double yyVel: Its current velocity in the y direction
     *	double mass: Its mass
     *	String imgFileName: The name of the file that corresponds to the image that depicts the body.
     *  public static final double G: Universal gravitational constant
     */

    public double xxPos;
    public double yyPos;
    public double xxVel;
    public double yyVel;
    public double mass;
    public String imgFileName;
    private static final double G = 6.67e-11;

    public Body(double xP, double yP, double xV, double yV, double m, String img) {
        this.xxPos = xP;
        this.yyPos = yP;
        this.xxVel = xV;
        this.yyVel = yV;
        this.mass = m;
        this.imgFileName = img;
    }

    public Body(Body b) {
        this.xxPos = b.xxPos;
        this.yyPos = b.yyPos;
        this.xxVel = b.xxVel;
        this.yyVel = b.yyVel;
        this.mass = b.mass;
        this.imgFileName = b.imgFileName;
    }

    /**
     *  Calculate the distance between two Bodies.
     *  @param the body to be calculated
     *  @return a double describing the distance between two Bodies
     */

    public double calcDistance(Body b) {
        double dx = this.xxPos - b.xxPos;
        double dy = this.yyPos - b.yyPos;
        return Math.sqrt(dx*dx + dy*dy);
    }

    /**
     *  Calculate the force exerted on this Body by the given Body.
     *  @param Body that will provide the force
     *  @return a double describing the force exerted on this Body by the given Body
     */

    public double calcForceExertedBy(Body b) {
        return (G / Math.pow(calcDistance(b), 2) * this.mass * b.mass);
    }

    /**
     *  Calcualte the force exerted in the X direction
     *  @param Body to be calculated
     *  @return a double describing the force exerted in the X direction
     */

    public double calcForceExertedByX(Body b) {

        double xDirectionForce = (b.xxPos - this.xxPos) / calcDistance(b);
        return calcForceExertedBy(b) * xDirectionForce;
    }

    /**
     *  Calcualte the force exerted in the Y direction
     *  @param Body to be calculated
     *  @return a double describing the force exerted in the Y direction
     */

    public double calcForceExertedByY(Body b) {

        double yDirectionForce = (b.yyPos - this.yyPos) / calcDistance(b);
        return calcForceExertedBy(b) * yDirectionForce;
    }

    /**
     *  Calculate the net X force exerted by all Bodies in a array.
     *  @param Body[] a Body array
     *  @return a double describing the net X force exerted by other Bodies
     */

    public double calcNetForceExertedByX(Body[] bs) {

        double xNetForce = 0;

        for(Body b : bs) {
            if (!this.equals(b)) {
                xNetForce += calcForceExertedByX(b);
            }
        }
        return xNetForce;
    }

    /**
     *  Calculate the net Y force exerted by all Bodies in a array.
     *  @param Body[] a Body array
     *  @return a double describing the net Y force exerted by other Bodies
     */

    public double calcNetForceExertedByY(Body[] bs) {

        double yNetForce = 0;

        for(Body b : bs) {
            if (!this.equals(b)) {
                yNetForce += calcForceExertedByY(b);
            }
        }
        return yNetForce;
    }

    /**
     *  Calculate how much the forces exerted on the Body will cause that Body
     *  to accelerate, and the resulting change in the body’s velocity
     *  and position in a small period of time dt.
     *  @param double,double,double a small period of time dt , x- forces and y- forces
     *
     */

    public void update(double dt, double fX, double fY) {

        xxVel += dt * fX / mass;
        yyVel += dt * fY / mass;
        xxPos += dt * xxVel;
        yyPos += dt * yyVel;
    }

    /**
     *   Draw the Body’s image at the Body’s position
     */

    public void draw() {

        String filename = "images/" + imgFileName;
        StdDraw.picture(xxPos, yyPos, filename);
    }
}