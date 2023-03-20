package dungeonmania.item;

import dungeonmania.util.Position;

/**
 * Treasure class.
 */

public class Treasure extends Item{

    /**
     * Constructor for Treasure.
     * @param position Position of the treasure.
     */
    public Treasure(Position position) {
        super("treasure", position);
    }
    
}
