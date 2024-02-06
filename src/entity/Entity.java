package entity;

import collidable.Collidable;
import main.GamePanel;
import main.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

public class Entity extends Collidable {
    // --- MOVEMENT ---

    public int speed;
    public String direction;

    // --- END OF MOVEMENT ---

    // --- SPRITES ---

    public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2;
    public BufferedImage attackUp1, attackUp2, attackDown1, attackDown2, attackLeft1, attackLeft2, attackRight1, attackRight2;
    public int spriteCounter = 0;
    public int spriteNum = 1;
    public int actionLockCounter = 0;

    // --- END SPRITES ---

    // --- COLLISION ---

    public boolean topCollisionOn = false;
    public boolean bottomCollisionOn = false;
    public boolean leftCollisionOn = false;
    public boolean rightCollisionOn = false;

    public Rectangle attackArea = new Rectangle(0,0,0,0);

    // --- END OF COLLISION ---

    // --- DIALOGUES ---

    ArrayList<String> dialogue = new ArrayList<>();
    int dialogueIndex = -1;

    // -- END OF DIALOGUES ---

    // --- CHARACTER STATUS ---

    public int maxLife;
    public int life;
    public boolean attacking;
    public int invincibleCounter = 0;

    // --- END OF CHARACTER STATUS ---

    public Entity(GamePanel gp, int worldX, int worldY) {
        super(gp, worldX, worldY, true);
        setDefaultValues();
        getEntityImages();
    }

    public void getEntityImages() {

    }

    public void setDefaultValues() {
        direction = "down";

        int hitBoxSize = 28;
        solidArea = new Rectangle(8, 16, hitBoxSize, hitBoxSize);
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
    }

    public BufferedImage setup(String imagePath, int width, int height) {
        UtilityTool tool = new UtilityTool();
        BufferedImage scaledImage = null;
        try {
            BufferedImage original = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(imagePath + ".png")));
            scaledImage = tool.scaleImage(original, width, height);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return scaledImage;
    }

    public boolean isInvincible() {
        return invincibleCounter > 0;
    }
    public void draw(Graphics2D g2) {
        int screenX = gp.getScreenX(worldX);
        int screenY = gp.getScreenY(worldY);

        if (tileIsWithinBounds(gp, screenX, screenY)) {
            BufferedImage image = nextImage();

            if (isInvincible()) {
                g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.4F));
            }

            g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);

            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1F));
        }
    }

    public void update() {
        setAction();

        resetCollisions();

        gp.updateTileCollisionsFor(this);
        gp.updateMonsterCollisionsFor(this);
        gp.updateNPCCollisionsFor(this);
        if (gp.updatePlayerCollisionFor(this)) {
            collidedWithPlayer();
        }

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
        if (invincibleCounter > 0) invincibleCounter--;

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
                if (attacking) {
                    if (spriteNum == 1) image = attackUp1;
                    else image = attackUp2;
                } else {
                    if (spriteNum == 1) image = up1;
                    else image = up2;
                }
                break;
            case "down":
                if (attacking) {
                    if (spriteNum == 1) image = attackDown1;
                    else image = attackDown2;
                } else {
                    if (spriteNum == 1) image = down1;
                    else image = down2;
                }
                break;
            case "right":
                if (attacking) {
                    if (spriteNum == 1) image = attackRight1;
                    else image = attackRight2;
                } else {
                    if (spriteNum == 1) image = right1;
                    else image = right2;
                }
                break;
            case "left":
                if (attacking) {
                    if (spriteNum == 1) image = attackLeft1;
                    else image = attackLeft2;
                } else {
                    if (spriteNum == 1) image = left1;
                    else image = left2;
                }
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

        if (dialogueIndex <  dialogue.size() - 1) dialogueIndex++;
        return dialogue.get(dialogueIndex);
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

    public void collidedWithPlayer() {

    }

    public void takeHit() {
        if (isInvincible()) {
            return;
        }
        resetInvincibleCounter();
        life--;
    }

    private void resetInvincibleCounter() {
        invincibleCounter = 40;
    }

    public boolean isDead() {
        return life == 0;
    }
}
