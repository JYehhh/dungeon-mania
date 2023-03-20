package dungeonmania.player.PotionStates;

import dungeonmania.Game;
import dungeonmania.movingentity.MovingEntity;

/**
 * PotionState Interface.
 */

public interface PotionState {

    /**
     * Updates the potion state.
     */
    public void updateState();

    /**
     * Battles the entity.
     * @param game
     * @param entity
     */
    public void battle(Game game, MovingEntity e);

    /**
     * Getter for the duration.
     * @return int
     */
    public int getTimeUsed();

    /**
     * Getter for the duration.
     * @return int
     */
    public void setTimeUsed(int timeUsed);
}
