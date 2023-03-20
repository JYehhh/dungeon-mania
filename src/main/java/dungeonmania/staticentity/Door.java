package dungeonmania.staticentity;

import dungeonmania.Entity;
import dungeonmania.Game;
import dungeonmania.item.Key;
import dungeonmania.movingentity.Spider;
import dungeonmania.player.Player;
import dungeonmania.util.Position;

/**
 * Door class.
 **/

public class Door extends StaticEntity{

    private int key;
    private boolean isOpen; 

    /**
     * Constructor for the Door class.
     * @param position
     * @param key
     **/
    public Door(Position position, int key) {
        super(position, "door", true);
        this.isOpen = false;
        this.key = key;
    }

    /**
     * Constructor for the Door class.
     * @param position
     * @param key
     * @param isOpen
     **/
    public Door(Position position, int key, boolean isOpen) {
        super(position, "door", true);
        this.isOpen = isOpen;
        this.key = key;
    }

    /**
     * Opens the door.
     **/
    public void openDoor() {
        this.isOpen = true;
    }

    /**
     * Gets the key of the door.
     * @return the key
     **/
    public int getKey() {
        return key;
    }

    /**
     * Checks if the door is open.
     * @return boolean
     **/
    public boolean isOpen() {
        return this.isOpen;
    }

    /**
     * Interacts with the door.
     * @param game
     * @param enity
     **/
    @Override
    public void interact(Game game, Entity entity) {
        // only player can unlock the door
        if (!isOpen) {
            if ((entity instanceof Player)) {
                Player p = (Player) entity;
                if (p.hasItemOfType("sun_stone")) {
                    this.openDoor();
                    p.setPosition(this.getPosition());
                } else {
                    Key k = p.getInventory().getKey();
                    if (k != null && k.getKey() == this.getKey()) {
                        this.openDoor();
                        p.setPosition(this.getPosition());
                        k.consume(game, p);
                    }
                }
            } else if (entity instanceof Spider) {
                    entity.setPosition(this.getPosition());
            }
        } else {
            entity.setPosition(this.getPosition());
        }
    }    
}

