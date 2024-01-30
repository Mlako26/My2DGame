package collidable;

import main.GamePanel;

import java.awt.*;

public abstract class Collidable {
    public GamePanel gp;

    public int worldX, worldY;

    public boolean collisionOn;
    public Rectangle solidArea;
    public int solidAreaDefaultX = 0;
    public int solidAreaDefaultY = 0;

    public Collidable(GamePanel gp, int worldX, int worldY, boolean collision) {
        this.gp = gp;
        this.worldX = worldX * gp.tileSize;
        this.worldY = worldY * gp.tileSize;
        this.collisionOn = collision;
        solidArea = new Rectangle(solidAreaDefaultX, solidAreaDefaultY, gp.tileSize, gp.tileSize);
    }

    public boolean tileIsWithinBounds(GamePanel gp, int screenX, int screenY) {
        return  screenX + gp.tileSize > 0 &&
                screenX - gp.tileSize < gp.screenWidth &&
                screenY + gp.tileSize > 0 &&
                screenY - gp.tileSize < gp.screenHeight;
    }

    public abstract void draw(Graphics2D g2);
}
