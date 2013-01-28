package Project.Game;

import java.util.Random;

/**
 * Created with IntelliJ IDEA.
 * User: Piers
 * Date: 19/10/12
 * Time: 16:49
 */
public class Utilities {

    private final static Random random = new Random();

    public static double manhattanDistance(double x, double y, double oX, double oY) {
        return ((x - oX) * (x - oX)) + ((y - oY) * (y - oY));
    }

    public static double distance(double x, double y, double oX, double oY) {
        return Math.sqrt(Utilities.manhattanDistance(x, y, oX, oY));
    }

    public static Vector2D randomLocation() {
        return randomLocation(0, 0);
    }

    public static Vector2D randomLocation(int margin) {
        return randomLocation(margin, margin);
    }

    public static Vector2D randomLocation(int xMargin, int yMargin) {
        return new Vector2D(
                random.nextInt((Main.MAP_WIDTH - (2 * xMargin)) + xMargin),
                random.nextInt((Main.MAP_HEIGHT - (2 * yMargin)) + yMargin)
        );
    }
}
