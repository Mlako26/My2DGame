package object;

import main.GamePanel;

public class OBJ_Sword_Normal extends GameObject {

    public OBJ_Sword_Normal(GamePanel gp, int x, int y, boolean collision) {
        super(gp, x, y, "swordNormal", collision);
        name = "Iron Sword";
        attackValue = 1;
        description = "[" + name + "]\nAn old sword.";
    }
}
