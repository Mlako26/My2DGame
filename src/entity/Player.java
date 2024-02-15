package entity;

import main.GamePanel;
import main.KeyHandler;
import object.*;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Player extends Entity {

    KeyHandler keyH;

    public final int screenX;
    public final int screenY;

    public static final int startingCol = 25;
    public static final int startingRow = 26;

    public ArrayList<GameObject> inventory = new ArrayList<>();
    public int maxInventorySize = 20;

    public Player(GamePanel gp, KeyHandler keyH) {
        super(gp, startingCol, startingRow);
        this.keyH = keyH;

        screenX = gp.screenWidth/2 - gp.tileSize / 2;
        screenY = gp.screenHeight/2 - gp.tileSize / 2;

        setInitialInventory();
        getSwordAttackImages();
    }

    public void setDefaultValues() {
        super.setDefaultValues();
        speed = 4;
        maxLife = 6;
        life = maxLife;

        level = 1;
        strength = 1;
        dexterity = 1;
        exp = 0;
        nextLevelExp = 5;
        coins = 0;
        currentWeapon = new OBJ_Sword_Iron(gp,0,0);
        currentShield = new OBJ_Shield_Wooden(gp,0,0);
        attack = getAttack();
        defense = getDefense();
    }

    private int getDefense() {
        return dexterity * currentShield.defenseValue;
    }

    private int getAttack() {
        return strength * currentWeapon.attackValue;
    }

    public void setInitialInventory() {
        inventory.add(currentWeapon);
        inventory.add(currentShield);
        inventory.add(new OBJ_Key(gp, 0,0));

    }

    public void getEntityImages() {
        up1 = setup("/player/up1", gp.tileSize, gp.tileSize);
        up2 = setup("/player/up2", gp.tileSize, gp.tileSize);
        down1 = setup("/player/down1", gp.tileSize, gp.tileSize);
        down2 = setup("/player/down2", gp.tileSize, gp.tileSize);
        left1 = setup("/player/left1", gp.tileSize, gp.tileSize);
        left2 = setup("/player/left2", gp.tileSize, gp.tileSize);
        right1 = setup("/player/right1", gp.tileSize, gp.tileSize);
        right2 = setup("/player/right2", gp.tileSize, gp.tileSize);
    }

    public void getSwordAttackImages() {
        attackUp1 = setup("/player/attack/player_ironSword_up1", gp.tileSize, gp.tileSize * 2);
        attackUp2 = setup("/player/attack/player_ironSword_up2", gp.tileSize, gp.tileSize * 2);
        attackDown1 = setup("/player/attack/player_ironSword_down1", gp.tileSize, gp.tileSize * 2);
        attackDown2 = setup("/player/attack/player_ironSword_down2", gp.tileSize, gp.tileSize * 2);
        attackLeft1 = setup("/player/attack/player_ironSword_left1", gp.tileSize * 2, gp.tileSize);
        attackLeft2 = setup("/player/attack/player_ironSword_left2", gp.tileSize * 2, gp.tileSize);
        attackRight1 = setup("/player/attack/player_ironSword_right1", gp.tileSize * 2, gp.tileSize);
        attackRight2 = setup("/player/attack/player_ironSword_right2", gp.tileSize * 2, gp.tileSize);
    }

    public void getAxeAttackImages() {
        attackUp1 = setup("/player/attack/player_ironAxe_up1", gp.tileSize, gp.tileSize * 2);
        attackUp2 = setup("/player/attack/player_ironAxe_up2", gp.tileSize, gp.tileSize * 2);
        attackDown1 = setup("/player/attack/player_ironAxe_down1", gp.tileSize, gp.tileSize * 2);
        attackDown2 = setup("/player/attack/player_ironAxe_down2", gp.tileSize, gp.tileSize * 2);
        attackLeft1 = setup("/player/attack/player_ironAxe_left1", gp.tileSize * 2, gp.tileSize);
        attackLeft2 = setup("/player/attack/player_ironAxe_left2", gp.tileSize * 2, gp.tileSize);
        attackRight1 = setup("/player/attack/player_ironAxe_right1", gp.tileSize * 2, gp.tileSize);
        attackRight2 = setup("/player/attack/player_ironAxe_right2", gp.tileSize * 2, gp.tileSize);
    }

    public void draw(Graphics2D g2) {
        BufferedImage image = nextImage();

        if (isInvincible()) {
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3F));
        }
        int temporaryScreenX = screenX;
        int temporaryScreenY = screenY;

        if (attacking) {
            if (direction.equals("up")) {
                temporaryScreenY -= gp.tileSize;
            } else if (direction.equals("left")) {
                temporaryScreenX -= gp.tileSize;
            }
        }

        g2.drawImage(image, temporaryScreenX, temporaryScreenY, null);

        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1F));

    }


    public void update() {
        if (invincibleCounter > 0) invincibleCounter--;

        if (attacking) {
            updateOnAttack();
        } else if (keyH.anyKeyIsPressed()) {
            updateOnMovement();
        } else {
            spriteNum = 1;
        }
    }

    private void updateOnMovement() {
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
    }

    private void updateOnAttack() {
        spriteCounter++;

        if (spriteCounter <= 10) {
            spriteNum = 1;
        } else if (spriteCounter <= 25) {
            spriteNum = 2;
            checkAttackCollision();
        } else {
            spriteNum = 1;
            spriteCounter = 0;
            attacking = false;
        }
    }

    private void checkAttackCollision() {
        // Save current player values
        int currentWorldX = worldX;
        int currentWorldY = worldY;
        int solidAreaWidth = solidArea.width;
        int solidAreaHeight = solidArea.height;

        // Change players hit-box to match the weapon's
        switch(direction) {
            case "up": worldY -= currentWeapon.attackArea.height; break;
            case "down": worldY += currentWeapon.attackArea.height; break;
            case "left": worldX -= currentWeapon.attackArea.width; break;
            case "right": worldX += currentWeapon.attackArea.width; break;
        }
        solidArea.width = currentWeapon.attackArea.width;
        solidArea.height = currentWeapon.attackArea.height;

        int monsterIndex = gp.updateMonsterCollisionsFor(this);

        // Reset values
        worldX = currentWorldX;
        worldY = currentWorldY;
        solidArea.width = solidAreaWidth;
        solidArea.height = solidAreaHeight;

        if (monsterIndex != -1) {
            gp.attackMonster(monsterIndex, attack);
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

    public void pickUpObject(int objectIndex) {
        if (objectIndex == -1) return;
        String text = "";

        if (inventory.size() < maxInventorySize) {
            GameObject pickedUpObject = gp.pickUpObject(objectIndex);
            inventory.add(pickedUpObject);
            gp.playSoundEffect(1);
            text = "Got a " + pickedUpObject.name + "!";
        } else {
            text = "Your inventory is full!";
        }

        gp.ui.addMessage(text);
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

        gp.playerInteractedWithMonster(monsterIndex);
    }

    public void takeHit(int attackDamage) {
        if (invincibleCounter == 0) {
            gp.playSoundEffect(8);
            int damageTaken = Math.max(attackDamage - defense, 0);
            life = Math.max(life - damageTaken, 0);
            invincibleCounter = 60;
        }
    }

    public void startAttack() {
        attacking = true;
        gp.playSoundEffect(6);
    }

    public void gainEXP(int exp) {
        this.exp += exp;
        if (this.exp >= nextLevelExp) {
            gp.playSoundEffect(10);

            level++;
            nextLevelExp = nextLevelExp * 2 + 10;

            maxLife += 2;
            life += 2;

            strength++;
            dexterity++;
            attack = getAttack();
            defense = getDefense();

            gp.playerLeveledUp(level);
        }
    }

    public void selectItem() {
        int itemIndex = gp.ui.getItemIndex();

        if (itemIndex < inventory.size()) {
            GameObject selectedItem = inventory.get(itemIndex);
            selectedItem.interact(this);
        }
    }

    public void interactWithWeapon(Weapon weapon) {
        currentWeapon = weapon;
        weapon.getPlayerAttackImages(this);
        attack = getAttack();
    }

    public void interactWithConsumable(Consumable consumable) {
        boolean used = consumable.use(this);
        if (used) inventory.remove(consumable);
    }

    public void interactWithShield(Shield shield) {
        currentShield = shield;
        defense = getDefense();
    }
}
