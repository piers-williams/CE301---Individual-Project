package Learning.Towers.Entities.Meta;

import Learning.Towers.Entities.Entity;

import java.util.ArrayList;

/**
 *  Group of entities
 */
public class Group {

    int x, y;
    int radius;

    ArrayList<Entity> entities;
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public ArrayList<Entity> getEntities() {
        return entities;
    }

    public int getRadius() {
        return radius;
    }
}
