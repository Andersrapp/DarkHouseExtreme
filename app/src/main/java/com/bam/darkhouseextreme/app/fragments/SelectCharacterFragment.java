package com.bam.darkhouseextreme.app.fragments;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
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
 *
 * Handles the select character option in menu.
 *
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
    private TextView toastText;
    private Toast toast;

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
        final Typeface font = Typeface.createFromAsset(context.getAssets(), "fonts/MISFITS_.TTF");

        View toastView = inflater.inflate(R.layout.custom_toast, (ViewGroup) root.findViewById(R.id.toast_root));

        toast = new Toast(context);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 200);
        toast.setView(toastView);

        toastText = (TextView) toastView.findViewById(R.id.toast_text);
        Utilities.setFontForView(toastText, font);

        return root;
    }

    /**
     * Makes a selection of the character in the list that is clicked on
     *
     */
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

    /**
     * Makes sure previously selected character gets unselected when a new character gets selected
     */
    public void clearSelection()
    {
        if(lastSelectedView != null && !player.isDead()) {
            lastSelectedView.setBackgroundResource(R.drawable.alive_list_row_bg);
        } else if (lastSelectedView != null) {
            lastSelectedView.setBackgroundResource(R.drawable.dead_list_row_bg);
        }
    }

    /**
     * Removes a character completely from the game.
     */
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
                    toastText.setText("No character selected");
                    toast.show();
                }
            }
        });
    }

    /**
     * Continues the game with the selected character as long as the character has not died in
     * the game.
     */
    public void chooseSelectedCharacter() {
        selectCharacterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (player != null && !player.isDead()) {
                    SaveUtility.loadCharacter(player);
                    Intent intent = new Intent(context, GameActivity.class);
                    startActivity(intent);
                } else if (player != null && player.isDead()) {
                    toastText.setText("Character is dead");
                    toast.show();
                } else {
                    toastText.setText("No character selected");
                    toast.show();
                }
            }
        });
    }
}
