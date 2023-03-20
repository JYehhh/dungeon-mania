package dungeonmania.staticentity;

import dungeonmania.Entity;
import dungeonmania.Game;
import dungeonmania.util.Position;

/**
 * SwampTile class.
 **/

public class SwampTile extends StaticEntity {

    private int movementFactor;

    /**
     * Constructor for the SwampTile class.
     * @param position
     * @param movementFactor
     **/
    public SwampTile(Position position, int movementFactor) {
        super(position, "swamp_tile", false);
        this.movementFactor = movementFactor;
    }

    /**
     * Gets the movement factor of the swamp tile.
     * @return the movement factor
     **/
    public int getMovementFactor() {
        return movementFactor;
    }

    /**
     * Interacts with the swamp tile.
     * @param game
     * @param entity
     **/
    @Override
    public void interact(Game game, Entity e) {
        return;
    }
    
}
