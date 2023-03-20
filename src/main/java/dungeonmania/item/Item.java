package dungeonmania.item;

import dungeonmania.Entity;
import dungeonmania.Game;
import dungeonmania.util.Position;
import dungeonmania.player.Player;
import dungeonmania.response.models.ItemResponse;

/**
 * Base class for all items.
 */

public abstract class Item extends Entity {

    /**
     * Constructor for Item.
     * @param type type of the item.
     * @param position Position of the item.
     */
    public Item(String type, Position position) {
        super(position, type, false);
    }

    public void interact(Game game, Entity player) {
        if (!(player instanceof Player)) return;
        player.setPosition(this.getPosition());
        ((Player) player).collect(this, game);
        // Remove entity from dungeon
    }

    /**
     * Void tick function
     * @param game Game.
     */
    public void tick(Game game) {
        return;
    }

    // RESPONSE OBJECT FETCHING
    public ItemResponse getItemResponse() {
        return new ItemResponse(this.getId(), this.getType());
    }  


}
