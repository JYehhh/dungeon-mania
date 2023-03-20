package dungeonmania.item.potion;

import dungeonmania.item.Item;
import dungeonmania.player.Player;
import dungeonmania.util.Position;

/**
 * Potion class.
 */

public abstract class Potion extends Item {
    int duration;

    /**
     * Constructor for the Potion class.
     * @param type
     * @param position
     * @param duration
     */
    public Potion(String type, Position position, int duration) {
        super(type, position);
        this.duration = duration;
    }

    /**
     * Consumes the Potion.
     * @param player player
     */
    public void consume(Player player) {};
}
