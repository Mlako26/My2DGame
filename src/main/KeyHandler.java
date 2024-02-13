package main;

import state.*;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {

    public boolean upPressed, downPressed, leftPressed, rightPressed, interactPressed;
    int code;
    boolean debugMode = false;
    GamePanel gp;

    public KeyHandler(GamePanel gp) {
        this.gp = gp;
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        code = e.getKeyCode();
        gp.updateKeyHandlerState();
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();

        if (code == KeyEvent.VK_W) {
            upPressed = false;
        }
        if (code == KeyEvent.VK_S) {
            downPressed = false;
        }
        if (code == KeyEvent.VK_A) {
            leftPressed = false;
        }
        if (code == KeyEvent.VK_D) {
            rightPressed = false;
        }
        if (code == KeyEvent.VK_E) {
            interactPressed = false;
        }
    }

    public boolean anyKeyIsPressed() {
        return upPressed || downPressed || leftPressed || rightPressed || interactPressed;
    }

    public void keyPressedOnDialogueState() {
        if (code == KeyEvent.VK_ENTER || code == KeyEvent.VK_E) {
            gp.gameState = new PlayGameState();
        }
    }

    public void keyPressedOnPlayState() {
        if (code == KeyEvent.VK_W) {
            upPressed = true;
        }
        if (code == KeyEvent.VK_S) {
            downPressed = true;
        }
        if (code == KeyEvent.VK_A) {
            leftPressed = true;
        }
        if (code == KeyEvent.VK_D) {
            rightPressed = true;
        }
        if (code == KeyEvent.VK_E) {
            interactPressed = true;
        }
        if (code == KeyEvent.VK_ESCAPE) {
            gp.pauseGame();
        }
        if (code == KeyEvent.VK_ENTER) {
            gp.actionKeyWasPressed();
        }
        if (code == KeyEvent.VK_I) {
            gp.openInventory();
        }

        // DEBUG
        if (code == KeyEvent.VK_T) {
            debugMode = !debugMode;
        }

        if (code == KeyEvent.VK_R) {
            gp.tileManager.loadMap("/maps/world0.txt");
        }
    }

    public void keyPressedOnPauseState() {
        if (code == KeyEvent.VK_ESCAPE) {
            gp.resumeGame();
        }
    }

    public void keyPressedOnTitleState() {
        if (code == KeyEvent.VK_W) {
            gp.goUpOneOptionInTitleScreen();
        }

        if (code == KeyEvent.VK_S) {
            gp.goDownOneOptionInTitleScreen();
        }

        if (code == KeyEvent.VK_ENTER) {
            gp.enterPressedInTitleScreen();
        }
    }

    public void keyPressedOnCharacterState() {
        if (code == KeyEvent.VK_I || code == KeyEvent.VK_ESCAPE) {
            gp.resumeGame();
        } else if (code == KeyEvent.VK_W) {
            gp.playSoundEffect(11);
            gp.ui.slotRow = Math.max(gp.ui.slotRow - 1, 0);
        } else if (code == KeyEvent.VK_S) {
            gp.playSoundEffect(11);
            gp.ui.slotRow = Math.min(gp.ui.slotRow + 1, 3);
        } else if (code == KeyEvent.VK_A) {
            gp.playSoundEffect(11);
            gp.ui.slotCol = Math.max(gp.ui.slotCol - 1, 0);
        } else if (code == KeyEvent.VK_D) {
            gp.playSoundEffect(11);
            gp.ui.slotCol = Math.min(gp.ui.slotCol + 1, 4);
        } else if (code == KeyEvent.VK_ENTER) {
            gp.player.selectItem();
        }
    }
}
