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

public class SwordTests {
    @Test
    @DisplayName("Test create sword")
    public void testCreateSword() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse initDungeonRes = dmc.newGame("d_itemTests_basicPotion", "c_itemTests_basicPotion");
        assertTrue(countEntityOfType(initDungeonRes, "invincibility_potion") == 1);
    }

    @Test
    @DisplayName("Test pickup invincibility potions")
    public void test() {
        DungeonManiaController dmc = new DungeonManiaController();
        dmc.newGame("d_itemTests_basicPotion", "c_itemTests_basicPotion");
        DungeonResponse afterPickupRes = dmc.tick(Direction.RIGHT);
        assertEquals(1, getInventory(afterPickupRes, "invincibility_potion").size());
        
        // simple test pickup
    }
}