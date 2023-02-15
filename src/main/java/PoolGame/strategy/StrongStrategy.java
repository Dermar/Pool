package PoolGame.strategy;

/**
 * Strategy for brown and black balls
 */
public class StrongStrategy extends PocketStrategy{
    /** Creates a new strategy for brown and black balls. */
    public StrongStrategy() {
        this.lives = 3;
    }

    /**
     * Returns the BallStrategy to its initial state
     */
    public void reset() {
        this.lives = 3;
    }
}
