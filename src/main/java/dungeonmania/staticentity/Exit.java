package dungeonmania.staticentity;

import dungeonmania.Entity;
import dungeonmania.Game;
import dungeonmania.util.Position;

/**
 * Exit class.
 **/

public class Exit extends StaticEntity{

    /**
     * Constructor for the Exit class.
     * @param position
     **/
    public Exit(Position position) {
        super(position, "exit", false);
    }

    /**
     * Interacts with the exit.
     * @param game
     * @param enity
     **/
    @Override
    public void interact(Game game, Entity entity) {
            entity.setPosition(this.getPosition());
    }
}
