package dungeonmania.player;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import dungeonmania.item.Item;
import dungeonmania.item.Key;
import dungeonmania.item.Weapon;
import dungeonmania.response.models.ItemResponse;

/*
 * Inventory class.
 */

public class Inventory {
    private ArrayList<Item> items = new ArrayList<Item>();

    /**
     * Constructor for the Inventory class.
     * @param ArrayList<Item> items
     */
    public Inventory(ArrayList<Item> arrayList) {
        this.items = arrayList;
    }

    /**
     * Adds an item to the inventory.
     * @param item
     */
    public void addItem(Item item) {
        items.add(item);
    }

    /**
     * Removes an item from the inventory.
     * @param itemId
     */
    public void removeInventoryItem(String itemId) {
        // PLEASE NOTE THAT HERE - IT WILL DELETE EVERY ITEM WITH THE GIVEN ITEMID (if there are more than 1)
        Predicate<Item> removeCondition = item -> item.getId().equals(itemId);
        items.removeIf(removeCondition);
    }

    /**
     * Generates inventory response.
     * @return ArrayList<ItemResponse>
     */
    public ArrayList<ItemResponse> getInventoryResponse() {
        return (ArrayList<ItemResponse>) items
            .stream()
            .map(item -> item.getItemResponse())
            .collect(Collectors.toList());
    }    

    /**
     * Gets the item with the given id.
     * @param itemId
     * @return Item
     */
    public Item getItem(String itemId) {
        return items
            .stream()
            .filter(item -> item.getId().equals(itemId))
            .findFirst().orElse(null);
    }

    /**
     * Gets all the items.
     * @return ArrayList<Item>
     */
    public ArrayList<Item> getItems() {
        return items;
    }

    /**
     * Gets items with the given type.
     * @param type
     * @return List<Item>
     */
    public List<Item> getItemsOfType(String type) {
        return items
            .stream()
            .filter(item -> item.getType().equals(type))
            .collect(Collectors.toList());
    }

    /**
     * Gets item with the given type.
     * @param type
     * @return Item
     */
    public Item getItemOfType(String type) {
        return items
            .stream()
            .filter(item -> item.getType().equals(type))
            .findFirst().orElse(null);
    }

    /**
     * Gets the key
     * @return Key
     */
    public Key getKey() {
        return items
            .stream()
            .filter(item -> (item instanceof Key))
            .map(item -> (Key) item)
            .findFirst().orElse(null);
    }

    /**
     * Gets the weapon used.
     * @return List<Item>
     */
    public List<Item> getWeaponsUsed() {
        return items
            .stream()
            .filter(item -> item instanceof Weapon)
            .collect(Collectors.toList());
    }

    /**
     * Removes item with the given id and quantity.
     * @param type
     * @param quantity
     **/
    public void removeItemQuantity(String type, int quantity) {
        if (this.getItemQuantity(type) >= quantity) {
            List<Item> toRemove = items
                .stream()
                .filter(item -> item.getType().equals(type))
                .limit(quantity)
                .collect(Collectors.toList());
            toRemove.stream().forEach(item -> items.remove(item));
        };
    }

    /**
     * Gets the quantity of the given type.
     * @param type
     * @return int
     */
    public int getItemQuantity(String type) {
        return items
            .stream()
            .filter(item -> (item.getType().equals(type)))
            .collect(Collectors.toList())
            .size();
    }

    /**
     * Reduces the durability of all weapons.
     * @param player
     */
    public void reduceAllDurability(Player player) {
        items
            .stream()
            .filter(item -> (item instanceof Weapon))
            .forEach(w -> ((Weapon) w).tickDurability(player));
    }

    /**
     * Sets the item list.
     * @param itemList
     */
    public void setItemList(ArrayList<Item> itemList) {
        items = itemList;
    }
}
    
    

