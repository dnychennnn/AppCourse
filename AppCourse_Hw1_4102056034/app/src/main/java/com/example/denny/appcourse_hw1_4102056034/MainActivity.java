package com.example.denny.appcourse_hw1_4102056034;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText editH = (EditText)findViewById(R.id.editText_height);
        final EditText editW = (EditText)findViewById(R.id.editText_weight);
        final EditText editbmi = (EditText)findViewById(R.id.editText_bmi);

        Button btCal = (Button)findViewById(R.id.button_cal);

        btCal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                float val= 0;
                Log.d("AAA", "In");
                val = Float.valueOf(editW.getText().toString())/Float.valueOf(editH.getText().toString())*Float.valueOf(editH.getText().toString());
                Log.d("AAA", val+"");
                editbmi.setText(val+"");
            }
        });
    }
}
