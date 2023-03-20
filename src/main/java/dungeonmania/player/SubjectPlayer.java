package dungeonmania.player;

import dungeonmania.Game;
import dungeonmania.movingentity.ObserverMovingEntities;

/**
 * SubjectPlayer class.
 */

public interface SubjectPlayer {

    /**
     * Adds an observer to the list of observers.
     * @param observer
     */
    public void attach(ObserverMovingEntities entity);

    /**
     * Removes an observer from the list of observers.
     * @param observer
     */
    public void detach(ObserverMovingEntities entity);

    /**
     * Notifies all observers.
     */
    public void notifyObservers(Game game);
}
