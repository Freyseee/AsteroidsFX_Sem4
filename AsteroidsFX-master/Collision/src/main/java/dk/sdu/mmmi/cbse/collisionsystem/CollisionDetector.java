package dk.sdu.mmmi.cbse.collisionsystem;

import dk.sdu.mmmi.cbse.common.asteroids.Asteroid;
import dk.sdu.mmmi.cbse.common.services.IPostEntityProcessingService;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.bullet.Bullet;
import dk.sdu.mmmi.cbse.common.ship.Ship;
import dk.sdu.mmmi.cbse.common.asteroids.IAsteroidFactory;
import java.util.ServiceLoader;
import java.util.Set;

public class CollisionDetector implements IPostEntityProcessingService {

    public CollisionDetector() {
    }

    @Override
    public void process(GameData gameData, World world) {
        java.util.Set<Entity> removed = new java.util.HashSet<>();

        for (Entity entity1 : world.getEntities()) {
            for (Entity entity2 : world.getEntities()) {
                if (entity1.getID().equals(entity2.getID())) continue;
                if (removed.contains(entity1) || removed.contains(entity2)) continue;

                if (this.collides(entity1, entity2)) {
                    //Skips if the colliding objects should cause no action
                    if (entity1 instanceof Asteroid && entity2 instanceof Asteroid) continue;
                    if (entity1 instanceof Bullet && ((Bullet) entity1).getOwnerID().equals(entity2.getID())) continue;
                    if (entity2 instanceof Bullet && ((Bullet) entity2).getOwnerID().equals(entity1.getID())) continue;
                    if (entity1 instanceof Bullet && entity2 instanceof Bullet) continue;

                    handleCollision(entity1, entity2, gameData, world, removed);
                }
            }
        }
    }
    //Checks what type of entities are colliding
    private void handleCollision(Entity entity1, Entity entity2, GameData gameData, World world, Set<Entity> removed) {
        if (entity1 instanceof Asteroid && entity2 instanceof Bullet)
            handleAsteroidBullet((Asteroid) entity1, (Bullet) entity2, gameData, world, removed);
        else if (entity2 instanceof Asteroid && entity1 instanceof Bullet)
            handleAsteroidBullet((Asteroid) entity2, (Bullet) entity1, gameData, world, removed);
        else if (entity1 instanceof Asteroid && entity2 instanceof Ship)
            handleAsteroidShip((Asteroid) entity1, (Ship) entity2, world, removed);
        else if (entity2 instanceof Asteroid && entity1 instanceof Ship)
            handleAsteroidShip((Asteroid) entity2, (Ship) entity1, world, removed);
        else if (entity1 instanceof Ship && entity2 instanceof Bullet)
            handleShipBullet((Ship) entity1, (Bullet) entity2, gameData, world, removed);
        else if (entity2 instanceof Ship && entity1 instanceof Bullet)
            handleShipBullet((Ship) entity2, (Bullet) entity1, gameData, world, removed);
        else if (entity1 instanceof Ship && entity2 instanceof Ship)
            handleShipShip((Ship) entity1, (Ship) entity2, world, removed);
    }
    //Entity-specific handlers
    private void handleAsteroidBullet(Asteroid asteroid, Bullet bullet, GameData gameData, World world, Set<Entity> removed) {
        asteroid.setHit(true);
        if (isPlayerBullet(bullet, world)) gameData.addScore(100);
        world.removeEntity(asteroid);
        world.removeEntity(bullet);
        removed.add(asteroid);
        removed.add(bullet);
    }

    private void handleAsteroidShip(Asteroid asteroid, Ship ship, World world, Set<Entity> removed) {
        asteroid.setHit(true);
        ship.setLives(0);
        world.removeEntity(asteroid);
        world.removeEntity(ship);
        removed.add(asteroid);
        removed.add(ship);
    }

    private void handleShipBullet(Ship ship, Bullet bullet, GameData gameData, World world, Set<Entity> removed) {
        ship.setLives(ship.getLives() - 1);
        if (isPlayerBullet(bullet, world)) gameData.addScore(1000);
        world.removeEntity(bullet);
        removed.add(bullet);
        if (ship.getLives() < 1) {
            world.removeEntity(ship);
            removed.add(ship);
        }
    }

    private void handleShipShip(Ship ship1, Ship ship2, World world, Set<Entity> removed) {
        ship1.setLives(0);
        ship2.setLives(0);
        world.removeEntity(ship1);
        world.removeEntity(ship2);
        removed.add(ship1);
        removed.add(ship2);
    }

    //Helpers
    public Boolean collides(Entity entity1, Entity entity2) {
        float dx = (float) entity1.getX() - (float) entity2.getX();
        float dy = (float) entity1.getY() - (float) entity2.getY();
        float distance = (float) Math.sqrt(dx * dx + dy * dy);
        return distance < (entity1.getRadius() + entity2.getRadius());
    }

    private boolean isPlayerBullet(Bullet bullet, World world) {
        return world.getEntities().stream()
                .anyMatch(e -> e.getID().equals(bullet.getOwnerID())
                        && e instanceof Ship
                        && "player".equals(((Ship) e).getType()));
    }

}
