package com.example.jamesdouble.checkinspecture;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;


public class makethecheckin extends Activity {

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;
    public Bitmap bmap;
    //image view setting
    ImageView image;
    String namestr;
    String person;
    String idstr;
    String state;
    ProgressBar spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        // getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
        //         WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_makethecheckin);
        spinner=(ProgressBar)findViewById(R.id.progressBar);
        spinner.setVisibility(View.VISIBLE);
        //hideSystemUI();
         namestr = getIntent().getStringExtra("name");
         person = getIntent().getStringExtra("person");
         idstr = getIntent().getStringExtra("id");
         state = getIntent().getStringExtra("state");
        ///////////////////////////////////////////////////
        //粉專圖片 ///////////////////////////////////////////////////////////////////////////////////////////////////////////

        AccessToken accessToken = AccessToken.getCurrentAccessToken();

        GraphRequest request = GraphRequest.newGraphPathRequest(
                accessToken,
                idstr,                           //把ID當作輸入 找PHOTO
                new GraphRequest.Callback() {
                    @Override
                    public void onCompleted(GraphResponse response) {
                        // Insert your code here
                        try {

                            String result = response.toString();
                            String result2 = result.substring(44);
                            JSONObject json = new JSONObject(result2);
                            JSONObject json2 = json.getJSONObject("photos");
                            JSONArray data = json2.getJSONArray("data");
                            Log.e("pink",String.valueOf(data.length()-1));
                            JSONObject images = data.getJSONObject(data.length()-1);
                            JSONArray images2 = images.getJSONArray("images");
                            ////////////////////////////////////////////////////////////////////////
                            //get your values
                            JSONObject link1 = images2.getJSONObject(0);
                            String link2 = new String();
                            link2 = link1.getString("source");
                            Log.e("pink", "粉絲專業有照片");
                            //new DownloadImageTask(image).execute(link2);
                             new DownloadFilesTask().execute(link2);   //抓照片

                        } catch (JSONException e) {
                            drawallthestuff();
                            spinner.setVisibility(View.GONE);
                            Log.e("pink","here now");
                            e.printStackTrace();
                        }
                    }
                });

        Bundle parameters = new Bundle();
        parameters.putString("fields", "photos{images}");
        parameters.putString("access_token", "534104256741044|z5zdUFB0JB3AnCWryDsI8C0NQl8");
        request.setParameters(parameters);

        request.executeAsync();
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();

    }

    private  void drawallthestuff()
    {
        //找出大小
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int windowx = metrics.widthPixels;
        int windowy = metrics.heightPixels;
        /////// 寫上文字   /////////////////////////////////////////////////////////////////////////////
        RelativeLayout layout = (RelativeLayout) findViewById(R.id.root);
        final DrawView view = new DrawView(makethecheckin.this);
        view.setMinimumHeight(windowy);
        view.setMinimumWidth(windowx);
        view.setall(windowx, windowy, person, namestr,bmap,state);

        //通知view组件重绘
        view.invalidate();
        layout.addView(view);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_makethecheckin, menu);
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

    private void hideSystemUI() {
        // Set the IMMERSIVE flag.
        // Set the content to appear under the system bars so that the content
        // doesn't resize when the system bars hide and show.
        View mdecorView = getWindow().getDecorView();
        mdecorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
                        | View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
                        | View.SYSTEM_UI_FLAG_IMMERSIVE);
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "makethecheckin Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.example.jamesdouble.checkinspecture/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "makethecheckin Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.example.jamesdouble.checkinspecture/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }

    class DownloadFilesTask extends AsyncTask<String,Integer,Bitmap>
    {
        @Override
        protected void onPostExecute(Bitmap bitmap) {
            bmap = bitmap;
            drawallthestuff();
            spinner.setVisibility(View.GONE);
        }

        @Override
        protected Bitmap doInBackground(String... strings) {
            try {
                URL url = new URL(strings[0]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);
                connection.connect();
                InputStream input = connection.getInputStream();
                Bitmap myBitmap = BitmapFactory.decodeStream(input);
                return myBitmap;
            } catch (IOException e) {
                // Log exception
                return null;
            }
        }
    }
}


