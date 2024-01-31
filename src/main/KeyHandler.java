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
}
