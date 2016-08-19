package com.example.jamesdouble.checkinspecture;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.Signature;

import static android.app.PendingIntent.getActivity;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


         //Number pick/#######################################################################
        NumberPicker numPicker = (NumberPicker) findViewById(R.id.np1);
        numPicker.setMaxValue(50);
        numPicker.setMinValue(0);
        numPicker.setValue(10);

        //取得使用者選擇的值
        numPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            public void onValueChange(NumberPicker view, int oldValue, int newValue) {
                // tv.setText("pick number is " + String.valueOf(newValue));
            }
        });
        // ###########################################################################################
        Button Checkinbutton = (Button) findViewById(R.id.checkinbutton);
        Checkinbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    PlacePicker.IntentBuilder intentBuilder = new PlacePicker.IntentBuilder();
                    Intent intent = intentBuilder.build(MainActivity.this);
                    //跳到選擇地點的畫面，

                    // Start the Intent by requesting a result, identified by a request code = 1
                    startActivityForResult(intent, 1);

                    // Hide the pick option in the UI to prevent users from starting the picker
                    // multiple times.
                   // showPickAction(false);

                } catch (GooglePlayServicesRepairableException e) {
                    GooglePlayServicesUtil
                            .getErrorDialog(e.getConnectionStatusCode(), MainActivity.this, 0);
                } catch (GooglePlayServicesNotAvailableException e) {
                    Toast.makeText(MainActivity.this, "Google Play Services is not available.",
                            Toast.LENGTH_LONG)
                            .show();
                }
                //############################################################################################
            }
        });



    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {   //傳回來的畫面
        // BEGIN_INCLUDE(activity_result)
        if (requestCode == 1) {                //REQUEST_PLACE_PICKER  打卡傳回來
            // This result is from the PlacePicker dialog.

            // Enable the picker option
          // showPickAction(true);

            if (resultCode == Activity.RESULT_OK) {
                /* User has picked a place, extract data.
                   Data is extracted from the returned intent by retrieving a Place object from
                   the PlacePicker.
                 */
                final Place place = PlacePicker.getPlace(data, MainActivity.this);

                /* A Place object contains details about that place, such as its name, address
                and phone number. Extract the name, address, phone number, place ID and place types.
                 */
                final CharSequence name = place.getName();
                final CharSequence address = place.getAddress();
                final CharSequence phone = place.getPhoneNumber();
                final String placeId = place.getId();
                String attribution = PlacePicker.getAttributions(data);
                if(attribution == null){
                    attribution = "";
                }

                TextView nametext = (TextView) findViewById(R.id.wherename);
                TextView addresstext = (TextView) findViewById(R.id.whereaddress);
                TextView phonetext = (TextView) findViewById(R.id.wherephone);

                // Update data on card.
              /*  getCardStream().getCard(CARD_DETAIL)
                        .setTitle(name.toString())
                        .setDescription(getString(R.string.detail_text, placeId, address, phone,
                                attribution));
                      */
                nametext.setText(name);
                addresstext.setText(address);
                phonetext.setText(phone);


                // Print data to debug log

               // Log.d(TAG, "Place selected: " + placeId + " (" + name.toString() + ")");

                // Show the card.
               // getCardStream().showCard(CARD_DETAIL);

            } else {
                // User has not selected a place, hide the card.
              //  getCardStream().hideCard(CARD_DETAIL);
            }

        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
        // END_INCLUDE(activity_result)
    }





    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

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
