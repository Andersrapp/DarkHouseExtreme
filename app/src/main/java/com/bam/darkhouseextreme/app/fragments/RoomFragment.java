package com.bam.darkhouseextreme.app.fragments;

import android.content.Context;
import android.graphics.Point;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;
import com.bam.darkhouseextreme.app.R;
import com.bam.darkhouseextreme.app.adapter.Shaker;
import com.bam.darkhouseextreme.app.utilities.SaveUtility;
import com.bam.darkhouseextreme.app.utilities.Utilities;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Chobii on 29/04/15.
 */
public class RoomFragment extends Fragment {

    public final String LOG_DATA = RoomFragment.class.getSimpleName();

    private View root;
    private Button buttonUp, buttonDown, buttonLeft, buttonRight, itemButton1, itemButton2, itemButton3;
    private Context context;
    private ImageView roomImage;
    private int x_cord, y_cord, score;

    private List<Button> eventsInRoom = new ArrayList<>();

    private SensorManager sManager;
    private Sensor sensor;
    private Shaker shaker;

    private Animation animation;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        context = getActivity().getApplicationContext();

        sManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        sensor = sManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        shaker = new Shaker();

        shaker.setShakeListener(new Shaker.OnShakeListener() {
            @Override
            public void shake(int count) {
                handleShake();
            }
        });


        root = inflater.inflate(R.layout.room, container, false);

        roomImage = (ImageView) root.findViewById(R.id.roomImage);
        animation = AnimationUtils.loadAnimation(context, R.anim.alpha_button);

        int[] stats = SaveUtility.loadStats();
        x_cord = stats[0];
        y_cord = stats[1];
        score = stats[2];

        continueIfApplicable(x_cord, y_cord);



        return root;
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);

//        nullifyAndRemoveButtonsFromParent();
//        createButtons();
//        setItemButtons();
    }


