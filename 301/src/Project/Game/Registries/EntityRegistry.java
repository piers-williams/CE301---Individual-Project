package Project.Game.Registries;

import Project.Game.Entities.Entity;
import de.matthiasmann.twl.Widget;

import java.util.HashMap;

/**
 *
 */
public class EntityRegistry implements NameRegistry {

    HashMap<String, Entity> dictionary;
    private final Object _dictionary = new Object();

    // String is the key to the series, Integer was the number in that series last given out
    // String-Integer
    // BV-23
    private static HashMap<String, Integer> NAMES = new HashMap<>();

    public EntityRegistry() {
        dictionary = new HashMap<>(1000);
    }

    public void layout(Widget widget, boolean visible) {
        synchronized (_dictionary) {
            for (Entity entity : dictionary.values()) {
                entity.layout(widget, visible);
            }
        }
    }

    @Override
    public Boolean has(String name) {
        synchronized (_dictionary) {
            return dictionary.containsKey(name);
        }
    }

    @Override
    public Entity get(String name) {
        synchronized (_dictionary) {
            return dictionary.get(name);
        }
    }

    @Override
    public void add(Entity entity) {
        synchronized (_dictionary) {
            dictionary.put(entity.getName(), entity);
        }
    }

    @Override
    public void remove(String name) {
        synchronized (_dictionary) {
            if (dictionary.containsKey(name)) dictionary.remove(name);
        }
    }

    public static String getNewName(String key) {
        if (!NAMES.containsKey(key)) NAMES.put(key, new Integer(0));
        NAMES.put(key, NAMES.get(key) + 1);

        return key + "-" + NAMES.get(key).toString();
    }
}
