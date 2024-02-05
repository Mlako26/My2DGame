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

    public int invincibleCounter = 0;

    public Player(GamePanel gp, KeyHandler keyH) {
        super(gp, startingCol, startingRow);
        this.keyH = keyH;

        screenX = gp.screenWidth/2 - gp.tileSize / 2;
        screenY = gp.screenHeight/2 - gp.tileSize / 2;
    }

    public void setDefaultValues() {
        super.setDefaultValues();
        speed = 4;
        maxLife = 6;
        life = maxLife;
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

        if (isInvincible()) {
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3F));
        }

        g2.drawImage(image, screenX, screenY, null);

        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1F));

    }


    public void update() {
        if (invincibleCounter > 0) invincibleCounter--;

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

            gp.eventHandler.checkEvent();
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

        int monsterIndex = gp.updateMonsterCollisionsFor(this);
        interactWithMonster(monsterIndex);
    }

    public void pickUpObject(int i) {
        if (i == -1) return;
    }

    public void interactWithNPC(int npcIndex) {
        if (npcIndex == -1) return;

        if (gp.keyH.interactPressed) {
            gp.playerInteractedWithNPC(npcIndex);
            gp.keyH.interactPressed = false;
        }
    }

    public void interactWithMonster(int monsterIndex) {
        if (monsterIndex == -1) return;
        if (invincibleCounter == 0) {
            life--;
            invincibleCounter = 60;
        }
    }

    public boolean isInvincible() {
        return invincibleCounter > 0;
    }
}
