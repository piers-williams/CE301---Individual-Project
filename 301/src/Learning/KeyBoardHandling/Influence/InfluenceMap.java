package Learning.KeyBoardHandling.Influence;

import Learning.KeyBoardHandling.Entity;
import Learning.KeyBoardHandling.Main;
import Learning.KeyBoardHandling.Vector2D;
import org.lwjgl.opengl.GL11;

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

    private boolean running, paused;
    private int tickDelay;

    public InfluenceMap(int width, int height, int cellSize, int tickDelay) {
        this.width = width;
        this.height = height;
        this.cellSize = cellSize;
        this.tickDelay = tickDelay;
        running = true;

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
        synchronized (Main.GAME_LOOP._entities) {
            for (Entity entity : Main.GAME_LOOP.getEntities()) {
                addGridToInfluence(entity.getInfluenceGrid(), getPoint(entity));
            }
        }
    }

    private Vector2D getPoint(Entity entity) {
        return new Vector2D((int) (entity.x / cellSize), (int) (entity.y / cellSize));
    }

    private void addGridToInfluence(InfluenceGrid grid, Vector2D point) {
        int xWidth = grid.influence.length / 2;
        int yWidth = grid.influence[0].length / 2;

        for (int x = -xWidth; x <= xWidth; x++) {
            for (int y = -yWidth; y <= yWidth; y++) {
                try {
                    influence[x + (int) point.x][y + (int) point.y] += grid.influence[x + xWidth][y + yWidth] / 32;
//                    System.out.println(grid.influence[x + xWidth][y + yWidth]);
//                    System.out.println("Doing something");
                } catch (ArrayIndexOutOfBoundsException aioobe) {
//                    System.err.println("oh dear :(");
                }
            }
        }

    }

    public void setPaused(boolean paused) {
        this.paused = paused;
    }

    public void draw() {
        for (int x = 0, i = 0; x < width && i < influence.length; x += cellSize, i++) {
            for (int y = 0, j = 0; y < height && j < influence[i].length; y += cellSize, j++) {

                float strength = (float)influence[i][j];
                if(strength > 255) strength = 255;
//                System.out.println(strength);

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
