package Learning.InfluenceMaps;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created with IntelliJ IDEA.
 * User: Piers
 * Date: 23/10/12
 * Time: 11:17
 * To change this template use File | Settings | File Templates.
 */
public class GameLoop implements Runnable {

    private ArrayList<Entity> entities;
    public final Object _entities = new Object();

    private ArrayList<Faction> factions;
    private final Object _factions = new Object();

    private ArrayList<Entity> addEntities;

    private int tickDelay;
    private boolean paused;

    public GameLoop() {
        entities = new ArrayList<Entity>();
        addEntities = new ArrayList<Entity>();
        factions = new ArrayList<Faction>();
        tickDelay = 50;
        paused = true;

        ShootingEntity.SQUARES = entities;
    }

    public GameLoop(int tickDelay) {
        this();
        this.tickDelay = tickDelay;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(tickDelay);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (!paused) {
                // Run the removal run
                synchronized (_entities) {
                    Iterator<Entity> iterator = entities.iterator();
                    while (iterator.hasNext()) {
                        Entity entity = iterator.next();
                        if (!entity.alive) {
                            iterator.remove();
                        }
                    }
                }
                // Addition run
                synchronized (_entities) {
                    entities.addAll(addEntities);
                    addEntities.clear();
                }

                // Update run
                synchronized (_entities) {
                    for (Entity entity : entities) entity.update();
                }

                // Faction update
                synchronized (_factions) {
                    for (Faction faction : factions) faction.update();
                }
            }
        }
    }

    public void addEntity(Entity entity) {
        addEntities.add(entity);
    }


    // Not thread safe - only add factions when thread paused
    public void addFaction(Faction faction) {
        factions.add(faction);
    }

    public void setPaused(boolean paused) {
        this.paused = paused;
    }

    public ArrayList<Entity> getEntities() {
        return entities;
    }

    public void draw() {
        synchronized (_entities) {
            for (Entity entity : entities) {
                entity.draw();
            }
        }
        synchronized (_factions) {
            for (Faction faction : factions) {
                faction.draw();
            }
        }
    }
}
