package Learning.Towers.Entities.Meta;

import Learning.Towers.Behaviours.Movement.Movement;
import Learning.Towers.Entities.Entity;

import java.util.ArrayList;

/**
 * Group of entities
 * <p/>
 * A tactical group that will stay together and achieve tasks together as one
 * cohesive unit
 */
public class Group {

    private int x, y;
    private int radius;

    private ArrayList<Entity> entities;

    private Movement movementBehaviour;

    public Group() {
        entities = new ArrayList<>();
    }

    public Group(Entity entity) {
        this();
        entities.add(entity);
    }

    public Group(ArrayList<Entity> entities) {
        this();
        this.entities.addAll(entities);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public ArrayList<Entity> getEntities() {
        return entities;
    }

    public int getRadius() {
        return radius;
    }
}
