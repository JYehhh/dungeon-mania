package dungeonmania.MovingEntities;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import static dungeonmania.TestUtils.*;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import dungeonmania.DungeonManiaController;
import dungeonmania.Game;
import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.item.potion.InvincibilityPotion;
import dungeonmania.item.Key;
import dungeonmania.item.Treasure;
import dungeonmania.movingentity.Mercenary;
import dungeonmania.player.Player;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.staticentity.*;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

import java.lang.Math;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MercenaryTest {

    @Test
    @DisplayName("Test create mercenary")
    public void testCreateMercenary() {
        DungeonManiaController dmc = new DungeonManiaController();

        DungeonResponse res = dmc.newGame("d_mercenaryTest_basicMovement0", "c_mercenaryTest_basicMovement0");

        Game game = dmc.getGame(res.getDungeonId());

        assertEquals(1, countEntityOfType(game.getDungeonResponse(), "mercenary"));
    }
    
    @Test
    @DisplayName("Test simple mercenary movement")
    public void testMercenaryBasicMovementTowardsPlayer() {
        DungeonManiaController dmc = new DungeonManiaController();

        DungeonResponse res = dmc.newGame("d_mercenaryTest_basicMovement", "c_mercenaryTest_basicMovement");

        Game game = dmc.getGame(res.getDungeonId());

        Mercenary mercenary = new Mercenary(new Position(1, 5), 5, 1);
        game.addEntity(mercenary);

        game.tickDirection(Direction.STATIC);

        //assertTrue(mercenary.getX() == 1 && mercenary.getY() == 4);

        game.tickDirection(Direction.STATIC);

        //assertTrue(mercenary.getX() == 1 && mercenary.getY() == 3);

        game.tickDirection(Direction.STATIC);

        //assertTrue(mercenary.getX() == 1 && mercenary.getY() == 2);

    }

    @Test
    @DisplayName("Test simple mercenary movement around wall")
    public void testMercenaryBasicMovementAroundWall() {
        DungeonManiaController dmc = new DungeonManiaController();

        DungeonResponse res = dmc.newGame("d_mercenaryTest_basicMovement", "c_mercenaryTest_basicMovement");

        Game game = dmc.getGame(res.getDungeonId());

        Mercenary mercenary = new Mercenary(new Position(1, 5), 5, 1);
        game.addEntity(mercenary);

        for (int i = 1; i < 5; ++i) {
            Wall wall = new Wall(new Position(i, 4));
            game.addEntity(wall);
        }

        game.tickDirection(Direction.STATIC);

        assertTrue(mercenary.getX() == 0 && mercenary.getY() == 5);

        game.tickDirection(Direction.STATIC);

        assertTrue(mercenary.getX() == 0 && mercenary.getY() == 4);

        game.tickDirection(Direction.STATIC);

        assertTrue(mercenary.getX() == 0 && mercenary.getY() == 3);

        game.tickDirection(Direction.LEFT);

        assertTrue(mercenary.getX() == 0 && mercenary.getY() == 2);
    }

    @Test
    @DisplayName("Test simple mercenary movement around boulder")
    public void testMercenaryMovementAroundBoulder() {
        DungeonManiaController dmc = new DungeonManiaController();

        DungeonResponse res = dmc.newGame("d_mercenaryTest_basicMovement", "c_mercenaryTest_basicMovement");

        Game game = dmc.getGame(res.getDungeonId());

        Mercenary mercenary = new Mercenary(new Position(1, 5), 5, 1);
        game.addEntity(mercenary);

        for (int i = 1; i < 5; ++i) {
            Boulder boulder = new Boulder(new Position(i, 4));
            game.addEntity(boulder);
        }

        game.tickDirection(Direction.STATIC);

        assertTrue(mercenary.getX() == 0 && mercenary.getY() == 5);

        game.tickDirection(Direction.STATIC);

        assertTrue(mercenary.getX() == 0 && mercenary.getY() == 4);

        game.tickDirection(Direction.STATIC);

        assertTrue(mercenary.getX() == 0 && mercenary.getY() == 3);

        game.tickDirection(Direction.LEFT);

        assertTrue(mercenary.getX() == 0 && mercenary.getY() == 2);
    }

    @Test
    @DisplayName("Test simple mercenary movement through exit")
    public void testMercenaryMovementWithExit() {
        DungeonManiaController dmc = new DungeonManiaController();

        DungeonResponse res = dmc.newGame("d_mercenaryTest_basicMovement", "c_mercenaryTest_basicMovement");

        Game game = dmc.getGame(res.getDungeonId());

        Mercenary mercenary = new Mercenary(new Position(1, 5), 5, 1);
        game.addEntity(mercenary);

        Exit exit = new Exit(new Position(1, 4));
        game.addEntity(exit);

        game.tickDirection(Direction.STATIC);

        assertTrue(mercenary.getX() == 1 && mercenary.getY() == 4);

        game.tickDirection(Direction.STATIC);

        assertTrue(mercenary.getX() == 1 && mercenary.getY() == 3);
    }

    @Test
    @DisplayName("Test simple mercenary movement through floor switch")
    public void testMercenaryMovementThroughFloorSwitch() {
        DungeonManiaController dmc = new DungeonManiaController();

        DungeonResponse res = dmc.newGame("d_mercenaryTest_basicMovement", "c_mercenaryTest_basicMovement");

        Game game = dmc.getGame(res.getDungeonId());

        Mercenary mercenary = new Mercenary(new Position(1, 5), 5, 1);
        game.addEntity(mercenary);

        FloorSwitch floorSwitch = new FloorSwitch(new Position(1, 4));
        game.addEntity(floorSwitch);

        game.tickDirection(Direction.STATIC);

        assertTrue(mercenary.getX() == 1 && mercenary.getY() == 4);

        game.tickDirection(Direction.STATIC);

        assertTrue(mercenary.getX() == 1 && mercenary.getY() == 3);

        game.tickDirection(Direction.STATIC);

        assertTrue(mercenary.getX() == 1 && mercenary.getY() == 2);
    }

    @Test
    @DisplayName("Test simple mercenary movement through closed door")
    public void testMercenaryMovementWithClosedDoor() {
        DungeonManiaController dmc = new DungeonManiaController();

        DungeonResponse res = dmc.newGame("d_mercenaryTest_basicMovement", "c_mercenaryTest_basicMovement");

        Game game = dmc.getGame(res.getDungeonId());

        Mercenary mercenary = new Mercenary(new Position(1, 5), 5, 1);
        game.addEntity(mercenary);

        Key key = new Key(new Position(4, 1), 1);
        game.addEntity(key);

        Key key1 = new Key(new Position(4, 2), 2);
        game.addEntity(key1);

        Door door = new Door(new Position(1, 4), 1);
        game.addEntity(door);

        Door door1 = new Door(new Position(1, 3), 2);
        game.addEntity(door1);

        game.tickDirection(Direction.STATIC);

        assertTrue(mercenary.getX() == 0 && mercenary.getY() == 5);

        game.tickDirection(Direction.STATIC);

        assertTrue(mercenary.getX() == 0 && mercenary.getY() == 4);

        game.tickDirection(Direction.LEFT);

        assertTrue(mercenary.getX() == 0 && mercenary.getY() == 3);
    }

    @Test
    @DisplayName("Test simple mercenary movement through open door")
    public void testMercenaryMovementWithOpenDoor() {
        DungeonManiaController dmc = new DungeonManiaController();

        DungeonResponse res = dmc.newGame("d_mercenaryTest_basicMovement", "c_mercenaryTest_basicMovement");

        Game game = dmc.getGame(res.getDungeonId());

        Mercenary mercenary = new Mercenary(new Position(1, 10), 5, 1);
        game.addEntity(mercenary);

        Key key = new Key(new Position(1, 3), 1);
        game.addEntity(key);

        Door door = new Door(new Position(1, 4), 1);
        game.addEntity(door);

        door.openDoor();

        game.tickDirection(Direction.DOWN);

        assertTrue(mercenary.getX() == 1 && mercenary.getY() == 9);

        game.tickDirection(Direction.DOWN);

        assertTrue(mercenary.getX() == 1 && mercenary.getY() == 8);

        game.tickDirection(Direction.DOWN);

        assertTrue(mercenary.getX() == 1 && mercenary.getY() == 7);

        game.tickDirection(Direction.DOWN);

        assertTrue(mercenary.getX() == 1 && mercenary.getY() == 6);

        game.tickDirection(Direction.UP);

        assertTrue(mercenary.getX() == 1 && mercenary.getY() == 5);

        game.tickDirection(Direction.UP);

        assertTrue(mercenary.getX() == 1 && mercenary.getY() == 4);

        game.tickDirection(Direction.UP);

        assertTrue(mercenary.getX() == 1 && mercenary.getY() == 3);
    }

    @Test
    @DisplayName("Test simple mercenary movement with portal in the way of path")
    public void testMercenaryMovementWithPortalInPath() {
        DungeonManiaController dmc = new DungeonManiaController();

        DungeonResponse res = dmc.newGame("d_mercenaryTest_basicMovement", "c_mercenaryTest_basicMovement");

        Game game = dmc.getGame(res.getDungeonId());

        Mercenary mercenary = new Mercenary(new Position(1, 5), 5, 1);
        game.addEntity(mercenary);

        Portal portal = new Portal(new Position(1, 4), "RED");
        game.addEntity(portal);

        Portal portal2 = new Portal(new Position(3, 2), "RED");
        game.addEntity(portal2);

        Wall wall = new Wall(new Position(2, 4));
        game.addEntity(wall);

        game.tickDirection(Direction.STATIC);

        assertTrue(mercenary.getX() == 0 && mercenary.getY() == 5);

        game.tickDirection(Direction.STATIC);

        assertTrue(mercenary.getX() == 0 && mercenary.getY() == 4);
    }

    @Test
    @DisplayName("Test bribe Mercenary without treasure")
    public void testBribeMercenaryWithoutTreasure() {
        DungeonManiaController dmc = new DungeonManiaController();

        DungeonResponse res = dmc.newGame("d_mercenaryTest_basicMovement", "c_mercenaryTest_basicMovement");

        Game game = dmc.getGame(res.getDungeonId());

        Mercenary mercenary = new Mercenary(new Position(1, 2), 5, 1);
        game.addEntity(mercenary);

        String mercenaryId = mercenary.getId();

        assertThrows(InvalidActionException.class, () -> dmc.interact(mercenaryId));
    }

    @Test
    @DisplayName("Test bribe Mercenary not within bribing radius")
    public void testBribeMercenaryNotWithinRadius() {
        DungeonManiaController dmc = new DungeonManiaController();

        DungeonResponse res = dmc.newGame("d_mercenaryTest_basicMovement", "c_mercenaryTest_basicMovement");

        Game game = dmc.getGame(res.getDungeonId());

        Mercenary mercenary = new Mercenary(new Position(1, 5), 5, 1);
        game.addEntity(mercenary);

        String mercenaryId = mercenary.getId();

        assertThrows(InvalidActionException.class, () -> dmc.interact(mercenaryId));
    }

    @Test
    @DisplayName("Test bribe Mercenary movement")
    public void testBribeMercenaryMovement() {
        DungeonManiaController dmc = new DungeonManiaController();

        DungeonResponse res = dmc.newGame("d_mercenaryTest_basicMovement2", "c_mercenaryTest_basicMovement");

        Game game = dmc.getGame(res.getDungeonId());

        Mercenary mercenary = new Mercenary(new Position(1, 6), 5, 1);
        game.addEntity(mercenary);
        
        Treasure treasure = new Treasure(new Position(1, 2));
        game.addEntity(treasure);

        Treasure treasure1 = new Treasure(new Position(1, 3));
        game.addEntity(treasure1);

        game.tickDirection(Direction.DOWN);

        assertTrue(mercenary.getX() == 1 && mercenary.getY() == 5);

        game.tickDirection(Direction.DOWN);

        assertTrue(mercenary.getX() == 1 && mercenary.getY() == 4);

        String mercenaryId = mercenary.getId();

        assertDoesNotThrow(() -> dmc.interact(mercenaryId));

        game.tickDirection(Direction.RIGHT);

        assertTrue(mercenary.getX() == 1 && mercenary.getY() == 3);

        game.tickDirection(Direction.RIGHT);

        assertTrue(mercenary.getX() == 2 && mercenary.getY() == 3);
    }

    @Test
    @DisplayName("Test bribe Mercenary movement with portal")
    public void testBribeMercenaryMovementWithPortal() {
        DungeonManiaController dmc = new DungeonManiaController();

        DungeonResponse res = dmc.newGame("d_mercenaryTest_basicMovement2", "c_mercenaryTest_basicMovement");

        Game game = dmc.getGame(res.getDungeonId());

        Mercenary mercenary = new Mercenary(new Position(1, 6), 5, 1);
        game.addEntity(mercenary);
        
        Treasure treasure = new Treasure(new Position(1, 2));
        game.addEntity(treasure);

        Treasure treasure1 = new Treasure(new Position(1, 3));
        game.addEntity(treasure1);

        game.tickDirection(Direction.DOWN);

        assertTrue(mercenary.getX() == 1 && mercenary.getY() == 5);

        game.tickDirection(Direction.DOWN);

        assertTrue(mercenary.getX() == 1 && mercenary.getY() == 4);

        String mercenaryId = mercenary.getId();

        assertDoesNotThrow(() -> dmc.interact(mercenaryId));

        Portal portal = new Portal(new Position(2, 3), "RED");
        game.addEntity(portal);

        Portal portal1 = new Portal(new Position(3, 1), "RED");
        game.addEntity(portal1);

        game.tickDirection(Direction.RIGHT);

        assertTrue(mercenary.getX() == 1 && mercenary.getY() == 3);

        game.tickDirection(Direction.DOWN);

        List<Position> positions = new ArrayList<Position>(
            Arrays.asList(
                new Position(3, 2),
                new Position(2, 1),
                new Position(4, 1),
                new Position(3, 0)
            )
        );

        assertTrue(positions.contains(mercenary.getPosition()));

    }
    
    @Test
    @DisplayName("Test bribe Mercenary does not attack player")
    public void testBribedMercenaryDoesNotAttackPlayer() {
        DungeonManiaController dmc = new DungeonManiaController();

        DungeonResponse res = dmc.newGame("d_mercenaryTest_basicMovement2", "c_mercenaryTest_basicMovement");

        Game game = dmc.getGame(res.getDungeonId());

        Mercenary mercenary = new Mercenary(new Position(1, 6), 5, 1);
        game.addEntity(mercenary);
        
        Treasure treasure = new Treasure(new Position(1, 2));
        game.addEntity(treasure);

        Treasure treasure1 = new Treasure(new Position(1, 3));
        game.addEntity(treasure1);

        game.tickDirection(Direction.DOWN);

        assertTrue(mercenary.getX() == 1 && mercenary.getY() == 5);

        game.tickDirection(Direction.DOWN);

        assertTrue(mercenary.getX() == 1 && mercenary.getY() == 4);

        String mercenaryId = mercenary.getId();

        assertDoesNotThrow(() -> dmc.interact(mercenaryId));

        Player player = game.getPlayer();

        double playerHealth = player.getHealth();

        game.tickDirection(Direction.STATIC);
        assertEquals(playerHealth, player.getHealth());
        game.tickDirection(Direction.STATIC);
        assertEquals(playerHealth, player.getHealth());
    }

    @Test
    @DisplayName("Test mercenary movement with invincibility potion potion")
    public void testMercenaryMovementWithInvincibilityPotion() {
        DungeonManiaController dmc = new DungeonManiaController();

        DungeonResponse res = dmc.newGame("d_mercenaryTest_basicMovement", "c_mercenaryTest_basicMovement");

        Game game = dmc.getGame(res.getDungeonId());

        Mercenary mercenary = new Mercenary(new Position(1, 5), 5, 1);
        game.addEntity(mercenary);

        InvincibilityPotion invincibilityPotion = new InvincibilityPotion(new Position(1, 1), 5);
        game.addEntity(invincibilityPotion);

        game.tickDirection(Direction.DOWN);

        Player player = game.getPlayer();

        assertTrue(player.getX() == 1 && player.getY() == 1);
        assertTrue(mercenary.getX() == 1 && mercenary.getY() == 4);

        Position positionBetween = Position.calculatePositionBetween(player.getPosition(), mercenary.getPosition());
        double distance = Math.sqrt(positionBetween.getX() * positionBetween.getX() + positionBetween.getY() * positionBetween.getY());

        assertDoesNotThrow(() -> game.tickItem(invincibilityPotion.getId()));

        Position positionBetweenNew = Position.calculatePositionBetween(player.getPosition(), mercenary.getPosition());
        double distanceNew = Math.sqrt(positionBetweenNew.getX() * positionBetweenNew.getX() + positionBetweenNew.getY() * positionBetweenNew.getY());

        assertTrue(distanceNew > distance); 

    }
}   
