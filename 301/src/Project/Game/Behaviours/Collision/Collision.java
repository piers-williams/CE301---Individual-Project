package Project.Game.Behaviours.Collision;

import Project.Game.Behaviours.Behaviour;
import Project.Game.Entities.Entity;

/**
 *
 */
public interface Collision extends Behaviour {

    public int getWidth();

    public Entity getEntity();
}
