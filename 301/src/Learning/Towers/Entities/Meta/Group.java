package Learning.Towers.Entities.Meta;

import Learning.Towers.Behaviours.Drawing.RadiusIndicator;
import Learning.Towers.Behaviours.Influence.SimpleInfluence;
import Learning.Towers.Behaviours.Movement.PathFollower;
import Learning.Towers.Behaviours.Movement.Static;
import Learning.Towers.Behaviours.Movement.Wandering;
import Learning.Towers.Entities.Entity;
import Learning.Towers.Faction;
import Learning.Towers.Vector2D;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Group of entities
 * <p/>
 * A tactical group that will stay together and achieve tasks together as one
 * cohesive unit
 */
public class Group extends Entity {

    private double radius;

    private ArrayList<Entity> entities;
    private int maxSize;

    public Group(float r, float g, float b, Vector2D location, int maxSize, Faction faction) {
        this.r = r;
        this.g = g;
        this.b = b;

        movementBehaviour = new Static(this, location);
        entities = new ArrayList<>(maxSize);
        this.maxSize = maxSize;

        this.faction = faction;

        drawingBehaviour = new RadiusIndicator(this, r, g, b, movementBehaviour, this);
        influenceBehaviour = new SimpleInfluence(this, 5, 0);
    }

    @Override
    public void update() {
        radius = Math.sqrt(Math.pow(entities.size(), 1.5) * 16);

        super.update();

        Iterator<Entity> itr = entities.iterator();
        while (itr.hasNext()) {
            Entity entity = itr.next();
            if (!entity.isAlive()) itr.remove();
        }
    }

    public ArrayList<Entity> getEntities() {
        return entities;
    }

    public void addEntity(Entity entity) {
        entities.add(entity);
    }

    public Boolean isFull() {
        return entities.size() >= maxSize;
    }

    public double getRadius() {
        return radius;
    }

    public void switchToWander() {
        movementBehaviour = new Wandering(this, movementBehaviour.getLocation());
    }
    public void switchToFollow(Vector2D target){
        movementBehaviour = new PathFollower(this, movementBehaviour.getLocation(), target, 5 );
    }
}
