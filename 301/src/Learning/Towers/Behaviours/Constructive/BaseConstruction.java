package Learning.Towers.Behaviours.Constructive;

import Learning.Towers.Main;
import Learning.Towers.Vector2D;
import Learning.Towers.Entities.Entity;
import Learning.Towers.Entities.EntityFactory;
import Learning.Towers.Faction;

import java.util.ArrayList;

/**
 * Builds Two Unit Constructors and 4 Towers in a grid
 */
public class BaseConstruction implements Construction {
    private Faction faction;
    private Entity entity;

    private int towerDistanceFromCenter;
    private int maxResource = 100, resource = 0;

    private ArrayList<Entity> towers;
    private Entity[] shipyards;
    private Vector2D[] shipyardLocations;

    @Override
    public void update() {
        removeDeadBuildings();
        findEmptyBuildSlot();
    }

    // TODO Improve this
    private void findEmptyBuildSlot() {
        for (int i = 0; i < shipyards.length; i++) {
            if (shipyards[i] == null) {
                if (resource > 100) {
                    Entity shipyard = EntityFactory.getBaseEntity(faction, shipyardLocations[i], 26);
                    shipyards[i] = shipyard;
                    Main.GAME_LOOP.addEntity(shipyard);
                }
            }
        }
    }

    private void removeDeadBuildings(){

    }

    @Override
    public Entity getEntity() {
        return null;
    }
}