//    private void setItemButtons() {
//        for (int i = 1; i < 4; i++) {
//            int itemID;
//            try {
//                itemID = context.getResources().getIdentifier(
//                        "item" + i + "" + String.valueOf(x_cord) + "" + String.valueOf(y_cord), "id", context.getPackageName());
//            } catch (Exception e) {
//                itemID = 0;
//            }
//            if (itemID != 0) {
//                switch (i) {
//                    case 1:
//                        itemButton1 = (Button) root.findViewById(itemID);
//                        break;
//                    case 2:
//                        itemButton2 = (Button) root.findViewById(itemID);
//                        break;
//                    case 3:
//                        itemButton3 = (Button) root.findViewById(itemID);
//                        break;
//                    default:
//                        break;
//                }
//            }
//        }
//    }

    private void setItemClickListener(Button itemButton) {

        itemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {

                String idOfPickedUpItem = v.getTag().toString();
                int drawableID = Utilities.isViableItem(idOfPickedUpItem, context, x_cord, y_cord);

                if (!SaveUtility.alreadyHasItem(idOfPickedUpItem) && drawableID != 0) {
//                    v.setClickable(false);
                    v.setBackgroundResource(drawableID);
                    SaveUtility.saveItemToCharacter(idOfPickedUpItem);
                    Animation fadein = AnimationUtils.loadAnimation(context, R.anim.fade_in);

                    v.startAnimation(fadein);

                    fadein.setAnimationListener(new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animation animation) {
                            Animation fadeout = AnimationUtils.loadAnimation(context, R.anim.fade_out);
                            v.startAnimation(fadeout);
                            fadeout.setAnimationListener(new Animation.AnimationListener() {
                                @Override
                                public void onAnimationStart(Animation animation) {

                                }

                                @Override
                                public void onAnimationEnd(Animation animation) {
                                    v.setBackgroundResource(R.drawable.placeholder);
                                    eventsInRoom.remove(v);
                                }

                                @Override
                                public void onAnimationRepeat(Animation animation) {

                                }
                            });


                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {

                        }
                    });
                } else {
                    noItemMessage();
                }
            }
        });
    }

    private void changeRoom(final int roomId) {

        Log.d(LOG_DATA, "Changed Room");
        placeItems(root);

        Animation fadeout = AnimationUtils.loadAnimation(context, R.anim.fade_out);
        roomImage.startAnimation(fadeout);

        fadeout.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                roomImage.setImageResource(roomId);
                Animation fadein = AnimationUtils.loadAnimation(context, R.anim.fade_in);
                roomImage.startAnimation(fadein);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    private void noItemMessage() {
        Toast.makeText(context, "There could have been an item here. But now there is none.", Toast.LENGTH_SHORT)
                .show();
    }

    private void informOfError() {
        Toast.makeText(context, "Can't go this way", Toast.LENGTH_SHORT)
                .show();
    }

    public boolean isRoom(int x, int y) {
        String room = String.valueOf(x) + String.valueOf(y);
        final int roomId;
        if ((roomId = Utilities.isViableRoom(room, context)) != 0) {
            if (Utilities.haveItemForDoor(x_cord, y_cord, x, y)) {
                Log.d(LOG_DATA, String.valueOf(eventsInRoom.size()));
                nullifyAndRemoveButtonsFromParent();
                Log.d(LOG_DATA, room);
                Log.d(LOG_DATA, "Is a room");
                eventsInRoom.addAll(Utilities.buttonsForRooms.get(room));
                Log.d(LOG_DATA, String.valueOf(eventsInRoom.size()));
                x_cord = x;
                y_cord = y;
                changeRoom(roomId);
                SaveUtility.saveProgress(x, y, score += 10);
                return true;
            }
        } else {
            Toast.makeText(context, "You need some kind of item to get passed this door", Toast.LENGTH_SHORT).show();
        }

        return false;
    }

    private void continueIfApplicable(int x, int y) {
        Log.d(LOG_DATA, "Continuing");
        String room = String.valueOf(x) + String.valueOf(y);
        final int roomId;
        eventsInRoom.addAll(Utilities.buttonsForRooms.get(room));
        Log.d("List", String.valueOf(eventsInRoom.size()));
        roomId = Utilities.isViableRoom(room, context);
        placeItems(root);
        roomImage.setImageResource(roomId);
    }

    private void handleShake() {
        for (final Button event : eventsInRoom) {
            if (!SaveUtility.alreadyHasItem(String.valueOf(event.getTag()))) {
                event.setBackgroundResource(R.drawable.item_button);
                event.startAnimation(animation);
                new Handler().postDelayed(
                        new Runnable() {
                              @Override
                              public void run() {
                                  animation.cancel();
                                  event.setBackgroundResource(R.drawable.placeholder);
                              }
                          },
                        500);
            }
        }
    }

//    public void createButtons() {
//        String coordinates = String.valueOf(x_cord) + String.valueOf(y_cord);
//
//        switch (coordinates) {
//            case "00":
//                initiateButtons();
//                placeItems(root);
//                break;
//            default:
//                break;
//        }
//    }

//    public void initiateButtons() {
//        eventsInRoom.clear();
//        itemButton1 = new Button(context);
//        itemButton2 = new Button(context);
//        itemButton3 = new Button(context);
//        itemButton1.setTag(1);
//        itemButton2.setTag(2);
//        itemButton3.setTag(3);
//        itemButton1.setBackgroundResource(R.drawable.placeholder);
//        itemButton2.setBackgroundResource(R.drawable.placeholder);
//        itemButton3.setBackgroundResource(R.drawable.placeholder);
//        itemButton1.setId((R.id.item100));
//        itemButton2.setId((R.id.item200));
//        itemButton3.setId((R.id.item300));
//
//        eventsInRoom.add(itemButton1);
//        eventsInRoom.add(itemButton2);
//        eventsInRoom.add(itemButton3);
//
//        for (Button b : eventsInRoom) {
//            setItemClickListener(b);
//        }
//    }


    public RelativeLayout placeItems(View root) {

        Log.d(LOG_DATA, "Placing items");

        String room = String.valueOf(x_cord) + String.valueOf(y_cord);

        Point size = new Point();
        getActivity().getWindowManager().getDefaultDisplay().getSize(size);
        int screenWidth = size.x;

        int screenHeight = size.y - 100;
        RelativeLayout mainRelativeLayout = (RelativeLayout) root.findViewById(R.id.mainRel);

        RelativeLayout.LayoutParams doorUp = getParams();
        RelativeLayout.LayoutParams doorDown = getParams();
        RelativeLayout.LayoutParams doorRight = getParams();
        RelativeLayout.LayoutParams doorLeft = getParams();

        doorRight.setMargins((screenWidth - 70), (screenHeight /2), 0, 0);
        doorDown.setMargins((screenWidth / 2), (screenHeight - 70), 0, 0);
        doorUp.setMargins((screenWidth / 2), 10, 0, 0);

        Button up;
        Button down;
        Button right;
        Button left;


        switch (room) {
            case "01":

                RelativeLayout.LayoutParams paper = getParams();
                RelativeLayout.LayoutParams skeleton = getParams();
                Log.d(LOG_DATA, String.valueOf(eventsInRoom.size()));
                Log.d(LOG_DATA, "Room 02");
                right = eventsInRoom.get(0);
                down = eventsInRoom.get(1);



                paper.setMargins((screenHeight / 2), (screenWidth / 2), 0, 0);
                skeleton.setMargins((screenHeight / 4), (screenWidth / 2), 0, 0);

                right.setLayoutParams(doorRight);
                down.setLayoutParams(doorDown);


                mainRelativeLayout.addView(right);
                mainRelativeLayout.addView(down);

                if (eventsInRoom.size() > 3) {

                    Button papererino = eventsInRoom.get(2);
                    Button skeletini = eventsInRoom.get(3);

                    papererino.setLayoutParams(paper);
                    skeletini.setLayoutParams(skeleton);

                    mainRelativeLayout.addView(skeletini);
                    mainRelativeLayout.addView(papererino);
                } else if (eventsInRoom.size() > 2) {

                    if (eventsInRoom.get(2).getTag().equals("paper")) {
                        Button papererino = eventsInRoom.get(2);
                        papererino.setLayoutParams(paper);
                        mainRelativeLayout.addView(papererino);
                    } else {
                        Button skeletini = eventsInRoom.get(2);
                        skeletini.setLayoutParams(skeleton);
                        mainRelativeLayout.addView(skeletini);
                    }
                }

                break;
            case "00":
                Log.d(LOG_DATA, "Room 01");
                RelativeLayout.LayoutParams key = getParams();

                up = eventsInRoom.get(0);
                right = eventsInRoom.get(1);
                Button keyerino = eventsInRoom.get(2);

                key.setMargins((screenWidth / 2), (screenHeight / 2), 0 ,0);


                up.setLayoutParams(doorUp);
                right.setLayoutParams(doorRight);
                keyerino.setLayoutParams(key);

                mainRelativeLayout.addView(up);
                mainRelativeLayout.addView(right);
                mainRelativeLayout.addView(keyerino);
                Log.d(LOG_DATA, String.valueOf(eventsInRoom.size()));

                break;
            case "11":
                break;
            case "21":
                break;
            case "20":
                break;
            case "12":
                break;
            case "22":
                break;
            case "13":
                break;
            case "23":
                break;
            case "33":
                break;
            case "32":
                break;
            default:
                break;
        }

        return mainRelativeLayout;
    }

    private void nullifyAndRemoveButtonsFromParent() {
        RelativeLayout mainRelativeLayout = (RelativeLayout) root.findViewById(R.id.mainRel);
        Log.d(LOG_DATA, "empty");
        for (Button b : eventsInRoom) {
            mainRelativeLayout.removeView(b);
        }
        eventsInRoom.clear();
    }

    private RelativeLayout.LayoutParams getParams() {
        return new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        );
    }

    @Override
    public void onPause() {
        super.onPause();
        sManager.unregisterListener(shaker);
    }

    @Override
    public void onResume() {
        super.onResume();
        sManager.registerListener(shaker, sensor, SensorManager.SENSOR_DELAY_UI);
    }
}
