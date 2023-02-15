package PoolGame;

import PoolGame.mediator.Cheat;
import PoolGame.mediator.Mediator;
import PoolGame.memento.GameCaretaker;
import PoolGame.memento.GameMemento;
import PoolGame.difficultyState.*;
import PoolGame.objects.*;

import java.util.ArrayList;
import java.util.List;

import javafx.geometry.Point2D;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;

import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.scene.paint.Paint;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;

import javafx.scene.text.Text;
import javafx.util.Duration;
import javafx.util.Pair;

/**
 * Controls the game interface; drawing objects, handling logic and collisions.
 */
public class GameManager {
    private Table table;
    private ArrayList<Ball> balls = new ArrayList<Ball>();

    private Line cue = new Line();
    private boolean cueSet;
    private boolean cueActive;
    private Ball cueBall;
    private boolean winFlag = false;
    private int score = 0;
    private int currTime = 0;
    private final double TABLEBUFFER = Config.getTableBuffer();
    private final double TABLEEDGE = Config.getTableEdge();

    private Text scoreNum;
    private Mediator cheatMechanism;
    private Text timeNum;
    private GraphicsContext gc;
    private Pane pane = new Pane();
    private Scene scene = new Scene(pane);
    private GameCaretaker stateKeep = new GameCaretaker();
    private State difficultyState = new EasyState(); // The game starts on easy mode

