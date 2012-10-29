package Learning.InfluenceMaps.Influence;

import Learning.InfluenceMaps.Entity;
import Learning.InfluenceMaps.GameLoop;
import Learning.InfluenceMaps.Vector2D;

/**
 * Created with IntelliJ IDEA.
 * User: Piers
 * Date: 29/10/12
 * Time: 08:56
 * To change this template use File | Settings | File Templates.
 */
public class InfluenceMap implements Runnable {

    private double[][] influence;

    private int width, height, cellSize;

    private GameLoop loop;

    private boolean running, paused;
    private int tickDelay;

    public InfluenceMap(int width, int height, int cellSize, int tickDelay) {
        this.width = width;
        this.height = height;
        this.cellSize = cellSize;
        this.tickDelay = tickDelay;

        influence = new double[width][height];
    }

    @Override
    public void run() {
        while (running) {
            try {
                Thread.sleep(tickDelay);
            } catch (InterruptedException ie) {
                System.out.println(ie.getStackTrace());
            }
            if (!paused) {
                 update();
            }
        }
    }

    private void update() {
        influence = new double[width][height];
        for (Entity entity : loop.getEntities()) {
            addGridToInfluence(entity.getInfluenceGrid(), getPoint(entity));
        }
    }

    private Vector2D getPoint(Entity entity){
        return new Vector2D((int)(entity.x / cellSize), (int)(entity.y / cellSize));
    }
    private void addGridToInfluence(InfluenceGrid grid, Vector2D point){

    }

    public void draw() {

    }
}
