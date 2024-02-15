package object;

import entity.Player;
import main.GamePanel;

public abstract class Shield extends GameObject {
    public Shield(GamePanel gp, int x, int y, String imageName, String name, boolean collision) {
        super(gp, x, y, imageName, name, collision);
    }

    public void interact(Player player) {
        player.interactWithShield(this);
    }
}
