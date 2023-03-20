package dungeonmania.movingentity;

import java.util.List;

import dungeonmania.Config;
import dungeonmania.Entity;
import dungeonmania.Game;
import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.movingentity.MovementStrategy.MovementStrategy;
import dungeonmania.movingentity.MovementStrategy.RandomMovement;
import dungeonmania.movingentity.MovementStrategy.RunAway;
import dungeonmania.movingentity.MovementStrategy.TowardsPlayer;
import dungeonmania.player.Player;
import dungeonmania.player.PotionStates.InvincibilityState;
import dungeonmania.player.PotionStates.InvisibilityState;
import dungeonmania.player.PotionStates.RegularState;
import dungeonmania.staticentity.Boulder;
import dungeonmania.staticentity.Door;
import dungeonmania.staticentity.Portal;
import dungeonmania.staticentity.Wall;
import dungeonmania.util.Position;

public abstract class BribableEntity extends MovingEntity {

    MovementStrategy strategy = new TowardsPlayer(this);
    private boolean isBribed;
    private boolean isMindControlled;
    private int mindControlledCounter = 0;

    /**
     * Constructor for the BribableEntity class.
     *
     * @param position The position of the entity.
     * @param health The health of the entity.
     * @param attackDamage The attack damage of the entity.
     **/
    public BribableEntity(Position position, String type, double health, double attackDamage) {
        super(position, type, true, health, attackDamage);
        this.isBribed = false;
        this.isMindControlled = false;
    }

    /**
     * Constructor for the BribableEntity class.
     *
     * @param position The position of the entity.
     * @param health The health of the entity.
     * @param attackDamage The attack damage of the entity.
     * @param isBribed Whether or not the mercenary is bribed.
     */
    public BribableEntity(Position position, String type, double attackDamage, double health, boolean isBribed, boolean isMindControlled, int mindControlledCounter) {
        super(position, type, true, health, attackDamage);
        this.isBribed = isBribed;
        this.isMindControlled = isMindControlled;
        this.mindControlledCounter = mindControlledCounter;
    }

    /**
     * Checks if the entity is bribed.
     * @return boolean
     */
    public Boolean isBribed() {
		return isBribed;
	}

    /**
     * Sets the entity's bribed status.
     * @param isBribed
     **/
    public void setBribed(boolean isBribed) {
        this.isBribed = isBribed;
    }

    /**
     * Checks if the entity is mind controlled.
     * @return boolean
     **/
    public Boolean isMindControlled() {
        return isMindControlled;
    }

    /**
     * Sets the entity's mind controlled status.
     * @param isMindControlled
     **/
    public void setMindControlled(boolean isMindControlled) {
        this.isMindControlled = isMindControlled;
    }

    /**
     * Gets the entity's mind controlled counter.
     * @return int
     **/
    public int getMindControlledCounter() {
        return mindControlledCounter;
    }

    /**
     * Sets the entity's mind controlled counter.
     * @param mindControlledCounter
     **/
    public void setMindControlledCounter(int mindControlledCounter) {
        this.mindControlledCounter = mindControlledCounter;
    }

    /**
     * Checks if the entity can move through a certain Entity.
     * @param entity
     * @return boolean
     **/
    @Override
    public boolean canTraverse(Entity entity) {
        if (entity instanceof Wall || entity instanceof Boulder || (entity instanceof Portal && !isBribed)) {
            return false;
        } else if (entity instanceof Door) {
            Door door = (Door) entity;
            return door.isOpen();
        } else {
            return true;
        }
    }

    /**
     * Updates the movement strategy of the mercenary based on the potion state of the player.
     * @param player
     **/
    @Override
    public void update(Game game, Player player) {
        Config config = game.getConfig();

        if (player.getPotionState() instanceof InvincibilityState && !isBribed) {
            strategy = new RunAway(this);
        }

        if (player.getPotionState() instanceof RegularState) {
            new TowardsPlayer(this);
        }
        if (player.getPotionState() instanceof InvisibilityState && this instanceof Mercenary) {
            strategy = new RandomMovement(this);
        }

        List<Position> reconRadiusPositions = game.getAdjacentPositions(getPosition(), config.getAssassinReconRadius());
        if (player.getPotionState() instanceof InvisibilityState && !reconRadiusPositions.contains(player.getPosition())) {
            strategy = new RandomMovement(this);
        }
    }

    /**
     * Ticks the entity, and thus moves the entity.
     * Ticks the entities mind controlled counter, if the entity is mind controlled.
     * @param game
     **/
    @Override
    public void tick(Game game) {
        strategy.move(game);

        Config config = game.getConfig();
        if (isMindControlled) {
            if (mindControlledCounter == config.getMindControlDuration()) {
                isMindControlled = false;
                mindControlledCounter = 0;
                setHostile(true);
                game.getPlayer().removeAllies(this);
            }
            mindControlledCounter++;
        }
    }

    /**
     * Mind controls the entity
     * @param game
     * @param player
     * @throws InvalidActionException
     **/
    public boolean mindControl(Game game, Player player) {
        if (!player.hasItemOfType("sceptre")) {
            return false; 
        }
        isMindControlled = true;
        player.addAllies(this);

        return true;
    }

    /** 
     * Bribes the entity
     * @param game
     * @param player
     **/
    public abstract void bribe(Game game, Player player) throws InvalidActionException;

    /**
     * Interacts with the entity
     * @param game
     * @param entity
     **/
    public abstract void interact(Game game, Entity entity);

}
