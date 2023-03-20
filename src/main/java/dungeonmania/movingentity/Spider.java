package dungeonmania.movingentity;

import dungeonmania.Config;
import dungeonmania.Entity;
import dungeonmania.Game;
import dungeonmania.movingentity.MovementStrategy.CircularMovement;
import dungeonmania.movingentity.MovementStrategy.MovementStrategy;
import dungeonmania.player.Player;
import dungeonmania.staticentity.Boulder;
import dungeonmania.util.Position;

import java.util.Random;

/**
 * Spider class extends MovingEntity class.
 **/

public class Spider extends MovingEntity {

    MovementStrategy strategy = new CircularMovement(this);
    final static private int MIN_SPAWN_COORD = 0;
    final static private int MAX_SPAWN_COORD = 20;

    /**
     * Constructor for the Spider class.
     *
     * @param position The position of the entity.
     * @param health The health of the entity.
     * @param attackDamage The attack damage of the entity.
     **/
    public Spider(Position position, double health, double attackDamage) {
        super(position, "spider", false, health, attackDamage);
    }

    /**
     * Spawns a spider at a random position.
     * @param game
     **/
    public static void spawnSpider(Game game) {
        int gameTick = game.getTick();
        Config config = game.getConfig();
        if (gameTick != 0 && config.getSpiderSpawnRate() != 0 && gameTick % config.getSpiderSpawnRate() == 0) {
            Position position = randomSpiderSpawnLocation(game);
            Spider spider = new Spider(position, config.getSpiderHealth(), config.getSpiderAttack());
            game.addEntity(spider);
        }
    }

    /**
     * Randomly generates a position for the spider to spawn.
     * @param game
     * @return Position
     **/
    private static Position randomSpiderSpawnLocation(Game game) {
        int x = 0;
        int y = 0;
        Position newSpiderSpawnLocation;
        Random rand = new Random();
        while (true) {
            x = rand.nextInt(MAX_SPAWN_COORD - MIN_SPAWN_COORD + 1) + MIN_SPAWN_COORD;
            y = rand.nextInt(MAX_SPAWN_COORD - MIN_SPAWN_COORD + 1) + MIN_SPAWN_COORD;
            newSpiderSpawnLocation = new Position(x, y);

            if (!game.getEntities(newSpiderSpawnLocation).stream().anyMatch(entity -> entity instanceof Boulder)) {
                break;
            }
        }

        return newSpiderSpawnLocation;
    }

    /**
     * Checks if the spider can move through a certain Entity.
     * @param entity
     * @return boolean
     **/
    @Override
    public boolean canTraverse(Entity entity) {
        if (entity instanceof Boulder) {
            return false;
        }
        return true;
    }

    /**
     * Ticks the spider, and thus moves the spider.
     * @param game
     **/
    @Override
    public void tick(Game game) {
        strategy.move(game);
    }

    /**
     * Updates the movement strategy of the spider based on the potion state of the player.
     * Since the spider is not affected by the potion state of the player, this method is empty.
     * @param player
     **/
    @Override
    public void update(Game game, Player player) {
        return;
    }

    /**
     * Interacts with the given entity.
     * @param entity
     * @param game
     **/
    @Override
    public void interact(Game game, Entity entity) {
        ((Player) entity).setPosition(this.getPosition()); 
        if (entity instanceof Player) {
            ((Player) entity).getPotionState().battle(game, this);
        }
        
    }
}