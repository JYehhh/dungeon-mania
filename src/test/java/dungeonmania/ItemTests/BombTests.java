package dungeonmania.ItemTests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import static dungeonmania.TestUtils.getInventory;
import static dungeonmania.TestUtils.getEntities;
import static dungeonmania.TestUtils.countEntityOfType;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import dungeonmania.DungeonManiaController;
import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class BombTests {
    @Test
    @DisplayName("Test create bomb")
    public void testCreateBomb() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse initDungeonRes = dmc.newGame("d_bombTest", "c_bombTest_radius2");
        assertTrue(countEntityOfType(initDungeonRes, "bomb") == 1);
    }

    @Test
    @DisplayName("Test pickup bomb")
    public void testPickupBomb() {
        DungeonManiaController dmc = new DungeonManiaController();
        dmc.newGame("d_bombTest", "c_bombTest_radius2");
        DungeonResponse afterPickupRes = dmc.tick(Direction.RIGHT);
        assertEquals(1, getInventory(afterPickupRes, "bomb").size());
    }

    @Test
    @DisplayName("Test place bomb")
    public void testPlaceBomb() throws IllegalArgumentException, InvalidActionException {
        DungeonManiaController dmc = new DungeonManiaController();
        dmc.newGame("d_bombTest", "c_bombTest_radius2");

        DungeonResponse afterPickupRes = dmc.tick(Direction.RIGHT);
        String itemId = getInventory(afterPickupRes, "bomb").get(0).getId();

        DungeonResponse afterPlaceRes = dmc.tick(itemId);
        assertEquals(0, getInventory(afterPlaceRes, "bomb").size());

        EntityResponse bomb = getEntities(afterPlaceRes, "bomb").get(0);
        assertEquals(bomb.getPosition(), new Position(2, 1));
    }

    @Test
    @DisplayName("Test bomb cannot be picked back up")
    public void testInvalidPickup() throws IllegalArgumentException, InvalidActionException {
        DungeonManiaController dmc = new DungeonManiaController();
        dmc.newGame("d_bombTest", "c_bombTest_radius2");

        DungeonResponse afterPickupRes = dmc.tick(Direction.RIGHT);
        String itemId = getInventory(afterPickupRes, "bomb").get(0).getId();

        dmc.tick(itemId);
        dmc.tick(Direction.DOWN);
        dmc.tick(Direction.UP);

        // assert that the player hasn't moved
        // assert that the bomb is still at position _
        // assert that the bomb is not in inventory
    }

    @Test
    @DisplayName("Test bomb explode")
    public void testBombExplode() throws IllegalArgumentException, InvalidActionException {
        // when switch is not triggered
        DungeonManiaController dmc = new DungeonManiaController();
        dmc.newGame("d_bombTest", "c_bombTest_radius2");

        DungeonResponse afterPickupRes = dmc.tick(Direction.RIGHT);
        String itemId = getInventory(afterPickupRes, "bomb").get(0).getId();

        dmc.tick(itemId);
        dmc.tick(Direction.DOWN);
        dmc.tick(Direction.DOWN);
        dmc.tick(Direction.RIGHT);
        DungeonResponse afterExplode = dmc.tick(Direction.UP);

        // assert that some things disappeared.
        // everything except for player and exit.
        assertEquals(2, getEntities(afterExplode, "").size());

    } 
}