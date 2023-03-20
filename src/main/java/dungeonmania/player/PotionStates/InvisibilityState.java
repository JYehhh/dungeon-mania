package dungeonmania.player.PotionStates;

import dungeonmania.Game;
import dungeonmania.movingentity.MovingEntity;
import dungeonmania.player.Player;

/**
 * InvisibilityState class.
 */

public class InvisibilityState implements PotionState {
    private Player player;
    private int duration;
    private int timeUsed = 0;

    /**
     * Constructor for the InvisibilityState class.
     * @param Player player
     * @param int duration
     */
    public InvisibilityState(Player player, int duration) {
        this.player = player;
        this.duration = duration;
    }

    /**
     * Battles the entity.
     * @param game
     * @param entity
     */
    public void battle(Game game, MovingEntity e) {}

    /**
     * Updates the potion state.
     */
    @Override
    public void updateState() {
        if (duration == timeUsed) {
            player.setPotionState(new RegularState(player));
            return;
        }
        timeUsed++;
    }

    /**
     * Getter for the duration.
     * @return int
     */
    public int getDuration() {
        return duration;
    }

    /**
     * Getter for the time used.
     * @return int
     */
    @Override
    public int getTimeUsed() {
        return timeUsed;
    }

    /**
     * Getter for the player.
     * @return Player
     */
    @Override
    public void setTimeUsed(int timeUsed) {
        this.timeUsed = timeUsed;
    }

}
