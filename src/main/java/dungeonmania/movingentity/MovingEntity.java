package dungeonmania.movingentity;

import dungeonmania.Entity;
import dungeonmania.Game;
import dungeonmania.player.Player;
import dungeonmania.util.Position;

/**
 * MovingEntity is an abstract class that represents all the moving entity in the dungeon including the player and enemies.
 **/

public abstract class MovingEntity extends Entity implements ObserverMovingEntities {
    double health;
    double attackDamage;
    double defense;
    boolean isHostile;

    /**
     * Constructor for the MovingEntity class.
     *
     * @param position The position of the entity.
     * @param type The type of the entity.
     * @param isInteractable Whether you can interact with the entity or not.
     * @param health The health of the entity.
     * @param attackDamage The attack damage of the entity.
     * @param defense The defense of the entity.
     * @param isHostile Whether or not the entity is hostile.
     **/
    public MovingEntity(Position position, String type, boolean isInteractable, double health, double attackDamage) {
        super(position, type, isInteractable);
        this.health = health;
        this.attackDamage = attackDamage;
        this.defense = 0;
        this.isHostile = true;
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //                                              Setters and Getters                                                 //
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Getter for the health.
     * @return health
     **/
    public double getHealth() {
        return health;
    }

    /**
     * Setter for the health.
     * @param health
     **/
    public void setHealth(double health) {
        this.health = health;
    }

    /**
     * Getter for the attack damage.
     * @return attackDamage
     **/
    public double getAttackDamage() {
        System.out.println("FROM FUNCTION CALL" + attackDamage);
        return attackDamage;
    }

    /**
     * Setter for the attack damage.
     * @param attackDamage
     **/
    public void setAttackDamage(double attackDamage) {
        this.attackDamage = attackDamage;
    }

    /*
     * Getter for the defense.
     * @return defense
     **/
    public double getDefense() {
        return this.defense;
    }

    /**
     * Setter for the defense.
     * @param defense
     **/
    public void setDefense(double defense) {
        this.defense = defense;
    }
    
    /**
     * Getter for the isHostile.
     * @return isHostile
     **/
    public boolean isHostile() {
        return isHostile;
    }

    /**
     * Setter for the isHostile.
     * @param isHostile
     **/
    public void setHostile(boolean isHostile) {
        this.isHostile = isHostile;
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //                                             Functionality Methods                                                //
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Reduce the health of the entity by the damage
     * @param damage
     **/
    public void reduceHealthDamageTaken(Game game, double damage) {
        this.health -= damage;
    }

    /**
     * Void interact method that is overridden by the subclasses.
     * @param game
     * @param entity
     **/
    public abstract void interact(Game game, Entity entity);

    /**
     * Void canTraverse method that is overridden by the subclasses.
     * Used to check if a moving entity can traverse a certain entity.
     * @param player
     **/
    public abstract boolean canTraverse(Entity entity);

    /**
     * Void tick method that is overridden by the subclasses.
     * Used to tick the moving entity, and thus move the entity
     * @param game
     **/
    @Override
    public abstract void tick(Game game);

    /**
     * Void update method that is overridden by the subclasses.
     * Used to update the movement strategy of the moving entity based on the potion state of the player.
     * @param game
     **/
    @Override
    public abstract void update(Game game, Player player);
}