package main;

import entity.Entity;
import entity.NPC_Explorer;
import object.GameObject;

import java.util.ArrayList;

public class AssetSetter {
    GamePanel gp;

    public AssetSetter(GamePanel gp) {
        this.gp = gp;
    }

    public ArrayList<GameObject> setObjects() {
        return new ArrayList<>();
    }

    public ArrayList<Entity> setNPC() {
        ArrayList<Entity> npcs = new ArrayList<>();

        npcs.add(new NPC_Explorer(gp, 28, 42));

        return npcs;
    }
}
