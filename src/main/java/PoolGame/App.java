package PoolGame;
import javafx.application.Application;
import javafx.stage.Stage;

/** Main application entry point. */
public class App extends Application {
    /**
     * @param args First argument is the path to the config file
     */
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    /**
     * Starts the application on the default easy mode.
     * 
     * @param primaryStage The primary stage for the application.
     */
    public void start(Stage primaryStage) {
        primaryStage.setMaximized(true);
        primaryStage.setFullScreen(true);
        // Set the game difficulty as easy on start
        GameManager gameManager = new GameManager();
        gameManager.getDifficultyState().modeSet(gameManager);

        // Initialise the stage
        primaryStage.setTitle("Pool");
    
        primaryStage.setScene(gameManager.getScene());
        primaryStage.setWidth(Config.SCREENWIDTH);
        primaryStage.setHeight(Config.SCREENHEIGHT);
        //primaryStage.setWidth(gameManager.getTable().getxLength() + Config.getTableBuffer() * 2);
        //primaryStage.setHeight(gameManager.getTable().getyLength() + Config.getTableBuffer() * 2);
        primaryStage.show();
        gameManager.run();

    }

}
