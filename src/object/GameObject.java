package object;

import main.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;

public class GameObject {
    public BufferedImage image;
    public String name;
    public boolean collision = false;
    public int worldX, worldY;

    public void draw(Graphics2D g2, GamePanel gp) {

        int screenX = gp.getScreenX(worldX);
        int screenY = gp.getScreenY(worldY);

        if (tileIsWithinBounds(gp, screenX, screenY)) {
            g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
        }

    }

    private boolean tileIsWithinBounds(GamePanel gp, int screenX, int screenY) {
        return  screenX + gp.tileSize > 0 &&
                screenX - gp.tileSize < gp.screenWidth &&
                screenY + gp.tileSize > 0 &&
                screenY - gp.tileSize < gp.screenHeight;
    }
}
