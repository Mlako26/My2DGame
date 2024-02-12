package object;

import main.GamePanel;

public class OBJ_Shield_Wood extends GameObject{
    public OBJ_Shield_Wood(GamePanel gp, int x, int y, boolean collision) {
        super(gp, x, y, "shieldWood", collision);
        name = "Wooden Shield";
        defenseValue = 1;
        description = "[" + name +"]\nA trustworthy shield.";
    }
}
