package main;

import entity.Entity;
import entity.Player;
import object.GameObject;
import tile.TileManager;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class GamePanel extends JPanel implements Runnable {
    // --- SCREEN SETTINGS --

    // Tile Size
    final int originalTileSize = 16;
    final int scale = 3;
    public final int tileSize = originalTileSize * scale;

    // Window Size
    public final int maxScreenCol = 16;
    public final int maxScreenRow = 12;
    public final int screenWidth = tileSize * maxScreenCol; // 768 px
    public final int screenHeight = tileSize * maxScreenRow; // 576 px

    // FPS
    int FPS = 60;

    // --- END OF SCREEN SETTINGS ---

    // -- WORLD SETTINGS ---

    public final int maxWorldCol = 50;
    public final int maxWorldRow = 50;

    // --- END OF WORLD SETTINGS  ---

    // --- GAME SYSTEM VARIABLES ---

    Thread gameThread;
    KeyHandler keyH = new KeyHandler();
    public TileManager tileManager = new TileManager(this);
    public CollisionDetector collisionDetector = new CollisionDetector(this);
    public AssetSetter assetSetter = new AssetSetter(this);
    Sound music = new Sound();
    public Sound soundEffect = new Sound();
    public UserInterface ui = new UserInterface(this);

    // --- END OF GAME SYSTEM VARIABLES ---

    // --- ENTITIES ---

    public Player player = new Player(this, keyH);
    public ArrayList<GameObject> objects = new ArrayList<>();

    // --- END OF ENTITIES ---

    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
    }

    public void setupGame() {
        objects = assetSetter.setObjects();
        playMusic(0);
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {

        double drawInterval = 1e9 / FPS; // nanoseconds
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer = 0;
        int drawCount = 0;

        while (gameThread != null) {

            currentTime = System.nanoTime();

            delta += (currentTime - lastTime) / drawInterval;
            timer += currentTime - lastTime;
            lastTime = currentTime;

            if (delta >= 1) {
                update();
                repaint();
                delta--;
                drawCount++;
            }

            // FPS printer
            if (timer >= 1e9) {
                System.out.println("FPS: " + drawCount);
                timer = 0;
                drawCount = 0;
            }
        }
    }

    public void update() {
        player.update();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        long drawStart = System.nanoTime();

        tileManager.draw(g2);
        for (GameObject object : objects) {
            object.draw(g2, this);
        }
        player.draw(g2);
        ui.draw(g2);

        long drawEnd = System.nanoTime();
        if (keyH.debugMode) {
            g2.setColor(Color.white);
            g2.drawString("Draw time: " + (drawEnd - drawStart) / 1e6,10, 400);
            g2.drawString("X: " + player.worldX / tileSize + ", Y: " + player.worldY / tileSize, tileSize / 2, tileSize * 3);
            System.out.println("Draw time: " + (drawEnd - drawStart));
        }
        g2.dispose();
    }

    public int getScreenY(int worldY) {
        return worldY - player.worldY + player.screenY;
    }

    public int getScreenX(int worldX) {
        return worldX - player.worldX + player.screenX;
    }

    public void updateCollisionsFor(Entity entity) {
        collisionDetector.updateCollisionsFor(entity);
    }

    public boolean isCollideable(int tileType) {
        return tileManager.isCollideable(tileType);
    }

    public void playMusic(int i) {
        music.setFile(i);
        music.play();
        music.loop();
    }

    public void stopMusic() {
        music.stop();
    }

    public void playSoundEffect(int i) {
        soundEffect.setFile(i);
        soundEffect.play();
    }

    public void playerGrabbedKey(int i) {
        consumeObject(i, 1);
        ui.displayMessage("Agarraste una llave pa!");
    }

    public void playerOpenedDoor(int i) {
        consumeObject(i, 3);
        ui.displayMessage("Hiciste pija la puerta no way!");
    }

    public void playerCantOpenDoor() {
        ui.displayMessage("Todavia no la podes abrir...");
    }

    public void playerGrabbedBoots(int i) {
        consumeObject(i,2);
        ui.displayMessage("MEEEEEESSSSSSIIIIIIII");
    }

    public void playerOpenedChest() {
        if (!ui.gameIsFinished) {
            ui.gameIsFinished = true;
            stopMusic();
            playSoundEffect(4);
        }
    }

    private void consumeObject(int objectIndex, int soundEffectIndex) {
        playSoundEffect(soundEffectIndex);
        objects.remove(objectIndex);
    }
}


