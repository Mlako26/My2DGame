package entity;

import main.GamePanel;

public class NPC_Explorer extends Entity {

    public NPC_Explorer(GamePanel gp, int worldX, int worldY) {
        super(gp, worldX, worldY);
    }

    public void setDefaultValues() {
        direction = "down";
        speed = 1;
    }

    public void getEntityImages() {
        up1 = setup("/NPC/explorer_up1");
        up2 = setup("/NPC/explorer_up2");
        down1 = setup("/NPC/explorer_down1");
        down2 = setup("/NPC/explorer_down2");
        left1 = setup("/NPC/explorer_left1");
        left2 = setup("/NPC/explorer_left2");
        right1 = setup("/NPC/explorer_right1");
        right2 = setup("/NPC/explorer_right2");
    }

}
