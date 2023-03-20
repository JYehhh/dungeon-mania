package dungeonmania.movingentity.MovementStrategy;

import java.util.List;

import dungeonmania.Entity;
import dungeonmania.Game;
import dungeonmania.movingentity.MovingEntity;
import dungeonmania.util.Position;

/**
 * This class is the base class for all movement strategies.
 **/

public abstract class MovementStrategy {
    MovingEntity movingEntity;

    /**
     * Constructor for the MovementStrategy class.
     * @param entity
     **/
    public MovementStrategy(MovingEntity entity) {
        this.movingEntity = entity;
    }

    /**
     * Getter for the moving entity.
     * @return MovingEntity
     **/
    public MovingEntity getEntity() {
        return movingEntity;
    }

    /**
     * Moves the entity.
     * @param game
     **/
    public void move(Game game) {
        Position newPosition = newPossiblePosition(game);
        
        List<Entity> entities = game.getEntities(newPosition);
        
        if (entities.size() == 0 || movingEntity.getPosition().equals(newPosition)) {
            movingEntity.setPosition(newPosition);
        } else { 
            entities.forEach(entity -> entity.interact(game, movingEntity));
        }
    }

    /**
     * Abstract method for getting a new possible position.
     * @param game
     * @return Position
     **/
    public abstract Position newPossiblePosition(Game game);
    
}
