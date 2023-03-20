package dungeonmania.ItemTests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import static dungeonmania.TestUtils.getInventory;
import static dungeonmania.TestUtils.countEntityOfType;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import dungeonmania.DungeonManiaController;
import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.util.Direction;

public class InvincibilityPotionTest {
    @Test
    @DisplayName("Test create invincibility potions")
    public void testCreateInvincibility() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse initDungeonRes = dmc.newGame("d_itemTests_basicPotion", "c_itemTests_basicPotion");
        assertTrue(countEntityOfType(initDungeonRes, "invincibility_potion") == 1);
    }

    @Test
    @DisplayName("Test pickup invincibility potions")
    public void testPickupInvincibility() {
        DungeonManiaController dmc = new DungeonManiaController();
        dmc.newGame("d_itemTests_basicPotion", "c_itemTests_basicPotion");
        DungeonResponse afterPickupRes = dmc.tick(Direction.RIGHT);
        assertEquals(1, getInventory(afterPickupRes, "invincibility_potion").size());
        
        // simple test pickup
    }

    @Test
    @DisplayName("Test consume potion")
    public void testConsumePotion() throws IllegalArgumentException, InvalidActionException {
        DungeonManiaController dmc = new DungeonManiaController();
        dmc.newGame("d_itemTests_basicPotion", "c_itemTests_basicPotion");

        DungeonResponse afterPickupRes = dmc.tick(Direction.RIGHT);
        String itemId = getInventory(afterPickupRes, "invincibility_potion").get(0).getId();

        DungeonResponse afterConsumeRes = dmc.tick(itemId);
        assertEquals(0, getInventory(afterConsumeRes, "invincibility_potion").size());

        // can't really check that the state changes
        // check state with duration and enemy behaviour
    }

    @Test
    @DisplayName("Test duration")
    public void testInvincibilityDuration() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse initDungonRes = dmc.newGame("d_movementTest_testMovementDown", "c_movementTest_testMovementDown");
        // create game
        // move to pick it up
        // consume it (need to add a method in game to consume potion)
        // check the state
        // keep moving until it's gone
        // check the state
    }

    // @Test
    // @DisplayName("Test merceneries and zombies altered movement")
    // public void testAlteredMovement() {
    //     DungeonManiaController dmc = new DungeonManiaController();
    //     DungeonResponse initDungonRes = dmc.newGame("d_movementTest_testMovementDown", "c_movementTest_testMovementDown");
    // }

    // @Test
    // @DisplayName("Test battle instantly ends")
    // public void testBattleInstaWin() {
    //     DungeonManiaController dmc = new DungeonManiaController();
    //     DungeonResponse initDungonRes = dmc.newGame("d_movementTest_testMovementDown", "c_movementTest_testMovementDown");
    // }    

}