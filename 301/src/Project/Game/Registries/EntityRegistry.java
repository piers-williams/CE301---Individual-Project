package Project.Game.Registries;

import Project.Game.Entities.Entity;

import java.util.HashMap;

/**
 *
 */
public class EntityRegistry implements NameRegistry {

    HashMap<String, Entity> dictionary;

    public EntityRegistry() {
        dictionary = new HashMap<>(1000);
    }

    @Override
    public Boolean has(String name) {
        return dictionary.containsKey(name);
    }

    @Override
    public Entity get(String name) {
        return dictionary.get(name);
    }

    @Override
    public void add(Entity entity) {
        dictionary.put(entity.getName(), entity);
    }

    @Override
    public void remove(String name) {
        if (dictionary.containsKey(name)) dictionary.remove(name);
    }
}
