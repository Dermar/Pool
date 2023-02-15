package PoolGame.config;

import PoolGame.GameManager;
import PoolGame.objects.Pocket;
import PoolGame.objects.Table;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Reads into pockets in the given json file and attaches the pockets it finds to table
 */
public class PocketReader implements Reader{

    /**
     * Reads into the pocket section of the given JSON file and creates the pockets to be used for the game
     * @param path the path to the correct json file
     * @param gameManager the holder for all of the game objects and game window
     */
    public void parse(String path, GameManager gameManager) {
        JSONParser parser = new JSONParser();
        ArrayList<Pocket> pockets = new ArrayList<Pocket>();

        try {
            Object object = parser.parse(new FileReader(path));

            // convert Object to JSONObject
            JSONObject jsonObject = (JSONObject) object;

            // reading the Table section:
            JSONObject jsonTable = (JSONObject) jsonObject.get("Table");

            // reading the "pockets" section:
            JSONArray jsonPockets = (JSONArray) jsonTable.get("pockets");

            // reading from the array:
            for (Object obj : jsonPockets) {
                JSONObject jsonPocket = (JSONObject) obj;

                // Pocket position
                Double positionX = (Double) ((JSONObject) jsonPocket.get("position")).get("x");
                Double positionY = (Double) ((JSONObject) jsonPocket.get("position")).get("y");
                Double radius = (Double) jsonPocket.get("radius");



                pockets.add(new Pocket(positionX, positionY, radius));
            }
            gameManager.getTable().initialisePockets(pockets);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
