package Project.Game;


/**
 *
 */
public enum Factions {
    Nature(1, 1, 1, new Vector2D(450, 450)),
    Red(1, 0, 0, new Vector2D(1350, 1350)),
//    Green(0, 1, 0, new Vector2D(450, 1350)),
//    Blue(0, 0, 1, new Vector2D(1350, 450))
    ;
    private Faction faction;

    private Factions(float r, float g, float b, Vector2D location) {
        this.faction = new Faction(r, g, b, location);
    }

    public Faction getFaction() {
        return faction;
    }
}
