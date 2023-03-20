package dungeonmania.movingentity;

import dungeonmania.Game;
import dungeonmania.player.Player;

/**
 * This class is an observer of the MovingEntities. It is used to update the moving entites movement strategy when the player changes potion states.
 **/

public interface ObserverMovingEntities {

    /**
     * Updates the movement strategy of the moving entity based on the potion state of the player.
     * @param player
     */
    public void update(Game game, Player player);
}