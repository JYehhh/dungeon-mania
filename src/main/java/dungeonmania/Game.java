package dungeonmania;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.json.JSONObject;

import dungeonmania.battles.Battle;
import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.goal.Goal;
import dungeonmania.item.Item;
import dungeonmania.item.buildableentity.BuildableEntity;
import dungeonmania.item.buildableentity.BuildableFactory;
import dungeonmania.movingentity.BribableEntity;
import dungeonmania.movingentity.MovingEntity;
import dungeonmania.movingentity.ObserverMovingEntities;
import dungeonmania.movingentity.Spider;
import dungeonmania.item.Consumable;
import dungeonmania.player.Player;
import dungeonmania.response.models.BattleResponse;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.response.models.ItemResponse;
import dungeonmania.staticentity.SwampTile;
import dungeonmania.staticentity.ZombieToastSpawner;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

/**
 * Game class.
 */

public class Game {
    private String dungeonId;
    private String dungeonName;
    private List<Entity> entities;
    private Goal goal;
    private Config config;
    private List<String> buildables;
    private List<Battle> battles;  
    private int tick = 1;
    private String configName;
    private JSONObject goalJSON;
    private int battlesSize;

    /**
     * Constructor for Game.
     * @param dungeonName
     * @param configName
     * @param entities
     * @param goal
     * @param config
     * @param goalJSON
     */
    public Game(String dungeonName, String configName, List<Entity> entities, Goal goal, Config config, JSONObject goalJSON) {
        this.dungeonId = UUID.randomUUID().toString();
        this.dungeonName = dungeonName;
        this.configName = configName;
        this.entities = entities;
        this.goal = goal;
        this.buildables = Arrays.asList("bow", "shield", "sceptre", "midnight_armour");
        this.battles = new ArrayList<Battle>();
        this.config = config;
        this.goalJSON = goalJSON;
    }

    /**
     * Ticks the game when player moves.
     * Moves all entities and checks for collisions.
     * @param direction
     * @return DungeonResponse
     */
    public DungeonResponse tickDirection(Direction direction) {
        // calls player.move()
        // has to make sure that adjacent entities are passable
        // at the end of the tick, check if the goal is completed
        // once a player moves, check every single entity that is on that tile
        // return the new dungeon response
        // entities are existing, just breaking at move???
        getPlayer().move(this, direction);
        if (!playerAlive()) return getGameOverResponse();
        List<Entity> movingEntities = entities.stream().filter(entity -> !(entity instanceof Player) && entity instanceof MovingEntity).collect(Collectors.toList());
        for (Entity e : movingEntities) {
            if (!(e instanceof Player) && e instanceof MovingEntity) e.tick(this);
        }

        if (!playerAlive()) return getGameOverResponse();

        Spider.spawnSpider(this);

        addObserver();
        goal.checkCompleted(this);
       
        tick++;
        // returns dungeon response 
        return getDungeonResponse();

    }

    /**
     * Checks if the player is alive.
     * @return boolean
     */
    public boolean playerAlive() {
        return getPlayer() != null;
    }

    /**
     * Ticks when player uses an item
     * 
     * @param itemUsedId
     * @return DungeonResponse
     */
    public DungeonResponse tickItem(String itemUsedId) throws IllegalArgumentException, InvalidActionException {
        // if the item isn't in inventory
        Item itemUsed = getInventoryItem(itemUsedId);
        if (itemUsed == null) throw new InvalidActionException("Item not in inventory!");

        // if it aint a bomb or potion, throw exception
        if (!itemUsed.getType().contains("bomb") && !itemUsed.getType().contains("potion")) {
            throw new IllegalArgumentException("Not a bomb or a potion");
        }
        
        // If it's a consumable (which it should be), consume it
        if (itemUsed instanceof Consumable) {
            ((Consumable) itemUsed).consume(this, getPlayer());
            getPlayer().notifyObservers(this);
        } 

        // ticking all other entities
        entities.stream().filter(entity ->  !(entity instanceof Player)).forEach(entity -> {
            entity.tick(this);
        });
        // returns dungeon response 
        return getDungeonResponse();
    }

    /**
     * Generates a new dungeon response.
     * @return DungeonResponse
     */
    public DungeonResponse getDungeonResponse() {
        List<EntityResponse> entityResponseList = new ArrayList<EntityResponse>();
        for (Entity entity : entities) {
            entityResponseList.add(entity.getEntityResponse());
        }
        List<BattleResponse> battleResponseList = new ArrayList<BattleResponse>();
        for (Battle battle : battles) {
            battleResponseList.add(battle.getBattleResponse());
        }
        // REMEMBER !!!!! create a list of battleresponses
        return new DungeonResponse(
            this.dungeonId, 
            this.dungeonName, 
            entityResponseList,
            getPlayer().getInventoryResponse(), 
            battleResponseList,
            BuildableFactory.getPossibleBuildables(getPlayer(), this, config), 
            goal.toString(this)
        );
    }

