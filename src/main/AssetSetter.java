package main;

import object.*;

import java.util.ArrayList;

public class AssetSetter {
    GamePanel gp;

    public AssetSetter(GamePanel gp) {
        this.gp = gp;
    }

    public ArrayList<GameObject> setObjects() {
        ArrayList<GameObject> objects = new ArrayList<>();

        objects.add(new OBJ_Key(gp, 32, 8));

        objects.add(new OBJ_Key(gp, 13, 37));

        objects.add(new OBJ_Boots(gp, 35, 42));

        objects.add(new OBJ_Chest(gp, 17 ,12));

        objects.add(new OBJ_Door(gp, 24, 17));

        objects.add(new OBJ_Door(gp, 17, 14));

        objects.add(new OBJ_PinkFlower(gp, 36, 18));

        objects.add(new OBJ_BlueFlower(gp, 34, 18));

        objects.add(new OBJ_PinkFlower(gp, 32, 18));

        objects.add(new OBJ_BlueFlower(gp, 30, 18));

        objects.add(new OBJ_PinkFlower(gp, 28, 18));

        return objects;
    }
}
