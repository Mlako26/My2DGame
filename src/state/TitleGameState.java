package state;

import main.GamePanel;
import main.UserInterface;

import java.awt.*;

public class TitleGameState implements GameState {
    @Override
    public void updatePanel(GamePanel gp) {
        gp.updateOnPlayState();
    }

    @Override
    public void updateInterface(UserInterface ui) {
        ui.drawTitleScreen();
    }

    @Override
    public GameState changePausedState() {
        return new PauseGameState();
    }

    @Override
    public void whatShouldIPaint(GamePanel gp, Graphics2D g2) {
        gp.paintTitle(g2);
    }
}
