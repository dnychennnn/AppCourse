package com.example.jamesdouble.checkinspecture;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;


public class chooseplace extends Activity {

    List<HashMap<String,Object>> myStrList;
    List<String> stringList;
    HashMap<String,Object> itemHashMap;
    LocationManager lms;
    Location nowlocation;
    Double longitude;
    Double latitude;
    ArrayAdapter<String> adapter1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_chooseplace);
        FacebookSdk.sdkInitialize(getApplicationContext());
        myStrList = new ArrayList<HashMap<String, Object>>();
        stringList = new ArrayList<String>();
        //取得系統定位服務
        LocationManager status = (LocationManager) (this.getSystemService(Context.LOCATION_SERVICE));
        if (status.isProviderEnabled(LocationManager.GPS_PROVIDER) || status.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            //如果GPS或網路定位開啟，呼叫locationServiceInitial()更新位置
            Log.e("GPS", "startlocate");
            locationServiceInitial();
        } else {
            Toast.makeText(this, "請開啟定位服務", Toast.LENGTH_LONG).show();
            startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));    //開啟設定頁面
        }

        //呼叫facebookAPI的必要方法
        AccessToken accessToken = AccessToken.getCurrentAccessToken();

        final GraphRequest request = GraphRequest.newGraphPathRequest(
                accessToken,
                "/search",
                new GraphRequest.Callback() {
                    @Override
                    public void onCompleted(GraphResponse response) {
                        // Insert your code here
                        try {

                            String result = response.toString();
                            String result2 = result.substring(44);
                            JSONObject json = new JSONObject(result2);
                            JSONArray jarray = json.getJSONArray("data");
                            for (int i = 0; i < jarray.length()-1; i++) {
                                JSONObject data = jarray.getJSONObject(i);
                                //get your values
                                String name = new String();
                                String id = new String();
                                String type = new String();
                                String city = new String();
                                id = data.getString("id");
                                name = data.getString("name"); // this will return you the album's name.
                                type = data.getString("category");

                                stringList.add(name);

                                itemHashMap = new HashMap<String, Object>();
                                itemHashMap.put("name", name);
                                itemHashMap.put("id",id);
                                itemHashMap.put("type",type);

                                myStrList.add(itemHashMap);
                            }
                            adapter1.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
        String locationnumber;
        locationnumber = ""+ latitude + ", " + longitude;
        Bundle parameters = new Bundle();
        parameters.putString("q", "");
        parameters.putString("type", "place");
        parameters.putString("center", locationnumber);
        parameters.putString("distance", "5000");
        parameters.putString("access_token","534104256741044|z5zdUFB0JB3AnCWryDsI8C0NQl8");
        request.setParameters(parameters);
        request.executeAsync();


        ///////////////////////////////////////////////////////////////////////////////////

        adapter1 = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,stringList);
        ListView namess = (ListView) findViewById(R.id.placelist);
        namess.setAdapter(adapter1);

        namess.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                HashMap<String, Object> passitem = myStrList.get(position);
                Log.e("d",passitem.get("name").toString());
                Intent returnintent = new Intent();
                returnintent.putExtra("returnData", passitem);
                setResult(100, returnintent);
                finish();
            }
        });
    }


private void locationServiceInitial() {
        lms = (LocationManager) getSystemService(LOCATION_SERVICE);	//取得系統定位服務
        nowlocation = lms.getLastKnownLocation(LocationManager.GPS_PROVIDER);	//使用GPS定位座標
        getLocation(nowlocation);
        }
private void getLocation(Location location) {	//將定位資訊顯示在畫面中
        if(location != null) {

        longitude = location.getLongitude();	//取得經度
        latitude = location.getLatitude();	//取得緯度
        }
        else {
        Toast.makeText(this, "無法定位座標", Toast.LENGTH_LONG).show();
        }
        }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_chooseplace, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
