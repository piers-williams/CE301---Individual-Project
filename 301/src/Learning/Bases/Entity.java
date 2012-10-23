package Learning.Bases;

import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.Random;

/**
 * Created with IntelliJ IDEA.
 * User: Piers
 * Date: 18/10/12
 * Time: 22:05
 * To change this template use File | Settings | File Templates.
 */
public class Entity {
//    public static CollisionBoard BOARD;
  // Location
    double x, y;
    int width;
    double dX, dY;

    Point oldCell;

    float r, g, b;

    boolean alive = true;

    private static Random random = new Random();

    public Entity(int width) {
        this(width, random.nextFloat(), random.nextFloat(), random.nextFloat());
    }

    public Entity(int width, float r, float g, float b) {
        x = random.nextInt(Main.MAP_WIDTH);
        y = random.nextInt(Main.MAP_HEIGHT);

        oldCell = Main.COLLISION_BOARD.getPoint(this);
        Main.COLLISION_BOARD.addEntity(this);

        dX = (random.nextDouble() * 2) - 1;
        dY = (random.nextDouble() * 2) - 1;
        this.r = r;
        this.g = g;
        this.b = b;

        this.width = width;
    }

    public void update() {
        move();

        if (x < 0 || x > Main.MAP_WIDTH) dX *= -1;
        if (y < 0 || y > Main.MAP_HEIGHT) dY *= -1;
    }

    public void move() {
        x += dX;
        y += dY;

        Point cell = Main.COLLISION_BOARD.getPoint(this);

        if (!oldCell.equals(cell)) {
            Main.COLLISION_BOARD.moveEntity(this, (Point) oldCell.clone());
            oldCell = cell;
        }
    }

    public void draw() {
        if (alive) {
            GL11.glColor4f(r, g, b, 1.0f);
            GL11.glBegin(GL11.GL_QUADS);
            GL11.glVertex2d(x - width / 2, y - width / 2);
            GL11.glVertex2d(x + width / 2, y - width / 2);
            GL11.glVertex2d(x + width / 2, y + width / 2);
            GL11.glVertex2d(x - width / 2, y + width / 2);
            GL11.glEnd();
        }
    }

    protected void moveToPoint(double tX, double tY) {
        int radius = 50;

        if (x - radius > tX) x -= 1;
        if (x + radius < tX) x += 1;
        if (y - radius > tY) y -= 1;
        if (y + radius < tY) y += 1;
    }

    public void kill() {
        alive = false;
    }

    public static boolean collidesWith(Entity first, Entity second) {
        if (first.equals(second)) return false;

        double distance = (first.x - second.x) * (first.x - second.x) + (first.y - second.y) * (first.y - second.y);
        return (distance < first.width * first.width);
    }

    public static void bounce(Entity first, Entity second) {
        double dX, dY;
        dX = first.dX;
        dY = first.dY;
        first.dX = second.dX;
        first.dY = second.dY;
        second.dX = dX;
        second.dY = dY;

        double distance = (first.x - second.x) * (first.x - second.x) + (first.y - second.y) * (first.y - second.y);
        double ang = Math.atan2(second.y - first.y, second.x - first.x);
        double mov = first.width - Math.sqrt(distance);
        mov /= 2;

        first.x -= Math.cos(ang) * mov;
        first.y -= Math.sin(ang) * mov;
        ang -= Math.PI;
        second.x -= Math.cos(ang) * mov;
        second.y -= Math.sin(ang) * mov;

    }

}
