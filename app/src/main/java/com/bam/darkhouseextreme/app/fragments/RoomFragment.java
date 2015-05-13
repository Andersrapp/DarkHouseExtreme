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

    public View root;
    private Context context;
    private ImageView roomImage;
    private int x_cord, y_cord, score;

    public List<Button> eventsInRoom = new ArrayList<>();

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

    public boolean isRoom(int x, int y) {
        String room = String.valueOf(x) + String.valueOf(y);
        final int roomId;
        if ((roomId = Utilities.isViableRoom(room, context)) != 0) {
            int alternative;
            if ((alternative = Utilities.doorOpened(context, room)) != 0) {
                nullifyAndRemoveButtonsFromParent();
                eventsInRoom.addAll(Utilities.buttonsForRooms.get(room));
                x_cord = x;
                y_cord = y;
                changeRoom(alternative);
                SaveUtility.saveProgress(x, y, score += 10);
                return true;
            }
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

    public void eventTriggeredSwap(String room) {
        int roomId = Utilities.doorOpened(context, room);
        Log.d(LOG_DATA, String.valueOf(roomId));
        roomImage.setImageResource(roomId);
        if (room.equals("21")) {
            nullifyAndRemoveButtonsFromParent();
            eventsInRoom.addAll(Utilities.buttonsForRooms.get("21"));
            placeItems(root);
        }

    }

    public RelativeLayout placeItems(View root) {

        Log.d(LOG_DATA, "Placing items");

        String room = String.valueOf(x_cord) + String.valueOf(y_cord);

        Log.d(LOG_DATA, room);

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
        doorDown.setMargins(((screenWidth / 2) - (screenWidth / 11)), (screenHeight - 70), 0, 0);
        doorUp.setMargins(((screenWidth / 2) - (screenWidth / 11)), 10, 0, 0);
        doorLeft.setMargins(0, (screenHeight / 2), 0, 0);

        Button up;
        Button down;
        Button right;
        Button left;


        switch (room) {
            case "02":

                right = eventsInRoom.get(0);
                down = eventsInRoom.get(1);


                RelativeLayout.LayoutParams paper = getParams();
                RelativeLayout.LayoutParams skeleton = getParams();

                paper.setMargins((screenWidth / 2),(screenHeight / 2) , 0, 0);
                skeleton.setMargins((screenWidth / 2),(screenHeight / 4) , 0, 0);


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
                        Button skeletini = eventsInRoom.get(3);
                        skeletini.setLayoutParams(skeleton);
                        mainRelativeLayout.addView(skeletini);
                    }
                }

                break;
            case "01":

                up = eventsInRoom.get(0);
                right = eventsInRoom.get(1);

                up.setLayoutParams(doorUp);
                right.setLayoutParams(doorRight);

                mainRelativeLayout.addView(up);
                mainRelativeLayout.addView(right);


                if (eventsInRoom.size() > 2) {
                    Button keyerino = eventsInRoom.get(2);
                    RelativeLayout.LayoutParams key = getParams();
                    key.setMargins((screenWidth / 2), (screenHeight / 2), 0 ,0);
                    keyerino.setLayoutParams(key);
                    mainRelativeLayout.addView(keyerino);

                }

                break;
            case "11":

                left = eventsInRoom.get(0);
                right = eventsInRoom.get(1);
                Button clock = eventsInRoom.get(2);

                left.setLayoutParams(doorLeft);
                right.setLayoutParams(doorRight);

                mainRelativeLayout.addView(left);
                mainRelativeLayout.addView(right);

                break;
            case "21":

                Log.d(LOG_DATA, String.valueOf(eventsInRoom.size()));

                if (eventsInRoom.size() < 2) {

                    Button light = eventsInRoom.get(0);

                    RelativeLayout.LayoutParams lightparams = getParams();

                    lightparams.setMargins((screenWidth / 2), (screenHeight / 2), 0, 0);

                    light.setLayoutParams(lightparams);

                    mainRelativeLayout.addView(light);

                } else if (eventsInRoom.get(0).getTag().equals("table")) {

                    Button table = eventsInRoom.get(0);
                    down = eventsInRoom.get(1);
                    left = eventsInRoom.get(2);

                    RelativeLayout.LayoutParams tablelayout = getParams();
                    tablelayout.setMargins(((screenWidth / 2) - (screenWidth / 11)), (screenHeight / 10), 0, 0);

                    down.setLayoutParams(doorDown);
                    left.setLayoutParams(doorLeft);
                    table.setLayoutParams(tablelayout);

                    mainRelativeLayout.addView(down);
                    mainRelativeLayout.addView(left);
                    mainRelativeLayout.addView(table);

                } else {

                    down = eventsInRoom.get(0);
                    left = eventsInRoom.get(1);
                    up = eventsInRoom.get(2);

                    down.setLayoutParams(doorDown);
                    left.setLayoutParams(doorLeft);
                    up.setLayoutParams(doorUp);

                    mainRelativeLayout.addView(down);
                    mainRelativeLayout.addView(left);
                    mainRelativeLayout.addView(up);
                }


                break;
            case "20":

                up = eventsInRoom.get(0);

                up.setLayoutParams(doorUp);

                mainRelativeLayout.addView(up);

                break;
            case "12":

                right = eventsInRoom.get(0);
                left = eventsInRoom.get(1);
                Button stairs = eventsInRoom.get(2);

                right.setLayoutParams(doorRight);
                left.setLayoutParams(doorLeft);

                RelativeLayout.LayoutParams stairsparam = getParams();

                stairsparam.setMargins((screenWidth / 4), (screenHeight /2), 0, 0);

                stairs.setLayoutParams(stairsparam);

                mainRelativeLayout.addView(right);
                mainRelativeLayout.addView(left);
                mainRelativeLayout.addView(stairs);

                break;
            case "22":

                down = eventsInRoom.get(0);
                left = eventsInRoom.get(1);
                right = eventsInRoom.get(2);
                up = eventsInRoom.get(3);

                down.setLayoutParams(doorDown);
                left.setLayoutParams(doorLeft);
                right.setLayoutParams(doorRight);
                up.setLayoutParams(doorUp);

                mainRelativeLayout.addView(up);
                mainRelativeLayout.addView(down);
                mainRelativeLayout.addView(right);
                mainRelativeLayout.addView(left);

                break;
            case "13":

                right = eventsInRoom.get(0);

                right.setLayoutParams(doorRight);

                mainRelativeLayout.addView(right);

                break;
            case "23":

                down = eventsInRoom.get(0);
                left = eventsInRoom.get(1);
                right = eventsInRoom.get(2);

                down.setLayoutParams(doorDown);
                left.setLayoutParams(doorLeft);
                right.setLayoutParams(doorRight);

                mainRelativeLayout.addView(down);
                mainRelativeLayout.addView(right);
                mainRelativeLayout.addView(left);

                break;
            case "33":

                down = eventsInRoom.get(0);
                left = eventsInRoom.get(1);

                down.setLayoutParams(doorDown);
                left.setLayoutParams(doorLeft);

                mainRelativeLayout.addView(down);
                mainRelativeLayout.addView(left);

                break;
            case "32":

                left = eventsInRoom.get(0);
                up = eventsInRoom.get(1);

                left.setLayoutParams(doorLeft);
                up.setLayoutParams(doorUp);

                mainRelativeLayout.addView(left);
                mainRelativeLayout.addView(up);

                break;
            default:
                break;
        }

        return mainRelativeLayout;
    }

    public void nullifyAndRemoveButtonsFromParent() {
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
