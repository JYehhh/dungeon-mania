package dungeonmania.movingentity.MovementStrategy;

import java.util.Map;

import dungeonmania.Game;
import dungeonmania.movingentity.MovingEntity;
import dungeonmania.player.Player;
import dungeonmania.util.Position;

/**
 * RunAway extends MovementStrategy.
 **/

public class RunAway extends MovementStrategy {

    /**
     * Constructor for the RunAway class.
     * @param entity
     **/
    public RunAway(MovingEntity entity) {
        super(entity);
    }

    /**
     * Generates a new possible position.
     * @param game
     * @return Position
     **/
    @Override
    public Position newPossiblePosition(Game game) {
        
        Player player = game.getPlayer();
        Position playerPosition = player.getPosition();
        MovingEntity movingEntity = getEntity();
        Position possibleNewPosition = getEntity().getPosition();

        Graph graph = new Graph(getEntity());

        int newPathLength = Integer.MIN_VALUE;

        for (Position possiblePosition : game.getNeighbours(movingEntity, possibleNewPosition)) {
            Map<Position, Position> previous = graph.dijkstra(game, possiblePosition);
            int pathLength = 0;
            Position pos = previous.get(playerPosition);
            while (pos != null && !movingEntity.getPosition().equals(pos)) {
                pos = previous.get(pos);
                pathLength++;
            }

            if (pathLength > newPathLength) {
                possibleNewPosition = possiblePosition;
                newPathLength = pathLength;
            }
        }

        return possibleNewPosition;
    }
}
