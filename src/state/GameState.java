package state;

import main.GamePanel;
import main.UserInterface;

import java.awt.*;

public interface GameState {

    void updatePanel(GamePanel gp);

    void updateInterface(UserInterface ui);

    GameState changePausedState();

    void whatShouldIPaint(GamePanel gp, Graphics2D g2);
}
