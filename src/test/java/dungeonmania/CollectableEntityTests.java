package dungeonmania;

import static org.junit.jupiter.api.Assertions.assertEquals;

import static dungeonmania.TestUtils.getInventory;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import dungeonmania.response.models.DungeonResponse;
import dungeonmania.util.Direction;


public class CollectableEntityTests {
    
    @Test
    @DisplayName("Player picks up all possible collectible entities and they appear in player's inventory")
    public void testEntityPickup() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse initDungeonRes = dmc.newGame("d_collectibleTest_pickupCollectibles", "c_collectibleTest_pickupCollectibles");
        
        initDungeonRes = dmc.tick(Direction.RIGHT);
        assertEquals(1, getInventory(initDungeonRes, "key").size());
       
        initDungeonRes = dmc.tick(Direction.RIGHT);
        initDungeonRes = dmc.tick(Direction.RIGHT);
        assertEquals(1, getInventory(initDungeonRes, "treasure").size());

        initDungeonRes = dmc.tick(Direction.RIGHT);
        assertEquals(1, getInventory(initDungeonRes, "wood").size());

        initDungeonRes = dmc.tick(Direction.RIGHT);
        assertEquals(1, getInventory(initDungeonRes, "sword").size());

        initDungeonRes = dmc.tick(Direction.RIGHT);
        assertEquals(1, getInventory(initDungeonRes, "bomb").size());

        initDungeonRes = dmc.tick(Direction.RIGHT);
        assertEquals(1, getInventory(initDungeonRes, "invincibility_potion").size());

        initDungeonRes = dmc.tick(Direction.RIGHT);
        assertEquals(1, getInventory(initDungeonRes, "invisibility_potion").size());
    }

}
