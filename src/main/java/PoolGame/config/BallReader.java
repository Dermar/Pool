package PoolGame.config;

import PoolGame.objects.*;
import PoolGame.GameManager;
import java.util.ArrayList;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/** Reads in ball section of JSON. */
public class BallReader implements Reader {

	/**
	 * Parses the JSON file and builds the balls.
	 * 
	 * @param path        The path to the JSON file.
	 * @param gameManager The game manager.
	 */
	public void parse(String path, GameManager gameManager) {
		JSONParser parser = new JSONParser();
		ArrayList<Ball> balls = new ArrayList<Ball>();

		try {
			Object object = parser.parse(new FileReader(path));

			// convert Object to JSONObject
			JSONObject jsonObject = (JSONObject) object;

			// reading the "Balls" section:
			JSONObject jsonBalls = (JSONObject) jsonObject.get("Balls");

			// reading the "Balls: ball" array:
			JSONArray jsonBallsBall = (JSONArray) jsonBalls.get("ball");
			// reading from the array:
			for (Object obj : jsonBallsBall) {
				JSONObject jsonBall = (JSONObject) obj;

				// the ball colour is a String
				String colour = (String) jsonBall.get("colour");

				// the ball position, velocity, mass are all doubles
				Double positionX = (Double) ((JSONObject) jsonBall.get("position")).get("x");
				Double positionY = (Double) ((JSONObject) jsonBall.get("position")).get("y");

				// Check ball is within bounds
				Table table = gameManager.getTable();
				if (positionX > table.getxLength() || positionX < 10 || positionY > table.getyLength()
						|| positionY < 10) {
					System.out.println("Ball position is outside the table");
					System.exit(0);
				}

				Double velocityX = (Double) ((JSONObject) jsonBall.get("velocity")).get("x");
				Double velocityY = (Double) ((JSONObject) jsonBall.get("velocity")).get("y");

				Double mass = (Double) jsonBall.get("mass");

				// Builder code
				PoolBallBuilder builder = new PoolBallBuilder();
				builder.setColour(colour);
				builder.setxPos(positionX);
				builder.setyPos(positionY);
				builder.setxVel(velocityX);
				builder.setyVel(velocityY);
				builder.setMass(mass);
				balls.add(builder.build());

			}
			gameManager.setBalls(balls);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
}