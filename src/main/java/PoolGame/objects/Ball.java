package PoolGame.objects;

import PoolGame.Config;
import PoolGame.strategy.PocketStrategy;
import javafx.scene.paint.Paint;

import java.util.Map;

/** Holds information for all ball-related objects. */
public class Ball extends CirclePrototype {
    private Integer score;
    private Paint colour;
    private double startX;
    private double startY;
    private double xVelocity;
    private double yVelocity;
    private double mass;
    private boolean isCue;
    private PocketStrategy strategy;
    private final double MAXVEL = 20;


    public Ball(String colour, double xPosition, double yPosition, double xVelocity, double yVelocity, double mass,
            boolean isCue, PocketStrategy strategy) {
        super(xPosition, yPosition, 20);
        this.colour = Paint.valueOf(colour);
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.startX = xPosition;
        this.startY = yPosition;
        this.xVelocity = xVelocity;
        this.yVelocity = yVelocity;
        this.mass = mass;
        this.isCue = isCue;
        this.strategy = strategy;
        this.score = Config.getScores().get(colour);
    }

    /**
     * Updates ball position per tick.
     */
    public void tick() {
        xPosition += xVelocity;
        yPosition += yVelocity;
    }

    /**
     * Resets ball position, velocity, and activity.
     */
    public void reset() {
        resetPosition();
        strategy.reset();
    }

    /**
     * Resets ball position and velocity.
     */
    public void resetPosition() {
        xPosition = startX;
        yPosition = startY;
        xVelocity = 0;
        yVelocity = 0;
    }

    /**
     * Removes ball from play.
     * 
     * @return true if ball is successfully removed
     */
    public boolean remove() {
        if (strategy.remove()) {
            return true;
        } else {
            resetPosition();
            return false;
        }
    }

    /**
     * Sets x-axis velocity of ball.
     * 
     * @param xVelocity of ball.
     */
    public void setxVel(double xVelocity) {
        if (xVelocity > MAXVEL) {
            this.xVelocity = MAXVEL;
        } else if (xVelocity < -MAXVEL) {
            this.xVelocity = -MAXVEL;
        } else {
            this.xVelocity = xVelocity;
        }

    }

    /**
     * Sets y-axis velocity of ball.
     * 
     * @param yVelocity of ball.
     */
    public void setyVel(double yVelocity) {
        if (yVelocity > MAXVEL) {
            this.yVelocity = MAXVEL;
        } else if (yVelocity < -MAXVEL) {
            this.yVelocity = -MAXVEL;
        } else {
            this.yVelocity = yVelocity;
        }
    }

    /**
     * Copies the current instance
     * @return a deepcopy of the ball
     */
    public Ball copy(){
        String colour = "";
        // find the ball's colour as a string

        for (Map.Entry<String, Integer> sc: Config.getScores().entrySet()){
            if (sc.getValue() == score){
                colour = sc.getKey();
            }
        }
        return new Ball(colour, xPosition, yPosition, xVelocity, yVelocity,
                mass, isCue, strategy);
    }

    /**
     * Sets x-axis position of ball.
     * 
     * @param xPosition of ball.
     */
    public void setxPos(double xPosition) {
        this.xPosition = xPosition;
    }

    /**
     * Sets y-axis position of ball.
     * 
     * @param yPosition of ball.
     */
    public void setyPos(double yPosition) {
        this.yPosition = yPosition;
    }

    /**
     * Getter method for radius of ball.
     * 
     * @return radius length.
     */
    public double getRadius() {
        return radius;
    }

    /**
     * Getter method for x-position of ball.
     * 
     * @return x position.
     */
    public double getxPos() {
        return xPosition + Config.getTableBuffer();
    }

    /**
     * Getter method for y-position of ball.
     * 
     * @return y position.
     */
    public double getyPos() {
        return yPosition + Config.getTableBuffer();
    }

    public PocketStrategy getStrategy(){
        return strategy;
    }

    /**
     * Getter method for starting x-position of ball.
     * 
     * @return starting x position.
     */
    public double getStartXPos() {
        return startX;
    }

    /**
     * Getter method for starting y-position of ball.
     * 
     * @return starting y position.
     */
    public double getStartYPos() {
        return startY;
    }

    /**
     * Getter method for starting mass of ball.
     * 
     * @return mass.
     */
    public double getMass() {
        return mass;
    }

    /**
     * Getter method for colour of ball.
     * 
     * @return colour.
     */
    public Paint getColour() {
        return colour;
    }

    /**
     * Getter method for x-axis velocity of ball.
     * 
     * @return x velocity.
     */
    public double getxVel() {
        return xVelocity;
    }

    /**
     * Getter method for y-axis velocity of ball.
     * 
     * @return y velocity.
     */
    public double getyVel() {
        return yVelocity;
    }
    public int getScore(){return score;}

    /**
     * Getter method for whether ball is cue ball.
     * 
     * @return true if ball is cue ball.
     */
    public boolean isCue() {
        return isCue;
    }

    /**
     * Setter for isCue
     * @param value whether the ball is a cue ball or not
     */
    public void setIsCue(boolean value){this.isCue = value;}

    /**
     * Sets the strategy for the ball- how it's going to behave when falling into a pocket
     * @param strat
     */
    public void setStrategy(PocketStrategy strat){this.strategy = strat;}

    /**Sets the ball's point value
     *
     * @param score
     */
    public void setScore(int score){this.score = score;}


}
