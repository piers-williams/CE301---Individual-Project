package Learning.Towers.Behaviours.Collision;

import Learning.Towers.Behaviours.Behaviour;
import Learning.Towers.Entities.Entity;

/**
 *
 */
public interface Collision extends Behaviour {

    public int getWidth();

    public Entity getEntity();
}
