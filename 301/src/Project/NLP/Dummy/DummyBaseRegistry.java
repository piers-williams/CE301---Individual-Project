package Project.NLP.Dummy;

import Project.Game.Entities.Entity;
import Project.Game.Entities.EntityFactory;
import Project.Game.Factions;
import Project.Game.Registries.BaseRegistry;

import java.util.HashMap;

/**
 *
 */
public class DummyBaseRegistry implements BaseRegistry {

    HashMap<String, Entity> values = new HashMap<>();

    public DummyBaseRegistry() {
        for (Integer i = 0; i < 10; i++) {
            values.put("BS-" + i.toString(), EntityFactory.getFakeEntity(Factions.Nature.getFaction()));
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
