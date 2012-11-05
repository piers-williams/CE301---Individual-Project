package Learning.KeyBoardHandling.Influence;

import Learning.KeyBoardHandling.Entity;

// Grid used by Entities to affect the influence maps
public class InfluenceGrid {

    double[][] influence;

    private InfluenceGrid(double[][] influence) {
        this.influence = influence;
    }

    // Only way to get one
    public static InfluenceGrid createGrid(Entity entity, InfluenceGridType type) {
//        System.out.println("Creating grids");
        InfluenceGrid grid;
        double[][] influence;
        switch (type) {
            case SimpleSquare:
                influence = new double[][]{
                        new double[]{0.25 * entity.getIS(), 0.25 * entity.getIS(), 0.25 * entity.getIS(), 0.25 * entity.getIS(), 0.25 * entity.getIS()},
                        new double[]{0.25 * entity.getIS(), 0.50 * entity.getIS(), 0.50 * entity.getIS(), 0.50 * entity.getIS(), 0.25 * entity.getIS()},
                        new double[]{0.25 * entity.getIS(), 0.50 * entity.getIS(), 1.00 * entity.getIS(), 0.50 * entity.getIS(), 0.25 * entity.getIS()},
                        new double[]{0.25 * entity.getIS(), 0.50 * entity.getIS(), 0.50 * entity.getIS(), 0.50 * entity.getIS(), 0.25 * entity.getIS()},
                        new double[]{0.25 * entity.getIS(), 0.25 * entity.getIS(), 0.25 * entity.getIS(), 0.25 * entity.getIS(), 0.25 * entity.getIS()}
                };
                return new InfluenceGrid(influence);

        }
        return new InfluenceGrid(new double[5][5]);
    }

    @Override
    public String toString() {
        return Double.toString(influence[3][3]);
    }
}
