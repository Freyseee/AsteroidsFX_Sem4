package dk.sdu.mmmi.cbse.playersystem;

import dk.sdu.mmmi.cbse.common.bullet.BulletSPI;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.GameKeys;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;

import java.util.Collection;
import java.util.ServiceLoader;

import static java.util.stream.Collectors.toList;

public class PlayerControlSystem implements IEntityProcessingService {

    private double velocityX = 0;
    private double velocityY = 0;
    private final double ACCELERATION = 0.2;
    private final double MAX_SPEED = 5;
    private long lastBulletTime = 0;
    private final long BULLET_COOLDOWN = 300;

    @Override
    public void process(GameData gameData, World world) {

        for (Entity player : world.getEntities(Player.class)) {

            // Rotate
            if (gameData.getKeys().isDown(GameKeys.LEFT)) {
                player.setRotation(player.getRotation() - 3);
            }
            if (gameData.getKeys().isDown(GameKeys.RIGHT)) {
                player.setRotation(player.getRotation() + 3);
            }

            //Acceleration
            if (gameData.getKeys().isDown(GameKeys.UP)) {
                double radians = Math.toRadians(player.getRotation());
                velocityX += Math.cos(radians) * ACCELERATION;
                velocityY += Math.sin(radians) * ACCELERATION;
            }

            //Max speed
            double speed = Math.sqrt(velocityX * velocityX + velocityY * velocityY);
            if (speed > MAX_SPEED) {
                velocityX = (velocityX / speed) * MAX_SPEED;
                velocityY = (velocityY / speed) * MAX_SPEED;
            }

            //Slows down when not pressing the move button
            velocityX *= 0.95;
            velocityY *= 0.95;

            //Moving
            player.setX(player.getX() + velocityX);
            player.setY(player.getY() + velocityY);

            //Player cant leave the edges of the screen!
            if (player.getX() < 0) player.setX(1);
            if (player.getX() > gameData.getDisplayWidth()) player.setX(gameData.getDisplayWidth() - 1);
            if (player.getY() < 0) player.setY(1);
            if (player.getY() > gameData.getDisplayHeight()) player.setY(gameData.getDisplayHeight() - 1);

            //Shooting
            long currentTime = System.currentTimeMillis();
            if (gameData.getKeys().isDown(GameKeys.SPACE) && currentTime - lastBulletTime >= BULLET_COOLDOWN) {
                lastBulletTime = currentTime;
                getBulletSPIs().stream().findFirst().ifPresent(
                        spi -> world.addEntity(spi.createBullet(player, gameData))
                );
            }
        }

    }

    private Collection<? extends BulletSPI> getBulletSPIs() {
        return ServiceLoader.load(BulletSPI.class).stream().map(ServiceLoader.Provider::get).collect(toList());
    }
}