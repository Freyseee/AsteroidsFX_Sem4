module CommonAsteroids {
    requires Common;
    exports dk.sdu.mmmi.cbse.common.asteroids;
    provides dk.sdu.mmmi.cbse.common.asteroids.IAsteroidFactory with dk.sdu.mmmi.cbse.common.asteroids.AsteroidFactory;
}