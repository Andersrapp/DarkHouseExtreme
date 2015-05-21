package com.bam.darkhouseextreme.app.fragments;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.bam.darkhouseextreme.app.R;
import com.bam.darkhouseextreme.app.activities.GameActivity;
import com.bam.darkhouseextreme.app.adapter.CharacterListAdapter;
import com.bam.darkhouseextreme.app.model.Player;
import com.bam.darkhouseextreme.app.utilities.SaveUtility;
import com.bam.darkhouseextreme.app.utilities.Utilities;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Chobii on 28/04/15.
 */
public class SelectCharacterFragment extends Fragment {

    private Context context;
    private Button deleteBtn;
    private Button selectCharacterBtn;
    private List<Player> players = new ArrayList<>();
    private CharacterListAdapter characterListAdapter;
    private ListView characterListView;
    private Player player;
    public static View lastSelectedView = null;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        context = getActivity().getApplicationContext();
        final View root = inflater.inflate(R.layout.selectcharacterfragment, container, false);
        final Typeface fonts = Typeface.createFromAsset(context.getAssets(), "fonts/MISFITS_.TTF");

        players = SaveUtility.getAllCharacters();

        characterListView = (ListView) root.findViewById(R.id.characterList);
        characterListView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        deleteBtn = (Button) root.findViewById(R.id.deleteCharacterButton);
        selectCharacterBtn = (Button) root.findViewById(R.id.loadGameButton);

        characterListAdapter = new CharacterListAdapter(context, R.layout.characterselectionrow, players);

        if (characterListView != null) {
            characterListView.setAdapter(characterListAdapter);
        }

        Utilities.setFontForView(root, fonts);
        selectCharacter();
        chooseSelectedCharacter();
        deleteCharacter();

        return root;
    }

    public void selectCharacter() {
        characterListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (player != null) {
                    clearSelection();
                }
                player = players.get(position);
                lastSelectedView = view;

                if (!player.isDead()) {
                    view.setBackgroundResource(R.drawable.selected_alive_list_row_bg);
                } else {
                    view.setBackgroundResource(R.drawable.selected_dead_list_row_bg);

                }
            }

        });
    }
    public void clearSelection()
    {
        if(lastSelectedView != null && !player.isDead()) {
            lastSelectedView.setBackgroundResource(R.drawable.alive_list_row_bg);
        } else if (lastSelectedView != null) {
            lastSelectedView.setBackgroundResource(R.drawable.dead_list_row_bg);
        }
    }

    public void deleteCharacter() {
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (player != null) {
                    SaveUtility.deleteCharacter(player);
                    clearSelection();
                    players.remove(player);
                    player = null;
                    lastSelectedView = null;
                    characterListAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(context, "No character selected", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void chooseSelectedCharacter() {
        selectCharacterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (player != null && !player.isDead()) {
                    SaveUtility.loadCharacter(player);
                    Intent intent = new Intent(context, GameActivity.class);
                    startActivity(intent);
                } else if (player.isDead()) {
                    Toast.makeText(context, "Character is dead", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "No character selected", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
