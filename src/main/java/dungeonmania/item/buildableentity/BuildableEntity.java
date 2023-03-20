package dungeonmania.item.buildableentity;

import dungeonmania.Game;
import dungeonmania.player.Player;

/**
 * Buildable Entity Interface
 */

public interface BuildableEntity{

    /**
     * Builds the entity.
     * @param player player
     * @param game game
     */
    public void build(Player player, Game game);

    /**
     * Checks if the entity is buildable.
     * @param player player
     * @param game game
     * @return boolean
     */
    public boolean isBuildable(Player player, Game game);
}
