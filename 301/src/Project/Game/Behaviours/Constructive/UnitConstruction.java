package Project.Game.Behaviours.Constructive;

import Project.Game.AI.SPL.Orders.AttackOrder;
import Project.Game.AI.SPL.SPLQueue;
import Project.Game.Entities.Entity;
import Project.Game.Entities.Meta.Group;
import Project.Game.Faction;
import Project.Game.Main;
import Project.Game.Resource.ResourcePool;
import Project.Game.Vector2D;
import com.sun.istack.internal.Nullable;

/**
 * Builds units - better than SimpleConstruction
 */
public class UnitConstruction extends BasicConstruction {

    // Current state for the object
    private UnitState state;
    private SPLQueue splQueue;

    @Nullable
    private BuildOrder buildOrder = null;

    public UnitConstruction(Faction faction, Entity entity, ResourcePool resourcePool) {
        super(faction, entity, resourcePool);

        state = UnitState.Waiting;
        splQueue = faction.getSplQueue();
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

                if (splQueue.hasAttackOrder()) {
                    // Consume the order
                    AttackOrder order = splQueue.getNextAttackOrder();
                    buildOrder = new BuildOrder(30, order.getLocation(), faction, getSpawnPoint());
                }
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

/**
 * Builds and registers a group, stores information from SPL to be used once group is full
 */
class BuildOrder {
    // Number of units needed to fulfill the order
    int numberToBuild;
    // Group to store units in for the order
    Group group;
    // Location to send the units to in the end
    Vector2D targetLocation;

    BuildOrder(int numberToBuild, Vector2D targetLocation, Faction faction, Vector2D startLocation) {
        this.numberToBuild = numberToBuild;
        this.targetLocation = targetLocation;

        group = new Group(faction.getR(), faction.getG(), faction.getB(), startLocation, numberToBuild, faction);
        // Register the group
        Main.GAME_LOOP.addEntity(group);

    }
}
