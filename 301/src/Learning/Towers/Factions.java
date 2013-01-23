package Learning.Towers;


/**
 *
 */
public enum Factions {
    Nature(1, 1, 1),
    Red(1, 0, 0),
    Green(0, 1, 0)
    ;

    private float r, g, b;
    private Faction faction;

    private Factions(float r, float g, float b) {
        this.r = r;
        this.g = g;
        this.b = b;

        this.faction = new Faction(r, g, b);
    }

    public Faction getFaction() {
        return faction;
    }
}
