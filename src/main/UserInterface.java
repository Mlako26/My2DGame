package main;

import object.OBJ_Heart;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class UserInterface {

    GamePanel gp;
    Graphics2D g2;
    Font defaultFont;

    public String currentDialogue = "";

    TitleScreenDrawer titleScreenDrawer;

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

        OBJ_Heart heart = new OBJ_Heart(gp, 0, 0);
        heartFull = heart.image;
        heartHalf = heart.image2;
        heartEmpty = heart.image3;

        titleScreenDrawer = new TitleScreenDrawer(gp);
    }

    public void draw(Graphics2D g2) {
        this.g2 = g2;

        g2.setFont(defaultFont);
        g2.setColor(Color.white);

        gp.updateInterface();
    }

    public void drawTitleScreen() {
        titleScreenDrawer.draw(g2);
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

    public void goUpOneOptionInTitleScreen() {
        titleScreenDrawer.goUpOneOptionInTitleScreen();
    }

    public void goDownOneOptionInTitleScreen() {
       titleScreenDrawer.goDownOneOptionInTitleScreen();
    }

    public void enterPressedInTitleScreen() {
        titleScreenDrawer.enterPressedInTitleScreen();
    }
}
