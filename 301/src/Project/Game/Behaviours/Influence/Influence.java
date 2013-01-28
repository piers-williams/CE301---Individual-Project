package Project.Game.Behaviours.Influence;

import Project.Game.Behaviours.Behaviour;
import Project.Game.Influence.InfluenceGrid;

/**
 * As these are rather static etc this can be sparsely implemented, and reduce memory consumption
 */
public interface Influence extends Behaviour {

    public InfluenceGrid getInfluenceGrid();

    public double getStrength();
}
