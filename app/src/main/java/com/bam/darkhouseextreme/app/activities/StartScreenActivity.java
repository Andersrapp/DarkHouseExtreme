package com.bam.darkhouseextreme.app.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import com.bam.darkhouseextreme.app.R;
import com.bam.darkhouseextreme.app.fragments.StartScreenFragment;
import com.bam.darkhouseextreme.app.helper.SoundHelper;
import com.bam.darkhouseextreme.app.utilities.SaveUtility;
import com.bam.darkhouseextreme.app.utilities.Utilities;

/**
 * The first thing to run when our application is run.
 * Uses several fragments for different pages in the menu.
 *
 */

public class StartScreenActivity extends FragmentActivity {

    public static FragmentActivity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Log.d("Start", "Created");

        activity = this;
        Utilities.setContext(getApplicationContext());

        if (SaveUtility.helper == null) {
            SaveUtility.setHelper(getApplicationContext());
        }

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        SoundHelper.initContext(getApplicationContext());

        super.onCreate(savedInstanceState);
        setContentView(R.layout.startactivity);
        if (savedInstanceState == null) {
            StartScreenFragment fragment = new StartScreenFragment();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.startscreenlayout, fragment, "startScreen")
                    .addToBackStack("StartScreen")
                    .commit();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("Woop", "Woop");
        if (SoundHelper.currentlyPlaying != R.raw.menu_music) {
            Log.d("Woop2", String.valueOf(R.raw.menu_music));
            SoundHelper.stopBackGroundMusic();
            SoundHelper.playBackGroundMusic(R.raw.menu_music);
        } else {
            if (!SoundHelper.backGroundMusic.isPlaying()) {
                SoundHelper.resumeBackGroundMusic();
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        SoundHelper.pauseBackGroundMusic();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onBackPressed() {
        final StartScreenFragment fragment =
                (StartScreenFragment) getSupportFragmentManager().findFragmentByTag("startScreen");

        if (fragment.isVisible()) {
            SoundHelper.stopBackGroundMusic();
            finish();
        } else {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.setCustomAnimations(R.anim.back_enter, R.anim.back_exit);
            transaction.replace(R.id.startscreenlayout, fragment);
            transaction.commit();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
