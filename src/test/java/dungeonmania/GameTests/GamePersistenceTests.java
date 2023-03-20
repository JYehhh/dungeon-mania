package dungeonmania.GameTests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static dungeonmania.TestUtils.getPlayer;
import static dungeonmania.TestUtils.getEntities;
import static dungeonmania.TestUtils.getInventory;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import dungeonmania.DungeonManiaController;
import dungeonmania.Game;
import dungeonmania.TestUtils;
import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.movingentity.Mercenary;
import dungeonmania.player.Player;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class GamePersistenceTests {
    @Test
    @DisplayName("Test saving game")
    public void testSaveGame() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse initDungeonRes = dmc.newGame("d_gameTest_basic", "c_gameTest_basic");
        
        initDungeonRes = dmc.tick(Direction.LEFT);
        dmc.saveGame(initDungeonRes.getDungeonName());
        assertEquals(1, dmc.allGames().size());
    }

    @Test
    @DisplayName("Test loading game saves player position")
    public void testSavePlayerPosition() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse initDungeonRes = dmc.newGame("d_gameTest_basic", "c_gameTest_basic");
        
        initDungeonRes = dmc.tick(Direction.RIGHT);

        EntityResponse player1 = getPlayer(initDungeonRes).get();
        Position pos = new Position(1, 1);
        assertEquals(player1.getPosition(), pos);

        initDungeonRes = dmc.saveGame(initDungeonRes.getDungeonName());
        initDungeonRes = dmc.loadGame(initDungeonRes.getDungeonName());

        player1 = getPlayer(initDungeonRes).get();
        assertEquals(pos, player1.getPosition());

        initDungeonRes = dmc.tick(Direction.DOWN);
        player1 = getPlayer(initDungeonRes).get();
        assertEquals(new Position(1, 2), player1.getPosition());
    }

    @Test
    @DisplayName("Test loading game saves static entity position")
    public void testSaveStaticEntityPosition() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse initDungeonRes = dmc.newGame("d_gameTest_basic", "c_gameTest_basic");
        List<EntityResponse> woodList = getEntities(initDungeonRes, "wood");  
        EntityResponse wood1 = woodList.get(0);
        Position pos = new Position(1, 1);
        assertEquals(wood1.getPosition(), pos);

        initDungeonRes = dmc.saveGame(initDungeonRes.getDungeonName());
        initDungeonRes = dmc.loadGame(initDungeonRes.getDungeonName());

        woodList = getEntities(initDungeonRes, "wood"); 
        wood1 = woodList.get(0);

        assertEquals(wood1.getPosition(), pos);
    }

    @Test
    @DisplayName("Test loading game saves player inventory")
    public void testSavePlayerInventory() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse initDungeonRes = dmc.newGame("d_gameTest_basic", "c_gameTest_basic");
        
        initDungeonRes = dmc.tick(Direction.RIGHT);
        assertEquals(1, getInventory(initDungeonRes, "wood").size());

        initDungeonRes = dmc.saveGame(initDungeonRes.getDungeonName());
        initDungeonRes = dmc.loadGame(initDungeonRes.getDungeonName());

        assertEquals(1, getInventory(initDungeonRes, "wood").size());
    }

    @Test
    @DisplayName("Test loading game saves open door status") 
    public void testSaveDoorStatus() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse initDungeonRes = dmc.newGame("d_DoorsKeysTest_useKeyWalkThroughOpenDoor", "c_DoorsKeysTest_useKeyWalkThroughOpenDoor");
        initDungeonRes = dmc.tick(Direction.RIGHT);
        initDungeonRes = dmc.tick(Direction.RIGHT);
        initDungeonRes = dmc.tick(Direction.RIGHT);

        initDungeonRes = dmc.saveGame(initDungeonRes.getDungeonName());
        initDungeonRes = dmc.loadGame(initDungeonRes.getDungeonName());

        assertEquals(0, getInventory(initDungeonRes, "key").size());
        initDungeonRes = dmc.tick(Direction.LEFT);

        EntityResponse player1 = getPlayer(initDungeonRes).get();
        assertEquals(player1.getPosition(), new Position(3, 1));
    }

    @Test
    @DisplayName("Test loading game saves moving entity position")
    public void testSaveMovingEntityPosition() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse initDungeonRes = dmc.newGame("d_bribeTest_saveBribeStatus", "c_bribeTest_saveBribeStatus");

        initDungeonRes = dmc.tick(Direction.RIGHT);

        Game game = dmc.getGame(initDungeonRes.getDungeonId());

        Mercenary mercenary = (Mercenary) game.getEntitiesByType("mercenary").get(0);
        Position mercenaryPosition = new Position(7, 1);
        assertEquals(mercenaryPosition, mercenary.getPosition());

        initDungeonRes = dmc.saveGame(initDungeonRes.getDungeonName());
        initDungeonRes = dmc.loadGame(initDungeonRes.getDungeonName());

        game = dmc.getGame(initDungeonRes.getDungeonId());

        mercenary = (Mercenary) game.getEntitiesByType("mercenary").get(0);
        assertEquals(mercenaryPosition, mercenary.getPosition());
    }

    @Test
    @DisplayName("Test loading game saves player potion status")
    public void testSavePotionStatus() throws IllegalArgumentException, InvalidActionException {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse initDungeonRes = dmc.newGame("d_itemTests_basicPotion", "c_itemTests_basicPotion");
        
        initDungeonRes = dmc.tick(Direction.RIGHT);
        String itemId = getInventory(initDungeonRes, "invincibility_potion").get(0).getId();

        initDungeonRes = dmc.tick(itemId);
        assertEquals(0, getInventory(initDungeonRes, "invincibility_potion").size());

        initDungeonRes = dmc.saveGame(initDungeonRes.getDungeonName());
        initDungeonRes = dmc.loadGame(initDungeonRes.getDungeonName());

        Game game = dmc.getGame(initDungeonRes.getDungeonId());

        Player player = game.getPlayer();
        assertEquals("invincibile", player.getPotionStateString());
    }

    @Test
    @DisplayName("Test loading game saves moving entities bribe status")
    public void testSaveBribeStatus() throws IllegalArgumentException, InvalidActionException {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse initDungeonRes = dmc.newGame("d_bribeTest_saveBribeStatus", "c_bribeTest_saveBribeStatus");

        initDungeonRes = dmc.tick(Direction.RIGHT);
        initDungeonRes = dmc.tick(Direction.RIGHT);
        initDungeonRes = dmc.tick(Direction.RIGHT);

        Game game = dmc.getGame(initDungeonRes.getDungeonId());

        Mercenary mercenary = (Mercenary) game.getEntitiesByType("mercenary").get(0);
        String mercenaryId = mercenary.getId();

        dmc.interact(mercenaryId);

        assertEquals(true, mercenary.isBribed());

        initDungeonRes = dmc.saveGame(initDungeonRes.getDungeonName());
        initDungeonRes = dmc.loadGame(initDungeonRes.getDungeonName());

        game = dmc.getGame(initDungeonRes.getDungeonId());
        mercenary = (Mercenary) game.getEntitiesByType("mercenary").get(0);
        assertEquals(true, mercenary.isBribed());
    }


    @Test
    @DisplayName("Test loading game saves armour durability after battle") 
    public void testSaveWeaponDurability() {

    }

    @Test
    @DisplayName("Test loading game saves goal status")
    public void testSaveGoalStatus() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse initDungeonRes = dmc.newGame("d_bribeTest_saveBribeStatus", "c_bribeTest_saveBribeStatus");

        initDungeonRes = dmc.tick(Direction.RIGHT);
        initDungeonRes = dmc.tick(Direction.RIGHT);
        initDungeonRes = dmc.tick(Direction.RIGHT);

        assertEquals("(:exit)", TestUtils.getGoals(initDungeonRes));
        
        Game game = dmc.getGame(initDungeonRes.getDungeonId());

        initDungeonRes = dmc.saveGame(initDungeonRes.getDungeonName());
        initDungeonRes = dmc.loadGame(initDungeonRes.getDungeonName());

        assertEquals("(:exit)", TestUtils.getGoals(initDungeonRes));
    }

}
