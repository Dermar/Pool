package PoolGame.strategy;

public class BallStrategy extends PocketStrategy {
    /**
     * Creates a new ball strategy.
     */
    public BallStrategy() {
        this.lives = 1;
    }

    /**
     * Returns the BallStrategy to its initial state
     */
    public void reset() {
        this.lives = 1;
    }
}
