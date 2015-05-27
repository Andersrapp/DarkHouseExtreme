package com.bam.darkhouseextreme.app.helper;

import android.content.Context;
import android.media.MediaPlayer;

/**
 * Created by Chobii on 26/05/15.
 *
 * Helper class to handle the music and sounds in the game.
 *
 */
public class SoundHelper {

    private static Context context;
    public static MediaPlayer backGroundMusic;
    public static MediaPlayer eventSounds;
    public static int currentlyPlaying;
    private static int currentPos;

    public static void initContext(Context context1) {
        context = context1;

    }

    public static void playBackGroundMusic(int soundID) {
        currentlyPlaying = soundID;
        backGroundMusic = MediaPlayer.create(context, soundID);
        backGroundMusic.start();

    }

    public static void stopBackGroundMusic() {
        if (backGroundMusic != null) {
            backGroundMusic.stop();
            backGroundMusic.release();
            backGroundMusic = null;
        }
    }

    public static void pauseBackGroundMusic() {
        currentPos = backGroundMusic.getCurrentPosition();
        backGroundMusic.pause();
    }

    public static void resumeBackGroundMusic() {
        if (backGroundMusic != null) {
            backGroundMusic.seekTo(currentPos);
            backGroundMusic.start();
        }
    }

    public static void playEventSounds(int soundID) {
        eventSounds = MediaPlayer.create(context, soundID);
        eventSounds.start();
        backGroundMusic.setLooping(true);
    }


}
