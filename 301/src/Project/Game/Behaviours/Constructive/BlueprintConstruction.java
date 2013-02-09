package Project.Game.Behaviours.Constructive;

import Project.Game.Blueprints.Blueprint;
import Project.Game.Entities.Entity;
import Project.Game.Faction;
import Project.Game.Main;
import Project.Game.Vector2D;

import java.util.HashMap;

/**
 *
 */
public class BlueprintConstruction implements Construction {
    private Faction faction;
    private Entity entity;
    private Vector2D location;

    private Blueprint blueprint;

    // Use the offset as a key to locate buildings
    private HashMap<Vector2D, Entity> buildings;

    public BlueprintConstruction(Faction faction, Entity entity, Vector2D location, Blueprint blueprint) {
        this.faction = faction;
        this.entity = entity;
        this.location = location;
        this.blueprint = blueprint;

        buildings = new HashMap<>();
    }

    @Override
    public Vector2D getSpawnPoint() {
        return Main.VECTOR2D_SOURCE.getVector(500, 500);
    }

    @Override
    public void update() {
    }

    @Override
    public Entity getEntity() {
        return entity;
    }
}
