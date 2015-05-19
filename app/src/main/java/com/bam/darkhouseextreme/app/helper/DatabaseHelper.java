package com.bam.darkhouseextreme.app.helper;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.bam.darkhouseextreme.app.R;
import com.bam.darkhouseextreme.app.model.Item;
import com.bam.darkhouseextreme.app.model.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Anders on 2015-04-28.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "DarkHouse.db";
    private static final int DATABASE_VERSION = 2;
    private static final String PLAYER_TABLE_NAME = "Player";
    private static final String PLAYER_ID = "Id";
    private static final String PLAYER_NAME = "Name";
    private static final String PLAYER_MAP_X = "MapXCoordinate";
    private static final String PLAYER_MAP_Y = "MapYCoordinate";
    private static final String PLAYER_SCORE = "Score";
    private static final String PLAYER_ROOM01 = "Room01";
    private static final String PLAYER_ROOM01A = "Room01a";
    private static final String PLAYER_ROOM02 = "Room02";
    private static final String PLAYER_ROOM11 = "Room11";
    private static final String PLAYER_ROOM13A = "Room13a";
    private static final String PLAYER_ROOM13B = "Room13b";
    private static final String PLAYER_ROOM21 = "Room21";
    private static final String PLAYER_ROOM22 = "Room22";
    private static final String PLAYER_ROOM32 = "Room32";
    private static final String PLAYER_ROOM33 = "Room33";
    private static final String PLAYER_DEAD = "Dead";

    private static final String ITEM_TABLE_NAME = "Item";
    private static final String ITEM_ID = "Id";
    private static final String ITEM_NAME = "Name";
    private static final String ITEM_DESCRIPTION = "Description";

    private static final String PLAYER_ITEM_JUNCTION_TABLE_NAME = "Player_Item";
    private static final String PLAYER_ITEM_ID = "Id";
    private static final String JUNCTION_TABLE_PLAYER_ID = "Player_Id";
    private static final String JUNCTION_TABLE_ITEM_ID = "Item_Id";

    private SQLiteDatabase db;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS " + PLAYER_TABLE_NAME + " (" + PLAYER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                        + PLAYER_NAME + " TEXT, " + PLAYER_MAP_X + " INTEGER, " + PLAYER_MAP_Y + " INTEGER, " + PLAYER_SCORE + " INTEGER, "
                        + PLAYER_ROOM01 + " INTEGER," + PLAYER_ROOM01A + " INTEGER," + PLAYER_ROOM02 + " INTEGER, " + PLAYER_ROOM11 + " INTEGER, " + PLAYER_ROOM13A + " INTEGER, "
                        + PLAYER_ROOM13B + " INTEGER, " + PLAYER_ROOM21 + " INTEGER, " + PLAYER_ROOM22 + " INTEGER, " + PLAYER_ROOM32 + " INTEGER, "
                        + PLAYER_ROOM33 + " INTEGER," + PLAYER_DEAD + " INTEGER)"
        );
        db.execSQL("CREATE TABLE IF NOT EXISTS " + ITEM_TABLE_NAME + " (" + ITEM_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                        + ITEM_NAME + " TEXT, " + ITEM_DESCRIPTION + " TEXT)"
        );

        db.execSQL("CREATE TABLE IF NOT EXISTS " + PLAYER_ITEM_JUNCTION_TABLE_NAME + " (" + PLAYER_ITEM_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                        + JUNCTION_TABLE_PLAYER_ID + " INTEGER REFERENCES " + PLAYER_TABLE_NAME + ", " + JUNCTION_TABLE_ITEM_ID + " INTEGER REFERENCES " + ITEM_TABLE_NAME + ")"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + PLAYER_TABLE_NAME);
        onCreate(db);
    }

    public Player createCharacter(String name) {
        db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(PLAYER_NAME, name);
        long rowId = db.insert(PLAYER_TABLE_NAME, null, contentValues);

        db.close();

        if (rowId == -1) {
            return null;
        } else {
            return new Player(rowId, name);
        }
    }

    public Player getOneCharacter(String id) {
        db = this.getReadableDatabase();
        String[] selection = {id};
        Cursor cursor = db.rawQuery("SELECT * FROM " + PLAYER_TABLE_NAME + " WHERE " + PLAYER_ID + " = ?", selection);
        db.close();

        Player player = getListOfPlayers(cursor).get(0);
        return player;
    }

    public List<Player> getAllCharacters() {
        db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + PLAYER_TABLE_NAME, null);
        return getListOfPlayers(cursor);
    }

    public boolean updateCharacter(String id, String mapXCoordinate, String mapYCoordinate,
                                   int score, boolean room01, boolean room01a, boolean room02, boolean room11,
                                   boolean room13a, boolean room13b, boolean room21,
                                   boolean room22, boolean room32, boolean room33, boolean dead)
    {
        db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(PLAYER_MAP_X, mapXCoordinate);
        contentValues.put(PLAYER_MAP_Y, mapYCoordinate);
        contentValues.put(PLAYER_SCORE, score);
        contentValues.put(PLAYER_ROOM01, room01 ? 1 : 0);
        contentValues.put(PLAYER_ROOM01A, room01a ? 1 : 0);
        contentValues.put(PLAYER_ROOM02, room02 ? 1 : 0);
        contentValues.put(PLAYER_ROOM11, room11 ? 1 : 0);
        contentValues.put(PLAYER_ROOM13A, room13a ? 1 : 0);
        contentValues.put(PLAYER_ROOM13B, room13b ? 1 : 0);
        contentValues.put(PLAYER_ROOM21, room21 ? 1 : 0);
        contentValues.put(PLAYER_ROOM22, room22 ? 1 : 0);
        contentValues.put(PLAYER_ROOM32, room32 ? 1 : 0);
        contentValues.put(PLAYER_ROOM33, room33 ? 1 : 0);
        contentValues.put(PLAYER_DEAD, dead ? 1 : 0);
        String whereClause = PLAYER_ID + " = ?";
        String[] whereArgs = {id};

        return db.update(PLAYER_TABLE_NAME, contentValues, whereClause, whereArgs) > 0;
    }

    public boolean deleteCharacter(String id) {

        db = this.getWritableDatabase();
        String whereClause = PLAYER_ID + " = ?";
        String[] whereArgs = {id};
        if (db.delete(PLAYER_TABLE_NAME, whereClause, whereArgs) > 0) {
            db.delete(PLAYER_ITEM_JUNCTION_TABLE_NAME, whereClause, whereArgs);
            db.close();
            return true;
        } else return false;
    }

    public boolean addItemToPlayerInventory(String playerId, String itemId) {
        db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(JUNCTION_TABLE_PLAYER_ID, playerId);
        contentValues.put(JUNCTION_TABLE_ITEM_ID, itemId);
        long rowId = db.insert(PLAYER_ITEM_JUNCTION_TABLE_NAME, null, contentValues);
        db.close();

        return rowId != -1;
    }

    public Item createOneItem(String itemName, String itemDescription) {
        db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(ITEM_NAME, itemName);
        contentValues.put(ITEM_DESCRIPTION, itemDescription);
        long rowId = db.insert(ITEM_TABLE_NAME, null, contentValues);

        if (rowId != -1) {
            return new Item(rowId, itemName, itemDescription);
        } else {
            return null;
        }
    }

    public long createAllItems(Resources res) {
        db = this.getWritableDatabase();
        TypedArray items = res.obtainTypedArray(R.array.items);
        Cursor cursor = db.rawQuery("SELECT * FROM " + ITEM_TABLE_NAME, null);

        long rowId = 0;
        if (cursor.getCount() == 0) {

        for (int i = 0; i < items.length(); i += 2) {
            String itemName = items.getString(i);
            String itemDescription = items.getString(i + 1);
            ContentValues contentValues = new ContentValues();
            contentValues.put(ITEM_NAME, itemName);
            contentValues.put(ITEM_DESCRIPTION, itemDescription);
            rowId = db.insert(ITEM_TABLE_NAME, null, contentValues);
        }

        }
        return rowId;
    }

    public Item getOneItem(String id) {
        db = this.getReadableDatabase();
        String[] whereArgs = {id};
        Item item = new Item();
        Cursor cursor = db.rawQuery("SELECT * FROM " + ITEM_TABLE_NAME + " WHERE " + ITEM_ID + " = ?", whereArgs);
        while (cursor.moveToNext()) {
            item.setId(cursor.getInt(0));
            item.setName(cursor.getString(1));
            item.setDescription(cursor.getString(2));
        }
//        item.setId(1);
//        item.setName("Key");
//        item.setDescription("Can open doors");
        return item;
    }

