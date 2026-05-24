package dk.sdu.mmmi.cbse.bulletsystem;

import dk.sdu.mmmi.cbse.common.bullet.Bullet;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;

public class BulletPlugin implements IGamePluginService {

    private Entity bullet;

    /***
     * <p><b>Pre: </b>gamedata and world not null</p>
     * <p><b>Post: </b>no bullets created, but can be created via bulletcontrolsystem using bulletSPI</p>
     * @param gameData
     * @param world
     */
    @Override
    public void start(GameData gameData, World world) {

    }

    /***
     * <p><b>Pre: </b>gamedata and world are not null</p>
     * <p><b>Post: </b>all bullets are removed</p>
     * @param gameData
     * @param world
     */
    @Override
    public void stop(GameData gameData, World world) {
        for (Entity e : world.getEntities()) {
            if (e.getClass() == Bullet.class) {
                world.removeEntity(e);
            }
        }
    }

}
