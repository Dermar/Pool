package PoolGame.objects;

/**
 * Abstract class for prototype pattern. All classes that extend from this need to have a copy method
 */
public abstract class CirclePrototype {
    protected double xPosition;
    protected double yPosition;
    protected double radius;

    /**
     * Constructor for Prototype
     * @param xPosition
     * @param yPosition
     */
    public CirclePrototype(double xPosition, double yPosition, double radius){
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.radius = radius;
    }

    /**
     * A method that uses the current object to create a copy of itself
     * @return a deep copy of itself
     */
    public abstract CirclePrototype copy();
}
