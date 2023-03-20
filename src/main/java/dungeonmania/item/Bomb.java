package dungeonmania.item;

import java.util.List;

import dungeonmania.Entity;
import dungeonmania.Game;
import dungeonmania.player.Player;
import dungeonmania.staticentity.FloorSwitch;
import dungeonmania.util.Position;

/**
 * Bomb class. 
 **/

public class Bomb extends Item implements Consumable{
    
    private int radius;
    private boolean isPlaced = false;

    /**
     * Constructor for Bomb.
     * @param position Position of the bomb.
     * @param radius Radius of the bomb.
     **/
    public Bomb(Position position, int radius) {
        super("bomb", position);
        this.radius = radius;
    }

    /** Uses the bomb, and consumes the bomb.
     * @param Game
     * @param player
     **/
    @Override
    public void consume(Game game, Player player) {
        this.setPosition(player.getPosition());
        player.removeInventoryItem(this.getId());
        game.addEntity(this);
        this.isPlaced = true;

        // if there is an active switch next to it, then explode
        List<Entity> cAdjacentEntities = game.getCardinallyAdjacentEntities(player.getPosition());
        cAdjacentEntities.forEach(e -> {
            if (e instanceof FloorSwitch) {
                if (((FloorSwitch) e).isTriggered(game)) this.explode(game);
            } 
        });
        // in switch class, if it flips to active and theres a bomb next to it, explode.
    }

    /**
     * Interacts with the bomb.
     * @param Game
     **/
    @Override
    public void interact(Game game, Entity player) {
        if (!isPlaced) {
            super.interact(game, player);
        }
    }

    /**
     * Explodes the bomb.
     * @param Game
     **/
    public void explode(Game game) {
        // gotta make this explode everything in a radius
        List<Entity> entities = game.getAdjacentEntities(this.getPosition(), this.radius);
        game.removeEntity(this);
        entities.forEach(entity -> { 
            if (entity instanceof Bomb) ((Bomb) entity).explode(game); // recursion: explode all bombs around the area
            if (!(entity instanceof Player)) game.removeEntity(entity); // if the entity is not a player, get rid of it
        });
    }

    /**
     * Checks if the bomb is placed.
     * @return radius
     **/
    public boolean isPlaced() {
        return isPlaced;
    }

}
