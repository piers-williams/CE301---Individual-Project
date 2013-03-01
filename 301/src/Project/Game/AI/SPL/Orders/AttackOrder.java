package Project.Game.AI.SPL.Orders;

import Project.Game.Vector2D;

/**
 *
 */
public class AttackOrder extends BasicOrder {
    private Vector2D location;

    public AttackOrder(Vector2D location, double priority) {
        super(priority, "Attack");
        this.location = location;
    }

    public Vector2D getLocation() {
        return location;
    }
}
