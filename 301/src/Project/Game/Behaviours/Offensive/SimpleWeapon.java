package Project.Game.Behaviours.Offensive;

import Project.Game.Entities.Entity;
import Project.Game.Main;
import Project.Game.Vector2D;
import org.lwjgl.opengl.GL11;

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
    // Used for drawing
    private Entity targetEntity;

    public SimpleWeapon(Entity entity, int targetRadius, int damage, int coolDown) {
        this.entity = entity;
        this.currentLocation = new Vector2D(entity.getMovementBehaviour().getLocation(), true);
        this.targetRadius = targetRadius;
        this.damage = damage;
        //this.coolDown = coolDown;
        this.maxCoolDown = coolDown;
    }

    @Override
    public void update() {
        currentLocation.clone(entity.getMovementBehaviour().getLocation());
        if (coolDown == 0) {
            targetEntity = null;
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
                targetEntity = closestEntitySoFar;
                coolDown = maxCoolDown;
            }
        } else {
            coolDown--;
        }
    }

    @Override
    public void draw() {
        if (targetEntity != null) {

            GL11.glColor4f(1f, 1f, 0f, 1f);
            GL11.glBegin(GL11.GL_LINE_STRIP);
            // Our point
            GL11.glVertex2d(entity.getX(), entity.getY());
            // Target point
            GL11.glVertex2d(targetEntity.getX(), targetEntity.getY());
            GL11.glEnd();
        }

    }

    @Override
    public Entity getEntity() {
        return entity;
    }
}
