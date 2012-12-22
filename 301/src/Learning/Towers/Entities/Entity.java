package Learning.Towers.Entities;

import Learning.Towers.Behaviours.Collision.Collision;
import Learning.Towers.Behaviours.Drawing.Drawing;
import Learning.Towers.Behaviours.Drawing.SimpleQuad;
import Learning.Towers.Behaviours.Influence.Influence;
import Learning.Towers.Behaviours.Movement.Movement;
import Learning.Towers.Behaviours.Movement.Static;
import Learning.Towers.Influence.InfluenceGrid;
import Learning.Towers.Main;
import Learning.Towers.Vector2D;

import java.util.Random;

/**
 * Entity class
 */
public class Entity {
    // Location
    @Deprecated
    public double x, y;
    @Deprecated
    protected int width;
    @Deprecated
    double dX, dY;

    protected float r, g, b;

    protected boolean alive = true;

    private static Random random = new Random();

    // Number used to generate the influence grid
    @Deprecated
    private double influenceStrength;

    // Grid to be used for generating influence maps
    @Deprecated
    private InfluenceGrid influenceGrid;

    // TODO Strip out the functionality to fill these with useful things
    // Hopefully this section will replace most of the other code
    protected Movement movementBehaviour;
    protected Collision collisionBehaviour;
    protected Drawing drawingBehaviour;
    protected Influence influenceBehaviour;

    // TODO Create factory methods for making Entities
    public Entity(int width) {
        this(width, random.nextFloat(), random.nextFloat(), random.nextFloat(), 1);
    }

    public Entity(int width, float r, float g, float b) {
        this(width, r, g, b, 1);
    }

    public Entity(int width, float r, float g, float b, double strength) {
        this.r = r;
        this.g = g;
        this.b = b;

        setUpInfluence(strength);

        movementBehaviour = new Static(new Vector2D(random.nextInt(Main.MAP_WIDTH), random.nextInt(Main.MAP_HEIGHT)));
        drawingBehaviour = new SimpleQuad(width, r, g, b, movementBehaviour);
    }

    public void update() {
        movementBehaviour.update();
    }

    public void draw() {
        if (alive) {
            drawingBehaviour.draw();
        }
    }

    public void kill() {
        alive = false;
    }

    public boolean isAlive() {
        return alive;
    }

    public double getX() {
        return movementBehaviour.getLocation().getX();
    }

    public double getY() {
        return movementBehaviour.getLocation().getY();
    }

    public static boolean collidesWith(Entity first, Entity second) {
        if (first.equals(second)) return false;

        double distance = (first.x - second.x) * (first.x - second.x) + (first.y - second.y) * (first.y - second.y);
        return (distance < first.width * first.width);
    }

    public static void bounce(Entity first, Entity second) {
        double dX, dY;
        dX = first.dX;
        dY = first.dY;
        first.dX = second.dX;
        first.dY = second.dY;
        second.dX = dX;
        second.dY = dY;

        double distance = (first.x - second.x) * (first.x - second.x) + (first.y - second.y) * (first.y - second.y);
        double ang = Math.atan2(second.y - first.y, second.x - first.x);
        double mov = first.width - Math.sqrt(distance);
        mov /= 2;

        first.x -= Math.cos(ang) * mov;
        first.y -= Math.sin(ang) * mov;
        ang -= Math.PI;
        second.x -= Math.cos(ang) * mov;
        second.y -= Math.sin(ang) * mov;
    }

    public final double getIS() {
        return influenceStrength;
    }

    private void setUpInfluence(double strength) {
        this.influenceStrength = strength;
        this.influenceGrid = InfluenceGrid.createGrid(this, 5);
    }

    public InfluenceGrid getInfluenceGrid() {
        return influenceGrid;
    }
}
