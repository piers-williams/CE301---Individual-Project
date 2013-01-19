package Learning.Towers.Behaviours.Offensive;

import Learning.Towers.Entities.Entity;
import Learning.Towers.Main;
import Learning.Towers.Vector2D;

import java.util.ArrayList;

/**
 * Attacks the nearest enemy entity with a beam weapon
 */
public class SimpleWeapon implements Offensive {
    // Entity reference
    private Entity entity;

    // Location extracted from the entity
    private Vector2D currentLocation;

    private int targetRadius, damage, coolDown;

    public SimpleWeapon(Entity entity, int targetRadius, int damage, int coolDown) {
        this.entity = entity;
        this.currentLocation = new Vector2D(entity.getMovementBehaviour().getLocation());
        this.targetRadius = targetRadius;
        this.damage = damage;
        this.coolDown = coolDown;
    }

    @Override
    public void update() {
        currentLocation.clone(entity.getMovementBehaviour().getLocation());
        if (coolDown != 0) {
            ArrayList<Entity> entities = Main.GAME_LOOP.getEntities(currentLocation, targetRadius);
        }

    }

    @Override
    public Entity getEntity() {
        return entity;
    }
}
