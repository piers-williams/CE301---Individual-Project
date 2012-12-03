package Learning.Towers.Behaviours.Movement;

import Learning.Towers.Vector2D;

/**
 * Completely static movement implementation
 *
 * Will not move
 */
public class Static implements Movement {
    private Vector2D location;

    public Static(Vector2D location) {
        this.location = location;
    }

    @Override
    public void update() {
    }

    @Override
    public Vector2D getLocation() {
        return location;
    }
}
