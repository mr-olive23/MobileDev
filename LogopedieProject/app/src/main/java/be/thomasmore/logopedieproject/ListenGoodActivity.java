package be.thomasmore.logopedieproject;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class ListenGoodActivity extends AppCompatActivity  {
    ArrayList<String> location = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listen_good);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //get location
        Intent intent = getIntent();
        location = intent.getStringArrayListExtra("navPosition");

        //get auditing bombardment file
        String jsonText = loadJSONFromAsset(getApplicationContext());
        try {
            JSONObject config = new JSONObject(jsonText);
            JSONObject temp = config;
            for (int i = 0; i < location.size(); i++){
                temp = temp.getJSONObject(location.get(i));
            }
            String soundName = temp.getString("sound");
            Log.e("gogo", soundName);






            


















            //check earbuds
            AudioManager audioManager = (AudioManager)getSystemService(Context.AUDIO_SERVICE);
            if(audioManager.isWiredHeadsetOn()){
                //get rid of the message
                Log.e("gogo", "earbuds are plugged in and good to go");
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    //when eearbuds are plugged in remove overlay

    //when earbuds are plugged out add overlay

    //play button (starts playing the bombardment), first a small introduction
    public  void start(View v){

    }

    public void back(View v){
        Intent intentNew = new Intent(this, GameMainActivity.class);
        intentNew.putExtra("navPosition", location);
        startActivity(intentNew);
        finish();
    }

    @Override
    public void onBackPressed() {
        Intent intentNew = new Intent(this, GameMainActivity.class);
        intentNew.putExtra("navPosition", location);
        startActivity(intentNew);
        finish();
    }

    private String loadJSONFromAsset(Context context) {
        String json = null;
        try {
            InputStream is = context.getAssets().open("config.json");

            int size = is.available();

            byte[] buffer = new byte[size];

            is.read(buffer);

            is.close();

            json = new String(buffer, "UTF-8");


        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;

    }
}
