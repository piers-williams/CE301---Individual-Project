package Learning.Towers.Behaviours.Drawing;

import Learning.Towers.Behaviours.Movement.Movement;
import Learning.Towers.Entities.Entity;
import Learning.Towers.Entities.Meta.Group;
import org.lwjgl.opengl.GL11;

/**
 * Draws 4 semi transparent square to indicate the radius/size of
 * a group mostly
 */
public class RadiusIndicator implements Drawing {

    private Movement movementBehaviour;
    private float r, g, b;
    private Entity entity;

    // Store variables used for drawing to avoid over allocations
    private int x, y, radius;
    private Group group;

    public RadiusIndicator(Entity entity, float r, float g, float b, Movement movementBehaviour, Group group) {
        this.entity = entity;
        this.r = r;
        this.g = g;
        this.b = b;

        this.movementBehaviour = movementBehaviour;
        this.group = group;
    }

    @Override
    public void draw() {
        drawSmallSquare((x - radius), (y + radius));
        drawSmallSquare((x + radius), (y + radius));
        drawSmallSquare((x - radius), (y - radius));
        drawSmallSquare((x + radius), (y - radius));
    }

    @Override
    public void update() {
        x = (int) movementBehaviour.getLocation().x;
        y = (int) movementBehaviour.getLocation().y;
        radius = (int) group.getRadius();
    }

    private void drawSmallSquare(int x, int y) {
        int width = 10;
        GL11.glColor4f(r, g, b, 0.2f);
        GL11.glBegin(GL11.GL_QUADS);
        GL11.glVertex2d(x - width / 2, y - width / 2);
        GL11.glVertex2d(x + width / 2, y - width / 2);
        GL11.glVertex2d(x + width / 2, y + width / 2);
        GL11.glVertex2d(x - width / 2, y + width / 2);
        GL11.glEnd();
    }

    @Override
    public Entity getEntity() {
        return entity;
    }
}
