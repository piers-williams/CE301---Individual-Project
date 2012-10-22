package Learning.IntelligentAgents;

import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created with IntelliJ IDEA.
 * User: Piers
 * Date: 19/10/12
 * Time: 16:42
 * To change this template use File | Settings | File Templates.
 */
public class Group {
    private final static Random random = new Random();

    ArrayList<GroupedEntity> entities;

    int group;
    float r, g, b;

    // location
    double x, y;
    // waypoint
    double tX, tY;

    double speed = 1;

    public Group(int group, float r, float g, float b) {
        this.group = group;
        this.r = r;
        this.g = g;
        this.b = b;

        x = random.nextInt(Main.MAP_WIDTH);
        y = random.nextInt(Main.MAP_HEIGHT);
        setNewTarget();

        entities = new ArrayList<GroupedEntity>(10);
    }

    public void addEntity(GroupedEntity entity) {
        entities.add(entity);
    }

    public void update() {
        // check if at target or close enough
        if (Utilities.distance(x, y, tX, tY) < 5) {
            // if so make new target
            do {
                setNewTarget();
            } while (tX < 0 || tX > Main.MAP_WIDTH || tY < 0 || tY > Main.MAP_HEIGHT);
        }

        // in all cases
        if (x > tX) x -= speed;
        if (x < tX) x += speed;
        if (y > tY) y -= speed;
        if (y < tY) y += speed;

        speed = 2;
    }

    private void setNewTarget() {
        tX = random.nextInt(500);
        tY = random.nextInt(500);

        tX -= 250;
        tY -= 250;
        tX += x;
        tY += y;
    }

    public void draw() {

        int width = 6;
        GL11.glColor4f(r, g, b, 1.0f);
        GL11.glBegin(GL11.GL_QUADS);
        GL11.glVertex2d(x - width / 2, y - width / 2);
        GL11.glVertex2d(x + width / 2, y - width / 2);
        GL11.glVertex2d(x + width / 2, y + width / 2);
        GL11.glVertex2d(x - width / 2, y + width / 2);
        GL11.glEnd();

        width = 12;
        GL11.glColor4f(r, g, b, 1.0f);
        GL11.glBegin(GL11.GL_QUADS);
        GL11.glVertex2d(tX - width / 2,tY - width / 2);
        GL11.glVertex2d(tX + width / 2,tY - width / 2);
        GL11.glVertex2d(tX + width / 2,tY + width / 2);
        GL11.glVertex2d(tX - width / 2,tY + width / 2);
        GL11.glEnd();
    }
}
