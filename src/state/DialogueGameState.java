package state;

import main.GamePanel;
import main.UserInterface;

public class DialogueGameState implements GameState {

    @Override
    public void updatePanel(GamePanel gp) {
        gp.updateOnPauseState();
    }

    @Override
    public void updateInterface(UserInterface ui) {
        ui.drawDialogueScreen();
    }

    @Override
    public GameState changePausedState() {
        return new PlayGameState();
    }
}
