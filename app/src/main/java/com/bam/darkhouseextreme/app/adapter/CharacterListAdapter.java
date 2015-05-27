package com.bam.darkhouseextreme.app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.bam.darkhouseextreme.app.R;
import com.bam.darkhouseextreme.app.fragments.SelectCharacterFragment;
import com.bam.darkhouseextreme.app.model.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Benjamin on 4/29/2015.
 *
 * Adapter to show characters found in the database in a list in select character in menus.
 *
 */

public class CharacterListAdapter extends ArrayAdapter<Player> {


    private Context context;
    private int layoutResourceId;
    private List<Player> data = new ArrayList<>();
    private View lastSelectedView;

    public CharacterListAdapter(Context context, int resource, List<Player> data) {
        super(context, resource, data);
        this.context = context;
        this.layoutResourceId = resource;
        this.data = data;
    }

    @Override
    public Player getItem(int position) {
        return super.getItem(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        lastSelectedView = SelectCharacterFragment.lastSelectedView;
        View row = convertView;

        PlayerHolder playerHolder;
        if (row == null) {
            LayoutInflater layoutInflater = LayoutInflater.from(context);
            row = layoutInflater.inflate(layoutResourceId, parent, false);
            playerHolder = new PlayerHolder();
            playerHolder.playerName = (TextView) row.findViewById(R.id.characterNameText);
            playerHolder.progress = (TextView) row.findViewById(R.id.progressText);
            row.setTag(playerHolder);
        } else {
            playerHolder = (PlayerHolder) row.getTag();
        }

        Player player = data.get(position);

        if (player.isDead()) {
            row.setBackgroundResource(R.drawable.dead_list_row_bg);
        } else {
            row.setBackgroundResource(R.drawable.alive_list_row_bg);
        }

        long playerId = player.getId();
        playerHolder.playerName.setText(player.getName());
        playerHolder.playerName.setTag(playerId);
        playerHolder.progress.setText(String.valueOf(player.getScore()));

        return row;
    }

    private static class PlayerHolder {
        TextView playerName;
        TextView progress;
    }

}