    /**
     * Generates a game over response.
     * @return DungeonResponse
     */
    private DungeonResponse getGameOverResponse() {
        List<EntityResponse> entityResponseList = new ArrayList<EntityResponse>();
        for (Entity entity : entities) {
            entityResponseList.add(entity.getEntityResponse());
        }
        List<BattleResponse> battleResponseList = new ArrayList<BattleResponse>();
        for (Battle battle : battles) {
            battleResponseList.add(battle.getBattleResponse());
        }

        return new DungeonResponse(
            this.dungeonId, 
            this.dungeonName, 
            entityResponseList,
            new ArrayList<ItemResponse>(), 
            battleResponseList,
            new ArrayList<String>(),
            goal.toString(this)
        );
    }

    /**
     * Interacts with the entity
     * @param entityId
     * @return DungeonResponse
     * @throws IllegalArgumentException
     * @throws InvalidActionException
     */
    public final DungeonResponse interact(String entityId) throws IllegalArgumentException, InvalidActionException {
        if (getEntity(entityId) == null) {
            throw new IllegalArgumentException("Entity not found");
        }

        Entity entity = getEntity(entityId);
        if (entity instanceof BribableEntity) {
            if (!((BribableEntity) entity).mindControl(this, getPlayer())) {
                ((BribableEntity) entity).bribe(this, getPlayer());
            }          
        } else if (entity instanceof MovingEntity) {
            ((MovingEntity) entity).interact(this, getPlayer());
        } else if (entity instanceof ZombieToastSpawner) {
            ((ZombieToastSpawner) entity).interact(this, getPlayer());
        }

        return getDungeonResponse();
    }

    /**
     * Adds an entity to the game.
     * @param entity
     */
    public final void addEntity(Entity entity) {
        entities.add(entity);
    }

    /**
     * Removes an entity from the game.
     * @param entity
     * @return boolean
     */
    public final boolean removeEntity(Entity entity) {
        return entities.remove(entity);
    }

    /**
     * Adds a battle to the game.
     * @param battle
     */
    public final void addBattle(Battle battle) {
        battles.add(battle);
    }

    /**
     * Gets the entities.
     * @return List<Entity>
     */
    public List<Entity> getEntities() {
        return entities;
    }

    /**
     * Get Entity by Position
     * @param position
     * @return List<Entity>
     */
    public List<Entity> getEntities(Position position) {
        return entities.stream().filter(entity -> entity.getPosition().equals(position)).collect(Collectors.toList());
    }

    /**
     * Gets Entity by type
     * @param type
     * @return List<Entity>
     */
    public List<Entity> getEntitiesByType(String entityType) {
        return entities.stream().filter(entity -> entity.getType().equals(entityType)).collect(Collectors.toList());
    }

    /**
     * Gets all adjacent entities to given position.
     * @param position
     * @return List<Entity>
     */
    public List<Entity> getAdjacentEntities(Position position) {
        return entities.stream().filter(e -> Position.isAdjacent(position, e.getPosition())).collect(Collectors.toList());
    }

    /**
     * Gets all cardinal adjacent entities to given position.
     * @param position
     * @return List<Entity>
     */
    public List<Entity> getCardinallyAdjacentEntities(Position position) {
        return entities.stream().filter(e -> Position.isCardinallyAdjacent(position, e.getPosition())).collect(Collectors.toList());
    }

    /**
     * Gets the goal.
     * @return Goal
     */
    public Goal getGoal() {
        return goal;
    }

    /**
     * Gets the config
     * @return Config
     */
    public Config getConfig() {
        return config;
    }

    /**
     * Gets the buildables
     * @return List<String>
     */
    public List<String> getBuildables() {
        return buildables;
    }

    /**
     * Gets the battles
     * @return List<Battle>
     */
    public List<Battle> getBattles() {
        return battles;
    }

    /**
     * Gets configName
     * @return String
     */
    public String getConfigName() {
        return configName;
    }

    /**
     * Gets goal JSON
     * @return JSONObject
     */
    public JSONObject getGoalJSON() {
        return goalJSON;
    }

    /**
     * Gets battle size.
     * @return int
     */
    public int getBattlesSize() {
        return battlesSize + battles.size();
    }

    /**
     * Sets the battles size.
     * @param battlesSize
     */
    public void setBattlesSize(int battlesSize) {
        this.battlesSize = battlesSize;
    } 

    /**
     * Gets the current tick.
     * @return
     */
    public int getTick() {
        return tick;
    }

    /**
     * Get entity by entityId
     * @param entityId
     * @return Entity
     */
    public Entity getEntity(String entityId) {
        return entities
            .stream()
            .filter(entity -> entity.getId().equals(entityId))
            .findFirst().orElse(null);
    }

    /**
     * Get item in inventory by itemId
     * @return Item
     */
    public Item getInventoryItem(String itemId) {
        return getPlayer().getItem(itemId);
    }

    /**
     * Gets the player
     * @return Player
     */
    public Player getPlayer() {
        return (Player) entities
            .stream()
            .filter(player -> player.getType().equals("player"))
            .findFirst().orElse(null);
    }

