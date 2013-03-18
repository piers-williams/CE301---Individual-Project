package Project.Game.Behaviours.Collision;

import Project.Game.Behaviours.Movement.Movement;
import Project.Game.Entities.Entity;
import Project.Game.Main;
import Project.Game.Vector2D;

/**
 *
 */
public class SimpleCollision implements Collision {
    private Entity entity;
    private Vector2D oldCell;
    private int width;


    public SimpleCollision(Entity entity, int width) {
        this.entity = entity;
        this.oldCell = Main.COLLISION_BOARD.getPoint(entity);
        Main.COLLISION_BOARD.addEntity(entity);

        this.width = width;
    }

    @Override
    public void update() {
        Vector2D cell = Main.COLLISION_BOARD.getPoint(entity);
        if (!oldCell.equals(cell)) {
            Main.COLLISION_BOARD.moveEntity(entity, new Vector2D(oldCell));
            oldCell = cell;
        }
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public Entity getEntity() {
        return entity;
    }

    public static boolean collidesWith(Collision first, Collision second) {
        if (first.getEntity().equals(second.getEntity())) return false;

        Vector2D firstLocation = first.getEntity().getMovementBehaviour().getLocation();
        Vector2D secondLocation = second.getEntity().getMovementBehaviour().getLocation();

        double distance = firstLocation.dist(secondLocation);

        return (distance < (first.getWidth() + second.getWidth()) / 2);
    }

    public static void bounce(Collision first, Collision second) {
        Movement firstMovement, secondMovement;

        firstMovement = first.getEntity().getMovementBehaviour();
        secondMovement = second.getEntity().getMovementBehaviour();

        Vector2D tempDirection = firstMovement.getDirection();
        firstMovement.setDirection(secondMovement.getDirection());
        secondMovement.setDirection(tempDirection);

        double distance = firstMovement.getLocation().dist(secondMovement.getLocation());
        double ang = Math.atan2(
                secondMovement.getLocation().getY() - firstMovement.getLocation().getY(),
                secondMovement.getLocation().getX() - firstMovement.getLocation().getX()
        );
        double mov = first.getWidth() - Math.sqrt(distance);
        mov /= 2;

        Vector2D location = firstMovement.getLocation();
        location.x -= Math.cos(ang) * mov;
        location.y -= Math.sin(ang) * mov;
        firstMovement.setLocation(location);

        location = secondMovement.getLocation();
        ang -= Math.PI;
        location.x -= Math.cos(ang) * mov;
        location.y -= Math.sin(ang) * mov;
        secondMovement.setLocation(location);
    }


}
