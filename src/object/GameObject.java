package object;

import collidable.Collidable;
import main.GamePanel;
import main.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class GameObject extends Collidable {
    public BufferedImage image;
    public UtilityTool utilities = new UtilityTool();

    // --- ITEM ATTRIBUTES ---

    public int attackValue = 0;
    public int defenseValue = 0;
    public String description = "";

    // --- END OF ITEM ATTRIBUTES ---

    public GameObject(GamePanel gp, int x, int y, String name, boolean collision) {
        super(gp, x, y, collision);

        this.name = name;
        try {
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/objects/" + name + ".png")));
            image = utilities.scaleImage(image, gp.tileSize, gp.tileSize);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void draw(Graphics2D g2) {
        int screenX = gp.getScreenX(worldX);
        int screenY = gp.getScreenY(worldY);

        if (tileIsWithinBounds(gp, screenX, screenY)) {
            g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
        }
    }
}
