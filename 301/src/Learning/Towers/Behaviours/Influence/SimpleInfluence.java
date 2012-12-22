package Learning.Towers.Behaviours.Influence;

import Learning.Towers.Influence.InfluenceGrid;

/**
 * Probably all we will need, unless Influences changes based on mode or something
 */
public class SimpleInfluence implements Influence {

    private InfluenceGrid grid;

    public SimpleInfluence(InfluenceGrid grid) {
        this.grid = grid;
    }

    @Override
    public InfluenceGrid getInfluenceGrid() {
        return grid;
    }

    @Override
    public void update() {
        // do nothing
    }
}
