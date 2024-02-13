package object;

import main.GamePanel;

public class OBJ_Shield_Iron extends GameObject {
    public OBJ_Shield_Iron(GamePanel gp, int x, int y) {
        super(gp, x, y, "shieldIron", "Iron Shield", false);
        defenseValue = 2;
        setDescription("A strong shield\nfor strong fighters.");
        type = typeShield;
    }
}
