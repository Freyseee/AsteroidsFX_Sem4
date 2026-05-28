package dk.sdu.mmmi.cbse.asteroid;

import dk.sdu.mmmi.cbse.common.asteroids.Asteroid;
import dk.sdu.mmmi.cbse.common.asteroids.IAsteroidFactory;
import dk.sdu.mmmi.cbse.common.asteroids.IAsteroidSplitter;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import java.util.Random;
import java.util.ServiceLoader;

public class AsteroidSplitterImpl implements IAsteroidSplitter {

    @Override
    public void createSplitAsteroid(Entity e, World world, GameData gameData) {
        Asteroid original = (Asteroid) e;

        Random rnd = new Random();
        int newSize = Math.max(original.getSize() / 2, 3);

        if (original.getSize() < 10) {
            System.out.print("1");
            ServiceLoader.load(IAsteroidFactory.class).stream()
                    .findFirst()
                    .ifPresent(provider -> world.addEntity(provider.get().createAsteroid(gameData)));
        }

        else {
            System.out.print("2");
            for(int i = 0; i<2; i++){
                System.out.print("creating a fragment");
                Asteroid fragment = new Asteroid();
                fragment.setSize(newSize);
                fragment.setPolygonCoordinates(newSize, -newSize, -newSize, -newSize, -newSize, newSize, newSize, newSize);
                fragment.setRadius(newSize);
                fragment.setX(original.getX() + rnd.nextInt(20) - 10);
                fragment.setY(original.getY() + rnd.nextInt(20) - 10);
                fragment.setRotation(rnd.nextInt(360));
                world.addEntity(fragment);
            }
        }
    }
}