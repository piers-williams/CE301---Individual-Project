package Project.Game.Behaviours;

import Project.Game.Entities.Entity;

/**
 *    Basic Behaviour for Entities
 */
public interface Behaviour {

    public void update();

    public Entity getEntity();
}
