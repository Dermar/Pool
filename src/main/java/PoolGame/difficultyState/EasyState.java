package PoolGame.difficultyState;

import PoolGame.GameManager;
import PoolGame.config.*;

/**
 * EasyState looks into config_easy to set the game to easy mode
 */
public class EasyState implements State{
    /**
     * Sets the game to easy mode
     */
    @Override
    public void modeSet(GameManager gameManager){
        String configPath = "./src/main/resources/config_easy.json";
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
