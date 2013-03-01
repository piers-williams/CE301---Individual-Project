package Project.Game.AI.SPL.Orders;

import Project.Game.Blueprints.Blueprint;
import Project.Game.Vector2D;

/**
 *
 */
public class BuildOrder extends BasicOrder {

    private Vector2D location;
    private Blueprint blueprint;


    public BuildOrder(Vector2D location, Blueprint blueprint, double priority) {
        super(priority, "Build");
        this.location = location;
        this.blueprint = blueprint;
    }

    public Vector2D getLocation() {
        return location;
    }

    public Blueprint getBlueprint() {
        return blueprint;
    }
}
