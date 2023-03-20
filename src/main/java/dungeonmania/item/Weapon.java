package dungeonmania.item;

import dungeonmania.player.Player;
import dungeonmania.util.Position;

/**
 * Weapon class.
 */

public abstract class Weapon extends Item{
    private int durability;

    /**
     * Constructor for Weapon.
     * @param name Name of the weapon.
     * @param position Position of the weapon.
     * @param durability Durability of the weapon.
     */
    public Weapon(String type, Position position, int durability) {
        super(type, position);
        this.durability = durability;
    }

    /**
     * Getter for durability.
     * @return durability
     */
	public int getDurability() {
		return durability;
	}

    /**
     * Ticks the durability of the weapon
     * @param player
     */
    public void tickDurability(Player player) {
        durability--;
        if (durability <= 0)   {
            player.removeInventoryItem(this.getId());
        }
    }

    /**
     * Applies attack buff.
     * @param attackBefore
     * @return attackAfter
     **/
    public double applyAttackBuff(double attackBefore) {
        return attackBefore;
    };

    /**
     * Applies defense buff.
     * @param defenseBefore
     * @return defenseAfter
     **/
    public double applyDefenceBuff(double defenceBefore) {
        return defenceBefore;
    }; 

}
