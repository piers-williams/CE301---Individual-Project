package Learning.Bases;

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

    Faction faction;

    public static Boolean shooting = false;
    double maxSpeed = 1;


    public GroupedEntity(Faction faction, int width) {
        super(width, faction.r, faction.g, faction.b);
        this.faction = faction;
    }

    public void update() {
        Point2D.Double cohesion = calculateCohesion();
        Point2D.Double separation = calculateSeparation();

        if (Utilities.distance(faction.x, faction.y, this.x, this.y) > faction.radius) {
            dX = cohesion.x + separation.x / 2;
            dY = cohesion.y + separation.y / 2;

            dX *= 1.4;
            dY *= 1.4;
        } else {
            dX = separation.x;
            dY = separation.y;
        }

        if (shooting) {

        }

        super.update();
    }

    private Point2D.Double calculateCohesion() {
        double cX = 0, cY = 0;

//        double distance = Utilities.distance(x, y, faction.x, faction.y);
        cX = (x > faction.x) ? -1 : 1;
        cY = (y > faction.y) ? -1 : 1;

        return new Point2D.Double(cX, cY);
    }

    // This code is wrong
    private Point2D.Double calculateSeparation() {
        double sX = 0, sY = 0;
        for (GroupedEntity entity : faction.entities) {
            if (entity != this) {
                double distance = Utilities.distance(entity.x, entity.y, this.x, this.y);
//                System.out.println("Distance is: " + distance);
                if (distance < Main.SQUARE_WIDTH * 3) {
                    sX += ((this.x - entity.x > 0) ? 1 : -1);
                    sY += ((this.y - entity.y > 0) ? 1 : -1);
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
