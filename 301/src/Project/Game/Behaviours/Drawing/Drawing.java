package Project.Game.Behaviours.Drawing;

import Project.Game.Behaviours.Behaviour;

/**
 * Defines the use of the drawing behaviour
 */
public interface Drawing extends Behaviour {

    /**
     * Should always draw with the units location being the center point of the drawing
     */
    public void draw();
}
