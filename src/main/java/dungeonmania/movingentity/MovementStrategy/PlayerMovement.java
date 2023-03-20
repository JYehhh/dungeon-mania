package dungeonmania.movingentity.MovementStrategy;

import dungeonmania.Game;
import dungeonmania.movingentity.MovingEntity;
import dungeonmania.player.Player;
import dungeonmania.util.Position;

/**
 * PlayerMovement extends MovementStrategy.
 */

public class PlayerMovement extends MovementStrategy {
    
    /**
     * Constructor for the PlayerMovement class.
     * @param entity
     */
    public PlayerMovement(MovingEntity entity) {
        super(entity);
    }

    /**
     * Generates a new possible position.
     * @param game
     * @return Position
     */
    @Override
    public Position newPossiblePosition(Game game) {
        Player player = (Player) getEntity();
        Position newPosition = player.getPosition().translateBy(player.getDirectionToMove());

        boolean canMove = game.getEntities(newPosition).stream().allMatch(e -> getEntity().canTraverse(e));
        if (canMove) {
            return newPosition;
        }

        return player.getPosition();
    }
}   
