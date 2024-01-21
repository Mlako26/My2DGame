package object;

import javax.imageio.ImageIO;
import java.io.IOException;

public class OBJ_Chest extends GameObject{
    public OBJ_Chest() {
        name = "chest";

        try {
            image = ImageIO.read(getClass().getResourceAsStream("/objects/chest.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
