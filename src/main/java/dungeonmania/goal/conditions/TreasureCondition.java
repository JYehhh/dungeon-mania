package dungeonmania.goal.conditions;

import dungeonmania.Game;
import dungeonmania.goal.GoalLeaf;
import dungeonmania.player.Inventory;
import dungeonmania.player.Player;

/**
 * TreasureCondition class
 */

public class TreasureCondition extends GoalLeaf{

    private String goal;

    /**
     * Constructor for TreasureCondition class
     * @param target
     */
    public TreasureCondition(int target) {
        super(target);
        this.goal = "treasure";
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
        Inventory playerInventory = player.getInventory();
        if (playerInventory.getItemsOfType("treasure").size() + playerInventory.getItemsOfType("sun_stone").size()>= getTarget()) return true;
        else return false;
    }
}
