package Project.NLP.Dummy;

import Project.Game.Blueprints.BlueprintRegistry;
import Project.Game.Entities.Entity;
import Project.Game.Entities.EntityFactory;
import Project.Game.Factions;
import Project.Game.Main;
import Project.Game.Registries.BaseRegistry;
import Project.Game.Utilities;

import java.util.HashMap;

/**
 *
 */
public class DummyBaseRegistry implements BaseRegistry {

    HashMap<String, Entity> values = new HashMap<>();

    public DummyBaseRegistry() {

        BlueprintRegistry.load("Content/Bases/Bases.xml");
        for (Integer i = 0; i < 10; i++) {
            values.put("BS-" + i.toString(), EntityFactory.getBlueprint(Factions.Nature.getFaction(), Utilities.randomLocation(), "Production Level 1"));
        }
    }

    @Override
    public Boolean has(String name) {
        return values.containsKey(name);
    }

    @Override
    public Entity get(String name) {
        return values.get(name);
    }
}
