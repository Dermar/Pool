package PoolGame.strategy;

/** Holds strategy for when balls enter a pocket. */
public abstract class PocketStrategy {
    /** Number of lives the ball has. */
    protected int lives;

    /**
     * Removes a life from the ball and determines if ball should be active.
     * 
     * @return true if ball should be removed, false otherwise.
     */
    public boolean remove() {
        this.lives--;

        if (this.lives == 0) {
            return true;
        }
        return false;
    }

    /**
     * Resets the ball to its original state.
     */
    public abstract void reset();
}
