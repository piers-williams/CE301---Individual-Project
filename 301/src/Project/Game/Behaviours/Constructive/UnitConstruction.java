package Project.Game.Behaviours.Constructive;

import Project.Game.Entities.Entity;
import Project.Game.Entities.Meta.Group;
import Project.Game.Faction;
import Project.Game.Resource.ResourcePool;
import Project.Game.Vector2D;
import com.sun.istack.internal.Nullable;

/**
 * Builds units - better than SimpleConstruction
 */
public class UnitConstruction extends BasicConstruction {

    // Current state for the object
    private UnitState state;

    @Nullable
    private BuildOrder buildOrder = null;

    public UnitConstruction(Faction faction, Entity entity, ResourcePool resourcePool) {
        super(faction, entity, resourcePool);

        state = UnitState.Waiting;
    }

    @Override
    public Vector2D getSpawnPoint() {
        return null;
    }

    @Override
    public void update() {

        switch (state) {
            case Waiting:
                // check faction for new order to complete
                break;
            case Building:
                // Complete next stage of construction for the current order
                break;
            case AllBuilt:
                // Clear and reset the variables ready for the next order

                buildOrder = null;
                // switch state to waiting
                state = UnitState.Waiting;
                break;
        }
    }
}

enum UnitState {
    Waiting, Building, AllBuilt;
}

class BuildOrder {
    // Number of units needed to fulfill the order
    int numberToBuild;
    // Group to store units in for the order
    Group group;
}
