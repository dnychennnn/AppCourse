package com.example.denny.appcourse_4102056034_hw7;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MainActivity extends AppCompatActivity {
    SQLiteDatabase db;
    EditText input;
    Button tophone, toserver, phonedb, serverdb;
    ListView listView;
    ArrayAdapter<String> arrayAdapter = null;
    static final String TAG = "MyTAG";
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://192.168.1.255:8889/pm25"; //jdbc:mysql://mysql_IP位址:port/資料庫名稱
    private final static String CREATE_TABLE = "CREATE TABLE table01(_id INTEGER PRIMARY KEY,pm25 INTEGER)";
    String str, itendata, query;
    int n=1;

    static final String USER = "root"; //mysql帳號
    static final String PWD = "root";  //mysql密碼

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupview();

        db = openOrCreateDatabase("db1.db", MODE_PRIVATE, null);
        try{
            db.execSQL(CREATE_TABLE);
        }catch (Exception e){
        }

        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);

        tophone.setOnClickListener(btnclick);
        toserver.setOnClickListener(btnclick);
        serverdb.setOnClickListener(btnclick);
        phonedb.setOnClickListener(btnclick);


    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        db.execSQL("DROP TABLE table01");
        db.close();
    }

    private void insertJDBC() {

        try {
            Class.forName(JDBC_DRIVER);

            Log.d(TAG, "Connecting to database...");
            Connection conn = DriverManager.getConnection(DB_URL, USER, PWD);

            Log.d(TAG, "Creating statement...");
            Statement stmt = conn.createStatement();

            String sql  = "insert into table01(data) values(?)";

            Log.d(TAG, "inserting data...");
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, Integer.parseInt(input.getText().toString()));
            pst.executeUpdate();
            Log.d(TAG, "inserting data done...");

            stmt.close();
            conn.close();

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            Log.d(TAG, "ClassNotFoundException:" + e.toString());
        } catch (SQLException e) {
            e.printStackTrace();
            Log.d(TAG, "SQLException:" + e.toString());
        }
    }

    private ArrayAdapter<String> jdbc() {
        ArrayAdapter<String> ad = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
        try {

            Class.forName(JDBC_DRIVER);

            Log.d(TAG, "Connecting to database...");
            Connection conn = DriverManager.getConnection(DB_URL, USER, PWD);

            Log.d(TAG, "Creating statement...");
            Statement stmt = conn.createStatement();

            String sql  = "select * from table01";
            ResultSet rs = stmt.executeQuery(sql);

            while(rs.next()){
                //Log.d(TAG, String.valueOf(rs.getInt("id")) + "&&" + String.valueOf(rs.getInt("data")));
                ad.add(String.valueOf(rs.getInt("data")));
            }

            rs.close();
            stmt.close();
            conn.close();

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            Log.d(TAG, "ClassNotFoundException:" + e.toString());
        } catch (SQLException e) {
            e.printStackTrace();
            Log.d(TAG, "SQLException:" + e.toString());
        }finally {
            return ad;
        }
    }
    public void UpdateLocalAdpater(){
        Cursor c = db.rawQuery("SELECT * FROM table01", null);
        if(c!=null && c.getCount()>=0)
        {
            SimpleCursorAdapter adp = new SimpleCursorAdapter(this,
                    android.R.layout.simple_list_item_2,
                    c,
                    new String[]{"_id", "pm25"},
                    new int[]{android.R.id.text1, android.R.id.text2},
                    0);
            listView.setAdapter(adp);
        }
    }

    Button.OnClickListener btnclick = new Button.OnClickListener(){
        @Override
        public void onClick(View v) {
            switch(v.getId()){
                case R.id.button2:       // save to local
                {
                    try{
                        db.execSQL("INSERT INTO table01 (pm25) values (" + Integer.parseInt(input.getText().toString()) + ")");
                    }catch (Exception e){
                        setTitle("SQL 語法錯誤!");
                    }
                    break;
                }
                case R.id.button3:      // save to server
                {
                    new Thread(new Runnable() {
                        public void run() {
                            insertJDBC();
                        }
                    }).start();
                    break;
                }
                case R.id.button4:      //load local
                {
                    UpdateLocalAdpater();
                    break;
                }
                case R.id.button5:      //load server
                {

                    Thread t = new Thread(new Runnable() {
                        public void run() {
                            arrayAdapter = jdbc();
                        }
                    });
                    t.start();
                    try{
                        t.join();
                    }catch(Exception e){
                        Log.d(TAG, "Thread Exception:" + e.toString());
                    }
                    listView.setAdapter(arrayAdapter);
                    break;
                }
                default:break;
            }
        }
    };

    private void setupview()
    {
        input = (EditText)findViewById(R.id.editText);
        tophone = (Button)findViewById(R.id.button2);
        toserver = (Button)findViewById(R.id.button3);
        phonedb = (Button)findViewById(R.id.button4);
        serverdb = (Button)findViewById(R.id.button5);
        listView = (ListView)findViewById(R.id.listView);
    }


    public void setQuery(int n) {
        itendata = "資料項目" + n;
        str = "INSERT INTO pm25 (num,data) values (" + n + ",'" + itendata + "')";
        query = str;
    }

    public void updateAdapter() {
        Cursor c = db.rawQuery("SELECT * FROM pm25", null);
        if (c != null || c.getCount() >= 0) {
            SimpleCursorAdapter adapter = new SimpleCursorAdapter(this,
                    android.R.layout.simple_list_item_2,
                    c,
                    new String[]{"num", "data"},
                    new int[]{android.R.id.text1, android.R.id.text2},
                    0);
            listView.setAdapter(adapter);
        }

    }

}

