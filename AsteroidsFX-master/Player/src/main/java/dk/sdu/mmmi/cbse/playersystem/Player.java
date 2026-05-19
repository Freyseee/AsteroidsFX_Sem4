package dk.sdu.mmmi.cbse.playersystem;

import dk.sdu.mmmi.cbse.common.data.Entity;

public class Player extends Entity {

    private int lives;


    public Player(int lives) {
        this.lives = lives;
    }

    public int getLives() {
        return lives;
    }

    public void loseLife() {
        lives--;
    }

    public boolean isAlive() {
        return lives > 0;
    }

}
