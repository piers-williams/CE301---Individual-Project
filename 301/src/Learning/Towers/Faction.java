package Learning.Towers;

import Learning.Towers.Entities.Buildings.Base;
import Learning.Towers.Entities.GroupedEntity;
import Learning.Towers.Entities.Units.ShootingEntity;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

/**
 * Created with IntelliJ IDEA.
 * User: Piers
 * Date: 19/10/12
 * Time: 16:42
 * To change this template use File | Settings | File Templates.
 */
public class Faction {
    private final static Random random = new Random();

    ArrayList<GroupedEntity> entities;

    int group;
    float r, g, b;

    // location
    double x, y;
    // waypoint
    double tX, tY;

    double speed = 1;

    double radius;

    Vector2D startLocation;

    @Deprecated
    public Faction(int group, float r, float g, float b) {
        this.group = group;
        this.r = r;
        this.g = g;
        this.b = b;

        x = random.nextInt(Main.MAP_WIDTH);
        y = random.nextInt(Main.MAP_HEIGHT);
        setNewTarget();

        entities = new ArrayList<GroupedEntity>(10);
    }

    public Faction(int group, float r, float g, float b, Vector2D startLocation) {
        this(group, r, g, b);
        this.startLocation = startLocation;
        Base base = new Base(this, 26);
        Main.GAME_LOOP.addEntity(base);
    }

    public void addEntity(GroupedEntity entity) {
        entities.add(entity);
    }

    public void makeEntity() {
        makeEntity(random.nextInt(Main.MAP_WIDTH), random.nextInt(Main.MAP_HEIGHT));
    }

    public void makeEntity(double x, double y) {
        GroupedEntity entity = new ShootingEntity(this, Main.SQUARE_WIDTH, x, y);
        entities.add(entity);
        Main.GAME_LOOP.addEntity(entity);
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

        speed = 1;
        radius = Math.sqrt(Math.pow(entities.size(), 1.5) * 16);

        Iterator<GroupedEntity> itr = entities.iterator();
        while (itr.hasNext()) {
            GroupedEntity entity = itr.next();
            if (!entity.isAlive()) itr.remove();
        }

    }

    private void setNewTarget() {
        tX = random.nextInt(500);
        tY = random.nextInt(500);

        tX -= 250;
        tY -= 250;
        tX += x;
        tY += y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public float getR() {
        return r;
    }

    public float getG() {
        return g;
    }

    public float getB() {
        return b;
    }

    public double getRadius() {
        return radius;
    }

    public ArrayList<GroupedEntity> getEntities() {
        return entities;
    }

    public void draw() {
        drawSmallSquare((int) (x - radius), (int) (y + radius));
        drawSmallSquare((int) (x + radius), (int) (y + radius));
        drawSmallSquare((int) (x - radius), (int) (y - radius));
        drawSmallSquare((int) (x + radius), (int) (y - radius));
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
}
