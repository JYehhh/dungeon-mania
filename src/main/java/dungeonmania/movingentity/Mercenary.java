package dungeonmania.movingentity;

import java.util.List;

import dungeonmania.Config;
import dungeonmania.Entity;
import dungeonmania.Game;
import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.item.Item;
import dungeonmania.player.Player;
import dungeonmania.util.Position;

/**
 * Mercenary extends MovingEntity.
 **/

public class Mercenary extends BribableEntity {
    
    /**
     * Constructor for the Mercenary class.
     *
     * @param position The position of the entity.
     * @param health The health of the entity.
     * @param attackDamage The attack damage of the entity.
     **/
    public Mercenary(Position position, double health, double attackDamage) {
        super(position, "mercenary", health, attackDamage);
    }
    
    /**
     * Constructor for the Mercenary class.
     * 
     * @param position The position of the entity.
     * @param health The health of the entity.
     * @param attackDamage The attack damage of the entity.
     * @param isBribed Whether or not the mercenary is bribed.
     */
    public Mercenary(Position position, double health, double attackDamage, boolean isBribed, boolean isMindControlled, int mindControlledCounter) {
        super(position, "mercenary", health, attackDamage, isBribed, isMindControlled, mindControlledCounter);
    }    

    /**
     * Bribes the mercenary.
     * @param game
     * @param player
     * @throws InvalidActionException
     **/
    public void bribe(Game game, Player player) throws InvalidActionException {
        Config config = game.getConfig();
        List<Position> adjacentPositions = game.getAdjacentPositions(player.getPosition(), config.getBribeRadius());
        if (!adjacentPositions.contains(this.getPosition())) {
            throw new InvalidActionException("You must be adjacent to the mercenary to bribe him.");
        }

        List<Item> treasures = player.getTreasure();
        if (treasures.size() < config.getBribeAmount()) {
            throw new InvalidActionException("You do not have enough gold to bribe the mercenary.");
        }
        setBribed(true);
        setHostile(false);
        player.addAllies(this);
        for (int i = 0; i < config.getBribeAmount(); i++) {
            player.removeInventoryItem(treasures.get(i).getId());
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