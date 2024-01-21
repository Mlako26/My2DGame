package main;

import object.*;

public class AssetSetter {
    GamePanel gp;

    public AssetSetter(GamePanel gp) {
        this.gp = gp;
    }

    public void setObjects() {
        gp.objects.add(new OBJ_Key());
        gp.objects.getLast().worldX = 42 * gp.tileSize;
        gp.objects.getLast().worldY = 28 * gp.tileSize;

        gp.objects.add(new OBJ_Key());
        gp.objects.getLast().worldX = 37 * gp.tileSize;
        gp.objects.getLast().worldY = 15 * gp.tileSize;

        gp.objects.add(new OBJ_Chest());
        gp.objects.getLast().worldX = gp.tileSize;
        gp.objects.getLast().worldY = 18 * gp.tileSize;

        gp.objects.add(new OBJ_Door());
        gp.objects.getLast().worldX = 37 * gp.tileSize;
        gp.objects.getLast().worldY = 17 * gp.tileSize;

    }
}
