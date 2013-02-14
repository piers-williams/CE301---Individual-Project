package Project.Game;

import Project.Game.Blueprints.BlueprintRegistry;
import Project.Game.Influence.InfluenceMap;
import de.matthiasmann.twl.Button;
import de.matthiasmann.twl.GUI;
import de.matthiasmann.twl.Widget;
import de.matthiasmann.twl.renderer.lwjgl.LWJGLRenderer;
import de.matthiasmann.twl.theme.ThemeManager;
import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;

import java.io.File;
import java.io.IOException;

/**
 * User: Piers
 * Date: 16/10/12
 * Time: 11:49
 */
public class Main extends Widget {
    public static int SCREEN_WIDTH = 800;
    public static int SCREEN_HEIGHT = 600;

    public static int MAP_WIDTH = 1600;
    public static int MAP_HEIGHT = 900;
    public static int SQUARE_WIDTH = 6;
    public static boolean FULL_SCREEN = false;
    // Collision detection cell size
    public static final int CELL_SIZE = 75;

    public static GameLoop GAME_LOOP;
    public static CollisionBoard COLLISION_BOARD;
    public static InfluenceMap INFLUENCE_MAP;
    public static CachedVector2DSource VECTOR2D_SOURCE;
    public static BlueprintRegistry BLUEPRINT_REGISTRY;

    public static KeyManager KEY_MANAGER;
    private Boolean paused;

    private GUI gui;
    private LWJGLRenderer renderer;
    private ThemeManager themeManager;
    private Button button;

    // Where the view is located
    public static final Vector2D viewLocation = new Vector2D(0, 0);

    public Main() {

        paused = true;
        Main.GAME_LOOP = new GameLoop(20);
        VECTOR2D_SOURCE = new CachedVector2DSource();
        Main.COLLISION_BOARD = new CollisionBoard(CELL_SIZE);
        Main.INFLUENCE_MAP = new InfluenceMap(Main.MAP_WIDTH, Main.MAP_HEIGHT, 30, 40);
        BLUEPRINT_REGISTRY = BlueprintRegistry.load("Content/Bases/Bases.xml");
        KEY_MANAGER = new KeyManager(this);
        if (FULL_SCREEN) System.setProperty("org.lwjgl.opengl.Window.undecorated", "true");
        // Set up the display
        try {
            Display.setDisplayMode(new DisplayMode(SCREEN_WIDTH, SCREEN_HEIGHT));
            Display.setFullscreen(false);
            Display.create();

            GL11.glMatrixMode(GL11.GL_PROJECTION);
            GL11.glLoadIdentity();
            GL11.glOrtho(0, SCREEN_WIDTH, 0, SCREEN_HEIGHT, 1, -1);
            GL11.glMatrixMode(GL11.GL_MODELVIEW);
            GL11.glEnable(GL11.GL_BLEND);
            GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

            initTWL();

            button = new Button("Pause");
            button.setTheme("button");
            add(button);
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
            GL11.glTranslated(viewLocation.x, viewLocation.y, 0);

            Main.GAME_LOOP.draw();
            INFLUENCE_MAP.draw();

            GL11.glTranslated(-viewLocation.x, -viewLocation.y, 0);
            drawBoundary();
            gui.update();
            Display.update();
            KEY_MANAGER.update();
        }
        Display.destroy();
    }

    private void drawBoundary() {
        GL11.glColor4f(255, 255, 255, 255);

        // Left
        GL11.glBegin(GL11.GL_LINE);
        GL11.glVertex2d(0, 0);
        GL11.glVertex2d(10, Main.MAP_HEIGHT);
        GL11.glEnd();

        // Top
        GL11.glBegin(GL11.GL_LINE);
        GL11.glVertex2d(0, 0);
        GL11.glVertex2d(Main.MAP_WIDTH, 10);
        GL11.glEnd();

        // Bottom
        GL11.glBegin(GL11.GL_LINE);
        GL11.glVertex2d(0, Main.MAP_HEIGHT);
        GL11.glVertex2d(Main.MAP_WIDTH, Main.MAP_HEIGHT);
        GL11.glEnd();

        // Right
        GL11.glBegin(GL11.GL_LINE);
        GL11.glVertex2d(Main.MAP_WIDTH, 0);
        GL11.glVertex2d(Main.MAP_WIDTH, Main.MAP_HEIGHT);
        GL11.glEnd();
    }

    public static void main(String[] args) {
        new Main();
    }

    /**
     * Switches the game from a paused state to an un-paused state or vice visa
     */
    public void togglePause() {
        paused = !paused;

        // Set the loops to do work
        COLLISION_BOARD.setPaused(paused);
        GAME_LOOP.setPaused(paused);
        INFLUENCE_MAP.setPaused(paused);
    }

    public void shiftView(Vector2D shiftAmount) {
        Main.viewLocation.add(shiftAmount);
    }

    private void initTWL() {
        try {
            renderer = new LWJGLRenderer();
            themeManager = ThemeManager.createThemeManager(new File("Content/UITheme/simple.xml").toURL(), renderer);

        } catch (LWJGLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        gui = new GUI(this, renderer);
        gui.applyTheme(themeManager);
    }

    @Override
    protected void layout() {
        button.setPosition(50, 50);
        button.setSize(100, 50);
        button.adjustSize();
    }
}
