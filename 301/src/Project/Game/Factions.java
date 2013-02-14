package Project.Game;


/**
 *
 */
public enum Factions {
    Nature(1, 1, 1, new Vector2D(450, 450), false),
    Red(1, 0, 0, new Vector2D(1350, 1350), true),
//    Green(0, 1, 0, new Vector2D(450, 1350), true),
//    Blue(0, 0, 1, new Vector2D(1350, 450), true)
    ;
    private Faction faction;
    private Boolean intelligent;

    private Factions(float r, float g, float b, Vector2D location, boolean intelligent) {
        this.faction = new Faction(r, g, b, location);
        this.intelligent = intelligent;
    }

    public Faction getFaction() {
        return faction;
    }

    public Boolean hasIntelligence() {
        return intelligent;
    }
}
