package dungeonmania.staticTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import dungeonmania.staticentity.Door;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;
import dungeonmania.DungeonManiaController;
import dungeonmania.Game;
import dungeonmania.item.Key;
import dungeonmania.player.Player;
import dungeonmania.response.models.DungeonResponse;

public class DoorTest {
    @Test
    @DisplayName("Test the door entity is correctly created")
    public void createDoorTest() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_test_dungeon", "simple");
        
        Game game = dmc.getGame(res.getDungeonId());

        Door door = new Door(new Position(1, 3), 1);
        game.addEntity(door);

        assertTrue(new Position(1, 3).equals(game.getEntity(door.getId()).getPosition()));
    }

    @Test
    @DisplayName("Test player can open the door with the matching key")
    public void playerOpenDoorWithKeyTest() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_test_dungeon", "simple");
        
        Game game = dmc.getGame(res.getDungeonId());

        Player p = game.getPlayer();
        Door d = new Door(new Position(1, 3), 1);
        game.addEntity(d);
        Key k = new Key(new Position(1, 2), 1);
        game.addEntity(k);

        // player move down to pick up the key
        p.move(game, Direction.DOWN);
        assertEquals(k, p.getItem(k.getId()));
        assertEquals(new Position(1, 2), game.getEntity(p.getId()).getPosition());

        // player move down again to open the door
        p.move(game, Direction.DOWN);
        assertTrue(d.isOpen());
        assertEquals(new Position(1, 3), game.getEntity(p.getId()).getPosition());
    }

    @Test
    @DisplayName("Test playeris blocked by the door without a key")
    public void playerBlockedByDoorWithoutKeyTest() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_test_dungeon", "simple");
        
        Game game = dmc.getGame(res.getDungeonId());

        Player p = game.getPlayer();
        Door d = new Door(new Position(1, 2), 1);
        game.addEntity(d);

        // player move down to open the door but failed
        p.move(game, Direction.DOWN);
        assertFalse(d.isOpen());
        assertEquals(new Position(1, 1), game.getEntity(p.getId()).getPosition());
    }

    @Test
    @DisplayName("Test player is blocked by the door without a matching key")
    public void playerBlockedByDoorWithoutMatchingKeyTest() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_test_dungeon", "simple");
        
        Game game = dmc.getGame(res.getDungeonId());

        Player p = game.getPlayer();
        Door d = new Door(new Position(1, 3), 1);
        game.addEntity(d);
        Key k = new Key(new Position(1, 2), 2);
        game.addEntity(k);

        // player move down to pick up the key
        p.move(game, Direction.DOWN);
        assertEquals(k, p.getItem(k.getId()));
        assertEquals(new Position(1, 2), game.getEntity(p.getId()).getPosition());

        // player move down again to open the door
        p.move(game, Direction.DOWN);
        assertFalse(d.isOpen());
        assertEquals(new Position(1, 2), game.getEntity(p.getId()).getPosition());
    }
}
