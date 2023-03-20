package dungeonmania.movingentity.MovementStrategy;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import dungeonmania.Game;
import dungeonmania.movingentity.MovingEntity;
import dungeonmania.player.Player;
import dungeonmania.util.Position;

/**
 * TowardsPlayer extends MovementStrategy.
 **/

public class TowardsPlayer extends MovementStrategy {

    /**
     * Constructor for the TowardsPlayer class.
     * @param entity
     **/
    public TowardsPlayer(MovingEntity entity) {
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
        MovingEntity movingEntity = getEntity();
        Position possibleNewPosition = getEntity().getPosition();

        if (game.getNeighbours(movingEntity, possibleNewPosition).contains(player.getPreviousPosition())) {
            return player.getPreviousPosition();
        }

        Graph graph = new Graph(getEntity());
        Map<Position, Position> previous = graph.dijkstra(game, movingEntity.getPosition());

        List<Position> path = new ArrayList<>();

        Position target = player.getPosition();
        path.add(target);

        while (previous.get(target) != null) {
            target = previous.get(target);
            path.add(target);
        }

        Collections.reverse(path);

        if (path.size() > 1) {
            return path.get(1);
        } else {
            return path.get(0);
        }
    }
}
