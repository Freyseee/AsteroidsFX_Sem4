package dk.sdu.mmmi.cbse.enemysystem;

import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;
import dk.sdu.mmmi.cbse.common.ship.Ship;

public class EnemyPlugin implements IGamePluginService {

    private Ship enemy;

    /***
     * <p><b>Pre: </b>World and gamedata not null, no enemy entity</p>
     * <p><b>Post: </b>enemy entity spawned</p>
     * @param gameData
     * @param world
     */
    @Override
    public void start(GameData gameData, World world) {
        enemy = createEnemy(gameData);
        world.addEntity(enemy);
    }

    private Ship createEnemy(GameData gameData) {
        Ship enemy = new Enemy();
        enemy.setLives(3);
        enemy.setType("enemy");
        enemy.setPolygonCoordinates(-5, -5, 10, 0, -5, 5);
        enemy.setX(gameData.getDisplayWidth() / 4);
        enemy.setY(gameData.getDisplayHeight() / 4);
        enemy.setRadius(8);
        return enemy;
    }

    /***
     * <p><b>Pre: </b> Start has been called. world and gamedata not null</p>
     * <p><b>Post: </b>enemy is removed</p>
     * @param gameData
     * @param world
     */
    @Override
    public void stop(GameData gameData, World world) {
        world.removeEntity(enemy);
    }
}