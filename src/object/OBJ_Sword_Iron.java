package object;

import main.GamePanel;

public class OBJ_Sword_Iron extends GameObject {

    public OBJ_Sword_Iron(GamePanel gp, int x, int y) {
        super(gp, x, y, "swordNormal", "Iron Sword", false);
        setDescription("An old sword.");

        attackValue = 1;
        attackArea.width = 36;
        attackArea.height = 36;
        type = typeWeapon;
    }

}
