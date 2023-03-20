package dungeonmania.staticentity;

import dungeonmania.Entity;
import dungeonmania.Game;
import dungeonmania.util.Position;

/**
 * Base class for all static entities.
 */

public abstract class StaticEntity extends Entity{

    /**
     * Constructor for the StaticEntity class.
     * @param position
     * @param name
     * @param isInteractable
     */
    public StaticEntity(Position position, String type, boolean isInteractable) {
        super(position, type, isInteractable);
    }

    /**
     * Interacts with the static entity.
     * @param game
     * @param entity
     */
    @Override
    public void interact(Game game, Entity e) {};

    /**
     * void tick function.
     * @param game
     */
    @Override
    public void tick(Game game) {
        return;
    } 
}
