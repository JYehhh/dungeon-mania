package dungeonmania.movingentity.MovementStrategy;

import java.util.Arrays;
import java.util.List;

import dungeonmania.Game;
import dungeonmania.movingentity.MovingEntity;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

/**
 * CircularMovement extends MovementStrategy.
 **/

public class CircularMovement extends MovementStrategy {

    private List<Position> possiblePositions;
    boolean initialMovement;
    boolean clockwise;
    int nextMove;

    /**
     * Constructor for the CircularMovement class.
     * @param entity
     **/
    public CircularMovement(MovingEntity entity) {
        super(entity);
        this.possiblePositions = Arrays.asList(
            entity.getPosition().translateBy(1, -1),
            entity.getPosition().translateBy(1, 0),
            entity.getPosition().translateBy(1, 1),
            entity.getPosition().translateBy(0, 1),
            entity.getPosition().translateBy(-1, 1),
            entity.getPosition().translateBy(-1, 0),
            entity.getPosition().translateBy(-1, -1),
            entity.getPosition().translateBy(0, -1)
        );
        this.initialMovement = true;
        this.clockwise = true;
        this.nextMove = 0;
    }

    /**
     * Generates a new possible position.
     * @param game
     * @return Position
     **/
    public Position newPossiblePosition(Game game) {
        Position currentPosition = getEntity().getPosition();

        if (initialMovement) {
            Position newPosition = currentPosition.translateBy(Direction.UP);
            boolean canMove = game.getEntities(newPosition).stream().allMatch(e -> getEntity().canTraverse(e));
            if (canMove) {
                initialMovement = false;
                return newPosition;
            } else {
                clockwise = !clockwise;
                return currentPosition;
            }
        } else {
            Position newPosition = possiblePositions.get(nextMove);
            boolean canMove = game.getEntities(newPosition).stream().allMatch(e -> getEntity().canTraverse(e));
            if (canMove) {
                if (clockwise) {
                    nextMove++;
                } else {
                    nextMove--;
                }

                if (nextMove == possiblePositions.size()) {;
                    nextMove = 0;
                }
                
                if (nextMove == -1) {
                    nextMove = 7;
                }
                
                return newPosition;
            } else {
                clockwise = !clockwise;
                if (clockwise) {
                    nextMove = nextMove + 2;
                    if (nextMove >= possiblePositions.size()) {
                        nextMove = (nextMove % possiblePositions.size() + possiblePositions.size()) % possiblePositions.size();
                    }
                } else {
                    nextMove = nextMove - 2;
                    if (nextMove < 0) {
                        nextMove = (nextMove % possiblePositions.size() + possiblePositions.size()) % possiblePositions.size();
                    }
                }
                return currentPosition;
            }   
        }
    }
}
