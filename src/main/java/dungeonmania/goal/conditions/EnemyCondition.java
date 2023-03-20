package dungeonmania.goal.conditions;

import dungeonmania.Game;
import dungeonmania.goal.GoalLeaf;

/**
 * EnemyCondition class
 */

public class EnemyCondition extends GoalLeaf{

    private String goal;

    /**
     * Constructor for EnemyCondition class
     * @param target
     */
    public EnemyCondition(int target) {
        super(target);
        this.goal = "enemies";
    }

    /**
     * ToString method
     * @param game
     * @return String
     */
    @Override
    public String toString(Game game) {
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
        if (game.getBattlesSize() >= getTarget()) return true;
        else return false;
    }
}
