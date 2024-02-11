package main;

import collidable.Collidable;
import entity.Entity;
import entity.Player;
import object.GameObject;
import state.*;
import tile.TileManager;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;

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
    public KeyHandler keyH = new KeyHandler(this);
    public TileManager tileManager = new TileManager(this);
    public CollisionDetector collisionDetector = new CollisionDetector(this);
    public AssetSetter assetSetter = new AssetSetter(this);
    Sound music = new Sound();
    public Sound soundEffect = new Sound();
    public UserInterface ui = new UserInterface(this);
    public EventHandler eventHandler = new EventHandler(this);

    // --- END OF GAME SYSTEM VARIABLES ---

    // --- ENTITIES ---

    public Player player = new Player(this, keyH);
    public ArrayList<GameObject> objects = new ArrayList<>();
    public ArrayList<Entity> npcs = new ArrayList<>();
    public ArrayList<Entity> monsters = new ArrayList<>();

    // --- END OF ENTITIES ---

    // --- GAME STATE ---

    GameState gameState = new TitleGameState();

    // --- END OF GAME STATE ---

    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
    }

    public void setupGame() {
        objects = assetSetter.setObjects();
        npcs = assetSetter.setNPC();
        monsters = assetSetter.setMonsters();
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
        playMusic(5);
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
        gameState.updatePanel(this);
    }

    public void updateOnPlayState() {
        player.update();

        ArrayList<Integer> deadMonsters = new ArrayList<>();
        for (Entity npc : npcs) {
            npc.update();
        }
        for (int i = 0; i < monsters.size(); i++) {
            Entity monster = monsters.get(i);
            if (monster.isDead()) deadMonsters.add(i);
            else monster.update();
        }
        for (int i : deadMonsters) {
            monsters.remove(i);
        }
    }

    public void updateOnPauseState() {

    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        long drawStart = System.nanoTime();

        gameState.whatShouldIPaint(this, g2);

        long drawEnd = System.nanoTime();
        if (keyH.debugMode) {
            g2.setColor(Color.white);
            g2.drawString("Draw time: " + (drawEnd - drawStart) / 1e6,10, 400);
            g2.drawString("X: " + player.worldX / tileSize + ", Y: " + player.worldY / tileSize, tileSize / 2, tileSize * 3);
            System.out.println("Draw time: " + (drawEnd - drawStart));
        }

        g2.dispose();
    }

    public void paintTitle(Graphics2D g2) {
        ui.draw(g2);
    }

    public void paintGame(Graphics2D g2) {

        tileManager.draw(g2);

        ArrayList<Collidable> collidablesToRender = setCollidables();
        for (Collidable collidable : collidablesToRender) {
            collidable.draw(g2);
        }

        ui.draw(g2);
    }

    private ArrayList<Collidable> setCollidables() {
        ArrayList<Collidable> collidablesToRender = new ArrayList<>();

        collidablesToRender.add(player);
        collidablesToRender.addAll(npcs);
        collidablesToRender.addAll(objects);
        collidablesToRender.addAll(monsters);
        collidablesToRender.sort(Comparator.comparingInt(o -> o.worldY));

        return collidablesToRender;
    }

    public int getScreenY(int worldY) {
        return worldY - player.worldY + player.screenY;
    }

    public int getScreenX(int worldX) {
        return worldX - player.worldX + player.screenX;
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

    public void updateInterface() {
        gameState.updateInterface(ui);
    }

    public void updateTileCollisionsFor(Entity entity) {
        collisionDetector.updateTileCollisionsFor(entity);
    }

    public boolean updatePlayerCollisionFor(Entity entity) {
        return collisionDetector.updateCollisionWithPlayerFor(entity) != -1;
    }

    public int updateNPCCollisionsFor(Entity entity) {
        return collisionDetector.updateCollisionsFor(entity, npcs);
    }

    public int updateObjectCollisionsFor(Entity entity) {
        return collisionDetector.updateCollisionsFor(entity, objects);
    }

    public int updateMonsterCollisionsFor(Entity entity) {
        return collisionDetector.updateCollisionsFor(entity, monsters);
    }

    public void playerInteractedWithNPC(int npcIndex) {
        gameState = new DialogueGameState();
        ui.updateDialogue(npcs.get(npcIndex).speak());
    }

    public void updateKeyHandlerState() {
        gameState.updateKeyHandler(keyH);
    }

    public void pauseGame() {
        gameState = new PauseGameState();
    }

    public void resumeGame() {
        gameState = new PlayGameState();
    }

    public void goUpOneOptionInTitleScreen() {
        ui.goUpOneOptionInTitleScreen();
    }

    public void goDownOneOptionInTitleScreen() {
        ui.goDownOneOptionInTitleScreen();
    }

    public void enterPressedInTitleScreen() {
        ui.enterPressedInTitleScreen();
    }

    public void actionKeyWasPressed() {
        player.startAttack();
    }

    public void attackMonster(int monsterIndex, int attackDamage) {
        attackEntity(monsters.get(monsterIndex), attackDamage);
    }

    public void attackEntity(Entity entity, int attackDamage) {
        entity.takeHit(attackDamage);
    }

    public void openInventory() {
        gameState = new CharacterGameState();
    }

    public void playerInteractedWithMonster(int monsterIndex) {
        monsters.get(monsterIndex).collidedWithPlayer();
    }


    public void entityKilled(Entity entity) {
        ui.addMessage("You killed: " + entity.name + "!");
        ui.addMessage("Gained " + entity.exp + "EXP");
        player.gainEXP(entity.exp);
    }

    public void playerLeveledUp(int level) {
        ui.currentDialogue = "You reached level " + level + "!\nYou feel stronger...";
        gameState = new DialogueGameState();
    }
}


