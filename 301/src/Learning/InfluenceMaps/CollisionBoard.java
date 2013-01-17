package Learning.InfluenceMaps;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created with IntelliJ IDEA.
 * User: Piers
 * Date: 18/10/12
 * Time: 22:05
 */
public class CollisionBoard implements Runnable {

    private int cellSize;

    HashMap<Vector2D, ArrayList<Entity>> cellEntities;
    final Object _cellEntities = new Object();

    ArrayList<Entity> addEntities, deleteEntities;
    ArrayList<EntityCellPair> moveEntities;
    final Object _moveEntities = new Object();

    // Thread stuff
    boolean running = true, paused = true;
    int tickDelay;

    public CollisionBoard(int cellSize) {
        this.cellSize = cellSize;

        cellEntities = new HashMap<>();
        moveEntities = new ArrayList<>();
        addEntities = new ArrayList<>();
        deleteEntities = new ArrayList<>();

        tickDelay = 10;
    }

    public Vector2D getPoint(double x, double y) {
        return new Vector2D(x / cellSize, y / cellSize);
    }

    public Vector2D getPoint(Entity entity) {
        return getPoint(entity.x, entity.y);
    }

    public void setPaused(boolean paused) {
        this.paused = paused;
    }

    @Override
    public void run() {

//        long time = System.currentTimeMillis();
//        long oldTime = time;
        while (running) {
            try {
                Thread.sleep(tickDelay);
            } catch (InterruptedException ie) {

            }

//            time = System.currentTimeMillis();
//            System.out.println(1000/(time - oldTime) + ":FPS");
//            oldTime = time;

            if (!paused) {
                // Add entities that are due
                addEntitiesToMap();

                // Move entities that are due
                moveEntitiesToMap();

                // Remove entities that are due
                deleteEntitiesToMap();

                // Run the collision detection
                runCollisionDetection();
            }
        }
    }

    public void addEntity(Entity entity) {
        addEntities.add(entity);
    }

    public void moveEntity(Entity entity, Vector2D oldCell) {
        synchronized (_moveEntities) {
            moveEntities.add(new EntityCellPair(entity, oldCell));
        }
    }

    @SuppressWarnings("UnusedDeclaration")
    public void deleteEntity(Entity entity) {
        deleteEntities.add(entity);
    }

    private void addEntitiesToMap() {
        for (Entity entity : addEntities) {
            Vector2D cell = getPoint(entity);
            addEntityToMap(cell, entity);
        }
        addEntities.clear();
    }

    private void moveEntitiesToMap() {
        synchronized (_moveEntities) {
            for (EntityCellPair entityPair : moveEntities) {
                removeEntityFromMap(entityPair.cell, entityPair.entity);
                Vector2D cell = getPoint(entityPair.entity);
                addEntityToMap(cell, entityPair.entity);
            }
            moveEntities.clear();
        }
    }

    private void deleteEntitiesToMap() {

        for (Entity entity : deleteEntities) {
            Vector2D cell = getPoint(entity);
            removeEntityFromMap(cell, entity);
        }
        deleteEntities.clear();
    }

    private void runCollisionDetection() {
        for (Vector2D cell : cellEntities.keySet()) {
            synchronized (_cellEntities) {
                ArrayList<Entity> tl = cellEntities.get(cell);

                ArrayList<Entity> entities = new ArrayList<>(tl);
                if (cellEntities.containsKey(new Vector2D(cell.x + 1, cell.y))) {
                    entities.addAll(cellEntities.get(new Vector2D(cell.x + 1, cell.y)));
                }
                if (cellEntities.containsKey(new Vector2D(cell.x, cell.y + 1))) {
                    entities.addAll(cellEntities.get(new Vector2D(cell.x, cell.y + 1)));
                }
                if (cellEntities.containsKey(new Vector2D(cell.x + 1, cell.y + 1))) {
                    entities.addAll(cellEntities.get(new Vector2D(cell.x + 1, cell.y + 1)));
                }

                for (int i = 0; i < tl.size(); i++) {
                    for (int j = i + 1; j < entities.size(); j++) {
                        if (Entity.collidesWith(entities.get(i), entities.get(j))) {
                            Entity.bounce(entities.get(i), entities.get(j));
                        }
                    }
                }
            }
        }
    }

    private void addEntityToMap(Vector2D point, Entity entity) {
        synchronized (_cellEntities) {
            if (!cellEntities.containsKey(point)) {
                cellEntities.put(point, new ArrayList<Entity>());
            }
            cellEntities.get(point).add(entity);
        } // End synchronized
    }

    private void removeEntityFromMap(Vector2D point, Entity entity) {
        synchronized (_cellEntities) {
            if (cellEntities.containsKey(point)) {
                cellEntities.get(point).remove(entity);
            }
        } // End synchronized
    }
}

final class EntityCellPair {
    final Entity entity;
    final Vector2D cell;

    EntityCellPair(Entity entity, Vector2D cell) {
        this.entity = entity;
        this.cell = cell;
    }
}
