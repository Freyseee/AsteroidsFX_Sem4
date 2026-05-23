package dk.sdu.mmmi.cbse.common.services;

import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;

public interface IEntityProcessingService {

    /**
     *
     * Called once per frame. Used for updating entity states
     *
     * @param gameData
     * @param world
     * @throws
     */
    void process(GameData gameData, World world);
}
