package state;

import main.GamePanel;
import main.UserInterface;

import java.awt.*;

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

    @Override
    public void whatShouldIPaint(GamePanel gp, Graphics2D g2) {
        gp.paintGame(g2);
    }

}
