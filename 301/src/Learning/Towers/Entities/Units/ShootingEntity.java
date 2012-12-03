package Learning.Towers.Entities.Units;

import Learning.Towers.Entities.Entity;
import Learning.Towers.Entities.GroupedEntity;
import Learning.Towers.Faction;
import Learning.Towers.Projectile;
import Learning.Towers.Utilities;
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

    private static double MAX_RANGE = 100;

    public static Boolean shooting = false;

    public ShootingEntity(Faction faction, int width) {
        super(faction, width);
    }

    public ShootingEntity(Faction faction, int width, double x, double y) {
        super(faction, width, 3);
        this.x = x;
        this.y = y;
    }

    public void update() {
        super.update();
        if (alive) {
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
                            } else if (((ShootingEntity) other).faction != this.faction) {
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
    }

    public void draw() {
        if (alive) {
            GL11.glColor4f(r, g, b, 1f);
            GL11.glBegin(GL11.GL_QUADS);
            GL11.glVertex2d(x - width / 2, y - width / 2);
            GL11.glVertex2d(x + width / 2, y - width / 2);
            GL11.glVertex2d(x + width / 2, y + width / 2);
            GL11.glVertex2d(x - width / 2, y + width / 2);
            GL11.glEnd();


            if (projectile != null) {
                projectile.draw();
            }
        }
    }
}
