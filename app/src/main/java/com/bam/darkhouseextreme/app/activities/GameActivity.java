package com.bam.darkhouseextreme.app.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import com.bam.darkhouseextreme.app.R;
import com.bam.darkhouseextreme.app.fragments.RoomFragment;
import com.bam.darkhouseextreme.app.utilities.Utilities;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Chobii on 28/04/15.
 */
public class GameActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setButtonsForRooms();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.gameactivity);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.gamelayout, new RoomFragment(), "room")
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

    private void setButtonsForRooms() {
        List<Button> room1 = new ArrayList<>();
        Button doorRight = new Button(getApplicationContext());
        Button doorDown = new Button(getApplicationContext());
        Button paper = new Button(getApplicationContext());
        Button skeleton = new Button(getApplicationContext());
        room1.add(doorRight);
        room1.add(doorDown);
        room1.add(paper);
        room1.add(skeleton);
        Utilities.setButtonsForRooms("02", room1);


        List<Button> room2 = new ArrayList<>();
        Button doorUp = new Button(getApplicationContext());
        Button doorRight2 = new Button(getApplicationContext());
        Button key = new Button(getApplicationContext());
        room2.add(doorUp);
        room2.add(doorRight2);
        room2.add(key);
        Utilities.setButtonsForRooms("01", room2);

    }

    private void setButtonsForRoom02() {
        List<Button> room1 = new ArrayList<>();
        Button doorRight = new Button(getApplicationContext());
        Button doorDown = new Button(getApplicationContext());
        Button paper = new Button(getApplicationContext());
        Button skeleton = new Button(getApplicationContext());
        room1.add(doorRight);
        room1.add(doorDown);
        room1.add(paper);
        room1.add(skeleton);
        Utilities.setButtonsForRooms("02", room1);
    }
}
