package main;

import java.awt.*;
import java.awt.image.BufferedImage;

public class UtilityTool {

    public BufferedImage scaleImage(BufferedImage original, int width, int heigth) {
        BufferedImage scaledImage = new BufferedImage(width, heigth, original.getType());

        Graphics2D g2 = scaledImage.createGraphics();
        g2.drawImage(original, 0, 0, width, heigth, null);
        g2.dispose();

        return scaledImage;
    }

    public int getXForCenteredText(String text, Graphics2D g2, int screenWidth) {
        int textLenght = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        return (screenWidth - textLenght) / 2;
    }
}