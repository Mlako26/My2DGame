package state;

import main.GamePanel;
import main.UserInterface;

public class PlayGameState implements GameState{
    @Override
    public void updatePanel(GamePanel gp) {
        gp.updateOnPlayState();
    }

    @Override
    public void updateInterface(UserInterface ui) {
        ui.drawPlayScreen();
    }

    @Override
    public GameState changePausedState() {
        return new PauseGameState();
    }
}
