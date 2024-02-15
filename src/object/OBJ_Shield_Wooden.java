package object;

import main.GamePanel;

public class OBJ_Shield_Wooden extends Shield {
    public OBJ_Shield_Wooden(GamePanel gp, int x, int y) {
        super(gp, x, y, "shieldWood", "Wooden Shield", false);
        defenseValue = 1;
        setDescription("A trustworthy shield.");
        type = typeShield;
    }
}
