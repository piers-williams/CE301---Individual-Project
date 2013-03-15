package Project.Game.Behaviours.Drawing;

import Project.Game.Behaviours.Movement.Movement;
import Project.Game.Entities.Entity;
import Project.Game.Entities.Meta.Group;
import org.lwjgl.opengl.GL11;

/**
 * Draws 4 semi transparent square to indicate the radius/size of
 * a group mostly
 */
public class RadiusIndicator implements Drawing {

    private Movement movementBehaviour;
    private float r, g, b, alpha, delta;
    private Entity entity;

    // Store variables used for drawing to avoid over allocations
    private int x, y, radius;
    private Group group;

    private boolean pulsing;

    public RadiusIndicator(Entity entity, float r, float g, float b, Movement movementBehaviour, Group group, boolean pulsing) {
        this.entity = entity;
        this.r = r;
        this.g = g;
        this.b = b;

        this.movementBehaviour = movementBehaviour;
        this.group = group;
        this.pulsing = pulsing;
    }

    public RadiusIndicator(Entity entity, float r, float g, float b, Movement movementBehaviour, Group group) {
        this(entity, r, g, b, movementBehaviour, group, false);
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

        if (pulsing) {
            if (alpha > 0.9f) delta = -0.05f;
            if (alpha <= 0.05f) delta = 0.05f;
            alpha += delta;
        }
    }

    private void drawSmallSquare(int x, int y) {
        int width = 10;
        if (!pulsing) {
            GL11.glColor4f(r, g, b, 0.2f);
        } else {
            GL11.glColor4f(r, g, b, alpha);
        }
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
