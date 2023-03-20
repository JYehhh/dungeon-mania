package dungeonmania;

import java.io.IOException;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import dungeonmania.goal.Goal;
import dungeonmania.goal.GoalComposite;
import dungeonmania.goal.conditions.EnemyCondition;
import dungeonmania.goal.conditions.ExitCondition;
import dungeonmania.goal.conditions.SwitchCondition;
import dungeonmania.goal.conditions.TreasureCondition;
import dungeonmania.goal.operator.AndOperator;
import dungeonmania.goal.operator.OrOperator;
import dungeonmania.player.Player;
import dungeonmania.item.Arrow;
import dungeonmania.item.Bomb;
import dungeonmania.item.Item;
import dungeonmania.item.Key;
import dungeonmania.item.SunStone;
import dungeonmania.item.Sword;
import dungeonmania.item.TimeTurner;
import dungeonmania.item.Treasure;
import dungeonmania.item.Wood;
import dungeonmania.item.buildableentity.MidnightArmour;
import dungeonmania.item.buildableentity.Sceptre;
import dungeonmania.item.buildableentity.Shield;
import dungeonmania.item.buildableentity.Bow;
import dungeonmania.item.potion.InvincibilityPotion;
import dungeonmania.item.potion.InvisibilityPotion;
import dungeonmania.movingentity.Spider;
import dungeonmania.movingentity.ZombieToast;
import dungeonmania.movingentity.Assassin;
import dungeonmania.movingentity.Hydra;
import dungeonmania.movingentity.Mercenary;
import dungeonmania.staticentity.Boulder;
import dungeonmania.staticentity.Door;
import dungeonmania.staticentity.Exit;
import dungeonmania.staticentity.FloorSwitch;
import dungeonmania.staticentity.LightBulbOff;
import dungeonmania.staticentity.Portal;
import dungeonmania.staticentity.SwampTile;
import dungeonmania.staticentity.SwitchDoor;
import dungeonmania.staticentity.TimeTravellingPortal;
import dungeonmania.staticentity.Wall;
import dungeonmania.staticentity.Wire;
import dungeonmania.staticentity.ZombieToastSpawner;
import dungeonmania.util.FileLoader;
import dungeonmania.util.Position;

public final class JSONReader {
    public static JSONObject createJSONDungeon(String dungeonName) {
        String jsonDungeon;
        try {
            jsonDungeon = FileLoader.loadResourceFile("dungeons/" + dungeonName +".json");
            return new JSONObject(jsonDungeon);
        } catch (IOException e) {
            System.out.println("Invalid file");
        }
        return null;
    }

    public static JSONObject loadJSONDungeon(String dungeonName) {
        String jsonDungeon;
        try {
            jsonDungeon = FileLoader.loadResourceFile("saved_dungeons/" + dungeonName +".json");
            return new JSONObject(jsonDungeon);
        } catch (IOException e) {
            System.out.println("Invalid file");
        }
        return null;
    }

    public static JSONObject createJSONConfig(String configName) {
        String jsonConfig;
        try {
            jsonConfig = FileLoader.loadResourceFile("configs/" + configName +".json");
            return new JSONObject(jsonConfig);
        } catch (IOException e) {
            System.out.println("Invalid file");
        }
        return null;
    }

    public static JSONObject generateGoalJSON(JSONObject jsonDungeon) {
        if (jsonDungeon.getJSONObject("goal-condition") != null) {
            return jsonDungeon.getJSONObject("goal-condition");
        } else return null;
        
    }  

    public static Goal getGoal(JSONObject jsonGoal, Config config) {
        switch(jsonGoal.getString("goal")) {
            case "enemies":
                return new EnemyCondition(config.getEnemyGoal());
            case "treasure":
                return new TreasureCondition(config.getTreasureGoal());
            case "exit":
                return new ExitCondition();
            case "boulders":
                return new SwitchCondition();
            case "AND":
                GoalComposite andOperator = new AndOperator();
                JSONArray andSubgoals = jsonGoal.getJSONArray("subgoals");  
                for (int i = 0; i < andSubgoals.length(); i++) {
                    JSONObject subgoalJSON = andSubgoals.getJSONObject(i);
                    andOperator.addChild(getGoal(subgoalJSON, config));
                }
                return andOperator;
            case "OR":
                GoalComposite orOperator = new OrOperator();
                JSONArray orSubgoals = jsonGoal.getJSONArray("subgoals");
                for (int i = 0; i < orSubgoals.length(); i++) {
                    JSONObject subgoalJSON = orSubgoals.getJSONObject(i);
                    orOperator.addChild(getGoal(subgoalJSON, config));
                }
                return orOperator;
            default:
                return null;
        }
    }

