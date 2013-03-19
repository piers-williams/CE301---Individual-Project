package Project.Game.AI.SPL.Orders;

import Project.Game.Vector2D;

/**
 *
 */
public class DefendOrder extends BasicOrder {
    private Vector2D location;
    private int radius;
    private int numberOfUnits;

    public DefendOrder(Vector2D location, double priority, boolean wasNLP) {
        this(location, 200, 20, priority, wasNLP);
    }

    public DefendOrder(Vector2D location, int radius, int numberOfUnits, double priority, boolean wasNLP) {
        super(priority, "Defend", wasNLP);
        this.location = location;
        this.numberOfUnits = numberOfUnits;
        this.radius = radius;
    }

    public Vector2D getLocation() {
        return location;
    }

    public int getRadius() {
        return radius;
    }

    public int getNumberOfUnits() {
        return numberOfUnits;
    }

    @Override
    public String toString() {
        return super.toString() + "Defend Order: Location: " + location + " Number of Units: " + numberOfUnits + " Radius: " + radius;
    }
}
