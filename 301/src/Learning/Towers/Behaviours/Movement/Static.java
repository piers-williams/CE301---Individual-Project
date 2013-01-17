package Learning.Towers.Behaviours.Movement;

import Learning.Towers.Entities.Entity;
import Learning.Towers.Vector2D;

/**
 * Completely static movement implementation
 * Any attempt to move this will cause it to move back
 * <p/>
 * Will not move
 */
public class Static implements Movement {
    private Vector2D location;
    private Vector2D _location;
    private Vector2D direction;
    private Entity entity;

    public Static(Entity entity, Vector2D location) {
        this.entity = entity;
        this.location = location;
        _location = new Vector2D(location);
    }

    @Override
    public void update() {
        // If we have somehow moved and need to reset
        if(!location.equals(_location)){
            location.x = _location.x;
            location.y = _location.y;
        }
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
