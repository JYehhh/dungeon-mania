package dungeonmania.MovingEntities;

import static org.junit.jupiter.api.Assertions.assertEquals;

import static dungeonmania.TestUtils.getEntities;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import dungeonmania.DungeonManiaController;
import dungeonmania.Game;
import dungeonmania.movingentity.Spider;
import dungeonmania.staticentity.FloorSwitch;
import dungeonmania.item.Key;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.staticentity.*;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class SpiderTest {
    @Test
    @DisplayName("Test basic clockwise circular movement of spider")
    public void testSpiderBasicMovement() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_spiderTest_basicMovement", "c_spiderTest_basicMovement");
        Game game = dmc.getGame(res.getDungeonId());
        
        int x = 2;
        int y = 3;
        Position pos = new Position(x, y);

        Spider spider = new Spider(pos, 5, 1);
        game.addEntity(spider);

        List<Position> movementTrajectory = pos.movementTrajectory();
        for (int i = 0; i < movementTrajectory.size(); i++) {
        }
        int nextPositionElement = 0;

        for (int i = 0; i <= 30; ++i) {
            game.tickDirection(Direction.STATIC);
            assertEquals(movementTrajectory.get(nextPositionElement), spider.getPosition());

            nextPositionElement++;
            if (nextPositionElement == 8){
                nextPositionElement = 0;
            }
        } 
    }

    @Test
    @DisplayName("Test spider can traverse through static entities, except boulders")
    public void testSpiderTraverseStaticEntitiesExceptBoulders() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_spiderTest_basicMovement", "c_spiderTest_basicMovement");
        Game game = dmc.getGame(res.getDungeonId());

        int x = 2;
        int y = 3;
        Position pos = new Position(x, y);
        
        Spider spider = new Spider(pos, 5, 1);
        game.addEntity(spider);

        Wall wall = new Wall(pos.translateBy(0, -1));
        game.addEntity(wall);

        Key key = new Key(pos.translateBy(10, 10), 1);
        Door door = new Door(pos.translateBy(1, -1), 1);
        game.addEntity(key);
        game.addEntity(door);

        FloorSwitch floorSwitch = new FloorSwitch(pos.translateBy(1, 0));
        game.addEntity(floorSwitch);

        Portal portal = new Portal(pos.translateBy(1, 1), "RED");
        game.addEntity(portal);

        Portal portal2 = new Portal(pos.translateBy(5, 5), "RED");
        game.addEntity(portal2);

        Exit exit = new Exit(pos.translateBy(0, 1));
        game.addEntity(exit);

        List<Position> movementTrajectory = pos.movementTrajectory();
        int nextPositionElement = 0;

        for (int i = 0; i <= 30; ++i) {
            game.tickDirection(Direction.STATIC);
            assertEquals(movementTrajectory.get(nextPositionElement), spider.getPosition());

            nextPositionElement++;
            if (nextPositionElement == 8){
                nextPositionElement = 0;
            }
        } 
    }

    @Test
    @DisplayName("Test that spider reverses direction when encounters a boulder")
    public void testSpiderReverseDirectionBoulder() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_spiderTest_basicMovement", "c_spiderTest_basicMovement");
        Game game = dmc.getGame(res.getDungeonId());
        
        int x = 2;
        int y = 3;
        Position pos = new Position(x, y);
        
        Spider spider = new Spider(pos, 5, 1);
        game.addEntity(spider);

        Boulder boulder = new Boulder(pos.translateBy(0, 1));
        game.addEntity(boulder);

        List<Position> movementTrajectory =  new ArrayList<Position>();
        movementTrajectory.add(pos.translateBy(-1, 1));
        movementTrajectory.add(pos.translateBy(-1, 0));
        movementTrajectory.add(pos.translateBy(-1, -1));
        movementTrajectory.add(pos.translateBy(0, -1));
        movementTrajectory.add(pos.translateBy(1, -1));
        movementTrajectory.add(pos.translateBy(1, 0));
        movementTrajectory.add(pos.translateBy(1, 1));
        int nextPositionElement = 3;
        boolean clockwise = true;


        for (int i = 0; i <= 30; ++i) {
            game.tickDirection(Direction.STATIC);
            assertEquals(movementTrajectory.get(nextPositionElement), spider.getPosition());

            if (clockwise) {
                nextPositionElement++;
            } else {
                nextPositionElement--;
            }

            if (nextPositionElement == 7){
                nextPositionElement = 6;
                clockwise = false;
            }

            if (nextPositionElement == -1) {
                nextPositionElement = 0;
                clockwise = true;
            }
        } 
    }

    @Test
    @DisplayName("Test spider movement when there are two boulders")
    public void testSpiderReverseDirectionTwoBoulder() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_spiderTest_basicMovement", "c_spiderTest_basicMovement");
        Game game = dmc.getGame(res.getDungeonId());
        
        int x = 2;
        int y = 3;
        Position pos = new Position(x, y);
        
        Spider spider = new Spider(pos, 5, 1);
        game.addEntity(spider);

        Boulder boulder = new Boulder(pos.translateBy(1, 0));
        game.addEntity(boulder);

        Boulder boulder1 = new Boulder(pos.translateBy(-1, 0));
        game.addEntity(boulder1);

        List<Position> movementTrajectory =  new ArrayList<Position>();
        movementTrajectory.add(pos.translateBy(-1, -1));
        movementTrajectory.add(pos.translateBy(0, -1));
        movementTrajectory.add(pos.translateBy(1, -1));
        int nextPositionElement = 1;
        boolean clockwise = true;

        for (int i = 0; i <= 30; ++i) {
            game.tickDirection(Direction.STATIC);
            assertEquals(spider.getPosition(), movementTrajectory.get(nextPositionElement));

            if (clockwise) {
                nextPositionElement++;
            } else {
                nextPositionElement--;
            }

            if (nextPositionElement == 3){
                nextPositionElement = 2;
                clockwise = false;
            }

            if (nextPositionElement == -1) {
                nextPositionElement = 0;
                clockwise = true;
            }
        }
    }

    @Test
    @DisplayName("Test spider movement when there is a boulder directly above it")
    public void testSpiderMovementBoulderAbove() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_spiderTest_basicMovement", "c_spiderTest_basicMovement");
        Game game = dmc.getGame(res.getDungeonId());
        
        int x = 2;
        int y = 3;
        Position pos = new Position(x, y);
        
        Spider spider = new Spider(pos, 5, 1);
        game.addEntity(spider);

        Boulder boulder = new Boulder(pos.translateBy(0, -1));
        game.addEntity(boulder);

        for (int i = 0; i <= 30; ++i) {
            game.tickDirection(Direction.STATIC);
            assertEquals(spider.getPosition(), pos);
        }
    }

    @Test
    @DisplayName("Test spider spawn rate at 3")
    public void testSpiderSpawnRateThree() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_spiderTest_basicMovement", "c_spiderTest_spawnRate");
        Game game = dmc.getGame(res.getDungeonId());

        for (int i = 0; i < 3; ++i) {
            game.tickDirection(Direction.STATIC);
        }

        assertEquals(1, getEntities(game.getDungeonResponse(), "spider").size());

        for (int i = 0; i < 3; ++i) {
            game.tickDirection(Direction.STATIC);
        }

        assertEquals(2, getEntities(game.getDungeonResponse(), "spider").size());

        for (int i = 0; i < 3; ++i) {
            game.tickDirection(Direction.STATIC);
        }

        assertEquals(3, getEntities(game.getDungeonResponse(), "spider").size());

        for (int i = 0; i < 3; ++i) {
            game.tickDirection(Direction.STATIC);
        }

        assertEquals(4, getEntities(game.getDungeonResponse(), "spider").size());
    }
}
