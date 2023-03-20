package dungeonmania.item;

import dungeonmania.Game;
import dungeonmania.player.Player;

/**
 * Consumable Interface.
 */

public interface Consumable {
    
    /**
     * Consumes the item.
     * @param game Game.
     * @param player Player.
     */
    public void consume(Game game, Player player);
}