    public static Goal generateGoal(String dungeonName, String configName) {
        JSONObject jsonDungeon = createJSONDungeon(dungeonName);
        JSONObject goalJSON = generateGoalJSON(jsonDungeon);
        return getGoal(goalJSON, generateConfig(configName));
    }

    public static Config generateConfig(String configName) {
        JSONObject jsonConfig = createJSONConfig(configName);
        Config config = new Config();
        config.setAllyAttack(jsonConfig.getInt("ally_attack"));
        config.setAllyDefence(jsonConfig.getInt("ally_defence"));
        config.setBribeRadius(jsonConfig.getInt("bribe_radius"));
        config.setBribeAmount(jsonConfig.getInt("bribe_amount"));
        config.setBombRadius(jsonConfig.getInt("bomb_radius"));
        config.setBowDurability(jsonConfig.getInt("bow_durability"));
        config.setPlayerHealth(jsonConfig.getInt("player_health"));
        config.setPlayerAttack(jsonConfig.getInt("player_attack"));
        config.setEnemyGoal(jsonConfig.getInt("enemy_goal"));
        config.setInvincibilityPotionDuration(jsonConfig.getInt("invincibility_potion_duration"));
        config.setInvisibilityPotionDuration(jsonConfig.getInt("invisibility_potion_duration"));
        config.setMercenaryAttack(jsonConfig.getInt("mercenary_attack"));
        config.setMercenaryHealth(jsonConfig.getInt("mercenary_health"));
        config.setSpiderAttack(jsonConfig.getInt("spider_attack"));
        config.setSpiderHealth(jsonConfig.getInt("spider_health"));
        config.setSpiderSpawnRate(jsonConfig.getInt("spider_spawn_rate"));
        config.setShieldDurability(jsonConfig.getInt("shield_durability"));
        config.setShieldDefence(jsonConfig.getInt("shield_defence"));
        config.setSwordAttack(jsonConfig.getInt("sword_attack"));
        config.setSwordDurability(jsonConfig.getInt("sword_durability"));
        config.setTreasureGoal(jsonConfig.getInt("treasure_goal"));
        config.setZombieAttack(jsonConfig.getInt("zombie_attack"));
        config.setZombieHealth(jsonConfig.getInt("zombie_health"));
        config.setZombieSpawnRate(jsonConfig.getInt("zombie_spawn_rate"));
        config.setAssassinAttack(jsonConfig.optInt("assassin_attack", 0));
        config.setAssassinBribeAmount(jsonConfig.optInt("assassin_bribe_amount", 0));
        config.setAssassinBribeFailRate(jsonConfig.optDouble("assassin_bribe_fail_rate", 0.0));
        config.setAssassinHealth(jsonConfig.optInt("assassin_health", 0));
        config.setAssassinReconRadius(jsonConfig.optInt("assassin_recon_radius", 0));
        config.setHydraAttack(jsonConfig.optInt("hydra_attack", 0));
        config.setHydraHealth(jsonConfig.optInt("hydra_health", 0));
        config.setHydraHealthIncreaseRate(jsonConfig.optDouble("hydra_health_increase_rate", 0));
        config.setHydraHealthIncreaseAmount(jsonConfig.optInt("hydra_health_increase_amount", 0));
        config.setMindControlDuration(jsonConfig.optInt("mind_control_duration", 0));
        config.setMidnightArmourAttack(jsonConfig.optInt("midnight_armour_attack", 0));
        config.setMidnightArmourDefence(jsonConfig.optInt("midnight_armour_defence", 0));
        return config;
    }

    public static ArrayList<Entity> generateEntities(String dungeonName, String configName)  {
        return generateEntities(dungeonName, generateConfig(configName));
    }

