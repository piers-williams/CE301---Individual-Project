package Project.Game.Behaviours.Drawing;

import Project.Game.Behaviours.Movement.Movement;
import Project.Game.Entities.Entity;
import Project.Game.Vector2D;
import org.lwjgl.opengl.GL11;

/**
 *
 */
public class SimpleQuad implements Drawing {

    private int width;
    private Movement movementBehaviour;
    private float r, g, b;
    private Entity entity;

    public SimpleQuad(Entity entity, int width, float r, float g, float b) {
        this.entity = entity;
        this.width = width;

        this.r = r;
        this.g = g;
        this.b = b;

        this.movementBehaviour = entity.getMovementBehaviour();
    }

    @Override
    public void draw() {
        Vector2D location = movementBehaviour.getLocation();
        double x = location.x, y = location.y;


        float health = entity.getHealth() / (float) entity.getMaxHealth();

        if (health > 1) health = 1;
        if (health < 0) health = 0;
        GL11.glColor4f(r, g, b, health);
        GL11.glBegin(GL11.GL_QUADS);
        GL11.glVertex2d(x - width / 2, y - width / 2);
        GL11.glVertex2d(x + width / 2, y - width / 2);
        GL11.glVertex2d(x + width / 2, y + width / 2);
        GL11.glVertex2d(x - width / 2, y + width / 2);
        GL11.glEnd();
    }

    @Override
    public void update() {
        // Nothing to do in this case
    }

    @Override
    public Entity getEntity() {
        return entity;
    }
}
