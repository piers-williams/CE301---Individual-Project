package Project.Game.AI.ParticleSwarm;

import Project.Game.Main;
import Project.Game.Vector2D;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Stores a grid of allowed points and runs swarms to find best
 * <p/>
 * Might need to thread this one out and use callbacks of a kind
 */
public class PlanningGrid {

    // TODO Encode information about whether something already exists here
    HashMap<Vector2D, ArrayList<Character>> grid;
    int cellSize;
    ArrayList<BasicParticle> particles;

    ArrayList<BuildingShadow> buildingShadows;

    // Map existing buildings onto the grid using basic collision based on the radius.
    // assign a shadow to each particle to use to check for collisions with existing buildings
    public PlanningGrid(int cellSize, int Rp, int Rt, int Rc) {
        this.cellSize = cellSize;
        grid = new HashMap<>();
        particles = new ArrayList<>(300);
        // Perform conversion to cells from actual radius
        constructGrid(Rp / cellSize, Rt / cellSize, Rc / cellSize);

        buildingShadows = new ArrayList<>();
    }

    /**
     * Creates the grid and fills it with where we can build things
     *
     * @param Rp Radius in cells of the Production bases
     * @param Rt Radius in cells of the Tower bases
     * @param Rc Radius in cells of the Construction bases
     */
    private void constructGrid(int Rp, int Rt, int Rc) {
        for (int x = 0; x < Main.MAP_WIDTH / cellSize; x += Rp) {
            for (int y = 0; y < Main.MAP_HEIGHT / cellSize; y += Rp) {
                grid.put(Main.VECTOR2D_SOURCE.getVector(x, y), new ArrayList<Character>(3));
                grid.get(Main.VECTOR2D_SOURCE.getVector(x, y)).add('p');
            }
        }
        for (int x = 0; x < Main.MAP_WIDTH / cellSize; x += Rc) {
            for (int y = 0; y < Main.MAP_HEIGHT / cellSize; y += Rc) {
                if (!grid.containsKey(Main.VECTOR2D_SOURCE.getVector(x, y))) {
                    grid.put(Main.VECTOR2D_SOURCE.getVector(x, y), new ArrayList<Character>(3));
                }
                grid.get(Main.VECTOR2D_SOURCE.getVector(x, y)).add('c');
            }
        }
        for (int x = 0; x < Main.MAP_WIDTH / cellSize; x += Rt) {
            for (int y = 0; y < Main.MAP_HEIGHT / cellSize; y += Rt) {
                if (!grid.containsKey(Main.VECTOR2D_SOURCE.getVector(x, y))) {
                    grid.put(Main.VECTOR2D_SOURCE.getVector(x, y), new ArrayList<Character>(3));
                }
                grid.get(Main.VECTOR2D_SOURCE.getVector(x, y)).add('t');
            }
        }
    }

    /**
     * Calculates and returns the location of the global best position of all the other particles
     *
     * @return Vector2D the best global position
     */
    private Vector2D calculateGlobalBest() {
        BasicParticle bestSoFar = null;
        double bestFitnessSoFar = Double.MAX_VALUE;
        for (BasicParticle particle : particles) {
            if (bestSoFar == null || particle.fitness() < bestFitnessSoFar) {
                bestSoFar = particle;
                bestFitnessSoFar = particle.fitness();
            }
        }

        return bestSoFar.location;
    }

}

class BuildingShadow {
    Vector2D location;
    Vector2D size;

    BuildingShadow(Vector2D location, Vector2D size) {
        this.location = location;
        this.size = size;
    }

    static boolean collides(BuildingShadow first, BuildingShadow second) {

        return (
                Math.abs(first.location.x - second.location.x) <= Math.abs(first.size.x - second.size.x)
                        &&
                        Math.abs(first.location.y - second.location.y) <= Math.abs(first.size.y - second.size.y)
        );
    }
}


class BasicParticle {
    Vector2D location;
    Vector2D velocity;
    PlanningGrid grid;
    FitnessFunction fitnessFunction;

    protected BasicParticle(Vector2D location, Vector2D velocity, PlanningGrid grid, FitnessFunction fitnessFunction) {
        this.location = location;
        this.velocity = velocity;
        this.grid = grid;
        this.fitnessFunction = fitnessFunction;
    }

    protected void update(Vector2D globalBest) {

        // Use new velocity to move
        location.add(velocity);
    }

    protected double fitness() {
        return fitnessFunction.fitness();
    }

    /**
     * Resets the particle to a new location, velocity and fitness function
     *
     * @param location        new location to start from
     * @param velocity        new velocity to begin with
     * @param fitnessFunction new fitness function to calculate with
     */
    protected void reset(Vector2D location, Vector2D velocity, FitnessFunction fitnessFunction) {
        this.location = location;
        this.velocity = velocity;
        this.fitnessFunction = fitnessFunction;
    }
}

abstract class FitnessFunction {
    protected abstract double fitness();
}