    public static ArrayList<Entity> generateEntities(String dungeonName, Config config) {
        ArrayList<Entity> entitiesList  = new ArrayList<Entity>();
        JSONObject jsonDungeon = createJSONDungeon(dungeonName);
        JSONArray entitiesArray = jsonDungeon.getJSONArray("entities");
        for (int i = 0; i < entitiesArray.length(); i++) {
            String type = entitiesArray.getJSONObject(i).getString("type");
            Position position = new Position(entitiesArray.getJSONObject(i).getInt("x"), entitiesArray.getJSONObject(i).getInt("y"));
            JSONObject object = entitiesArray.getJSONObject(i);
            if (type.equals("player")) entitiesList.add(new Player(position, config.getPlayerHealth(), config.getPlayerAttack()));
            else if (type.equals("wall")) entitiesList.add(new Wall(position));
            else if (type.equals("exit")) entitiesList.add(new Exit(position)); 
            else if (type.equals("boulder")) entitiesList.add(new Boulder(position));
            else if (type.equals("switch")) entitiesList.add(new FloorSwitch(position));
            else if (type.equals("door")) entitiesList.add(new Door(position, object.getInt("key"), false));
            else if (type.equals("portal")) entitiesList.add(new Portal(position, object.getString("colour")));
            else if (type.equals("zombie_toast_spawner")) entitiesList.add(new ZombieToastSpawner(position, config.getZombieSpawnRate()));
            else if (type.equals("spider")) entitiesList.add(new Spider(position, config.getSpiderHealth(), config.getSpiderAttack()));
            else if (type.equals("zombie_toast")) entitiesList.add(new ZombieToast(position, config.getZombieHealth(), config.getZombieAttack()));
            else if (type.equals("mercenary")) entitiesList.add(new Mercenary(position, config.getMercenaryHealth(), config.getMercenaryAttack()));
            else if (type.equals("assassin")) entitiesList.add(new Assassin(position, config.getAssassinHealth(), config.getAssassinAttack()));
            else if (type.equals("hydra")) entitiesList.add(new Hydra(position, config.getHydraHealth(), config.getHydraAttack()));
            else if (type.equals("treasure")) entitiesList.add(new Treasure(position));
            else if (type.equals("key")) entitiesList.add(new Key(position, object.getInt("key")));
            else if (type.equals("invisibility_potion")) entitiesList.add(new InvisibilityPotion(position, config.getInvincibilityPotionDuration()));
            else if (type.equals("invincibility_potion")) entitiesList.add(new InvincibilityPotion(position, config.getInvisibilityPotionDuration())); 
            else if (type.equals("wood")) entitiesList.add(new Wood(position));
            else if (type.equals("arrow")) entitiesList.add(new Arrow(position));
            else if (type.equals("bomb")) entitiesList.add(new Bomb(position, config.getBombRadius()));
            else if (type.equals("sword")) entitiesList.add(new Sword(position, config.getSwordAttack(), config.getSwordDurability()));
            else if (type.equals("sun_stone")) entitiesList.add(new SunStone(position));
            else if (type.equals("swamp_tile")) entitiesList.add(new SwampTile(position, object.getInt("movement_factor")));
            else if (type.equals("time_turner")) entitiesList.add(new TimeTurner(position));
            else if (type.equals("time_travelling_portal")) entitiesList.add(new TimeTravellingPortal(position));
            else if (type.equals("light_bulb_off")) entitiesList.add(new LightBulbOff(position));
            else if (type.equals("wire")) entitiesList.add(new Wire(position));
            else if (type.equals("switch_door")) entitiesList.add(new SwitchDoor(position));
        }
        return entitiesList;
    }

