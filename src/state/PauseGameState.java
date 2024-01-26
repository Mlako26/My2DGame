package state;

import main.GamePanel;
import main.UserInterface;

public class PauseGameState implements GameState {

    @Override
    public void updatePanel(GamePanel gp) {
        gp.updateOnPauseState();
    }

    @Override
    public void updateInterface(UserInterface ui) {
        ui.drawPauseScreen();
    }

    @Override
    public GameState changePausedState() {
        return new PlayGameState();
    }
}
