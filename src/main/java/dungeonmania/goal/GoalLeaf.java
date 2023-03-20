package dungeonmania.goal;

import dungeonmania.Game;

/**
 * GoalLeaf class
 */

public abstract class GoalLeaf implements Goal {
    private int target;

    /**
     * Constructor for GoalLeaf class
     * @param target
     */
    public GoalLeaf(int target) {
        this.target = target;
    }

    /**
     * Default constructor for GoalLeaf class
     */
    public GoalLeaf() {
    }
    
    /**
     * GetTarget method
     * @return Int
     */
    public int getTarget() {
        return target;
    }

    /**
     * CheckCompleted method
     * @param game
     * @return boolean
     */
    @Override
    public abstract boolean checkCompleted(Game game); 

    /**
     * ToString method
     * @param game
     * @return String
     */
    @Override
    public abstract String toString(Game game);


}
