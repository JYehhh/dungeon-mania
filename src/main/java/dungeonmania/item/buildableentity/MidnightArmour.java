package dungeonmania.item.buildableentity;

import dungeonmania.Game;
import dungeonmania.item.Weapon;
import dungeonmania.player.Player;
import dungeonmania.util.Position;

/**
 * Midnight Armour class.
 */


public class MidnightArmour extends Weapon implements BuildableEntity{
    private static final int SWORD_REQUIRED = 1;
    private static final int SUNSTONE_REQUIRED = 1;

    private int additiveAttack;
    private int additiveDefence;

    /**
     * Constructor for the Midnight Armour class.
     * @param Position position
     * @param attackDamage attackDamage
     * @param defence defence
     */
    public MidnightArmour(Position position, int attackDamage, int defence) {
        super("midnight_armour", position, 0);
        this.additiveAttack = attackDamage;
        this.additiveDefence = defence;
    }

    /**
     * Builds the Midnight Armour.
     * @param player player
     * @param game game
     */
    @Override
    public void build(Player player, Game game) {
        if (this.isBuildable(player, game)) {
            player.removeItemQuantity("sword", SWORD_REQUIRED);
            player.removeItemQuantity("sun_stone", SUNSTONE_REQUIRED);
            player.addItem(this);
        }
    }

    /**
     * Checks if the Midnight Armour is buildable.
     * @param player player
     * @param game game
     * @return boolean
     */
    @Override
    public boolean isBuildable(Player player, Game game) {
        if (game.getNumberEntities("zombie") == 0) {
            if (player.getItemQuantity("sword") > SWORD_REQUIRED &&
            player.getItemQuantity("sun_stone") > SUNSTONE_REQUIRED) {
                return true;
            }
        }
        return false;
    }

    /**
     * Ticks the durability of the armour.
     * @param player
     */
    @Override 
    public void tickDurability(Player player) {}

    /**
     * Applies attack buff
     * @param attackBefore
     * @return attackAfter
     */
    @Override
    public double applyAttackBuff(double attackBefore) {
        return attackBefore + additiveAttack;
    };

    /**
     * Applies defence buff
     * @param defenceBefore
     * @return defenceAfter
     */
    @Override
    public double applyDefenceBuff(double defenceBefore) {
        return defenceBefore + additiveDefence;
    };
}
