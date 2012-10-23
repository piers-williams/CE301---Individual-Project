package Learning.ShootingEachOtherAgents;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created with IntelliJ IDEA.
 * User: Piers
 * Date: 23/10/12
 * Time: 11:17
 * To change this template use File | Settings | File Templates.
 */
public class EntityList implements Runnable{

    private ArrayList<Entity> entities;
    private final Object _entities = new Object();

    private ArrayList<Entity> addEntities;

    private int tickDelay;

    public EntityList(){
        entities = new ArrayList<Entity>();
        addEntities = new ArrayList<Entity>();
        tickDelay = 50;
    }
    public EntityList(int tickDelay){
        this();
        this.tickDelay = tickDelay;
    }

    @Override
    public void run() {
        while(true){
            try {
                Thread.sleep(tickDelay);
            } catch (InterruptedException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }

            // Run the removal run
            synchronized (_entities){
                Iterator<Entity> iterator = entities.iterator();
                while(iterator.hasNext()){
                    Entity entity = iterator.next();
                    if(!entity.alive){
                        iterator.remove();
                    }
                }
            }
            // Addition run
            synchronized (_entities){
                entities.addAll(addEntities);
            }
        }
    }

    public void addEntity(Entity entity){
        addEntities.add(entity);
    }
}
