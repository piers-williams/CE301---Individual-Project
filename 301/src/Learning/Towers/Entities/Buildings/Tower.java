package Learning.Towers.Entities.Buildings;

import Learning.Towers.Entities.Units.ShootingEntity;
import Learning.Towers.Faction;

/**
 * Tower building
 */
public class Tower extends ShootingEntity {

    /**
     * Only want to allow manual placement
     * @param faction Faction we belong to
     * @param width  Width of the tower
     * @param x  X co-ordinate of the tower
     * @param y Y co-ordinate of the tower
     */
    public Tower(Faction faction, int width, int x, int y) {
        super(faction, width, x, y);
    }

    @Override
    public void update() {
        super.update();
    }

    @Override
    public void draw() {
        super.draw();
    }
}
