package state;

import main.GamePanel;
import main.UserInterface;

public interface GameState {

    void updatePanel(GamePanel gp);

    void updateInterface(UserInterface ui);

    GameState changePausedState();
}
