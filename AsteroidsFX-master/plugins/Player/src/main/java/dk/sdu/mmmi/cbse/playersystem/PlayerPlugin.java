package dk.sdu.mmmi.cbse.playersystem;

import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;
import dk.sdu.mmmi.cbse.common.ship.Ship;

public class PlayerPlugin implements IGamePluginService {

    private Ship player;

    public PlayerPlugin() {
    }

    /***
     * <p><b>Pre-conditions: </b>gamedata and world are not null, there is no player entity</p>
     * <p><b>Post-conditions: </b>There is a player entity and is at the center of the screen/world</p>
     * @param gameData
     * @param world
     */

    @Override
    public void start(GameData gameData, World world) {

        // Add entities to the world
        player = createPlayerShip(gameData);
        world.addEntity(player);
    }

    private Ship createPlayerShip(GameData gameData) {

        Ship playerShip = new Player();
        playerShip.setLives(3);
        playerShip.setType("player");
        playerShip.setPolygonCoordinates(-5,-5,10,0,-5,5);
        playerShip.setX(gameData.getDisplayHeight()/2);
        playerShip.setY(gameData.getDisplayWidth()/2);
        playerShip.setRadius(8);
        return playerShip;
    }

    /***
     * <p><b>Pre-conditions: </b>start has been called. gamedata and world are not null</p>
     * <p><b>Post-conditions: </b>There is no player entity and the game is game over</p>
     * @param gameData
     * @param world
     */

    @Override
    public void stop(GameData gameData, World world) {
        // Remove entities
        gameData.setGameOver(true);
        world.removeEntity(player);
    }

}
