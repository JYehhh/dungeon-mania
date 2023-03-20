package dungeonmania.EntityBattleTests;

import dungeonmania.util.Direction;
import dungeonmania.util.Position;
import dungeonmania.Config;
import dungeonmania.DungeonManiaController;
import dungeonmania.Entity;
import dungeonmania.Game;
import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.item.Sword;
import dungeonmania.item.buildableentity.Bow;
import dungeonmania.item.buildableentity.MidnightArmour;
import dungeonmania.item.buildableentity.Sceptre;
import dungeonmania.item.buildableentity.Shield;
import dungeonmania.movingentity.Mercenary;
import dungeonmania.response.models.BattleResponse;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.response.models.RoundResponse;
import static dungeonmania.TestUtils.getValueFromConfigFile;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class BattleTests {

    // TEST BASIC
    @Test
    @DisplayName("Test mercenary initiates battle")
    public void testMercenaryInitiate() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_battleTest_basicMercenary", "c_gameTest_basic");
        Game game = dmc.getGame(res.getDungeonId());

        game.tickDirection(Direction.RIGHT); // battle should be starting here
        
        assertEquals(game.getBattles().size(), 1);
    }

    @Test
    @DisplayName("Test player initiates battle")
    public void testPlayerInitiates() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_battleTest_playerInitiates", "c_gameTest_basic");
        Game game = dmc.getGame(res.getDungeonId());

        game.tickDirection(Direction.RIGHT);
        game.tickDirection(Direction.RIGHT);
        game.tickDirection(Direction.RIGHT); 
        game.tickDirection(Direction.RIGHT); // battle should be starting here

        assertEquals(game.getBattles().size(), 1);
    }

    @Test
    @DisplayName("Test mercenary death")
    public void testMercenaryDeath() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_battleTest_basicMercenary", "c_battleTests_basicMercenaryMercenaryDies");
        Game game = dmc.getGame(res.getDungeonId());

        game.tickDirection(Direction.RIGHT); // battle should be starting here

        assertEquals(game.getEntitiesByType("mercenary").size(), 0);

    }

    @Test
    @DisplayName("Test player death")
    public void testPlayerDeath() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_battleTest_basicMercenary", "c_battleTests_basicMercenaryPlayerDies");
        Game game = dmc.getGame(res.getDungeonId());

        game.tickDirection(Direction.RIGHT); // battle should be starting here

        assertEquals(game.getEntitiesByType("player").size(), 0);

    }


    // TEST OTHER ENTITIES
    @Test
    @DisplayName("Test battle hydra")
    public void testBattleHydra() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_battleTest_basicHydra", "c_battleTests_playerGodMode");
        Game game = dmc.getGame(res.getDungeonId());

        game.tickDirection(Direction.RIGHT); 
        game.tickDirection(Direction.RIGHT); 

        assertEquals(game.getBattles().size(), 1);
        assertEquals(game.getEntitiesByType("mercenary").size(), 0);


    }

    @Test
    @DisplayName("Test battle assassin")
    public void testBattleAssassin() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_battleTest_basicAssassin", "c_battleTests_playerGodMode");
        Game game = dmc.getGame(res.getDungeonId());

        game.tickDirection(Direction.RIGHT); // battle should be starting here

        assertEquals(game.getBattles().size(), 1);
        assertEquals(game.getEntitiesByType("assassin").size(), 0);

    }

    @Test
    @DisplayName("Test battle Zombie Toast")
    public void testBattleZombieToast() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_battleTest_basicZombie", "c_battleTests_playerGodMode");
        Game game = dmc.getGame(res.getDungeonId());

        game.tickDirection(Direction.RIGHT); 
        game.tickDirection(Direction.RIGHT); // battle should be starting here

        assertEquals(game.getBattles().size(), 1);
        assertEquals(game.getEntitiesByType("zombie_toast").size(), 0);

    }

    @Test
    @DisplayName("Test no weapons calculation")
    public void testBaseAttack() {
        String configFile = "c_battleTests_tankyEntities";
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_battleTest_basicMercenary", configFile);
        Game game = dmc.getGame(res.getDungeonId());

        game.tickDirection(Direction.RIGHT); 
        DungeonResponse postBattleResponse = game.tickDirection(Direction.RIGHT); // battle should be starting here
        BattleResponse battle = postBattleResponse.getBattles().get(0);

        List<RoundResponse> rounds = battle.getRounds();
        double playerHealth = Double.parseDouble(getValueFromConfigFile("player_health", configFile));
        double enemyHealth = Double.parseDouble(getValueFromConfigFile("mercenary" + "_health", configFile));
        double playerAttack = Double.parseDouble(getValueFromConfigFile("player_attack", configFile));
        double enemyAttack = Double.parseDouble(getValueFromConfigFile("mercenary" + "_attack", configFile));

        for (RoundResponse round : rounds) {
            assertEquals(-(enemyAttack / 10), round.getDeltaCharacterHealth(), 0.001);
            assertEquals(-(playerAttack / 5), round.getDeltaEnemyHealth(), 0.001);
            enemyHealth += round.getDeltaEnemyHealth();
            playerHealth += round.getDeltaCharacterHealth();
        }

        assertTrue(enemyHealth <= 0);

    }

    @Test
    @DisplayName("Test all weapons calculation")
    public void testAllBuffedAttack() {
        String configFile = "c_battleTests_tankyEntities";
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_battleTest_basicMercenary", configFile);
        Game game = dmc.getGame(res.getDungeonId());

        // give the player all the weapons :)
        Config c = game.getConfig();
        game.getPlayer().addItem(new Bow(null, c.getBowDurability()));
        game.getPlayer().addItem(new MidnightArmour(null, c.getMidnightArmourAttack(), c.getMidnightArmourDefence()));
        game.getPlayer().addItem(new Shield(null, c.getShieldDefence(), c.getShieldDurability()));
        game.getPlayer().addItem(new Sword(null, c.getSwordAttack(), c.getSwordDurability()));

        game.tickDirection(Direction.RIGHT); 
        DungeonResponse postBattleResponse = game.tickDirection(Direction.RIGHT); // battle should be starting here
        BattleResponse battle = postBattleResponse.getBattles().get(0);

        List<RoundResponse> rounds = battle.getRounds();
        double enemyBaseAttack = Double.parseDouble(getValueFromConfigFile("mercenary" + "_attack", configFile));
        double enemyModAttack = enemyBaseAttack - c.getMidnightArmourDefence() - c.getShieldDefence();
        double playerBaseAttack = Double.parseDouble(getValueFromConfigFile("player_attack", configFile));
        double playerModAttack = (playerBaseAttack + c.getSwordAttack() + c.getMidnightArmourAttack()) * 2;

        for (RoundResponse round : rounds) {
            assertEquals(-(enemyModAttack / 10), round.getDeltaCharacterHealth(), 0.001);
            assertEquals(-(playerModAttack / 5), round.getDeltaEnemyHealth(), 0.001);
        }
    }

    @Test
    @DisplayName("Test allies calculation")
    public void testAlliesBuff() throws IllegalArgumentException, InvalidActionException {
        String configFile = "c_battleTests_tankyEntities";
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_battleTest_basicMercenary", configFile);
        Game game = dmc.getGame(res.getDungeonId());

        Config c = game.getConfig();

        game.getPlayer().addItem(new Sceptre(null));

        game.addEntity(new Mercenary(new Position(10, 10), c.getMercenaryHealth(), c.getMercenaryAttack()));
        Entity m = game.getEntitiesByType("mercenary").get(0);
        dmc.interact(m.getId());

        game.tickDirection(Direction.RIGHT); 
        DungeonResponse postBattleResponse = game.tickDirection(Direction.RIGHT); // battle should be starting here
        BattleResponse battle = postBattleResponse.getBattles().get(0);

        List<RoundResponse> rounds = battle.getRounds();
        double enemyBaseAttack = Double.parseDouble(getValueFromConfigFile("mercenary" + "_attack", configFile));
        double enemyModAttack = enemyBaseAttack - c.getAllyDefence();
        double playerBaseAttack = Double.parseDouble(getValueFromConfigFile("player_attack", configFile));
        double playerModAttack = playerBaseAttack + c.getAllyAttack();

        for (RoundResponse round : rounds) {
            assertEquals(-(enemyModAttack / 10), round.getDeltaCharacterHealth(), 0.001);
            assertEquals(-(playerModAttack / 5), round.getDeltaEnemyHealth(), 0.001);
        }
    }
}