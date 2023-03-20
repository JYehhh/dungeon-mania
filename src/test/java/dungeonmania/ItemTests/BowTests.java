package dungeonmania.ItemTests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import static dungeonmania.TestUtils.getInventory;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import dungeonmania.DungeonManiaController;
import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.util.Direction;


public class BowTests {
    @Test
    @DisplayName("Test bow is buildable")
    public void testBowBuildable() {
        DungeonManiaController dmc = new DungeonManiaController();
        dmc.newGame("d_bowTest", "testConfig");
        
        dmc.tick(Direction.RIGHT);
        dmc.tick(Direction.RIGHT);
        dmc.tick(Direction.RIGHT);
        dmc.tick(Direction.RIGHT);

        DungeonResponse partsPickedUp = dmc.tick(Direction.RIGHT);

        assertTrue(partsPickedUp.getBuildables().contains("bow"));
    }

    @Test
    @DisplayName("Test throws exception for invalid build")
    public void testInvalidInput() throws IllegalArgumentException, InvalidActionException {
        DungeonManiaController dmc = new DungeonManiaController();
        dmc.newGame("d_bowTest", "testConfig");
        
        assertThrows(IllegalArgumentException.class, () -> {
            dmc.build("bowZZZ");
        });
    }

    @Test
    @DisplayName("Test bow is successfully built")
    public void testCreateBow() throws IllegalArgumentException, InvalidActionException {
        DungeonManiaController dmc = new DungeonManiaController();
        dmc.newGame("d_bowTest", "testConfig");
        
        dmc.tick(Direction.RIGHT);
        dmc.tick(Direction.RIGHT);
        dmc.tick(Direction.RIGHT);
        dmc.tick(Direction.RIGHT);

        DungeonResponse bowBuilt = dmc.build("bow");

        assertEquals(getInventory(bowBuilt, "arrow").size(), 0);
        assertEquals(getInventory(bowBuilt, "wood").size(), 0);
        assertEquals(getInventory(bowBuilt, "bow").size(), 1);
    }

}
