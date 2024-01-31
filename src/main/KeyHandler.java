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
    }

    public boolean anyKeyIsPressed() {
        return upPressed || downPressed || leftPressed || rightPressed;
    }

    public void keyPressedOnDialogueState() {
        if (code == KeyEvent.VK_ENTER) {
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


        // DEBUG
        if (code == KeyEvent.VK_T) {
            debugMode = !debugMode;
        }
    }

    public void keyPressedOnPauseState() {
        if (code == KeyEvent.VK_ESCAPE) {
            gp.resumeGame();
        }
    }

    public void keyPressedOnTitleState() {
        if (gp.ui.titleScreenPage == 0) {
            if (code == KeyEvent.VK_W) {
                gp.ui.command = (((gp.ui.command - 1) % 3) + 3) % 3;
            }

            if (code == KeyEvent.VK_S) {
                gp.ui.command = (gp.ui.command + 1) % 3;
            }

            if (code == KeyEvent.VK_ENTER) {
                switch (gp.ui.command) {
                    case 0:
                        gp.ui.titleScreenPage = 1;
                        break;
                    case 1:
                        break;
                    case 2:
                        System.exit(0);
                }
            }
        } else if (gp.ui.titleScreenPage == 1) {
            if (code == KeyEvent.VK_W) {
                gp.ui.command = (((gp.ui.command - 1) % 4) + 4) % 4;
            }

            if (code == KeyEvent.VK_S) {
                gp.ui.command = (gp.ui.command + 1) % 4;
            }

            if (code == KeyEvent.VK_ENTER) {
                switch (gp.ui.command) {
                    case 0:
                    case 1:
                    case 2:
                        gp.gameState = new PlayGameState();
                        gp.playMusic(0);
                        break;
                    case 3:
                        gp.ui.titleScreenPage = 0;
                        gp.ui.command = 0;
                        break;
                }
            }
        }
    }
}
