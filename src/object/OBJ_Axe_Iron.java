package object;

import main.GamePanel;

public class OBJ_Axe_Iron extends GameObject{
    public OBJ_Axe_Iron(GamePanel gp, int x, int y) {
        super(gp, x, y, "axeNormal", "Iron Axe", false);
        setDescription("A shiny axe.");

        attackValue = 2;
        attackArea.width = 30;
        attackArea.height = 30;
        type = typeAxe;
    }

}
