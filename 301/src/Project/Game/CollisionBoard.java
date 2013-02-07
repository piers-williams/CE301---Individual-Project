package Project.Game;

import Project.Game.Behaviours.Collision.SimpleCollision;
import Project.Game.Entities.Entity;

import java.util.ArrayList;
import java.util.HashMap;

/**
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

    private ArrayList<Entity> collisionEntities, temporaryEntityHolding;

    private CachedVector2DSource vector2DSource;

    public CollisionBoard(int cellSize) {
        this.cellSize = cellSize;

        cellEntities = new HashMap<>();
        moveEntities = new ArrayList<>();
        addEntities = new ArrayList<>();
        deleteEntities = new ArrayList<>();

        collisionEntities = new ArrayList<>();
        temporaryEntityHolding = new ArrayList<>();

        tickDelay = 20;

        vector2DSource = Main.VECTOR2D_SOURCE;
    }

    public Vector2D getPoint(double x, double y) {
        return vector2DSource.getVector((int) x / cellSize, (int) y / cellSize);
    }

    public Vector2D getPoint(Entity entity) {
        return getPoint(entity.getX(), entity.getY());
    }

    public void setPaused(boolean paused) {
        this.paused = paused;
    }

    @Override
    public void run() {
        while (running) {
            try {
                Thread.sleep(tickDelay);
            } catch (InterruptedException ie) {

            }

            if (!paused) {
                // Add entities that are due
                addEntitiesToMap();

                // Move entities that are due
                moveEntitiesToMap();

                // Remove entities that are due
                deleteEntitiesToMap();

                // Run the collision detection
                runCollisionDetection();

//                System.out.println("Number of vectors: " + vector2DSource.numberOfVectors());
//                System.gc();
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
                temporaryEntityHolding.clear();
                temporaryEntityHolding.addAll(cellEntities.get(cell));

                collisionEntities.clear();
                collisionEntities.addAll(temporaryEntityHolding);

                if (cellEntities.containsKey(vector2DSource.getVector(cell.x + 1, cell.y))) {
                    collisionEntities.addAll(cellEntities.get(vector2DSource.getVector(cell.x + 1, cell.y)));
                }
                if (cellEntities.containsKey(vector2DSource.getVector(cell.x, cell.y + 1))) {
                    collisionEntities.addAll(cellEntities.get(vector2DSource.getVector(cell.x, cell.y + 1)));
                }
                if (cellEntities.containsKey(vector2DSource.getVector(cell.x + 1, cell.y + 1))) {
                    collisionEntities.addAll(cellEntities.get(vector2DSource.getVector(cell.x + 1, cell.y + 1)));
                }

                for (int i = 0; i < temporaryEntityHolding.size(); i++) {
                    for (int j = i + 1; j < collisionEntities.size(); j++) {
                        if (SimpleCollision.collidesWith(collisionEntities.get(i).getCollisionBehaviour(), collisionEntities.get(j).getCollisionBehaviour())) {
                            SimpleCollision.bounce(collisionEntities.get(i).getCollisionBehaviour(), collisionEntities.get(j).getCollisionBehaviour());
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


