package dungeonmania.staticentity;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import dungeonmania.Entity;
import dungeonmania.Game;
import dungeonmania.movingentity.ZombieToast;
import dungeonmania.player.Player;
import dungeonmania.util.Position;

/**
 * ZombieToastSpawner class.
 */

public class ZombieToastSpawner extends StaticEntity{
    private int ticking;
    private int tickRate;

    /**
     * Constructor for the ZombieToastSpawner class.
     * @param position
     * @param tickRate
     */
    public ZombieToastSpawner(Position position, int tickRate) {
        super(position, "zombie_toast_spawner", true);
        this.ticking = 0;
        this.tickRate = tickRate;
    }

    /**
     * Interacts with the ZombieToastSpawner.
     * @param game
     * @param entity
     */
    @Override
    public void interact(Game game, Entity entity) {
        // the player can destroy a zombie spawner 
        // if they have a weapon and 
        // are cardinally adjacent to the spawner

        if (!(entity instanceof Player)) return;
        Player p = (Player) entity;
        
        List<Entity> carAdjEntities = game.getCardinallyAdjacentEntities(this.getPosition());
        
        if (carAdjEntities.contains(p) && p.hasWeapon()) {
            game.removeEntity(this);
        }
    }

    /**
     * Tick the ZombieToastSpawner.
     * @param game
     */
    @Override
    public void tick(Game game) {
        if (++ticking % tickRate != 0) return;
        List<Position> carAdjPos = this.getPosition().getCardinallyAdjacentPositions();

        // check if the cardinally adjacent postiions are available
        List<Position> availableCarAdjPos = carAdjPos
            .stream()
            .filter(pos -> game.getEntities(pos).isEmpty())
            .collect(Collectors.toList());
        
        if (availableCarAdjPos.isEmpty()) return;
            
        // spawner spawns zombie at a random position
        Random rand = new Random();
        Position spawningPosition = availableCarAdjPos.get(rand.nextInt(availableCarAdjPos.size()));
        game.addEntity(new ZombieToast(spawningPosition, game.getConfig().getZombieHealth(), game.getConfig().getZombieAttack()));
    }
}
