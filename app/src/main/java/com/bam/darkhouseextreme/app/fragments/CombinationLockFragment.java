package com.bam.darkhouseextreme.app.fragments;


import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.bam.darkhouseextreme.app.R;
import com.bam.darkhouseextreme.app.helper.SoundHelper;
import com.bam.darkhouseextreme.app.utilities.SaveUtility;
import com.bam.darkhouseextreme.app.utilities.Utilities;

/**
 * Fragment for the CombinationLock in room33.
 *
 */

public class CombinationLockFragment extends Fragment {

    Context context;
    View root;

    CombinationLockFragment fragment;

    TextView number1;
    TextView number2;
    TextView number3;
    TextView number4;

    ImageView greenLight, redLight;

    Button resetButton, enterButton;

//    MediaPlayer mediaPlayer;

    int number1Value = 0;
    int number2Value = 0;
    int number3Value = 0;
    int number4Value = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        context = getActivity().getApplicationContext();
        root = inflater.inflate(R.layout.fragment_combination_lock, container, false);

        fragment = this;

        number1 = (TextView) root.findViewById(R.id.number1);
        number2 = (TextView) root.findViewById(R.id.number2);
        number3 = (TextView) root.findViewById(R.id.number3);
        number4 = (TextView) root.findViewById(R.id.number4);

        greenLight = (ImageView) root.findViewById(R.id.greenLight);
        redLight = (ImageView) root.findViewById(R.id.redLight);

        number1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                number1Value = (number1Value + 1) % 10;
                number1.setText(String.valueOf(number1Value));
            }
        });

        number2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                number2Value = (number2Value + 1) % 10;
                number2.setText(String.valueOf(number2Value));
            }
        });

        number3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                number3Value = (number3Value + 1) % 10;
                number3.setText(String.valueOf(number3Value));
            }
        });

        number4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                number4Value = (number4Value + 1) % 10;
                number4.setText(String.valueOf(number4Value));
            }
        });

        enterButton = (Button) root.findViewById(R.id.enterButton);
        resetButton = (Button) root.findViewById(R.id.resetButton);

        enterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String code;
                String n1 = number1.getText().toString();
                String n2 = number2.getText().toString();
                String n3 = number3.getText().toString();
                String n4 = number4.getText().toString();
                code = n1 + n2 + n3 + n4;
                checkCode(code);
            }
        });

        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                number1Value = number2Value = number3Value = number4Value = 0;
                SoundHelper.playEventSounds(R.raw.reset_dials);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        number1.setText(String.valueOf(number1Value));
                        number2.setText(String.valueOf(number2Value));
                        number3.setText(String.valueOf(number3Value));
                        number4.setText(String.valueOf(number4Value));
                    }
                }, 100);
            }
        });
        return root;
    }

    /**
     * Check if the code input by player is correct. if yes, unlock the door.
     * @param code - code input by player.
     */

    public void checkCode(String code) {
        int correctCode = 1240;

        int enteredCode = Integer.parseInt(code);
        if (correctCode == enteredCode) {
            SaveUtility.player.setRoom33(true);
            Utilities.room33 = true;

            greenLight.setBackgroundResource(R.drawable.lit_green_led);
            SoundHelper.playEventSounds(R.raw.combination_door_unlock);

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    greenLight.setBackgroundResource(R.drawable.unlit_green_led);
                    getActivity().getSupportFragmentManager().beginTransaction().remove(fragment).commit();
                }
            }, 2000);
        } else {
            resetButton.setClickable(false);
            enterButton.setClickable(false);
            SoundHelper.playEventSounds(R.raw.red_light_on);

            redLight.setBackgroundResource(R.drawable.lit_red_led);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    SoundHelper.playEventSounds(R.raw.red_light_off);
                    redLight.setBackgroundResource(R.drawable.unlit_red_led);
                    resetButton.setClickable(true);
                    enterButton.setClickable(true);
                }
            }, 2000);
        }
    }
}
