package dungeonmania.player.PotionStates;

import dungeonmania.Game;
import dungeonmania.battles.Battle;
import dungeonmania.movingentity.MovingEntity;
import dungeonmania.player.Player;

/**
 * InvincibilityState class.
 */

public class InvincibilityState implements PotionState {
    private Player player;
    private int duration;
    private int timeUsed = 0;

    /**
     * Constructor for the InvincibilityState class.
     * @param Player player
     * @param int duration
     */
    public InvincibilityState(Player player, int duration) {
        this.player = player;
        this.duration = duration;
    }

    /**
     * Battles the entity.
     * @param game
     * @param entity
     */
    public void battle(Game game, MovingEntity e) {
        if (e.isHostile()) {
            Battle b = new Battle(player, e);
            b.invincibleBattle(game);
            game.addBattle(b);
            // THIS WON'T REDUCE THE DURABILITY OF WEAPONS
            // enemy loses by default
            game.removeEntity(e);
            player.detach(e);
        }
    }
    
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
     * Setter for the time used.
     * @param int timeUsed
     */
    @Override
    public void setTimeUsed(int timeUsed) {
        this.timeUsed = timeUsed;
    }
    
}
