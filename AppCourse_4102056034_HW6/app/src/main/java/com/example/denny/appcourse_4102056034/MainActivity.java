package com.example.denny.appcourse_4102056034;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class MainActivity extends AppCompatActivity {
    SQLiteDatabase db;
    private EditText query;
    private Button execute;
    private ListView listView;
    private final static String CREATE_TABLE = "CREATE TABLE table01(_id INTEGER PRIMARY KEY,num INTEGER,data TEXT)", TAG = "MyLog";
    String str, itendata;
    int n=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findView();

        db = openOrCreateDatabase("db1.db", MODE_PRIVATE, null);
        try{
            db.execSQL(CREATE_TABLE);
        }catch(Exception e){
            updateAdapter();
        }

        setQuery(n);

        execute.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                try{
                    db.execSQL(query.getText().toString());
                    updateAdapter();
                    n++;
                    setQuery(n);
                }catch(Exception e){
                    setTitle("SQL 語法錯誤!");
                }
            }
        });

    }

    public void findView(){
        query = (EditText) findViewById(R.id.editText);
        execute = (Button) findViewById(R.id.button);
        listView = (ListView) findViewById(R.id.listView);
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        db.execSQL("DROP TABLE table01");
        db.close();
    }

    public void setQuery(int n){
        itendata = "資料項目"+n;
        str = "INSERT INTO table01 (num,data) values ("+n+",'"+itendata+"')";
        query.setText(str);
    }
    public void updateAdapter(){
        Cursor c = db.rawQuery("SELECT * FROM table01", null);
        if(c!=null || c.getCount() >=0){
            SimpleCursorAdapter adapter = new SimpleCursorAdapter(this,
                    android.R.layout.simple_list_item_2,
                    c,
                    new String[]{"num", "data"},
                    new int[]{android.R.id.text1,android.R.id.text2},
                    0);
            listView.setAdapter(adapter);
        }
    }
}