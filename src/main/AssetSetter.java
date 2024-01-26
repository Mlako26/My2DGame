package main;

import collidable.Collidable;
import entity.NPC_Explorer;
import java.util.ArrayList;

public class AssetSetter {
    GamePanel gp;

    public AssetSetter(GamePanel gp) {
        this.gp = gp;
    }

    public ArrayList<Collidable> setObjects() {
        return new ArrayList<>();
    }

    public ArrayList<Collidable> setNPC() {
        ArrayList<Collidable> npcs = new ArrayList<>();

        npcs.add(new NPC_Explorer(gp, 28, 42));

        return npcs;
    }
}
