package dk.sdu.mmmi.cbse.collisionsystem;

import dk.sdu.mmmi.cbse.common.asteroids.Asteroid;
import dk.sdu.mmmi.cbse.common.services.IPostEntityProcessingService;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.bullet.Bullet;
import dk.sdu.mmmi.cbse.common.asteroids.IAsteroidFactory;
import java.util.ServiceLoader;

public class CollisionDetector implements IPostEntityProcessingService {

    public CollisionDetector() {
    }

    @Override
    public void process(GameData gameData, World world) {

        //keeps track of all entities already dealt with
        java.util.Set<Entity> removed = new java.util.HashSet<>();

        // two for loops for all entities in the world
        for (Entity entity1 : world.getEntities()) {
            for (Entity entity2 : world.getEntities()) {
                if (entity1.getID().equals(entity2.getID())) continue;
                if (removed.contains(entity1) || removed.contains(entity2)) continue;

                if (this.collides(entity1, entity2)) {
                    if(entity1 instanceof Asteroid && entity2 instanceof Asteroid) continue;

                    if (entity1 instanceof Bullet && ((Bullet) entity1).getOwnerID().equals(entity2.getID())) continue;
                    if (entity2 instanceof Bullet && ((Bullet) entity2).getOwnerID().equals(entity1.getID())) continue;

                    if (entity1 instanceof Asteroid) {
                        System.out.println("Asteroid hit set, size: " + ((Asteroid) entity1).getSize());
                        ((Asteroid) entity1).setHit(true);
                        removed.add(entity1);
                        removed.add(entity2);
                        world.removeEntity(entity2);
                    } else if (entity2 instanceof Asteroid) {
                        System.out.println("Asteroid hit set, size: " + ((Asteroid) entity2).getSize());
                        ((Asteroid) entity2).setHit(true);
                        removed.add(entity1);
                        removed.add(entity2);
                        world.removeEntity(entity1);
                    } else {
                        removed.add(entity1);
                        removed.add(entity2);
                        world.removeEntity(entity1);
                        world.removeEntity(entity2);
                        spawnAsteroid(gameData, world);
                    }
                }
            }
        }
    }

    public Boolean collides(Entity entity1, Entity entity2) {
        float dx = (float) entity1.getX() - (float) entity2.getX();
        float dy = (float) entity1.getY() - (float) entity2.getY();
        float distance = (float) Math.sqrt(dx * dx + dy * dy);
        return distance < (entity1.getRadius() + entity2.getRadius());
    }

    private void spawnAsteroid(GameData gameData, World world) {
        ServiceLoader.load(IAsteroidFactory.class).stream()
                .findFirst()
                .ifPresent(provider -> world.addEntity(provider.get().createAsteroid(gameData)));
    }

}
