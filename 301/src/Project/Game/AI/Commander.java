package Project.Game.AI;

import Project.Game.AI.ParticleSwarm.PlanningGrid;
import Project.Game.AI.SPL.Orders.AttackOrder;
import Project.Game.Entities.Meta.Group;
import Project.Game.Faction;
import Project.Game.Main;
import Project.Game.Vector2D;

/**
 * Primary AI class
 * <p/>
 * Co ordinates everything
 */
public class Commander {

    private Faction faction;

    private AttackFinder attackFinder;
    private NewBaseBuilder newBaseBuilder;

    private PlanningGrid planningGrid;

    public Commander(Faction faction) {
        this.faction = faction;

        attackFinder = new AttackFinder(this, 30);
        newBaseBuilder = new NewBaseBuilder(this, 400);
        planningGrid = new PlanningGrid(10, 50, 50, 400);
    }

    public void update() {
        attackFinder.update();
    }

    public Faction getFaction() {
        return faction;
    }

    public void groupFilled(Group group) {
        if (faction.getSplQueue().hasAttackOrder()) {
            group.switchToFollow((Vector2D) faction.getSplQueue().getNextAttackOrder().getArguments()[0]);
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
                            new Object[]{new Vector2D(lowX * Main.INFLUENCE_MAP.getCellSize(), lowY * Main.INFLUENCE_MAP.getCellSize()), 10},
                            10,
                            "Assassinate"
                    )
            );
        }
    }
}

class NewBaseBuilder extends TacticalAnalysis {

    NewBaseBuilder(Commander commander, int tickFrequency) {
        super(commander, tickFrequency);
    }

    @Override
    public void updateSpecialisation() {
        int percentage = commander.getFaction().getResourcePool().getPercentage();

        if (percentage > 120) {
            // Build something to introduce drains
        } else {
            // Improve economy

        }
    }
}

