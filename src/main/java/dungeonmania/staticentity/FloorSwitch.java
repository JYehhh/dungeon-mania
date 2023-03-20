package dungeonmania.staticentity;

import java.util.List;

import dungeonmania.Entity;
import dungeonmania.Game;
import dungeonmania.item.Bomb;
import dungeonmania.util.Position;

/**
 * FloorSwitch class.
 **/

public class FloorSwitch extends StaticEntity{

    /**
     * Constructor for the FloorSwitch class.
     * @param position
     **/
    public FloorSwitch(Position position) {
        super(position, "switch", true);
    }

    /**
     * Checks if the switch is triggered.
     * @param game
     * @return boolean
     */
    public boolean isTriggered(Game game) {
        List<Entity> entitiesOnPosition = game.getEntities(this.getPosition());
        return entitiesOnPosition.stream().anyMatch(e -> e instanceof Boulder);
    }

    /**
     * Triggers the switch.
     * @param game
     **/
    public void triggerFloorSwitch(Game game) {
        // when the switch is triggered, cardinally adjacent bomb will explode
        List<Entity> entitiesCardAdjacent = game.getCardinallyAdjacentEntities(this.getPosition());
        for (Entity e : entitiesCardAdjacent) {
            if (e instanceof Bomb) {
                if (((Bomb) e).isPlaced()) {
                    ((Bomb) e).explode(game);
                }
            }
        }
    }

    /**
     * Interacts with the switch.
     * @param game
     * @param entity
     **/
    @Override
    public void interact(Game game, Entity entity) {
        entity.setPosition(this.getPosition());
    }    
}
