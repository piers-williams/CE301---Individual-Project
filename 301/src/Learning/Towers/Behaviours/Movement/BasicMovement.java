package Learning.Towers.Behaviours.Movement;

import Learning.Towers.Entities.Entity;
import Learning.Towers.Main;
import Learning.Towers.Vector2D;

/**
 *
 */
public abstract class BasicMovement implements Movement {
    private Entity entity;
    private Vector2D location;
    private Vector2D direction;

    Vector2D oldCell;


    protected BasicMovement(Entity entity, Vector2D location) {
        this.entity = entity;
        this.location = location;
        this.direction = new Vector2D(0,0);
        this.oldCell = Main.COLLISION_BOARD.getPoint(entity);
    }

    @Override
    public void update() {
        if (location.x < 0 || location.x > Main.MAP_WIDTH) direction.x *= -1;
        if (location.y < 0 || location.y > Main.MAP_HEIGHT) direction.y *= -1;


        Vector2D cell = Main.COLLISION_BOARD.getPoint(entity);
        if (!oldCell.equals(cell)) {
            Main.COLLISION_BOARD.moveEntity(entity, new Vector2D(oldCell));
            oldCell = cell;
        }
    }

    @Override
    public Vector2D getLocation() {
        return null;
    }
}