//    public Item getOneItemFromCharacter(String itemId) {
//        db = this.getReadableDatabase();
//        String[] whereArgs = {itemId};
//
//
//
//    }


    private Cursor getAllItemsFromCharacter(long playerId) {
        db = this.getReadableDatabase();
        String[] selection = {String.valueOf(playerId)};
        Cursor cursor = db.rawQuery("SELECT * FROM " + ITEM_TABLE_NAME + " AS I JOIN " + PLAYER_ITEM_JUNCTION_TABLE_NAME + " AS PI ON I." + ITEM_ID + " = PI." + JUNCTION_TABLE_ITEM_ID + " WHERE PI." + JUNCTION_TABLE_PLAYER_ID + " = ?", selection);
        return cursor;
    }

    private List<Player> getListOfPlayers(Cursor cursor) {
        List<Player> players = new ArrayList<>();

        while (cursor.moveToNext()) {
            Player player = new Player();
            player.setId(cursor.getLong(0));
            player.setName(cursor.getString(1));
            player.setMapXCoordinate(cursor.getInt(2));
            player.setMapYCoordinate(cursor.getInt(3));
            player.setScore(cursor.getInt(4));
            player.setRoom01(cursor.getInt(5) == 1);
            player.setRoom02(cursor.getInt(6) == 1);
            player.setRoom11(cursor.getInt(7) == 1);
            player.setRoom13a(cursor.getInt(8) == 1);
            player.setRoom13b(cursor.getInt(9) == 1);
            player.setRoom21(cursor.getInt(10) == 1);
            player.setRoom22(cursor.getInt(11) == 1);
            player.setRoom32(cursor.getInt(12) == 1);
            player.setRoom33(cursor.getInt(13) == 1);
            player.setDead(cursor.getInt(15) == 1);
            player.setPlayerItems(getListOfPlayerItems(player.getId()));
            players.add(player);
        }
        return players;
    }

    public List<Item> getListOfPlayerItems(long playerId) {
        Cursor cursor = getAllItemsFromCharacter(playerId);
        List<Item> playerItems = new ArrayList<>();
        while (cursor.moveToNext()) {
            Item item = new Item();
            item.setId(cursor.getInt(0));
            item.setName(cursor.getString(1));
            item.setDescription(cursor.getString(2));
            playerItems.add(item);
        }
        return playerItems;
    }

    public boolean removeObjectFromInventory(String playerId, String itemId) {
        db = this.getWritableDatabase();

        String whereClause = " Id in " + "(SELECT Id FROM " + PLAYER_ITEM_JUNCTION_TABLE_NAME +
                " WHERE " + JUNCTION_TABLE_PLAYER_ID + " = ? AND " + JUNCTION_TABLE_ITEM_ID + " = ? LIMIT 1)";
        String[] whereArgs = {playerId, itemId};

        int i = db.delete(PLAYER_ITEM_JUNCTION_TABLE_NAME, whereClause, whereArgs);

        db.close();

        return i == -1;

//        Alternatively:
//        This is probably a worse way of doing the same thing as above but I'm keeping it 'til we know for sure that it works.
//        String whereClause = " WHERE " + PLAYER_ID + " = ? AND " + ITEM_ID + " = ? LIMIT 1";
//        String[] whereArgs = {playerId, itemId};
//        db.delete(PLAYER_ITEM_JUNCTION_TABLE_NAME, whereClause, whereArgs);
    }
}
