package entity;

import collidable.Collidable;
import main.GamePanel;
import main.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;
import java.util.Random;

public class Entity extends Collidable {
    // --- MOVEMENT ---

    public int speed;
    public String direction;

    // --- END OF MOVEMENT ---

    // --- SPRITES ---

    public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2;
    public int spriteCounter = 0;
    public int spriteNum = 1;
    public int actionLockCounter = 0;

    // --- END SPRITES ---

    // --- COLLISION ---

    public boolean topCollisionOn = false;
    public boolean bottomCollisionOn = false;
    public boolean leftCollisionOn = false;
    public boolean rightCollisionOn = false;

    // --- END OF COLLISION ---

    // --- DIALOGUES ---

    String[] dialogue = new String[20];
    int dialogueIndex = 0;

    // -- END OF DIALOGUES ---

    public Entity(GamePanel gp, int worldX, int worldY) {
        super(gp, worldX, worldY, true);
        setDefaultValues();
        getEntityImages();
    }

    public void getEntityImages() {

    }

    public void setDefaultValues() {

    }

    public BufferedImage setup(String imagePath) {
        UtilityTool tool = new UtilityTool();
        BufferedImage scaledImage = null;
        try {
            BufferedImage original = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(imagePath + ".png")));
            scaledImage = tool.scaleImage(original, gp.tileSize, gp.tileSize);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return scaledImage;
    }

    public void draw(Graphics2D g2) {
        int screenX = gp.getScreenX(worldX);
        int screenY = gp.getScreenY(worldY);

        if (tileIsWithinBounds(gp, screenX, screenY)) {
            BufferedImage image = nextImage();
            g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
        }
    }

    public void update() {
        setAction();

        resetCollisions();

        gp.updateTileCollisionsFor(this);
        gp.updatePlayerCollisionFor(this);

        if (direction.equals("up") && !topCollisionOn) {
            worldY -= speed;
        } else if (direction.equals("down") && !bottomCollisionOn) {
            worldY += speed;
        } else if (direction.equals("left") && !leftCollisionOn) {
            worldX -= speed;
        } else if (direction.equals("right") && !rightCollisionOn) {
            worldX += speed;
        }

        updateSpriteCounter();
    }

    public void updateSpriteCounter() {
        spriteCounter++;
        if (spriteCounter > 12) {
            if (spriteNum == 1) {
                spriteNum = 2;
            } else if (spriteNum == 2) {
                spriteNum = 1;
            }

            spriteCounter = 0;
        }
    }

    public BufferedImage nextImage() {
        BufferedImage image = null;
        switch (direction) {
            case "up":
                if (spriteNum == 1) image = up1;
                else image = up2;
                break;
            case "down":
                if (spriteNum == 1) image = down1;
                else image = down2;
                break;
            case "right":
                if (spriteNum == 1) image = right1;
                else image = right2;
                break;
            case "left":
                if (spriteNum == 1) image = left1;
                else image = left2;
                break;
        }
        return image;
    }

    public void setAction() {
        actionLockCounter++;

        if (actionLockCounter < 120) {
            return;
        }

        Random random = new Random();
        int i = random.nextInt(100) + 1;

        if (i <= 25) {
            direction = "up";
        } else if (i <= 50) {
            direction = "down";
        } else if (i <= 75) {
            direction = "left";
        } else {
            direction = "right";
        }

        actionLockCounter = 0;
    }

    public void resetCollisions() {
        topCollisionOn = false;
        bottomCollisionOn = false;
        rightCollisionOn = false;
        leftCollisionOn = false;
    }

    public String speak() {
        faceThePlayer();

        if (dialogueIndex <  dialogue.length) dialogueIndex++;
        return dialogue[dialogueIndex - 1];
    }
    
    public void faceThePlayer() {
        switch (gp.player.direction) {
            case "up":
                direction = "down";
                break;
            case "down":
                direction = "up";
                break;
            case "left":
                direction = "right";
                break;
            case "right":
                direction = "left";
                break;
        }
    }
}
