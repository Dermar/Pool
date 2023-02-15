package PoolGame.difficultyState;

import PoolGame.GameManager;
import PoolGame.config.*;

/**
 * Normal mode
 */
public class NormalState implements State{
    /**
     * Sets the game to normal mode by reading into config_normal.json
     * @param gameManager
     */
    @Override
    public void modeSet(GameManager gameManager){
        String configPath = "./src/main/resources/config_normal.json";
        // READ IN CONFIG
        // Read in the info for table
        ReaderFactory tableFactory = new TableReaderFactory();
        Reader tableReader = tableFactory.buildReader();
        tableReader.parse(configPath, gameManager);

        // Read in the info for the balls
        ReaderFactory ballFactory = new BallReaderFactory();
        Reader ballReader = ballFactory.buildReader();
        ballReader.parse(configPath, gameManager);

        // Read in the info for the pockets
        ReaderFactory pocketFactory = new PocketReaderFactory();
        Reader pocketReader = pocketFactory.buildReader();
        pocketReader.parse(configPath, gameManager);

        gameManager.buildManager();

        // START GAME MANAGER
        gameManager.run();
    }
}
