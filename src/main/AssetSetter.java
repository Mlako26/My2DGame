package main;

import entity.Entity;
import entity.NPC_Explorer;
import monster.MON_GreenSlime;
import object.GameObject;
import object.OBJ_BlueFlower;
import object.OBJ_Chest;
import object.OBJ_PinkFlower;

import java.util.ArrayList;

public class AssetSetter {
    GamePanel gp;

    public AssetSetter(GamePanel gp) {
        this.gp = gp;
    }

    public ArrayList<GameObject> setObjects(String currentMap) {
        ArrayList<GameObject> objects = new ArrayList<>();

//        try {
//            String file = "/maps/" + currentMap + "_obj.txt";
//
//            InputStream is = getClass().getResourceAsStream(file);
//            BufferedReader br = new BufferedReader(new InputStreamReader(Objects.requireNonNull(is)));
//
//            if (br.ready()) {
//                String[] parts = br.readLine().split(" ");
//            }
//
//
//            br.close();
//        } catch (Exception e) {
//            System.out.println("Error: " + e.getMessage());
//        }

        objects.add(new OBJ_PinkFlower(gp, 37, 16));
        objects.add(new OBJ_PinkFlower(gp, 40, 16));
        objects.add(new OBJ_PinkFlower(gp, 39, 18));
        objects.add(new OBJ_BlueFlower(gp, 41, 15));
        objects.add(new OBJ_BlueFlower(gp, 40, 18));
        objects.add(new OBJ_BlueFlower(gp, 35, 18));
        objects.add(new OBJ_BlueFlower(gp, 35, 18));
        objects.add(new OBJ_Chest(gp, 10, 41));


        return objects;
    }

    public ArrayList<Entity> setNPC() {
        ArrayList<Entity> npcs = new ArrayList<>();

        npcs.add(new NPC_Explorer(gp, 15, 15));

        return npcs;
    }

    public ArrayList<Entity> setMonsters() {
        ArrayList<Entity> monsters = new ArrayList<>();

        monsters.add(new MON_GreenSlime(gp, 26, 8));
        monsters.add(new MON_GreenSlime(gp, 21, 6));
        monsters.add(new MON_GreenSlime(gp, 22, 9));
        monsters.add(new MON_GreenSlime(gp, 30, 9));

        return monsters;
    }
}
