package Learning.Towers.AI;

import Learning.Towers.Faction;
import Learning.Towers.Influence.InfluenceMap;

/**
 * Primary AI class
 * <p/>
 * Gives the actual orders
 */
public class Commander {

    private Faction faction;
    private InfluenceMap influence;

    private AttackFinder attackFinder;
    private DefenseFinder defenseFinder;

    public Commander(Faction faction){
        this.faction = faction;

        attackFinder = new AttackFinder(30);
        defenseFinder = new DefenseFinder(30);
    }

    private void update() {
        attackFinder.update();
        defenseFinder.update();
    }
}

abstract class TacticalAnalysis {
    private int tickFrequency, currentTick;

    private Commander commander;

    public TacticalAnalysis(int tickFrequency) {
        this.tickFrequency = tickFrequency;
    }

    public final void update() {
        currentTick = (currentTick == tickFrequency) ? 0 : currentTick + 1;
        updateSpecialisation();
    }

    public abstract void updateSpecialisation();
}

/**
 * This will churn out attack orders
 */
class AttackFinder extends TacticalAnalysis {

    AttackFinder(int tickFrequency) {
        super(tickFrequency);
    }

    public void updateSpecialisation() {

    }
}

/**
 * This class will locate the next location to build a tower
 */
class DefenseFinder extends TacticalAnalysis {

    DefenseFinder(int tickFrequency) {
        super(tickFrequency);
    }

    public void updateSpecialisation() {

    }
}
