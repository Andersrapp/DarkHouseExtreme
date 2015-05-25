package com.bam.darkhouseextreme.app;

import android.test.ActivityInstrumentationTestCase2;
import android.widget.ListView;

import com.bam.darkhouseextreme.app.activities.StartScreenActivity;
import com.bam.darkhouseextreme.app.adapter.CharacterListAdapter;
import com.bam.darkhouseextreme.app.model.Player;

import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Benjamin on 4/30/2015.
 */
public class SelectCharacterTest extends ActivityInstrumentationTestCase2<StartScreenActivity> {

    public Player player;
    public CharacterListAdapter cla;


    public SelectCharacterTest() {
        super(StartScreenActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        player = new Player(1, "Benji");
        List<Player> players = new ArrayList<>();
        players.add(player);
        cla = new CharacterListAdapter(getActivity().getApplicationContext(), R.layout.characterselectionrow, players);
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    public void testAddedCharacterToList() {
        assertEquals(player, cla.getItem(0));
    }

}
