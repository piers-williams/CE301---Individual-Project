package Learning.Towers.Behaviours.Movement;

import Learning.Towers.Entities.Entity;
import Learning.Towers.Main;
import Learning.Towers.Vector2D;

import java.util.Random;

/**
 * Randomly wanders the battlefield in straight lines
 */
public class Wandering extends BasicMovement {
    private static Random random = new Random();
    // Target location
    private Vector2D targetLocation;
    // Max distance from location to pick a new one
    private int newLocationThreshold;
    // Threshold for deciding if we are near enough the target
    private int distanceThreshold;

    public Wandering(Entity entity, Vector2D location, int newLocationThreshold, int distanceThreshold) {
        super(entity, location);
        this.newLocationThreshold = newLocationThreshold;
        this.distanceThreshold = distanceThreshold;
        setNewTarget();
    }

    public Wandering(Entity entity, Vector2D location) {
        this(entity, location, 500, 5);
    }

    @Override
    public void updateSpecialisation() {
        if (location.dist(targetLocation) < distanceThreshold) {
            setNewTarget();
        }
        location.add(location.getNormalDirectionBetween(targetLocation));
    }

    // Pick a new target somewhere close
    private void setNewTarget() {
        int tX = random.nextInt(newLocationThreshold), tY = random.nextInt(newLocationThreshold);
        tX -= newLocationThreshold / 2;
        tY -= newLocationThreshold / 2;
        targetLocation = new Vector2D(location.x + tX, location.y + tY);
        targetLocation.wrap(Main.MAP_WIDTH, Main.MAP_HEIGHT);
    }
}
