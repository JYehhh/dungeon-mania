package dungeonmania;

import static org.junit.jupiter.api.Assertions.assertEquals;

import static dungeonmania.TestUtils.getPlayer;
import static dungeonmania.TestUtils.getInventory;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import dungeonmania.item.Key;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.staticentity.Door;
import dungeonmania.staticentity.Wall;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class PlayerTests {
    @Test
    @DisplayName("Test basic movement of Player")
    public void testbasicMovement() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse initDungeonRes = dmc.newGame("d_gameTest_basic", "c_gameTest_basic");
        initDungeonRes = dmc.tick(Direction.DOWN);
        EntityResponse player1 = getPlayer(initDungeonRes).get();
        Position pos = new Position(0, 2);
        assertEquals(player1.getPosition(), pos);
    }

    @Test
    @DisplayName("Test basic pickup of item")
    public void testBasicCollect() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse initDungeonRes = dmc.newGame("d_gameTest_basic", "c_gameTest_basic");
        initDungeonRes = dmc.tick(Direction.RIGHT);
        assertEquals(1, getInventory(initDungeonRes, "wood").size());
    }
    
    @Test
    @DisplayName("Test pickup multiple of item")
    public void testCollectMultiple() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse initDungeonRes = dmc.newGame("d_gameTest_multipleEntities", "c_gameTest_multipleEntities");
        initDungeonRes = dmc.tick(Direction.RIGHT);
        assertEquals(1, getInventory(initDungeonRes, "wood").size());
    }

    @Test
    @DisplayName("Player cannot move into wall")
    public void testPlayerCannotMoveIntoWall() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse initDungeonRes = dmc.newGame("d_gameTest_basic", "c_gameTest_basic");
        EntityResponse player1 = getPlayer(initDungeonRes).get();
        Wall wall = new Wall(new Position(0, 2));
        Game game = dmc.getGame(initDungeonRes.getDungeonId());
        game.addEntity(wall);
        initDungeonRes = dmc.tick(Direction.DOWN);
        assertEquals(player1.getPosition(), new Position(0, 1));
    }

    @Test
    @DisplayName("Player cannot exit through door without key")
    public void testPlayerCannotEnterDoorWithoutKey() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse initDungeonRes = dmc.newGame("d_gameTest_basic", "c_gameTest_basic");
        EntityResponse player1 = getPlayer(initDungeonRes).get();
        Key key = new Key(new Position(0, 2), 1);
        Door door = new Door(new Position(0, 0), 1);
        Game game = dmc.getGame(initDungeonRes.getDungeonId());
        game.addEntity(key);
        game.addEntity(door);
        initDungeonRes = dmc.tick(Direction.UP);
        assertEquals(player1.getPosition(), new Position(0, 1));
    }

    @Test
    @DisplayName("Player can enter door with key")
    public void testPlayerCanEnterDoorWithKey() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse initDungeonRes = dmc.newGame("d_gameTest_basic", "c_gameTest_basic");
        EntityResponse player1 = getPlayer(initDungeonRes).get();
        Key key = new Key(new Position(0, 2), 1);
        Door door = new Door(new Position(0, 0), 1);
        Game game = dmc.getGame(initDungeonRes.getDungeonId());
        game.addEntity(key);
        game.addEntity(door);
        initDungeonRes = dmc.tick(Direction.DOWN);
        assertEquals(1, getInventory(initDungeonRes, "key").size());
        initDungeonRes = dmc.tick(Direction.UP);
        initDungeonRes = dmc.tick(Direction.UP);
        player1 = getPlayer(initDungeonRes).get();
        assertEquals(player1.getPosition(), new Position(0, 0));
    }

}
