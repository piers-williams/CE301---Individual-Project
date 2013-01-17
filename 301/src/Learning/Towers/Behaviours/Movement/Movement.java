package Learning.Towers.Behaviours.Movement;

import Learning.Towers.Behaviours.Behaviour;
import Learning.Towers.Vector2D;

/**
 *
 */
public interface Movement extends Behaviour {

    public Vector2D getLocation();
    public void setLocation(Vector2D location);

    public Vector2D getDirection();
    public void setDirection(Vector2D direction);
}
