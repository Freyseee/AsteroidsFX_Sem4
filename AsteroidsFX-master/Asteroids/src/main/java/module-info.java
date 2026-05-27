import dk.sdu.mmmi.cbse.asteroid.AsteroidController;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;

module Asteroid {
    uses dk.sdu.mmmi.cbse.common.asteroids.IAsteroidFactory;
    requires Common;
    requires CommonAsteroids;
    provides IGamePluginService with dk.sdu.mmmi.cbse.asteroid.AsteroidPlugin;
    provides IEntityProcessingService with AsteroidController;
    provides dk.sdu.mmmi.cbse.common.asteroids.IAsteroidSplitter with dk.sdu.mmmi.cbse.asteroid.AsteroidSplitterImpl;
}