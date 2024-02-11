package monster;

import entity.Entity;
import main.GamePanel;

public class MON_GreenSlime extends Entity {
    public MON_GreenSlime(GamePanel gp, int worldX, int worldY) {
        super(gp, worldX, worldY);

        name = "Green Slime";
        speed = 1;
        maxLife = 4;
        life = maxLife;
        attack = 5;
        defense = 0;
        exp = 5;

        setHitBox();
        getImages();
    }

    private void setHitBox() {
        solidArea.x = 3;
        solidArea.y = 15;
        solidArea.width = 42;
        solidArea.height = 33;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
    }

    public void getImages() {
        up1 = setup("/monster/greenSlime_down1", gp.tileSize, gp.tileSize);
        up2 = setup("/monster/greenSlime_down2", gp.tileSize, gp.tileSize);
        down1 = up1;
        down2 = up2;
        left1 = up1;
        left2 = up2;
        right1 = up1;
        right2 = up2;
    }
    public void takeHit(int attackDamage) {
        super.takeHit(attackDamage);

        reactToGettingHit();
    }

    public void collidedWithPlayer() {
        if (isAlive()) {
            gp.attackEntity(gp.player, attack);
        }
    }

    public void reactToGettingHit() {
        actionLockCounter = 0;
        direction = gp.player.direction;
    }

}
