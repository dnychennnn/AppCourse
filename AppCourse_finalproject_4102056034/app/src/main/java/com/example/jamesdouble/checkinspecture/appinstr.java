package com.example.jamesdouble.checkinspecture;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class appinstr extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appinstr);

        getActionBar().setTitle("打卡惡魔 - 關於");
        // Get a support ActionBar corresponding to this toolbar
        ActionBar ab = getActionBar();
        // Enable the Up button
        ab.setDisplayHomeAsUpEnabled(true);

        TextView annonce = (TextView) findViewById(R.id.textView8);
        annonce.setText("                    本APP純屬娛樂性質\n" +
                      "     請勿使用在有商業利益或利益交換之用\n" +
                      "       如遇任何法律問題,一概與作者無關\n");
    }
}
