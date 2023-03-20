package dungeonmania.staticTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import dungeonmania.staticentity.Portal;
import dungeonmania.staticentity.Wall;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;
import dungeonmania.DungeonManiaController;
import dungeonmania.Game;
import dungeonmania.movingentity.ZombieToast;
import dungeonmania.player.Player;
import dungeonmania.response.models.DungeonResponse;

public class PortalTest {
    @Test
    @DisplayName("Test the portal entity is correctly created")
    public void createPortalTest() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_test_dungeon", "simple");
        
        Game game = dmc.getGame(res.getDungeonId());


        Portal portal = new Portal(new Position(1, 3), "RED");
        game.addEntity(portal);

        assertTrue(new Position(1, 3).equals(game.getEntity(portal.getId()).getPosition()));
    }

    @Test
    @DisplayName("Test the simple teleport")
    public void simpleTeleportTest() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_test_dungeon", "simple");
        
        Game game = dmc.getGame(res.getDungeonId());
        Player p = game.getPlayer();

        Portal prt1 = new Portal(new Position(1, 2), "RED");
        game.addEntity(prt1);
        Portal prt2 = new Portal(new Position(1, 5), "RED");
        game.addEntity(prt2);

        List<Position> potentialPos = new ArrayList<>();
        potentialPos.add(new Position(1, 6));
        potentialPos.add(new Position(2, 5));
        potentialPos.add(new Position(1, 4));
        potentialPos.add(new Position(0, 5));

        // player teleports to p2 from p1
        // teleports to the down next square to p2
        p.move(game, Direction.DOWN);
        assertTrue(potentialPos.contains(game.getEntity(p.getId()).getPosition()));
    }

    @Test
    @DisplayName("Test teleport to a blocked entity")
    public void teleportBlockedTest() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_test_dungeon", "simple");
        
        Game game = dmc.getGame(res.getDungeonId());

        Player p = game.getPlayer();

        Portal prt1 = new Portal(new Position(1, 2), "RED");
        game.addEntity(prt1);
        Portal prt2 = new Portal(new Position(1, 5), "RED");
        game.addEntity(prt2);

        // construct walls cardinally adjacent to the destination portal prt2 
        Wall w1 = new Wall(new Position(1, 4));
        game.addEntity(w1);
        Wall w2 = new Wall(new Position(1, 6));
        game.addEntity(w2);
        Wall w3 = new Wall(new Position(0, 5));
        game.addEntity(w3);
        Wall w4 = new Wall(new Position(2, 5));
        game.addEntity(w4);

        // player cannot teleports to prt2 from prt1
        // so stay still
        p.move(game, Direction.DOWN);
        assertEquals(new Position(1, 1), game.getEntity(p.getId()).getPosition());
    }

    @Test
    @DisplayName("Test zombie cannot teleport")
    public void teleportZombieFailTest() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_test_dungeon", "simple");
        
        Game game = dmc.getGame(res.getDungeonId());

        ZombieToast zt = new ZombieToast(new Position(2, 2), game.getConfig().getZombieHealth(), game.getConfig().getZombieAttack());
        game.addEntity(zt);

        // add walls around zombie to enforce it move down
        game.addEntity(new Wall(new Position(1, 2)));
        game.addEntity(new Wall(new Position(3, 2)));
        game.addEntity(new Wall(new Position(2, 1)));

        Portal prt1 = new Portal(new Position(2, 3), "RED");
        game.addEntity(prt1);
        Portal prt2 = new Portal(new Position(2, 5), "RED");
        game.addEntity(prt2);

        // zombie teleports to p2 from p1 failed
        zt.tick(game); // zombie move up
        assertEquals(new Position(2, 2), game.getEntity(zt.getId()).getPosition());
    }
}
