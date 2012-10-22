package Learning.LWJGL;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;

import java.awt.*;
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
    ArrayList<MovingSquare> squares;

    static int SCREEN_WIDTH = 1600;
    static int SCREEN_HEIGHT = 900;
    static int SQUARE_WIDTH = 4;
    static int SQUARE_COUNT = 4000;
    public Main() {
        try {
            Display.setDisplayMode(new DisplayMode(SCREEN_WIDTH, SCREEN_HEIGHT));
            Display.setFullscreen(true);
            Display.create();
        } catch (Exception e) {

        }

        squares = new ArrayList<MovingSquare>(SQUARE_COUNT);
        for (int i = 0; i < SQUARE_COUNT; i++) {
            squares.add(new MovingSquare(SQUARE_WIDTH));
        }
        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glLoadIdentity();
        GL11.glOrtho(0, SCREEN_WIDTH, 0, SCREEN_HEIGHT, 1, -1);
        GL11.glMatrixMode(GL11.GL_MODELVIEW);

        while (!Display.isCloseRequested()) {

            try {
                Thread.sleep(20);
            } catch (Exception e) {

            }
            GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
            for (int j = 0; j < squares.size(); j++) {
                MovingSquare s = squares.get(j);
                s.update();

                // Collision detection
                for (int i = j; i < squares.size(); i++) {
                    if (MovingSquare.collidesWith(s, squares.get(i))) {
                        MovingSquare.bounce(s, squares.get(i));
                    }
                }
                s.draw();
            }


            Display.update();
        }
        Display.destroy();
    }

    public static void main(String[] args) {
        new Main();
    }
}

class MovingSquare {
    int x, y;
    int width;
    int dX, dY;

    private static Random random = new Random();

    public MovingSquare(int width) {
        x = random.nextInt(Main.SCREEN_WIDTH);
        y = random.nextInt(Main.SCREEN_HEIGHT);

        do {
            dX = random.nextInt(3) - 1;
            dY = random.nextInt(3) - 1;
        } while (dX == 0 && dY == 0);

        this.width = width;
    }

    public void bounce() {
        dX *= -1;
        dY *= -1;
    }

    public void update() {
        x += dX;
        y += dY;

        // Bounce
        if (x < 0 || x > Main.SCREEN_WIDTH) dX *= -1;
        if (y < 0 || y > Main.SCREEN_HEIGHT) dY *= -1;
    }
    public void draw() {
        GL11.glBegin(GL11.GL_QUADS);
        GL11.glVertex2f(x - width / 2, y - width / 2);
        GL11.glVertex2f(x + width / 2, y - width / 2);
        GL11.glVertex2f(x + width / 2, y + width / 2);
        GL11.glVertex2f(x - width / 2, y + width / 2);
        GL11.glEnd();
    }

    public static boolean collidesWith(MovingSquare first, MovingSquare second) {
        if (first.equals(second)) return false;

        double distance = (first.x - second.x) * (first.x - second.x) + (first.y - second.y)*(first.y - second.y);
        return (distance < first.width * first.width);
    }

    public static void bounce(MovingSquare first, MovingSquare second) {
        int dX, dY;
        dX = first.dX;
        dY = first.dY;
        first.dX = second.dX;
        first.dY = second.dY;
        second.dX = dX;
        second.dY = dY;
    }
}
