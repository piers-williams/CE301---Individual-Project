package Learning.Towers.Behaviours.Movement;

import Learning.Towers.Entities.Entity;
import Learning.Towers.Main;
import Learning.Towers.Vector2D;

/**
 * Implements common sections of Movement aspects
 * <p/>
 * Should force units to bounce on the screen etc
 */
public abstract class BasicMovement implements Movement {
    protected Entity entity;
    protected Vector2D location;
    protected Vector2D direction;

    protected BasicMovement(Entity entity, Vector2D location) {
        direction = new Vector2D(1, 1);
        this.entity = entity;
        this.location = location;
    }

    @Override
    public final void update() {
        updateSpecialisation();
        boundaryCheck();
    }

    public abstract void updateSpecialisation();

    private void boundaryCheck() {
        if (location.x < 0 || location.x > Main.MAP_WIDTH) direction.x *= -1;
        if (location.y < 0 || location.y > Main.MAP_HEIGHT) direction.y *= -1;
    }

    @Override
    public final Vector2D getLocation() {
        return location;
    }

    @Override
    public void setLocation(Vector2D location) {
        this.location = location;
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
    public Entity getEntity() {
        return entity;
    }
}
