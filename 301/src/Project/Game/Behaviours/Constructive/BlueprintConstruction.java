package Project.Game.Behaviours.Constructive;

import Project.Game.Blueprints.Blueprint;
import Project.Game.Blueprints.BlueprintBuilding;
import Project.Game.Buildings.MetaBuilding;
import Project.Game.Entities.Entity;
import Project.Game.Entities.EntityFactory;
import Project.Game.Faction;
import Project.Game.Main;
import Project.Game.Resource.ResourceDrain;
import Project.Game.Resource.ResourcePool;
import Project.Game.Vector2D;

import java.util.HashMap;

/**
 *
 */
public class BlueprintConstruction extends BasicConstruction {
    private Faction faction;
    private Vector2D location;

    private Blueprint blueprint;

    // Use the offset as a key to locate buildings
    private HashMap<Vector2D, Entity> buildings;

    private BlueprintState state;
    BlueprintBuilding currentBlueprint;
    MetaBuilding currentlyBuilding;

    private int ticksTillFinished;

    public BlueprintConstruction(Faction faction, Entity entity, ResourcePool resourcePool, Vector2D location, Blueprint blueprint) {
        super(faction, entity, resourcePool);
        this.faction = faction;
        this.location = location;
        this.blueprint = blueprint;

        buildings = new HashMap<>();

        state = BlueprintState.Looking;
    }

    @Override
    public Vector2D getSpawnPoint() {
        return Main.VECTOR2D_SOURCE.getVector(0, 0);
    }

    @Override
    public void update() {
        switch (state) {
            case Looking:
                // Find the next building to build
                findAndBuildNext();
                break;
            case AllConstructed:
                break;
            case Constructing:
                break;
        }

    }

    private void findAndBuildNext() {

        for (BlueprintBuilding blueprintBuilding : blueprint.getBlueprintBuildings()) {
            if (!buildings.containsKey(blueprintBuilding.getOffset())) {
                resourceDrain.deRegister();
                currentBlueprint = blueprintBuilding;
                currentlyBuilding = Main.BUILDING_REGISTRY.getBuilding(blueprintBuilding.getType());
                // Need to put this type of information into the blueprint

                int drainPerTick = currentlyBuilding.getCost() / currentlyBuilding.getBuildTime();
                resourceDrain = new ResourceDrain(resourcePool, drainPerTick);
                // Register the resourceDrain
                resourcePool.register(resourceDrain);
                // Only find first one but don't change the state
                return;
            }
        }

        state = BlueprintState.AllConstructed;
    }

    private void constructionWork() {
        ticksTillFinished++;

        if (ticksTillFinished == 0) {
            // Switch state
            state = BlueprintState.Looking;

            // Obtain building
            Entity entity = EntityFactory.getBuilding(
                    faction,
                    Vector2D.add(location, currentBlueprint.getOffset()),
                    currentlyBuilding.getName()
            );

            buildings.put(currentBlueprint.getOffset(), entity);
            Main.GAME_LOOP.addEntity(entity);
        }
    }
}

enum BlueprintState {
    Constructing,
    AllConstructed,
    Looking
}
