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
import dungeonmania.movingentity.Assassin;
import dungeonmania.player.Player;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.staticentity.*;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

import java.lang.Math;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AssassinTest {

    @Test
    @DisplayName("Test create assassin")
    public void testCreateAssassin() {
        DungeonManiaController dmc = new DungeonManiaController();

        DungeonResponse res = dmc.newGame("d_assassinTest_basicMovement0", "c_assassinTest_basicMovement0");

        Game game = dmc.getGame(res.getDungeonId());

        assertEquals(1, countEntityOfType(game.getDungeonResponse(), "assassin"));
    }
    
    @Test
    @DisplayName("Test simple assassin movement")
    public void testAssassinBasicMovementTowardsPlayer() {
        DungeonManiaController dmc = new DungeonManiaController();

        DungeonResponse res = dmc.newGame("d_assassinTest_basicMovement", "c_assassinTest_basicMovement");

        Game game = dmc.getGame(res.getDungeonId());

        Assassin assassin = new Assassin(new Position(1, 5), 5, 1);
        game.addEntity(assassin);

        game.tickDirection(Direction.STATIC);

        assertTrue(assassin.getX() == 1 && assassin.getY() == 4);

        game.tickDirection(Direction.STATIC);

        assertTrue(assassin.getX() == 1 && assassin.getY() == 3);

        game.tickDirection(Direction.STATIC);

        assertTrue(assassin.getX() == 1 && assassin.getY() == 2);

    }

    @Test
    @DisplayName("Test simple assassin movement around wall")
    public void testAssassinBasicMovementAroundWall() {
        DungeonManiaController dmc = new DungeonManiaController();

        DungeonResponse res = dmc.newGame("d_assassinTest_basicMovement", "c_assassinTest_basicMovement");

        Game game = dmc.getGame(res.getDungeonId());

        Assassin assassin = new Assassin(new Position(1, 5), 5, 1);
        game.addEntity(assassin);

        for (int i = 1; i < 5; ++i) {
            Wall wall = new Wall(new Position(i, 4));
            game.addEntity(wall);
        }

        game.tickDirection(Direction.STATIC);

        assertTrue(assassin.getX() == 0 && assassin.getY() == 5);

        game.tickDirection(Direction.STATIC);

        assertTrue(assassin.getX() == 0 && assassin.getY() == 4);

        game.tickDirection(Direction.STATIC);

        assertTrue(assassin.getX() == 0 && assassin.getY() == 3);

        game.tickDirection(Direction.LEFT);

        assertTrue(assassin.getX() == 0 && assassin.getY() == 2);
    }

    @Test
    @DisplayName("Test simple assassin movement around boulder")
    public void testAssassinMovementAroundBoulder() {
        DungeonManiaController dmc = new DungeonManiaController();

        DungeonResponse res = dmc.newGame("d_assassinTest_basicMovement", "c_assassinTest_basicMovement");

        Game game = dmc.getGame(res.getDungeonId());

        Assassin assassin = new Assassin(new Position(1, 5), 5, 1);
        game.addEntity(assassin);

        for (int i = 1; i < 5; ++i) {
            Boulder boulder = new Boulder(new Position(i, 4));
            game.addEntity(boulder);
        }

        game.tickDirection(Direction.STATIC);

        assertTrue(assassin.getX() == 0 && assassin.getY() == 5);

        game.tickDirection(Direction.STATIC);

        assertTrue(assassin.getX() == 0 && assassin.getY() == 4);

        game.tickDirection(Direction.STATIC);

        assertTrue(assassin.getX() == 0 && assassin.getY() == 3);

        game.tickDirection(Direction.LEFT);

        assertTrue(assassin.getX() == 0 && assassin.getY() == 2);
    }

    @Test
    @DisplayName("Test simple assassin movement through exit")
    public void testAssassinMovementWithExit() {
        DungeonManiaController dmc = new DungeonManiaController();

        DungeonResponse res = dmc.newGame("d_assassinTest_basicMovement", "c_assassinTest_basicMovement");

        Game game = dmc.getGame(res.getDungeonId());

        Assassin assassin = new Assassin(new Position(1, 5), 5, 1);
        game.addEntity(assassin);

        Exit exit = new Exit(new Position(1, 4));
        game.addEntity(exit);

        game.tickDirection(Direction.STATIC);

        assertTrue(assassin.getX() == 1 && assassin.getY() == 4);

        game.tickDirection(Direction.STATIC);

        assertTrue(assassin.getX() == 1 && assassin.getY() == 3);
    }

    @Test
    @DisplayName("Test simple assassin movement through floor switch")
    public void testAssassinMovementThroughFloorSwitch() {
        DungeonManiaController dmc = new DungeonManiaController();

        DungeonResponse res = dmc.newGame("d_assassinTest_basicMovement", "c_assassinTest_basicMovement");

        Game game = dmc.getGame(res.getDungeonId());

        Assassin assassin = new Assassin(new Position(1, 5), 5, 1);
        game.addEntity(assassin);

        FloorSwitch floorSwitch = new FloorSwitch(new Position(1, 4));
        game.addEntity(floorSwitch);

        game.tickDirection(Direction.STATIC);

        assertTrue(assassin.getX() == 1 && assassin.getY() == 4);

        game.tickDirection(Direction.STATIC);

        assertTrue(assassin.getX() == 1 && assassin.getY() == 3);

        game.tickDirection(Direction.STATIC);

        assertTrue(assassin.getX() == 1 && assassin.getY() == 2);
    }

    @Test
    @DisplayName("Test simple assassin movement through closed door")
    public void testAssassinMovementWithClosedDoor() {
        DungeonManiaController dmc = new DungeonManiaController();

        DungeonResponse res = dmc.newGame("d_assassinTest_basicMovement", "c_assassinTest_basicMovement");

        Game game = dmc.getGame(res.getDungeonId());

        Assassin assassin = new Assassin(new Position(1, 5), 5, 1);
        game.addEntity(assassin);

        Key key = new Key(new Position(4, 1), 1);
        game.addEntity(key);

        Key key1 = new Key(new Position(4, 2), 2);
        game.addEntity(key1);

        Door door = new Door(new Position(1, 4), 1);
        game.addEntity(door);

        Door door1 = new Door(new Position(1, 3), 2);
        game.addEntity(door1);

        game.tickDirection(Direction.STATIC);

        assertTrue(assassin.getX() == 0 && assassin.getY() == 5);

        game.tickDirection(Direction.STATIC);

        assertTrue(assassin.getX() == 0 && assassin.getY() == 4);

        game.tickDirection(Direction.LEFT);

        assertTrue(assassin.getX() == 0 && assassin.getY() == 3);
    }

    @Test
    @DisplayName("Test simple assassin movement through open door")
    public void testAssassinMovementWithOpenDoor() {
        DungeonManiaController dmc = new DungeonManiaController();

        DungeonResponse res = dmc.newGame("d_assassinTest_basicMovement", "c_assassinTest_basicMovement");

        Game game = dmc.getGame(res.getDungeonId());

        Assassin assassin = new Assassin(new Position(1, 10), 5, 1);
        game.addEntity(assassin);

        Key key = new Key(new Position(1, 3), 1);
        game.addEntity(key);

        Door door = new Door(new Position(1, 4), 1);
        game.addEntity(door);

        door.openDoor();

        game.tickDirection(Direction.DOWN);

        assertTrue(assassin.getX() == 1 && assassin.getY() == 9);

        game.tickDirection(Direction.DOWN);

        assertTrue(assassin.getX() == 1 && assassin.getY() == 8);

        game.tickDirection(Direction.DOWN);

        assertTrue(assassin.getX() == 1 && assassin.getY() == 7);

        game.tickDirection(Direction.DOWN);

        assertTrue(assassin.getX() == 1 && assassin.getY() == 6);

        game.tickDirection(Direction.UP);

        assertTrue(assassin.getX() == 1 && assassin.getY() == 5);

        game.tickDirection(Direction.UP);

        assertTrue(assassin.getX() == 1 && assassin.getY() == 4);

        game.tickDirection(Direction.UP);

        assertTrue(assassin.getX() == 1 && assassin.getY() == 3);
    }

    @Test
    @DisplayName("Test simple assassin movement with portal in the way of path")
    public void testAssassinMovementWithPortalInPath() {
        DungeonManiaController dmc = new DungeonManiaController();

        DungeonResponse res = dmc.newGame("d_assassinTest_basicMovement", "c_assassinTest_basicMovement");

        Game game = dmc.getGame(res.getDungeonId());

        Assassin assassin = new Assassin(new Position(1, 5), 5, 1);
        game.addEntity(assassin);

        Portal portal = new Portal(new Position(1, 4), "RED");
        game.addEntity(portal);

        Portal portal2 = new Portal(new Position(3, 2), "RED");
        game.addEntity(portal2);

        Wall wall = new Wall(new Position(2, 4));
        game.addEntity(wall);

        game.tickDirection(Direction.STATIC);

        assertTrue(assassin.getX() == 0 && assassin.getY() == 5);

        game.tickDirection(Direction.STATIC);

        assertTrue(assassin.getX() == 0 && assassin.getY() == 4);
    }

    @Test
    @DisplayName("Test bribe Assassin without treasure")
    public void testBribeAssassinWithoutTreasure() {
        DungeonManiaController dmc = new DungeonManiaController();

        DungeonResponse res = dmc.newGame("d_assassinTest_basicMovement", "c_assassinTest_basicMovement");

        Game game = dmc.getGame(res.getDungeonId());

        Assassin assassin = new Assassin(new Position(1, 2), 5, 1);
        game.addEntity(assassin);

        String assassinId = assassin.getId();

        assertThrows(InvalidActionException.class, () -> dmc.interact(assassinId));
    }

    @Test
    @DisplayName("Test bribe Assassin not within bribing radius")
    public void testBribeAssassinNotWithinRadius() {
        DungeonManiaController dmc = new DungeonManiaController();

        DungeonResponse res = dmc.newGame("d_assassinTest_basicMovement", "c_assassinTest_basicMovement");

        Game game = dmc.getGame(res.getDungeonId());

        Assassin assassin = new Assassin(new Position(1, 5), 5, 1);
        game.addEntity(assassin);

        String assassinId = assassin.getId();

        assertThrows(InvalidActionException.class, () -> dmc.interact(assassinId));
    }

    @Test
    @DisplayName("Test bribe Assassin movement")
    public void testBribeAssassinMovement() {
        DungeonManiaController dmc = new DungeonManiaController();

        DungeonResponse res = dmc.newGame("d_assassinTest_basicMovement2", "c_assassinTest_basicMovement");

        Game game = dmc.getGame(res.getDungeonId());

        Assassin assassin = new Assassin(new Position(1, 6), 5, 1);
        game.addEntity(assassin);
        
        Treasure treasure = new Treasure(new Position(1, 2));
        game.addEntity(treasure);

        Treasure treasure1 = new Treasure(new Position(1, 3));
        game.addEntity(treasure1);

        game.tickDirection(Direction.DOWN);

        assertTrue(assassin.getX() == 1 && assassin.getY() == 5);

        game.tickDirection(Direction.DOWN);

        assertTrue(assassin.getX() == 1 && assassin.getY() == 4);

        String assassinId = assassin.getId();

        assertDoesNotThrow(() -> dmc.interact(assassinId));

        game.tickDirection(Direction.RIGHT);

        assertTrue(assassin.getX() == 1 && assassin.getY() == 3);

        game.tickDirection(Direction.RIGHT);

        assertTrue(assassin.getX() == 2 && assassin.getY() == 3);
    }

    @Test
    @DisplayName("Test bribe Assassin movement with portal")
    public void testBribeAssassinMovementWithPortal() {
        DungeonManiaController dmc = new DungeonManiaController();

        DungeonResponse res = dmc.newGame("d_assassinTest_basicMovement2", "c_assassinTest_basicMovement");

        Game game = dmc.getGame(res.getDungeonId());

        Assassin assassin = new Assassin(new Position(1, 6), 5, 1);
        game.addEntity(assassin);
        
        Treasure treasure = new Treasure(new Position(1, 2));
        game.addEntity(treasure);

        Treasure treasure1 = new Treasure(new Position(1, 3));
        game.addEntity(treasure1);

        game.tickDirection(Direction.DOWN);

        assertTrue(assassin.getX() == 1 && assassin.getY() == 5);

        game.tickDirection(Direction.DOWN);

        assertTrue(assassin.getX() == 1 && assassin.getY() == 4);

        String assassinId = assassin.getId();

        assertDoesNotThrow(() -> dmc.interact(assassinId));

        Portal portal = new Portal(new Position(2, 3), "RED");
        game.addEntity(portal);

        Portal portal1 = new Portal(new Position(3, 1), "RED");
        game.addEntity(portal1);

        game.tickDirection(Direction.RIGHT);

        assertTrue(assassin.getX() == 1 && assassin.getY() == 3);

        game.tickDirection(Direction.DOWN);

        List<Position> positions = new ArrayList<Position>(
            Arrays.asList(
                new Position(3, 2),
                new Position(2, 1),
                new Position(4, 1),
                new Position(3, 0)
            )
        );

        assertTrue(positions.contains(assassin.getPosition()));

    }
    
    @Test
    @DisplayName("Test bribe Assassin does not attack player")
    public void testBribedAssassinDoesNotAttackPlayer() {
        DungeonManiaController dmc = new DungeonManiaController();

        DungeonResponse res = dmc.newGame("d_assassinTest_basicMovement2", "c_assassinTest_basicMovement");

        Game game = dmc.getGame(res.getDungeonId());

        Assassin assassin = new Assassin(new Position(1, 6), 5, 1);
        game.addEntity(assassin);
        
        Treasure treasure = new Treasure(new Position(1, 2));
        game.addEntity(treasure);

        Treasure treasure1 = new Treasure(new Position(1, 3));
        game.addEntity(treasure1);

        game.tickDirection(Direction.DOWN);

        assertTrue(assassin.getX() == 1 && assassin.getY() == 5);

        game.tickDirection(Direction.DOWN);

        assertTrue(assassin.getX() == 1 && assassin.getY() == 4);

        String assassinId = assassin.getId();

        assertDoesNotThrow(() -> dmc.interact(assassinId));

        Player player = game.getPlayer();

        double playerHealth = player.getHealth();

        game.tickDirection(Direction.STATIC);
        assertEquals(playerHealth, player.getHealth());
        game.tickDirection(Direction.STATIC);
        assertEquals(playerHealth, player.getHealth());
    }

    @Test
    @DisplayName("Test assassin movement with invincibility potion potion")
    public void testAssassinMovementWithInvincibilityPotion() {
        DungeonManiaController dmc = new DungeonManiaController();

        DungeonResponse res = dmc.newGame("d_assassinTest_basicMovement", "c_assassinTest_basicMovement");

        Game game = dmc.getGame(res.getDungeonId());

        Assassin assassin = new Assassin(new Position(1, 5), 5, 1);
        game.addEntity(assassin);

        InvincibilityPotion invincibilityPotion = new InvincibilityPotion(new Position(1, 1), 5);
        game.addEntity(invincibilityPotion);

        game.tickDirection(Direction.DOWN);

        Player player = game.getPlayer();

        assertTrue(player.getX() == 1 && player.getY() == 1);
        assertTrue(assassin.getX() == 1 && assassin.getY() == 4);

        Position positionBetween = Position.calculatePositionBetween(player.getPosition(), assassin.getPosition());
        double distance = Math.sqrt(positionBetween.getX() * positionBetween.getX() + positionBetween.getY() * positionBetween.getY());

        assertDoesNotThrow(() -> game.tickItem(invincibilityPotion.getId()));

        Position positionBetweenNew = Position.calculatePositionBetween(player.getPosition(), assassin.getPosition());
        double distanceNew = Math.sqrt(positionBetweenNew.getX() * positionBetweenNew.getX() + positionBetweenNew.getY() * positionBetweenNew.getY());

        assertTrue(distanceNew > distance); 

    }
}   
