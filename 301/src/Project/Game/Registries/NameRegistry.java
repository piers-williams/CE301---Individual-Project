package Project.Game.Registries;

import Project.Game.Entities.Entity;
import de.matthiasmann.twl.Widget;

/**
 *
 */
public interface NameRegistry {

    public Boolean has(String name);

    public Entity get(String name);

    public void add(Entity entity);

    public void remove(String name);

    public void layout(Widget widget, boolean visible);
}
