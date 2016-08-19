package com.example.jamesdouble.checkinspecture;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.text.Editable;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class facebookplaces extends Activity {

    List<HashMap<String,Object>> myStrList;
    List<String> stringList;
    HashMap<String,Object> itemHashMap;
    String state;
    String namestr;
    String person;
    String idstr;
    String city;
    String cityinchinese;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fabebookplaces);

        getActionBar().setTitle("打卡惡魔");


        AdView mAdView = (AdView) findViewById(R.id.adView);  //廣告
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);



        final android.os.Bundle k;
        k = null;
        stringList = new ArrayList<String>();
        myStrList = new ArrayList<HashMap<String, Object>>();


        Button start = (Button) findViewById(R.id.startbutton);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent detailed = new Intent(facebookplaces.this,chooseplace.class);
                startActivityForResult(detailed,200);
            }
    });
        Button checkin = (Button) findViewById(R.id.finalcheck);
        checkin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                EditText person = (EditText) findViewById(R.id.editText1);
                EditText states = (EditText) findViewById(R.id.statetext);
                EditText shop = (EditText) findViewById(R.id.namefiled);

                state = states.getText().toString();

                String personstr = new String();
                personstr = person.getText().toString();

                String shopstr = "";
                shopstr = shop.getText().toString();

                Log.e("pink",String.valueOf(shopstr.length()));
                if (shopstr.length() == 0) {
                    Toast.makeText(facebookplaces.this, "請輸入手動地名或選取地標", Toast.LENGTH_LONG).show();
                }
                else {
                    Intent getplaceimg = new Intent(facebookplaces.this, makethecheckin.class);
                    getplaceimg.putExtra("state", state);
                    getplaceimg.putExtra("person", personstr);
                    getplaceimg.putExtra("id", idstr);
                    getplaceimg.putExtra("name", shopstr);
                    startActivity(getplaceimg);
                }
            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {      //有人從另一個地方回來
        super.onActivityResult(requestCode, resultCode, data);                        //(此acitvity原本就活著)
        if(requestCode==200 && resultCode==100) {                                  //確保是我出去，回來的是想要的
            HashMap<String,Object> got = (HashMap<String, Object>) data.getExtras().get("returnData");

            namestr = (String) got.get("name");
            person = getIntent().getStringExtra("person");
            idstr = (String) got.get("id");
            EditText name = (EditText) findViewById(R.id.namefiled);
            name.setText(namestr);
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_fabebookplaces, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case R.id.action_help:
                Intent help = new Intent(facebookplaces.this,appinstr.class);
                startActivity(help);
                return true;
            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);
        }
    }
}
