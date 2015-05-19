package com.bam.darkhouseextreme.app.utilities;

import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Chobii on 28/04/15.
 */
public class Utilities {

    public static Map<String, List<Button>> buttonsForRooms = new HashMap<>();
    public static Context context;
    public static boolean room11;
    public static boolean room01;
    public static boolean room01a;
    public static boolean room22;
    public static boolean room32;
    public static boolean room02;
    public static boolean room33;
    public static boolean room21;
    public static boolean room12;
    public static boolean room13;
    public static boolean room13a;

    /**
     * For each View in a ViewGroup, send the View to @code{setFontForView}.
     *
     * @param layout - The ViewGroup sent by @code{setFontForView}
     * @param fonts - The font to be set.
     */

    public static void setFontsForLayout(ViewGroup layout, Typeface fonts) {

        if (fonts != null && layout != null) {
            try {
                for (int i = 0; i < layout.getChildCount(); ++i) {
                    setFontForView(layout.getChildAt(i), fonts);
                }
            } catch (Exception e) {
            }
        }
    }

    /**
     * Sets the font of an object if it's a instance of a TextView.
     * Or if ViewGroup, send to @code{setFontsForLayout}.
     *
     * @param view - The View being sent by the Fragment or Activity.
     * @param fonts - The font to be set.
     *
     */

    public static void setFontForView(View view, Typeface fonts) {

        if (fonts != null && view != null) {
            try {
                if (view instanceof TextView) {
                    ((TextView) view).setTypeface(fonts);
                } else if (view instanceof ViewGroup) {
                    setFontsForLayout((ViewGroup) view, fonts);
                }
            } catch (Exception e) {
            }
        }
    }

    /**
     * Checks if the room exist in drawable.
     *
     * @param room - The concatenated String of X, and Y-coordinates.
     *
     * @return item id if exist, else 0.
     *
     */

    public static int isViableRoom(String room) {

        try {
            int roomID = context.getResources().getIdentifier(
                    "room" + room, "drawable", context.getPackageName()
            );

            return roomID;

        } catch (Exception e) {
            return 0;
        }
    }

    /**
     * Checks if the item exist in drawable.
     *
     * @param itemID - The concatenated String of X, and Y-coordinates.
     *
     * @return drawable id if exist, else 0.
     *
     */

    public static int isViableItem(String itemID, int x, int y) {

        try {
            int drawableID = context.getResources().getIdentifier(
                    "item" + itemID + "" + String.valueOf(x) + "" + String.valueOf(y), "drawable", context.getPackageName()
            );

            return drawableID;

        } catch (Exception e) {
            return 0;
        }
    }



    /**
     * Checks if the room going to has a door that needs an item to open.
     *
     * @param x - The X-coordinate of the room.
     * @param y - The Y-coordinate of the room.
     *
     * @return @code{haveItem} - True or False.
     *
     */

    public static boolean haveItemForDoor(int pX, int pY, int x, int y) {
        String roomGoingTo = String.valueOf(x) + String.valueOf(y);
        String roomGoingFrom = String.valueOf(pX) + String.valueOf(pY);

        if (roomGoingTo.equals("10")) {
            return haveItem("key");
        } else if (roomGoingTo.equals("11")) {
            if (roomGoingFrom.equals("01")) {
                return haveItem("key");
            } else return true;

        } else {
            return true;
        }
    }

    /**
     * Checks if character has a specific item.
     *
     * @param item - Key, weapon or any other object in the game.
     *
     * @return true if exist, false if not.
     *
     */

    public static boolean haveItem(String item) {

        switch (item) {
            case "key":
                //Do Something
                return true;
            case "weapon":
                //Do Something else
                return true;
            default:
                return true;
        }
    }

    public static void setButtonsForRooms(String key, List<Button> buttons) {
        buttonsForRooms.put(key, buttons);
    }

    /**
     * Checks if the room has had event triggered.
     *
     * @param room - The room going to.
     *
     * @return alternative room image if event has been triggered.
     *
     */

    public static int doorOpened(String room) {

        int roomVersion;
        try {
            roomVersion = context.getResources().getIdentifier(
                    "room" + room + "a", "drawable", context.getPackageName()
            );
        } catch (Exception e) {
            return 0;
        }

        switch (room) {
            case "02":
                return room02 ? roomVersion : 0;
            case "01":
                Log.d("bla", String.valueOf(room01));
                return room01 ? roomVersion : 0;
            case "01a":
                return room01a ? roomVersion : 0;
            case "22":
                return room22 ? roomVersion : 0;
            case "33":
                return room33 ? roomVersion : 0;
            case "32":
                return room32 ? roomVersion : 0;
            case "21":
                return room21 ? roomVersion : 0;
            case "11":
                return room11 ? roomVersion : 0;
            case "12":
                return room12 ? roomVersion : 0;
            case "13":
                return room13 ? roomVersion : 0;
            case "13a":
                return room13a ? roomVersion : 0;
            default:
                return 0;
        }
    }

    public static void setBooleanValues() {
        room01 = SaveUtility.player.isRoom01();
        room02 = SaveUtility.player.isRoom02();
        room11 = SaveUtility.player.isRoom11();
        room12 = SaveUtility.player.isRoom12();
        room13 = SaveUtility.player.isRoom13a();
        room21 = SaveUtility.player.isRoom21();
        room22 = SaveUtility.player.isRoom22();
        room32 = SaveUtility.player.isRoom32();
        room33 = SaveUtility.player.isRoom33();
    }

    public static void setContext(Context contexts) {
        context = contexts;
    }

}
