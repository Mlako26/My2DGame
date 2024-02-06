package main;

import javax.swing.*;

import state.PlayGameState;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.Objects;

public class TitleScreenDrawer implements ActionListener {
    GamePanel gp;
    UtilityTool uTool = new UtilityTool();
    Graphics2D g2;

    int titleScreenPage = 0;
    int command = 0;

    private final ImageIcon animatedGif;

    public TitleScreenDrawer(GamePanel gp) {
        this.gp = gp;

        // Load the animated GIF (replace "your_animation.gif" with the actual path to your GIF)
        animatedGif = new ImageIcon(Objects.requireNonNull(getClass().getResource("/titleScreen/background.gif")));

        // Set up Timer to repaint the panel every 100 milliseconds
        int delay = 100;  // Milliseconds between repaints
        Timer timer = new Timer(delay, this);
        timer.start();
    }

    public void draw(Graphics2D g2) {
        this.g2 = g2;
        g2.drawImage(animatedGif.getImage(),0,0,gp.screenWidth, gp.screenHeight, gp);
        if (titleScreenPage == 0) {
            drawMenu();
        } else if (titleScreenPage == 1) {
            drawClassSelection();
        }
    }

    private void drawMenu() {
       // drawTitleBackground();
        drawGameTitle();
        drawPlayerWithFlowers();
        drawTitleOptions();
    }

    private void drawTitleOptions() {
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 40F));

        String text = "NEW GAME";
        int x = uTool.getXForCenteredText(text, g2, gp.screenWidth);
        int y = (int) (gp.tileSize * 8.5);
        g2.drawString(text, x, y);
        if (command == 0) {
            g2.drawString(">", x-gp.tileSize / 2, y);
        }

        text = "LOAD GAME";
        x = uTool.getXForCenteredText(text, g2, gp.screenWidth);
        y += gp.tileSize;
        g2.drawString(text, x, y);
        if (command == 1) {
            g2.drawString(">", x-gp.tileSize / 2, y);
        }

        text = "QUIT";
        x = uTool.getXForCenteredText(text, g2, gp.screenWidth);
        y += gp.tileSize;
        g2.drawString(text, x, y);
        if (command == 2) {
            g2.drawString(">", x-gp.tileSize / 2, y);
        }
    }

    private void drawPlayerWithFlowers() {
        int x = gp.screenWidth / 2 - gp.tileSize;
        int y = gp.tileSize * 5;

        g2.drawImage(gp.player.down1, x, y, gp.tileSize * 2, gp.tileSize * 2, null);
//
//        BufferedImage flower = null;
//        try {
//            flower = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/objects/pinkFlower.png")));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        g2.drawImage(flower, x - 3 * gp.tileSize, y, gp.tileSize * 2, gp.tileSize * 2, null);
//        g2.drawImage(flower, x + 3 * gp.tileSize, y, gp.tileSize * 2, gp.tileSize * 2, null);
    }

    private void drawGameTitle() {
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 96F));

        String text = "Simple RPG Game";
        int x = uTool.getXForCenteredText(text, g2, gp.screenWidth);
        int y = 3 * gp.tileSize;

        // Draws Shadows
        g2.setColor(Color.black);
        g2.drawString(text, x + 5, y + 5);

        // Draws Actual Title
        g2.setColor(Color.white);
        g2.drawString(text, x, y);
    }

    private void drawTitleBackground() {
        g2.setColor(Color.darkGray);
        g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
    }

    private void drawClassSelection() {
       // drawTitleBackground();
        drawClassOptions();
    }

    private void drawClassOptions() {
        g2.setColor(Color.white);
        g2.setFont(g2.getFont().deriveFont(42F));

        String text = "Select your class!";
        int x = uTool.getXForCenteredText(text, g2, gp.screenWidth);
        int y = gp.tileSize * 3;
        g2.drawString(text, x, y);

        text = "Fighter";
        x = uTool.getXForCenteredText(text, g2, gp.screenWidth);
        y += gp.tileSize * 2;
        g2.drawString(text, x ,y);
        if (command == 0) {
            g2.drawString(">", x - gp.tileSize / 2, y);
        }

        text = "Thief";
        x = uTool.getXForCenteredText(text, g2, gp.screenWidth);
        y += gp.tileSize;
        g2.drawString(text, x ,y);
        if (command == 1) {
            g2.drawString(">", x - gp.tileSize / 2, y);
        }

        text = "Mage";
        x = uTool.getXForCenteredText(text, g2, gp.screenWidth);
        y += gp.tileSize;
        g2.drawString(text, x ,y);
        if (command == 2) {
            g2.drawString(">", x - gp.tileSize / 2, y);
        }

        text = "Return";
        x = uTool.getXForCenteredText(text, g2, gp.screenWidth);
        y += gp.tileSize * 2;
        g2.drawString(text, x ,y);
        if (command == 3) {
            g2.drawString(">", x - gp.tileSize / 2, y);
        }
    }

    public void goUpOneOptionInTitleScreen() {
        if (titleScreenPage == 0) {
            command = (((command - 1) % 3) + 3) % 3;
        } else if (titleScreenPage == 1) {
            command = (((command - 1) % 4) + 4) % 4;
        }
    }

    public void goDownOneOptionInTitleScreen() {
        if (titleScreenPage == 0) {
            command = (command + 1) % 3;
        } else if (titleScreenPage == 1) {
            command = (command + 1) % 4;
        }
    }

    public void enterPressedInTitleScreen() {
        if (titleScreenPage == 0) {
            switch (command) {
                case 0:
                    titleScreenPage = 1;
                    break;
                case 1:
                    break;
                case 2:
                    System.exit(0);
            }
        } else if (titleScreenPage == 1) {
            switch (command) {
                case 0:
                case 1:
                case 2:
                    gp.gameState = new PlayGameState();
                    gp.stopMusic();
                    gp.playMusic(0);
                    break;
                case 3:
                    titleScreenPage = 0;
                    command = 0;
                    break;
            }
        }
        
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        gp.repaint();
    }
}
