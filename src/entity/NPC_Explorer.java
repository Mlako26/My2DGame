package entity;

import main.GamePanel;

public class NPC_Explorer extends Entity {

    public NPC_Explorer(GamePanel gp, int worldX, int worldY) {
        super(gp, worldX, worldY);
        setDialogue();
    }

    public void setDefaultValues() {
        super.setDefaultValues();
        speed = 1;
    }

    public void getEntityImages() {
        up1 = setup("/NPC/explorer_up1", gp.tileSize, gp.tileSize);
        up2 = setup("/NPC/explorer_up2", gp.tileSize, gp.tileSize);
        down1 = setup("/NPC/explorer_down1", gp.tileSize, gp.tileSize);
        down2 = setup("/NPC/explorer_down2", gp.tileSize, gp.tileSize);
        left1 = setup("/NPC/explorer_left1", gp.tileSize, gp.tileSize);
        left2 = setup("/NPC/explorer_left2", gp.tileSize, gp.tileSize);
        right1 = setup("/NPC/explorer_right1", gp.tileSize, gp.tileSize);
        right2 = setup("/NPC/explorer_right2", gp.tileSize, gp.tileSize);
    }

    public void setDialogue() {
        dialogue.add("Hello, lad.");
        dialogue.add("So, you've come to this island to \nfind the treasure?");
        dialogue.add("I'm still looking for it myself.\nI'll race you to it!");
        dialogue.add("Well, good luck with your hunt...");
    }

    public String speak() {
        return super.speak();
    }


}
