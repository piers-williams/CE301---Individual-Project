package Project.Game;

import Project.Game.AI.Commander;
import Project.Game.AI.SPL.SPLQueue;
import Project.Game.Behaviours.Constructive.Construction;
import Project.Game.Entities.Entity;
import Project.Game.Entities.EntityFactory;
import Project.Game.Entities.Meta.Group;
import Project.Game.Resource.ResourceGenerator;
import Project.Game.Resource.ResourcePool;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;

/**
 * User: Piers
 * Date: 19/10/12
 * Time: 16:42
 */

public class Faction {
    private float r, g, b;

    protected Commander commander;

    private Vector2D location;

    private SPLQueue splQueue;

    protected Boolean intelligent;

    // The resource pool for the faction
    private ResourcePool resourcePool;

    private GroupHandler groupHandler;

    protected Faction(float r, float g, float b, Vector2D startLocation, Boolean intelligent) {
        this.r = r;
        this.g = g;
        this.b = b;
        this.intelligent = intelligent;

        // Set up initial resource generator
        resourcePool = new ResourcePool();
        resourcePool.register(new ResourceGenerator(resourcePool, 10));

        if (intelligent) commander = new Commander(this);


        Entity base = EntityFactory.getBase(this, startLocation);
//        Group newGroup = new Group(r, g, b, base.getConstructionBehaviour().getSpawnPoint(), 5, this);
//        baseGroup.put(base.getConstructionBehaviour(), newGroup);
//
        Main.GAME_LOOP.addEntity(base);
//        Main.GAME_LOOP.addEntity(newGroup);

        this.location = startLocation;

        splQueue = new SPLQueue();

        groupHandler = new GroupHandler(this);
    }

    public void makeEntity(Vector2D location, Construction base) {
        makeEntity(location.x, location.y, base);
    }

    public void makeEntity(double x, double y, Construction base) {
        groupHandler.makeEntity(x, y, base);
    }

    public void addEntity(Entity entity) {

    }

    public void addConstruction(Construction construction, Vector2D spawnPoint) {
        groupHandler.addConstruction(construction, spawnPoint);
    }

    public void update() {
        // sort out resource for this tick
        resourcePool.update();
        // if necessary update the AI
        if (intelligent) commander.update();
    }

    public float getR() {
        return r;
    }

    public float getG() {
        return g;
    }

    public float getB() {
        return b;
    }

    public Vector2D getLocation() {
        return location;
    }

    public SPLQueue getSplQueue() {
        return splQueue;
    }

    public ResourcePool getResourcePool() {
        return resourcePool;
    }

    public Object getService(String service) {
        switch (service) {
            case "Production Difference":
                return resourcePool.getPercentage();
        }
        return "";
    }


    @Override
    public String toString() {
        return "Faction: R:" + Float.toString(r) + "G:" + Float.toString(g) + "B:" + Float.toString(b);
    }
}

class GroupHandler {

    // List of full groups in the game
    private ArrayList<Group> groups;
    // List of partial groups and the Construction responsible for them
    private Dictionary<Construction, Group> baseGroup;
    // Faction we are tied to
    private Faction faction;


    public GroupHandler(Faction faction) {
        this.faction = faction;
        groups = new ArrayList<>();
        baseGroup = new Hashtable<>();

    }

    public void addConstruction(Construction construction, Vector2D spawnPoint) {
        if (baseGroup.get(construction) == null) {
            Group group = new Group(faction.getR(), faction.getG(), faction.getB(), new Vector2D(spawnPoint, true), 5, faction);
            Main.GAME_LOOP.addEntity(group);
            baseGroup.put(construction, group);
        }
    }

    public void makeEntity(double x, double y, Construction base) {
        // Create new entity
        Entity entity = EntityFactory.getGroupedEntity(faction, baseGroup.get(base), new Vector2D(x, y, true), 2);

        // Add entity to group
        baseGroup.get(base).addEntity(entity);
        // register entity with game
        Main.GAME_LOOP.addEntity(entity);

        // House keeping on the groups
        if (baseGroup.get(base).isFull()) {
            Group newGroup = new Group(faction.getR(), faction.getG(), faction.getB(), new Vector2D(base.getSpawnPoint(), true), 5, faction);
            groups.add(baseGroup.get(base));
            baseGroup.get(base).switchToWander();
            if (faction.intelligent) faction.commander.groupFilled(baseGroup.get(base));
            baseGroup.put(base, newGroup);
            Main.GAME_LOOP.addEntity(newGroup);
        }
    }

}



