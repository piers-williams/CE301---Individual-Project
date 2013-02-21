package Project.Game.Behaviours.Offensive;

import Project.Game.Entities.Entity;
import Project.Game.Main;
import Project.Game.Vector2D;

import java.util.ArrayList;

/**
 * Attacks the nearest enemy entity with a beam weapon
 */
public class SimpleWeapon implements Offensive {
    // Entity reference
    private Entity entity;

    // Location extracted from the entity
    private Vector2D currentLocation;

    private int targetRadius, damage, coolDown, maxCoolDown;

    public SimpleWeapon(Entity entity, int targetRadius, int damage, int coolDown) {
        this.entity = entity;
        this.currentLocation = new Vector2D(entity.getMovementBehaviour().getLocation());
        this.targetRadius = targetRadius;
        this.damage = damage;
        //this.coolDown = coolDown;
        this.maxCoolDown = coolDown;
    }

    @Override
    public void update() {
        currentLocation.clone(entity.getMovementBehaviour().getLocation());
        if (coolDown == 0) {
            ArrayList<Entity> entities = Main.GAME_LOOP.getEntities(currentLocation, targetRadius, entity.getFaction());
            if (entities.size() > 0) {

                Entity closestEntitySoFar = entities.get(0);
                double lowestDistanceSoFar = entities.get(0).getMovementBehaviour().getLocation().dist(entity.getMovementBehaviour().getLocation());
                for (int i = 1; i < entities.size(); i++) {
                    double thisDistance = entities.get(i).getMovementBehaviour().getLocation().dist(entity.getMovementBehaviour().getLocation());
                    if (thisDistance < lowestDistanceSoFar) {
                        lowestDistanceSoFar = thisDistance;
                        closestEntitySoFar = entities.get(i);
                    }
                }

                // Take shot
                closestEntitySoFar.damage(damage);
                coolDown = maxCoolDown;
            }
        } else {
            coolDown--;
        }
    }

    @Override
    public Entity getEntity() {
        return entity;
    }
}
