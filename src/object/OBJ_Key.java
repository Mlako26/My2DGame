package object;

import main.GamePanel;

public class OBJ_Key extends GameObject {
    public OBJ_Key(GamePanel gp, int x, int y) {
        super(gp, x, y, "key", "Key",false);
        setDescription("It opens doors.\nSingle use.");
    }
}
