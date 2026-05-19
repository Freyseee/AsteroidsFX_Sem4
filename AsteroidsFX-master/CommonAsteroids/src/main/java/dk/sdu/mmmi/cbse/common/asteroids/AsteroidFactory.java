package dk.sdu.mmmi.cbse.common.asteroids;

import dk.sdu.mmmi.cbse.common.asteroids.Asteroid;
import dk.sdu.mmmi.cbse.common.asteroids.IAsteroidFactory;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import java.util.Random;

public class AsteroidFactory implements IAsteroidFactory {

    @Override
    public Entity createAsteroid(GameData gameData) {
        Asteroid asteroid = new Asteroid();
        Random rnd = new Random();
        int size = rnd.nextInt(10) + 5;
        asteroid.setPolygonCoordinates(size, -size, -size, -size, -size, size, size, size);
        asteroid.setRadius(size);
        int edge = rnd.nextInt(4);
        switch (edge) {
            case 0:
                asteroid.setX(rnd.nextInt(gameData.getDisplayWidth()));
                asteroid.setY(0);
                asteroid.setRotation(rnd.nextInt(180));
                break;
            case 1:
                asteroid.setX(rnd.nextInt(gameData.getDisplayWidth()));
                asteroid.setY(gameData.getDisplayHeight());
                asteroid.setRotation(rnd.nextInt(180) + 180);
                break;
            case 2:
                asteroid.setX(0);
                asteroid.setY(rnd.nextInt(gameData.getDisplayHeight()));
                asteroid.setRotation(rnd.nextInt(180) - 90);
                break;
            case 3:
                asteroid.setX(gameData.getDisplayWidth());
                asteroid.setY(rnd.nextInt(gameData.getDisplayHeight()));
                asteroid.setRotation(rnd.nextInt(180) + 90);
                break;
        }
        return asteroid;
    }
}