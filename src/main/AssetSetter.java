package main;

import entity.Entity;
import entity.NPC_Explorer;
import monster.MON_GreenSlime;
import object.GameObject;

import java.lang.reflect.Array;
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

    public ArrayList<Entity> setMonsters() {
        ArrayList<Entity> monsters = new ArrayList<>();

        monsters.add(new MON_GreenSlime(gp, 38, 37));
        monsters.add(new MON_GreenSlime(gp, 37, 36));
        monsters.add(new MON_GreenSlime(gp, 39, 39));
        monsters.add(new MON_GreenSlime(gp, 38, 38));

        return monsters;
    }
}
