package dungeonmania.staticTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import dungeonmania.staticentity.Wall;
import dungeonmania.staticentity.ZombieToastSpawner;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;
import dungeonmania.DungeonManiaController;
import dungeonmania.Entity;
import dungeonmania.Game;
import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.movingentity.ZombieToast;
import dungeonmania.player.Player;
import dungeonmania.response.models.DungeonResponse;

public class ZombieToastSpawnerTest {
    @Test
    @DisplayName("Test the zombie toast spawner entity is correctly created")
    public void createZombieToastSpawnerTest() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("zombies", "simple");
        
        Game game = dmc.getGame(res.getDungeonId());

        ZombieToastSpawner zombieToastSpawner = new ZombieToastSpawner(new Position(5, 1), game.getConfig().getZombieSpawnRate());
        game.addEntity(zombieToastSpawner);

        assertTrue(new Position(5, 1).equals(game.getEntity(zombieToastSpawner.getId()).getPosition()));
    }

    @Test
    @DisplayName("Test zombie toast spawner can spawn")
    public void simpleZombieToastSpawnerTest() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("zombies", "simple");
        
        Game game = dmc.getGame(res.getDungeonId());

        ZombieToastSpawner zts = game.getEntities()
            .stream()
            .filter(e -> (e instanceof ZombieToastSpawner))
            .map(e -> (ZombieToastSpawner) e)
            .findFirst().orElse(null);

        for (int i=0; i < 10; i++) zts.tick(game);

        // check there's only 1 zombie in the map after 10 tick
        int zombieCount = 0;
        for (Entity e : game.getEntities()) {
            if (e instanceof ZombieToast) {
                zombieCount++;
            }
        }
        assertEquals(10, game.getConfig().getZombieSpawnRate());
        assertTrue(zombieCount == 1);
    }

    @Test
    @DisplayName("Test zombie toast spawner cannot spawn if all the cardinally adjacent cells to the spawner are walls")
    public void zombieToastSpawnerSurroundedByWallTest() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("zombies", "simple");
        
        Game game = dmc.getGame(res.getDungeonId());
        Player p = game.getPlayer();
        p.move(game, Direction.UP);

        ZombieToastSpawner zts = game.getEntities()
        .stream()
        .filter(e -> (e instanceof ZombieToastSpawner))
        .map(e -> (ZombieToastSpawner) e)
        .findFirst().orElse(null);

        // add walls cardinally adjacent to the spawner
        game.addEntity(new Wall(new Position(1, 1)));
        game.addEntity(new Wall(new Position(2, 2)));
        game.addEntity(new Wall(new Position(3, 1)));
        game.addEntity(new Wall(new Position(2, 0)));

        for (int i=0; i < 10; i++) zts.tick(game);

        // check there's no zombie in the map after 10 tick
        int zombieCount = 0;
        for (Entity e : game.getEntities()) {
            if (e instanceof ZombieToast) {
                zombieCount++;
            }
        }

        assertTrue(zombieCount == 0);
    }

    @Test
    @DisplayName("Test player can destroy zombie toast spawner if they have a weapon and are cardinally adjacent to the spawner")
    public void playerDestroyZombieToastSpawnerTest() throws IllegalArgumentException, InvalidActionException {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("zombies", "simple");
        
        Game game = dmc.getGame(res.getDungeonId());
        Player p = game.getPlayer();

        ZombieToastSpawner zts = game.getEntities()
        .stream()
        .filter(e -> (e instanceof ZombieToastSpawner))
        .map(e -> (ZombieToastSpawner) e)
        .findFirst().orElse(null);

        // player move down to pick up the sword
        p.move(game, Direction.DOWN);

        // player move up to be cardinally adjacent to the spawner
        p.move(game, Direction.UP);

        // assertEquals(p.getPosition(), zts.getPosition());
        // assertEquals(new Position(0, 0), zts.getPosition());
        game.interact(zts.getId());
        assertTrue(game.getEntity(zts.getId()) == null);
    } 

    @Test
    @DisplayName("Test player cannot destroy zombie toast spawner if they don't have a weapon and are cardinally adjacent to the spawner")
    public void playerDestroyZombieToastSpawnerWithoutWeaponTest() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("zombies", "simple");
        
        Game game = dmc.getGame(res.getDungeonId());
        Player p = game.getPlayer();

        // player is created cardinally adjacent to the spawner
        p.tick();

        ZombieToastSpawner zts = game.getEntities()
        .stream()
        .filter(e -> (e instanceof ZombieToastSpawner))
        .map(e -> (ZombieToastSpawner) e)
        .findFirst().orElse(null);

        assertTrue(zts != null);
    }

    @Test
    @DisplayName("Test player cannot destroy zombie toast spawner if they have a weapon but are not cardinally adjacent to the spawner")
    public void playerDestroyZombieToastSpawnerNotAdjacentTest() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("zombies", "simple");
        
        Game game = dmc.getGame(res.getDungeonId());
        Player p = game.getPlayer();

        // player move down to pick up the sword
        p.move(game, Direction.DOWN);

        // player move down again
        p.move(game, Direction.DOWN);

        p.tick();

        ZombieToastSpawner zts = game.getEntities()
        .stream()
        .filter(e -> (e instanceof ZombieToastSpawner))
        .map(e -> (ZombieToastSpawner) e)
        .findFirst().orElse(null);

        assertTrue(zts != null);
    }
}
