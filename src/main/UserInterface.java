package main;

import object.OBJ_Key;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.text.DecimalFormat;

public class UserInterface {

    GamePanel gp;
    public boolean gameIsFinished = false;
    Font keyFont = new Font("Arial", Font.PLAIN, 40);
    Font winningFont = new Font("Arial", Font.BOLD, 65);
    double playTime = 0.0;
    DecimalFormat decimalFormat = new DecimalFormat("#0.00");

    // Images
    BufferedImage keyImage;

    // Message
    public boolean messageOn = false;
    public String message = "";
    int messageRemainingFrames = 0;


    public UserInterface(GamePanel gp) {
        this.gp = gp;
        OBJ_Key key = new OBJ_Key(gp,0,0);
        keyImage = key.image;
    }

    public void draw(Graphics2D g2) {

        if (gameIsFinished) {
            g2.setFont(keyFont);
            g2.setColor(Color.white);

            String winningText = "FELICIDADES";
            int textLength = (int) g2.getFontMetrics().getStringBounds(winningText, g2).getWidth();
            int x = gp.screenWidth/2 - textLength / 2;
            int y = gp.screenHeight/2 - gp.tileSize * 3;
            g2.drawString(winningText,x,y);

            winningText = "Tu tiempo fue: " + decimalFormat.format(playTime);
            textLength = (int) g2.getFontMetrics().getStringBounds(winningText, g2).getWidth();
            x = gp.screenWidth/2 - textLength / 2;
            y = gp.screenHeight/2 + gp.tileSize * 4;
            g2.drawString(winningText,x,y);


            g2.setFont(winningFont);
            g2.setColor(Color.YELLOW);
            winningText = "Terminaste el juego!";
            textLength = (int) g2.getFontMetrics().getStringBounds(winningText, g2).getWidth();
            x = gp.screenWidth/2 - textLength / 2;
            y = gp.screenHeight/2 + gp.tileSize * 3;
            g2.drawString(winningText, x, y);

            return;
        }

        g2.setFont(keyFont);
        g2.setColor(Color.white);

        g2.drawImage(keyImage, gp.tileSize / 2, gp.tileSize / 2, gp.tileSize, gp.tileSize, null);
        g2.drawString("x " + gp.player.keys, 75, 65);

        playTime += (double) 1/60;
        g2.drawString("Time: " + decimalFormat.format(playTime), gp.tileSize * 11, 65);

        if (messageRemainingFrames > 0) {
            g2.setFont(g2.getFont().deriveFont(30F));
            g2.drawString(message, 30, gp.screenHeight - 50);
            messageRemainingFrames--;
        }
    }

    public void displayMessage(String text) {
        message = text;
        messageOn = true;
        messageRemainingFrames = 2 * gp.FPS;
    }
}
