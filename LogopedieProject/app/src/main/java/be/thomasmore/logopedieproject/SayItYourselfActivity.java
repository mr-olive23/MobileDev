package be.thomasmore.logopedieproject;

import android.animation.Animator;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Random;

public class SayItYourselfActivity extends AppCompatActivity {
    ArrayList<String> location = new ArrayList<String>();
    JSONObject word1, word2;
    String picture1, picture2;

    private int correctButtonID;
    ImageView ivShowing = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_say_it_yourself);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //get picture
        Intent intent = getIntent();
        location = intent.getStringArrayListExtra("navPosition");

        //get auditing bombardment file
        String jsonText = loadJSONFromAsset(getApplicationContext());
        try {
            JSONObject config = new JSONObject(jsonText);
            JSONObject temp = config;
            for (int i = 0; i < (location.size()); i++) {
                temp = temp.getJSONObject(location.get(i));
            }
            picture1 = temp.getJSONObject("word1").getString("picture");
            picture2 = temp.getJSONObject("word2").getString("picture");

            String word1 = temp.getJSONObject("word1").getString("word");
            String word2 = temp.getJSONObject("word2").getString("word");

            Button b_1 = (Button) findViewById(R.id.answer_1);
            Button b_2 = (Button) findViewById(R.id.answer_2);

            b_1.setText(word1);
            b_2.setText(word2);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private Drawable randomDrawable() {
        Random r = new Random();
        boolean number = r.nextBoolean();
        if(number) {
            int id = getResources().getIdentifier(picture1, "drawable",getPackageName());
            correctButtonID = R.id.answer_1;
            return ContextCompat.getDrawable(getApplicationContext(), id);
        }else{
            int id = getResources().getIdentifier(picture2, "drawable",getPackageName());
            correctButtonID = R.id.answer_2;
            return ContextCompat.getDrawable(getApplicationContext(), id);
        }
    }

    public void onClickAnswer(View v) {
        int clickedButtonID = v.getId();
        final MediaPlayer goed = MediaPlayer.create(this, R.raw.goedzo);
        final MediaPlayer fout = MediaPlayer.create(this, R.raw.bijna_goed_nog_eens);
        if (clickedButtonID == correctButtonID) {
            // JUIST
            Toast.makeText(this, "CORRECT", Toast.LENGTH_SHORT).show();
            goed.start();

        } else {
            // FOUT
            Toast.makeText(this, "FOUT", Toast.LENGTH_SHORT).show();
            draaiOpnieuwOmNaarVraagteken();
            fout.start();

        }

    }

    private void draaiOpnieuwOmNaarVraagteken(){
        Resources resources = getApplicationContext().getResources();
        final int vraagtekenResourceID = resources.getIdentifier("vraagteken", "drawable", getApplicationContext().getPackageName());
        ivShowing.setRotation(0f);
        ivShowing.animate().rotationY(90f).setListener(new Animator.AnimatorListener(){
            @Override
            public void onAnimationStart(Animator animation)
            {
            }

            @Override
            public void onAnimationRepeat(Animator animation)
            {
            }

            @Override
            public void onAnimationEnd(Animator animation)
            {
                ivShowing.setImageResource(vraagtekenResourceID);
                ivShowing.setRotationY(270f);
                ivShowing.animate().rotationY(360f).setListener(null);
            }

            @Override
            public void onAnimationCancel(Animator animation)
            {
            }
        });
    }

    public void onClickTerug(View v) {


    }

    public void onClickDraaiPrent(View v) {
        final Drawable drawable=randomDrawable();
        ivShowing = (ImageView) findViewById(v.getId());
        ivShowing.setRotationY(0f);
        ivShowing.animate().rotationY(90f).setListener(new Animator.AnimatorListener()
        {

            @Override
            public void onAnimationStart(Animator animation)
            {
            }

            @Override
            public void onAnimationRepeat(Animator animation)
            {
            }

            @Override
            public void onAnimationEnd(Animator animation)
            {
                ivShowing.setImageDrawable(drawable);
                ivShowing.setRotationY(270f);
                ivShowing.animate().rotationY(360f).setListener(null);
            }

            @Override
            public void onAnimationCancel(Animator animation)
            {
            }
        });
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
