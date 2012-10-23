package Learning.ShootingEachOtherAgents;

import org.lwjgl.opengl.GL11;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: Piers
 * Date: 22/10/12
 * Time: 17:04
 * To change this template use File | Settings | File Templates.
 */
public class ShootingEntity extends GroupedEntity {

    public static ArrayList<Entity> SQUARES;

    private Projectile projectile;

    private static double MAX_RANGE = 50;

    public ShootingEntity(Group group, int width) {
        super(group, width);
    }

    public ShootingEntity(Group group, int width, double x, double y) {
        super(group, width);
        this.x = x;
        this.y = y;
    }

    public void update() {
        super.update();

        if (projectile != null) {
            projectile.update();
        }
        if (shooting) {
            if (projectile == null || !projectile.alive) {
//                System.out.println("Shooting");
                Entity target = null;
                double closestDistance = Double.MAX_VALUE;
                for (Entity other : SQUARES) {
                    if (other.alive) {
                        if (!(other instanceof ShootingEntity)) {

                            double distance = Utilities.distance(x, y, other.x, other.y);
                            if (distance < closestDistance) {
                                target = other;
                                closestDistance = distance;
                            }
                        } else if (((ShootingEntity) other).group != this.group) {
                            double distance = Utilities.distance(x, y, other.x, other.y);
                            if (distance < closestDistance) {
                                target = other;
                                closestDistance = distance;
                            }
                        }
                    }
                }

                if (target != null && closestDistance < ShootingEntity.MAX_RANGE) {
                    projectile = new Projectile(this, target, true);
                }
            }
        }
    }

    public void draw() {
        if (alive) {
            GL11.glColor4f(r, g, b, 0.5f);
            GL11.glBegin(GL11.GL_QUADS);
            GL11.glVertex2d(x - width / 2, y - width / 2);
            GL11.glVertex2d(x + width / 2, y - width / 2);
            GL11.glVertex2d(x + width / 2, y + width / 2);
            GL11.glVertex2d(x - width / 2, y + width / 2);
            GL11.glEnd();

            GL11.glColor4f(r, g, b, 1.0f);
            GL11.glBegin(GL11.GL_QUADS);
            GL11.glVertex2d(x - width / 4, y - width / 4);
            GL11.glVertex2d(x + width / 4, y - width / 4);
            GL11.glVertex2d(x + width / 4, y + width / 4);
            GL11.glVertex2d(x - width / 4, y + width / 4);
            GL11.glEnd();


            if (projectile != null) {
                projectile.draw();
            }
        }
    }
}
