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
        lookForDeadBuildings();
//        System.out.println(state);
        switch (state) {
            case Looking:
                // Find the next building to build
                findAndBuildNext();
                break;
            case AllConstructed:
                break;
            case Constructing:
                constructionWork();
                break;
        }

    }

    private void lookForDeadBuildings() {
        for (Vector2D buildingOffset : buildings.keySet()) {
            Entity building = buildings.get(buildingOffset);
            if (!building.isAlive()) {
                // Might not like this while it is traversing
                buildings.remove(buildingOffset);
                if (state == BlueprintState.AllConstructed) state = BlueprintState.Looking;
            }
        }

    }

    private void findAndBuildNext() {

        //System.out.println(blueprint.getBlueprintBuildings().size());
        // Is empty ...
        for (BlueprintBuilding blueprintBuilding : blueprint.getBlueprintBuildings()) {
            if (!buildings.containsKey(blueprintBuilding.getOffset())) {
                if (resourceDrain != null) resourceDrain.deRegister();
                currentBlueprint = blueprintBuilding;
                currentlyBuilding = Main.BUILDING_REGISTRY.getBuilding(blueprintBuilding.getType());
                // Need to put this type of information into the blueprint

                int drainPerTick = currentlyBuilding.getCost() / currentlyBuilding.getBuildTime();
                ticksTillFinished = currentlyBuilding.getBuildTime();
                resourceDrain = new ResourceDrain(resourcePool, drainPerTick);
                // Register the resourceDrain
                resourcePool.register(resourceDrain);
                // Only find first one but don't change the state
                state = BlueprintState.Constructing;
                return;
            }
        }

        state = BlueprintState.AllConstructed;
    }

    private void constructionWork() {
        ticksTillFinished--;

        if (ticksTillFinished == 0) {
            // Switch state
            state = BlueprintState.Looking;

            // Obtain building
            Entity entity = EntityFactory.getBuilding(
                    faction,
                    Vector2D.add(location, currentBlueprint.getOffset()),
                    currentlyBuilding
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
