package PoolGame.difficultyState;

import PoolGame.GameManager;

/**
 * Interface for difficulty states
 */
public interface State {

    /**
     * Method that sets the gameManager's difficulty
     * @param gameManager
     */
    public void modeSet(GameManager gameManager);
}
