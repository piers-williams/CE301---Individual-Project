package Project.Game.AI.ParticleSwarm;

import Project.Game.Main;
import Project.Game.Vector2D;

import java.util.ArrayList;

/**
 * Stores a grid of allowed points and runs swarms to find best
 * <p/>
 * Might need to thread this one out and use callbacks of a kind
 */
public class PlanningGrid {
    // [x][y][allowedType]
    // allowedType:
    // 0 = p
    // 1 = t
    // 2 = c
    char[][][] grid;
    int cellSize;
    ArrayList<BasicParticle> particles;

    public PlanningGrid(int cellSize, int Rp, int Rt, int Rc) {
        this.cellSize = cellSize;
        grid = new char[Main.MAP_WIDTH / cellSize][Main.MAP_HEIGHT / cellSize][3];
        particles = new ArrayList<>(300);

        constructGrid(Rp / cellSize, Rt / cellSize, Rc / cellSize);
    }

    /**
     * Creates the grid and fills it with where we can build things
     *
     * @param Rp Radius in cells of the Production bases
     * @param Rt Radius in cells of the Tower bases
     * @param Rc Radius in cells of the Construction bases
     */
    private void constructGrid(int Rp, int Rt, int Rc) {
        for (int x = 0; x < grid.length; x += Rp) {
            for (int y = 0; y < grid[x].length; y += Rp) {
                grid[x][y][0] = 'p';
            }
        }
        for (int x = 0; x < grid.length; x += Rt) {
            for (int y = 0; y < grid[x].length; y += Rt) {
                grid[x][y][1] = 't';
            }
        }
        for (int x = 0; x < grid.length; x += Rc) {
            for (int y = 0; y < grid[x].length; y += Rc) {
                grid[x][y][2] = 'c';
            }
        }
    }

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


abstract class BasicParticle {
    Vector2D location;
    Vector2D velocity;
    PlanningGrid grid;

    protected BasicParticle(Vector2D location, Vector2D velocity, PlanningGrid grid) {
        this.location = location;
        this.velocity = velocity;
        this.grid = grid;
    }

    protected void update() {

        // Use new velocity to move
        location.add(velocity);
    }

    protected abstract double fitness();
}
