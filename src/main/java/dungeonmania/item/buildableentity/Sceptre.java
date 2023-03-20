package dungeonmania.item.buildableentity;

import dungeonmania.Game;
import dungeonmania.item.Weapon;
import dungeonmania.player.Player;
import dungeonmania.util.Position;

/*
 * Sceptre class.
 */

public class Sceptre extends Weapon implements BuildableEntity{
    private static final int WOOD_REQUIRED = 1;
    private static final int ARROWS_REQUIRED = 2;
    private static final int KEYS_REQUIRED = 1;
    private static final int TREASURE_REQUIRED = 1;
    private static final int SUNSTONE_REQUIRED = 1;

    /**
     * Constructor for the Sceptre class.
     * @param Position position
     */
    public Sceptre(Position position) {
        super("sceptre", position, 0);
    }

    /**
     * Builds the Sceptre.
     * @param player player
     * @param game game
     */
    @Override
    public void build(Player player, Game game) {
        if (this.isBuildable(player, game)) {
            boolean prioritiseTreasure = player.getItemQuantity("treasure") >= TREASURE_REQUIRED;
            boolean prioritiseWood = player.getItemQuantity("wood") >= WOOD_REQUIRED;
            if (prioritiseTreasure) {
                player.removeItemQuantity("treasure", TREASURE_REQUIRED);
            } else {
                player.removeItemQuantity("key", KEYS_REQUIRED);
            }

            if (prioritiseWood) {
                player.removeItemQuantity("wood", WOOD_REQUIRED);
            } else {
                player.removeItemQuantity("arrow", ARROWS_REQUIRED);
            }

            player.removeItemQuantity("sun_stone", SUNSTONE_REQUIRED);
            player.addItem(this);
        }
        
    }

    /**
     * Checks if the Sceptre is buildable.
     * @param player player
     * @param game game
     * @return boolean
     */
    public boolean isBuildable(Player player, Game game) {
        if (player.getItemQuantity("sun_stone") >= SUNSTONE_REQUIRED && (
            player.getItemQuantity("treasure") >= TREASURE_REQUIRED ||
            player.getItemQuantity("key") >= KEYS_REQUIRED) && (
            player.getItemQuantity("wood") >= WOOD_REQUIRED ||
            player.getItemQuantity("arrow") >= ARROWS_REQUIRED
            )) {
            return true;
        }
        return false;
    }
    
    /**
     * void ticks the durabiltiy of the Sceptre.
     * @param player
     */
    @Override 
    public void tickDurability(Player player) {}
}
