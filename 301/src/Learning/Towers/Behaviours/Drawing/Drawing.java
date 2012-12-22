package Learning.Towers.Behaviours.Drawing;

import Learning.Towers.Behaviours.Behaviour;

/**
 * Defines the use of the drawing behaviour
 */
public interface Drawing extends Behaviour {

    /**
     * Should always draw with the units location being the center point of the drawing
     */
    public void draw();
}
