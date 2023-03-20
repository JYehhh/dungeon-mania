package dungeonmania.TimeTravelTests;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import dungeonmania.DungeonManiaController;
import dungeonmania.Game;
import dungeonmania.item.TimeTurner;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.util.Position;

public class TimeTurnerTest {
    @Test
    @DisplayName("Test the TimeTurner can be created successfully")
    public void createTimeTurnerTest() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_test_dungeon", "simple");
        
        Game game = dmc.getGame(res.getDungeonId());

        TimeTurner turner = new TimeTurner(new Position(2, 1));
        game.addEntity(turner);

        assertTrue(new Position(2, 1).equals(game.getEntity(turner.getId()).getPosition()));
    }
}
