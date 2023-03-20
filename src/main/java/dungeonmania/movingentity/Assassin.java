package dungeonmania.movingentity;

import java.util.List;
import java.util.Random;

import dungeonmania.Config;
import dungeonmania.Entity;
import dungeonmania.Game;
import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.item.Item;
import dungeonmania.player.Player;
import dungeonmania.util.Position;

/**
 * Assassin extends MovingEntity.
 **/

public class Assassin extends BribableEntity {

    /**
     * Constructor for the Assassin class.
     *
     * @param position The position of the entity.
     * @param health The health of the entity.
     * @param attackDamage The attack damage of the entity.
     **/
    public Assassin(Position position, double health, double attackDamage) {
        super(position, "assassin", health, attackDamage);
    }

    /**
     * Constructor for the Assassin class.
     * 
     * @param position The position of the entity.
     * @param health The health of the entity.
     * @param attackDamage The attack damage of the entity.
     * @param isBribed Whether or not the mercenary is bribed.
     */
    public Assassin(Position position, double health, double attackDamage, boolean isBribed, boolean isMindControlled, int mindControlledCounter) {
        super(position, "assassin", health, attackDamage, isBribed, isMindControlled, mindControlledCounter);
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
            throw new InvalidActionException("You must be adjacent to the assassin to bribe him.");
        }

        List<Item> treasures = player.getTreasure();
        if (treasures.size() < config.getAssassinBribeAmount()) {
            throw new InvalidActionException("You do not have enough gold to bribe the assassin.");
        }

        Random random = new Random();
        
        setBribed(random.nextFloat() < (1 - config.getAssassinBribeFailRate()));
        if (isBribed()) {
            setHostile(false);
            player.addAllies(this);
        }
        for (int i = 0; i < config.getBribeAmount(); i++) {
            player.removeInventoryItem(treasures.get(i).getId());
        }
    }

    @Override
    public void interact(Game game, Entity entity) {
        ((Player) entity).setPosition(this.getPosition()); 
        if (entity instanceof Player) { 
            ((Player) entity).getPotionState().battle(game, this);
        }
        
    }
}
