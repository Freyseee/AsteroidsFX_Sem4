package dk.sdu.mmmi.cbse.asteroid;

import dk.sdu.mmmi.cbse.common.asteroids.Asteroid;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;
import java.util.Random;

public class AsteroidPlugin implements IGamePluginService {

    @Override
    public void start(GameData gameData, World world) {
        Entity asteroid = createAsteroid(gameData);
        world.addEntity(asteroid);
    }

    @Override
    public void stop(GameData gameData, World world) {
        // Remove entities
        for (Entity asteroid : world.getEntities(Asteroid.class)) {
            world.removeEntity(asteroid);
        }
    }

    private Entity createAsteroid(GameData gameData) {
        Entity asteroid = new Asteroid();
        Random rnd = new Random();
        int size = rnd.nextInt(10) + 5;
        asteroid.setPolygonCoordinates(size, -size, -size, -size, -size, size, size, size);

        int edge = rnd.nextInt(4); //Spawns the asteroid at a random edge
        switch (edge) {//Top of the screen
            case 0:
                asteroid.setX(rnd.nextInt(gameData.getDisplayWidth()));
                asteroid.setY(0);
                asteroid.setRotation(rnd.nextInt(180));
                break;
            case 1://bottom
                asteroid.setX(rnd.nextInt(gameData.getDisplayWidth()));
                asteroid.setY(gameData.getDisplayHeight());
                asteroid.setRotation(rnd.nextInt(180)+180);
                break;
            case 2://left
                asteroid.setX(0);
                asteroid.setY(rnd.nextInt(gameData.getDisplayHeight()));
                asteroid.setRotation(rnd.nextInt(180)-90);
                break;
            case 3://right
                asteroid.setX(gameData.getDisplayWidth());
                asteroid.setY(rnd.nextInt(gameData.getDisplayHeight()));
                asteroid.setRotation(rnd.nextInt(180)+90);
                break;
        }

        asteroid.setRadius(size);

        return asteroid;
    }
}
