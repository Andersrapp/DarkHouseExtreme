package com.bam.darkhouseextreme.app.utilities;

import android.content.Context;
import android.content.res.Resources;

import com.bam.darkhouseextreme.app.helper.DatabaseHelper;
import com.bam.darkhouseextreme.app.model.Item;
import com.bam.darkhouseextreme.app.model.Player;

import java.util.List;

/**
 * Created by Chobii on 30/04/15.
 *
 * Handles the saving and loading of the currently playing character.
 *
 */
public class SaveUtility {

    public static Player player;
    public static DatabaseHelper helper;

    /**
     * initialize the DatabaseHelper.
     *
     * @param context - context for the application.
     */

    public static void setHelper(Context context) {
        helper = new DatabaseHelper(context);
    }

    /**
     * Save progress to the local database on the phone.
     *
     * @param x - the current X-axis of the character.
     * @param y - the current Y-axis of the character.
     * @param score - the current score of the character.
     *
     */

    public static void saveProgress(int x, int y, int score) {
        boolean success = helper.updateCharacter(String.valueOf(player.getId()), String.valueOf(x),
                String.valueOf(y), score,
                player.isRoom01(),
                player.isRoom01a(),
                player.isRoom02(),
                player.isRoom11(),
                player.isRoom11a(),
                player.isRoom12(),
                player.isRoom13(),
                player.isRoom13a(),
                player.isRoom20(),
                player.isRoom21(),
                player.isRoom22(),
                player.isRoom32(),
                player.isRoom33(),
                player.isDead());

        player.setMapXCoordinate(x);
        player.setMapYCoordinate(y);
    }

    /**
     * @return all characters from database.
     */

    public static List<Player> getAllCharacters() {
        return helper.getAllCharacters();
    }

    /**
     * Save item to character in database.
     * @param itemID - the corresponding id of the item in item table in database.
     */
    public static void saveItemToCharacter(String itemID) {
        Item item = helper.getOneItem(itemID);
        boolean added = helper.addItemToPlayerInventory(String.valueOf(player.getId()), String.valueOf(item.getId()));
        player.getPlayerItems().add(item);
    }

    /**
     * removes the item from character in databse.
     * @param itemID - the corresponding id of the item in item table in database.
     */
    public static void removeItemFromCharacter(String itemID) {
        Item item = helper.getOneItem(itemID);
        helper.removeObjectFromInventory(String.valueOf(player.getId()), String.valueOf(item.getId()));
        player.getPlayerItems().remove(item);
    }

    /**
     * Deletes the character from the database.
     * @param player - the character to remove from database.
     */
    public static void deleteCharacter(Player player) {
        helper.deleteCharacter(String.valueOf(player.getId()));
    }

    /**
     * Load the character into the static Player variable.
     * @param player
     */
    public static void loadCharacter(Player player) {
        SaveUtility.player = player;
    }

    /**
     * Creates the character, store it in the database and save it to the static variable.
     * @param name - name of the character.
     * @param resources - Our created resource.
     */
    public static void createCharacter(String name, Resources resources) {
        player = helper.createCharacter(name);
        helper.createAllItems(resources);
        player.setMapXCoordinate(0);
        player.setMapYCoordinate(2);
        player.setRoom01(false);
        player.setRoom01a(false);
        player.setRoom02(false);
        player.setRoom11(false);
        player.setRoom11a(false);
        player.setRoom12(false);
        player.setRoom13(false);
        player.setRoom13a(false);
        player.setRoom20(false);
        player.setRoom21(false);
        player.setRoom22(false);
        player.setRoom32(false);
        player.setRoom33(false);
        helper.updateCharacter(String.valueOf(
                        player.getId()),
                String.valueOf(0),
                String.valueOf(2),
                0,
                false,
                false,
                false,
                false,
                false,
                false,
                false,
                false,
                false,
                false,
                false,
                false,
                false,
                false
        );
    }

    /**
     * Get the current position and score of character.
     * @return int[3].
     */

    public static int[] loadStats() {
        return new int[]{player.getMapXCoordinate(), player.getMapYCoordinate(), player.getScore()};
    }

    /**
     * Checks if a item already exist in the character item database.
     * @param itemID - ID of item in database.
     * @return true/false.
     */
    public static boolean alreadyHasItem(String itemID) {

        Item item = helper.getOneItem(itemID);
        List<Item> inventory = helper.getListOfPlayerItems(player.getId());
        return inventory.contains(item);
    }

    /**
     * @return list of retrieved item for character.
     */
    public static List<Item> loadPlayersItems() {
        return player.getPlayerItems();
    }
}
