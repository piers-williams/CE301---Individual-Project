package Learning.Towers.Entities;

import Learning.Towers.Behaviours.Movement.Flocking;
import Learning.Towers.Behaviours.Movement.Movement;
import Learning.Towers.Faction;
import Learning.Towers.Main;
import Learning.Towers.Utilities;

import java.awt.*;
import java.awt.geom.Point2D;

/**
 * Created with IntelliJ IDEA.
 * User: Piers
 * Date: 19/10/12
 * Time: 16:37
 * To change this template use File | Settings | File Templates.
 */
public class GroupedEntity extends Entity {

    protected Faction faction;

    public GroupedEntity(Faction faction, int width) {
        this(faction, width, 3);
    }

    public GroupedEntity(Faction faction, int width, double strength) {
        super(width, faction.getR(), faction.getG(), faction.getB(), strength);
        this.faction = faction;
        movementBehaviour = new Flocking();
    }


    public void update() {
        super.update();
    }





}
