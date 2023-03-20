package dungeonmania;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import dungeonmania.player.Player;
import dungeonmania.staticentity.Door;
import dungeonmania.staticentity.Portal;
import dungeonmania.staticentity.SwampTile;
import dungeonmania.util.FileLoader;
import dungeonmania.movingentity.Mercenary;
import dungeonmania.item.Item;
import dungeonmania.item.Key;
import dungeonmania.item.Weapon;
import dungeonmania.movingentity.Assassin;

public final class JSONWriter {

    private static FileWriter file;
    // Exports JSON file of saved game state 
    public static void saveJSON(String saveName, Player player, List<Entity> entities, ArrayList<Item> items, String configName, JSONObject goalJSON, int battlesSize) {

        JSONObject json = new JSONObject();
        JSONArray jsonEntities = saveEntities(entities);
        JSONObject jsonPlayer = savePlayer(player);
        JSONArray jsonInventory = saveInventory(items);
        JSONObject goalCompletion = new JSONObject();
        goalCompletion.put("battles", battlesSize);
        jsonEntities.put(jsonPlayer);
        json.put("entities", jsonEntities);       
        json.put("inventory", jsonInventory); 
        json.put("config", configName);
        json.put("goal-condition", goalJSON);
        json.put("goal-completion", goalCompletion);

        try {   
            // Gets bin path
            String savePath = FileLoader.getPathForNewFile("saved_dungeons", saveName);
            // Writes json file to bin path 
            file = new FileWriter(savePath + ".json");
            file.write(json.toString());
        } catch (NullPointerException | IOException e1) {
            e1.printStackTrace();
        } finally {
            try {
                file.flush();
                file.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static JSONObject savePlayer(Player player) {
        JSONObject jsonPlayer = new JSONObject();
        jsonPlayer.put("type", "player"); 
        jsonPlayer.put("x", player.getX());
        jsonPlayer.put("y", player.getY());
        jsonPlayer.put("potion-state", player.getPotionStateString());
        jsonPlayer.put("health", player.getHealth()); // Double check this
        jsonPlayer.put("potion-tick", player.getTimeUsed());

        return jsonPlayer;
    }

    public static JSONArray saveEntities(List<Entity> entities) {
        JSONArray jsonEntities = new JSONArray();
        for (Entity entity : entities) {
            JSONObject jsonEntity = new JSONObject();
            jsonEntity.put("type", entity.getType()); 
            jsonEntity.put("x", entity.getX());
            jsonEntity.put("y", entity.getY());
            switch(entity.getType()) {
                case "player":
                    continue;
                case "door":
                    jsonEntity.put("is-open", ((Door) entity).isOpen());
                    jsonEntity.put("key", ((Door) entity).getKey());
                    break;
                case "key":
                    jsonEntity.put("key", ((Key) entity).getKey());
                    break;
                case "mercenary":
                    jsonEntity.put("is-bribed", ((Mercenary) entity).isBribed());
                    jsonEntity.put("is-mind-controlled", ((Mercenary) entity).isMindControlled());
                    jsonEntity.put("mind-controlled-counter", ((Mercenary) entity).getMindControlledCounter());
                    break;
                case "assassin":
                    jsonEntity.put("is-bribed", ((Assassin) entity).isBribed());
                    jsonEntity.put("is-mind-controlled", ((Assassin) entity).isMindControlled());
                    jsonEntity.put("mind-controlled-counter", ((Assassin) entity).getMindControlledCounter());
                    break;
                case "portal":
                    jsonEntity.put("colour", ((Portal) entity).getColour());
                    break;
                case "swamp_tile":
                    jsonEntity.put("movement_factor", ((SwampTile) entity).getMovementFactor());
                    break;
                default:
            }
            jsonEntities.put(jsonEntity);
        }
        return jsonEntities;
    }

    public static JSONArray saveInventory(ArrayList<Item> items) {
        JSONArray jsonInventory = new JSONArray();
        for (Item item : items) {
            JSONObject jsonItem = new JSONObject();
            jsonItem.put("type", item.getType()); 
            jsonItem.put("x", item.getX());
            jsonItem.put("y", item.getY());
            if (item instanceof Weapon) jsonItem.put("durability",(((Weapon) item).getDurability()));
            else if (item instanceof Key) jsonItem.put("key", (((Key) item).getKey()));
            jsonInventory.put(jsonItem);
        }
        return jsonInventory;
    }

    public static void saveGeneratedDungeonJSON(ArrayList<Entity> entitiesList, String dungeonName) {
        JSONObject json = new JSONObject();
        JSONArray jsonEntities = saveRandomisedEntities(entitiesList);
        JSONObject jsonGoal = new JSONObject();
        jsonGoal.put("goal", "exit");
        json.put("entities", jsonEntities);
        json.put("goal-condition", jsonGoal);
        

        try {   
            // Gets bin path
            String savePath = FileLoader.getPathForNewFile("random_dungeons", dungeonName);
            // Writes json file to bin path 
            file = new FileWriter(savePath + ".json");
            file.write(json.toString());
        } catch (NullPointerException | IOException e1) {
            e1.printStackTrace();
        } finally {
            try {
                file.flush();
                file.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static JSONArray saveRandomisedEntities(ArrayList<Entity> entitiesList) {
        JSONArray jsonEntities = new JSONArray();
        for (Entity entity : entitiesList) {
            JSONObject jsonEntity = new JSONObject();
            jsonEntity.put("type", entity.getType()); 
            jsonEntity.put("x", entity.getX());
            jsonEntity.put("y", entity.getY());
            jsonEntities.put(jsonEntity);
        }
        return jsonEntities;
    }

    
}
