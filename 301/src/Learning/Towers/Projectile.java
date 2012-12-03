package Learning.Towers;

import Learning.Towers.Entities.Entity;
import Learning.Towers.Entities.Units.ShootingEntity;
import org.lwjgl.opengl.GL11;

/**
 * Created with IntelliJ IDEA.
 * User: Piers
 * Date: 22/10/12
 * Time: 17:00
 * To change this template use File | Settings | File Templates.
 */
public class Projectile {

    ShootingEntity source;
    Entity target;

    int lifeInTicks = 15;

    boolean alive = true;
    boolean converter;

    public Projectile(ShootingEntity source, Entity target) {
        this.source = source;
        this.target = target;
        converter = false;
    }

    public Projectile(ShootingEntity source, Entity target, boolean converter) {
        this.source = source;
        this.target = target;
        this.converter = converter;
    }

    public void update() {
        lifeInTicks--;
        if (lifeInTicks <= 0) {
            alive = false;
            target.kill();
        }
    }

    public void draw() {
        if (alive) {
            int width = 2;
            GL11.glColor4f(1, 0.2f, 0, 1.0f);
            GL11.glBegin(GL11.GL_QUADS);
            GL11.glVertex2d(source.x - width / 2, source.y - width / 2);
            GL11.glVertex2d(source.x + width / 2, source.y - width / 2);
            GL11.glVertex2d(target.x + width / 2, target.y + width / 2);
            GL11.glVertex2d(target.x - width / 2, target.y + width / 2);
            GL11.glEnd();
        }
    }
}
