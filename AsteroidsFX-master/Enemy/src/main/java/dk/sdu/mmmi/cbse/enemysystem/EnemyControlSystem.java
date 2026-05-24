package dk.sdu.mmmi.cbse.enemysystem;

import dk.sdu.mmmi.cbse.common.bullet.BulletSPI;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;

import java.util.*;

import static java.util.stream.Collectors.toList;

public class EnemyControlSystem implements IEntityProcessingService {

    private final Random rnd = new Random();
    private long lastShotTime = 0;
    private final long SHOOT_COOLDOWN = 2000;

    @Override
    public void process(GameData gameData, World world) {
        for (Entity e : world.getEntities(Enemy.class)) {
            Enemy enemy = (Enemy) e;

            // Randomly change direction
            if (rnd.nextInt(100) < 3) {
                enemy.setRotation(enemy.getRotation() + rnd.nextInt(90) - 45);
            }

            // Move forward
            double changeX = Math.cos(Math.toRadians(enemy.getRotation()));
            double changeY = Math.sin(Math.toRadians(enemy.getRotation()));
            enemy.setX(enemy.getX() + changeX * 2);
            enemy.setY(enemy.getY() + changeY * 2);

            // Wrap around edges
            if (enemy.getX() < 0) enemy.setX(gameData.getDisplayWidth());
            if (enemy.getX() > gameData.getDisplayWidth()) enemy.setX(0);
            if (enemy.getY() < 0) enemy.setY(gameData.getDisplayHeight());
            if (enemy.getY() > gameData.getDisplayHeight()) enemy.setY(0);

            // Shoot randomly
            long currentTime = System.currentTimeMillis();
            if (currentTime - lastShotTime >= SHOOT_COOLDOWN && rnd.nextInt(100) < 10) {
                lastShotTime = currentTime;
                getBulletSPIs().stream().findFirst().ifPresent(
                        spi -> world.addEntity(spi.createBullet(enemy, gameData))
                );
            }
        }
    }

    private Collection<? extends BulletSPI> getBulletSPIs() {
        List<BulletSPI> spis = new ArrayList<>();
        ServiceLoader.load(BulletSPI.class).stream().map(ServiceLoader.Provider::get).forEach(spis::add);
        ModuleLayer pluginsLayer = GameData.getPluginsLayer();
        if (pluginsLayer != null) {
            ServiceLoader.load(pluginsLayer, BulletSPI.class).stream().map(ServiceLoader.Provider::get).forEach(spis::add);
        }
        return spis;
    }
}