package Project.Game.Behaviours.Movement;

import Project.Game.Entities.Entity;
import Project.Game.Vector2D;

/**
 *
 */
public class RadiusPatrol extends BasicMovement {
    private Vector2D centerLocation;
    private int radius;

    public RadiusPatrol(Entity entity, Vector2D location) {
        super(entity, location);
    }

    @Override
    public void updateSpecialisation() {

    }
}
