package Project.Game.Behaviours.Movement;

import Project.Game.Entities.Entity;
import Project.Game.Main;
import Project.Game.Vector2D;

import java.util.ArrayList;

/**
 *
 */
public class RadiusPatrol extends BasicMovement {
    private Vector2D centerLocation;
    private int radius;
    private int checkPointIndex;
    private ArrayList<Vector2D> checkPoints;

    private static final int CHECKPOINT_THRESHOLD = 15;
    private final static int NUMBER_OF_CHECKPOINTS = 16;

    public RadiusPatrol(Entity entity, Vector2D location, Vector2D centerLocation, int radius) {
        super(entity, location);
        this.centerLocation = centerLocation;
        this.radius = radius;

        setUpCheckPoints();
        checkPointIndex = 0;
    }

    private void setUpCheckPoints() {
        checkPoints = new ArrayList<>(NUMBER_OF_CHECKPOINTS);
        Vector2D temp = new Vector2D(radius, 0, true);
        // Possibly make this a float or just pick numbers that work in ints nicely
        for (int i = 0; i < NUMBER_OF_CHECKPOINTS; i++) {
            temp.rotate(Math.toRadians(360 / NUMBER_OF_CHECKPOINTS));
            checkPoints.add(Main.VECTOR2D_SOURCE.getVector(centerLocation.x + temp.x, centerLocation.y + temp.y));
        }

//        System.out.println("Calculated Checkpoints: ");
//        for (Vector2D checkPoint : checkPoints) {
//            System.out.println("    " + checkPoint);
//        }
    }

    @Override
    public void updateSpecialisation() {
        if (location.dist(checkPoints.get(checkPointIndex)) < CHECKPOINT_THRESHOLD) {
//            System.out.println("Checkpoint reached");
            checkPointIndex++;
            if (checkPointIndex >= checkPoints.size()) checkPointIndex = 0;
        }
        // Calculate move to next point
        direction = location.getNormalDirectionBetween(checkPoints.get(checkPointIndex));
        // Move to next point
        location.add(direction);
    }
}
