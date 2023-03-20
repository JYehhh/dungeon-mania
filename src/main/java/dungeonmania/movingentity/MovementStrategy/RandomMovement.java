package dungeonmania.movingentity.MovementStrategy;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import dungeonmania.Game;
import dungeonmania.movingentity.MovingEntity;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

/**
 * RandomMovement extends MovementStrategy.
 **/

public class RandomMovement extends MovementStrategy {

    private List<Direction> possibleDirections;

    /**
     * Constructor for the RandomMovement class.
     * @param entity
     **/
    public RandomMovement(MovingEntity entity) {
        super(entity);
        this.possibleDirections = Arrays.asList(
            Direction.UP,
            Direction.LEFT,
            Direction.DOWN,
            Direction.RIGHT,
            Direction.STATIC
        );

    }

    /**
     * Generates a new possible position.
     * @param game
     * @return Position
     **/
    @Override
    public Position newPossiblePosition(Game game) {
        
        Random rand = new Random();
        Direction newDirection = possibleDirections.get(rand.nextInt(possibleDirections.size()));
        Position newPosition = getEntity().getPosition().translateBy(newDirection);

        boolean canMove = game.getEntities(newPosition).stream().allMatch(e -> getEntity().canTraverse(e));
        if (canMove) {
            return newPosition;
        } 
        return getEntity().getPosition();
    }
}
