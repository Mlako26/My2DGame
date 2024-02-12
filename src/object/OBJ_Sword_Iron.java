package object;

import main.GamePanel;

public class OBJ_Sword_Iron extends GameObject {

    public OBJ_Sword_Iron(GamePanel gp, int x, int y, boolean collision) {
        super(gp, x, y, "swordNormal", "Iron Sword", collision);
        attackValue = 1;
        setDescription("An old sword.");
    }

}
