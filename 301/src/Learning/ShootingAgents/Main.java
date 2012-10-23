package Learning.ShootingAgents;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;

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


    ArrayList<Group> groups;

    static int SCREEN_WIDTH = 1600;
    static int SCREEN_HEIGHT = 900;

    static int MAP_WIDTH = 1600;
    static int MAP_HEIGHT = 900;
    static int SQUARE_WIDTH = 6;
    static int SQUARE_COUNT = 200;
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
        Entity.BOARD = new CollisionBoard(Main.MAP_WIDTH, Main.MAP_HEIGHT, 75);

        Thread thread = new Thread(Entity.BOARD);
        thread.setDaemon(true);
        thread.start();
        /**
         * Set up the squares
         */
        squares = new ArrayList<Entity>(SQUARE_COUNT);
        for (int i = 0; i < SQUARE_COUNT; i++) {
            // all grey
            squares.add(new Entity(SQUARE_WIDTH, 0.5f, 0.5f, 0.5f));
        }

        ShootingEntity.SQUARES = squares;
        groups = new ArrayList<Group>();
        groups.add(new Group(0, 1, 0, 0));
        groups.add(new Group(1, 0, 1, 0));
        groups.add(new Group(2, 0, 0, 1));
        groups.add(new Group(3, 1, 0, 1));
        groups.add(new Group(4, 0, 1, 1));
        groups.add(new Group(5, 1, 1, 0));
        groups.add(new Group(6, 0.25f, 0.75f, 0.25f));
        groups.add(new Group(7, 0.75f, 0.25f, 0.25f));
        groups.add(new Group(8, 0.25f, 0.25f, 0.75f));

        Random random = new Random();
        for(Group group : groups){
            for(int i = 0; i < random.nextInt(30) + 20; i++){
                GroupedEntity entity = new ShootingEntity(group, SQUARE_WIDTH);
                group.addEntity(entity);
                squares.add(entity);
            }
        }

        System.out.println(squares.size() > SQUARE_COUNT);
        Entity.BOARD.setPaused(false);
        /**
         * Main loop
         */
        while (!Display.isCloseRequested()) {
            try {
                Thread.sleep(20);
            } catch (Exception e) {

            }

            // Keyboard release
            if(Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)){
                break;
            }
            if(Keyboard.isKeyDown(Keyboard.KEY_A)) addMoreUnits();
            if(Keyboard.isKeyDown(Keyboard.KEY_C)) GroupedEntity.shooting = !GroupedEntity.shooting;
            if(Keyboard.isKeyDown(Keyboard.KEY_P)) addMorePrey();

            GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
            for (int j = 0; j < squares.size(); j++) {
                Entity entity = squares.get(j);
                entity.update();

                entity.draw();
            }

            for(Group group : groups){
                group.update();
                group.draw();
            }
            Display.update();
        }
        Display.destroy();
    }

    public static void main(String[] args) {
        new Main();
    }

    private void addMoreUnits(){
        Random random = new Random();
        for(Group group : groups){
            for(int i = 0; i < random.nextInt(5) + 20; i++){
                GroupedEntity entity = new ShootingEntity(group, SQUARE_WIDTH);
                group.addEntity(entity);
                squares.add(entity);
            }
        }
    }

    private void addMorePrey(){
        for (int i = 0; i < 250; i++) {
            // all grey
            squares.add(new Entity(SQUARE_WIDTH, 0.5f, 0.5f, 0.5f));
        }
    }
}
