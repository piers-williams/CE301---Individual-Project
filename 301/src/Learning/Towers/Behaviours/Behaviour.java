package Learning.Towers.Behaviours;

import Learning.Towers.Entities.Entity;

/**
 *    Basic Behaviour for Entities
 */
public interface Behaviour {

    public void update();

    public Entity getEntity();
}
