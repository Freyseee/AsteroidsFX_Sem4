package dk.sdu.mmmi.cbse.collision.test;

import java.util.List;

import dk.sdu.mmmi.cbse.common.asteroids.Asteroid;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.bullet.Bullet;
import dk.sdu.mmmi.cbse.common.ship.Ship;
import dk.sdu.mmmi.cbse.collisionsystem.CollisionDetector;

class CollisionTest {

    private World world;
    private GameData gameData;

    private CollisionDetector collisionDetector;
    private Ship player;
    private Ship enemy;
    private Bullet playerBullet;
    private Bullet enemyBullet;
    private Asteroid asteroid;

    @BeforeEach
    void setUp() {
        world = new World();
        gameData = new GameData();
        collisionDetector = new CollisionDetector();

        player = new Ship();
        player.setLives(3);
        player.setX(100);
        player.setY(100);
        player.setRadius(10);

        enemy = new Ship();
        enemy.setLives(3);
        enemy.setX(100);
        enemy.setY(100);
        enemy.setRadius(10);

        playerBullet = new Bullet();
        playerBullet.setX(100);
        playerBullet.setY(100);
        playerBullet.setRadius(5);
        playerBullet.setOwnerID(player.getID());

        enemyBullet = new Bullet();
        enemyBullet.setX(100);
        enemyBullet.setY(100);
        enemyBullet.setRadius(5);
        enemyBullet.setOwnerID(enemy.getID());

        asteroid = new Asteroid();
        asteroid.setX(100);
        asteroid.setY(100);
        asteroid.setRadius(10);
    }


    @Test
    void playerBulletHitsEnemy() {
        world.addEntity(playerBullet);
        world.addEntity(enemy);

        collisionDetector.process(gameData, world);

        assertAll(
                () -> assertEquals(2, enemy.getLives()),
                () -> assertTrue(world.getEntities().contains(enemy)),
                () -> assertFalse(world.getEntities().contains(playerBullet))
        );
    }

    @Test
    void enemyBulletHitsPlayer() {
        world.addEntity(enemyBullet);
        world.addEntity(player);

        collisionDetector.process(gameData, world);

        assertAll(
                () -> assertEquals(2, player.getLives()),
                () -> assertTrue(world.getEntities().contains(player)),
                () -> assertFalse(world.getEntities().contains(enemyBullet))
        );
    }

    @Test
    void playerShotThrice() {
        world.addEntity(enemyBullet);
        world.addEntity(player);
        collisionDetector.process(gameData, world);

        world.addEntity(enemyBullet);
        collisionDetector.process(gameData, world);

        world.addEntity(enemyBullet);
        collisionDetector.process(gameData, world);

        assertAll(
                () -> assertEquals(0, player.getLives()),
                () -> assertFalse(world.getEntities().contains(player)),
                () -> assertFalse(world.getEntities().contains(enemyBullet))
        );
    }


    @Test
    void AsteroidHitsEnemy() {
        world.addEntity(enemy);
        world.addEntity(asteroid);

        collisionDetector.process(gameData, world);

        assertAll(
                () -> assertEquals(0, enemy.getLives()),
                () -> assertFalse(world.getEntities().contains(enemy)),
                () -> assertFalse(world.getEntities().contains(asteroid))
        );
    }

    @Test
    void AsteroidHitsPlayer() {
        world.addEntity(player);
        world.addEntity(asteroid);

        collisionDetector.process(gameData, world);

        assertAll(
                () -> assertEquals(0, player.getLives()),
                () -> assertFalse(world.getEntities().contains(player)),
                () -> assertFalse(world.getEntities().contains(asteroid))
        );
    }

    @Test
    void PlayerAndEnemyCollides() {
        world.addEntity(player);
        world.addEntity(enemy);

        collisionDetector.process(gameData, world);

        assertAll(
                () -> assertFalse(world.getEntities().contains(enemy)),
                () -> assertFalse(world.getEntities().contains(player)),
                () -> assertEquals(0, player.getLives()),
                () -> assertEquals(0, enemy.getLives())
        );
    }

    @Test
    void PlayersBulletHitsPlayer() {
        world.addEntity(player);
        world.addEntity(playerBullet);

        collisionDetector.process(gameData, world);

        assertAll(
                () -> assertEquals(3, player.getLives()),
                () -> assertTrue(world.getEntities().contains(player))
        );
    }

    @Test
    void EnemysBulletHitsEnemy() {
        world.addEntity(enemy);
        world.addEntity(enemyBullet);

        collisionDetector.process(gameData, world);

        assertAll(
                () -> assertEquals(3, enemy.getLives()),
                () -> assertTrue(world.getEntities().contains(enemy))
        );
    }

}
