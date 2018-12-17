package be.thomasmore.logopedieproject;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.media.AudioManager;
import android.media.MediaDataSource;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileDescriptor;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class ListenGoodActivity extends AppCompatActivity  {
    ArrayList<String> location = new ArrayList<String>();
    String soundName;
    MediaPlayer mp = new MediaPlayer();

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
            soundName = temp.getString("sound");
            Log.e("gogo", soundName);

            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setMessage("Steek een hoofdtelefoon of oortjes in het apparaat!");
            alertDialogBuilder.setCancelable(false);
            alertDialogBuilder.setPositiveButton("Ga verder",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                        }});
            AlertDialog alertDialog = alertDialogBuilder.create();
            //check earbuds
            AudioManager audioManager = (AudioManager)getSystemService(Context.AUDIO_SERVICE);
            if(audioManager.isWiredHeadsetOn()){
                //get rid of the message
                alertDialog.hide();
            }else{
                alertDialog.show();
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    //play button (starts playing the bombardment), first a small introduction
    public  void start(View v){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Steek een hoofdtelefoon of oortjes in het apparaat!");
        alertDialogBuilder.setCancelable(false);
        alertDialogBuilder.setPositiveButton("Ga verder",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    }});
        AlertDialog alertDialog = alertDialogBuilder.create();
        AudioManager audioManager = (AudioManager)getSystemService(Context.AUDIO_SERVICE);
        if(audioManager.isWiredHeadsetOn()){
            //get rid of the message
            alertDialog.hide();
            Log.e("gogo", String.valueOf(soundName));
            //play bombardment
            int soundId = getRaw(soundName);
            Log.e("gogo", String.valueOf(soundId));

                mp = MediaPlayer.create(getApplicationContext(), soundId);
                mp.start();

            RotateAnimation anim = new RotateAnimation(0f, 360f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
            anim.setInterpolator(new LinearInterpolator());
            anim.setRepeatCount(Animation.INFINITE);
            anim.setDuration(3000);

            final ImageView splash = (ImageView) findViewById(R.id.imageView);
            splash.startAnimation(anim);



        }else{
            alertDialog.show();
        }
    }

    public void back(View v){
        Intent intentNew = new Intent(this, GameMainActivity.class);
        intentNew.putExtra("navPosition", location);
        startActivity(intentNew);
        if(mp.isPlaying()){
            mp.stop();
        }
        finish();
    }

    @Override
    public void onBackPressed() {
        Intent intentNew = new Intent(this, GameMainActivity.class);
        intentNew.putExtra("navPosition", location);
        startActivity(intentNew);
        if(mp.isPlaying()){
            mp.stop();
        }
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

    private static int getRaw(String name)
    {
        try {
            return R.raw.class.getField(name).getInt(null);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }

        return -1;
    }
}
