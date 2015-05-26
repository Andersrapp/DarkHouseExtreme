package com.bam.darkhouseextreme.app.adapter;

import android.content.Context;
import android.media.Image;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bam.darkhouseextreme.app.R;
import com.bam.darkhouseextreme.app.model.Item;
import com.bam.darkhouseextreme.app.model.Player;
import com.bam.darkhouseextreme.app.utilities.SaveUtility;
import com.bam.darkhouseextreme.app.utilities.Utilities;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Benjamin on 5/25/2015.
 */
public class InventoryAdapter extends ArrayAdapter<Item> {

    private Context context;
    private int layoutResourceId;
    private List<Item> items = new ArrayList<>();

    public InventoryAdapter(Context context, int resource, List<Item> items) {
        super(context, resource, items);
        this.context = context;
        this.layoutResourceId = resource;
        this.items = items;
    }

    @Override
    public Item getItem(int position) {
        return super.getItem(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;

        ItemHolder itemHolder;
        if (row == null) {
            LayoutInflater layoutInflater = LayoutInflater.from(context);
            row = layoutInflater.inflate(layoutResourceId, parent, false);
            itemHolder = new ItemHolder();
            itemHolder.itemImage = (ImageView) row.findViewById(R.id.inventoryItem);
            row.setTag(itemHolder);
        } else {
            itemHolder = (ItemHolder) row.getTag();
        }

        Item item = items.get(position);
        Log.d("INVENTORYADAPTER", String.valueOf(item.getId()));

        int itemID = (int) item.getId();

        switch (itemID) {
            case 1:
                itemHolder.itemImage.setBackgroundResource(R.drawable.duct_tape);
                Log.d("INVENTORYADAPTER", String.valueOf(itemID));
                break;
            case 5:
                itemHolder.itemImage.setBackgroundResource(R.drawable.key);
                Log.d("INVENTORYADAPTER", String.valueOf(itemID));
                break;
            case 7:
                itemHolder.itemImage.setBackgroundResource(R.drawable.hour_hand);
                Log.d("INVENTORYADAPTER", String.valueOf(itemID));
                break;
            case 8:
                itemHolder.itemImage.setBackgroundResource(R.drawable.minute_hand);
                Log.d("INVENTORYADAPTER", String.valueOf(itemID));
                break;
            case 9:
                itemHolder.itemImage.setBackgroundResource(R.drawable.lever_handle_inventory);
                Log.d("INVENTORYADAPTER", String.valueOf(itemID));
                break;
            case 11:
                itemHolder.itemImage.setBackgroundResource(R.drawable.bucket);
                Log.d("INVENTORYADAPTER", String.valueOf(itemID));
                break;
            case 12:
                itemHolder.itemImage.setBackgroundResource(R.drawable.bucket_filled);
                Log.d("INVENTORYADAPTER", String.valueOf(itemID));
                break;
            case 13:
                itemHolder.itemImage.setBackgroundResource(R.drawable.master_key);
                Log.d("INVENTORYADAPTER", String.valueOf(itemID));
                break;
            default:
                break;
        }

        return row;
    }

    private static class ItemHolder {
        ImageView itemImage;
    }
}
