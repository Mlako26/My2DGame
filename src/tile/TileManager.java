package tile;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.*;
import java.util.HashMap;
import java.util.Objects;

public class TileManager {
    GamePanel gp;
    HashMap<Integer, Tile> tiles;
    int[][] mapTileNum;

    public TileManager(GamePanel gp) {
        this.gp = gp;
        tiles = new HashMap<>();
        mapTileNum = new int[gp.maxWorldCol][gp.maxWorldRow];

        getTileImages();
        loadMap("testWorld");
    }

    public void getTileImages() {
        try {
            File dir = new File("./res/tiles");

            File[] files = dir.listFiles();
            if (files != null) {
                for (File tileImage : files) {
                    String tileNameAndCode = removeFileExtension(tileImage);
                    String tileName = getTileName(tileNameAndCode);
                    int tileId = getTileId(tileNameAndCode);
                    Tile nextTileType = new Tile(tileName, tileId);
                    nextTileType.image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/" + tileImage.getName())));
                    tiles.put(nextTileType.id, nextTileType);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String removeFileExtension(File file) {
        String[] parts = file.getName().split("\\.");
        return parts[0];
    }

    private String getTileName(String nameAndId) {
        String[] parts = nameAndId.split("_");
        return parts[1];
    }

    private int getTileId(String nameAndId) {
        String[] parts = nameAndId.split("_");
        return Integer.parseInt(parts[0]);
    }

    public void loadMap(String file) {
        try {
            if (!file.contains("/maps")) file = "/maps/" + file;
            if (!file.contains(".txt")) file = file + ".txt";
            InputStream is = getClass().getResourceAsStream(file);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            for (int row = 0; row < gp.maxWorldRow; row++) {
                String[] parts = br.readLine().split(" ");
                for (int col = 0; col < gp.maxWorldCol; col++) {
                    mapTileNum[col][row] = Integer.parseInt(parts[col]);
                }
            }

            br.close();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void draw(Graphics2D g2) {
        for (int xPos = 0; xPos < gp.maxWorldCol; xPos++) {
            for (int yPos = 0; yPos < gp.maxWorldRow; yPos++) {
                int tileType = mapTileNum[xPos][yPos];

                int worldX = xPos * gp.tileSize;
                int worldY = yPos * gp.tileSize;

                int screenX = gp.getScreenX(worldX);
                int screenY = gp.getScreenY(worldY);

                if (tileIsWithinBounds(screenX, screenY)) {
                    g2.drawImage(tiles.get(tileType).image, screenX, screenY, gp.tileSize, gp.tileSize, null);
                }
            }
        }
    }



    private boolean tileIsWithinBounds(int screenX, int screenY) {
        return  screenX + gp.tileSize > 0 &&
                screenX - gp.tileSize < gp.screenWidth &&
                screenY + gp.tileSize > 0 &&
                screenY - gp.tileSize < gp.screenHeight;
    }
}
