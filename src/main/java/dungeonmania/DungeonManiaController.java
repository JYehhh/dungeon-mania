package dungeonmania;

import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.goal.Goal;
import dungeonmania.goal.conditions.ExitCondition;
import dungeonmania.item.Item;
import dungeonmania.player.Inventory;
import dungeonmania.player.Player;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.staticentity.Exit;
import dungeonmania.util.Direction;
import dungeonmania.util.FileLoader;
import dungeonmania.util.Position;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

/**
 * DungeonManiaController class.
 */

public class DungeonManiaController {

    private Game currentGame;
    private ArrayList<String> gamesList = new ArrayList<String>();
    
    /**
     * Gets the current skin.
     */
    public String getSkin() {
        return "default";
    }

    /**
     * Gets the current localisation.
     */
    public String getLocalisation() {
        return "en_US";
    }

    /**
     * /dungeons
     */
    public static List<String> dungeons() {
        return FileLoader.listFileNamesInResourceDirectory("dungeons");
    }

    /**
     * /configs
     */
    public static List<String> configs() {
        return FileLoader.listFileNamesInResourceDirectory("configs");
    }

    /**
     * /game/new
     */
    public DungeonResponse newGame(String dungeonName, String configName) throws IllegalArgumentException {
        // this will check if dungeonName and configName are correct
        if (!dungeons().contains(dungeonName)) throw new IllegalArgumentException(dungeonName);
        if (!configs().contains(configName)) throw new IllegalArgumentException(configName);
        // and then construct the game
        // get config 
        Config gameConfig = generateConfig(configName);
        // get entities
        ArrayList<Entity> gameEntities = generateEntities(dungeonName, configName);
        // get goal
        Goal gameGoal = generateGoal(dungeonName, configName);

        JSONObject goalJSON = JSONReader.generateGoalJSON(JSONReader.createJSONDungeon(dungeonName));

        this.currentGame = new Game(dungeonName, configName, gameEntities, gameGoal, gameConfig, goalJSON);
        
        return getDungeonResponseModel();
    }

    /**
     * /game/dungeonResponseModel
     */
    public DungeonResponse getDungeonResponseModel() {
        return this.currentGame.getDungeonResponse();
    }

    /**
     * /game/tick/item
     */
    public DungeonResponse tick(String itemUsedId) throws IllegalArgumentException, InvalidActionException {
        return this.currentGame.tickItem(itemUsedId);
    }

    /**
     * /game/tick/movement
     */
    public DungeonResponse tick(Direction movementDirection) {
        return this.currentGame.tickDirection(movementDirection);
    }

    /**
     * /game/build
     */
    public DungeonResponse build(String buildable) throws IllegalArgumentException, InvalidActionException {
        return currentGame.build(buildable);
    }

    /**
     * /game/interact
     */
    public DungeonResponse interact(String entityId) throws IllegalArgumentException, InvalidActionException {
        return currentGame.interact(entityId);
    }

    /**
     * /game/save
     */
    public DungeonResponse saveGame(String name) {
        Player player = currentGame.getPlayer();
        Inventory inventory = player.getInventory();
        int battlesSize = currentGame.getBattlesSize();
        JSONWriter.saveJSON(name, currentGame.getPlayer(), currentGame.getEntities(), inventory.getItems(), currentGame.getConfigName(), currentGame.getGoalJSON(), battlesSize);
        gamesList.add(name);
        return getDungeonResponseModel();
    }

    /**
     * /game/load
     */ 
    public DungeonResponse loadGame(String name) throws IllegalArgumentException {
        // reads jsonFile and creates new instance of game
        // makes currentGame that instance of game
        if (!gamesList.contains(name)) throw new IllegalArgumentException(name);

        // Read json load file
        JSONObject dungeonJSON = loadJSON(name);

        String configName = dungeonJSON.getString("config");
        
        Config gameConfig = generateConfig(configName);

        ArrayList<Entity> gameEntities = JSONReader.loadEntities(name, gameConfig);

        Goal gameGoal = JSONReader.loadGoal(name, configName);

        JSONObject goalJSON = JSONReader.generateGoalJSON(JSONReader.loadJSONDungeon(name));

        ArrayList<Item> inventoryList = JSONReader.loadInventory(name, gameConfig);

        this.currentGame = new Game(name, configName, gameEntities, gameGoal, gameConfig, goalJSON);
        currentGame.setPlayerInventory(inventoryList);
        currentGame.setBattlesSize(JSONReader.loadBattlesSize(name));
        return getDungeonResponseModel();
    }

    /**
     * /games/all
     */
    public List<String> allGames() {
        return gamesList;
    }

    public DungeonResponse generateDungeon(int xStart, int yStart, int xEnd, int yEnd, String configName) throws IllegalArgumentException{
        if (!configs().contains(configName)) throw new IllegalArgumentException(configName);
        String dungeonName = "Randomised-maze";
        Config gameConfig = generateConfig(configName);
        // get entities
        DungeonGenerator newDungeon = new DungeonGenerator(xStart, yStart, xEnd, yEnd);
        ArrayList<Entity> gameEntities = newDungeon.generateEntities(); // generate entities 
        gameEntities.add(new Player(new Position(xStart, yStart), gameConfig.getPlayerHealth(), gameConfig.getPlayerAttack()));
        gameEntities.add(new Exit(new Position(xEnd, yEnd)));

        // Writes the json file into the random_dungeons folder
        // JSONWriter.saveGeneratedDungeonJSON(gameEntities, dungeonName);
        Goal gameGoal = new ExitCondition();

        JSONObject goalJSON = new JSONObject();
        goalJSON.put("goal", "exit");

        currentGame = new Game(dungeonName, configName, gameEntities, gameGoal, gameConfig, goalJSON);

        return getDungeonResponseModel();
    }

    // HELPER FUNCTIONS

    /**
     * Generates the Entities of the game.
     * @param dungeonName
     * @param configName
     * @return ArrayList<Entity>
     */
    public static ArrayList<Entity> generateEntities(String dungeonName, String configName) {
        return JSONReader.generateEntities(dungeonName, configName);
    }

    /**
     * Generates the config of the game.
     * @param configName
     * @return Config
     */
    public static Config generateConfig(String configName) {
        return JSONReader.generateConfig(configName);
    }

    /**
     * Generates the goal of the game.
     * @param dungeonName
     * @param configName
     * @return Goal
     */
    public static Goal generateGoal(String dungeonName, String configName) {
        return JSONReader.generateGoal(dungeonName, configName);
    }

    /**
     * Gets the current game
     * @param dungeonId
     * @return Game
     */
    public Game getGame(String dungeonId) {
        return currentGame;
    }

    /**
     * Loads the json file
     * @param name
     * @return JSONObject
     */
    public JSONObject loadJSON(String name) {
        String dungeonFile;
        try {
            dungeonFile = FileLoader.loadResourceFile("saved_dungeons/" + name +".json");
            return new JSONObject(dungeonFile);
        } catch (IOException e) {
            System.out.println("Invalid file");
        }
        return null;
    }
}

