package Learning.Towers.Behaviours.Influence;

import Learning.Towers.Entities.Entity;
import Learning.Towers.Influence.InfluenceGrid;

/**
 * Probably all we will need, unless Influences changes based on mode or something
 */
public class SimpleInfluence implements Influence {

    private InfluenceGrid grid;
    private double strength;
    private Entity entity;

    public SimpleInfluence(Entity entity, int size, double strength) {
        this.entity = entity;

        this.grid = InfluenceGrid.createGrid(size, strength);
        this.strength = strength;
    }

    @Override
    public InfluenceGrid getInfluenceGrid() {
        return grid;
    }

    @Override
    public void update() {
        // do nothing
    }

    @Override
    public Entity getEntity() {
        return entity;
    }

    public double getStrength() {
        return strength;
    }
}
