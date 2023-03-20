package dungeonmania.movingentity;

import dungeonmania.movingentity.MovementStrategy.RandomMovement;
import dungeonmania.movingentity.MovementStrategy.RunAway;
import dungeonmania.player.Player;
import dungeonmania.player.PotionStates.InvisibilityState;
import dungeonmania.staticentity.Boulder;
import dungeonmania.staticentity.Door;
import dungeonmania.staticentity.Wall;
import dungeonmania.Entity;
import dungeonmania.Game;
import dungeonmania.movingentity.MovementStrategy.MovementStrategy;
import dungeonmania.util.Position;

/**
 * ZombieToast extends MovingEntity.
 **/

public class ZombieToast extends MovingEntity {

    MovementStrategy strategy = new RandomMovement(this);
    
    /**
     * Constructor for the ZombieToast class.
     *
     * @param position The position of the entity.
     * @param health The health of the entity.
     * @param attackDamage The attack damage of the entity.
     **/
    public ZombieToast(Position position, double health, double attackDamage) {
        super(position, "zombietoast", false, health, attackDamage);
    }

    /**
     * Checks if the ZombieToast can move through a certain Entity.
     * @param entity
     * @return boolean
     **/
    @Override
    public boolean canTraverse(Entity entity) {
        if (entity instanceof Wall || entity instanceof Boulder) {
            return false;
        } else if (entity instanceof Door) {
            Door door = (Door) entity;
            return door.isOpen();
        } else {
            return true;
        }
    }

    /**
     * Ticks the zombieToast, and thus moves the zombieToast.
     * @param game
     **/
    @Override
    public void tick(Game game) {
        strategy.move(game);
    }

    /**
     * Updates the movement strategy of the zombieToast based on the potion state of the player.
     * @param player
     **/
    @Override
    public void update(Game game, Player player) {
        if (player.getPotionState() instanceof InvisibilityState) {
            strategy = new RunAway(this);
        } else {
            strategy = new RandomMovement(this);
        }
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