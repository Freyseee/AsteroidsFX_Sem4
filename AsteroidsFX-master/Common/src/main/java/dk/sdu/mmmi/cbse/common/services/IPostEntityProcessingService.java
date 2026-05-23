package dk.sdu.mmmi.cbse.common.services;

import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;

public interface IPostEntityProcessingService {
    /**
     * Called once per frame. Use this for logic that depends on the final state of entities
     * @param gameData
     * @param world
     */
    void process(GameData gameData, World world);
}
