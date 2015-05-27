package com.bam.darkhouseextreme.app.fragments;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.AnimationDrawable;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.*;
import com.bam.darkhouseextreme.app.R;
import com.bam.darkhouseextreme.app.activities.GameActivity;
import com.bam.darkhouseextreme.app.activities.StartScreenActivity;
import com.bam.darkhouseextreme.app.adapter.InventoryAdapter;
import com.bam.darkhouseextreme.app.model.Item;
import com.bam.darkhouseextreme.app.helper.SoundHelper;
import com.bam.darkhouseextreme.app.utilities.SaveUtility;
import com.bam.darkhouseextreme.app.utilities.Utilities;
import com.devsmart.android.ui.HorizontalListView;

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
    public List<Button> fadeOutButtons = new ArrayList<>();
    public List<Button> fadeInButtons = new ArrayList<>();

    public int screenWidth;
    public int screenHeight;

    private Animation animation;
    private Animation fadeout;
    private Animation fadein;
    private Animation fadein_buttons;
    private Animation fadeout_buttons;

    private Toast toast;
    private TextView toastText;

    private RelativeLayout mainRelativeLayout;
    private int[] tableLeftMargin = new int[6];
    private int tablePosition = 0;
    private int portion;
    private int startMargin;
    private RelativeLayout.LayoutParams tableLayout;
    private Button table;
    private Button minuteHand;
    private int tablepos;

    private ImageView gasView;
    private ImageView skullView;
    private Animation fadeInSkull;
    private Animation fadeInGas;
    private boolean gasPuzzleSolved;
    private CombinationLockFragment lockFragment;
    private AnimationDrawable paintingAnimation;
    private boolean freedPainting;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        context = getActivity().getApplicationContext();


        GameActivity activity = (GameActivity) getActivity();
        toast = activity.getToast();
        toastText = activity.getToastText();

        fadein_buttons = AnimationUtils.loadAnimation(context, R.anim.fade_in);
        fadeout_buttons = AnimationUtils.loadAnimation(context, R.anim.fade_out);

        fadein_buttons.setAnimationListener(
                new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                        for (Button b : fadeInButtons) {
                            b.setVisibility(View.VISIBLE);
                        }
                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                }
        );

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

    private void changeRoom(final int roomId, final String room) {

        fadeout = AnimationUtils.loadAnimation(context, R.anim.fade_out);
        roomImage.startAnimation(fadeout);

        fadeout.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                fadeOutButtons(fadeOutButtons);
                new Handler().post(new Runnable() {
                    @Override
                    public void run() {
                        nullifyAndRemoveButtonsFromParent();
                        eventsInRoom.addAll(Utilities.buttonsForRooms.get(room));
                        placeItems(root);
                    }
                });


            }

            @Override
            public void onAnimationEnd(Animation animation) {

                new Handler().post(new Runnable() {
                    @Override
                    public void run() {
                        roomImage.setImageResource(roomId);
                        fadein = AnimationUtils.loadAnimation(context, R.anim.fade_in);
                        roomImage.startAnimation(fadein);
                        fadeInButtons(fadeInButtons);
                    }
                });

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    public boolean isRoom(int x, int y) {
        String room = String.valueOf(x) + String.valueOf(y);
        final int roomId;
        if ((roomId = Utilities.isViableRoom(room)) != 0) {
            int alternative;
            if ((alternative = Utilities.doorOpened(room)) != 0) {
                if (Utilities.doorOpened(room + "a") != 0) {
                    alternative = Utilities.doorOpened(room + "a");
                }

                x_cord = x;
                y_cord = y;
                changeRoom(alternative, room);
                SaveUtility.saveProgress(x, y, score += 10);
                return true;
            }
            if (Utilities.haveItemForDoor(x_cord, y_cord, x, y)) {
                x_cord = x;
                y_cord = y;
                changeRoom(roomId, room);
                SaveUtility.saveProgress(x, y, score += 10);
                return true;
            }
        } else {
            Toast.makeText(context, "You need some kind of item to get passed this door", Toast.LENGTH_SHORT).show();
        }

        return false;
    }

    private void continueIfApplicable(int x, int y) {
        String room = String.valueOf(x) + String.valueOf(y);
        final int roomId;
        eventsInRoom.addAll(Utilities.buttonsForRooms.get(room));
        if (Utilities.doorOpened(room + "a") != 0) {
            roomId = Utilities.doorOpened(room + "a");
        } else if (Utilities.doorOpened(room) != 0) {
            roomId = Utilities.doorOpened(room);
        } else roomId = Utilities.isViableRoom(room);
        placeItems(root);
        roomImage.setImageResource(roomId);
        for (Button b : fadeInButtons) {
            b.setVisibility(View.VISIBLE);
        }
        fadeInButtons.clear();
    }


    public void eventTriggeredSwap(String room) {
        int roomId = Utilities.doorOpened(room);
        roomImage.setImageResource(roomId);
        SaveUtility.saveProgress(x_cord, y_cord, score += 10);
        if (room.equals("21")) {
            nullifyAndRemoveButtonsFromParent();
            eventsInRoom.addAll(Utilities.buttonsForRooms.get("21"));
            placeItems(root);
            for (Button b : eventsInRoom) {
                b.setVisibility(View.VISIBLE);
            }
        }

    }

    public RelativeLayout placeItems(View root) {


        fadeOutButtons.clear();
        fadeOutButtons.clear();

        RelativeLayout.LayoutParams minuteHandParams = getParams();
        minuteHandParams.setMargins((screenWidth / 2) - (screenWidth / 7), (screenHeight / 7), 0, 0);

        String room = String.valueOf(x_cord) + String.valueOf(y_cord);


        Point size = new Point();
        getActivity().getWindowManager().getDefaultDisplay().getSize(size);
        screenWidth = size.x;
        Utilities.screenWidth = screenWidth;

        screenHeight = size.y - 100;
        Utilities.screenHeight = screenHeight;

        portion = screenWidth / 20;
        startMargin = (portion * 10) - (portion * 3);
        tableLeftMargin[0] = startMargin;
        tableLeftMargin[1] = startMargin + portion;
        tableLeftMargin[2] = startMargin + (portion * 2);
        tableLeftMargin[3] = startMargin + (portion * 3);
        tableLeftMargin[4] = startMargin + (portion * 4);
        tableLeftMargin[5] = startMargin + (portion * 5);

        mainRelativeLayout = (RelativeLayout) root.findViewById(R.id.mainRel);

        RelativeLayout.LayoutParams doorUp = getParams();
        RelativeLayout.LayoutParams doorDown = getParams();
        RelativeLayout.LayoutParams doorRight = getParams();
        RelativeLayout.LayoutParams doorLeft = getParams();

        doorRight.setMargins((screenWidth - (screenWidth / 10)), ((screenHeight / 2) - (screenHeight / 25)), 0, 0);
        doorDown.setMargins(((screenWidth / 2) - (screenWidth / 20)), (screenHeight - (screenHeight / 10)), 0, 0);
        doorUp.setMargins(((screenWidth / 2) - (screenWidth / 20)), (screenHeight / 20), 0, 0);
        doorLeft.setMargins(0, ((screenHeight / 2) - (screenHeight / 27)), 0, 0);

        Button up;
        Button down;
        Button right;
        Button left;


        switch (room) {
            case "02":

                right = eventsInRoom.get(0);
                down = eventsInRoom.get(1);

                RelativeLayout.LayoutParams noteParams = getParams();
                RelativeLayout.LayoutParams ductTapeParams = getParams();


                noteParams.setMargins((screenWidth / 2) - (screenWidth / 27), (screenHeight / 2) - (screenHeight / 18), 0, 0);
                ductTapeParams.setMargins((screenWidth / 2 - screenWidth / 39), (screenHeight / 3 - screenHeight / 13), 0, 0);

                right.setLayoutParams(doorRight);
                down.setLayoutParams(doorDown);

                mainRelativeLayout.addView(right);
                mainRelativeLayout.addView(down);

                if (eventsInRoom.size() > 3) {

                    Button note = eventsInRoom.get(2);
                    Button ductTape = eventsInRoom.get(3);

                    note.setLayoutParams(noteParams);
                    ductTape.setLayoutParams(ductTapeParams);

                    fadeOutButtons.add(ductTape);
                    fadeInButtons.add(ductTape);
                    ductTape.setVisibility(View.INVISIBLE);

                    mainRelativeLayout.addView(ductTape);
                    mainRelativeLayout.addView(note);
                } else if (eventsInRoom.size() > 2) {

                    if (eventsInRoom.get(2).getTag().equals("paper")) {
                        Button papererino = eventsInRoom.get(2);
                        papererino.setLayoutParams(noteParams);
                        mainRelativeLayout.addView(papererino);
                    } else {
                        Button ductTape = eventsInRoom.get(2);
                        ductTape.setLayoutParams(ductTapeParams);
                        mainRelativeLayout.addView(ductTape);
                        fadeOutButtons.add(ductTape);
                        fadeInButtons.add(ductTape);
                        ductTape.setVisibility(View.INVISIBLE);
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

                Button blood = eventsInRoom.get(2);
                RelativeLayout.LayoutParams bloodParam = getParams();
                bloodParam.setMargins(screenWidth / 7, (screenHeight - screenHeight / 8), 0, 0);
                blood.setLayoutParams(bloodParam);
                mainRelativeLayout.addView(blood);


                if (eventsInRoom.size() > 3) {
                    Button key = eventsInRoom.get(3);
                    RelativeLayout.LayoutParams keyParams = getParams();
                    keyParams.setMargins((screenWidth / 2) + (screenWidth / 24), (screenHeight / 4) + (screenHeight / 14), 0, 0);
                    key.setLayoutParams(keyParams);
                    mainRelativeLayout.addView(key);

                }
                if (eventsInRoom.size() > 4) {
                    Button carpet = eventsInRoom.get(4);
                    RelativeLayout.LayoutParams carpetParam = getParams();
                    carpetParam.setMargins((screenWidth / 2) + (screenWidth / 24), (screenHeight / 4) + (screenHeight / 14), 0, 0);
                    carpet.setLayoutParams(carpetParam);
                    mainRelativeLayout.addView(carpet);
                }

                break;
            case "11":

                left = eventsInRoom.get(0);
                right = eventsInRoom.get(1);
                Button clock = eventsInRoom.get(2);
                Button gasLine = eventsInRoom.get(3);
                RelativeLayout.LayoutParams gasLineParams = getParams();
                gasLineParams.setMargins((screenWidth / 4) * 3, 0, 0, 0);
                gasLine.setLayoutParams(gasLineParams);

                RelativeLayout.LayoutParams clockParams = getParams();
                clockParams.setMargins((screenWidth / 7), (screenHeight - (screenHeight / 5)), 0, 0);

                clock.setLayoutParams(clockParams);
                left.setLayoutParams(doorLeft);
                right.setLayoutParams(doorRight);
                mainRelativeLayout.addView(gasLine);
                mainRelativeLayout.addView(clock);
                mainRelativeLayout.addView(left);
                mainRelativeLayout.addView(right);

                fadeOutButtons.add(clock);
                fadeInButtons.add(clock);
                clock.setVisibility(View.INVISIBLE);

                if (!Utilities.room11) {
                    setGasPuzzle();
                }

                break;
            case "21":


                if (eventsInRoom.size() < 2) {
                    toastText.setText(R.string.dark_room_description);
                    toast.show();

                    Button light = eventsInRoom.get(0);

                    RelativeLayout.LayoutParams lightparams = getParams();

                    lightparams.setMargins((screenWidth / 3 - screenWidth / 8), (screenHeight - 10), 0, 0);

                    light.setLayoutParams(lightparams);

                    mainRelativeLayout.addView(light);

                } else if (eventsInRoom.get(0).getTag().equals("table")) {

                    if (!SaveUtility.alreadyHasItem("8")) {
                        minuteHand = eventsInRoom.get(3);

                        minuteHand.setLayoutParams(minuteHandParams);
                        minuteHand.setMinimumWidth(0);
                        minuteHand.setMinWidth(0);
                        minuteHand.setMinimumHeight(0);
                        minuteHand.setMinHeight(0);

                        fadeInButtons.add(minuteHand);
                        fadeOutButtons.add(minuteHand);
                        minuteHand.setVisibility(View.INVISIBLE);

                        mainRelativeLayout.addView(minuteHand);
                    }

                    table = eventsInRoom.get(0);
                    down = eventsInRoom.get(1);
                    left = eventsInRoom.get(2);

                    tableLayout = getParams();
                    tableLayout.setMargins(startMargin, (screenHeight / 15), 0, 0);
                    table.setLayoutParams(tableLayout);

                    down.setLayoutParams(doorDown);
                    left.setLayoutParams(doorLeft);

                    fadeOutButtons.add(table);
                    fadeInButtons.add(table);
                    table.setVisibility(View.INVISIBLE);

                    mainRelativeLayout.addView(down);
                    mainRelativeLayout.addView(left);
                    mainRelativeLayout.addView(table);

                } else if (eventsInRoom.get(0).getTag().equals("door")) {


                    if (!SaveUtility.alreadyHasItem("8")) {
                        minuteHand = eventsInRoom.get(4);

                        minuteHand.setLayoutParams(minuteHandParams);
                        minuteHand.setMinimumWidth(0);
                        minuteHand.setMinWidth(0);
                        minuteHand.setMinimumHeight(0);
                        minuteHand.setMinHeight(0);

                        fadeInButtons.add(minuteHand);
                        fadeOutButtons.add(minuteHand);
                        minuteHand.setVisibility(View.INVISIBLE);

                        mainRelativeLayout.addView(minuteHand);
                    }

                    down = eventsInRoom.get(0);
                    left = eventsInRoom.get(1);
                    up = eventsInRoom.get(2);
                    table = eventsInRoom.get(3);

                    down.setLayoutParams(doorDown);
                    left.setLayoutParams(doorLeft);
                    up.setLayoutParams(doorUp);

                    tableLayout = getParams();
                    tableLayout.setMargins(tablepos, screenHeight / 15, 0, 0);
                    table.setLayoutParams(tableLayout);

                    fadeOutButtons.add(table);
                    fadeInButtons.add(table);
                    table.setVisibility(View.INVISIBLE);

                    mainRelativeLayout.addView(down);
                    mainRelativeLayout.addView(left);
                    mainRelativeLayout.addView(up);
                    mainRelativeLayout.addView(table);
                }


                break;
            case "20":

                Button water = eventsInRoom.get(eventsInRoom.size()-1);
                RelativeLayout.LayoutParams waterParams = getParams();
                waterParams.setMargins((screenWidth / 3), (screenHeight / 5) * 4, 0, 0);
                water.setLayoutParams(waterParams);
                mainRelativeLayout.addView(water);

                up = eventsInRoom.get(0);

                RelativeLayout.LayoutParams toiletParams = getParams();

                up.setLayoutParams(doorUp);

                if (eventsInRoom.size() > 3) {

                    Button toilet = eventsInRoom.get(1);
                    toiletParams.setMargins((screenWidth - screenWidth / 3), (screenHeight / 5), 0, 0);
                    toilet.setLayoutParams(toiletParams);
                    mainRelativeLayout.addView(toilet);

                    Button hourHand = eventsInRoom.get(2);
                    RelativeLayout.LayoutParams hourParam = getParams();
                    hourParam.setMargins((screenWidth - ((screenWidth / 3) - (screenWidth / 30))), (screenHeight / 5), 0, 0);
                    hourHand.setLayoutParams(hourParam);
                    mainRelativeLayout.addView(hourHand);
                }

                if (eventsInRoom.size() < 4 && eventsInRoom.size() != 2) {

                    Button hourHand = eventsInRoom.get(1);
                    RelativeLayout.LayoutParams hourParam = getParams();
                    hourParam.setMargins((screenWidth - screenWidth / 4), (screenHeight / 4), 0, 0);
                    hourHand.setLayoutParams(hourParam);
                    mainRelativeLayout.addView(hourHand);
                }

                mainRelativeLayout.addView(up);

                break;
            case "12":

                if (!Utilities.room12) {
                    right = eventsInRoom.get(0);
                    left = eventsInRoom.get(1);
                    Button stairs = eventsInRoom.get(2);

                    right.setLayoutParams(doorRight);
                    left.setLayoutParams(doorLeft);

                    RelativeLayout.LayoutParams stairsparam = getParams();

                    stairsparam.setMargins((screenWidth / 6), (screenHeight - (screenHeight / 5)), 0, 0);

                    stairs.setLayoutParams(stairsparam);

                    mainRelativeLayout.addView(right);
                    mainRelativeLayout.addView(left);
                    mainRelativeLayout.addView(stairs);
                }

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

                Button book = eventsInRoom.get(4);

                RelativeLayout.LayoutParams bookParam = getParams();
                bookParam.setMargins((screenWidth / 2) + (screenWidth / 17), (screenHeight / 2) + (screenHeight / 19), 0, 0);
                book.setLayoutParams(bookParam);

                mainRelativeLayout.addView(book);

                mainRelativeLayout.addView(up);
                mainRelativeLayout.addView(down);
                mainRelativeLayout.addView(right);
                mainRelativeLayout.addView(left);

                break;
            case "13":

                right = eventsInRoom.get(0);
                right.setLayoutParams(doorRight);
                mainRelativeLayout.addView(right);

                if (!Utilities.room13a) {
                    Button leverHolder = eventsInRoom.get(1);
                    RelativeLayout.LayoutParams leverHolderParams = getParams();
                    leverHolderParams.setMargins(0, ((screenHeight / 20) * 7), 0, 0);
                    leverHolder.setLayoutParams(leverHolderParams);
                    mainRelativeLayout.addView(leverHolder);
                }

                break;
            case "23":

                down = eventsInRoom.get(0);
                left = eventsInRoom.get(1);
                right = eventsInRoom.get(2);

                down.setLayoutParams(doorDown);
                left.setLayoutParams(doorLeft);
                right.setLayoutParams(doorRight);

                Button painting = eventsInRoom.get(3);
                if (freedPainting) {
                    painting.setBackgroundResource(R.drawable.painting_3);
                } else {
                    painting.setBackgroundResource(R.drawable.painting_animation);
                    paintingAnimation = (AnimationDrawable) painting.getBackground();
                    fadeInButtons.add(painting);
                    fadeOutButtons.add(painting);
                    painting.setVisibility(View.INVISIBLE);
                }

                RelativeLayout.LayoutParams paintingParams = getParams();
                paintingParams.setMargins((screenWidth / 4), (screenHeight / 5), 0, 0);
                painting.setLayoutParams(paintingParams);
                painting.setOnClickListener(
                        new View.OnClickListener() {

                            @Override
                            public void onClick(View v) {
                                if (!SaveUtility.alreadyHasItem("12")) {
                                    if (!SaveUtility.alreadyHasItem("11")) {
                                        toastText.setText(R.string.painting_description);
                                        toast.show();
                                    } else {
                                        toastText.setText(R.string.painting_description2);
                                        toast.show();
                                    }
                                } else {
                                    if (!freedPainting) {
                                        paintingAnimation.stop();
                                        paintingAnimation.start();
                                        freedPainting = true;
                                        new Handler().postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                toastText.setText(R.string.painting_description3);
                                                toast.show();
                                            }
                                        }, 400);
                                    } else {
                                        toastText.setText(R.string.painting_description4);
                                        toast.show();
                                    }
                                }
                            }
                        }
                );

                mainRelativeLayout.addView(painting);
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

                if (!SaveUtility.alreadyHasItem("11")) {
                    Button bucket = eventsInRoom.get(2);
                    bucket.setBackgroundResource(R.drawable.bucket);
                    bucket.setMinWidth(1);
                    bucket.setMinimumWidth(1);
                    RelativeLayout.LayoutParams bucketParams = getParams();
                    bucketParams.setMargins(screenWidth / 5, screenHeight / 5, 0, 0);
                    bucket.setLayoutParams(bucketParams);
                    mainRelativeLayout.addView(bucket);

                    fadeInButtons.add(bucket);
                    fadeOutButtons.add(bucket);
                    bucket.setVisibility(View.INVISIBLE);

                    bucket.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View v) {
                            mainRelativeLayout.removeView(v);
                            SaveUtility.saveItemToCharacter("11");
                            toastText.setText(R.string.bucket_empty_description);
                            toast.show();
                        }
                    });
                }

                break;
            case "32":

                left = eventsInRoom.get(0);
                up = eventsInRoom.get(1);

                left.setLayoutParams(doorLeft);
                up.setLayoutParams(doorUp);

                if (!SaveUtility.alreadyHasItem("9")) {
                    Button leverHandle = eventsInRoom.get(eventsInRoom.size()-1);
                    RelativeLayout.LayoutParams leverHandleParams = getParams();
                    leverHandleParams.setMargins((screenWidth / 7) * 5, (screenHeight / 7) * 5, 0, 0);
                    leverHandle.setLayoutParams(leverHandleParams);
                    mainRelativeLayout.addView(leverHandle);
                }

                if (!SaveUtility.alreadyHasItem("13")) {
                    Button masterKey = eventsInRoom.get(2);
                    RelativeLayout.LayoutParams masterKeyParams = getParams();
                    masterKeyParams.setMargins((screenWidth / 7), (screenHeight / 5) * 4, 0, 0);
                    masterKey.setLayoutParams(masterKeyParams);
                    mainRelativeLayout.addView(masterKey);

                    fadeInButtons.add(masterKey);
                    fadeOutButtons.add(masterKey);
                    masterKey.setVisibility(View.INVISIBLE);
                }

                mainRelativeLayout.addView(left);
                mainRelativeLayout.addView(up);

                break;
            default:
                break;
        }

        return mainRelativeLayout;
    }

    public void nullifyAndRemoveButtonsFromParent() {

        for (Button b : eventsInRoom) {
            try {
                mainRelativeLayout.removeView(b);
            } catch (Exception e) {
            }
        }
        for (Button b : fadeInButtons) {
            try {
                mainRelativeLayout.removeView(b);
            } catch (Exception e) {
            }
        }
        eventsInRoom.clear();
        fadeInButtons.clear();
    }

    private RelativeLayout.LayoutParams getParams() {
        return new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        );
    }

    public void moveTable() {

        int height = (screenHeight / 15);
        switch (tablePosition) {
            case 0:
                tablePosition++;
                tablepos = tableLeftMargin[tablePosition];
                tableLayout.setMargins(tablepos, height, 0, 0);
                table.setLayoutParams(tableLayout);
                break;

            case 1:
                tablePosition++;
                tablepos = tableLeftMargin[tablePosition];
                tableLayout.setMargins(tablepos, height, 0, 0);
                table.setLayoutParams(tableLayout);
                break;
            case 2:
                tablePosition++;
                tablepos = tableLeftMargin[tablePosition];
                tableLayout.setMargins(tablepos, height, 0, 0);
                table.setLayoutParams(tableLayout);
                break;
            case 3:
                tablePosition++;
                tablepos = tableLeftMargin[tablePosition];
                tableLayout.setMargins(tablepos, height, 0, 0);
                table.setLayoutParams(tableLayout);
                break;
            case 4:
                tablePosition++;
                tablepos = tableLeftMargin[tablePosition];
                tableLayout.setMargins(tablepos, height, 0, 0);
                table.setLayoutParams(tableLayout);
                break;
            case 5:
                tablepos = tableLeftMargin[tablePosition];
                tableLayout.setMargins(tablepos, height, 0, 0);
                table.setLayoutParams(tableLayout);
                break;
            default:
                break;
        }

        TransitionManager.beginDelayedTransition(mainRelativeLayout);

    }


    public void setGasPuzzle() {
        skullView = new ImageView(context);
        fadeInSkull = AnimationUtils.loadAnimation(context, R.anim.fade_in_skull);
        fadeInGas = AnimationUtils.loadAnimation(context, R.anim.fade_in_gas);


        gasView = (ImageView) root.findViewById(R.id.miscView);

        RelativeLayout.LayoutParams skullLP = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        );

        skullLP.addRule(RelativeLayout.CENTER_HORIZONTAL);
        skullLP.addRule(RelativeLayout.CENTER_VERTICAL);
        skullView.setLayoutParams(skullLP);
        mainRelativeLayout.addView(skullView);
        toastText.setText(R.string.gas_description);
        toast.show();
        new Handler().postDelayed(
                new Runnable() {
                    @Override
                    public void run() {
                        if (!gasPuzzleSolved) {
                            toastText.setText(R.string.death_description);
                            toast.show();
                            SaveUtility.player.setDead(true);
                            SoundHelper.PlayEventSounds(R.raw.death3);
                            nullifyAndRemoveButtonsFromParent();
                            new Handler().postDelayed(
                                    new Runnable() {
                                        @Override
                                        public void run() {
                                            SaveUtility.saveProgress(x_cord, y_cord, score);
                                            FragmentTransaction transaction =
                                                    StartScreenActivity.activity.getSupportFragmentManager().beginTransaction();

                                            transaction.replace(R.id.startscreenlayout,
                                                    StartScreenActivity.activity
                                                            .getSupportFragmentManager()
                                                            .findFragmentByTag("startScreen")
                                            );

                                            transaction.commitAllowingStateLoss();
                                            getActivity().finish();

                                        }
                                    },
                                    2000);
                        }
                    }
                },
                9000);


        animateGas();
        animateSkull();
    }

    public void animateGas() {
        gasView.setBackgroundColor(Color.argb(153, 80, 179, 80));
        gasView.startAnimation(fadeInGas);
    }

    public void animateSkull() {
        skullView.startAnimation(fadeInSkull);
    }

    public boolean fixGasLeak() {
        boolean hasDuctTape = SaveUtility.alreadyHasItem("1");

        if (fadeInSkull != null) {
            if (hasDuctTape && !fadeInSkull.hasEnded()) {
                SaveUtility.removeItemFromCharacter("1");
                toastText.setText(R.string.gas_pipe_description2);
                toast.show();

                fadeInSkull.cancel();
                fadeInGas.cancel();

                gasView.setBackgroundColor(Color.argb(0, 0, 0, 0));
                mainRelativeLayout.removeView(skullView);

                Utilities.room11 = true;
                SaveUtility.player.setRoom11(true);
                gasPuzzleSolved = true;
                eventTriggeredSwap("11");

            } else if (!fadeInSkull.hasEnded()) {
                toastText.setText(R.string.gas_pipe_description);
                toast.show();
            }
        }
        return gasPuzzleSolved;
    }

    public void openLockFragment() {
        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        lockFragment = new CombinationLockFragment();
        ft.add(R.id.mainRel, lockFragment, "combinationLock")
                .addToBackStack("combinationLock")
                .commit();

        mainRelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeFragment();
                v.setClickable(false);
            }
        });
    }

    public void removeFragment() {
        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        ft.hide(lockFragment);
        ft.commit();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void fadeOutButtons(List<Button> buttons) {
        for (Button b : buttons) {
            b.startAnimation(fadeout_buttons);
        }


    }

    private void fadeInButtons(List<Button> buttons) {
        for (Button b : buttons) {
            b.startAnimation(fadein_buttons);
        }

    }
}
