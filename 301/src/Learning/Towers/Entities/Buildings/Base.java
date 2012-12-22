package Learning.Towers.Entities.Buildings;

import Learning.Towers.Entities.Entity;
import Learning.Towers.Faction;

/**
 * Base building
 * <p/>
 * This doesn't make much sense really
 */
public class Base extends Entity {

    static final int MAX_RESOURCE = 30;
    int resource = MAX_RESOURCE;
    Faction faction;

    public Base(Faction faction, int width) {
        super(width, faction.getR(), faction.getG(), faction.getB());
        this.faction = faction;
    }

    public void update() {
        resource--;
        if (resource == 0) {
            resource = MAX_RESOURCE;
            faction.makeEntity(x + 25, y + 25);
        }
    }
}
