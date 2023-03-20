package dungeonmania.item;

import dungeonmania.Entity;
import dungeonmania.Game;
import dungeonmania.player.Player;
import dungeonmania.util.Position;

/**
 * Key class.
 */

public class Key extends Item implements Consumable {

    private int key;

    /**
     * Constructor for Key.
     * @param position Position of the key.
     * @param key Key number.
     */
    public Key(Position position, int key) {
        super("key", position);
        this.key = key;
    }
    
    /**
     * @return the key
     */
    public int getKey() {
        return key;
    }

    /**
     * Interacts with the key.
     * @param Game
     * @param player
     */
    @Override
    public void interact(Game game, Entity player) {
        if (!((Player) player).hasItemOfType("key")) {
            super.interact(game, player);
        } else {
            player.setPosition(this.getPosition());
        }
    }

    /**
     * Consumes the key.
     * @param Game
     * @param player
     */
    @Override
    public void consume(Game game, Player player) {
        player.removeInventoryItem(this.getId());
    }
    
}
