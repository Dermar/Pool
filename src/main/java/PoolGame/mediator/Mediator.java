package PoolGame.mediator;

import PoolGame.GameManager;
import PoolGame.objects.Ball;
import javafx.scene.control.ComboBox;
import java.util.List;

/**
 * Mediator is the entry-point from GameWindow to the logic behind the cheat mechanism- used to reduce coupling and increase extensibility
 */
public interface Mediator {
    /**
     * Notifies Cheat of a change in GameManager for cheat to act on it
     * @param gameManager
     * @param clicked the string value of the cheat button that has just been chosen in gameWindow
     */
    public void notify(GameManager gameManager, String clicked);

    public ComboBox getBox();
    public void setBalls(List<Ball> newBalls);
    public List<Ball> getBalls();
}
