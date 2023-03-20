package dungeonmania.MovingEntities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import dungeonmania.DungeonManiaController;
import dungeonmania.Game;
import dungeonmania.item.Key;
import dungeonmania.movingentity.ZombieToast;
import dungeonmania.player.Player;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.staticentity.Boulder;
import dungeonmania.staticentity.Door;
import dungeonmania.staticentity.Exit;
import dungeonmania.staticentity.FloorSwitch;
import dungeonmania.staticentity.Portal;
import dungeonmania.staticentity.Wall;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class ZombieToastTest {
    @Test
    @DisplayName("Test basic movement of zombie with wall")
    public void testBasicZombieMovementWithWall() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_zombieTest_basicMovement", "c_zombieTest_basicMovement");
        Game game = dmc.getGame(res.getDungeonId());

        Position zombiePosition = new Position(3, 4);
        ZombieToast zombie = new ZombieToast(zombiePosition, 5, 1);
        game.addEntity(zombie);

        Wall wall = new Wall(new Position(3, 3));
        game.addEntity(wall);

        Wall wall2 = new Wall(new Position(4, 4));
        game.addEntity(wall2);

        Wall wall3 = new Wall(new Position(3, 5));
        game.addEntity(wall3);

        game.tickDirection(Direction.STATIC);

        assertTrue(zombie.getPosition().equals(new Position(2, 4)) || zombie.getPosition().equals(zombiePosition));
    }

    @Test
    @DisplayName("Test basic movement of zombie with boulders")
    public void testBasicZombieMovementWithBoulder() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_zombieTest_basicMovement", "c_zombieTest_basicMovement");
        Game game = dmc.getGame(res.getDungeonId());    
        
        Player player = new Player(new Position(1, 0), 20, 5);
        game.addEntity(player);

        Position zombiePosition = new Position(3, 4);
        ZombieToast zombie = new ZombieToast(zombiePosition, 5, 1);
        game.addEntity(zombie);

        Boulder boulder = new Boulder(new Position(3, 3));
        game.addEntity(boulder);

        Boulder boulder2 = new Boulder(new Position(4, 4));
        game.addEntity(boulder2);

        Boulder boulder3 = new Boulder(new Position(3, 5));
        game.addEntity(boulder3);

        game.tickDirection(Direction.STATIC);

        System.out.println("Zombie position: " + zombie.getPosition());

        assertTrue(zombie.getPosition().equals(new Position(2, 4)) || zombie.getPosition().equals(zombiePosition));
    }

    @Test
    @DisplayName("Test basic movement of zombie with exit")
    public void testBasicZombieMovementWithExit() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_zombieTest_basicMovement", "c_zombieTest_basicMovement");
        Game game = dmc.getGame(res.getDungeonId());    
        
        Player player = new Player(new Position(1, 0), 20, 5);
        game.addEntity(player);

        Position zombiePosition = new Position(3, 4);
        ZombieToast zombie = new ZombieToast(zombiePosition, 5, 1);
        game.addEntity(zombie);

        Wall wall = new Wall(new Position(3, 3));
        game.addEntity(wall);

        Wall wall2 = new Wall(new Position(4, 4));
        game.addEntity(wall2);

        Wall wall3 = new Wall(new Position(3, 5));
        game.addEntity(wall3);

        Exit exit = new Exit(new Position(2, 4));
        game.addEntity(exit);

        game.tickDirection(Direction.STATIC);

        assertTrue(zombie.getPosition().equals(new Position(2, 4)) || zombie.getPosition().equals(zombiePosition));
    }

    @Test
    @DisplayName("Test basic movement of zombie with floor switch")
    public void testBasicZombieMovementWithFloorSwitch() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_zombieTest_basicMovement", "c_zombieTest_basicMovement");
        Game game = dmc.getGame(res.getDungeonId());    
        
        Player player = new Player(new Position(1, 0), 20, 5);
        game.addEntity(player);

        Position zombiePosition = new Position(3, 4);
        ZombieToast zombie = new ZombieToast(zombiePosition, 5, 1);
        game.addEntity(zombie);

        Wall wall = new Wall(new Position(3, 3));
        game.addEntity(wall);

        Wall wall2 = new Wall(new Position(4, 4));
        game.addEntity(wall2);

        Wall wall3 = new Wall(new Position(3, 5));
        game.addEntity(wall3);

        FloorSwitch floorSwitch = new FloorSwitch(new Position(2, 4));
        game.addEntity(floorSwitch);

        game.tickDirection(Direction.STATIC);

        assertTrue(zombie.getPosition().equals(new Position(2, 4)) || zombie.getPosition().equals(zombiePosition));
    }

    @Test
    @DisplayName("Test basic movement of zombie with Portal")
    public void testBasicZombieMovementWithPortal() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_zombieTest_basicMovement", "c_zombieTest_basicMovement");
        Game game = dmc.getGame(res.getDungeonId());    
        
        Player player = new Player(new Position(1, 0), 20, 5);
        game.addEntity(player);

        Position zombiePosition = new Position(3, 4);
        ZombieToast zombie = new ZombieToast(zombiePosition, 5, 1);
        game.addEntity(zombie);

        Wall wall = new Wall(new Position(3, 3));
        game.addEntity(wall);

        Wall wall2 = new Wall(new Position(4, 4));
        game.addEntity(wall2);

        Wall wall3 = new Wall(new Position(3, 5));
        game.addEntity(wall3);

        Portal portal = new Portal(new Position(2, 4), "RED");
        game.addEntity(portal);

        Portal portal2 = new Portal(new Position(1, 2), "RED");
        game.addEntity(portal2);

        game.tickDirection(Direction.STATIC);

        assertTrue(zombie.getPosition().equals(new Position(2, 4)) || zombie.getPosition().equals(zombiePosition));
    }

    @DisplayName("Test basic movement of zombie with closed door")
    public void testBasicZombieMovementWithClosedDoor() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_zombieTest_basicMovement", "c_zombieTest_basicMovement");
        Game game = dmc.getGame(res.getDungeonId());    
        
        Player player = new Player(new Position(1, 0), 20, 5);
        game.addEntity(player);

        Position zombiePosition = new Position(3, 4);
        ZombieToast zombie = new ZombieToast(zombiePosition, 5, 1);
        game.addEntity(zombie);

        Key key = new Key(new Position(0, 2), 1);
        game.addEntity(key);

        Key key1 = new Key(new Position(0, 3), 2);
        game.addEntity(key1);

        Key key2 = new Key(new Position(0, 4), 3);
        game.addEntity(key2);

        Door door = new Door(new Position(3, 3), 1);
        game.addEntity(door);

        Door door1 = new Door(new Position(4, 4), 2);
        game.addEntity(door1);

        Door door2 = new Door(new Position(3, 5), 3);
        game.addEntity(door2);

        game.tickDirection(Direction.STATIC);

        assertTrue(zombie.getPosition().equals(new Position(2, 4)) || zombie.getPosition().equals(zombiePosition));
    }

    @Test
    @DisplayName("Test basic movement of zombie with open door")
    public void testBasicZombieMovementWithOpenDoor() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_zombieTest_basicMovement", "c_zombieTest_basicMovement");
        Game game = dmc.getGame(res.getDungeonId());    
        
        Player player = new Player(new Position(1, 0), 20, 5);
        game.addEntity(player);

        Position zombiePosition = new Position(3, 4);
        ZombieToast zombie = new ZombieToast(zombiePosition, 5, 1);
        game.addEntity(zombie);

        Wall wall = new Wall(new Position(3, 3));
        game.addEntity(wall);

        Wall wall2 = new Wall(new Position(4, 4));
        game.addEntity(wall2);

        Wall wall3 = new Wall(new Position(3, 5));
        game.addEntity(wall3);

        Key key = new Key(new Position(0, 2), 1);
        Door door = new Door(new Position(2, 4), 1);
        game.addEntity(key);
        game.addEntity(door);

        door.openDoor();

        game.tickDirection(Direction.STATIC);

        assertTrue(zombie.getPosition().equals(new Position(2, 4)) || zombie.getPosition().equals(zombiePosition));
    }

    @Test
    @DisplayName("Test zombie cannot move boulder")
    public void testZombieCannotMoveBoulder() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_zombieTest_basicMovement", "c_zombieTest_basicMovement");
        Game game = dmc.getGame(res.getDungeonId());    
        
        Player player = new Player(new Position(1, 0), 20, 5);
        game.addEntity(player);

        Position zombiePosition = new Position(3, 4);
        ZombieToast zombie = new ZombieToast(zombiePosition, 5, 1);
        game.addEntity(zombie);

        Wall wall = new Wall(new Position(3, 3));
        game.addEntity(wall);

        Wall wall2 = new Wall(new Position(4, 4));
        game.addEntity(wall2);

        Wall wall3 = new Wall(new Position(3, 5));
        game.addEntity(wall3);

        Boulder boulder = new Boulder(new Position(2, 4));
        game.addEntity(boulder);

        game.tickDirection(Direction.STATIC);

        assertEquals(zombiePosition, zombie.getPosition());
    }

    @Test
    @DisplayName("Test zombie trapped cannot move anywhere")
    public void testZombieTrappedCannotMove() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_zombieTest_basicMovement", "c_zombieTest_basicMovement");
        Game game = dmc.getGame(res.getDungeonId());    
        
        Player player = new Player(new Position(1, 0), 20, 5);
        game.addEntity(player);

        Position zombiePosition = new Position(3, 4);
        ZombieToast zombie = new ZombieToast(zombiePosition, 5, 1);
        game.addEntity(zombie);

        Wall wall = new Wall(new Position(3, 3));
        game.addEntity(wall);

        Wall wall2 = new Wall(new Position(4, 4));
        game.addEntity(wall2);

        Wall wall3 = new Wall(new Position(3, 5));
        game.addEntity(wall3);

        Wall wall4 = new Wall(new Position(2, 4));
        game.addEntity(wall4);

        game.tickDirection(Direction.STATIC);

        assertEquals(zombiePosition, zombie.getPosition());
    }
}
