package Project.Game;


/**
 * Must only be one non intelligent Faction
 */
public enum Factions {
    Nature(1, 1, 1, Main.VECTOR2D_SOURCE.getVector(450, 450), false),
    Red(1, 0, 0, Main.VECTOR2D_SOURCE.getVector(1350, 1350), true),
    //    Green(0, 1, 0, Main.VECTOR2D_SOURCE.getVector(450, 1350), true),
    Blue(0, 0, 1, Main.VECTOR2D_SOURCE.getVector(1350, 450), true);

    // W-B
    // G-R
    private Faction faction;
    private Boolean intelligent;

    private Factions(float r, float g, float b, Vector2D location, boolean intelligent) {
        this.faction = new Faction(r, g, b, location, intelligent);
        this.intelligent = intelligent;
        if (!intelligent) Main.HUMAN_FACTION = faction;
    }

    public Faction getFaction() {
        return faction;
    }

    public Boolean hasIntelligence() {
        return intelligent;
    }
}
