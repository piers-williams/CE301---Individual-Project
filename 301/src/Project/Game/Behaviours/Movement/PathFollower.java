package Project.Game.Behaviours.Movement;

import Project.Game.Entities.Entity;
import Project.Game.Vector2D;

/**
 * Follows a path to the target location
 */
public class PathFollower extends BasicMovement {

    private Vector2D targetLocation;
    private int distanceThreshold;
    private boolean stopped = false;

    public PathFollower(Entity entity, Vector2D location, Vector2D target, int distanceThreshold) {
        super(entity, location);
        this.targetLocation = target;
        this.distanceThreshold = distanceThreshold;
    }

    @Override
    public void updateSpecialisation() {
        if (!stopped) {
            if (location.dist(targetLocation) < distanceThreshold) {
                stopped = true;
            } else {
                location.add(location.getNormalDirectionBetween(targetLocation));
            }

        }
    }
}
