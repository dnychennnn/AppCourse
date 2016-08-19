package com.example.denny.appcourse_hw5_4102056034;

import android.app.AlertDialog;
import android.app.PendingIntent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    EditText editName, editAge;
    RadioButton male, female;
    Button Return;
    CheckBox dad, mom, lilsis, bigsis, bigbro, lilbro;
    Spinner spinner1, spinner2;
    private int MIN_MARK, MAX_MARK;
    private String[] city = {"Taichung", "Taipei"};
    final String[] district_TC = {"中區","東區","西區","南區","北區","西屯區","南屯區","北屯區","豐原區","大里區","太平區","清水區","沙鹿區","大甲區","東勢區","梧棲區","烏日區","神岡區","大肚區","大雅區","后里區","霧峰區","潭子區","龍井區","外埔區","和平區","石岡區","大安區","新社區"};
    final String[] district_TPE = {"中正區","大同區","中山區","松山區","大安區","萬華區","信義區","士林區","北投區","內湖區","南港區"};
    private String gender = "", family = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getView();

        MIN_MARK = 0;
        MAX_MARK = 130;


        // age region
        setRegion(editAge);

        // adaptive spinner
        ArrayAdapter<String> spin1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, city);
        final ArrayAdapter<String> spin2 = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, district_TC);
        final ArrayAdapter<String> spin3 = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, district_TPE);
        spinner1.setAdapter(spin1);

        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (parent.getSelectedItem().toString()) {
                    case "Taichung":
                        spinner2.setAdapter(spin2);
                        break;
                    case "Taipei":
                        spinner2.setAdapter(spin3);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(MainActivity.this, "您沒有選擇任何項目", Toast.LENGTH_LONG).show();

            }
        });


                // alert dialog
        if (male.isChecked()) {
            gender = male.getText().toString();
        } else {
            gender = female.getText().toString();
        }


        Return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(MainActivity.this)
                        .setTitle("Homework 5")
                        .setMessage(
                                "Name: " + editName.getText() + "\t" +
                                        "Age:  " + editAge.getText() + "\t" +
                                        "Gender:" + gender + "\t" +
                                        "Family:" + checkFamily() + "\t" +
                                        "Address:" + spinner1.getSelectedItem().toString()+ spinner2.getSelectedItem().toString() + "\t"
                        ).show();

                try{
                    FileWriter fw = new FileWriter("/sdcard/output.txt", false);
                    BufferedWriter bw = new BufferedWriter(fw); //將BufferedWeiter與FileWrite物件做連結
                    bw.write(
                            "Name: " + editName.getText() + "\t" +
                            "Age:  " + editAge.getText() + "\t" +
                                    "Gender:" + gender + "\t" +
                                    "Family:" + checkFamily() + "\t" +
                                    "Address:" + spinner1.getSelectedItem().toString()+ spinner2.getSelectedItem().toString() + "\t"
                    );
                    bw.newLine();
                    bw.close();
                }catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        });
    }



    private String checkFamily() {
        String s1 = "", s2 = "", s3 = "", s4 = "", s5 = "", s6 = "";
        if (dad.isChecked()) {
            s1 = dad.getText().toString();
        }else
        {
            s1="";
        }
        if (bigbro.isChecked()) {
            s2 = bigbro.getText().toString();
        }else
        {
            s2="";
        }
        if (lilbro.isChecked()) {
            s3 = lilbro.getText().toString();
        }else
        {
            s3="";
        }
        if (mom.isChecked()) {
            s4 = mom.getText().toString();
        }else
        {
            s4="";
        }
        if (bigsis.isChecked()) {
            s5 = bigsis.getText().toString();
        }else
        {
            s5="";
        }
        if (lilsis.isChecked()) {
            s6 = lilsis.getText().toString();
        }else
        {
            s6="";
        }

        return family = s1 + s2 + s3 + s4 + s5 + s6;

    }





    private void getView()
    {
        editName = (EditText)findViewById(R.id.editText_name);
        editAge = (EditText)findViewById(R.id.editText_age);
        Return = (Button)findViewById(R.id.button_return);
        male = (RadioButton)findViewById(R.id.radioButton);
        female = (RadioButton)findViewById(R.id.radioButton2);
        dad =(CheckBox)findViewById(R.id.checkBox);
        bigbro= (CheckBox)findViewById(R.id.checkBox2);
        lilbro = (CheckBox)findViewById(R.id.checkBox3);
        mom = (CheckBox)findViewById(R.id.checkBox4);
        bigsis = (CheckBox)findViewById(R.id.checkBox5);
        lilsis = (CheckBox)findViewById(R.id.checkBox6);
        spinner1 = (Spinner)findViewById(R.id.spinner);
        spinner2 = (Spinner)findViewById(R.id.spinner2);

    }

    private void setRegion(final EditText editAge){
        editAge.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (start > 1) {
                    if (MIN_MARK != -1 && MAX_MARK != -1) {
                        int num = Integer.parseInt(s.toString());
                        if (num > MAX_MARK) {
                            s = String.valueOf(MAX_MARK);
                            editAge.setText(s);
                        } else if (num < MIN_MARK)
                            s = String.valueOf(MIN_MARK);
                        return;
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s != null && !s.equals(""))
                {
                    if (MIN_MARK != -1 && MAX_MARK != -1)
                    {
                        int markVal = 0;
                        try
                        {
                            markVal = Integer.parseInt(s.toString());
                        }
                        catch (NumberFormatException e)
                        {
                            markVal = 0;
                        }
                        if (markVal > MAX_MARK)
                        {
                            Toast.makeText(getBaseContext(), "Age region is 0 ~ 130", Toast.LENGTH_SHORT).show();
                            editAge.setText(String.valueOf(MAX_MARK));
                        }
                        else if (markVal < MIN_MARK)
                        {
                            Toast.makeText(getBaseContext(), "Age region is 0 ~ 130", Toast.LENGTH_SHORT).show();
                            editAge.setText(String.valueOf(MIN_MARK));
                        }
                        return;
                    }
                }
            }
        });
    }
}



