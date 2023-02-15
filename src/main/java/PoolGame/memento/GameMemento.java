package PoolGame.memento;

import PoolGame.objects.Ball;

import java.util.*;

/**
 * GameMemento holds the GameWindow state by retaining a reference to its list of balls, score, and time
 */
public class GameMemento {
    private List<Ball> balls = new ArrayList<>();
    private int score;
    private int time;

    /**
     * Constructor for GameMemento
     * @param balls the list of balls currently being used in the game
     * @param score the current game score
     * @param time the current elapsed time/ticks
     */
    public GameMemento(ArrayList<Ball> balls, int score, int time){
        this.score = score;
        this.time = time;
        for (Ball ball: balls){
            this.balls.add(ball.copy());
        }

    }

    /**
     * Getter for balls
     *
     * @return a deep copy of the game's balls since the previous GameMemento.
     */
    public List<Ball> getBalls(){ return balls;}

    /**
     * Getter for score
     * @return retained score
     */
    public int getScore(){return score;}

    /**
     * Getter for time
     * @return the game time in miliseconds
     */
    public int getTime(){return time;}

}
