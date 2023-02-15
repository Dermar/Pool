package PoolGame.mediator;

import PoolGame.Config;
import PoolGame.GameManager;
import PoolGame.objects.Ball;
import java.util.*;

import javafx.collections.FXCollections;
import javafx.scene.control.ComboBox;

/**
 * Cheat
 */
public class Cheat implements Mediator{
    private List<Ball> balls;
    private ComboBox comboBox;

    /**
     * Constructor for Cheat. Upon instantiation,Cheat decides which values should be in the gm
     * drop-down list by looking into gm's ball types .
     * @param gm a reference to the game manager that this cheat mechanism will work on
     */
    public Cheat(GameManager gm){
        balls = gm.getBalls();

        // Cheat button
        List<String> options = new ArrayList<>();
        for (Ball ball: balls){
            String colour = Config.getColours().get(ball.getColour());
            String cap = colour.substring(0, 1).toUpperCase() + colour.substring(1);
            if (!options.contains(cap + " balls") && !colour.equals("white")) {
                options.add(cap + " balls");
            }
        }

        this.comboBox = new ComboBox(FXCollections.observableArrayList(options));
    }

    /**
     * Removes the balls corresponding to the cheat choice the user just made in gameManager
     * @param gameManager holds the balls and the score of the game
     * @param clicked the string of the option that the user just chose - contains the colour of the balls we want to remove
     */
    @Override
    public void notify(GameManager gameManager, String clicked) {
        String[] given = clicked.split(" ");
        String colourToRemove = given[0].toLowerCase();
        List<Ball> toRemove = new ArrayList<>();
        for (Ball b: balls){
            if (Config.getColours().get(b.getColour()).equals(colourToRemove)){
                toRemove.add(b);
                gameManager.setScore(gameManager.getScore() + b.getScore());
            }
        }
        for (Ball c: toRemove){
            balls.remove(c);
        }
    }

    //Getters and setters
    public ComboBox getBox(){return this.comboBox;}
    public List<Ball> getBalls(){return balls;}
    public void setBalls(List<Ball> newBalls){balls = newBalls;}
}
