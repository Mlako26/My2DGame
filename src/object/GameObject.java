package object;

import main.GamePanel;
import main.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class GameObject {
    public BufferedImage image;
    public String name;
    public boolean collision;
    public int worldX, worldY;
    public Rectangle solidArea;
    public int solidAreaDefaultX = 0;
    public int solidAreaDefaultY = 0;
    public UtilityTool utilities = new UtilityTool();

    public GameObject(GamePanel gp, int x, int y, String name, boolean collision) {
        this.name = name;

        try {
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/objects/" + name + ".png")));
            image = utilities.scaleImage(image, gp.tileSize, gp.tileSize);
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.worldX = x * gp.tileSize;
        this.worldY = y * gp.tileSize;
        this.collision = collision;
        solidArea = new Rectangle(0, 0, gp.tileSize, gp.tileSize);
    }

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
