package Learning.Towers.Entities.Buildings;

import Learning.Towers.Entities.GroupedEntity;
import Learning.Towers.Faction;

/**
 *
 */
public class Tower extends GroupedEntity {

    public Tower(Faction faction, int width) {
        super(faction, width);
    }

    public Tower(Faction faction, int width, double strength) {
        super(faction, width, strength);
    }
}
