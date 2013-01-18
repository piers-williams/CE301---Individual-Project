package Learning.Towers.Entities;

import Learning.Towers.*;
import Learning.Towers.Behaviours.Collision.SimpleCollision;
import Learning.Towers.Behaviours.Constructive.SimpleConstruction;
import Learning.Towers.Behaviours.Drawing.SimpleQuad;
import Learning.Towers.Behaviours.Influence.SimpleInfluence;
import Learning.Towers.Behaviours.Movement.Flocking;
import Learning.Towers.Behaviours.Movement.Static;
import Learning.Towers.Behaviours.Movement.Wandering;
import Learning.Towers.Entities.Meta.Group;

/**
 *
 */
public class EntityFactory {

    public static Entity getNaturalEntity(){
        Entity entity = new Entity();
        entity.r = 1;
        entity.g = 1;
        entity.b = 1;

        entity.faction = Factions.Nature.getFaction();

        entity.movementBehaviour = new Wandering(entity, Utilities.randomLocation(50));
        entity.drawingBehaviour = new SimpleQuad(entity, Main.SQUARE_WIDTH, entity.r, entity.g, entity.b);
        entity.influenceBehaviour = new SimpleInfluence(entity, 3, 1);
        entity.collisionBehaviour = new SimpleCollision(entity, Main.SQUARE_WIDTH);

        return entity;
    }

    public static Entity getGroupedEntity(Faction faction, Group group, Vector2D location, double strength) {
        Entity entity = new Entity();

        setColour(entity, faction);
        entity.faction = faction;
        entity.movementBehaviour = new Flocking(entity, location, group);
        entity.drawingBehaviour = new SimpleQuad(entity, Main.SQUARE_WIDTH, entity.r, entity.g, entity.b);
        entity.influenceBehaviour = new SimpleInfluence(entity, 7, strength);
        entity.collisionBehaviour = new SimpleCollision(entity, Main.SQUARE_WIDTH);

        return entity;
    }

    public static Entity getBaseEntity(Faction faction, Vector2D location, int width) {
        Entity entity = new Entity();

        setColour(entity, faction);
        entity.faction = faction;

        entity.movementBehaviour = new Static(entity, location);
        entity.drawingBehaviour = new SimpleQuad(entity, 26, entity.r, entity.g, entity.b);
        entity.influenceBehaviour = new SimpleInfluence(entity, 5, 0);
        entity.collisionBehaviour = new SimpleCollision(entity, 26);
        entity.constructionBehaviour = new SimpleConstruction(entity, faction);

        return entity;
    }

    // TODO get an offensive behaviour
    public static Entity getTower(Faction faction, Vector2D location){
        Entity entity = new Entity();

        setColour(entity, faction);
        entity.faction = faction;

        entity.movementBehaviour = new Static(entity, location);
        entity.drawingBehaviour = new SimpleQuad(entity, 20, entity.r, entity.g, entity.b);
        entity.influenceBehaviour = new SimpleInfluence(entity, 11, 10);
        entity.collisionBehaviour = new SimpleCollision(entity, 20);

        return entity;
    }

    private static void setColour(Entity entity, Faction faction) {
        entity.r = faction.getR();
        entity.g = faction.getG();
        entity.b = faction.getB();
    }
}
