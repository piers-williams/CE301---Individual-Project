package Project.Game.Registries;

import Project.Game.Entities.Entity;

import java.util.HashMap;

/**
 *
 */
public class EntityRegistry implements NameRegistry {

    HashMap<String, Entity> dictionary;

    // String is the key to the series, Integer was the number in that series last given out
    // String-Integer
    // BV-23
    private static HashMap<String, Integer> NAMES = new HashMap<>();

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

    public static String getNewName(String key) {
        if (!NAMES.containsKey(key)) NAMES.put(key, new Integer(0));
        NAMES.put(key, NAMES.get(key) + 1);

        return key + "-" + NAMES.get(key).toString();
    }
}
