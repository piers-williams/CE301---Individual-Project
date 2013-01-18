package Learning.Towers.Behaviours.Constructive;

import Learning.Towers.Entities.Entity;
import Learning.Towers.Faction;

/**
 *
 */
public class SimpleConstruction implements Construction {
    private int maxResource = 30, resource = maxResource;
    private Faction faction;
    private Entity entity;

    public SimpleConstruction(Entity entity, Faction faction) {
        this.entity = entity;
        this.faction = faction;
    }

    @Override
    public void update() {
        resource--;
        if (resource == 0) {
            resource = maxResource;
            // Create new entity near this one
            faction.makeEntity(entity.getX() + 25, entity.getY() + 25, this);
        }
    }

    @Override
    public Entity getEntity() {
        return entity;
    }
}
