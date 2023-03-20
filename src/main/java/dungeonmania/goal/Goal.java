package dungeonmania.goal;

import dungeonmania.Game;

/**
 * Goal class
 */

public interface Goal {


    /**
     * CheckCompleted method
     * @param game
     * @return boolean
     */
    public abstract boolean checkCompleted(Game game);

    /**
     * ToString method
     * @param game
     * @return String
     */
    public abstract String toString(Game game);
}
