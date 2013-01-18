package Learning.Towers.Behaviours.Influence;

import Learning.Towers.Behaviours.Behaviour;
import Learning.Towers.Influence.InfluenceGrid;

/**
 * As these are rather static etc this can be sparsely implemented, and reduce memory consumption
 */
public interface Influence extends Behaviour {

    public InfluenceGrid getInfluenceGrid();

    public double getStrength();
}
