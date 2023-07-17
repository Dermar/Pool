package PoolGame.objects;

import PoolGame.strategy.BallStrategy;
import PoolGame.strategy.BlueStrategy;
import PoolGame.strategy.PocketStrategy;
import PoolGame.strategy.StrongStrategy;

/** Builds the cue ball */
public class PoolBallBuilder implements BallBuilder {
    // Required Parameters
    private String colour;
    private double xPosition;
    private double yPosition;
    private double xVelocity;
    private double yVelocity;
    private double mass;

    // Variable Parameters
    private boolean isCue = false;
    public PocketStrategy strategy;

    @Override
    public void setColour(String colour) {
        this.colour = colour;
    };

    @Override
    public void setxPos(double xPosition) {
        this.xPosition = xPosition;
    };

    @Override
    public void setyPos(double yPosition) {
        this.yPosition = yPosition;
    };

    @Override
    public void setxVel(double xVelocity) {
        this.xVelocity = xVelocity;
    };

    @Override
    public void setyVel(double yVelocity) {
        this.yVelocity = yVelocity;
    };

    @Override
    public void setMass(double mass) {
        this.mass = mass;
    };

    /**
     * Builds the ball.
     *
     * @return ball
     */
    public Ball build() {
        if (colour.equals("white")) {
            isCue = true;
            strategy = new BallStrategy();
        } else if (colour.equals("blue") || colour.equals("purple") || colour.equals("green")) {
            strategy = new BlueStrategy();
        } else if (colour.equals("red") || colour.equals("yellow") || colour.equals("orange")){
            strategy = new BallStrategy();
        } else if (colour.equals("brown") || colour.equals("black")){
            strategy = new StrongStrategy();
        }

        return new Ball(colour, xPosition, yPosition, xVelocity, yVelocity, mass, isCue, strategy);
    }

}
