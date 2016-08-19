package com.example.denny.appcourse_hw8_4102056034;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import javax.microedition.khronos.egl.EGLDisplay;


public class MainActivity extends AppCompatActivity {

    EditText start, end;
    Button   btn;

    List<Address> endLocation=null;
    List<Address> startLocation = null;
    Address addrstart=null;
    Address addrend=null;
     double strlat=0;
     double strlong=0;
     double endlat=0;
     double endlong=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findview();
        final Intent intent = new Intent(MainActivity.this, MapsActivity.class);


        btn.setOnClickListener(new View.OnClickListener() {
            Geocoder geoCoder = new Geocoder(MainActivity.this, Locale.TRADITIONAL_CHINESE);

            @Override
            public void onClick(View v) {
                final String addressStart = start.getText().toString();
                final String addressEnd   = end.getText().toString();
                try {
                    startLocation = geoCoder.getFromLocationName(addressStart, 1);
                    endLocation = geoCoder.getFromLocationName(addressEnd, 1);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (startLocation == null || startLocation.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "起點查無地點", Toast.LENGTH_SHORT).show();
                } else {
                    addrstart = startLocation.get(0);
                    strlat = addrstart.getLatitude();
                    strlong = addrstart.getLongitude();
                }
                if (endLocation == null || endLocation.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "終點查無地點", Toast.LENGTH_SHORT).show();
                } else {
                    addrend = endLocation.get(0);
                    endlat = addrend.getLatitude();
                    endlong = addrend.getLongitude();
                }
                Log.d("startlat", addrstart + "~~~" + addrend);
                Log.d("startlat", "起點：" + strlat + " " + strlong + "終點：" + endlat + " " + endlong);

                Bundle bundle = new Bundle();
                bundle.putDouble("startlat", strlat);
                bundle.putDouble("startlong", strlong);
                bundle.putDouble("endlat", endlat);
                bundle.putDouble("endlong", endlong);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

    }
    public void findview(){
        start = (EditText)findViewById(R.id.editText);
        end   = (EditText)findViewById(R.id.editText2);
        btn   = (Button)findViewById(R.id.button);
    }
}
