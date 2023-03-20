package dungeonmania.movingentity.MovementStrategy;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

import dungeonmania.Game;
import dungeonmania.movingentity.MovingEntity;
import dungeonmania.util.Position;

/**
 * Graph class.
 **/

public class Graph {
    private MovingEntity movingEntity;

    /**
     * Constructor for the Graph class.
     * @param entity
     **/
    public Graph(MovingEntity movingEntity) {
        this.movingEntity = movingEntity;
    }

    /**
     * Runs dijkstra's algorithm on the grid.
     * @param game
     * @param source
     * @return
     */
    public Map<Position, Position> dijkstra(Game game, Position source) {
        Map<Position, Double> dist = new HashMap<>();
        Map<Position, Position> prev = new HashMap<>();

        for (Position position: game.generateGrid()) {
            dist.put(position, Double.POSITIVE_INFINITY);
            prev.put(position, null);
        }
        dist.put(source, 0.0);

        Queue<Position> queue = new LinkedList<>();
        queue.add(source);

        while (!queue.isEmpty()) {
            Position vertex = queue.remove();
            for (Position position: game.getNeighbours(movingEntity, vertex)) {
                int cost = 1;
                if (game.getSwampTile(position) != null) {
                    cost = game.getSwampTile(position).getMovementFactor();
                }
                if (dist.get(position) > dist.get(vertex) + cost) {
                    dist.put(position, dist.get(vertex) + cost);
                    prev.put(position, vertex);
                    queue.add(position);
                }
            }
        }
        
        return prev;
    }
}
