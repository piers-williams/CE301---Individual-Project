package Project.Game.AI.ParticleSwarm;

import Project.Game.Main;

import java.util.ArrayList;

/**
 * Stores a grid of allowed points and runs swarms to find best
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

    public PlanningGrid(int cellSize){
        this.cellSize = cellSize;
        grid = new char[Main.MAP_WIDTH / cellSize][Main.MAP_HEIGHT / cellSize][3];
        particles = new ArrayList<>(300);
    }

    private void constructGrid(){
        // Need to know Rp, Rt, Rc
    }

}


abstract class BasicParticle{

}
