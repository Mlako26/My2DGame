package main;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class TitleScreenDrawer {
    Graphics2D g2;
    GamePanel gp;
    int titleScreenPage = 0;

    public TitleScreenDrawer( GamePanel gp) {
        this.gp = gp;
    }

    public void draw(Graphics2D g2) {
        this.g2 = g2;
        if (titleScreenPage == 0) {
            drawMenu();
        } else if (titleScreenPage == 1) {
            drawClassSelection();
        }
    }

    private void drawMenu() {
        drawTitleBackground();
        drawGameTitle();
        drawPlayerWithFlowers();
        drawTitleOptions();
    }

    private void drawTitleOptions() {
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 40F));

        String text = "NEW GAME";
        int x = getXForCenteredText(text);
        int y = (int) (gp.tileSize * 8.5);
        g2.drawString(text, x, y);
        if (command == 0) {
            g2.drawString(">", x-gp.tileSize / 2, y);
        }

        text = "LOAD GAME";
        x = getXForCenteredText(text);
        y += gp.tileSize;
        g2.drawString(text, x, y);
        if (command == 1) {
            g2.drawString(">", x-gp.tileSize / 2, y);
        }

        text = "QUIT";
        x = getXForCenteredText(text);
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

        BufferedImage flower = null;
        try {
            flower = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/objects/pinkFlower.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }

        g2.drawImage(flower, x - 3 * gp.tileSize, y, gp.tileSize * 2, gp.tileSize * 2, null);
        g2.drawImage(flower, x + 3 * gp.tileSize, y, gp.tileSize * 2, gp.tileSize * 2, null);
    }

    private void drawGameTitle() {
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 96F));

        String text = "Simple RPG Game";
        int x = getXForCenteredText(text);
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
        drawTitleBackground();
        drawClassOptions();
    }

    private void drawClassOptions() {
        g2.setColor(Color.white);
        g2.setFont(g2.getFont().deriveFont(42F));

        String text = "Select your class!";
        int x = getXForCenteredText(text);
        int y = gp.tileSize * 3;
        g2.drawString(text, x, y);

        text = "Fighter";
        x = getXForCenteredText(text);
        y += gp.tileSize * 2;
        g2.drawString(text, x ,y);
        if (command == 0) {
            g2.drawString(">", x - gp.tileSize / 2, y);
        }

        text = "Thief";
        x = getXForCenteredText(text);
        y += gp.tileSize;
        g2.drawString(text, x ,y);
        if (command == 1) {
            g2.drawString(">", x - gp.tileSize / 2, y);
        }

        text = "Mage";
        x = getXForCenteredText(text);
        y += gp.tileSize;
        g2.drawString(text, x ,y);
        if (command == 2) {
            g2.drawString(">", x - gp.tileSize / 2, y);
        }

        text = "Return";
        x = getXForCenteredText(text);
        y += gp.tileSize * 2;
        g2.drawString(text, x ,y);
        if (command == 3) {
            g2.drawString(">", x - gp.tileSize / 2, y);
        }
    }

}
