package Project.Game.Behaviours.Constructive;

import Project.Game.Entities.Entity;
import Project.Game.Faction;
import Project.Game.Resource.ResourcePool;
import Project.Game.Vector2D;

/**
 *  Builds units - better than SimpleConstruction
 */
public class UnitConstruction extends BasicConstruction {

    public UnitConstruction(Faction faction, Entity entity, ResourcePool resourcePool) {
        super(faction, entity, resourcePool);
    }

    @Override
    public Vector2D getSpawnPoint() {
        return null;
    }

    @Override
    public void update() {
        super.update();
    }
}
