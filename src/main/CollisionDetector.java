package main;

import entity.Entity;

public class CollisionDetector {
    GamePanel gp;

    Entity currentEntity;

    int entityLeftWorldX;
    int entityRightWorldX;
    int entityTopWorldY;
    int entityBottomWorldY;

    int entityLeftCol;
    int entityRightCol;
    int entityTopRow;
    int entityBottomRow;


    public CollisionDetector(GamePanel gp) {
        this.gp = gp;
    }

    public void updateCollisionsFor(Entity entity) {
        this.currentEntity = entity;

        getEntityHitBoxWorldPos();
        getEntityHitBoxTiles();

        checkTopCollision();
        checkBottomCollision();
        checkLeftCollision();
        checkRightCollision();
    }

    private void getEntityHitBoxTiles() {
        entityLeftCol = entityLeftWorldX / gp.tileSize;
        entityRightCol = entityRightWorldX / gp.tileSize;
        entityTopRow = entityTopWorldY / gp.tileSize;
        entityBottomRow = entityBottomWorldY / gp.tileSize;
    }

    private void getEntityHitBoxWorldPos() {
        entityLeftWorldX = currentEntity.worldX + currentEntity.solidArea.x;
        entityRightWorldX = currentEntity.worldX + currentEntity.solidArea.x + currentEntity.solidArea.width;
        entityTopWorldY = currentEntity.worldY + currentEntity.solidArea.y;
        entityBottomWorldY = currentEntity.worldY + currentEntity.solidArea.y + currentEntity.solidArea.height;
    }

    private void checkTopCollision() {
        int tileId1, tileId2;
        int newEntityTopRow = (entityTopWorldY - currentEntity.speed) / gp.tileSize;

        tileId1 = gp.tileManager.mapTileNum[entityLeftCol][newEntityTopRow];
        tileId2 = gp.tileManager.mapTileNum[entityRightCol][newEntityTopRow];

        if (gp.isCollideable(tileId1) || gp.isCollideable(tileId2)) {
            currentEntity.topCollisionOn = true;
        }
    }

    private void checkBottomCollision() {
        int tileId1, tileId2;
        int newEntityBottomRow = (entityBottomWorldY + currentEntity.speed) / gp.tileSize;

        tileId1 = gp.tileManager.mapTileNum[entityLeftCol][newEntityBottomRow];
        tileId2 = gp.tileManager.mapTileNum[entityRightCol][newEntityBottomRow];

        if (gp.isCollideable(tileId1) || gp.isCollideable(tileId2)) {
            currentEntity.bottomCollisionOn = true;
        }

    }

    private void checkLeftCollision() {
        int tileId1, tileId2;
        int newEntityLeftCol = (entityLeftWorldX - currentEntity.speed) / gp.tileSize;

        tileId1 = gp.tileManager.mapTileNum[newEntityLeftCol][entityTopRow];
        tileId2 = gp.tileManager.mapTileNum[newEntityLeftCol][entityBottomRow];

        if (gp.isCollideable(tileId1) || gp.isCollideable(tileId2)) {
            currentEntity.leftCollisionOn = true;
        }
    }

    private void checkRightCollision() {
        int tileId1, tileId2;
        int newEntityRightCol = (entityRightWorldX + currentEntity.speed) / gp.tileSize;

        tileId1 = gp.tileManager.mapTileNum[newEntityRightCol][entityTopRow];
        tileId2 = gp.tileManager.mapTileNum[newEntityRightCol][entityBottomRow];

        if (gp.isCollideable(tileId1) || gp.isCollideable(tileId2)) {
            currentEntity.rightCollisionOn = true;
        }
    }

}
