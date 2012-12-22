package Learning.Towers.Influence;

import Learning.Towers.Entities.Entity;
import Learning.Towers.Main;
import Learning.Towers.Vector2D;
import org.lwjgl.opengl.GL11;

/**
 * Created with IntelliJ IDEA.
 * User: Piers
 * Date: 29/10/12
 * Time: 08:56
 */
public class InfluenceMap implements Runnable {

    private double[][][] influence;
    private int calculateIndex, drawIndex;


    private int width, height, cellSize;

    private boolean running, paused;
    private int tickDelay;

    public InfluenceMap(int width, int height, int cellSize, int tickDelay) {
        this.width = width;
        this.height = height;
        this.cellSize = cellSize;
        this.tickDelay = tickDelay;
        running = true;

        influence = new double[2][width][height];
        calculateIndex = 0;
        drawIndex = 1;
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
        // Swap the buffers
        calculateIndex = drawIndex;
        drawIndex = (drawIndex == 0) ? 1 : 0;
        // clear this section to 0.0
        for(int x = 0; x < influence[calculateIndex].length; x++) for(int y = 0; y < influence[calculateIndex][x].length; y++) influence[calculateIndex][x][y] = 0.0d;
        synchronized (Main.GAME_LOOP._entities) {
            for (Entity entity : Main.GAME_LOOP.getEntities()) {
                addGridToInfluence(entity.getInfluenceGrid(), getPoint(entity));
            }
        }
    }

    private Vector2D getPoint(Entity entity) {
        return new Vector2D((int) (entity.getX() / cellSize), (int) (entity.getY() / cellSize));
    }

    private void addGridToInfluence(InfluenceGrid grid, Vector2D point) {
        int xWidth = grid.influence.length / 2;
        int yWidth = grid.influence[0].length / 2;

        for (int x = -xWidth; x <= xWidth; x++) {
            for (int y = -yWidth; y <= yWidth; y++) {
                try {
                    influence[calculateIndex][x + (int) point.x][y + (int) point.y] += grid.influence[x + xWidth][y + yWidth] / 32;
                } catch (ArrayIndexOutOfBoundsException arrayIndexOutOrBoundsException) {
                }
            }
        }
    }

    public void setPaused(boolean paused) {
        this.paused = paused;
    }

    public void draw() {
        for (int x = 0, i = 0; x < width && i < influence[drawIndex].length; x += cellSize, i++) {
            for (int y = 0, j = 0; y < height && j < influence[drawIndex][i].length; y += cellSize, j++) {

                float strength = (float) influence[drawIndex][i][j];
                if (strength > 255) strength = 255;

                GL11.glColor4f(strength, 0, 0, 0.5f);
                GL11.glBegin(GL11.GL_QUADS);
                GL11.glVertex2d(x, y);
                GL11.glVertex2d(x + cellSize, y);
                GL11.glVertex2d(x + cellSize, y + cellSize);
                GL11.glVertex2d(x, y + cellSize);
                GL11.glEnd();
            }
        }
    }
}
