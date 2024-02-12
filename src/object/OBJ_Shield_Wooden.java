package object;

import main.GamePanel;

public class OBJ_Shield_Wooden extends GameObject{
    public OBJ_Shield_Wooden(GamePanel gp, int x, int y, boolean collision) {
        super(gp, x, y, "shieldWood", "Wooden Shield", collision);
        defenseValue = 1;
        setDescription("A trustworthy shield.");
    }
}
