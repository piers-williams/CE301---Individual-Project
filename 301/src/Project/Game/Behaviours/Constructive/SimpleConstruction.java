package Project.Game.Behaviours.Constructive;

import Project.Game.Entities.Entity;
import Project.Game.Faction;
import Project.Game.Vector2D;

/**
 *
 */
@Deprecated
public class SimpleConstruction implements Construction {
    private int maxResource = 30, resource = maxResource;
    private Faction faction;
    private Entity entity;

    public SimpleConstruction(Entity entity, Faction faction) {
        this.entity = entity;
        this.faction = faction;

        faction.addConstruction(this, getSpawnPoint());
    }

    @Override
    public void update() {
        resource--;
        if (resource == 0) {
            resource = maxResource;
            // Create new entity near this one
            faction.makeEntity(getSpawnPoint(), this);
        }
    }

    public Vector2D getSpawnPoint() {
        return Vector2D.add(entity.getMovementBehaviour().getLocation(), 100, 100);
    }

    @Override
    public Entity getEntity() {
        return entity;
    }
}
