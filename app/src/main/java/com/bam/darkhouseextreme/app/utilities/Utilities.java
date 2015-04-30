package com.bam.darkhouseextreme.app.utilities;

import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Chobii on 28/04/15.
 */
public class Utilities {

    private static final String LOG_DATA = Utilities.class.getSimpleName();

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
                Log.d(LOG_DATA, "Not a viable ViewGroup");
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
                Log.d(LOG_DATA, "Not a viable View");
            }
        }
    }

    /**
     * Checks if the room exist in drawable.
     *
     * @param room - The concatenated String of X, and Y-coordinates.
     * @param context - The Context of the Application.
     *
     * @return item id if exist, else 0.
     *
     */

    public static int isViableRoom(String room, Context context) {

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
     * Checks if the room going to has a door that needs an item to open.
     *
     * @param x - The X-coordinate of the room.
     * @param y - The Y-coordinate of the room.
     *
     * @return @code{haveItem} - True or False.
     *
     */

    public static boolean haveItemForDoor(int x, int y) {
        String roomGoingTo = String.valueOf(x) + String.valueOf(y);

        if (roomGoingTo.equals("10")) {
            return haveItem("key");
        } else if (roomGoingTo.equals("11")) {
            return haveItem("key");
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
}
