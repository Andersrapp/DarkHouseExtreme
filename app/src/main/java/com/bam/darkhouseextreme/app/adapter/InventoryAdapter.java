package com.bam.darkhouseextreme.app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import com.bam.darkhouseextreme.app.R;
import com.bam.darkhouseextreme.app.model.Item;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Benjamin on 5/25/2015.
 *
 * Adapter to be able to show a list of items in game.
 *
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

        int itemID = (int) item.getId();

        switch (itemID) {
            case 1:
                itemHolder.itemImage.setBackgroundResource(R.drawable.duct_tape);
                break;
            case 2:
                itemHolder.itemImage.setBackgroundResource(R.drawable.key);
                break;
            case 7:
                itemHolder.itemImage.setBackgroundResource(R.drawable.hour_hand);
                break;
            case 8:
                itemHolder.itemImage.setBackgroundResource(R.drawable.minute_hand);
                break;
            case 9:
                itemHolder.itemImage.setBackgroundResource(R.drawable.lever_handle_inventory);
                break;
            case 11:
                itemHolder.itemImage.setBackgroundResource(R.drawable.bucket);
                break;
            case 12:
                itemHolder.itemImage.setBackgroundResource(R.drawable.bucket_filled);
                break;
            case 13:
                itemHolder.itemImage.setBackgroundResource(R.drawable.master_key);
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
