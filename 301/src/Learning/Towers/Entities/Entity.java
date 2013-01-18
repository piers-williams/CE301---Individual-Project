package Learning.Towers.Entities;

import Learning.Towers.Behaviours.Collision.Collision;
import Learning.Towers.Behaviours.Constructive.Construction;
import Learning.Towers.Behaviours.Drawing.Drawing;
import Learning.Towers.Behaviours.Drawing.SimpleQuad;
import Learning.Towers.Behaviours.Influence.Influence;
import Learning.Towers.Behaviours.Influence.SimpleInfluence;
import Learning.Towers.Behaviours.Movement.Movement;
import Learning.Towers.Behaviours.Movement.Static;
import Learning.Towers.Behaviours.Movement.Wandering;
import Learning.Towers.Behaviours.Offensive.Offensive;
import Learning.Towers.Faction;
import Learning.Towers.Influence.InfluenceGrid;
import Learning.Towers.Main;
import Learning.Towers.Vector2D;

import java.util.Random;

/**
 * Entity class
 */
public class Entity {
    @Deprecated
    protected int width;

    protected float r, g, b;

    protected boolean alive = true;

    private static Random random = new Random();

    protected Faction faction;

    // Hopefully this section will replace most of the other code
    protected Movement movementBehaviour;
    protected Collision collisionBehaviour;
    protected Drawing drawingBehaviour;
    protected Influence influenceBehaviour;
    protected Construction constructionBehaviour;
    protected Offensive offensiveBehaviour;

    // Used for factories
    protected Entity(){

    }

    public void update() {
        movementBehaviour.update();
        if(collisionBehaviour != null) collisionBehaviour.update();
        if(constructionBehaviour != null) constructionBehaviour.update();
        if(drawingBehaviour != null) drawingBehaviour.update();
        if(influenceBehaviour != null) influenceBehaviour.update();
        if(offensiveBehaviour != null) offensiveBehaviour.update();
    }

    public void draw() {
        if (alive) {
            drawingBehaviour.draw();
        }
    }

    public void kill() {
        alive = false;
    }

    public boolean isAlive() {
        return alive;
    }

    public double getX() {
        return movementBehaviour.getLocation().getX();
    }

    public double getY() {
        return movementBehaviour.getLocation().getY();
    }

    public InfluenceGrid getInfluenceGrid() {
        return influenceBehaviour.getInfluenceGrid();
    }

    public Collision getCollisionBehaviour() {
        return collisionBehaviour;
    }

    public Movement getMovementBehaviour() {
        return movementBehaviour;
    }

    public Construction getConstructionBehaviour() {
        return constructionBehaviour;
    }

    public Faction getFaction() {
        return faction;
    }
}
