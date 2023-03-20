package dungeonmania.player.PotionStates;

import dungeonmania.Entity;
import dungeonmania.Game;
import dungeonmania.battles.Battle;
import dungeonmania.movingentity.MovingEntity;
import dungeonmania.player.Player;

/**
 * RegularState class.
 */

public class RegularState implements PotionState {
    private Player player;
    private int timeUsed = 0;

    /**
     * Constructor for the RegularState class.
     * @param Player player
     */
    public RegularState(Player player) {
        this.player = player;
    }

    /**
     * Battles the entity.
     * @param game
     * @param entity
     */
    public void battle(Game game, MovingEntity e) {
        if (e.isHostile()) {
            Battle b = new Battle(player, e);
            b.standardBattle(game);
            game.addBattle(b);
            if (b.playerLost()) game.removeEntity(player); // PRINT YOU LOST???
            if (b.enemyLost()) {
                game.removeEntity((Entity) e);
                player.detach(e);
            } 
            player.reduceAllDurability();
        }
    }

    /**
     * Updates the potion state.
     */
    @Override
    public void updateState() {}

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
