package dungeonmania.player;

import java.util.ArrayList;
import java.util.List;

import dungeonmania.Entity;
import dungeonmania.Game;
import dungeonmania.item.Item;
import dungeonmania.item.Weapon;
import dungeonmania.item.buildableentity.Bow;
import dungeonmania.item.potion.Potion;
import dungeonmania.movingentity.BribableEntity;
import dungeonmania.movingentity.MovingEntity;
import dungeonmania.movingentity.ObserverMovingEntities;
import dungeonmania.movingentity.MovementStrategy.MovementStrategy;
import dungeonmania.movingentity.MovementStrategy.PlayerMovement;
import dungeonmania.player.PotionStates.InvincibilityState;
import dungeonmania.player.PotionStates.InvisibilityState;
import dungeonmania.player.PotionStates.PotionState;
import dungeonmania.player.PotionStates.RegularState;
import dungeonmania.response.models.ItemResponse;
import dungeonmania.staticentity.Wall;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

/**
 * Player class.
 */

public class Player extends MovingEntity implements SubjectPlayer {
    private MovementStrategy strategy = new PlayerMovement(this);
    private PotionState potionState;
    private Inventory inventory;
    private Direction directionToMove;
    private List<ObserverMovingEntities> observers = new ArrayList<>();
    private Position previousPosition;
    private List<BribableEntity> allies = new ArrayList<>();

    /**
     * Constructor for the Player class.
     * @param position
     * @param health
     * @param attackDamage
     */
    public Player(Position position, int health, int attackDamage) {
        super(position, "player", false, (double) health, (double) attackDamage);
        this.potionState = new RegularState(this);
        this.inventory = new Inventory(new ArrayList<Item>());
    }

    /**
     * Constructor for the Player class.
     * @param position
     * @param health
     * @param attackDamage
     * @param potionStateString
     * @param invisibilityPotionDuration
     * @param invincibilityPotionDuration
     * @param timeUsed
     */
    public Player(Position position, double health, int attackDamage, String potionStateString, int invisibilityPotionDuration, int invincibilityPotionDuration, int timeUsed) {
        super(position, "player", false, health, attackDamage);
        this.potionState = setPotionStateString(potionStateString, invisibilityPotionDuration, invincibilityPotionDuration);
        this.inventory = new Inventory(new ArrayList<Item>());
        this.potionState.setTimeUsed(timeUsed);
    }

    /**
     * Gets the allies.
     * @return List<BribableEntity>
     */
    public List<BribableEntity> getAllies() {
        return allies;
    }

    /**
     * Adds an ally.
     * @param bribableEntity
     */
    public void addAllies(BribableEntity bribableEntity) {
        allies.add(bribableEntity);
    }

    /**
     * Removes an ally.
     * @param bribableEntity
     */
    public void removeAllies(BribableEntity bribableEntity) {
        allies.remove(bribableEntity);
    }
    
    /**
     * Gets the previous position.
     * @return Position
     */
    public Position getPreviousPosition() {
        return previousPosition;
    }

    /**
     * Sets the previous position.
     * @param previousPosition
     */
    public void setPreviousPosition(Position previousPosition) {
        this.previousPosition = previousPosition;
    }
    
    /**
     * Gets the time used.
     * @return int
     */
    public int getTimeUsed() {
        return potionState.getTimeUsed();
    }

    /**
     * Gets the PotionState.
     * @return PotionState
     */
    public PotionState getPotionState() {
        return potionState;
    }

    /**
     * Sets the PotionState.
     * @param potionState
     */
    public void setPotionState(PotionState potionState) {
        this.potionState = potionState;
    }

    /**
     * Gets the inventory.
     * @return Inventory
     */
    public Inventory getInventory() {
        return inventory;
    }

    /**
     * Sets the inventory.
     * @param inventory
     */
    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    /**
     * Gets the item with the given id.
     * @param Itemid
     * @return Item
     */
    public Item getItem(String itemId) {
        return inventory.getItem(itemId);
    }

    /**
     * cheks if the player has the item with the given id.
     * @param type
     * @return boolean
     */
    public boolean hasItemOfType(String type) {
        if (inventory.getItemsOfType(type).isEmpty()) return false;
        return true;
    }

    /**
     * Checks if has weapon.
     * @return boolean
     */
    public boolean hasWeapon() {
        if (inventory.getWeaponsUsed().isEmpty()) return false;
        return true;
    }

    /**
     * Gets treasure.
     * @return List<Item>
     */
    public List<Item> getTreasure() {
        return inventory.getItemsOfType("treasure");
    }

    /**
     * Gets inventory response.
     * @return List<ItemResponse>
     */
    public List<ItemResponse> getInventoryResponse() {
        return inventory.getInventoryResponse();
    }

    /**
     * ticks the player.
     */
    public void tick() {
        this.potionState.updateState();
        return;
    }

    /**
     * Moves the player.
     * @param game
     * @param direction
     */
    public void move(Game game, Direction direction) {
        directionToMove = direction;
        previousPosition = getPosition();
        strategy.move(game);
        // TODO: allow player to step on an exit/floor switch
        // TODO: allow player to step on the floor that the boulder previously on after moving a boulder
    }

