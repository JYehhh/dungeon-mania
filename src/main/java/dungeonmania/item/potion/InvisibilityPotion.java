package dungeonmania.item.potion;


import dungeonmania.Game;
import dungeonmania.item.Consumable;
import dungeonmania.player.Player;
import dungeonmania.player.PotionStates.InvisibilityState;
import dungeonmania.util.Position;

/*
 * Invisibility Potion class.
 */

public class InvisibilityPotion extends Potion implements Consumable{

    /**
     * Constructor for the Invisibility Potion class.
     * @param Position position
     * @param int duration
     */
    public InvisibilityPotion(Position position, int duration) {
        super("invisibility_potion", position, duration);
    }

    /**
     * Consumes the Invisibility Potion.
     * @param player player
     * @param game game
     */
    @Override
    public void consume(Game game, Player player) {
        player.setPotionState(new InvisibilityState(player, duration));
        player.removeInventoryItem(this.getId());
    }
}
