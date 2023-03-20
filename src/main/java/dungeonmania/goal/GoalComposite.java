package dungeonmania.goal;

import java.util.ArrayList;
import java.util.stream.Collectors;

import dungeonmania.Game;

/**
 * GoalComposite class
 */

public class GoalComposite implements Goal {
    private String operator;
    private ArrayList<Goal> childGoals = new ArrayList<Goal>();

    /**
     * Constructor for GoalComposite class
     * @param operator
     */
    public GoalComposite(String operator) {
        this.operator = operator;
    }

    /**
     * AddGoal method
     * @param goal
     */
    public void addChild(Goal child) {
        childGoals.add(child);
    }
    
    /**
     * CheckCompleted method
     * @param game
     * @return boolean
     */
    public boolean checkCompleted(Game game) {
        switch (operator) {
            case "AND": {
                for (Goal goal : childGoals) {
                    if (!goal.checkCompleted(game)) {
                        return false;
                    }
                }
                return true;
            } 
            case "OR": {
                for (Goal goal : childGoals) {
                    if (goal.checkCompleted(game)) {
                        return true;
                    }
                }
                return false;
            }
        }    
        return false;
    }

    /**
     * ToString method
     * @param game
     * @return String
     */
    @Override
    public String toString(Game game) {
        if (checkCompleted(game)) return "";

        return childGoals
            .stream()
            .filter(goal -> !goal.checkCompleted(game))
            .map(goal -> goal.toString(game))
            .collect(Collectors.joining(" " + this.operator + " ", "(", ")"));
    }


}
