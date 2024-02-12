package entity;

import collidable.Collidable;
import main.GamePanel;
import main.UtilityTool;
import object.GameObject;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

public class Entity extends Collidable {
    public int startingX, startingY;

    // --- MOVEMENT ---

    public int speed;
    public String direction;

    // --- END OF MOVEMENT ---

    // --- SPRITES ---

    public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2;
    public BufferedImage attackUp1, attackUp2, attackDown1, attackDown2, attackLeft1, attackLeft2, attackRight1, attackRight2;
    public int spriteCounter = 0;
    public int spriteNum = 1;
    public final int deathAnimationFrameCount = 40;
    int showHPBarCounter = 0;
    public int invincibleCounter = 0;
    int dyingCounter = 0;

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

    public int maxLife = 4;
    public int life = 4;
    public boolean attacking;
    public int level;
    public int strength;
    public int dexterity;
    public int attack;
    public int defense;
    public int exp;
    public int nextLevelExp;
    public int coins;
    public GameObject currentWeapon;
    public GameObject currentShield;

    // --- END OF CHARACTER STATUS ---

    public Entity(GamePanel gp, int worldX, int worldY) {
        super(gp, worldX, worldY, true);
        startingX = worldX * gp.tileSize;
        startingY = worldY * gp.tileSize;

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
            drawHPBar(g2, screenX, screenY);
            drawEntity(g2, screenX, screenY);
        }
    }

    private void drawEntity(Graphics2D g2, int screenX, int screenY) {
        BufferedImage image = nextImage();

        if (isInvincible()) {
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.4F));
        }

        if (isDying()) {
            dyingAnimation(g2);
        }
        g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);

        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1F));
    }

    private void drawHPBar(Graphics2D g2, int screenX, int screenY) {
        if (showHPBarCounter > 0) {
            showHPBarCounter--;
            g2.setColor(new Color(35,35,35));
            g2.fillRect(screenX-2, screenY - 17, gp.tileSize + 4, 14);
            g2.setColor(new Color(255,0,30));
            g2.fillRect(screenX, screenY - 15, (gp.tileSize / maxLife) * life, 10);
        }
    }

    private void dyingAnimation(Graphics2D g2) {
        if (dyingCounter > deathAnimationFrameCount) {
            return;
        }

        if ((dyingCounter % 10) <= 4) {
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0F));
        } else {
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1F));
        }

        dyingCounter++;
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

        if (i <= 25 || worldY >= startingY + 5 * gp.tileSize) {
            direction = "up";
        } else if (i <= 50 || worldY <= startingY - 5 * gp.tileSize) {
            direction = "down";
        } else if (i <= 75 || worldX >= startingX + 5 * gp.tileSize) {
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

    public void takeHit(int attackDamage) {
        if (isInvincible()) {
            return;
        }

        gp.ui.addMessage(attackDamage + " damage!");
        gp.playSoundEffect(7);

        int damageTaken = Math.max(attackDamage - defense, 0);
        life = Math.max(life - damageTaken, 0);


        if (isDying()) gp.entityKilled(this);

        resetInvincibleCounter();
        resetHPBarCounter();
    }

    private void resetHPBarCounter() {
        showHPBarCounter = 600;
    }

    private void resetInvincibleCounter() {
        invincibleCounter = 40;
    }

    protected boolean isDying() {
        return life == 0;
    }

    public boolean isDead() {
        return life == 0 && dyingCounter > deathAnimationFrameCount;
    }

    public boolean isAlive() {
        return !isDead();
    }
}
