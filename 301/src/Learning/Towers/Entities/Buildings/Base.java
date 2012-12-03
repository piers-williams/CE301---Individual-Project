package Learning.Towers.Entities.Buildings;

import Learning.Towers.Faction;
import Learning.Towers.Entities.GroupedEntity;

/**
 * Base building
 */
public class Base extends GroupedEntity {

    static final int MAX_RESOURCE = 30;
    int resource = MAX_RESOURCE;


    public Base(Faction faction, int width) {
        super(faction, width);
    }

    public void update() {
        resource--;
        if (resource == 0) {
            resource = MAX_RESOURCE;
            faction.makeEntity(x + 25, y + 25);
        }
    }
}
