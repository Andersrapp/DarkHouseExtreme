package com.bam.darkhouseextreme.app.activities;

import android.app.Dialog;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;
import com.bam.darkhouseextreme.app.R;
import com.bam.darkhouseextreme.app.fragments.RoomFragment;
import com.bam.darkhouseextreme.app.utilities.SaveUtility;
import com.bam.darkhouseextreme.app.utilities.Utilities;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Chobii on 28/04/15.
 */
public class GameActivity extends FragmentActivity {

    private RoomFragment fragment;
    private MediaPlayer mediaPlayer;
    private Dialog dialog;
    private ImageView dialogImage;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Utilities.setBooleanValues();
        initializeDialog();
        fragment = new RoomFragment();



            setButtonsForRoom02();
            setButtonsForRoom01();
            setButtonsForRoom11();
            setButtonsForRoom12();
            setButtonsForRoom13();
            setButtonsForRoom20();
            setButtonsForRoom21();
            setButtonsForRoom22();
            setButtonsForRoom23();
            setButtonsForRoom32();
            setButtonsForRoom33();



        Log.d("BAJSSS", fragment.toString());

        super.onCreate(savedInstanceState);
        setContentView(R.layout.gameactivity);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.gamelayout, fragment, "room")
                    .commit();
        }


    }

    @Override
    public void overridePendingTransition(int enterAnim, int exitAnim) {
        super.overridePendingTransition(enterAnim, exitAnim);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onBackPressed() {
        fragment.nullifyAndRemoveButtonsFromParent();
        FragmentTransaction transaction =
                StartScreenActivity.activity.getSupportFragmentManager().beginTransaction();

        transaction.replace(R.id.startscreenlayout,
                StartScreenActivity.activity
                        .getSupportFragmentManager()
                        .findFragmentByTag("startScreen")
        );

        transaction.commitAllowingStateLoss();
        finish();
    }

    private void setButtonsForRoom02() {

        List<Button> room1 = new ArrayList<>();

        Button doorRight = new Button(getApplicationContext());
        Button doorDown = new Button(getApplicationContext());

        doorRight.setBackgroundResource(R.drawable.item_button);
        doorDown.setBackgroundResource(R.drawable.item_button);

        room1.add(doorRight);
        room1.add(doorDown);

        if (!SaveUtility.alreadyHasItem("2")) {
            Button paper = new Button(getApplicationContext());
            paper.setTag("paper");
            paper.setBackgroundResource(R.drawable.item_button);
            room1.add(paper);

            paper.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            RelativeLayout layout = (RelativeLayout) findViewById(R.id.mainRel);
                            layout.removeView(v);
                            Utilities.buttonsForRooms.get("02").remove(v);
                        }
                    }
            );
        }

        if (!SaveUtility.alreadyHasItem("3")) {
            Button skeleton = new Button(getApplicationContext());
            skeleton.setTag("skeleton");
            skeleton.setBackgroundResource(R.drawable.item_button);
            room1.add(skeleton);

            skeleton.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
//                            dialogImage.setImageResource(R.drawable.book_dialog);
                            dialog.show();
                            dialog.getWindow().setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
                        }
                    }
            );
        }

        doorDown.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        fragment.isRoom(0, 1);
                    }
                }
        );

        doorRight.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        fragment.isRoom(1, 2);
                    }
                }
        );

        for (Button b : room1) {
            b.setMinWidth(50);
            b.setMinHeight(50);
            b.setMinimumWidth(50);
            b.setAlpha(1.0f);
        }

        Utilities.setButtonsForRooms("02", room1);
    }

    private void setButtonsForRoom01() {

        List<Button> room2 = new ArrayList<>();
        Button doorUp = new Button(getApplicationContext());
        Button doorRight2 = new Button(getApplicationContext());
        final Button key = new Button(getApplicationContext());
        doorRight2.setBackgroundResource(R.drawable.item_button);
        doorUp.setBackgroundResource(R.drawable.item_button);
        room2.add(doorUp);
        room2.add(doorRight2);



        if (!SaveUtility.alreadyHasItem("5")) {
            key.setBackgroundResource(R.drawable.key);
            if(Utilities.doorOpened("01") == 0) {
                key.setVisibility(View.GONE);
            }
            room2.add(key);

            key.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            SaveUtility.saveItemToCharacter("5");
                            RelativeLayout layout = (RelativeLayout) findViewById(R.id.mainRel);
                            layout.removeView(v);
                            Utilities.buttonsForRooms.get("01").remove(v);
                        }
                    }
            );
        }

        if (Utilities.doorOpened("01") == 0) {
            Button carpet = new Button(getApplicationContext());
            carpet.setBackgroundResource(R.drawable.item_button);
            room2.add(carpet);

            carpet.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Toast.makeText(getApplicationContext(), "You stumble on the carpet, flipping the side over.", Toast.LENGTH_SHORT).show();
                            RelativeLayout layout = (RelativeLayout) findViewById(R.id.mainRel);
                            layout.removeView(v);
                            Utilities.buttonsForRooms.get("01").remove(v);
                            SaveUtility.player.setRoom01(true);
                            Utilities.room01 = true;
                            fragment.eventTriggeredSwap("01");
                            key.setVisibility(View.VISIBLE);

                        }
                    }
            );
        }

        doorUp.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        fragment.isRoom(0, 2);
                    }
                }
        );

        doorRight2.setOnClickListener(
                new View.OnClickListener() {
                    int numOfClicks = 0;

                    @Override
                    public void onClick(View v) {

                        if (numOfClicks == 1 && SaveUtility.player.isRoom01()) {
                            fragment.isRoom(1, 1);
                        }
                        if (SaveUtility.alreadyHasItem("5") && numOfClicks == 0) {
                            Toast.makeText(getApplicationContext(), "You unlocked the door!", Toast.LENGTH_SHORT).show();
                            SaveUtility.player.setRoom01a(true);
                            Utilities.room01a = true;
                            fragment.eventTriggeredSwap("01a");
                            numOfClicks++;
                        } else {
                            Toast.makeText(getApplicationContext(), "Door is locked", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );

        Utilities.setButtonsForRooms("01", room2);
    }

    private void setButtonsForRoom11() {

        List<Button> buttons = new ArrayList<>();

        Button doorRight = new Button(getApplicationContext());
        Button doorLeft = new Button(getApplicationContext());
        Button clock = new Button(getApplicationContext());
        Button gasline = new Button(getApplicationContext());

        clock.setBackgroundResource(R.drawable.clock_no_hands);

        buttons.add(doorLeft);
        buttons.add(doorRight);
        buttons.add(clock);
        buttons.add(gasline);


        if (!SaveUtility.alreadyHasItem("4")) {

            Button hour = new Button(getApplicationContext());

            buttons.add(hour);

            hour.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            RelativeLayout layout = (RelativeLayout) findViewById(R.id.mainRel);
                            layout.removeView(v);
                            Utilities.buttonsForRooms.get("11").remove(v);
                        }
                    }
            );
        }

        doorLeft.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        fragment.isRoom(0, 1);
                    }
                }
        );

        doorRight.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        fragment.isRoom(2, 1);
                    }
                }
        );

        clock.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                }
        );

        gasline.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                }
        );

        Utilities.setButtonsForRooms("11", buttons);

    }

    private void setButtonsForRoom21b() {

        List<Button> buttons = new ArrayList<>();

        Button doorLeft = new Button(getApplicationContext());
        Button doorUp = new Button(getApplicationContext());
        Button doorDown = new Button(getApplicationContext());

        doorDown.setTag("door");

        buttons.add(doorDown);
        buttons.add(doorLeft);
        buttons.add(doorUp);

        doorDown.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        fragment.isRoom(2, 0);
                    }
                }
        );

        doorLeft.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        fragment.isRoom(1, 1);
                    }
                }
        );

        doorUp.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        fragment.isRoom(2, 2);
                    }
                }
        );

        Utilities.setButtonsForRooms("21", buttons);

    }

    private void setButtonsForRoom21a() {

        List<Button> buttons = new ArrayList<>();

        Button doorLeft = new Button(getApplicationContext());
        Button table = new Button(getApplicationContext());
        Button doorDown = new Button(getApplicationContext());

        table.setBackgroundResource(R.drawable.doorblockade);

        table.setTag("table");

        buttons.add(table);
        buttons.add(doorDown);
        buttons.add(doorLeft);

        if (!SaveUtility.alreadyHasItem("12")) {

            Button minuteHand = new Button(getApplicationContext());

            minuteHand.setBackgroundResource(R.drawable.minute_hand);
            minuteHand.setMinWidth(0);
            minuteHand.setMinimumWidth(0);
            minuteHand.setMinHeight(0);
            minuteHand.setMinimumHeight(0);
            buttons.add(minuteHand);

            minuteHand.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            SaveUtility.saveItemToCharacter("12");
                            RelativeLayout layout = (RelativeLayout) findViewById(R.id.mainRel);
                            layout.removeView(v);
                            Utilities.buttonsForRooms.get("21").remove(v);
                        }
                    }
            );
        }

        doorDown.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        fragment.isRoom(2, 0);
                    }
                }
        );

        doorLeft.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        fragment.isRoom(1, 1);
                    }
                }
        );

        table.setOnClickListener(
                new View.OnClickListener() {
                    int clickCount = 0;
                    @Override
                    public void onClick(View v) {
                        if (clickCount == 5) {
                            setButtonsForRoom21b();
                            fragment.eventTriggeredSwap("21");
                        }
                        if (clickCount < 5) {
                            fragment.moveTable();
                            clickCount++;
                        }
                    }
                }
        );

        Utilities.setButtonsForRooms("21", buttons);

    }

    private void setButtonsForRoom21() {

        List<Button> buttons = new ArrayList<>();

        if (SaveUtility.player.isRoom21()) {
            setButtonsForRoom21a();
        } else {
            Button light = new Button(getApplicationContext());

            buttons.add(light);

            light.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            SaveUtility.player.setRoom21(true);
                            Utilities.room21 = true;
                            setButtonsForRoom21a();
                            fragment.eventTriggeredSwap("21");
                        }
                    }
            );

            Utilities.setButtonsForRooms("21", buttons);
        }
    }

    private void setButtonsForRoom20() {

        List<Button> buttons = new ArrayList<>();

        Button doorUp = new Button(getApplicationContext());
        final Button toilet = new Button(getApplicationContext());
        final Button hourHand = new Button(getApplicationContext());

        buttons.add(doorUp);



        doorUp.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        fragment.isRoom(2, 1);
                    }
                }
        );

        if (Utilities.doorOpened("20") == 0) {

            toilet.setTag("toilet");

            toilet.setOnClickListener(
                    new View.OnClickListener() {
                        int clickCount = 0;
                        @Override
                        public void onClick(View v) {
                            switch (clickCount) {
                                case 0 :
                                    Toast.makeText(getApplicationContext(), "Don't really want to touch that", Toast.LENGTH_SHORT).show();
                                    clickCount++;
                                    break;
                                case 1:
                                    Toast.makeText(getApplicationContext(), "Someone made a mess in there", Toast.LENGTH_SHORT).show();
                                    clickCount++;
                                    break;
                                case 2:
                                    Toast.makeText(getApplicationContext(), "Alright, lets see if we can find some clues here", Toast.LENGTH_SHORT).show();
                                    clickCount++;
                                    break;
                                case 3:
                                    RelativeLayout layout = (RelativeLayout) findViewById(R.id.mainRel);
                                    layout.removeView(v);
                                    Utilities.buttonsForRooms.get("20").remove(v);
                                    hourHand.setVisibility(View.VISIBLE);
                            }
                        }
                    }
            );

            buttons.add(toilet);
        }

        if (!SaveUtility.alreadyHasItem("11")) {

            hourHand.setBackgroundResource(R.drawable.hour_hand);
            hourHand.setMinWidth(0);
            hourHand.setMinimumWidth(0);
            hourHand.setMinHeight(0);
            hourHand.setMinimumHeight(0);
            if (Utilities.doorOpened("20") == 0) {
                hourHand.setVisibility(View.INVISIBLE);
            }
            hourHand.setTag("hourHand");
            buttons.add(hourHand);

            hourHand.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            SaveUtility.saveItemToCharacter("11");
                            RelativeLayout layout = (RelativeLayout) findViewById(R.id.mainRel);
                            layout.removeView(v);
                            Utilities.buttonsForRooms.get("20").remove(v);
                        }
                    }
            );
        }

        Utilities.setButtonsForRooms("20", buttons);

    }

    private void setButtonsForRoom22() {

        List<Button> buttons = new ArrayList<>();

        Button doorDown = new Button(getApplicationContext());
        Button doorLeft = new Button(getApplicationContext());
        Button doorRight = new Button(getApplicationContext());
        Button doorUp = new Button(getApplicationContext());
        final Button book = new Button(getApplicationContext());

//        book.setBackgroundResource(R.drawable.book);


        buttons.add(doorDown);
        buttons.add(doorLeft);
        buttons.add(doorRight);
        buttons.add(doorUp);
        buttons.add(book);



        doorDown.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        fragment.isRoom(2, 1);
                    }
                }
        );

        doorLeft.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SaveUtility.player.setRoom12(true);
                        SaveUtility.player.setDead(true);
                        Utilities.room12 = true;
                        fragment.isRoom(1, 2);


                        Handler handler = new Handler();

                        handler.postDelayed(
                                new Runnable() {
                                    @Override
                                    public void run() {
                                        Random rn = new Random();
                                        int randomNumber = rn.nextInt(2) + 1;
                                        MediaPlayer mediaPlayer;
                                        switch (randomNumber) {
                                            case 1:
                                                mediaPlayer = MediaPlayer.create(GameActivity.this, R.raw.death1);
                                                mediaPlayer.setVolume(100, 100);
                                                mediaPlayer.start();
                                                break;
                                            case 2:
                                                mediaPlayer = MediaPlayer.create(GameActivity.this, R.raw.death2);
                                                mediaPlayer.setVolume(100, 100);
                                                mediaPlayer.start();
                                                break;
                                            default:
                                                break;

                                        }
                                    }
                                }
                                , 1000);

                        handler.postDelayed(
                                new Runnable() {
                                    @Override
                                    public void run() {
                                        onBackPressed();

                                    }
                                },
                                4000);

                    }
                }
        );

        doorRight.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        fragment.isRoom(3, 2);
                    }
                }
        );

        doorUp.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        fragment.isRoom(2, 3);
                    }
                }
        );

        book.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                }
        );

        Utilities.setButtonsForRooms("22", buttons);
    }

    private void setButtonsForRoom12() {

        List<Button> buttons = new ArrayList<>();

        Button doorRight = new Button(getApplicationContext());
        Button doorLeft = new Button(getApplicationContext());
        Button stairs = new Button(getApplicationContext());

        buttons.add(doorRight);
        buttons.add(doorLeft);
        buttons.add(stairs);

        stairs.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                }
        );

        doorLeft.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        fragment.isRoom(0, 2);
                    }
                }
        );

        Utilities.setButtonsForRooms("12", buttons);

    }

    private void setButtonsForRoom13() {

        List<Button> buttons = new ArrayList<>();

        Button doorRight = new Button(getApplicationContext());
        Button lever = new Button(getApplicationContext());

        buttons.add(doorRight);
        buttons.add(lever);

        lever.setOnClickListener(
                new View.OnClickListener() {
                    int clickCount = 0;
                    @Override
                    public void onClick(View v) {

                            switch (clickCount) {
                                case 0:
                                    Toast.makeText(getApplicationContext(), "Looks like a lever", Toast.LENGTH_SHORT).show();
                                    clickCount++;
                                    break;
                                case 1:
                                    if (SaveUtility.alreadyHasItem("16")) {
                                        RelativeLayout layout = (RelativeLayout) findViewById(R.id.mainRel);
                                        layout.removeView(v);
                                        fragment.eventTriggeredSwap("13");
                                        Toast.makeText(getApplicationContext(), "The handle fit perfectly", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(getApplicationContext(), "There should be something in this house i can use", Toast.LENGTH_SHORT).show();
                                    }
                            }
                        }
                    }
        );

        doorRight.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        fragment.isRoom(2, 3);
                    }
                }
        );

        Utilities.setButtonsForRooms("13", buttons);

    }

    private void setButtonsForRoom23() {

        List<Button> buttons = new ArrayList<>();

        Button doorLeft = new Button(getApplicationContext());
        Button doorRight = new Button(getApplicationContext());
        Button doorDown = new Button(getApplicationContext());
        Button painting = new Button(getApplicationContext());

        buttons.add(doorDown);
        buttons.add(doorLeft);
        buttons.add(doorRight);
        buttons.add(painting);

        painting.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                }
        );

        doorDown.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        fragment.isRoom(2, 2);
                    }
                }
        );

        doorLeft.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        fragment.isRoom(1, 3);
                    }
                }
        );

        doorRight.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        fragment.isRoom(3, 3);
                    }
                }
        );

        Utilities.setButtonsForRooms("23", buttons);
    }

    private void setButtonsForRoom33() {

        List<Button> buttons = new ArrayList<>();

        Button doorLeft = new Button(getApplicationContext());
        Button doorDown = new Button(getApplicationContext());

        buttons.add(doorDown);
        buttons.add(doorLeft);

        if (!SaveUtility.alreadyHasItem("9")) {

            Button bucket = new Button(getApplicationContext());

            buttons.add(bucket);

            bucket.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            RelativeLayout layout = (RelativeLayout) findViewById(R.id.mainRel);
                            layout.removeView(v);
                            Utilities.buttonsForRooms.get("33").remove(v);
                            SaveUtility.saveItemToCharacter("9");
                        }
                    }
            );
        }

        doorDown.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        fragment.isRoom(3, 2);
                    }
                }
        );

        doorLeft.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        fragment.isRoom(2, 3);
                    }
                }
        );

        Utilities.setButtonsForRooms("33", buttons);
    }

    private void setButtonsForRoom32() {

        List<Button> buttons = new ArrayList<>();

        Button doorUp = new Button(getApplicationContext());
        Button doorLeft = new Button(getApplicationContext());

        buttons.add(doorLeft);
        buttons.add(doorUp);

        if (!SaveUtility.alreadyHasItem("16")) {
            Button handle = new Button(getApplicationContext());
            handle.setBackgroundResource(R.drawable.lever_handle);

            buttons.add(handle);

            handle.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            RelativeLayout layout = (RelativeLayout) findViewById(R.id.mainRel);
                            layout.removeView(v);
                            Utilities.buttonsForRooms.get("32").remove(v);
                            SaveUtility.saveItemToCharacter("16");

                        }
                    }
            );
        }

        doorLeft.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        fragment.isRoom(2, 2);
                    }
                }
        );

        doorUp.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        fragment.isRoom(3, 3);
                    }
                }
        );

        Utilities.setButtonsForRooms("32", buttons);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mediaPlayer.stop();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mediaPlayer = MediaPlayer.create(GameActivity.this, R.raw.game_music);
        mediaPlayer.setLooping(true); // Set looping
        mediaPlayer.setVolume(100, 100);
        mediaPlayer.start();
    }

    private void initializeDialog() {
        dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_event);
        dialog.getWindow().setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
        dialogImage = (ImageView) dialog.findViewById(R.id.dialogimage);



    }
}