    /**
     * Gets all adjacent entities to given position and radius.
     * @param position
     * @param radius
     * @return ArrayList<Entity>
     */
    public ArrayList<Entity> getAdjacentEntities(Position position, int r) {
        ArrayList<Entity> entities = new ArrayList<Entity>();
        for (Position pos : this.getAdjacentPositions(position, r)) {
            entities.addAll(this.getEntitiesAtPosition(pos));
        }
        return entities;
    }


    /**
     * Gets all adjacent positions to give position and radius.
     * @param position
     * @param radius
     * @return ArrayList<Position>
     */
    public ArrayList<Position> getAdjacentPositions(Position position, int r) {
        int currX = position.getX();
        int currY = position.getY();
        ArrayList<Position> adjacentPositions = new ArrayList<>();
        for (int y = -r; y <= r; y++) {
            for (int x = -r; x <= r; x++) {
                adjacentPositions.add(new Position(currX + x,currY + y));
            }
        }

        // this does not include the source position.
        adjacentPositions.remove(position);
        return adjacentPositions;
    }

    /**
     * Get all entities at given position.
     * @param position
     * @return ArrayList<Entity>
     */
    public ArrayList<Entity> getEntitiesAtPosition(Position position) {
        ArrayList<Entity> ent = new ArrayList<Entity>();
        this.entities.forEach(e -> {
            if (e.getPosition().equals(position)) ent.add(e);
        });
        return ent;
    }

    /**
     * Adds observers to the player
     */
    public void addObserver() {
        entities.stream().filter(entity -> entity instanceof ObserverMovingEntities && !(entity instanceof Player)).forEach(entity -> {
            getPlayer().attach((ObserverMovingEntities) entity);
        });
    }

    /**
     * Builds a new entity.
     * @param buildable
     * @return DungeonResponse
     * @throws IllegalArgumentException
     */
    public DungeonResponse build(String buildable) throws IllegalArgumentException {
        // QUESTIONS
        // should i return an error if cannot be built
        // how will creating the entity first and then checking amount to build work with the frontend??? magin???
        // if the builable is not part of the buildable list, then will it break? since exception handling is in buildable fac
        BuildableEntity item = BuildableFactory.generateBuildable(buildable, config);
        item.build(getPlayer(), this);
        return getDungeonResponse();
    }

    /**
     * Get the number of entites by type
     * @param type
     * @return int
     */
    public int getNumberEntities(String type) {
        return entities
            .stream()
            .filter(item -> (item.getType().equals(type)))
            .collect(Collectors.toList())
            .size();
    }

    /**
     * Generates a grid to run dijsktra's algorithm on.
     * @return List<Position>
     */
    public List<Position> generateGrid() {
        List<Position> grid = new ArrayList<Position>();
        int minLeftXPostion = entities.stream().map(entity -> entity.getPosition()).min(Comparator.comparingInt(Position::getX)).get().getX() - 10;
        int minTopYPosition = entities.stream().map(entity -> entity.getPosition()).min(Comparator.comparingInt(Position::getY)).get().getY() - 10;

        int maxRightXPosition = entities.stream().map(entity -> entity.getPosition()).max(Comparator.comparingInt(Position::getX)).get().getX() + 10;
        int maxBottomYPosition = entities.stream().map(entity -> entity.getPosition()).max(Comparator.comparingInt(Position::getY)).get().getY() + 10;

        for (int y = minTopYPosition; y <= maxBottomYPosition; y++) {
            for (int x = minLeftXPostion; x <= maxRightXPosition; x++) {
                grid.add(new Position(x, y));
            }
        }

        return grid;
    }

    /**
     * Gets the neigbours of a moving entity based on position.
     * @param movingEntity
     * @param position
     * @return List<Position>
     */
    public List<Position> getNeighbours(MovingEntity movingEntity, Position position) {
        int x = position.getX();
        int y = position.getY();
        List<Position> positions = new ArrayList<Position>(
            Arrays.asList(
                new Position(x, y + 1),
                new Position(x - 1, y),
                new Position(x + 1, y),
                new Position(x, y - 1)
            )
        );

        List<Position> remove = new ArrayList<Position>();
        getCardinallyAdjacentEntities(position).forEach(e -> {
            if (!movingEntity.canTraverse(e)) {
                remove.add(e.getPosition());
            }
        });

        positions.removeAll(remove);

        positions.retainAll(generateGrid());

        return positions;
    }

    /**
     * Gets the swampTile.
     * @param position
     * @return SwampTile
     */
    public SwampTile getSwampTile(Position position) {
        return entities
            .stream()
            .filter(e -> e.getPosition().equals(position) && e instanceof SwampTile)
            .map(e -> (SwampTile) e)
            .findFirst()
            .orElse(null);
    }

    /**
     * Sets the player inventory
     * @param itemList
     */
    public void setPlayerInventory(ArrayList<Item> itemList) {
        getPlayer().setItemsList(itemList);
    }
}
