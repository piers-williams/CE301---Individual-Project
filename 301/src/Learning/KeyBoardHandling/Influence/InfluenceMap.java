package Learning.KeyBoardHandling.Influence;

import Learning.KeyBoardHandling.Entity;
import Learning.KeyBoardHandling.Faction;
import Learning.KeyBoardHandling.Main;
import Learning.KeyBoardHandling.Vector2D;
import org.lwjgl.opengl.GL11;

import java.util.HashMap;

/**
 * Created with IntelliJ IDEA.
 * User: Piers
 * Date: 29/10/12
 * Time: 08:56
 * To change this template use File | Settings | File Templates.
 */
public class InfluenceMap implements Runnable {

    @Deprecated
    private double[][] influence;
    private HashMap<Faction, double[][]> influences;
    private static final Object _influences = new Object();

    private int width, height, cellSize;

    private boolean running, paused;
    private int tickDelay;

    public InfluenceMap(int width, int height, int cellSize, int tickDelay) {
        this.width = width;
        this.height = height;
        this.cellSize = cellSize;
        this.tickDelay = tickDelay;
        running = true;

        influences = new HashMap<Faction, double[][]>(10);

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
        reset();
        synchronized (Main.GAME_LOOP._entities) {
            for (Entity entity : Main.GAME_LOOP.getEntities()) {
                if (entity.getFaction() != null) {
                    addGridToInfluence(entity.getInfluenceGrid(), getPoint(entity), entity.getFaction());
                }
            }
        }
    }


    private void reset() {
        synchronized (_influences) {
            influences.clear();
            for (Faction faction : Main.GAME_LOOP.getFactions()) {
                influences.put(faction, new double[width][height]);
            }
        }
    }

    private Vector2D getPoint(Entity entity) {
        return new Vector2D((int) (entity.x / cellSize), (int) (entity.y / cellSize));
    }

    private void addGridToInfluence(InfluenceGrid grid, Vector2D point, Faction faction) {
        int xWidth = grid.influence.length / 2;
        int yWidth = grid.influence[0].length / 2;

        for (int x = -xWidth; x <= xWidth; x++) {
            for (int y = -yWidth; y <= yWidth; y++) {
                try {
                    synchronized (_influences) {
                        influences.get(faction)[x + (int) point.x][y + (int) point.y] += grid.influence[x + xWidth][y + yWidth] / 32;
                    }
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
        synchronized (_influences) {
            for (Faction faction : influences.keySet()) {
                double[][] influence = influences.get(faction);
                for (int x = 0, i = 0; x < width && i < influence.length; x += cellSize, i++) {
                    for (int y = 0, j = 0; y < height && j < influence[i].length; y += cellSize, j++) {

                        float strength = (float) influence[i][j];
                        if (strength > 255) strength = 255;
                        if (strength != 0) {
                            GL11.glColor4f(strength * faction.r, strength * faction.g, strength * faction.b, 0.4f);
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
        }
    }
}
