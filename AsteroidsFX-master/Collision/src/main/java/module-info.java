import dk.sdu.mmmi.cbse.common.services.IPostEntityProcessingService;

module Collision {
    requires Common;
    //requires Player;
    //requires Enemy;
    requires CommonAsteroids;
    requires CommonBullet;
    exports dummy;
    provides IPostEntityProcessingService with dk.sdu.mmmi.cbse.collisionsystem.CollisionDetector;
    uses dk.sdu.mmmi.cbse.common.asteroids.IAsteroidFactory;
    uses dk.sdu.mmmi.cbse.common.asteroids.IAsteroidSplitter;
}