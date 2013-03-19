package Project.Game.AI.SPL.Orders;

import Project.Game.Vector2D;

/**
 *
 */
public class DefendOrder extends BasicOrder {
    private Vector2D location;
    private int radius;
    private int numberOfUnits;

    public DefendOrder(Vector2D location, double priority) {
        this(location, 200, 20, priority);
    }

    public DefendOrder(Vector2D location, int radius, int numberOfUnits, double priority) {
        super(priority, "Defend");
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
}
