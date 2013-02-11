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

import java.io.IOException;

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

    private void createButton(){
        button = new Button("Epic Button");
        button.setTheme("button");
        add(button);
    }

    private void initTWL() {
        try {
            renderer = new LWJGLRenderer();

            themeManager = ThemeManager.createThemeManager(getClass().getResource("lesson1.xml"), renderer);

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
}
