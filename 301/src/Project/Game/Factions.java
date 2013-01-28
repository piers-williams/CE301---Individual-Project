package Project.Game;


/**
 *
 */
public enum Factions {
    Nature(1, 1, 1, new Vector2D(100, 100)),
    Red(1, 0, 0, new Vector2D(1500, 800)),
//    Green(0, 1, 0)
    ;

    private float r, g, b;
    private Faction faction;

    private Factions(float r, float g, float b, Vector2D location) {
        this.r = r;
        this.g = g;
        this.b = b;

        this.faction = new Faction(r, g, b, location);
    }

    public Faction getFaction() {
        return faction;
    }
}
