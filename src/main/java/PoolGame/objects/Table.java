package PoolGame.objects;

import javafx.scene.paint.Paint;
import java.util.*;
/** Holds properties of the table object. */
public class Table{

    private Paint colour;
    private Long xLength;
    private Long yLength;
    private Double friction;
    private List<Pocket> pockets = new ArrayList<Pocket>();


    /**
     * Constructor for the game's Pool Table
     * @param colour the background of the table
     * @param xLength the width
     * @param yLength height
     * @param friction coefficient at which balls slow down over time
     */
    public Table(String colour, Long xLength, Long yLength, Double friction) {
        this.colour = Paint.valueOf(colour);
        this.xLength = xLength;
        this.yLength = yLength;
        this.friction = friction;
    }


    /**
     * Gets the colour of the table.
     * 
     * @return colour
     */
    public Paint getColour() {
        return colour;
    }

    /**
     * Gets the x length of the table.
     * 
     * @return xLength
     */
    public Long getxLength() {
        return xLength;
    }

    /**
     * Gets the y length of the table.
     * 
     * @return yLength
     */
    public Long getyLength() {
        return yLength;
    }

    /**
     * Gets the friction of the table.
     * 
     * @return friction
     */
    public Double getFriction() {
        return friction;
    }

    /**
     * Gets the pockets of the table.
     * 
     * @return pockets
     */
    public List<Pocket> getPockets() {
        return pockets;
    }

    /**
     * Initialises the pockets of the table after PocketReader configures them
     */
    public void initialisePockets(ArrayList<Pocket> pockets) {this.pockets = pockets;}

}
