package object;

import entity.Entity;
import entity.Player;
import main.GamePanel;
import state.DialogueGameState;

public class OBJ_Potion_Red extends Consumable {
    public OBJ_Potion_Red (GamePanel gp, int worldX, int worldY) {
        super(gp, worldX, worldY, "healthPotion", "Health potion", false);
        type = typeConsumable;
        setDescription("Restores a full heart.");
    }

    public boolean use(Player player) {
        gp.gameState = new DialogueGameState();

        if (player.life == player.maxLife) {
            gp.ui.currentDialogue = "You already are at full health!";
            return false;
        } else {
            gp.ui.currentDialogue = "You drank the " + name + "!\n" +
                    "You recovered a full heart...";
            player.life = Math.min(player.maxLife, player.life + 2);
            gp.playSoundEffect(9);
            return true;
        }
    }

}
