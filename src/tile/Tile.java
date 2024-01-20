package tile;

import java.awt.image.BufferedImage;

public class Tile {
    public BufferedImage image;
    public boolean collision = false;

    public String name;
    public int id;

    public Tile(String name, int id) {
        this.name = name;
        this.id = id;
    }

}
