package Learning.Towers.Behaviours.Collision;

import Learning.Towers.Entities.Entity;
import Learning.Towers.Main;
import Learning.Towers.Vector2D;

/**
 *
 */
public class SimpleCollision implements Collision {
    Entity entity;
    Vector2D oldCell;


    public SimpleCollision(Entity entity) {
        this.entity = entity;
        this.oldCell = Main.COLLISION_BOARD.getPoint(entity);
        Main.COLLISION_BOARD.addEntity(entity);
    }

    @Override
    public void update() {
        Vector2D cell = Main.COLLISION_BOARD.getPoint(entity);
        if (!oldCell.equals(cell)) {
            Main.COLLISION_BOARD.moveEntity(entity, new Vector2D(oldCell));
            oldCell = cell;
        }
    }
}
