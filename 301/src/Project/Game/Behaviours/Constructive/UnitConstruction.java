package Project.Game.Behaviours.Constructive;

import Project.Game.AI.SPL.Orders.AttackOrder;
import Project.Game.AI.SPL.Orders.DefendOrder;
import Project.Game.AI.SPL.SPLQueue;
import Project.Game.Entities.Entity;
import Project.Game.Entities.EntityFactory;
import Project.Game.Entities.Meta.Group;
import Project.Game.Faction;
import Project.Game.Main;
import Project.Game.Resource.ResourceDrain;
import Project.Game.Resource.ResourcePool;
import Project.Game.Vector2D;

/**
 * Builds units - better than SimpleConstruction
 */
public class UnitConstruction extends BasicConstruction {

    // Current state for the object
    private UnitState state;
    private SPLQueue splQueue;

    private AttackBuildOrder attackBuildOrder = null;
    private DefendBuildOrder defendBuildOrder = null;

    public UnitConstruction(Entity entity, Faction faction, ResourcePool resourcePool) {
        super(faction, entity, resourcePool);

        state = UnitState.Waiting;
        splQueue = faction.getSplQueue();
    }

    @Override
    public void update() {

        switch (state) {
            case Waiting:
                // check faction for new order to complete

                if (splQueue.hasDefendOrder()) {
                    DefendOrder order = splQueue.getNextDefendOrder();
                    defendBuildOrder = new DefendBuildOrder(order, faction, getSpawnPoint(), order.wasNLP());
                } else if (splQueue.hasAttackOrder()) {
                    // Consume the order
                    AttackOrder order = splQueue.getNextAttackOrder();
                    attackBuildOrder = new AttackBuildOrder(order.getNumberOfUnits(), order.getLocation(), faction, getSpawnPoint(), order.wasNLP());
                } else {
                    break;
                }

                state = UnitState.Building;
                resourceDrain = new ResourceDrain(resourcePool, 1);
                resourcePool.register(resourceDrain);
                break;
            case Building:
                // Complete next stage of construction for the current order
                if (resourceDrain.hasResource()) {
                    int temp = resourceDrain.claimResource();
                    resource += temp;
                } else {
                    System.out.println("No resource given");
                }
                if (resource > 60) {
                    resource -= 60;
                    if (attackBuildOrder != null) {
                        Entity entity = EntityFactory.getGroupedEntity(faction, attackBuildOrder.group, getSpawnPoint(), 2);
                        attackBuildOrder.group.addEntity(entity);
                        Main.GAME_LOOP.addEntity(entity);
                    } else if (defendBuildOrder != null) {
                        Entity entity = EntityFactory.getGroupedEntity(faction, defendBuildOrder.group, getSpawnPoint(), 2);
                        defendBuildOrder.group.addEntity(entity);
                        Main.GAME_LOOP.addEntity(entity);
                    }
                }
                // If group full, then we have built it all
                if (attackBuildOrder != null && attackBuildOrder.group.isFull()) state = UnitState.AllBuilt;
                if (defendBuildOrder != null && defendBuildOrder.group.isFull()) state = UnitState.AllBuilt;
                break;
            case AllBuilt:
                // Send off the group
                if (attackBuildOrder != null) {
                    attackBuildOrder.group.switchToFollow(attackBuildOrder.targetLocation);
                } else {
                    defendBuildOrder.group.setMovementBehaviour(null);
                }
                // Clear and reset the variables ready for the next order
                resourceDrain.deRegister();
                resourceDrain = null;
                attackBuildOrder = null;

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
class AttackBuildOrder {
    // Number of units needed to fulfill the order
    int numberToBuild;
    // Group to store units in for the order
    Group group;
    // Location to send the units to in the end
    Vector2D targetLocation;

    AttackBuildOrder(int numberToBuild, Vector2D targetLocation, Faction faction, Vector2D startLocation, boolean wasNLP) {
        this.numberToBuild = numberToBuild;
        this.targetLocation = targetLocation;

        group = new Group(faction, startLocation, numberToBuild, wasNLP);
        // Register the group
        Main.GAME_LOOP.addEntity(group);

    }
}

// Builds and registers a group, stores information from SPL to be used once group is full
class DefendBuildOrder {
    DefendOrder order;

    Group group;

    DefendBuildOrder(DefendOrder order, Faction faction, Vector2D startLocation, boolean wasNLP) {
        this.order = order;

        group = new Group(faction, startLocation, order.getNumberOfUnits(), wasNLP);

        Main.GAME_LOOP.addEntity(group);
    }
}