    /**
     * Collects the item.
     * @param item
     * @param game
     */
    public void collect(Item item, Game game) {
        inventory.addItem(item);
        game.removeEntity(item);
    }

    /**
     * Consumes the potion
     * @param potionId
     */
    public void consumePotion(String potionId) {
        Potion potion = (Potion) this.getItem(potionId);
        potion.consume(this);
    }

    /**
     * Removes the item from the inventory.
     * @param itemId
     */
    public void removeInventoryItem(String itemId) {
        inventory.removeInventoryItem(itemId);
    }


    /**
     * Interacts with the entity.
     * @param game
     * @param entity
     */
    @Override
    public void interact(Game game, Entity e) {
        // Implement if entity is hostile 
        if (e instanceof MovingEntity) { // && ifHostile
            // Battle b = new Battle(this, (MovingEntity) e);
            potionState.battle(game, (MovingEntity) e);
            // b.startBattle();
            // add method in game to store battle response object 
        }
        
    }

    /**
     * Void tick function
     * @param game
     */
    @Override
    public void tick(Game game) {
        return;
    }

    /**
     * Gets the direction to move.
     * @return Direction
     */
    public Direction getDirectionToMove() {
        return directionToMove;
    }

    /**
     * Checks if the entity can traverse the given entity.
     * @param entity
     */
    public boolean canTraverse(Entity entity) {
        if (entity instanceof Wall) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * Gets the potionStateString.
     * @return String
     */
    public String getPotionStateString() {
        if (potionState instanceof RegularState) return "regular";
        else if (potionState instanceof InvisibilityState) return "invisible";
        else return "invincibile";
    }

    /**
     * Sets the potionStateString.
     * @return PotionState
     */
    public PotionState setPotionStateString(String potionStateString, int invisibilityPotionDuration, int invincibilityPotionDuration) {
        if (potionStateString.equals("regular")) return new RegularState(this);
        else if (potionStateString.equals("invisible")) {
            return new InvisibilityState(this, invisibilityPotionDuration);
        } else {
            return new InvincibilityState(this, invincibilityPotionDuration);
        }
    }

    /**
     * Void update function
     * @param game
     * @param player
     */
    public void update(Game game, Player player) {
        return;
    }

    /**
     * Adds an observer to the list of observers.
     * @param observer
     */
    @Override
    public void attach(ObserverMovingEntities observer) {
        if (!observers.contains(observer)) {
            observers.add(observer);
        }
        
    }

    /**
     * Removes an observer from the list of observers.
     * @param observer
     */
    @Override
    public void detach(ObserverMovingEntities observer) {
        if (observers.contains(observer)) {
            observers.remove(observer);
        }
        
    }

    /**
     * Notifies all observers.
     * @param game
     */
    @Override
    public void notifyObservers(Game game) {
        for (ObserverMovingEntities observer : observers) {
            observer.update(game, this);
        }        
    }

    /**
     * Gets the quantity of the item with the given type
     * @param type
     * @return int
     */
    public int getItemQuantity(String type) {
        return inventory.getItemQuantity(type);
    }

    /**
     * Removes the item with the given type and quantity.
     * @param type
     * @param quantity
     */
    public void removeItemQuantity(String type, int quantity) {
        inventory.removeItemQuantity(type, quantity);
    }

    /**
     * Adds item to inventory.
     * @param item
     */
    public void addItem(Item item) {
        inventory.addItem(item);
    }

    /**
     * Reduces the durability of all weapons.
     */
    public void reduceAllDurability() {
        inventory.reduceAllDurability(this);
    }

    /**
     * Gets weapon used.
     * @return List<Item>
     */
    public List<Item> getWeaponsUsed() {
        return inventory.getWeaponsUsed();
    }

    public double getBuffedDefense(Game game) {
        double buffedDef = super.getDefense() + getAllyDefence(game);
        for (Item i : getWeaponsUsed()) buffedDef = ((Weapon) i).applyDefenceBuff(buffedDef);
        System.out.println("BUFFED DEFENCE " + buffedDef);
        return buffedDef;
    }

    public double getBuffedAttack(Game game) {
        double buffedAtk = super.getAttackDamage() + getAllyAttack(game);
        for (Item i : getWeaponsUsed()) 
            if (!(i instanceof Bow)) buffedAtk = ((Weapon) i).applyAttackBuff(buffedAtk);
        if (hasItemOfType("bow")) buffedAtk = ((Weapon) getItemOfType("bow")).applyAttackBuff(buffedAtk);
        System.out.println("BUFFED ATTACK " + buffedAtk);
        return buffedAtk;
    }



    public void setItemsList(ArrayList<Item> itemsList) {
        Inventory inventory = getInventory();
        inventory.setItemList(itemsList);
    }

    public double getAllyAttack(Game game) {
        return game.getConfig().getAllyAttack() * allies.size();
    }

    public double getAllyDefence(Game game) {
        return game.getConfig().getAllyDefence() * allies.size();
    }

    /////////////////////////////////////////

    /**
     * Gets item of the given type.
     * @param type
     * @return Item
     */
    private Item getItemOfType(String type) {
        if (hasItemOfType(type)) return inventory.getItemOfType(type);
        return null;
    }
}
