package PoolGame;

import PoolGame.config.*;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import javafx.application.Application;
import javafx.stage.Stage;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

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
        // Set the game difficulty as easy on start
        GameManager gameManager = new GameManager();
        gameManager.getDifficultyState().modeSet(gameManager);

        // Initialise the stage
        primaryStage.setTitle("Pool");
        primaryStage.setScene(gameManager.getScene());
        primaryStage.setWidth(gameManager.getTable().getxLength() + Config.getTableBuffer() * 2);
        primaryStage.setHeight(gameManager.getTable().getyLength() + Config.getTableBuffer() * 2);
        primaryStage.show();
        gameManager.run();

    }

    /**
     * Checks if the config file path is given as an argument.
     *
     * @param args
     * @return config path.
     */
    private static String checkConfig(List<String> args) {
        String configPath;
        if (args.size() > 0) {
            configPath = args.get(0);
        } else {
            configPath = "src/main/resources/config.json";
        }
        return configPath;
    }

}
