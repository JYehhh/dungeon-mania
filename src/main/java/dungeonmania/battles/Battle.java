package dungeonmania.battles;

import java.util.ArrayList;
import java.util.List;

import dungeonmania.Game;
import dungeonmania.movingentity.MovingEntity;
import dungeonmania.player.Player;
import dungeonmania.response.models.BattleResponse;
import dungeonmania.response.models.RoundResponse;

/*
 * Battle class
 */

public class Battle {
    
    private Player player;
    private MovingEntity enemy;
    private ArrayList<Round> rounds;
    private String enemyName;
    private double initialPlayerHealth;
    private double initialEnemyHealth;

    /**
     * Constructor for Battle class
     * @param player
     * @param enemy
     */
    public Battle(Player player, MovingEntity enemy) {
        this.player = player;
        this.enemy = enemy;
        this.enemyName = enemy.getClass().getSimpleName().toLowerCase();
        this.initialPlayerHealth = player.getHealth();
        this.initialEnemyHealth = enemy.getHealth();
        this.rounds = new ArrayList<Round>();
    }

    /**
     * Standard Battle method
     * @param game
     */
    public void standardBattle(Game game) {

        // BattleResponse battleData = new BattleResponse(enemy.getClass().getSimpleName().toLowerCase(), );
        while (!battleEnded()) {
            // Calculate Player damage 
            // Calculate Enemy damage
            // adjust Player Health and Enemy Health 
            // Record round 
            // Repeat loop until either enemy health <= 0 || player health <= 0
            // if enemy health <= 0 : remove entity 
                // if player health <= 0 : game over 
            double deltaEnemyHealth = player.getBuffedAttack(game)/ 5; 
            double rawDeltaPlayerHealth = enemy.getAttackDamage() - player.getBuffedDefense(game);
            if (rawDeltaPlayerHealth <= 0) rawDeltaPlayerHealth = 0;
            double deltaPlayerHealth = rawDeltaPlayerHealth / 10; 
            player.reduceHealthDamageTaken(game, deltaPlayerHealth);
            enemy.reduceHealthDamageTaken(game, deltaEnemyHealth);
            rounds.add(new Round(-deltaPlayerHealth, -deltaEnemyHealth, player.getWeaponsUsed())); 
        }
        // return battle response 
        if (player.getHealth() <= 0) playerLost();
        if (enemy.getHealth() <= 0) enemyLost();
    }

    /**
     * Invincible Battle method
     * @param game
     */
    public void invincibleBattle(Game game) {
    
        rounds.add(new Round(0, enemy.getHealth(), player.getWeaponsUsed()));
        enemy.reduceHealthDamageTaken(game, enemy.getHealth());
        enemyLost();
    }

    /**
     * Checks Battle ended method
     * @return boolean
     */
    public boolean battleEnded() {
        return (player.getHealth() <= 0) || (enemy.getHealth() <= 0);
    }

    /**
     * Checks Player lost method
     * @return boolean
     */
    public boolean playerLost() {
        return player.getHealth() <= 0; 
    }

    /**
     * Checks Enemy lost method
     * @return boolean
     */
    public boolean enemyLost() {
        return enemy.getHealth() <= 0;
    }
    
    /**
     * Gets battle response method
     * @return BattleResponse
     */
    public BattleResponse getBattleResponse() {
        List<RoundResponse> roundResponses = new ArrayList<RoundResponse>();
        for (Round r : rounds) {
            roundResponses.add(r.getRoundResponse());
        }
        return new BattleResponse(enemyName, roundResponses, initialPlayerHealth, initialEnemyHealth);
    }
}
    
