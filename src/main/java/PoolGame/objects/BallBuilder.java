package PoolGame.objects;

/** Interface for building pool balls. */
public interface BallBuilder {

    /**
     * Sets the colour of the ball.
     *
     * @param colour of ball.
     */
    public void setColour(String colour);

    /**
     * Sets the x position of the ball.
     *
     * @param xPosition of ball.
     */
    public void setxPos(double xPosition);

    /**
     * Sets the y position of the ball.
     *
     * @param yPosition of ball.
     */
    public void setyPos(double yPosition);

    /**
     * Sets the x velocity of the ball.
     *
     * @param xVelocity of ball.
     */
    public void setxVel(double xVelocity);

    /**
     * Sets the y velocity of the ball.
     *
     * @param yVelocity of ball.
     */
    public void setyVel(double yVelocity);

    /**
     * Sets the mass of the ball.
     *
     * @param mass of ball.
     */
    public void setMass(double mass);


}
