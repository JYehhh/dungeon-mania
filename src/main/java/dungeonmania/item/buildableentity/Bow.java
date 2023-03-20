package dungeonmania.item.buildableentity;

import dungeonmania.Game;
import dungeonmania.item.Weapon;
import dungeonmania.player.Player;
import dungeonmania.util.Position;

/**
 * Bow class.
 */

public class Bow extends Weapon implements BuildableEntity{
    private static final int WOOD_REQUIRED = 1;
    private static final int ARROWS_REQUIRED = 3;

    /**
     * Constructor for the Bow class.
     * @param Position position
     * @param durability durability
     */
    public Bow(Position position, int durability) {
        super("bow", position, durability);
    }

    /**
     * Builds the Bow.
     * @param player player
     * @param game game
     */
    @Override
    public void build(Player player, Game game) {
        if (isBuildable(player, game)) {
            player.removeItemQuantity("wood", WOOD_REQUIRED);
            player.removeItemQuantity("arrow", ARROWS_REQUIRED);
            player.addItem(this);
        }
    }

    /**
     * Checks if the Bow is buildable.
     * @param player player
     * @param game game
     * @return boolean
     */
    @Override
    public boolean isBuildable(Player player, Game game) {
        if (player.getItemQuantity("wood") >= WOOD_REQUIRED &&
         player.getItemQuantity("arrow") >= ARROWS_REQUIRED) {
            return true;
        }
        return false;
    }

    /**
     * Applies attack buff
     * @param attackBefore
     * @return attackAfter
     */
    public double applyAttackBuff(double attackBefore) {
        return attackBefore * 2;
    };
}
