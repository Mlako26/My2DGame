package tile;

import java.awt.image.BufferedImage;

public class Tile {
    public BufferedImage image;
    public boolean collision = false;

    public int id;

    public Tile( int id) {
        this.id = id;
    }

    public void activateCollision() {
        collision = true;
    }

}
