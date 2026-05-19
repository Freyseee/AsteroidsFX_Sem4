package dk.sdu.mmmi.cbse.collisionsystem;

import dk.sdu.mmmi.cbse.common.bullet.Bullet;
import dk.sdu.mmmi.cbse.common.services.IPostEntityProcessingService;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.playersystem.Player;
import dk.sdu.mmmi.cbse.common.asteroids.IAsteroidFactory;
import java.util.ServiceLoader;

public class CollisionDetector implements IPostEntityProcessingService {

    public CollisionDetector() {
    }

    @Override
    public void process(GameData gameData, World world) {
        // two for loops for all entities in the world
        for (Entity entity1 : world.getEntities()) {
            for (Entity entity2 : world.getEntities()) {

                // if the two entities are identical, skip the iteration
                if (entity1.getID().equals(entity2.getID())) {
                    continue;                    
                }

                // CollisionDetection
                if (this.collides(entity1, entity2)) {
                    if (entity1 instanceof Player) {
                        if (entity2 instanceof Bullet) {
                            continue;
                        }
                        ((Player) entity1).loseLife();
                        gameData.setLives(((Player) entity1).getLives());
                        if (!((Player) entity1).isAlive()) {
                            world.removeEntity(entity1);
                        }
                        world.removeEntity(entity2);
                        spawnAsteroid(gameData, world);
                    } else if (entity2 instanceof Player) {
                        if (entity1 instanceof Bullet) {
                            continue;
                        }
                        ((Player) entity2).loseLife();
                        gameData.setLives(((Player) entity2).getLives());
                        if (!((Player) entity2).isAlive()) {
                            world.removeEntity(entity2);
                        }
                        world.removeEntity(entity1);
                        spawnAsteroid(gameData, world);
                    } else {
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
