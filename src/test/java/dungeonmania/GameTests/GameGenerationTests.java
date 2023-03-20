package dungeonmania.GameTests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import static dungeonmania.TestUtils.getPlayer;
import static dungeonmania.TestUtils.getGoals;
import static dungeonmania.TestUtils.countEntityOfType;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import dungeonmania.DungeonManiaController;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;


public class GameGenerationTests {
    @Test
    @DisplayName("Test the game is being created")
    public void testGameCreation() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse initDungeonRes = dmc.newGame("d_gameTest_basic", "c_gameTest_basic");
        
        assertEquals(initDungeonRes.getDungeonName(), "d_gameTest_basic");
    }

    @Test
    @DisplayName("Test player created successfully")
    public void testPlayerCreated() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse initDungeonRes = dmc.newGame("d_gameTest_basic", "c_gameTest_basic");
        EntityResponse player1 = getPlayer(initDungeonRes).get();
        assertTrue(countEntityOfType(initDungeonRes, player1.getType()) == 1);
    }
    
    @Test
    @DisplayName("Testing basic goal creation")
    public void testBasicGoal() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse initDungeonRes = dmc.newGame("d_gameTest_basic", "c_gameTest_basic");
        assertTrue(getGoals(initDungeonRes).contains(":exit"));
    }

    @Test
    @DisplayName("Testing composite goal creation")
    public void testCompositeGoal() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse initDungeonRes = dmc.newGame("d_complexGoalsTest_andAll", "c_complexGoalsTest_andAll");
        System.out.println(getGoals(initDungeonRes).toString());
        assertTrue(getGoals(initDungeonRes).contains(":exit"));
        assertTrue(getGoals(initDungeonRes).contains(":treasure"));
        assertTrue(getGoals(initDungeonRes).contains(":enemies"));
        assertTrue(getGoals(initDungeonRes).contains(":boulders"));
    }

    @Test
    @DisplayName("Testing entity generation")
    public void testEntityGeneration() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse initDungeonRes = dmc.newGame("d_gameTest_basic", "c_gameTest_basic");
        assertTrue(countEntityOfType(initDungeonRes, "wood") == 1);
    }

    @Test
    @DisplayName("Testing multiple entity generation of same type")
    public void testMultipleEntityGeneration() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse initDungeonRes = dmc.newGame("d_gameTest_multipleEntities", "c_gameTest_multipleEntities");
        assertTrue(countEntityOfType(initDungeonRes, "wood") == 2);
    }

    @Test
    @DisplayName("Testing tick moves player")
    public void testTickMovesPlayer() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse initDungeonRes = dmc.newGame("d_gameTest_basic", "c_gameTest_basic");
        assertTrue(countEntityOfType(initDungeonRes, "wood") == 1);
        DungeonResponse res = dmc.tick(Direction.LEFT);
        EntityResponse player1 = getPlayer(res).get();
        assertEquals(new Position(-1,1), player1.getPosition());
    }
}