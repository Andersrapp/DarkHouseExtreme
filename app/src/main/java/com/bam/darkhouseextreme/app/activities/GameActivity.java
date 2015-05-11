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
        Button paper = new Button(getApplicationContext());
        Button skeleton = new Button(getApplicationContext());
        skeleton.setTag("skeleton");
        paper.setTag("paper");
        doorRight.setBackgroundResource(R.drawable.item_button);
        doorDown.setBackgroundResource(R.drawable.item_button);
        paper.setBackgroundResource(R.drawable.item_button);
        skeleton.setBackgroundResource(R.drawable.item_button);
        room1.add(doorRight);
        room1.add(doorDown);
        room1.add(paper);
        room1.add(skeleton);
        for (Button b : room1) {
            b.setMinWidth(50);
            b.setMinHeight(50);
            b.setMinimumWidth(50);
            b.setAlpha(1.0f);
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
        Utilities.setButtonsForRooms("01", room1);
    }

    private void setButtonsForRoom01() {

        List<Button> room2 = new ArrayList<>();
        Button doorUp = new Button(getApplicationContext());
        Button doorRight2 = new Button(getApplicationContext());
        Button key = new Button(getApplicationContext());
        doorRight2.setBackgroundResource(R.drawable.item_button);
        doorUp.setBackgroundResource(R.drawable.item_button);
        key.setBackgroundResource(R.drawable.item_button);
        room2.add(doorUp);
        room2.add(doorRight2);
        room2.add(key);

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
        Utilities.setButtonsForRooms("00", room2);
    }

    private void setButtonsForRoom11() {

    }

    private void setButtonsForRoom21() {

    }

    private void setButtonsForRoom20() {

    }

    private void setButtonsForRoom22() {

    }

    private void setButtonsForRoom12() {

    }

    private void setButtonsForRoom13() {

    }

    private void setButtonsForRoom23() {

    }

    private void setButtonsForRoom33() {

    }

    private void setButtonsForRoom32() {

    }
}
