package dungeonmania;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import dungeonmania.movingentity.Spider;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;


public class GoalTests {
    @Test
    @DisplayName("Test all or goal conditions - EXIT")
    public void testAllOrGoalConditionsExit() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse initDungeonRes = dmc.newGame("d_goalAllOr", "c_gameTest_basic");
        
        initDungeonRes = dmc.tick(Direction.DOWN);
        initDungeonRes = dmc.tick(Direction.DOWN);
        initDungeonRes = dmc.tick(Direction.RIGHT);
        initDungeonRes = dmc.tick(Direction.RIGHT);
        assertEquals("", TestUtils.getGoals(initDungeonRes));
    }

    @Test
    @DisplayName("Test all or goal conditions - TREASURE")
    public void testAllOrGoalConditionsTreasure() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse initDungeonRes = dmc.newGame("d_goalAllOr", "c_gameTest_basic");
        
        initDungeonRes = dmc.tick(Direction.UP);
        initDungeonRes = dmc.tick(Direction.RIGHT);
        initDungeonRes = dmc.tick(Direction.RIGHT);
        initDungeonRes = dmc.tick(Direction.RIGHT);
        initDungeonRes = dmc.tick(Direction.DOWN);
        initDungeonRes = dmc.tick(Direction.DOWN);
        initDungeonRes = dmc.tick(Direction.LEFT);
        assertEquals("", TestUtils.getGoals(initDungeonRes));
    }

    @Test
    @DisplayName("Test all or goal conditions - ENEMIES")
    public void testAllOrGoalConditionsEnemies() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse initDungeonRes = dmc.newGame("d_goalAllOr", "c_gameTest_basic");
        initDungeonRes = dmc.tick(Direction.RIGHT);
        assertEquals("", TestUtils.getGoals(initDungeonRes));
    }

    @Test
    @DisplayName("Test all or goal conditions - BOULDERS")
    public void testAllOrGoalConditionsBoulders() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse initDungeonRes = dmc.newGame("d_goalAllOr", "c_gameTest_basic");

        initDungeonRes = dmc.tick(Direction.UP);
        initDungeonRes = dmc.tick(Direction.DOWN);
        initDungeonRes = dmc.tick(Direction.RIGHT);
        initDungeonRes = dmc.tick(Direction.RIGHT);
        assertEquals("", TestUtils.getGoals(initDungeonRes));
    }

    @Test
    @DisplayName("Test treasure and sun stone goal condition")
    public void testTreasureAndSunStone() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse initDungeonRes = dmc.newGame("d_goalTreasureSunStone", "c_goalTreasureSunStone");
        assertEquals(":treasure", TestUtils.getGoals(initDungeonRes));

        initDungeonRes = dmc.tick(Direction.RIGHT);
        initDungeonRes = dmc.tick(Direction.RIGHT);

        assertEquals("", TestUtils.getGoals(initDungeonRes));
    }

    @Test
    @DisplayName("Test exit condition and treasure")
    public void testExitAndTreasure() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse initDungeonRes = dmc.newGame("d_goalExitTreasure", "c_gameTest_basic");
        assertEquals("(:treasure AND :exit)", TestUtils.getGoals(initDungeonRes));

        initDungeonRes = dmc.tick(Direction.UP);
        initDungeonRes = dmc.tick(Direction.RIGHT);
        initDungeonRes = dmc.tick(Direction.RIGHT);
        initDungeonRes = dmc.tick(Direction.DOWN);

        assertEquals("(:treasure)", TestUtils.getGoals(initDungeonRes));

        initDungeonRes = dmc.tick(Direction.LEFT);
        assertEquals("(:exit)", TestUtils.getGoals(initDungeonRes));

        initDungeonRes = dmc.tick(Direction.RIGHT);
        assertEquals("", TestUtils.getGoals(initDungeonRes));
    }

    @Test
    @DisplayName("Test exit condition and enemies")
    public void testExitAndEnemies() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse initDungeonRes = dmc.newGame("d_goalExitEnemies", "c_gameTest_basic");
        Game game = dmc.getGame(initDungeonRes.getDungeonId());

        assertEquals("(:enemies AND :exit)", TestUtils.getGoals(initDungeonRes));

        initDungeonRes = dmc.tick(Direction.UP);

        Position pos = new Position(1, 1);
        Spider spider = new Spider(pos, 5, 1);
        game.addEntity(spider);

        initDungeonRes = dmc.tick(Direction.RIGHT);

        assertEquals("(:exit)", TestUtils.getGoals(initDungeonRes));

        initDungeonRes = dmc.tick(Direction.RIGHT);
        initDungeonRes = dmc.tick(Direction.DOWN);

        assertEquals("", TestUtils.getGoals(initDungeonRes));
    }

    @Test
    @DisplayName("Test exit condition and switch")
    public void testExitAndSwitch() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse initDungeonRes = dmc.newGame("d_goalExitSwitch", "c_gameTest_basic");
        assertEquals("(:boulders AND :exit)", TestUtils.getGoals(initDungeonRes));

        initDungeonRes = dmc.tick(Direction.RIGHT);
        
        assertEquals("(:boulders)", TestUtils.getGoals(initDungeonRes));

        initDungeonRes = dmc.tick(Direction.RIGHT);
        
        assertEquals("(:exit)", TestUtils.getGoals(initDungeonRes));

        initDungeonRes = dmc.tick(Direction.LEFT);
        
        assertEquals("", TestUtils.getGoals(initDungeonRes));
    }

    @Test
    @DisplayName("Test all and goal conditions")
    public void testAllAndGoalConditions() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse initDungeonRes = dmc.newGame("d_complexGoalsTest_andAll", "c_complexGoalsTest_andAll");

        assertTrue(TestUtils.getGoals(initDungeonRes).contains(":exit"));
        assertTrue(TestUtils.getGoals(initDungeonRes).contains(":treasure"));
        assertTrue(TestUtils.getGoals(initDungeonRes).contains(":boulders"));
        assertTrue(TestUtils.getGoals(initDungeonRes).contains(":enemies"));

        // kill spider
        initDungeonRes = dmc.tick(Direction.RIGHT);
        assertTrue(TestUtils.getGoals(initDungeonRes).contains(":exit"));
        assertTrue(TestUtils.getGoals(initDungeonRes).contains(":treasure"));
        assertTrue(TestUtils.getGoals(initDungeonRes).contains(":boulders"));
        assertFalse(TestUtils.getGoals(initDungeonRes).contains(":enemies"));

        // move boulder onto switch
        initDungeonRes = dmc.tick(Direction.RIGHT);
        assertTrue(TestUtils.getGoals(initDungeonRes).contains(":exit"));
        assertTrue(TestUtils.getGoals(initDungeonRes).contains(":treasure"));
        assertFalse(TestUtils.getGoals(initDungeonRes).contains(":boulders"));
        assertFalse(TestUtils.getGoals(initDungeonRes).contains(":enemies"));

        // pickup treasure
        initDungeonRes = dmc.tick(Direction.DOWN);
        assertTrue(TestUtils.getGoals(initDungeonRes).contains(":exit"));
        assertFalse(TestUtils.getGoals(initDungeonRes).contains(":treasure"));
        assertFalse(TestUtils.getGoals(initDungeonRes).contains(":boulders"));
        assertFalse(TestUtils.getGoals(initDungeonRes).contains(":enemies"));

        // move to exit
        initDungeonRes = dmc.tick(Direction.DOWN);
        assertEquals("", TestUtils.getGoals(initDungeonRes));
    }


    
}