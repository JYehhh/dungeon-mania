package dungeonmania.item.potion;

import dungeonmania.Game;
import dungeonmania.item.Consumable;
import dungeonmania.player.Player;
import dungeonmania.player.PotionStates.InvincibilityState;
import dungeonmania.util.Position;

/**
 * Invincibility Potion class.
 */

public class InvincibilityPotion extends Potion implements Consumable{

    /**
     * Constructor for the Invincibility Potion class.
     * @param Position position
     * @param int duration
     */
    public InvincibilityPotion(Position position, int duration) {
        super("invincibility_potion", position, duration);
    }

    /**
     * Consumes the Invincibility Potion.
     * @param player player
     * @param game game
     */
    @Override
    public void consume(Game game, Player player) {
        player.setPotionState(new InvincibilityState(player, duration));
        player.removeInventoryItem(this.getId());
    }
}
