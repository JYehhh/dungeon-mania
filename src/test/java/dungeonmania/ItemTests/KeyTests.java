package dungeonmania.ItemTests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import static dungeonmania.TestUtils.getInventory;
import static dungeonmania.TestUtils.countEntityOfType;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import dungeonmania.DungeonManiaController;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.util.Direction;

public class KeyTests {
    @Test
    @DisplayName("Test create Key")
    public void testCreateKey() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse initDungeonRes = dmc.newGame("d_DoorsKeysTest_useKeyWalkThroughOpenDoor", "c_DoorsKeysTest_useKeyWalkThroughOpenDoor");
        assertTrue(countEntityOfType(initDungeonRes, "key") == 1);
    }

    @Test
    @DisplayName("Test pickup Key")
    public void testPickupKey() {
        DungeonManiaController dmc = new DungeonManiaController();
        dmc.newGame("d_DoorsKeysTest_useKeyWalkThroughOpenDoor", "c_DoorsKeysTest_useKeyWalkThroughOpenDoor");
        DungeonResponse afterPickupRes = dmc.tick(Direction.RIGHT);
        assertEquals(1, getInventory(afterPickupRes, "key").size());
    }
}