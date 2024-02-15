package object;

import entity.Player;
import main.GamePanel;
import entity.Entity;

public abstract class Weapon extends GameObject {
    public Weapon(GamePanel gp, int x, int y, String imageName, String name, boolean collision) {
        super(gp, x, y, imageName, name, collision);
    }

    public void interact(Player player) {
        player.interactWithWeapon(this);
    }

    public void getPlayerAttackImages(Player player) {
        player.getSwordAttackImages();
    }
}
