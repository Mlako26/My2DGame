package tile;

import main.GamePanel;
import main.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.*;
import java.util.HashMap;
import java.util.Objects;

public class TileManager {
    GamePanel gp;
    public HashMap<Integer, Tile> tiles;
    public int[][] mapTileNum;

    // --- INDEXES ---
    final int TILE_COLLISION_INDEX = 0;
    final int TILE_ID_INDEX = 1;

    public TileManager(GamePanel gp) {
        this.gp = gp;
        tiles = new HashMap<>();
        mapTileNum = new int[gp.maxWorldCol][gp.maxWorldRow];

        getTiles();
        loadMap("world0");
    }

    public void getTiles() {
        try {
            File dir = new File("./res/tiles");

            File[] files = dir.listFiles();
            if (files != null) {
                for (File tileType : files) {
                    // Create the new Tile Type
                    String[] tileProperties = getTileProperties(tileType);

                    int tileId = parseTileId(tileProperties[TILE_ID_INDEX]);
                    boolean tileCollision = parseTileCollision(tileProperties[TILE_COLLISION_INDEX]);

                    addTile(tileType.getName(), tileId, tileCollision);
                }
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void addTile(String fileName, int id, boolean collision) {
        UtilityTool tool = new UtilityTool();

        try {
            Tile newTileType = new Tile(id);

            // Imports and scales image
            newTileType.image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/" + fileName)));
            newTileType.image = tool.scaleImage(newTileType.image, gp.tileSize, gp.tileSize);

            if (collision) newTileType.activateCollision();

            tiles.put(newTileType.id, newTileType);

        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    public String[] getTileProperties(File tileFile) {
        return removeFileExtension(tileFile).split("_");
    }

    public int parseTileId(String id) {
        return Integer.parseInt(id);
    }

    public boolean parseTileCollision(String collision) {
        return collision.equals("C");
    }

    private String removeFileExtension(File file) {
        String[] parts = file.getName().split("\\.");
        return parts[0];
    }

    public void loadMap(String file) {
        try {
            file = formatMapFileName(file);

            InputStream is = getClass().getResourceAsStream(file);
            BufferedReader br = new BufferedReader(new InputStreamReader(Objects.requireNonNull(is)));

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

    private static String formatMapFileName(String file) {
        if (!file.contains("/maps")) file = "/maps/" + file;
        if (!file.contains(".txt")) file = file + ".txt";
        return file;
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
                    g2.drawImage(tiles.get(tileType).image, screenX, screenY, null);
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

    public boolean isCollideable(int tileId) {
        return tiles.get(tileId).collision;
    }
}
