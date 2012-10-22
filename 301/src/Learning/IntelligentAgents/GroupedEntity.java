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

    public static Boolean shooting = false;
    double maxSpeed = 1;

    public GroupedEntity(Group group, int width) {
        super(width, group.r, group.g, group.b);
        this.group = group;
    }

    public void update() {
        Point2D.Double cohesion = calculateCohesion();
        Point2D.Double separation = calculateSeparation();

        if (Utilities.distance(group.x, group.y, this.x, this.y) > group.entities.size() * 1.5) {
            dX = cohesion.x + separation.x;
            dY = cohesion.y + separation.y;
        } else {
            dX = separation.x;
            dY = separation.y;
        }

        if(shooting){

        }

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
                }
            }
        }

//        System.out.println("sX: " + sX + " sY: " + sY);
//        sX = (sX > 1) ? 1 : sX;
//        sX = (sX < -1) ? -1 : sX;
//        sY = (sY > 1) ? 1 : sY;
//        sY = (sY < -1) ? -1 : sY;

        return new Point2D.Double(sX, sY);
    }

    private Point calculateAlignment() {
        return new Point(0, 0);
    }
}
