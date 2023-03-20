package dungeonmania.item.buildableentity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import dungeonmania.Config;
import dungeonmania.Game;
import dungeonmania.player.Player;

/**
 * BuildableFactory class.
 */

public final class BuildableFactory {
    private static final List<String> buildables = Arrays.asList("bow", "shield", "midnight_armour", "sceptre");

    /**
     * Getter for the buildables.
     * @return buildables
     */
    public static List<String> getBuildables() {
        return buildables;
    }

    /**
     * Generates a buildable entity.
     * @param type
     * @param config
     * @return BuildableEntity
     */
    public static BuildableEntity generateBuildable(String type, Config config) {
        if (!buildables.contains(type)) throw new IllegalArgumentException();
        if (type.equals("bow")) {
            return new Bow(null, config.getBowDurability());
        } else if (type.equals("shield")) {
            return new Shield(null, config.getShieldDefence(), config.getShieldDurability());
        } else if (type.equals("midnight_armour")) {
            return new MidnightArmour(null, config.getMidnightArmourAttack(), config.getMidnightArmourDefence());
        } else if (type.equals("sceptre")) {
            return new Sceptre(null);
        }

        return null;
    }

    /**
     * Checks if the buildable is valid
     * @param type
     * @return boolean
     */
    public static boolean isValidBuildable(String type) {
        if (!buildables.contains(type)) return false;
        return true;
    }

    /**
     * Generartes a list of possible buildable entities.
     * @param player
     * @param game
     * @param config
     * @return List<String>
     */
    public static List<String> getPossibleBuildables(Player player, Game game, Config config) {
        List<String> possibleBuildables = new ArrayList<String>();
        for (String b : buildables) {
            if (generateBuildable(b, config).isBuildable(player, game)) possibleBuildables.add(b);
        }
        return possibleBuildables;
    }
}
