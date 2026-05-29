package dk.sdu.mmmi.cbse.common.ship;
import dk.sdu.mmmi.cbse.common.data.Entity;

public class Ship extends Entity {
    private int lives;
    private String type;

    public int getLives() {

        return lives;
    }

    public void setLives(int lives) {

        this.lives = lives;
    }

    public String getType(){
        return type;
    }

    public void setType(String type){
        this.type = type;
    }

}
