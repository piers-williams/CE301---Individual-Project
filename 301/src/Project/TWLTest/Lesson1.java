package Project.TWLTest;

import de.matthiasmann.twl.Button;
import de.matthiasmann.twl.GUI;
import de.matthiasmann.twl.Widget;
import de.matthiasmann.twl.renderer.lwjgl.LWJGLRenderer;
import de.matthiasmann.twl.theme.ThemeManager;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;

import java.io.File;
import java.io.IOException;
import java.net.URL;

/**
 *
 */
public class Lesson1 extends Widget {

    private static final int WIDTH = 800, HEIGHT = 600;

    private GUI gui;
    private LWJGLRenderer renderer;
    private ThemeManager themeManager;
    private Button button;

    public Lesson1() {
        setUpLWJGL();
        setUpOpenGL();
        initTWL();
        createButton();
    }

    private void createButton() {
        button = new Button("Epic Button");
        button.setTheme("button");
        add(button);
    }

    private void initTWL() {
        try {
            renderer = new LWJGLRenderer();

//            themeManager = ThemeManager.createThemeManager(getClass().getResource("Content/UITheme/Eforen.xml"), renderer);
            themeManager = ThemeManager.createThemeManager(new File("Content/UITheme/Eforen.xml").toURL(), renderer);

        } catch (LWJGLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        gui = new GUI(this, renderer);
        gui.applyTheme(themeManager);
    }

    private void setUpLWJGL() {

        try {
            Display.setDisplayMode(new DisplayMode(WIDTH, HEIGHT));
            Display.setTitle("Lesson 1");
            Display.create();
        } catch (LWJGLException e) {
            e.printStackTrace();
        }

    }

    private void setUpOpenGL() {
        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glLoadIdentity();

        GL11.glViewport(0, 0, WIDTH, HEIGHT);
        GL11.glOrtho(0.0f, WIDTH, HEIGHT, 0.0f, -1.0f, 1.0f);

        GL11.glClearColor(0, 0, 0, 1);
    }

    @Override
    protected void layout() {
        button.setPosition(100, 100);
        button.setSize(100, 33);

    }

    private void gameLoop() {
        while (!Display.isCloseRequested()) {
            GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
            gui.update();
            Display.update();
        }
    }

    public static void main(String[] args) {
        new Lesson1().gameLoop();
    }
}
