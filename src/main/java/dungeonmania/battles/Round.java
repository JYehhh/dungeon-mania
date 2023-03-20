package dungeonmania.battles;

import java.util.ArrayList;
import java.util.List;

import dungeonmania.item.Item;
import dungeonmania.response.models.ItemResponse;
import dungeonmania.response.models.RoundResponse;

/**
 * Round class
 */

public class Round {
    private double deltaPlayerHealth;
    private double deltaEnemyHealth;
    private List<Item> weaponsUsed;

    /**
     * Constructor for Round class
     * @param deltaPlayerHealth
     * @param deltaEnemyHealth
     * @param weaponsUsed
     */
    public Round(double deltaPlayerHealth, double deltaEnemyHealth, List<Item> weaponsUsed) {
        this.deltaPlayerHealth = deltaPlayerHealth;
        this.deltaEnemyHealth = deltaEnemyHealth;
        this.weaponsUsed = weaponsUsed;
    }

    /**
     * Generates round response
     * @return RoundResponse
     */
    public RoundResponse getRoundResponse() {
        return new RoundResponse(deltaPlayerHealth, deltaEnemyHealth, getWeaponsUsedResponse());
    }

    /**
     * Generates weapons used response
     * @return List<ItemResponse>
     */
    public List<ItemResponse> getWeaponsUsedResponse() {
        List<ItemResponse> weaponResponses = new ArrayList<ItemResponse>();
        for (Item w : weaponsUsed) {
            weaponResponses.add(new ItemResponse(w.getId(), w.getType()));
        }
        return weaponResponses;
    }
}
