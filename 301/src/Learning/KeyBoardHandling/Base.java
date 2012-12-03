package Learning.KeyBoardHandling;

public class Base extends GroupedEntity {

    static final int MAX_RESOURCE = 30;
    int resource = MAX_RESOURCE;

    // Awesomeness
    public Base(Faction faction, int width) {
        super(faction, width, 20);
    }

    public void update() {
        resource--;
        if (resource == 0) {
            resource = MAX_RESOURCE;
            faction.makeEntity(x + 25, y + 25);
        }
    }
}
