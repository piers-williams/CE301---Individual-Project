package Learning.CollisionDetectionImplementation;

import org.lwjgl.opencl.CL;
import org.lwjgl.opencl.CL10;
import org.lwjgl.opencl.CLDevice;
import org.lwjgl.opencl.CLPlatform;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;

import static org.lwjgl.opencl.CL10.*;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created with IntelliJ IDEA.
 * User: Piers
 * Date: 16/10/12
 * Time: 11:49
 * To change this template use File | Settings | File Templates.
 */
public class Main {
    ArrayList<Entity> squares;

    static int SCREEN_WIDTH = 1600;
    static int SCREEN_HEIGHT = 900;

    static int MAP_WIDTH = 1600;
    static int MAP_HEIGHT = 900;
    static int SQUARE_WIDTH = 40;
    static int SQUARE_COUNT = 100;
    static boolean FULL_SCREEN = true;

    public Main() {

        if(FULL_SCREEN) System.setProperty("org.lwjgl.opengl.Window.undecorated", "true");
        // Set up the display
        try {
            Display.setDisplayMode(new DisplayMode(SCREEN_WIDTH, SCREEN_HEIGHT));
            Display.setFullscreen(true);
            Display.create();

            GL11.glMatrixMode(GL11.GL_PROJECTION);
            GL11.glLoadIdentity();
            GL11.glOrtho(0, SCREEN_WIDTH, 0, SCREEN_HEIGHT, 1, -1);
            GL11.glMatrixMode(GL11.GL_MODELVIEW);
        } catch (Exception e) {

        }
        // Set the board
        Entity.BOARD = new CollisionBoard(Main.MAP_WIDTH, Main.MAP_HEIGHT, 100);

        Thread thread = new Thread(Entity.BOARD);
        thread.setDaemon(true);
        thread.start();
        /**
         * Set up the squares
         */
        squares = new ArrayList<Entity>(SQUARE_COUNT);
        for (int i = 0; i < SQUARE_COUNT; i++) {
            squares.add(new Entity(SQUARE_WIDTH));
        }
        Entity.BOARD.setPaused(false);
        /**
         * Main loop
         */
        while (!Display.isCloseRequested()) {
            try {
                Thread.sleep(20);
            } catch (Exception e) {

            }
            GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
            for (int j = 0; j < squares.size(); j++) {
                Entity entity = squares.get(j);
                entity.update();

                entity.draw();
            }
            Display.update();
        }
        Display.destroy();
    }

    public static void main(String[] args) {
        new Main();
    }
}
