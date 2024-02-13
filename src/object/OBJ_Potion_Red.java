package object;

import entity.Entity;
import main.GamePanel;
import state.DialogueGameState;

public class OBJ_Potion_Red extends GameObject {
    public OBJ_Potion_Red (GamePanel gp, int worldX, int worldY) {
        super(gp, worldX, worldY, "key", "Health potion", false);
        type = typeConsumable;
        setDescription("Restores a full heart.");
    }

    public boolean use(Entity entity) {
        gp.gameState = new DialogueGameState();
        if (entity.life == entity.maxLife) {
            gp.ui.currentDialogue = "You already are at full health!";
            return false;
        } else {
            gp.ui.currentDialogue = "You drank the " + name + "!\n" +
                    "You recovered a full heart...";
            entity.life = Math.min(entity.maxLife, entity.life + 2);
            gp.playSoundEffect(9);
            return true;
        }
    }
}
