package dungeonmania;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static dungeonmania.TestUtils.getEntities;
import static dungeonmania.TestUtils.getGoals;
import static dungeonmania.TestUtils.countEntityOfType;

import dungeonmania.response.models.DungeonResponse;
import dungeonmania.util.Position;


public class DungeonGenerationTest {
    private static String config = "c_assassinTest_basicMovement";

    private static DungeonResponse genericDungeonGeneration(int xStart, int yStart, int xEnd, int yEnd, DungeonManiaController controller) {
        DungeonResponse res = controller.generateDungeon(xStart, yStart, xEnd, yEnd, config);
        assertEquals(1, countEntityOfType(res, "player"));
        assertEquals(1, countEntityOfType(res, "exit"));
        return controller.getDungeonResponseModel();
    }

    @Test
    @DisplayName("Test correct player & exit position : +ve case")
    public void testCorrectPositionPositive() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = genericDungeonGeneration(0, 0, 50, 50, dmc);
        Position playerPos = getEntities(res, "player").get(0).getPosition();
        Position exitPos = getEntities(res, "exit").get(0).getPosition();
        Position expectedPlayer = new Position(0, 0);
        Position expectedExit = new Position(50, 50);
        assertEquals(playerPos, expectedPlayer);
        assertEquals(exitPos, expectedExit);
    }

    @Test
    @DisplayName("Test correct player & exit position : -ve case")
    public void testCorrectPositionNegative() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = genericDungeonGeneration(-50, -50, -1, -1, dmc);
        Position playerPos = getEntities(res, "player").get(0).getPosition();
        Position exitPos = getEntities(res, "exit").get(0).getPosition();
        Position expectedPlayer = new Position(-50, -50);
        Position expectedExit = new Position(-1, -1);
        assertEquals(playerPos, expectedPlayer);
        assertEquals(exitPos, expectedExit);
    }

    @Test
    @DisplayName("Test goal condition")
    public void testCorrectGoalCondition() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = genericDungeonGeneration(-50, -50, -1, -1, dmc);
        assertTrue(getGoals(res).contains(":exit"));
    }

    @Test
    @DisplayName("Test for invalid config file")
    public void testInvalidArguments() {
        DungeonManiaController dmc = new DungeonManiaController();
        assertThrows(IllegalArgumentException.class, () -> dmc.generateDungeon(0, 0, 50, 50, ""));
    }
}
