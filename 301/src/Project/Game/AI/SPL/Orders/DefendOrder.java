package Project.Game.AI.SPL.Orders;

import Project.Game.Vector2D;

/**
 *
 */
public class DefendOrder extends BasicOrder {
    private Vector2D location;

    public DefendOrder(Vector2D location, double priority) {
        super(priority, "Defend");
        this.location = location;
    }

    public Vector2D getLocation() {
        return location;
    }
}
