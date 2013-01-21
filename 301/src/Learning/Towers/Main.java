package Learning.Towers;

import Learning.Towers.Entities.EntityFactory;
import Learning.Towers.Influence.InfluenceMap;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;

/**
 * User: Piers
 * Date: 16/10/12
 * Time: 11:49
 */
public class Main {
    public static int SCREEN_WIDTH = 1600;
    public static int SCREEN_HEIGHT = 900;

    public static int MAP_WIDTH = 1600;
    public static int MAP_HEIGHT = 900;
    public static int SQUARE_WIDTH = 6;
    public static int SQUARE_COUNT = 0;
    public static boolean FULL_SCREEN = true;
    // Collision detection cell size
    public static final int CELL_SIZE = 75;

    public static GameLoop GAME_LOOP;
    public static CollisionBoard COLLISION_BOARD;
    public static InfluenceMap INFLUENCE_MAP;

    public static KeyManager KEY_MANAGER;
    private Boolean paused;

    public Main() {
        paused = true;
        Main.GAME_LOOP = new GameLoop(20);
        Main.COLLISION_BOARD = new CollisionBoard(CELL_SIZE);
        Main.INFLUENCE_MAP = new InfluenceMap(Main.MAP_WIDTH, Main.MAP_HEIGHT, 30, 40);
        KEY_MANAGER = new KeyManager(this);

        if (FULL_SCREEN) System.setProperty("org.lwjgl.opengl.Window.undecorated", "true");
        // Set up the display
        try {
            Display.setDisplayMode(new DisplayMode(SCREEN_WIDTH, SCREEN_HEIGHT));
            Display.setFullscreen(true);
            Display.create();

            GL11.glMatrixMode(GL11.GL_PROJECTION);
            GL11.glLoadIdentity();
            GL11.glOrtho(0, SCREEN_WIDTH, 0, SCREEN_HEIGHT, 1, -1);
            GL11.glMatrixMode(GL11.GL_MODELVIEW);
            GL11.glEnable(GL11.GL_BLEND);
            GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

        } catch (Exception e) {

        }

        Thread physics = new Thread(Main.COLLISION_BOARD);
        physics.setDaemon(true);
        physics.setName("Collision Detection");
        physics.start();

        Thread loop = new Thread(GAME_LOOP);
        loop.setDaemon(true);
        loop.setName("Game Loop");
        loop.start();

        Thread influence = new Thread(INFLUENCE_MAP);
        influence.setDaemon(true);
        influence.setName("Influence Thread");
        influence.start();

        for (Factions factions : Factions.values()) {
            GAME_LOOP.addFaction(factions.getFaction());
        }

        /**
         * Main render loop
         */
        while (!Display.isCloseRequested()) {
            try {
                Thread.sleep(20);
            } catch (Exception e) {

            }

            // Keyboard release
            if (Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) {
                break;
            }

            GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);

            Main.GAME_LOOP.draw();
            INFLUENCE_MAP.draw();

            Display.update();
            KEY_MANAGER.update();
        }
        Display.destroy();
    }

    public static void main(String[] args) {
        new Main();
    }

    /**
     * Switches the game from a paused state to an un-paused state or vice visa
     */
    public void togglePause(){
        paused = !paused;

        // Set the loops to do work
        COLLISION_BOARD.setPaused(paused);
        GAME_LOOP.setPaused(paused);
        INFLUENCE_MAP.setPaused(paused);
    }
}