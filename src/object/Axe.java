package object;

import entity.Player;
import main.GamePanel;

public abstract class Axe extends Weapon {
    public Axe(GamePanel gp, int x, int y, String imageName, String name, boolean collision) {
        super(gp, x, y, imageName, name, collision);
    }

    public void getPlayerAttackImages(Player player) {
        player.getAxeAttackImages();
    }
}
