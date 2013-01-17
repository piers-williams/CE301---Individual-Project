package Learning.Towers.Behaviours.Movement;

import Learning.Towers.Entities.Entity;
import Learning.Towers.Vector2D;

/**
 * Completely static movement implementation
 * <p/>
 * Will not move
 */
public class Static implements Movement {
    private Vector2D location;
    private Vector2D direction;
    private Entity entity;

    public Static(Entity entity, Vector2D location) {
        this.entity = entity;
        this.location = location;
    }

    @Override
    public void update() {
    }

    @Override
    public Vector2D getLocation() {
        return location;
    }

    @Override
    public Vector2D getDirection() {
        return direction;
    }

    @Override
    public void setDirection(Vector2D direction) {
        this.direction = direction;
    }

    @Override
    public void setLocation(Vector2D location) {
        this.location = location;
    }

    @Override
    public Entity getEntity() {
        return entity;
    }
}
