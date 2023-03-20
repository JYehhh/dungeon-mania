package dungeonmania.staticentity;

import dungeonmania.Entity;
import dungeonmania.Game;
import dungeonmania.movingentity.Spider;
import dungeonmania.util.Position;

/*
 * Wall class.
 */

public class Wall extends StaticEntity{

    /*
     * Constructor for the Wall class.
     * @param position
     */
    public Wall(Position position) {
        super(position, "wall", false);
    }

    /*
     * Interacts with the wall.
     * @param game
     * @param enity
     */
    @Override
    public void interact(Game game, Entity entity) {
        if (entity instanceof Spider) {
            entity.setPosition(this.getPosition());
        }        
    }
}
