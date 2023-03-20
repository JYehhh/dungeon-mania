package dungeonmania.staticTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import dungeonmania.DungeonManiaController;
import dungeonmania.Game;
import dungeonmania.movingentity.Mercenary;
import dungeonmania.player.Player;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.staticentity.Boulder;
import dungeonmania.staticentity.Wall;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;


public class WallTest {
    @Test
    @DisplayName("Test the wall entity is correctly created")
    public void createWallTest() {

        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_test_dungeon", "simple");
        
        Game game = dmc.getGame(res.getDungeonId());

        Wall wall = new Wall(new Position(1, 3));
        game.addEntity(wall);

        assertTrue(new Position(1, 3).equals(game.getEntity(wall.getId()).getPosition()));
    }

    @Test 
    @DisplayName("Test the wall blocks the movement of player")
    public void wallBlockPlayerTest() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_test_dungeon", "simple");
        
        Game game = dmc.getGame(res.getDungeonId());
        game.addEntity(new Wall(new Position(0, 0)));
        game.addEntity(new Wall(new Position(0, 1)));
        game.addEntity(new Wall(new Position(0, 2)));
        game.addEntity(new Wall(new Position(1, 0)));
        game.addEntity(new Wall(new Position(2, 0)));
        // the player has walls on the left and up
        Player p = game.getPlayer();
        // the player will be blocked if moving left or up
        p.move(game, Direction.LEFT);
        assertEquals(new Position(1, 1), game.getEntity(p.getId()).getPosition());
        p.move(game, Direction.UP);
        assertEquals(new Position(1, 1), game.getEntity(p.getId()).getPosition());
    }

    @Test 
    @DisplayName("Test the wall blocks the movement of enermies")
    public void wallBlockEnermiesTest() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_test_dungeon", "simple");
        
        Game game = dmc.getGame(res.getDungeonId());

        game.addEntity(new Wall(new Position(1, 3)));
        Mercenary m = new Mercenary(new Position(1, 4), game.getConfig().getMercenaryHealth(), game.getConfig().getMercenaryAttack());
        game.addEntity(m);

        // put player on the top of the wall so mercenary is moving up
        
        m.tick(game);
        assertEquals(new Position(0, 4), game.getEntity(m.getId()).getPosition());
    }

    @Test 
    @DisplayName("Test the wall blocks the movement of boulder")
    public void wallBlockBoulderTest() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_test_dungeon", "simple");
        
        Game game = dmc.getGame(res.getDungeonId());
        game.addEntity(new Wall(new Position(1, 3)));


        Boulder b = new Boulder(new Position(1, 2));
        game.addEntity(b);
        Player p = game.getPlayer();

        p.move(game, Direction.DOWN);
        assertEquals(new Position(1, 2), game.getEntity(b.getId()).getPosition());
    }
}
