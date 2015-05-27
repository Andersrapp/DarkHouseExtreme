package com.bam.darkhouseextreme.app;

import android.content.Context;
import android.media.MediaPlayer;
import android.test.AndroidTestCase;

import com.bam.darkhouseextreme.app.helper.SoundHelper;

/**
 * Created by Anders on 2015-05-27.
 */
public class SoundHelperTest extends AndroidTestCase {


    public Context context;
//
//    public SoundHelperTest() {
//        super();
//    }


    @Override
    protected void setUp() throws Exception {
        super.setUp();

        context = getContext();
        SoundHelper.initContext(context);
        SoundHelper.playBackGroundMusic(R.raw.menu_music);
    }

    public void testIsMusicPlaying() {

        assertTrue(SoundHelper.backGroundMusic.isPlaying());
    }

    public void testMusicIsPaused() {
        SoundHelper.backGroundMusic.pause();
        assertTrue(!SoundHelper.backGroundMusic.isPlaying());

    }

    public void testMusicIsNotPlaying() {
        SoundHelper.backGroundMusic.stop();
        assertTrue(!SoundHelper.backGroundMusic.isPlaying());
    }


}


