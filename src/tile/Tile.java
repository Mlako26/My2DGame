package tile;

import java.awt.image.BufferedImage;

public class Tile {
    public BufferedImage image;
    public boolean collision = false;

    public String type;
    public int id;

    public Tile(String type, int id) {
        this.type = type;
        this.id = id;
    }

    public void activateCollision() {
        collision = true;
    }

}