    public static ArrayList<Entity> loadEntities(String dungeonName, Config config) {
        ArrayList<Entity> entitiesList  = new ArrayList<Entity>();
        JSONObject jsonDungeon = loadJSONDungeon(dungeonName);
        JSONArray entitiesArray = jsonDungeon.getJSONArray("entities");
        for (int i = 0; i < entitiesArray.length(); i++) {
            String type = entitiesArray.getJSONObject(i).getString("type");
            Position position = new Position(entitiesArray.getJSONObject(i).getInt("x"), entitiesArray.getJSONObject(i).getInt("y"));
            JSONObject object = entitiesArray.getJSONObject(i);
            if (type.equals("player")) entitiesList.add(new Player(position, object.getDouble("health"), config.getPlayerAttack(), object.getString("potion-state"), config.getInvisibilityPotionDuration(), config.getInvincibilityPotionDuration(), object.getInt("potion-tick")));
            else if (type.equals("wall")) entitiesList.add(new Wall(position));
            else if (type.equals("exit")) entitiesList.add(new Exit(position)); 
            else if (type.equals("boulder")) entitiesList.add(new Boulder(position));
            else if (type.equals("switch")) entitiesList.add(new FloorSwitch(position));
            else if (type.equals("door")) entitiesList.add(new Door(position, object.getInt("key"), object.getBoolean("is-open")));
            else if (type.equals("portal")) entitiesList.add(new Portal(position, object.getString("colour")));
            else if (type.equals("zombie_toast_spawner")) entitiesList.add(new ZombieToastSpawner(position, config.getZombieSpawnRate()));
            else if (type.equals("spider")) entitiesList.add(new Spider(position, config.getSpiderHealth(), config.getSpiderHealth()));
            else if (type.equals("zombie_toast")) entitiesList.add(new ZombieToast(position, config.getZombieHealth(), config.getZombieHealth()));
            else if (type.equals("mercenary")) entitiesList.add(new Mercenary(position, config.getMercenaryHealth(), config.getMercenaryAttack(), object.getBoolean("is-bribed"), object.getBoolean("is-mind-controlled"), object.getInt("mind-controlled-counter")));
            else if (type.equals("assassin")) entitiesList.add(new Assassin(position, config.getAssassinAttack(), config.getAssassinHealth(), object.getBoolean("is-bribed"), object.getBoolean("is-mind-controlled"), object.getInt("mind-controlled-counter")));
            else if (type.equals("hydra")) entitiesList.add(new Hydra(position, config.getHydraAttack(), config.getHydraHealth()));
            else if (type.equals("treasure")) entitiesList.add(new Treasure(position));
            else if (type.equals("key")) entitiesList.add(new Key(position, object.getInt("key")));
            else if (type.equals("invisibility_potion")) entitiesList.add(new InvisibilityPotion(position, config.getInvincibilityPotionDuration()));
            else if (type.equals("invincibility_potion")) entitiesList.add(new InvincibilityPotion(position, config.getInvisibilityPotionDuration())); 
            else if (type.equals("wood")) entitiesList.add(new Wood(position));
            else if (type.equals("arrows")) entitiesList.add(new Arrow(position));
            else if (type.equals("bomb")) entitiesList.add(new Bomb(position, config.getBombRadius()));
            else if (type.equals("sword")) entitiesList.add(new Sword(position, config.getSwordAttack(), config.getSwordDurability()));
            else if (type.equals("sun_stone")) entitiesList.add(new SunStone(position));
            else if (type.equals("swamp_tile")) entitiesList.add(new SwampTile(position, object.getInt("movement_factor")));
            else if (type.equals("time_turner")) entitiesList.add(new TimeTurner(position));
            else if (type.equals("time_travelling_portal")) entitiesList.add(new TimeTravellingPortal(position));
            else if (type.equals("light_bulb_off")) entitiesList.add(new LightBulbOff(position));
            else if (type.equals("wire")) entitiesList.add(new Wire(position));
            else if (type.equals("switch_door")) entitiesList.add(new SwitchDoor(position));
        }
        return entitiesList;
    }

    public static ArrayList<Item> loadInventory(String dungeonName, Config config) {
        ArrayList<Item> inventoryList  = new ArrayList<Item>();
        JSONObject jsonDungeon = loadJSONDungeon(dungeonName);
        JSONArray jsonInventory = jsonDungeon.getJSONArray("inventory");
        for (int i = 0; i < jsonInventory.length(); i++) {
            String type = jsonInventory.getJSONObject(i).getString("type");
            Position position = new Position(jsonInventory.getJSONObject(i).getInt("x"), jsonInventory.getJSONObject(i).getInt("y"));
            JSONObject object = jsonInventory.getJSONObject(i);
            if (type.equals("arrows")) inventoryList.add(new Arrow(position));
            else if (type.equals("treasure")) inventoryList.add(new Treasure(position));
            else if (type.equals("wood")) inventoryList.add(new Wood(position));
            else if (type.equals("sun_stone")) inventoryList.add(new SunStone(position));
            else if (type.equals("time_turner")) inventoryList.add(new TimeTurner(position));
            else if (type.equals("key")) inventoryList.add(new Key(position, object.getInt("key")));
            else if (type.equals("sword")) inventoryList.add(new Sword(position, config.getSwordAttack(), object.getInt("durability")));
            else if (type.equals("bow")) inventoryList.add(new Bow(position, object.getInt("durability")));
            else if (type.equals("shield")) inventoryList.add(new Shield(position, config.getShieldDefence(), object.getInt("durability")));
            else if (type.equals("midnight_armour")) inventoryList.add(new MidnightArmour(position, config.getMidnightArmourAttack(), config.getMidnightArmourDefence()));
            else if (type.equals("sceptre")) inventoryList.add(new Sceptre(position));
        }
        return inventoryList;
    }

    public static Goal loadGoal(String dungeonName, String configName) {
        JSONObject jsonDungeon = loadJSONDungeon(dungeonName);
        JSONObject goalJSON = generateGoalJSON(jsonDungeon);
        return getGoal(goalJSON, generateConfig(configName));
    }

    public static int loadBattlesSize(String dungeonName) {
        JSONObject jsonDungeon = loadJSONDungeon(dungeonName);
        int battlesSize = jsonDungeon.getJSONObject("goal-completion").getInt("battles");
        return battlesSize;
    }

}
