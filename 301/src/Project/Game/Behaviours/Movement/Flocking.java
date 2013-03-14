package Project.Game.Behaviours.Movement;

import Project.Game.Entities.Entity;
import Project.Game.Entities.Meta.Group;
import Project.Game.Main;
import Project.Game.Utilities;
import Project.Game.Vector2D;

/**
 * Implements the Flocking behaviour
 */
public class Flocking extends BasicMovement {

    private Group group;

    public Flocking(Entity entity, Vector2D location, Group group) {
        super(entity, location);
        this.group = group;
    }

    @Override
    public void updateSpecialisation() {
        if (direction == null) {
            direction = new Vector2D();
        }
        Vector2D separation = calculateSeparation();

        if (Utilities.distance(group.getX(), group.getY(), location.x, location.y) > group.getRadius()) {

            Vector2D cohesion = calculateCohesion();

            direction.x = cohesion.x + separation.x / 2;
            direction.y = cohesion.y + separation.y / 2;

            direction.x *= 1.4;
            direction.y *= 1.4;
        } else {
            direction.x = separation.x;
            direction.y = separation.y;
        }

        location.add(direction);
    }

    private Vector2D calculateCohesion() {
        double cX, cY;

        cX = (location.x > group.getX()) ? -1 : 1;
        cY = (location.y > group.getY()) ? -1 : 1;

        return Main.VECTOR2D_SOURCE.getVector(cX, cY);
    }

    private Vector2D calculateSeparation() {
        double sX = 0, sY = 0;
        for (Entity entity : group.getEntities()) {
            if (entity != this.entity) {
                double distance = Utilities.distance(entity.getX(), entity.getY(), location.x, location.y);
                if (distance < Main.SQUARE_WIDTH * 3) {
                    sX += ((location.x - entity.getX() > 0) ? 1 : -1);
                    sY += ((location.y - entity.getY() > 0) ? 1 : -1);
                }
            }
        }
        return Main.VECTOR2D_SOURCE.getVector(sX, sY);
    }
}
