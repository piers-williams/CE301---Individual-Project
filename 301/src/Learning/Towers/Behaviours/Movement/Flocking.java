package Learning.Towers.Behaviours.Movement;

import Learning.Towers.Entities.Entity;
import Learning.Towers.Entities.Meta.Group;
import Learning.Towers.Main;
import Learning.Towers.Utilities;
import Learning.Towers.Vector2D;

/**
 * Implements the Flocking behaviour
 */
public class Flocking  extends BasicMovement {

    private Group group;

    public Flocking(Entity entity, Vector2D location, Group group) {
        super(entity, location);
        this.group = group;
    }

    @Override
    public void updateSpecialisation() {
        Vector2D cohesion = calculateCohesion();
        Vector2D separation = calculateSeparation();

        if (Utilities.distance(group.getX(), group.getY(), location.x, location.y) > group.getRadius()) {
            direction.x = cohesion.x + separation.x / 2;
            direction.y = cohesion.y + separation.y / 2;

            direction.x *= 1.4;
            direction.y *= 1.4;
        } else {
            direction.x = separation.x;
            direction.y = separation.y;
        }
    }

    private Vector2D calculateCohesion() {
        double cX = 0, cY = 0;

        cX = (location.x > group.getX()) ? -1 : 1;
        cY = (location.y > group.getY()) ? -1 : 1;

        return new Vector2D(cX, cY);
    }

    private Vector2D calculateSeparation() {
        double sX = 0, sY = 0;
        for (Entity entity : group.getEntities()) {
            if (entity != this.entity) {
                double distance = Utilities.distance(entity.x, entity.y, location.x, location.y);
                if (distance < Main.SQUARE_WIDTH * 3) {
                    sX += ((location.x - entity.x > 0) ? 1 : -1);
                    sY += ((location.y - entity.y > 0) ? 1 : -1);
                }
            }
        }
        return new Vector2D(sX, sY);
    }
}
