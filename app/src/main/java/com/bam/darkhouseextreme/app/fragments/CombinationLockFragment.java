package com.bam.darkhouseextreme.app.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.bam.darkhouseextreme.app.R;
import com.bam.darkhouseextreme.app.utilities.SaveUtility;
import com.bam.darkhouseextreme.app.utilities.Utilities;

public class CombinationLockFragment extends Fragment {

    Context context;
    View root;

    TextView number1;
    TextView number2;
    TextView number3;
    TextView number4;

    int number1Value = 0;
    int number2Value = 0;
    int number3Value = 0;
    int number4Value = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        context = getActivity().getApplicationContext();
        root = inflater.inflate(R.layout.fragment_combination_lock, container, false);

        number1 = (TextView) root.findViewById(R.id.number1);
        number2 = (TextView) root.findViewById(R.id.number2);
        number3 = (TextView) root.findViewById(R.id.number3);
        number4 = (TextView) root.findViewById(R.id.number4);

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

        Button enterButton = (Button) root.findViewById(R.id.enterButton);
        Button resetButton = (Button) root.findViewById(R.id.resetButton);

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
                number1.setText(String.valueOf(number1Value));
                number2.setText(String.valueOf(number2Value));
                number3.setText(String.valueOf(number3Value));
                number4.setText(String.valueOf(number4Value));
            }
        });
        return root;
    }

    public void checkCode(String code) {
        int correctCode = 1240;

        int enteredCode = Integer.parseInt(code);
        if (correctCode == enteredCode) {
            SaveUtility.player.setRoom33(true);
            Utilities.room33 = true;

            Toast.makeText(context, "Correct!", Toast.LENGTH_SHORT).show();
            getActivity().getSupportFragmentManager().beginTransaction().remove(this).commit();
        } else {
            Toast.makeText(context, "Try again!!", Toast.LENGTH_SHORT).show();

        }
    }

}
