package dungeonmania.goal.conditions;

import java.util.ArrayList;
import java.util.List;

import dungeonmania.Entity;
import dungeonmania.Game;
import dungeonmania.goal.GoalLeaf;

/**
 * SwitchCondition class
 */

public class SwitchCondition extends GoalLeaf{

    private String goal;

    /**
     * Constructor for SwitchCondition class
     */
    public SwitchCondition() {
        super();
        this.goal = "boulders";
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

    
    @Override
    public boolean checkCompleted(Game game) {
        // Gets all switches in the dungeon, then returns true if there are boulders at the same position of all switches, otherwise returns false
        List<Entity> switchList = game.getEntitiesByType("switch");
        for (Entity currentSwitch : switchList) {
            ArrayList<Entity> currentEntities = game.getEntitiesAtPosition(currentSwitch.getPosition());
            boolean hasBoulder = currentEntities.stream().anyMatch(currentEntity -> currentEntity.getType() == "boulder");
            if (hasBoulder == true) continue;
            else return false;
        }
        return true;
    }

    
}
