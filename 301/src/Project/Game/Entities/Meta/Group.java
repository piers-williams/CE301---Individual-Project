package Project.Game.Entities.Meta;

import Project.Game.Behaviours.Drawing.RadiusIndicator;
import Project.Game.Behaviours.Influence.SimpleInfluence;
import Project.Game.Behaviours.Movement.PathFollower;
import Project.Game.Behaviours.Movement.Static;
import Project.Game.Behaviours.Movement.Wandering;
import Project.Game.Entities.Entity;
import Project.Game.Faction;
import Project.Game.Vector2D;

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

    // Are we doing something or are we free to be used
    private boolean isAllocated = false;

    public Group(Faction faction, Vector2D location, int maxSize, boolean pulsing) {
        this.r = faction.getR();
        this.g = faction.getG();
        this.b = faction.getB();

        movementBehaviour = new Static(this, new Vector2D(location, true));
        entities = new ArrayList<>(maxSize);
        this.maxSize = maxSize;

        this.faction = faction;

        drawingBehaviour = new RadiusIndicator(this, this.r, this.g, this.b, movementBehaviour, this, pulsing);
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

    public int getMaxSize() {
        return maxSize;
    }

    public void setMaxSize(int maxSize) {
        this.maxSize = maxSize;
    }

    public boolean isAllocated() {
        return isAllocated;
    }

    public void setAllocated(boolean allocated) {
        isAllocated = allocated;
    }

    public double getRadius() {
        return radius;
    }

    public void switchToWander() {
        movementBehaviour = new Wandering(this, movementBehaviour.getLocation());
    }

    public void switchToFollow(Vector2D target) {
        isAllocated = true;
        movementBehaviour = new PathFollower(this, movementBehaviour.getLocation(), target, 5);
    }

    @Override
    public boolean isAlive() {
        return true;
    }
}
