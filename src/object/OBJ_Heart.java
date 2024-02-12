package object;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;


public class OBJ_Heart extends GameObject {
    public BufferedImage image2, image3;

    public OBJ_Heart(GamePanel gp, int x, int y) {
        super(gp, x, y, "heart_full", "Heart", true);

        try {
            image2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/objects/heart_half.png")));
            image2 = utilities.scaleImage(image2, gp.tileSize, gp.tileSize);
            image3 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/objects/heart_empty.png")));
            image3 = utilities.scaleImage(image3, gp.tileSize, gp.tileSize);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
