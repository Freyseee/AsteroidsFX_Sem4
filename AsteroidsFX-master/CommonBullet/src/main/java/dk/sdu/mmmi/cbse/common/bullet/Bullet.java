package dk.sdu.mmmi.cbse.common.bullet;

import dk.sdu.mmmi.cbse.common.data.Entity;

public class Bullet extends Entity {

    private String ownerID;
    private int lifespan;

    public int getLifespan() {

        return lifespan;
    }

    public void setLifespan(int lifespan) {

        this.lifespan = lifespan;
    }

    public String getOwnerID() {
        return ownerID;
    }

    public void setOwnerID(String ownerId) {
        this.ownerID = ownerId;
    }

}
