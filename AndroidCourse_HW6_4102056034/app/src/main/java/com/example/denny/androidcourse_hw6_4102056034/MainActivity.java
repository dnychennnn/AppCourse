package com.example.denny.androidcourse_hw6_4102056034;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class MainActivity extends AppCompatActivity {

    SharedPreferences pref;
    SharedPreferences.Editor editor;

    EditText ed1, ed2, editContent;
    Button bt1, bt2;
    private static final String FILENAME = "long.txt";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ed1=(EditText)findViewById(R.id.editText);
        ed2=(EditText)findViewById(R.id.editText2);
        editContent = (EditText)findViewById(R.id.editContent);
        bt1=(Button)findViewById(R.id.button);
        bt2=(Button)findViewById(R.id.button2);

        DisplayFile(FILENAME);
//        pref = getSharedPreferences("preFile", MODE_PRIVATE);
//
//        editor = pref.edit();
//
//        editor.putString("NAME", "Denny");
//        editor.putString("PHONE", "092345345");
//        editor.putString("EMAIL", "den;ajsf@;sdflkas");
//        editor.commit();
    }

    private Button.OnClickListener listener = new Button.OnClickListener(){
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.button:
                    if(ed1.getText().toString().equals("") || ed2.getText().toString().equals(""))
                    {
                        Toast.makeText(getApplicationContext(), "ACCOUNT AND PASSWD REQUIRED", Toast.LENGTH_LONG).show();
                        break;
                    }
                    FileOutputStream fout = null;
                    BufferedOutputStream bout = null;
                    try{
                        fout=openFileOutput(FILENAME, MODE_APPEND);
                        bout = new BufferedOutputStream(fout);
                        bout.write(ed1.getText().toString().getBytes());
                        bout.write("\n".getBytes());
                        bout.write(ed2.getText().toString().getBytes());
                        bout.write("\n".getBytes());
                        bout.close();
                    }catch (Exception e)
                    { e.printStackTrace();}
                    DisplayFile(FILENAME);
                    ed1.setText("");
                    ed2.setText("");
                    break;

                case R.id.button2:
                    try{
                        fout = openFileOutput(FILENAME, MODE_PRIVATE);
                        fout.close();
                    }catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                    DisplayFile(FILENAME);
                    break;
            }
        }
    };

    private void DisplayFile(String filename)
    {
        FileInputStream fin = null;
        BufferedInputStream bin = null;
        try{
            fin = openFileInput(filename);
            bin = new BufferedInputStream(fin);
            byte[] buffbyte = new byte[20];
            ed1.setText("");

        do {
        int flag = bin.read(buffbyte);
        if(flag ==-1)break;
        else
            editContent.append(new String(buffbyte),0,flag);

    }while(true);
    bin.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}

