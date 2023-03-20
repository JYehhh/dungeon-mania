package dungeonmania.staticTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import dungeonmania.staticentity.Boulder;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;
import dungeonmania.DungeonManiaController;
import dungeonmania.Game;
import dungeonmania.player.Player;
import dungeonmania.response.models.DungeonResponse;

public class BoulderTest {
    @Test
    @DisplayName("Test the door entity is correctly created")
    public void createBoulderTest() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_test_dungeon", "simple");
        
        Game game = dmc.getGame(res.getDungeonId());
        
        Boulder boulder = new Boulder(new Position(1, 3));
        game.addEntity(boulder);
        
        assertTrue(new Position(1, 3).equals(game.getEntity(boulder.getId()).getPosition()));
    }

    @Test
    @DisplayName("Test player can move the boulder")
    public void playerMoveBoulderTest() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_test_dungeon", "simple");
        
        Game game = dmc.getGame(res.getDungeonId());
        Player p = game.getPlayer();
        Boulder b = new Boulder(new Position(1, 2));
        game.addEntity(b);
        // push the boulder down
        p.move(game, Direction.DOWN);
        assertEquals(new Position(1, 2), game.getEntity(p.getId()).getPosition());
        assertEquals(new Position(1, 3), game.getEntity(b.getId()).getPosition());
    }

    @Test
    @DisplayName("Test player cannot move more than one boulder")
    public void playerMoveOnlyOneBoulderTest() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_test_dungeon", "simple");
        
        Game game = dmc.getGame(res.getDungeonId());

        Player p = game.getPlayer();

        Boulder b1 = new Boulder(new Position(1, 2));
        game.addEntity(b1);
        Boulder b2 = new Boulder(new Position(1, 3));
        game.addEntity(b2);

        // push the boulder up failed, player and boulders stay still
        p.move(game, Direction.DOWN);
        assertEquals(new Position(1, 1), game.getEntity(p.getId()).getPosition());
        assertEquals(new Position(1, 2), game.getEntity(b1.getId()).getPosition());
        assertEquals(new Position(1, 3), game.getEntity(b2.getId()).getPosition());

    }
}
