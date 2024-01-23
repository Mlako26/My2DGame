package entity;

import main.GamePanel;
import main.KeyHandler;
import main.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class Player extends Entity{

    GamePanel gamePanel;
    KeyHandler keyH;

    // Player drawn at this position
    public final int screenX;
    public final int screenY;

    // Inventory
    public int keys = 0;

    public Player(GamePanel gamePanel, KeyHandler keyH) {
        this.gamePanel = gamePanel;
        this.keyH = keyH;

        screenX = gamePanel.screenWidth/2 - gamePanel.tileSize / 2;
        screenY = gamePanel.screenHeight/2 - gamePanel.tileSize / 2;

        int hitBoxSize = 28;
        solidArea = new Rectangle(8, 16, hitBoxSize, hitBoxSize);
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        setDefaultValues();
        getPlayerImage();
    }

    public void setDefaultValues() {
        worldX = gamePanel.tileSize * 25;
        worldY = gamePanel.tileSize * 42;
        speed = 4;
        direction = "down";
    }

    public void getPlayerImage() {
        up1 = setup("up1");
        up2 = setup("up2");
        down1 = setup("down1");
        down2 = setup("down2");
        left1 = setup("left1");
        left2 = setup("left2");
        right1 = setup("right1");
        right2 = setup("right2");
    }

    public BufferedImage setup(String name) {
        UtilityTool tool = new UtilityTool();
        BufferedImage scaledImage = null;
        try {
            BufferedImage original = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/" + name + ".png")));
            scaledImage = tool.scaleImage(original, gamePanel.tileSize, gamePanel.tileSize);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return scaledImage;
    }

    public void update() {
        if (keyH.anyKeyIsPressed()) {

            resetCollisions();
            gamePanel.updateCollisionsFor(this);

            int objIndex = gamePanel.collisionDetector.checkObject(this, true);
            pickUpObject(objIndex);

            if (keyH.upPressed && !topCollisionOn) {
                worldY -= speed;
                direction = "up";
            }

            if (keyH.downPressed && !bottomCollisionOn) {
                worldY += speed;
                direction = "down";
            }

            if (keyH.leftPressed && !leftCollisionOn) {
                worldX -= speed;
                direction = "left";
            }

            if (keyH.rightPressed && !rightCollisionOn) {
                worldX += speed;
                direction = "right";
            }


            spriteCounter++;
            if (spriteCounter > 12) {
                if (spriteNum == 1) {
                    spriteNum = 2;
                } else if (spriteNum == 2) {
                    spriteNum = 1;
                }

                spriteCounter = 0;
            }
        } else {
            spriteNum = 1;
        }

    }

    private void resetCollisions() {
        topCollisionOn = false;
        bottomCollisionOn = false;
        rightCollisionOn = false;
        leftCollisionOn = false;
    }

    public void pickUpObject(int i) {
        if (i == -1) {
            return;
        }
        String objectName = gamePanel.objects.get(i).name;

        switch(objectName) {
            case "key":
                gamePanel.playerGrabbedKey(i);
                keys++;
                break;
            case "door":
                if (keys > 0) {
                    gamePanel.playerOpenedDoor(i);
                    keys--;
                } else {
                    gamePanel.playerCantOpenDoor();
                }
                break;
            case "boots":
                gamePanel.playerGrabbedBoots(i);
                speed += 2;
                break;
            case "chest":
                gamePanel.playerOpenedChest();
                break;
        }
    }

    public void draw(Graphics2D g2) {
        BufferedImage image = null;

        switch(direction) {
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

        g2.drawImage(image, screenX, screenY, null);
    }

}
