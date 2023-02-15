package PoolGame.strategy;

public class BlueStrategy extends PocketStrategy {
    /** Creates a new blue strategy. */
    public BlueStrategy() {
        this.lives = 2;
    }

    /**
     * Returns the BallStrategy to its initial state
     */
    public void reset() {
        this.lives = 2;
    }
}
