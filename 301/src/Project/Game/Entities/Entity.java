package Project.Game.Entities;

import Project.Game.Behaviours.Collision.Collision;
import Project.Game.Behaviours.Constructive.Construction;
import Project.Game.Behaviours.Drawing.Drawing;
import Project.Game.Behaviours.Influence.Influence;
import Project.Game.Behaviours.Movement.Movement;
import Project.Game.Behaviours.Offensive.Offensive;
import Project.Game.Behaviours.Resource.Resource;
import Project.Game.Faction;
import Project.Game.Influence.InfluenceGrid;
import Project.Game.Main;
import Project.Game.Vector2D;
import de.matthiasmann.twl.Button;
import de.matthiasmann.twl.Widget;

/**
 * Entity class
 */
public class Entity {

    protected float r, g, b;

    protected boolean alive = true;

    protected Faction faction;

    // Hopefully this section will replace most of the other code
    protected Movement movementBehaviour;
    protected Collision collisionBehaviour;
    protected Drawing drawingBehaviour;
    protected Influence influenceBehaviour;
    protected Construction constructionBehaviour;
    protected Offensive offensiveBehaviour;
    protected Resource resourceBehaviour;

    protected int maxHealth;
    protected int health;

    protected String name;

    // Label for the entity
    protected Button label;

    // Used for factories
    protected Entity() {

    }

    public void update() {
        if (movementBehaviour != null) movementBehaviour.update();
        if (collisionBehaviour != null) collisionBehaviour.update();
        if (constructionBehaviour != null) constructionBehaviour.update();
        if (drawingBehaviour != null) drawingBehaviour.update();
        if (influenceBehaviour != null) influenceBehaviour.update();
        if (offensiveBehaviour != null) offensiveBehaviour.update();
        if (resourceBehaviour != null) resourceBehaviour.update();
    }

    public void draw() {
        if (alive) {
            if (drawingBehaviour != null) drawingBehaviour.draw();
        }
    }

    public void kill() {
        alive = false;
    }

    public boolean isAlive() {
        return alive;
    }

    public void damage(int damage) {
        health -= damage;
        alive = (health > 0);
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

    public Vector2D getLocation() {
        if (movementBehaviour != null) return movementBehaviour.getLocation();
        return null;
    }

    public Construction getConstructionBehaviour() {
        return constructionBehaviour;
    }

    public Faction getFaction() {
        return faction;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void layout(Widget widget, boolean visible) {
        if (label != null) {
            if (label.getParent() != widget) {
                System.out.println("Adding widget Label");
                widget.add(label);
            }
            if (visible) {
                // adjust this to account for view location
                Vector2D labelLocation = Vector2D.subtract(getLocation(), -Main.viewLocation.x, (Main.viewLocation.y - Main.SCREEN_HEIGHT));
//                Vector2D labelLocation = Vector2D.subtract( Main.viewLocation, getLocation());
                label.setPosition((int) labelLocation.x, (int) labelLocation.y);
                label.setText(name);
                label.adjustSize();
            }
            label.setVisible(visible);
        }
    }

    public int getHealth() {
        return health;
    }

    public int getMaxHealth() {
        return maxHealth;
    }
}
