package Project.Game;

import Project.Game.AI.Commander;
import Project.Game.AI.SPL.SPLQueue;
import Project.Game.Behaviours.Constructive.Construction;
import Project.Game.Entities.Entity;
import Project.Game.Entities.EntityFactory;
import Project.Game.Entities.Meta.Group;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;

/**
 * User: Piers
 * Date: 19/10/12
 * Time: 16:42
 */
public class Faction {
    private ArrayList<Group> groups;
    private Dictionary<Construction, Group> baseGroup;

    private float r, g, b;

    private Commander commander;

    private int maxResource = 10, resource = maxResource;

    private Vector2D location;

    private SPLQueue splQueue;

    protected Faction(float r, float g, float b, Vector2D startLocation) {
        this.r = r;
        this.g = g;
        this.b = b;

        commander = new Commander(this);

        groups = new ArrayList<>();
        baseGroup = new Hashtable<>();

        Entity base = EntityFactory.getBase(this, startLocation);
        Group newGroup = new Group(r, g, b, base.getConstructionBehaviour().getSpawnPoint(), 5, this);
        baseGroup.put(base.getConstructionBehaviour(), newGroup);

        Main.GAME_LOOP.addEntity(base);
        Main.GAME_LOOP.addEntity(newGroup);

        this.location = startLocation;

        splQueue = new SPLQueue();
    }

    public void makeEntity(Vector2D location, Construction base) {
        makeEntity(location.x, location.y, base);
    }

    public void makeEntity(double x, double y, Construction base) {
        // Create new entity
        Entity entity = EntityFactory.getGroupedEntity(this, baseGroup.get(base), new Vector2D(x, y), 2);

        // Add entity to group
        baseGroup.get(base).addEntity(entity);
        // register entity with game
        Main.GAME_LOOP.addEntity(entity);

        // House keeping on the groups
        if (baseGroup.get(base).isFull()) {
            Group newGroup = new Group(r, g, b, base.getSpawnPoint(), 5, this);
            groups.add(baseGroup.get(base));
            baseGroup.get(base).switchToWander();
            commander.groupFilled(baseGroup.get(base));
            baseGroup.put(base, newGroup);
            Main.GAME_LOOP.addEntity(newGroup);
        }
    }

    public void addConstruction(Construction construction, Vector2D spawnPoint) {
        if (baseGroup.get(construction) == null) {
            Group group = new Group(r, g, b, spawnPoint, 5, this);
            Main.GAME_LOOP.addEntity(group);
            baseGroup.put(construction, group);
        }
    }

    public void update() {
        commander.update();

        // Build towers
        resource--;
        if (resource == 0) {
            resource = maxResource;
            commander.buildTower();
        }
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
}



