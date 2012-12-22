package Learning.Towers.Influence;

import Learning.Towers.Entities.Entity;

import java.util.HashMap;

// Grid used by Entities to affect the influence maps
public class InfluenceGrid {

    double[][] influence;

    // Stores grids already calculated to save computation
    // In all cases, create a grid with strength 1, for ease of calculating grids of any strength
    private static HashMap<Integer, HashMap<Double, InfluenceGrid>> cachedGrids = new HashMap<>();

    private InfluenceGrid(double[][] influence) {
        this.influence = influence;
    }

    // Only way to get one
    public static InfluenceGrid createGrid(Entity entity, Integer size) {
        // If we've made a grid of the correct size already
        if (cachedGrids.containsKey(size)) {
            HashMap<Double, InfluenceGrid> grids = cachedGrids.get(size);

            if (grids.containsKey(entity.getIS())) return grids.get(entity.getIS());

            // Obtain the normal grid and transform it
            InfluenceGrid newGrid = multiply(grids.get(1.0d), entity.getIS());

            // cache the new grid
            grids.put(entity.getIS(), newGrid);
            return newGrid;
        } else {
            HashMap<Double, InfluenceGrid> grids = new HashMap<>();
            InfluenceGrid normalGrid = generateNormalGrid(size);


            grids.put(1.0d, normalGrid);

            InfluenceGrid newGrid = multiply(normalGrid, entity.getIS());
            grids.put(entity.getIS(), newGrid);
            return newGrid;
        }
    }

    private static InfluenceGrid generateNormalGrid(Integer size) {
        // Make new grid of strength 1
        double[][] temp = new double[size][size];
        int center = ((size - 1) / 2) + 1;
        for (int x = 0; x < temp.length; x++) {
            for (int y = 0; y < temp[x].length; y++) {
                // calculate distance from center
                int deltaXSquared = (center - x) * (center - x);
                int deltaYSquared = (center - y) * (center - y);

                double distance = Math.sqrt(deltaXSquared + deltaYSquared);
                // Hopefully thats right
                temp[x][y] = 1 - (distance / size);
            }
        }

        return new InfluenceGrid(temp);
    }

    private static InfluenceGrid multiply(InfluenceGrid input, double factor) {
        double[][] temp = input.influence.clone();
        // Multiply everything by the factor
        for (int x = 0; x < temp.length; x++) for (int y = 0; y < temp[x].length; y++) temp[x][y] *= factor;
        // return the new grid
        return new InfluenceGrid(temp);
    }

    @Override
    public String toString() {
        return Double.toString(influence[3][3]);
    }
}
