package main;

import object.OBJ_Heart;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

public class UserInterface {

    GamePanel gp;
    Graphics2D g2;
    Font defaultFont;

    public String currentDialogue = "";

    public int command = 0;
    public int titleScreenPage = 0;

    BufferedImage heartFull, heartHalf, heartEmpty;

    public UserInterface(GamePanel gp) {
        this.gp = gp;

        InputStream is = getClass().getResourceAsStream("/font/x12y16pxMaruMonica.ttf");
        try {
            assert is != null;
            defaultFont = Font.createFont(Font.TRUETYPE_FONT, is);
        } catch (FontFormatException | IOException e) {
            throw new RuntimeException(e);
        }

        OBJ_Heart heart = new OBJ_Heart(gp, 0,0);
        heartFull = heart.image;
        heartHalf = heart.image2;
        heartEmpty = heart.image3;
    }

    public void draw(Graphics2D g2) {
        this.g2 = g2;

        g2.setFont(defaultFont);
        g2.setColor(Color.white);

        gp.updateInterface();
    }

    public void drawTitleScreen() {
        if (titleScreenPage == 0) {
            drawMenu();
        } else if (titleScreenPage == 1) {
            drawClassSelection();

        }
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

    public void drawPauseScreen() {
        drawPlayerLife();
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 80F));
        String text = "PAUSED";
        int x = getXForCenteredText(text);
        int y = gp.screenHeight / 2;

        g2.drawString(text, x, y);
    }

    public void drawPlayScreen() {
        drawPlayerLife();
    }

    public void drawDialogueScreen() {
        drawPlayerLife();

        // DIALOGUE WINDOW
        int x = gp.tileSize * 2;
        int y = gp.tileSize / 2;
        int width = gp.screenWidth - 4 * gp.tileSize;
        int height = gp.tileSize * 4;

        drawSubWindow(x, y, width, height);

        x += gp.tileSize;
        y += gp.tileSize;
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 28));

        for (String line : currentDialogue.split("\n")) {
            g2.drawString(line, x, y);
            y += 35;
        }
    }

    public void drawPlayerLife() {
        int y = gp.tileSize/2;

        // Draw max life
        for (int i = 0, x = gp.tileSize/2; i < gp.player.maxLife/2; i++, x+=gp.tileSize) {
            g2.drawImage(heartEmpty, x, y, null);
        }

        // Draw current life
        int x = gp.tileSize/2;
        for (int i = 0; i < gp.player.life/2; i++, x+=gp.tileSize) {
            g2.drawImage(heartFull, x, y, null);
        }

        if (gp.player.life % 2 == 1) {
            g2.drawImage(heartHalf, x, y, null);
        }

    }

    public void drawSubWindow(int x, int y, int width, int height) {
        Color c = new Color(0, 0, 0, 210);
        g2.setColor(c);
        g2.fillRoundRect(x, y, width, height, 35, 35);

        c = new Color(255, 255, 255);
        g2.setColor(c);
        g2.setStroke(new BasicStroke(5));
        g2.drawRoundRect(x+5, y+5, width-10, height-10, 25, 25);
    }

    public int getXForCenteredText(String text) {
        int textLenght = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        return (gp.screenWidth - textLenght) / 2;
    }

    public void updateDialogue(String newDialogue) {
        this.currentDialogue = newDialogue;
    }
}
