package state;

import main.GamePanel;
import main.KeyHandler;
import main.UserInterface;

import java.awt.*;

public interface GameState {

    void updatePanel(GamePanel gp);

    void updateInterface(UserInterface ui);

    void whatShouldIPaint(GamePanel gp, Graphics2D g2);

    void updateKeyHandler(KeyHandler keyH);
}
