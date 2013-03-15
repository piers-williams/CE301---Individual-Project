package Project.Game.Entities;

import Project.Game.Behaviours.Collision.SimpleCollision;
import Project.Game.Behaviours.Constructive.BlueprintConstruction;
import Project.Game.Behaviours.Constructive.UnitConstruction;
import Project.Game.Behaviours.Drawing.SimpleQuad;
import Project.Game.Behaviours.Influence.SimpleInfluence;
import Project.Game.Behaviours.Movement.Flocking;
import Project.Game.Behaviours.Movement.Static;
import Project.Game.Behaviours.Movement.Wandering;
import Project.Game.Behaviours.Offensive.SimpleWeapon;
import Project.Game.Behaviours.Resource.Resource;
import Project.Game.Buildings.MetaBuilding;
import Project.Game.Entities.Meta.Group;
import Project.Game.*;
import Project.Game.Registries.EntityRegistry;
import Project.Game.Resource.ResourceGenerator;
import Project.Game.Resource.ResourcePool;
import de.matthiasmann.twl.Button;

/**
 *
 */
public class EntityFactory {

    @SuppressWarnings("UnusedDeclaration")
    public static Entity getNaturalEntity() {
        Entity entity = new Entity();
        entity.r = 1;
        entity.g = 1;
        entity.b = 1;

        entity.faction = Factions.Nature.getFaction();

        entity.movementBehaviour = new Wandering(entity, Utilities.randomLocation(50));
        entity.drawingBehaviour = new SimpleQuad(entity, Main.SQUARE_WIDTH, entity.r, entity.g, entity.b);
        entity.influenceBehaviour = new SimpleInfluence(entity, 7, 1);
        entity.collisionBehaviour = new SimpleCollision(entity, Main.SQUARE_WIDTH);

        return entity;
    }

    public static Entity getGroupedEntity(Faction faction, Group group, Vector2D location, double strength) {
        Entity entity = new Entity();

        setColour(entity, faction);
        entity.faction = faction;
        entity.health = 30;
        entity.maxHealth = entity.health;
        entity.movementBehaviour = new Flocking(entity, location, group);
        entity.drawingBehaviour = new SimpleQuad(entity, Main.SQUARE_WIDTH, entity.r, entity.g, entity.b);
        entity.influenceBehaviour = new SimpleInfluence(entity, 7, strength);
        entity.collisionBehaviour = new SimpleCollision(entity, Main.SQUARE_WIDTH);
        entity.offensiveBehaviour = new SimpleWeapon(entity, 15, 5, 3 * 50);
        entity.setName(EntityRegistry.getNewName("ES"));
        Main.REGISTRY.add(entity);
        return entity;
    }

    public static Entity getBase(Faction faction, Vector2D location) {
        Entity entity = new Entity();

        setColour(entity, faction);
        entity.faction = faction;
        entity.movementBehaviour = new Static(entity, location);
        entity.drawingBehaviour = new SimpleQuad(entity, 40, entity.r, entity.g, entity.b);
        entity.influenceBehaviour = new SimpleInfluence(entity, 9, 1);
        entity.collisionBehaviour = new SimpleCollision(entity, 40);
        entity.constructionBehaviour = new BlueprintConstruction(faction, entity, faction.getResourcePool(), location, Main.BLUEPRINT_REGISTRY.get("Home Level 3"));
        entity.setName(EntityRegistry.getNewName("BS"));
        entity.label = new Button(entity.getName());
        Main.REGISTRY.add(entity);
        return entity;
    }

    public static Entity getBlueprint(Faction faction, Vector2D location, String blueprint) {
        Entity entity = new Entity();

        setColour(entity, faction);
        entity.faction = faction;
        entity.movementBehaviour = new Static(entity, location);
        entity.influenceBehaviour = new SimpleInfluence(entity, 5, 0.0);
        entity.constructionBehaviour = new BlueprintConstruction(faction, entity, faction.getResourcePool(), location, Main.BLUEPRINT_REGISTRY.get(blueprint));
        entity.setName(EntityRegistry.getNewName("BS"));
        entity.label = new Button(entity.getName());
        Main.REGISTRY.add(entity);
        return entity;
    }

    public static Entity getFakeEntity(Faction faction) {
        Entity entity = new Entity();
        setColour(entity, faction);
        entity.faction = faction;
        entity.movementBehaviour = new Static(entity, Utilities.randomLocation());
        return entity;
    }

    public static Entity getBuilding(final Faction faction, final Vector2D location, final MetaBuilding type) {

        final Entity entity = new Entity();
        setColour(entity, faction);
        entity.faction = faction;

        entity.health = type.getHealth();
        entity.maxHealth = entity.health;
        entity.movementBehaviour = new Static(entity, location);
        entity.drawingBehaviour = new SimpleQuad(entity, (int) type.getSize().x, entity.r, entity.g, entity.b);
        entity.influenceBehaviour = new SimpleInfluence(entity, type.getInfluence().getSize(), type.getInfluence().getStrength());
        entity.collisionBehaviour = new SimpleCollision(entity, (int) type.getSize().x);

        switch (type.getName()) {
            case "Tower":
                entity.offensiveBehaviour = new SimpleWeapon(entity, 100, 30, (int) (1.5 * 50));
                break;
            case "Construction":
                entity.constructionBehaviour = new UnitConstruction(entity, faction, faction.getResourcePool());
            case "Production":
                entity.resourceBehaviour = new Resource() {
                    ResourcePool pool = faction.getResourcePool();
                    ResourceGenerator generator = new ResourceGenerator(pool, type.getProductionPerTick());
                    boolean registered = false;

                    @Override
                    public void update() {
                        if (!registered) {
                            registered = true;
                            pool.register(generator);
                        }
                    }

                    @Override
                    public Entity getEntity() {
                        return entity;
                    }

                    @Override
                    public void deRegister() {
                        pool.deRegister(generator);
                    }
                };
                break;

        }

        return entity;
    }

    private static void setColour(Entity entity, Faction faction) {
        entity.r = faction.getR();
        entity.g = faction.getG();
        entity.b = faction.getB();
    }
}
