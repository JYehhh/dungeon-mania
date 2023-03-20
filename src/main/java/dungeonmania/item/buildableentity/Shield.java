package dungeonmania.item.buildableentity;

import dungeonmania.Game;
import dungeonmania.item.Weapon;
import dungeonmania.player.Player;
import dungeonmania.util.Position;

/**
 * Sheild class.
 */

public class Shield extends Weapon implements BuildableEntity{
    private static final int WOOD_REQUIRED = 2;
    private static final int TREASURE_REQUIRED = 1;
    private static final int KEY_REQUIRED = 1;

    private int additiveDefence;

    /**
     * Constructor for the Shield class.
     * @param Position position
     * @param defence defence
     * @param durability durability
     */
    public Shield(Position position, int defence, int durability) {
        super("shield", position, durability);
        this.additiveDefence = defence;
    }

    /**
     * Builds the Shield.
     * @param player player
     * @param game game
     */
    @Override
    public void build(Player player, Game game) {
        if (this.isBuildable(player, game)) {
            if (player.hasItemOfType("sun_stone")) {
                player.removeItemQuantity("wood", WOOD_REQUIRED);
            } else if (player.getItemQuantity("treasure") >= TREASURE_REQUIRED) {
                player.removeItemQuantity("wood", WOOD_REQUIRED);
                player.removeItemQuantity("treasure", TREASURE_REQUIRED);
            } else if (player.getItemQuantity("key") >= KEY_REQUIRED) {
                player.removeItemQuantity("wood", WOOD_REQUIRED);
                player.removeItemQuantity("key", KEY_REQUIRED);
            }
            player.addItem(this);
        }
    }

    /**
     * Checks if the Shield is buildable.
     * @param player player
     * @param game game
     * @return boolean
     */
    public boolean isBuildable(Player player, Game game) {
        if (player.getItemQuantity("treasure") >= TREASURE_REQUIRED ||
            player.getItemQuantity("key") >= KEY_REQUIRED ||
            player.hasItemOfType("sun_stone")) {
            return true;
        }
        return false;
    }

    /**
     * Applies a defence buff to the shield.
     * @param defenceBefore defence before
     * @return defenceAfter
     */
    @Override
    public double applyDefenceBuff(double defenceBefore) {
        return defenceBefore + additiveDefence;
    }; 
}
