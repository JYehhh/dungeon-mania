package dungeonmania.goal.conditions;

import java.util.List;

import dungeonmania.Entity;
import dungeonmania.Game;
import dungeonmania.goal.GoalLeaf;
import dungeonmania.player.Player;

/**
 * ExitCondition class
 */

public class ExitCondition extends GoalLeaf{

    private String goal;

    /**
     * Constructor for ExitCondition class
     */
    public ExitCondition() {
        super();
        this.goal = "exit";
    }

    /**
     * ToString method
     * @param game
     * @return String
     */
    @Override
    public String toString(Game game) {
        if (!game.playerAlive()) return goal;
        if (checkCompleted(game)) return "";
        return ":" + goal;
    }

    /**
     * CheckCompleted method
     * @param game
     * @return boolean
     */
    @Override
    public boolean checkCompleted(Game game) {
        Player player = game.getPlayer();
        List<Entity> currentEntities = game.getEntities(player.getPosition());
        return currentEntities.stream().anyMatch(currentEntity -> currentEntity.getType() == "exit");
    }
}
