package Learning.Bases;

/**
 * Created with IntelliJ IDEA.
 * User: Piers
 * Date: 19/10/12
 * Time: 16:49
 * To change this template use File | Settings | File Templates.
 */
public class Utilities {

    public static double manhattanDistance(double x, double y, double oX, double oY) {
        return ((x - oX) * (x - oX)) + ((y - oY) * (y - oY));
    }

    public static double distance(double x, double y, double oX, double oY) {
        return Math.sqrt(Utilities.manhattanDistance(x, y, oX, oY));
    }
}
