package com.bam.darkhouseextreme.app;

import android.content.res.Resources;
import android.content.res.TypedArray;
import android.test.AndroidTestCase;
import android.test.RenamingDelegatingContext;

import com.bam.darkhouseextreme.app.helper.DatabaseHelper;
import com.bam.darkhouseextreme.app.model.Item;
import com.bam.darkhouseextreme.app.model.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Chobii on 30/04/15.
 */


public class TestOfDatabase extends AndroidTestCase {

    private Player player;
    private Item item;
    private DatabaseHelper helper;
    private List<Item> items = new ArrayList<>();

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        RenamingDelegatingContext context = new RenamingDelegatingContext(getContext(), "_test");
        helper = new DatabaseHelper(context);
        player = helper.createCharacter("John");
        item = helper.createOneItem("Pointed Stick", "A pointed stick");
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    public void testCRUD() {
        // Test Add Character.
        String name = "Akana";
        Player player = helper.createCharacter(name);
        assertEquals(name, player.getName());
        // Test Update Character.
        boolean updated = helper.updateCharacter(String.valueOf(player.getId()),
                "0",
                "1",
                100
                , false
                , false
                , false
                , false
                , false
                , false
                , false
                , false
                , false
                , false
                , false
                , false
                , false
                , false);
        assertTrue(updated);
        // Test Read Character.
        List<Player> players = helper.getAllCharacters();
        assertTrue(players.size() != 0);
        // Test Delete Character.
        boolean deleted = helper.deleteCharacter(String.valueOf(player.getId()));
        assertTrue(deleted);
        // Test Add Item
        String itemName = "Shit on a stick";
        String itemDescription = "A crude stick that has been dipped in what appears to be shit";
        Item item = helper.createOneItem(itemName, itemDescription);
        assertEquals(itemName, item.getName());
    }

    public void testCreateAllItems() {
        Resources resources = getContext().getResources();
        long allItemsCreated = helper.createAllItems(resources);

        assertTrue(allItemsCreated > -1);
    }

    public void testAddItemToPlayerInventory() {
        boolean addedItemToPlayerInventory = helper.addItemToPlayerInventory(String.valueOf(player.getId()), String.valueOf(item.getId()));
        assertTrue(addedItemToPlayerInventory);
    }

    public void testGetListOfPlayerItems() {
        helper.addItemToPlayerInventory(String.valueOf(player.getId()), String.valueOf(item.getId()));
        items = helper.getListOfPlayerItems(player.getId());
        assertTrue(items.size() != 0);
    }
}
