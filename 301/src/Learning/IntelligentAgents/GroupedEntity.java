package Learning.IntelligentAgents;

import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.awt.geom.Point2D;

/**
 * Created with IntelliJ IDEA.
 * User: Piers
 * Date: 19/10/12
 * Time: 16:37
 * To change this template use File | Settings | File Templates.
 */
public class GroupedEntity extends Entity {

    Group group;

    double maxSpeed = 1;

    public GroupedEntity(Group group, int width) {
        super(width, group.r, group.g, group.b);
        this.group = group;
    }

    public void update() {
        Point2D.Double cohesion = calculateCohesion();
        Point2D.Double separation = calculateSeparation();

        if (Utilities.distance(group.x, group.y, this.x, this.y) > 150) {
            dX = cohesion.x;
            dY = cohesion.y;
        } else {
            dX = separation.x;
            dY = separation.y;
        }

//        dX = (separation.x != 0) ? separation.x + cohesion.x / 2 : cohesion.x;
//        dY = (separation.y != 0) ? separation.y + cohesion.x / 2 : cohesion.y;

        super.update();
    }

    private Point2D.Double calculateCohesion() {
        double cX = 0, cY = 0;

        double distance = Utilities.distance(x, y, group.tX, group.tY);
        cX = (x > group.tX) ? -1 : 1;
        cY = (y > group.tY) ? -1 : 1;

        return new Point2D.Double(cX, cY);
    }

    // This code is wrong
    private Point2D.Double calculateSeparation() {
        double sX = 0, sY = 0;
        for (GroupedEntity entity : group.entities) {
            if (entity != this) {
                double distance = Utilities.distance(entity.x, entity.y, this.x, this.y);
                //System.out.println("Distance is: " + distance);
                if (distance < Main.SQUARE_WIDTH * 3) {
                    sX += ((this.x - entity.x > 0) ? 1 : -1);
                    sY +=  ((this.y - entity.y > 0) ? 1 : -1);
//                    sX += (1 - sX) * (1 / (this.x - entity.x));
//                    sY += (1 - sY) * (1 / (this.y - entity.y));
//                    sX += (1 - sX) * ((this.x - entity.x));
//                    sY += (1 - sY) * ( (this.y - entity.y));
                }
            }
        }

//        sX /= group.entities.size();
//        sY /= group.entities.size();
        System.out.println("sX: " + sX + " sY: " + sY);
        sX = (sX > 1) ? 1 : sX;
        sX = (sX < -1) ? -1 : sX;
        sY = (sY > 1) ? 1 : sY;
        sY = (sY < -1) ? -1 : sY;

        return new Point2D.Double(sX, sY);
    }

    private Point calculateAlignment() {
        return new Point(0, 0);
    }
}
