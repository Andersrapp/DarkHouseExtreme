package com.bam.darkhouseextreme.app.utilities;

import android.content.Context;
import android.content.res.Resources;
import com.bam.darkhouseextreme.app.helper.DatabaseHelper;
import com.bam.darkhouseextreme.app.model.Item;
import com.bam.darkhouseextreme.app.model.Player;

import java.util.List;

/**
 * Created by Chobii on 30/04/15.
 */
public class SaveUtility {

    public static Player player;
    public static DatabaseHelper helper;

    public static void setHelper(Context context) {
        helper = new DatabaseHelper(context);
    }

    public static void saveProgress(int x, int y, int score) {
        boolean success = helper.updateCharacter(String.valueOf(player.getId()), String.valueOf(x), String.valueOf(y),
                score, player.isRoom01(), player.isRoom01a(), player.isRoom02(), player.isRoom11(), player.isRoom13a(), player.isRoom13b(),
                player.isRoom21(), player.isRoom22(), player.isRoom32(), player.isRoom33());
        player.setMapXCoordinate(x);
        player.setMapYCoordinate(y);
    }

    public static List<Player> getAllCharacters() {
        return helper.getAllCharacters();
    }

    public static void saveItemToCharacter(String itemID) {
        Item item = helper.getOneItem(itemID);
        boolean added = helper.addItemToPlayerInventory(String.valueOf(player.getId()), String.valueOf(item.getId()));
        player.getPlayerItems().add(item);
    }

    public static void deleteCharacter(Player player) {
        helper.deleteCharacter(String.valueOf(player.getId()));
    }

    public static void loadCharacter(Player player) {
        SaveUtility.player = player;
    }

    public static void createCharacter(String name, Resources resources) {
        player = helper.createCharacter(name);
        helper.createAllItems(resources);
        player.setMapXCoordinate(0);
        player.setMapYCoordinate(2);
        player.setRoom01(false);
        player.setRoom02(false);
        player.setRoom11(false);
        player.setRoom13a(false);
        player.setRoom13b(false);
        player.setRoom21(false);
        player.setRoom22(false);
        player.setRoom32(false);
        player.setRoom33(false);
        helper.updateCharacter(String.valueOf(player.getId()), String.valueOf(0), String.valueOf(2), 0, false, false, false, false, false, false, false, false, false, false);
    }

    public static int[] loadStats() {
        return new int[]{player.getMapXCoordinate(), player.getMapYCoordinate(), player.getScore()};
    }

    public static boolean alreadyHasItem(String itemID) {

        Item item = helper.getOneItem(itemID);
        List<Item> inventory = helper.getListOfPlayerItems(player.getId());
        return inventory.contains(item);
    }
}
