package Learning.Towers.Behaviours.Drawing;

import Learning.Towers.Behaviours.Movement.Movement;
import Learning.Towers.Entities.Entity;
import Learning.Towers.Vector2D;
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

        GL11.glColor4f(r, g, b, 1f);
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
