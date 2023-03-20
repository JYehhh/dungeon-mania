package dungeonmania.staticentity;

import java.util.List;
import java.util.stream.Collectors;
import dungeonmania.Entity;
import dungeonmania.Game;
import dungeonmania.movingentity.Hydra;
import dungeonmania.movingentity.MovingEntity;
import dungeonmania.movingentity.Spider;
import dungeonmania.movingentity.ZombieToast;
import dungeonmania.util.Position;

/**
 * Portal class.
 **/

public class Portal extends StaticEntity {

    String colour;

    /**
     * Constructor for the Portal class.
     * @param position
     * @param colour
     **/
    public Portal(Position position, String colour) {
        super(position, "portal", true);
        this.colour = colour;
    }

    /**
     * @return the colour
     */
    public String getColour() {
        return colour;
    }

    /**
     * Find the correct portal to teleport the player to.
     * @param game
     * @return Portal
     **/
    public Portal findCoPortal(Game game) {
        List<Portal> portalList = game
        .getEntities()
        .stream()
        .filter(e -> (e instanceof Portal))
        .map(e -> (Portal) e)
        .collect(Collectors.toList());
        return portalList
        .stream()
        .filter(p -> p.getColour().equals(this.getColour()) && p.getId() != this.getId())
        .findFirst()
        .orElse(null);
    }

    /**
     * Teleports the entity to the correct portal.
     * @param game
     * @param me
     */
    public void teleport(Game game, MovingEntity me) {
        Position possibleDest = me.getPosition();
        // find the position of the corresponding portal (same colour)
        Portal coPortal = this.findCoPortal(game);
        if (coPortal != null) {
            List<Position> carAdjPos = coPortal.getPosition().getCardinallyAdjacentPositions();

            boolean canTeleport = false;
            for (Position possiblePosition : carAdjPos) {
                List<Entity> entityExist = game.getEntities(possiblePosition);
                canTeleport = !entityExist.stream().anyMatch(e -> e instanceof Wall || e instanceof Portal || e instanceof Boulder || (e instanceof Door && !((Door) e).isOpen()));

                if (canTeleport) {
                    possibleDest = possiblePosition;
                    break;
                }
            }
        }
        // teleport the entity
        me.setPosition(possibleDest);
    }
    
    /**
     * Interacts with the portal.
     * @param game
     * @param entity
     **/
    @Override
    public void interact(Game game, Entity entity) {
        if (!(entity instanceof MovingEntity)) return;
        if (entity instanceof Hydra || entity instanceof Spider || entity instanceof ZombieToast) {
            entity.setPosition(this.getPosition());
        } else {
            this.teleport(game, (MovingEntity) entity);
        }
    }    
}
