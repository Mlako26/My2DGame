package entity;

import main.GamePanel;
import main.KeyHandler;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Player extends Entity {

    KeyHandler keyH;

    public final int screenX;
    public final int screenY;

    public static final int startingCol = 25;
    public static final int startingRow = 42;

    public Player(GamePanel gp, KeyHandler keyH) {
        super(gp, startingCol, startingRow);
        this.keyH = keyH;

        screenX = gp.screenWidth/2 - gp.tileSize / 2;
        screenY = gp.screenHeight/2 - gp.tileSize / 2;
    }

    public void setDefaultValues() {
        speed = 4;
        direction = "down";

        int hitBoxSize = 28;
        solidArea = new Rectangle(8, 16, hitBoxSize, hitBoxSize);
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
    }

    public void getEntityImages() {
        up1 = setup("/player/up1");
        up2 = setup("/player/up2");
        down1 = setup("/player/down1");
        down2 = setup("/player/down2");
        left1 = setup("/player/left1");
        left2 = setup("/player/left2");
        right1 = setup("/player/right1");
        right2 = setup("/player/right2");
    }

    public void draw(Graphics2D g2) {
        BufferedImage image = nextImage();
        g2.drawImage(image, screenX, screenY, null);
    }


    public void update() {
        if (keyH.anyKeyIsPressed()) {

            updateAllCollisions();

            if (keyH.upPressed) {
                direction = "up";
                if (!topCollisionOn) worldY -= speed;
            }

            if (keyH.downPressed) {
                direction = "down";
                if (!bottomCollisionOn) worldY += speed;
            }

            if (keyH.leftPressed) {
                direction = "left";
                if (!leftCollisionOn) worldX -= speed;
            }

            if (keyH.rightPressed) {
                direction = "right";
                if (!rightCollisionOn) worldX += speed;
            }

            updateSpriteCounter();

        } else {
            spriteNum = 1;
        }
    }

    private void updateAllCollisions() {
        resetCollisions();

        gp.updateTileCollisionsFor(this);

        int objIndex = gp.updateObjectCollisionsFor(this);
        pickUpObject(objIndex);

        int npcIndex = gp.updateNPCCollisionsFor(this);
        interactWithNPC(npcIndex);
    }

    public void pickUpObject(int i) {
        if (i == -1) {
            return;
        }
    }

    public void interactWithNPC(int npcIndex) {
        if (npcIndex == -1) {
            return;
        }

        if (gp.keyH.interactPressed) {
            gp.playerInteractedWithNPC(npcIndex);
            gp.keyH.interactPressed = false;
        }
    }
}
