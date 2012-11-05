package Learning.KeyBoardHandling;

/**
 * Created with IntelliJ IDEA.
 * User: Piers
 * Date: 26/10/12
 * Time: 13:55
 * To change this template use File | Settings | File Templates.
 */
public class Base extends GroupedEntity {

    static final int MAX_RESOURCE = 30;
    int resource = MAX_RESOURCE;

    // Awesomeness
    public Base(Faction faction, int width) {
        super(faction, width);
    }

    public void update() {
        resource--;
        if (resource == 0) {
            resource = MAX_RESOURCE;
            faction.makeEntity(x + 25, y + 25);
        }
    }
}
