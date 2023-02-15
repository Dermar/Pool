package PoolGame.objects;

import PoolGame.Config;

/** Holds information for all pocket-related objects. */
public class Pocket extends CirclePrototype {

    /**
     * Constructor for pockets
     * @param xPosition
     * @param yPosition
     * @param radius
     */
    public Pocket(double xPosition, double yPosition, double radius) {
        super(xPosition, yPosition, radius);
    }

    /**
     * Returns the x position of the pocket.
     * 
     * @return x position.
     */
    public double getxPos() {
        return xPosition + Config.getTableBuffer();
    }

    /**
     * Returns the y position of the pocket.
     * 
     * @return y position.
     */
    public double getyPos() {
        return yPosition + Config.getTableBuffer();
    }

    /**
     * Returns the radius of the pocket.
     * 
     * @return radius.
     */
    public double getRadius() {
        return radius;
    }

    /**
     * Checks if the ball is in the pocket.
     * 
     * @param ball
     * @return true if ball is in pocket.
     */
    public boolean isInPocket(Ball ball) {
        return Math.abs(this.getxPos() - ball.getxPos()) < this.getRadius() + 2 &&
                Math.abs(this.getyPos() - ball.getyPos()) < this.getRadius() + 2;
    }

    public Pocket copy(){
        return new Pocket(xPosition, yPosition, radius);
    }
}
