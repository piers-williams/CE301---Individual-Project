package Learning.InfluenceMaps.Influence;

import Learning.InfluenceMaps.Entity;
import Learning.InfluenceMaps.GameLoop;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: Piers
 * Date: 29/10/12
 * Time: 08:56
 * To change this template use File | Settings | File Templates.
 */
public class InfluenceMap {

    private double[][] influence;

    private int width, height, cellSize;

    private GameLoop loop;

    public InfluenceMap(int width, int height, int cellSize) {
        this.width = width;
        this.height = height;
        this.cellSize = cellSize;

        influence = new double[width][height];
    }

    public void update(){
        influence = new double[width][height];
        for(Entity entity : loop.entities){

        }
    }

    public void draw(){

    }

    public void setEntities(ArrayList<Entity> entities) {
        this.entities = entities;
    }
}
