package dungeonmania.staticTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import dungeonmania.staticentity.Exit;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;
import dungeonmania.DungeonManiaController;
import dungeonmania.Game;
import dungeonmania.player.Player;
import dungeonmania.response.models.DungeonResponse;

public class ExitTest {
    @Test
    @DisplayName("Test the exit entity is correctly created")
    public void createExitTest() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_test_dungeon", "simple");
        
        Game game = dmc.getGame(res.getDungeonId());
    
        Exit exit = new Exit(new Position(1, 3));
        game.addEntity(exit);
        
        assertTrue(new Position(1, 3).equals(game.getEntity(exit.getId()).getPosition()));

    }

    @Test
    @DisplayName("Test player can step on an exit")
    public void playerOnExitTest() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_test_dungeon", "simple");
        
        Game game = dmc.getGame(res.getDungeonId());

        Player p = game.getPlayer();
        Exit e = new Exit(new Position(0, 1));
        game.addEntity(e);

        p.move(game, Direction.LEFT);
        assertEquals(new Position(0, 1), game.getEntity(p.getId()).getPosition());

    }
}
