package main;

import java.awt.*;

public class UserInterface {

    GamePanel gp;
    Graphics2D g2;
    Font keyFont = new Font("Arial", Font.PLAIN, 40);

    public UserInterface(GamePanel gp) {
        this.gp = gp;
    }

    public void draw(Graphics2D g2) {
        this.g2 = g2;

        g2.setFont(keyFont);
        g2.setColor(Color.white);

        gp.updateInterface();
    }

    public void drawPauseScreen() {
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN,80F));
        String text = "PAUSED";
        int x = getXForCenteredText(text);
        int y = gp.screenHeight / 2;

        g2.drawString(text, x, y);
    }

    public void drawPlayScreen() {

    }

    public int getXForCenteredText(String text) {
        int textLenght = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        return (gp.screenWidth - textLenght) / 2;
    }
}
