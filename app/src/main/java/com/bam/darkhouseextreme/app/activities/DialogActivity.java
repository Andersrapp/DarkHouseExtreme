package com.bam.darkhouseextreme.app.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import com.bam.darkhouseextreme.app.R;
import com.bam.darkhouseextreme.app.utilities.Utilities;

/**
 * Created by Chobii on 20/05/15.
 */
public class DialogActivity extends Activity {

    private ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_event);

        Bundle b = getIntent().getExtras();
        int imageInt = b.getInt("image");


        getWindow().getAttributes().height = (Utilities.screenHeight / 2) + (Utilities.screenHeight / 8);
        getWindow().getAttributes().width = (Utilities.screenWidth / 2) + (Utilities.screenWidth / 8);

        image = (ImageView) findViewById(R.id.dialogimage);
        image.setImageResource(imageInt);
        image.setClickable(true);

        image.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                }
        );
    }
}
