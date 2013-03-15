package Project.Game.AI.SPL.Orders;

import Project.Game.Vector2D;

/**
 *
 */
public class AttackOrder extends BasicOrder {
    private Vector2D location;

    private int numberOfUnits;

    public AttackOrder(Vector2D location, double priority) {
        this(location, priority, 15);
    }

    public AttackOrder(Vector2D location, double priority, int numberOfUnits) {
        super(priority, "Attack");
        this.location = location;
        this.numberOfUnits = numberOfUnits;
    }

    public Vector2D getLocation() {
        return location;
    }

    public int getNumberOfUnits() {
        return numberOfUnits;
    }

    @Override
    public String toString() {
        return super.toString() + "Attack Order: Location: " + location + " Number of Units: " + numberOfUnits;
    }
}
