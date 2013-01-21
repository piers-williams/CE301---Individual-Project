package Learning.Towers.Influence;

import Learning.Towers.Entities.Entity;
import Learning.Towers.Faction;
import Learning.Towers.Factions;
import Learning.Towers.Main;
import Learning.Towers.Vector2D;
import org.lwjgl.opengl.GL11;

import java.util.HashMap;

/**
 * User: Piers
 * Date: 29/10/12
 * Time: 08:56
 */
public class InfluenceMap implements Runnable {

    private HashMap<Faction, double[][][]> influence;
    // Indices for which part to calculate and which to draw
    private int calculateIndex, drawIndex;
    // Which faction are we drawing
    private int factionIndex = 0;
    // Width and height of the board in cells, size of cell
    private int width, height, cellSize;
    // State markers
    private boolean running, paused;
    // How long to stay asleep for between calculations
    private int tickDelay;

    // TODO cache the vector2D's used for points
    public InfluenceMap(int width, int height, int cellSize, int tickDelay) {
        this.width = width;
        this.height = height;
        this.cellSize = cellSize;
        this.tickDelay = tickDelay;
        running = true;
        influence = new HashMap<>();
        for (Factions faction : Factions.values()) {
            influence.put(faction.getFaction(), new double[2][width][height]);
        }

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

        // clear all sections to 0.0
        for (double[][][] innerInfluence : influence.values()) {
            for (int x = 0; x < innerInfluence[calculateIndex].length; x++) {
                for (int y = 0; y < innerInfluence[calculateIndex][x].length; y++) {
                    innerInfluence[calculateIndex][x][y] = 0.0d;
                }
            }
            synchronized (Main.GAME_LOOP._entities) {
                for (Entity entity : Main.GAME_LOOP.getEntities()) {
                    addGridToInfluence(entity.getInfluenceGrid(), getPoint(entity), entity.getFaction());
                }
            }
        }
    }

    private Vector2D getPoint(Entity entity) {
        return new Vector2D((int) (entity.getX() / cellSize), (int) (entity.getY() / cellSize));
    }

    private void addGridToInfluence(InfluenceGrid grid, Vector2D point, Faction faction) {
        int xWidth = grid.influence.length / 2;
        int yWidth = grid.influence[0].length / 2;

        for (int x = -xWidth; x <= xWidth; x++) {
            for (int y = -yWidth; y <= yWidth; y++) {
                try {
                    if (influence.get(faction) == null) System.out.println(faction);
                    influence.get(faction)[calculateIndex][x + (int) point.x][y + (int) point.y] += grid.influence[x + xWidth][y + yWidth] / 32;
                } catch (ArrayIndexOutOfBoundsException arrayIndexOutOrBoundsException) {
                }
            }
        }
    }

    public void setPaused(boolean paused) {
        this.paused = paused;
    }

    public void draw() {
        Faction currentlyDrawingFaction = Factions.values()[factionIndex].getFaction();

        for (int x = 0, i = 0; x < width && i < influence.get(currentlyDrawingFaction)[drawIndex].length; x += cellSize, i++) {
            for (int y = 0, j = 0; y < height && j < influence.get(currentlyDrawingFaction)[drawIndex][i].length; y += cellSize, j++) {

                float strength = (float) influence.get(currentlyDrawingFaction)[drawIndex][i][j];
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

    public void cycleFaction() {
        factionIndex++;
        if (factionIndex >= Factions.values().length) factionIndex = 0;
    }
}
