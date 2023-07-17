package PoolGame;

import java.util.*;
import java.awt.*;
import javafx.scene.paint.Paint;

/** Holds static final data. */
public class Config {

    
    private static final double TABLEEDGE = 10;

    private static final int CUEHEIGHT = 400;
    private static final int CUEWIDTH = 15;
    private static final int CUESTARTX = 100;
    private static final int CUESTARTY = 300;
    private static final double FORCEFACTOR = 0.05;
    private static final Dimension s = Toolkit.getDefaultToolkit().getScreenSize();

    public static final double SCREENWIDTH = s.getWidth();
    public static final double SCREENHEIGHT = s.getHeight();
    private static final double TABLEBUFFER = (SCREENWIDTH - 1600)/2;


    // Scores for each colour of ball
    private static Map<String, Integer> BALLSCORES;
    static {
        BALLSCORES = new HashMap<String, Integer>();
        BALLSCORES.put("white", 0);
        BALLSCORES.put("red", 1);
        BALLSCORES.put("yellow", 2);
        BALLSCORES.put("green", 3);
        BALLSCORES.put("brown", 4);
        BALLSCORES.put("blue", 5);
        BALLSCORES.put("purple", 6);
        BALLSCORES.put("black", 7);
        BALLSCORES.put("orange", 8);
    }
    private static final Map<Paint, String> PAINTSTRINGS;
    static{
        PAINTSTRINGS = new HashMap<Paint, String>();
        PAINTSTRINGS.put(Paint.valueOf("white"), "white");
        PAINTSTRINGS.put(Paint.valueOf("red"), "red");
        PAINTSTRINGS.put(Paint.valueOf("yellow"), "yellow");
        PAINTSTRINGS.put(Paint.valueOf("green"), "green");
        PAINTSTRINGS.put(Paint.valueOf("brown"), "brown");
        PAINTSTRINGS.put(Paint.valueOf("blue"), "blue");
        PAINTSTRINGS.put(Paint.valueOf("purple"), "purple");
        PAINTSTRINGS.put(Paint.valueOf("black"), "black");
        PAINTSTRINGS.put(Paint.valueOf("orange"), "orange");
    }


    /**
     * Returns the buffer around the table.
     * 
     * @return buffer
     */
    public static double getTableBuffer() {
        return TABLEBUFFER;
    }

    /**
     * Returns the edge of the table.
     * 
     * @return edge length.
     */
    public static double getTableEdge() {
        return TABLEEDGE;
    }

    /**
     * Returns the configured scores for each colour of ball as a HashMap
     * @return BALLSCORES: a Map where the key is a string of a colour and the value is that coloured ball's score value
     */
    public static Map<String, Integer> getScores(){
        return BALLSCORES;
    }

    public static Map<Paint, String> getColours(){return PAINTSTRINGS;}

    public static int getCueStartX(){return CUESTARTX;}
    public static int getCueStartY(){return CUESTARTY;}
    public static int getCueWidth(){return CUEWIDTH;}
    public static int getCueHeight(){return CUEHEIGHT;}
    public static double getForcefactor(){return FORCEFACTOR;}
}
