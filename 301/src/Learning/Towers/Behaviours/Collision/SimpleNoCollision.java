package Learning.Towers.Behaviours.Collision;

import Learning.Towers.Entities.Entity;

/**
 *
 */
public class SimpleNoCollision implements Collision {
    private Entity entity;

    public SimpleNoCollision(Entity entity) {
        this.entity = entity;
    }

    @Override
    public int getWidth() {
        return 0;
    }

    @Override
    public Entity getEntity() {
        return entity;
    }

    @Override
    public void update() {
    }
}
