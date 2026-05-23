package dk.sdu.mmmi.cbse.common.services;

import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;

public interface IGamePluginService {

    /**
     *Called when the game starts. Should be used to spawn relevant entities
     * @param gameData
     * @param world
     */
    void start(GameData gameData, World world);

    /**
     * Called when the game stops or plugin is unloaded. Should remove its entities when used
     * @param gameData
     * @param world
     */
    void stop(GameData gameData, World world);
}
