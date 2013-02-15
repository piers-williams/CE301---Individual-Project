package Project.Game.Buildings;

import java.util.HashMap;

/**
 * Stores and loads the building xml
 *
 * Gives access to building stats
 */
public class BuildingRegistry {

    HashMap<String, MetaBuilding> buildings;

    public BuildingRegistry(){
        buildings = new HashMap<>();
    }

    public MetaBuilding getBuilding(String name){

    }
}
