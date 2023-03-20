package dungeonmania.staticTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import dungeonmania.staticentity.Boulder;
import dungeonmania.staticentity.FloorSwitch;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;
import dungeonmania.DungeonManiaController;
import dungeonmania.Game;
import dungeonmania.player.Player;
import dungeonmania.response.models.DungeonResponse;

public class FloorSwitchTest {
    @Test
    @DisplayName("Test the floor switch entity is correctly created")
    public void createSwitchTest() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_test_dungeon", "simple");
        
        Game game = dmc.getGame(res.getDungeonId());
    
        FloorSwitch floorSwitch = new FloorSwitch(new Position(1, 3));
        game.addEntity(floorSwitch);
        
        assertTrue(new Position(1, 3).equals(game.getEntity(floorSwitch.getId()).getPosition()));
    }

    @Test
    @DisplayName("Test floor switch acts like emoty square without boulder on it")
    public void switchBehaveLikeEmptySquareTest() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_test_dungeon", "simple");
        
        Game game = dmc.getGame(res.getDungeonId());

        Player p = game.getPlayer();
        FloorSwitch fs = new FloorSwitch(new Position(0, 1));
        game.addEntity(fs);

        p.move(game, Direction.LEFT);
        assertFalse(fs.isTriggered(game));
        assertEquals(new Position(0, 1), game.getEntity(p.getId()).getPosition());
    }

    @Test
    @DisplayName("Test boulder triggers a switch and then untriggers it")
    public void boulderTriggerSwitchTest() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_test_dungeon", "simple");
        
        Game game = dmc.getGame(res.getDungeonId());

        Player p = game.getPlayer();
        Boulder b = new Boulder(new Position(1, 2));
        game.addEntity(b);
        FloorSwitch fs = new FloorSwitch(new Position(1, 3));
        game.addEntity(fs);

        // player move the boulder on the switch to trigger it
        p.move(game, Direction.DOWN);
        assertEquals(new Position(1, 2), game.getEntity(p.getId()).getPosition());
        assertEquals(new Position(1, 3), game.getEntity(b.getId()).getPosition());
        assertTrue(fs.isTriggered(game));
        
        // player move the boulder off the switch to untrigger it
        p.move(game, Direction.DOWN);
        assertEquals(new Position(1, 3), game.getEntity(p.getId()).getPosition());
        assertEquals(new Position(1, 4), game.getEntity(b.getId()).getPosition());
        assertFalse(fs.isTriggered(game));
    }
}
