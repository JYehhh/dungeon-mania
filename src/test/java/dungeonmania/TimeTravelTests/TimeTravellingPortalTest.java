package dungeonmania.TimeTravelTests;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import dungeonmania.DungeonManiaController;
import dungeonmania.Game;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.staticentity.TimeTravellingPortal;
import dungeonmania.util.Position;

public class TimeTravellingPortalTest {
    @Test
    @DisplayName("Test the TimeTravellingPortal can be created sucessfully")
    public void createTimeTravellingPortalTest() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_test_dungeon", "simple");
        
        Game game = dmc.getGame(res.getDungeonId());

        TimeTravellingPortal ttp = new TimeTravellingPortal(new Position(2, 1));
        game.addEntity(ttp);

        assertTrue(new Position(2, 1).equals(game.getEntity(ttp.getId()).getPosition()));
    }
}
