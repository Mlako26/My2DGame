package main;

import state.DialogueGameState;
import state.GameState;

public class EventHandler {
    GamePanel gp;
    EventRectangle[][] eventRectangle;

    int lastEventCol, lastEventRow;

    public EventHandler(GamePanel gp) {
        this.gp = gp;

        eventRectangle = new EventRectangle[gp.maxWorldCol][gp.maxWorldRow];

        for (int i = 0; i < gp.maxWorldCol; i++) {
            for (int j = 0; j < gp.maxWorldRow; j++) {
                eventRectangle[i][j] = new EventRectangle();

                eventRectangle[i][j].x = 23;
                eventRectangle[i][j].y = 23;

                eventRectangle[i][j].width = 2;
                eventRectangle[i][j].height = 2;

                eventRectangle[i][j].eventRectangleDefaultX = eventRectangle[i][j].x;
                eventRectangle[i][j].eventRectangleDefaultY = eventRectangle[i][j].y;
            }
        }


    }

    public void checkEvent() {
        // Reset last event
        int xDistance = Math.abs(lastEventCol - gp.player.worldX / gp.tileSize);
        int yDistance = Math.abs(lastEventRow - gp.player.worldY / gp.tileSize);
        int distance = Math.max(xDistance, yDistance);

        if (distance > 1) {
            eventRectangle[lastEventCol][lastEventRow].eventDone = false;
        }


        if (hit(25,29, "right")) {
            damagePit(25, 29, new DialogueGameState());
        }

        if (hit(23,42,"left")) {
            healingPool(23, 42, new DialogueGameState());
        }
    }

    public boolean hit(int col, int row, String requiredDirection) {
        boolean hit = false;

        gp.player.solidArea.x = gp.player.worldX + gp.player.solidArea.x;
        gp.player.solidArea.y = gp.player.worldY + gp.player.solidArea.y;

        eventRectangle[col][row].x = col * gp.tileSize + eventRectangle[col][row].x;
        eventRectangle[col][row].y = row * gp.tileSize + eventRectangle[col][row].y;

        if (gp.player.solidArea.intersects(eventRectangle[col][row]) && !eventRectangle[col][row].eventDone) {
            if (gp.player.direction.equals(requiredDirection) || requiredDirection.equals("any")) {
                hit = true;
                lastEventCol = col;
                lastEventRow = row;
            }
        }

        gp.player.solidArea.x = gp.player.solidAreaDefaultX;
        gp.player.solidArea.y = gp.player.solidAreaDefaultY;

        eventRectangle[col][row].x = eventRectangle[col][row].eventRectangleDefaultX;
        eventRectangle[col][row].y = eventRectangle[col][row].eventRectangleDefaultY;

        return hit;
    }

    public void damagePit(int col, int row, GameState gameState) {
        gp.gameState = gameState;
        gp.ui.currentDialogue = "You fell into a pit!";
        gp.player.life = Math.max(0, gp.player.life - 1);
        eventRectangle[col][row].eventDone = true;
    }

    public void healingPool(int col, int row, GameState gameState) {
        if (gp.keyH.interactPressed) {
            gp.gameState = gameState;
            gp.ui.currentDialogue = "You drank the healing water!\nYou recovered a full heart.";
            gp.player.life = Math.min(6, gp.player.life + 2);
            gp.keyH.interactPressed = false;
            eventRectangle[col][row].eventDone = true;
        }
    }
}