    /**
     * Initialises timeline and cycle count.
     */
    public void run() {
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(17),
                t -> this.draw()));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }


    /**
     * Builds GameManager properties such as initialising pane, canvas,
     * graphicscontext, and setting events related to clicks.
     */
    public void buildManager() {
        currTime = 0;
        score = 0;

        // Restart the pane every time you look into a new file
        pane.getChildren().clear();

        timeNum = new Text(table.getxLength() + 20, 20, "Time: 0:00");
        scoreNum = new Text(table.getxLength() + 100, 20, "Score: " + score);

        Canvas canvas = new Canvas(table.getxLength() + TABLEBUFFER * 2, table.getyLength() + TABLEBUFFER * 2);
        gc = canvas.getGraphicsContext2D();

        // We need to know the cueBall for CueStick to detect so let's look for it
        for (Ball b: balls){
            if (b.isCue()){
                cueBall = b;
            }
        }
        // The cueStick is always present on the screen and starts at the left of the table
        cue.setStartX(Config.getCueStartX());
        cue.setStartY(Config.getCueStartY());
        cue.setEndX(Config.getCueStartX());
        cue.setEndY(Config.getCueStartY() + Config.getCueHeight());

        setClickEvents(pane);
        pane.getChildren().add(cue);

        // Difficulty buttons
        Button easy = new Button("Easy Mode");
        easy.setOnAction(value -> {
            // reset the game to easy mode
            difficultyState = new EasyState();
            difficultyState.modeSet(this);

        });
        easy.setLayoutX(table.getxLength() - 50);
        easy.setLayoutY(100);

        Button normal = new Button("Normal Mode");
        normal.setOnAction(value -> {
            // reset the game to normal mode
            difficultyState = new NormalState();
            difficultyState.modeSet(this);
        });
        normal.setLayoutX(table.getxLength() + 30);
        normal.setLayoutY(100);

        Button hard = new Button("Hard Mode");
        hard.setOnAction(value -> {
            difficultyState = new HardState();
            difficultyState.modeSet(this);
        });
        hard.setLayoutX(table.getxLength() + 130);
        hard.setLayoutY(100);

        // Undo button
        Button undo = new Button("Undo");
        undo.setOnAction(value -> {
            setMemento(stateKeep.getMemento());
        });
        undo.setLayoutX(table.getxLength() + TABLEBUFFER + 20);
        undo.setLayoutY(200);

        cheatMechanism = new Cheat(this);
        ComboBox comboBox = cheatMechanism.getBox();

        comboBox.setOnAction((event) -> {
            cheatMechanism.notify(this, String.valueOf(comboBox.getValue()));
        });

        comboBox.setLayoutX(40);
        Text cheat = new Text(5, 20, "Cheat: ");
        cheat.setFill(Paint.valueOf("black"));
        // Add all the buttons and the canvas to the pane
        pane.getChildren().add(canvas);
        pane.getChildren().add(hard);
        pane.getChildren().add(easy);
        pane.getChildren().add(normal);
        pane.getChildren().add(timeNum);
        pane.getChildren().add(scoreNum);
        pane.getChildren().add(undo);
        pane.getChildren().add(comboBox);
        pane.getChildren().add(cheat);
    }

    /**
     * Draws all relevant items - table, cue, balls, pockets - onto Canvas.
     * Used Exercise 6 as reference.
     */
    private void draw() {
        tick();

        // Display score and time
        scoreNum.setText("Score: " + score);
        if (!winFlag){
            currTime += 7;
        }
        long seconds = currTime/1000 % 60;
        long minutes = currTime/1000/60;
        timeNum.setText("Time: " + minutes + ":" + seconds);

        // Fill in background
        gc.setFill(Paint.valueOf("white"));
        gc.fillRect(0, 0, table.getxLength() + TABLEBUFFER * 2, table.getyLength() + TABLEBUFFER * 2);


        // Fill in edges
        gc.setFill(Paint.valueOf("brown"));
        gc.fillRect(TABLEBUFFER - TABLEEDGE, TABLEBUFFER - TABLEEDGE, table.getxLength() + TABLEEDGE * 2,
                table.getyLength() + TABLEEDGE * 2);

        // Fill in Table
        gc.setFill(table.getColour());
        gc.fillRect(TABLEBUFFER, TABLEBUFFER, table.getxLength(), table.getyLength());

        // Fill in Pockets
        for (Pocket pocket : table.getPockets()) {
            gc.setFill(Paint.valueOf("black"));
            gc.fillOval(pocket.getxPos() - pocket.getRadius(), pocket.getyPos() - pocket.getRadius(),
                    pocket.getRadius() * 2, pocket.getRadius() * 2);
        }

        // Cue draw
        gc.setStroke(Paint.valueOf("orange"));
        gc.setLineWidth(Config.getCueWidth());
        gc.strokeLine(cue.getStartX(), cue.getStartY(), cue.getEndX(), cue.getEndY());

        for (Ball ball : balls) {
            gc.setFill(ball.getColour());
            gc.fillOval(ball.getxPos() - ball.getRadius(),
                    ball.getyPos() - ball.getRadius(),
                    ball.getRadius() * 2,
                    ball.getRadius() * 2);

        }

        // Win
        if (winFlag) {
            gc.setStroke(Paint.valueOf("white"));
            gc.setFont(new Font("Impact", 80));
            gc.setLineWidth(1);
            gc.strokeText("Win and bye", table.getxLength() / 2 + TABLEBUFFER - 180,
                    table.getyLength() / 2 + TABLEBUFFER);
        }

    }

    /**
     * Updates positions of all balls, handles logic related to collisions.
     * Used Exercise 6 as reference.
     */
    public void tick() {

        // Check win
        if (balls.size() == 1) {
            winFlag = true;
        }

        // Process movement for balls
        List<Ball> toRemove = new ArrayList<Ball>();
        for (Ball ball : balls) {
            // Make memento if the cue ball isn't moving:
            if (ball.isCue() && ball.getyVel() == 0 && ball.getxVel() == 0){
                stateKeep.setMemento(saveState());
            }
            ball.tick();

            double width = table.getxLength();
            double height = table.getyLength();

            // Check if ball landed in pocket
            for (Pocket pocket : table.getPockets()) {
                if (pocket.isInPocket(ball)) {
                    if (ball.isCue()) {
                        this.reset();
                    } else {
                        score += ball.getScore();
                        if (!ball.remove()) {
                            // Check if when ball is replaced, any other balls are present in its space. (If
                            // another ball is present, blue ball is removed)
                            for (Ball otherBall : balls) {
                                if (otherBall != ball && checkCollision(otherBall, ball)) {
                                    ball.remove();
                                }
                            }
                        }
                        else{
                            toRemove.add(ball);
                        }
                    }
                    break;
                }
            }

            // Handle the edges (balls don't get a choice here)
            if (ball.getxPos() + ball.getRadius() > width + TABLEBUFFER) {
                ball.setxPos(width - ball.getRadius());
                ball.setxVel(ball.getxVel() * -1);
            }
            if (ball.getxPos() - ball.getRadius() < TABLEBUFFER) {
                ball.setxPos(ball.getRadius());
                ball.setxVel(ball.getxVel() * -1);
            }
            if (ball.getyPos() + ball.getRadius() > height + TABLEBUFFER) {
                ball.setyPos(height - ball.getRadius());
                ball.setyVel(ball.getyVel() * -1);
            }
            if (ball.getyPos() - ball.getRadius() < TABLEBUFFER) {
                ball.setyPos(ball.getRadius());
                ball.setyVel(ball.getyVel() * -1);
            }

            // Apply table friction
            double friction = table.getFriction();
            ball.setxVel(ball.getxVel() / (friction*1.119));
            ball.setyVel(ball.getyVel() / (friction*1.119));

            if (Math.abs(ball.getyVel()) < 0.035 && Math.abs(ball.getxVel()) < 0.035) {
                ball.setxVel(0);
                ball.setyVel(0);
            }

            // Check ball collisions
            for (Ball ballB : balls) {
                if (checkCollision(ball, ballB)) {
                    Point2D ballPos = new Point2D(ball.getxPos(), ball.getyPos());
                    Point2D ballBPos = new Point2D(ballB.getxPos(), ballB.getyPos());
                    Point2D ballVel = new Point2D(ball.getxVel(), ball.getyVel());
                    Point2D ballBVel = new Point2D(ballB.getxVel(), ballB.getyVel());
                    Pair<Point2D, Point2D> changes = calculateCollision(ballPos, ballVel, ball.getMass(), ballBPos,
                            ballBVel, ballB.getMass(), false);
                    calculateChanges(changes, ball, ballB);
                }
            }

        }
        // Remove all the balls we dropped in this frame
        for (Ball b: toRemove){
            balls.remove(b);
        }
    }

    /**
     * Sets the cue to be drawn on click, and manages cue actions
     * @param pane
     */
    private void setClickEvents(Pane pane) {
        pane.setOnMousePressed(event -> {
            cue.setStartX(event.getX());
            cue.setStartX(event.getY());
            calcEndStick();
            cueSet = false;
            cueActive = true;
        });

        pane.setOnMouseDragged(event -> {
            cue.setStartX(event.getX());
            cue.setStartY(event.getY());
            calcEndStick();
        });

        pane.setOnMouseReleased(event -> {
            cueSet = true;
            cueActive = false;
            hitBall(cueBall);
            // Set the cueStick to its standard place on release
            cue.setStartX(Config.getCueStartX());
            cue.setStartY(Config.getCueStartY());
            cue.setEndX(Config.getCueStartX());
            cue.setEndY(Config.getCueStartY() + Config.getCueHeight());
        });
    }

    /**
     * Hits the ball with the cue, distance of the cue indicates the strength of the
     * strike.
     *
     * @param ball
     */
    private void hitBall(Ball ball) {
        double deltaX = ball.getxPos() - cue.getStartX();
        double deltaY = ball.getyPos() - cue.getStartY();
        double distance = Math.sqrt(deltaX * deltaX + deltaY * deltaY);

        // Check that the cue ball has stopped moving
        if (cueBall.getxVel() == 0 && cueBall.getyVel() == 0) {
            // Collide ball with cue
            double hitxVel = (cue.getStartX() - cue.getEndX()) * distance/200 * Config.getForcefactor();
            double hityVel = (cue.getStartY() - cue.getEndY()) * distance/200 * Config.getForcefactor();

            ball.setxVel(hitxVel);
            ball.setyVel(hityVel);
        }
        cueSet = false;

    }

    /**
     * Calculates where the end of the cue stick should be so that the cue stick is always pointing towards the cue ball
     */
    public void calcEndStick(){
        // calculate the angle between the cueStick and the cue ball.
        // We have two cases: where the cueStick is in front of the cue ball or if it's behind it
        double theta;
        if (cue.getStartX() >= cueBall.getxPos()){
            theta = Math.atan((cue.getStartY() - cueBall.getyPos())/(cue.getStartX() - cueBall.getxPos()));
        }
        else{
            theta = Math.atan((cue.getStartY() - cueBall.getyPos())/(cue.getStartX() - cueBall.getxPos())) + Math.PI;
        }

        // Calculate the distance between the cuestick tip and the ball
        double dist = Math.sqrt(Math.pow(cue.getStartX() - cueBall.getxPos(), 2) + Math.pow(cue.getStartY() - cueBall.getyPos(), 2));

        // Calculate the position of the end of the cue stick using trig
        cue.setEndX(cueBall.getxPos() + (dist + Config.getCueHeight()) * Math.cos(theta));
        cue.setEndY(cueBall.getyPos() + (dist + Config.getCueHeight()) * Math.sin(theta));

    }


    /**
     * Creates a memento of the current game state so user can undo
     * @return GameMemento: a save of the game state before the most recent shot
     */
    public GameMemento saveState(){
        return new GameMemento(balls, score, currTime);
    }

    /**
     * Set the game state to that of the previous shot once the user clicks on the undo button
     * @param m
     */
    public void setMemento(GameMemento m){
        balls = (ArrayList<Ball>) m.getBalls();
        currTime = m.getTime();
        score = m.getScore();
        // Maintain undo and cheat mechanism ball lists
        for (Ball b: balls){
            if (b.isCue()){
                cueBall = b;
            }
        }
        cheatMechanism.setBalls(balls);
    }
    /**
     * Resets the game.
     */
    public void reset() {
        difficultyState.modeSet(this);

        this.score = 0;
        this.currTime = 0;
    }


    /**
     * Changes values of balls based on collision (if ball is null ignore it)
     * 
     * @param changes
     * @param ballA
     * @param ballB
     */
    private void calculateChanges(Pair<Point2D, Point2D> changes, Ball ballA, Ball ballB) {
        ballA.setxVel(changes.getKey().getX());
        ballA.setyVel(changes.getKey().getY());
        if (ballB != null) {
            ballB.setxVel(changes.getValue().getX());
            ballB.setyVel(changes.getValue().getY());
        }
    }


    /**
     * Checks if two balls are colliding.
     * Used Exercise 6 as reference.
     * 
     * @param ballA
     * @param ballB
     * @return true if colliding, false otherwise
     */
    private boolean checkCollision(Ball ballA, Ball ballB) {
        if (ballA == ballB) {
            return false;
        }

        return Math.abs(ballA.getxPos() - ballB.getxPos()) < ballA.getRadius() + ballB.getRadius() &&
                Math.abs(ballA.getyPos() - ballB.getyPos()) < ballA.getRadius() + ballB.getRadius();
    }

    /**
     * Collision function adapted from assignment, using physics algorithm:
     * http://www.gamasutra.com/view/feature/3015/pool_hall_lessons_fast_accurate_.php?page=3
     *
     * @param positionA The coordinates of the centre of ball A
     * @param velocityA The delta x,y vector of ball A (how much it moves per tick)
     * @param massA     The mass of ball A (for the moment this should always be the
     *                  same as ball B)
     * @param positionB The coordinates of the centre of ball B
     * @param velocityB The delta x,y vector of ball B (how much it moves per tick)
     * @param massB     The mass of ball B (for the moment this should always be the
     *                  same as ball A)
     *
     * @return A Pair in which the first (key) Point2D is the new
     *         delta x,y vector for ball A, and the second (value) Point2D is the
     *         new delta x,y vector for ball B.
     */
    public static Pair<Point2D, Point2D> calculateCollision(Point2D positionA, Point2D velocityA, double massA,
            Point2D positionB, Point2D velocityB, double massB, boolean isCue) {

        // Find the angle of the collision - basically where is ball B relative to ball
        // A. We aren't concerned with
        // distance here, so we reduce it to unit (1) size with normalize() - this
        // allows for arbitrary radii
        Point2D collisionVector = positionA.subtract(positionB);
        collisionVector = collisionVector.normalize();

        // Here we determine how 'direct' or 'glancing' the collision was for each ball
        double vA = collisionVector.dotProduct(velocityA);
        double vB = collisionVector.dotProduct(velocityB);

        // If you don't detect the collision at just the right time, balls might collide
        // again before they leave
        // each others' collision detection area, and bounce twice.
        // This stops these secondary collisions by detecting
        // whether a ball has already begun moving away from its pair, and returns the
        // original velocities
        if (vB <= 0 && vA >= 0 && !isCue) {
            return new Pair<Point2D, Point2D>(velocityA, velocityB);
        }

        // This is the optimisation function described in the gamasutra link. Rather
        // than handling the full quadratic
        // (which as we have discovered allowed for sneaky typos)
        // this is a much simpler - and faster - way of obtaining the same results.
        double optimizedP = (2.0 * (vA - vB)) / (massA + massB);

        // Now we apply that calculated function to the pair of balls to obtain their
        // final velocities
        Point2D velAPrime = velocityA.subtract(collisionVector.multiply(optimizedP).multiply(massB));
        Point2D velBPrime = velocityB.add(collisionVector.multiply(optimizedP).multiply(massA));

        return new Pair<Point2D, Point2D>(velAPrime, velBPrime);
    }

    /**
     * Returns the caretaker for the memento pattern
     * @return a GameCaretaker object which holds the previous game state
     */
    public GameCaretaker getStateKeep(){return stateKeep;}

    /**
     * @return scene.
     */
    public Scene getScene() {
        return this.scene;
    }

    /**
     * Sets the table of the game.
     *
     * @param table
     */
    public void setTable(Table table) {
        this.table = table;
    }

    /**
     * @return table
     */
    public Table getTable() {
        return this.table;
    }

    /**
     * Sets the balls of the game.
     *
     * @param balls
     */
    public void setBalls(ArrayList<Ball> balls) {
        this.balls = balls;
    }


    public State getDifficultyState(){return difficultyState;}

    public List<Ball> getBalls(){return balls;}

    public int getScore(){return score;}
    public void setScore(int score){this.score = score;}
}
