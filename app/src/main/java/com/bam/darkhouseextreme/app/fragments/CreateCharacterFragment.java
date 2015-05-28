package com.bam.darkhouseextreme.app.fragments;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import com.bam.darkhouseextreme.app.R;
import com.bam.darkhouseextreme.app.activities.GameActivity;
import com.bam.darkhouseextreme.app.utilities.SaveUtility;
import com.bam.darkhouseextreme.app.utilities.Utilities;

/**
 * Created by Chobii on 28/04/15.
 * Simple fragment to handle new character creation.
 *
 */

public class CreateCharacterFragment extends Fragment {

    public Button ok;
    private EditText editText;
    private Context context;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        context = getActivity().getApplicationContext();

        final Typeface fonts = Typeface.createFromAsset(context.getAssets(), "fonts/MISFITS_.TTF");
        final View root = inflater.inflate(R.layout.createcharacterfragment, container, false);
        ok = (Button)root.findViewById(R.id.okGo);
        editText = (EditText)root.findViewById(R.id.createEdit);

        Utilities.setFontForView(root, fonts);
        setGo();
        return root;
    }

    /**
     * Starts the game with the created character
     *
     */
    public void setGo() {
        ok.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String name = editText.getText().toString();
                        SaveUtility.createCharacter(name, context.getResources());
                        Intent intent = new Intent(context, GameActivity.class);
                        startActivity(intent);
                    }
                }
        );
    }





}
