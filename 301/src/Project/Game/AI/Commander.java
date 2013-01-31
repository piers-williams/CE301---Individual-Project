package Project.Game.AI;

import Project.Game.AI.SPL.Orders.AttackOrder;
import Project.Game.Entities.Entity;
import Project.Game.Entities.EntityFactory;
import Project.Game.Entities.Meta.Group;
import Project.Game.*;

/**
 * Primary AI class
 * <p/>
 * Gives the actual orders
 */
public class Commander {

    private Faction faction;

    private AttackFinder attackFinder;
    private DefenseFinder defenseFinder;

    public Commander(Faction faction) {
        this.faction = faction;

        attackFinder = new AttackFinder(this, 30);
        defenseFinder = new DefenseFinder(this, 30);
    }

    public void update() {
        attackFinder.update();
        defenseFinder.update();
    }

    public Faction getFaction() {
        return faction;
    }

    public void groupFilled(Group group) {
        System.out.println("Group filled");
        if(faction.getSplQueue().hasAttackOrder()){
            group.switchToFollow((Vector2D) faction.getSplQueue().getNextAttackOrder().getArguments()[0]);
        }
    }

    public void buildTower() {
        if (defenseFinder.nextTarget != null) {
            Entity tower = EntityFactory.getTower(faction, defenseFinder.nextTarget);
            Main.GAME_LOOP.addEntity(tower);

            defenseFinder.nextTarget = null;
        }
    }
}

abstract class TacticalAnalysis {
    private int tickFrequency, currentTick;

    protected Commander commander;

    public TacticalAnalysis(Commander commander, int tickFrequency) {
        this.commander = commander;
        this.tickFrequency = tickFrequency;
    }

    public final void update() {
        currentTick = (currentTick == tickFrequency) ? 0 : currentTick + 1;
        if (currentTick == 0) updateSpecialisation();
    }

    public abstract void updateSpecialisation();
}

/**
 * This will churn out attack orders
 * <p/>
 * This one is global, they will go on the SPL queue
 */
class AttackFinder extends TacticalAnalysis {
    double[][] enemyInfluence;

    Vector2D nextTarget;

    AttackFinder(Commander commander, int tickFrequency) {
        super(commander, tickFrequency);
    }

    public void updateSpecialisation() {
        enemyInfluence = Main.INFLUENCE_MAP.getEnemyInfluence(commander.getFaction());

        int lowX = 0, lowY = 0;
        boolean foundSomewhere = false;
        for (int x = 0; x < enemyInfluence.length; x++) {
            for (int y = 0; y < enemyInfluence[x].length; y++) {
                if (enemyInfluence[x][y] != 0) {
                    if (!foundSomewhere) {
                        foundSomewhere = true;
                        lowX = x;
                        lowY = y;
                    } else if (enemyInfluence[x][y] < enemyInfluence[lowX][lowY]) {
                        lowX = x;
                        lowY = y;
                    }
                }
            }
        }

        // It is possible not to find anything at all
        if (foundSomewhere) {
            nextTarget = new Vector2D(lowX * Main.INFLUENCE_MAP.getCellSize(), lowY * Main.INFLUENCE_MAP.getCellSize());

            commander.getFaction().getSplQueue().addAttackOrder(
                    new AttackOrder(
                            new Object[] {new Vector2D(lowX * Main.INFLUENCE_MAP.getCellSize(), lowY * Main.INFLUENCE_MAP.getCellSize()), 10},
                            10,
                            "Assassinate"
                    )
            );
        }
    }
}

/**
 * This class will locate the next location to build a tower
 * Should be rebuilt so that it operates on a single base
 * <p/>
 * Could go back to actually following plans
 */
class DefenseFinder extends TacticalAnalysis {
    double[][] influence;

    Vector2D nextTarget;

    DefenseFinder(Commander commander, int tickFrequency) {
        super(commander, tickFrequency);
    }

    public void updateSpecialisation() {
        influence = Main.INFLUENCE_MAP.getInfluence(commander.getFaction());

        int lowX = 0, lowY = 0;
        boolean foundSomewhere = false;
        for (int x = 0; x < influence.length; x++) {
            for (int y = 0; y < influence[x].length; y++) {
                if (influence[x][y] != 0) {
                    if (!foundSomewhere) {
                        foundSomewhere = true;
                        lowX = x;
                        lowY = y;
                    }
                    if (cost(x, y) < cost(lowX, lowY)) {
                        if (commander.getFaction() == Factions.Nature.getFaction()) {
                            System.out.println("Cost: " + cost(x, y));
                            System.out.println("Lowest Cost: " + cost(lowX, lowY));
                            System.out.println("Influence: " + influence[x][y]);
                        }
                        lowX = x;
                        lowY = y;
                    }
                }
            }
        }

        if (foundSomewhere) {
            nextTarget = new Vector2D(lowX * Main.INFLUENCE_MAP.getCellSize(), lowY * Main.INFLUENCE_MAP.getCellSize());

            System.out.println("Found somewhere");
            System.out.println("Location: " + nextTarget);
            System.out.println("Value: " + influence[lowX][lowY]);

            System.out.println();
        }
    }

    private double cost(int x, int y) {
        double cost = Utilities.distance(x * Main.INFLUENCE_MAP.getCellSize(), y * Main.INFLUENCE_MAP.getCellSize(), commander.getFaction().getLocation().x, commander.getFaction().getLocation().y);
        cost += influence[x][y] * 120;

        return cost;
    }
}
