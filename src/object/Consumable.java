package object;

import entity.Entity;
import entity.Player;
import main.GamePanel;

public abstract class Consumable extends GameObject {
    public Consumable(GamePanel gp, int x, int y, String imageName, String name, boolean collision) {
        super(gp, x, y, imageName, name, collision);
    }

    public void interact(Player player) {
        player.interactWithConsumable(this);
    }

    public abstract boolean use(Player player);
}
