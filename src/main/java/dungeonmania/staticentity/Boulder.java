package dungeonmania.staticentity;

import dungeonmania.util.Direction;

import java.util.List;

import dungeonmania.Entity;
import dungeonmania.Game;
import dungeonmania.movingentity.MovingEntity;
import dungeonmania.player.Player;
import dungeonmania.util.Position;

/**
 * Boulder class.
 **/

public class Boulder extends StaticEntity{

    /**
     * Constructor for the Boulder class.
     * @param position
     **/
    public Boulder(Position position) {
        super(position, "boulder", true);
    }

    /**
     * Moves the boulder.
     * @param game
     * @param d
     **/
    private void moveBoulder(Game game, Direction d) {
        Position newPosition = this.getPosition().translateBy(d);
        // check the status of the new position
        List<Entity> entitiesOnPosition = game.getEntities(newPosition);
        if (entitiesOnPosition.stream().noneMatch(e -> (e instanceof MovingEntity) || (e instanceof Wall) ||(e instanceof Boulder)) || entitiesOnPosition.isEmpty()) {
            game.getPlayer().setPosition(this.getPosition());
            this.setPosition(newPosition);
            // trigger the switch if the new position is a floor switch
            for (Entity e : entitiesOnPosition) {
                 if (e instanceof FloorSwitch) {
                    ((FloorSwitch) e).triggerFloorSwitch(game);
                    return;
                 }
            }
        }   

    }

    /**
     * Interacts with the boulder.
     * @param game
     * @param e
     **/
    @Override
    public void interact(Game game, Entity e) {
        if(e instanceof Player) {
            moveBoulder(game, ((Player) e).getDirectionToMove());
        }
    }
    
}
 