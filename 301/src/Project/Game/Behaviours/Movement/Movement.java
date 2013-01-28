package Project.Game.Behaviours.Movement;

import Project.Game.Behaviours.Behaviour;
import Project.Game.Vector2D;

/**
 *
 */
public interface Movement extends Behaviour {

    public Vector2D getLocation();

    public void setLocation(Vector2D location);

    public Vector2D getDirection();

    public void setDirection(Vector2D direction);
}
