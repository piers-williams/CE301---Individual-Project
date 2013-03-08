package Project.Game.Registries;

import Project.Game.Entities.Entity;

/**
 *
 */
public interface NameRegistry {

    public Boolean has(String name);

    public Entity get(String name);

    public void add(Entity entity);

    public void remove(String name);
}
