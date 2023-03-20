package dungeonmania.item;

import dungeonmania.util.Position;

/**
 * Sword class.
 */

public class Sword extends Weapon{
    private int additiveAttack;

    /**
     * Constructor for Sword.
     * @param position Position of the sword.
     * @param attack Attack of the sword.
     * @param durability Durability of the sword.
     */
    public Sword(Position position, int attackDamage, int durability) {
        super("sword", position, durability);
        this.additiveAttack = attackDamage;
    }

    /**
     * Applies attack buff.
     * @param attackBefore
     * @return attackAfter
     **/
    @Override
    public double applyAttackBuff(double attackBefore) {
        return attackBefore + additiveAttack;
    };
}
