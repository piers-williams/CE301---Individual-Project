package Learning.Towers.Behaviours.Movement;

import Learning.Towers.Entities.Entity;
import Learning.Towers.Vector2D;

/**
 *  Follows a path to the target location
 */
public class PathFollower extends BasicMovement {

    Vector2D target;

    public PathFollower(Entity entity, Vector2D location, Vector2D target) {
        super(entity, location);
        this.target = target;
    }

    @Override
    public void updateSpecialisation() {

    }
}
