package com.bam.darkhouseextreme.app.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RelativeLayout;
import com.bam.darkhouseextreme.app.R;
import com.bam.darkhouseextreme.app.fragments.RoomFragment;
import com.bam.darkhouseextreme.app.utilities.SaveUtility;
import com.bam.darkhouseextreme.app.utilities.Utilities;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Chobii on 28/04/15.
 */
public class GameActivity extends FragmentActivity {

    private RoomFragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        if (Utilities.buttonsForRooms.isEmpty()) {

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

        }

        fragment = new RoomFragment();

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
                            Utilities.buttonsForRooms.get("01").remove(v);
                        }
                    }
            );
        }

        if (!SaveUtility.alreadyHasItem("3")) {
            Button skeleton = new Button(getApplicationContext());
            skeleton.setTag("skeleton");
            skeleton.setBackgroundResource(R.drawable.item_button);
            room1.add(skeleton);
        }

        doorDown.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        fragment.isRoom(0, 0);
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

        Utilities.setButtonsForRooms("01", room1);
    }

    private void setButtonsForRoom01() {

        List<Button> room2 = new ArrayList<>();
        Button doorUp = new Button(getApplicationContext());
        Button doorRight2 = new Button(getApplicationContext());
        doorRight2.setBackgroundResource(R.drawable.item_button);
        doorUp.setBackgroundResource(R.drawable.item_button);
        room2.add(doorUp);
        room2.add(doorRight2);

        if (!SaveUtility.alreadyHasItem("1")) {
            Button key = new Button(getApplicationContext());
            key.setBackgroundResource(R.drawable.item_button);
            room2.add(key);

            key.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            RelativeLayout layout = (RelativeLayout) findViewById(R.id.mainRel);
                            layout.removeView(v);
                            Utilities.buttonsForRooms.get("00").remove(v);
                        }
                    }
            );

        }

        doorUp.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        fragment.isRoom(0, 1);
                    }
                }
        );

        doorRight2.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        fragment.isRoom(1, 1);
                    }
                }
        );

        Utilities.setButtonsForRooms("00", room2);
    }

    private void setButtonsForRoom11() {

        List<Button> buttons = new ArrayList<>();

        Button doorRight = new Button(getApplicationContext());
        Button doorLeft = new Button(getApplicationContext());

        buttons.add(doorLeft);
        buttons.add(doorRight);

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

        Utilities.setButtonsForRooms("11", buttons);

    }

    private void setButtonsForRoom21() {

        List<Button> buttons = new ArrayList<>();

        Button doorLeft = new Button(getApplicationContext());
        Button doorUp = new Button(getApplicationContext());
        Button doorDown = new Button(getApplicationContext());

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

    private void setButtonsForRoom20() {

        List<Button> buttons = new ArrayList<>();

        Button doorUp = new Button(getApplicationContext());

        buttons.add(doorUp);

        doorUp.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        fragment.isRoom(2, 1);
                    }
                }
        );

        Utilities.setButtonsForRooms("20", buttons);

    }

    private void setButtonsForRoom22() {

        List<Button> buttons = new ArrayList<>();

        Button doorDown = new Button(getApplicationContext());
        Button doorLeft = new Button(getApplicationContext());
        Button doorRight = new Button(getApplicationContext());
        Button doorUp = new Button(getApplicationContext());

        buttons.add(doorDown);
        buttons.add(doorLeft);
        buttons.add(doorRight);
        buttons.add(doorUp)

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
                        fragment.isRoom(1, 2);
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

        Utilities.setButtonsForRooms("22", buttons);
    }

    private void setButtonsForRoom12() {

        List<Button> buttons = new ArrayList<>();

        Button doorRight = new Button(getApplicationContext());
        Button doorLeft = new Button(getApplicationContext());

        buttons.add(doorRight);
        buttons.add(doorLeft);

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

        buttons.add(doorRight);

        doorRight.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        fragment.isRoom(2, 3),
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

        buttons.add(doorDown);
        buttons.add(doorLeft);
        buttons.add(doorRight);

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
}
