package Learning.Towers.Behaviours.Constructive;

import Learning.Towers.Entities.Entity;
import Learning.Towers.Entities.EntityFactory;
import Learning.Towers.Faction;
import Learning.Towers.Main;
import Learning.Towers.Vector2D;

/**
 * Builds Two Unit Constructors and 4 Towers in a grid
 */
public class BaseConstruction implements Construction {
    private Faction faction;
    private Entity entity;
    private Vector2D location;

    private int towerDistanceFromCenter;
    private int maxResource = 100, resource = 0;

    private Entity[] shipyards, towers;
    // These are offsets
    private Vector2D[] shipyardLocations, towerLocations;

    public BaseConstruction(Entity entity, Vector2D location, int towerDistanceFromCenter) {
        this.entity = entity;
        this.location = location;
        this.faction = entity.getFaction();
        this.towerDistanceFromCenter = towerDistanceFromCenter;

        calculateLocations();
    }

    private void calculateLocations() {
        shipyardLocations = new Vector2D[2];
        shipyards = new Entity[2];

        shipyardLocations[0] = new Vector2D(towerDistanceFromCenter / 2, 0);
        shipyardLocations[1] = new Vector2D(-towerDistanceFromCenter / 2, 0);

        towerLocations = new Vector2D[4];
        towers = new Entity[4];

        towerLocations[0] = new Vector2D(towerDistanceFromCenter, towerDistanceFromCenter);
        towerLocations[1] = new Vector2D(towerDistanceFromCenter, -towerDistanceFromCenter);
        towerLocations[2] = new Vector2D(-towerDistanceFromCenter, -towerDistanceFromCenter);
        towerLocations[3] = new Vector2D(-towerDistanceFromCenter, towerDistanceFromCenter);
    }

    @Override
    public void update() {
        removeDeadBuildings();
        findEmptyBuildSlot();

        if (resource < maxResource) resource++;
    }

    private void findEmptyBuildSlot() {
        // Check for Shipyards first
        if (resource > 75) {
            for (int i = 0; i < shipyards.length; i++) {
                if (shipyards[i] == null) {
                    Vector2D shipyardLocation = new Vector2D(shipyardLocations[i]);
                    shipyardLocation.add(this.location);
                    Entity shipyard = EntityFactory.getShipyard(faction, shipyardLocation);
                    shipyards[i] = shipyard;
                    Main.GAME_LOOP.addEntity(shipyard);

                    resource -= 75;

                    return;
                }
            }
        }
        if (resource > 30) {
            for (int i = 0; i < towers.length; i++) {
                if (towers[i] == null) {
                    // Build tower
                    Vector2D towerLocation = new Vector2D(towerLocations[i]);
                    towerLocation.add(this.location);
                    Entity tower = EntityFactory.getTower(faction, towerLocation);
                    towers[i] = tower;
                    Main.GAME_LOOP.addEntity(tower);
                    resource -= 30;

                    return;
                }
            }
        }
    }

    // TODO Need a notion of building cost
    // TODO Make Entity Blueprints
    // TODO Decide on the minimum information needed  - likely Faction/Location/Blueprint
    private void removeDeadBuildings() {
        for (int i = 0; i < shipyards.length; i++) {
            if (shipyards[i] != null) {
                if (!shipyards[i].isAlive()) shipyards[i] = null;
            }
        }
        for (int i = 0; i < towers.length; i++) {
            if (towers[i] != null) {
                if (!towers[i].isAlive()) towers[i] = null;
            }
        }
    }

    @Override
    public Entity getEntity() {
        return entity;
    }

    @Override
    public Vector2D getSpawnPoint() {
        return new Vector2D(500, 500);
    }
}
